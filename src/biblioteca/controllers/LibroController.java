package biblioteca.controllers;

import biblioteca.controllers.CsvController;
import biblioteca.models.Libro;
import biblioteca.models.Sucursal;
import java.io.IOException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextInputDialog;
import java.util.Optional;

/**
 * Controlador para la vista de gestión de libros en la aplicación de biblioteca.
 * Maneja las operaciones de agregar, editar y eliminar libros, así como la carga
 * y guardado de datos en un archivo CSV.
 * 
 * @author Angel Sanabria, Javier Alvarado
 * @version 1.0
 * @since 2024-09-04
 * @lastModified 2024-09-06
 */
public class LibroController {

    @FXML
    private TableView<Libro> tablaLibros; // Tabla que muestra los libros

    @FXML
    private TableColumn<Libro, String> columnaISBN; // Columna para el ISBN del libro

    @FXML
    private TableColumn<Libro, String> columnaTitulo; // Columna para el título del libro

    @FXML
    private TableColumn<Libro, String> columnaAutor; // Columna para el autor del libro

    @FXML
    private TableColumn<Libro, Integer> columnaAnio; // Columna para el año de publicación del libro

    @FXML
    private TableColumn<Libro, String> columnaGenero; // Columna para el género del libro

    private ObservableList<Libro> listaLibros; // Lista de libros que se muestra en la tabla
    private Sucursal sucursal; // Sucursal asociada a los libros
    
    private static final String RUTA_LIBROS = "src/biblioteca/db/libros.csv"; // Ruta del archivo CSV

    /**
     * Inicializa el controlador. Carga los libros desde el archivo CSV y configura las columnas de la tabla.
     */
    @FXML
    public void initialize() {
        sucursal = new Sucursal("Sucursal Central", "Calle Principal 123");
        try {
            listaLibros = FXCollections.observableArrayList(CsvController.cargarLibrosDesdeCSV(RUTA_LIBROS));
        } catch (IOException e) {
            System.out.println(e);
            mostrarAlertaError("Error de Carga", "No se pudieron cargar los libros desde el archivo CSV.");
        }

        // Configura las columnas de la tabla con las propiedades del libro
        columnaISBN.setCellValueFactory(cellData -> cellData.getValue().ISBNProperty());
        columnaTitulo.setCellValueFactory(cellData -> cellData.getValue().tituloProperty());
        columnaAutor.setCellValueFactory(cellData -> cellData.getValue().autorProperty());
        columnaAnio.setCellValueFactory(cellData -> cellData.getValue().anioPublicacionProperty().asObject());
        columnaGenero.setCellValueFactory(cellData -> cellData.getValue().generoProperty());

        tablaLibros.setItems(listaLibros);
    }

    /**
     * Agrega un nuevo libro a la lista y lo guarda en el archivo CSV.
     * Solicita al usuario los detalles del libro mediante diálogos de entrada.
     */
    @FXML
    private void agregarLibro() {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Agregar Libro");
        dialog.setHeaderText("Ingrese los detalles del libro.");
        dialog.setContentText("ISBN:");

        Optional<String> isbn = dialog.showAndWait();
        if (isbn.isPresent()) {
            String titulo = obtenerEntrada("Título:");
            String autor = obtenerEntrada("Autor:");
            int anio = Integer.parseInt(obtenerEntrada("Año de Publicación:"));
            String genero = obtenerEntrada("Género:");

            Libro libro = new Libro(isbn.get(), titulo, autor, anio, genero);
            listaLibros.add(libro);
            tablaLibros.setItems(listaLibros);
            try {
                CsvController.guardarLibrosEnCSV(listaLibros, RUTA_LIBROS);
            } catch (IOException e) {
                mostrarAlertaError("Error de Guardado", "No se pudo guardar el libro en el archivo CSV.");
            }
        }
    }

    /**
     * Solicita al usuario una entrada de texto mediante un diálogo de entrada.
     * 
     * @param mensaje Mensaje que se muestra en el diálogo de entrada.
     * @return La entrada del usuario como una cadena de texto.
     */
    private String obtenerEntrada(String mensaje) {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Entrada");
        dialog.setHeaderText(mensaje);
        Optional<String> resultado = dialog.showAndWait();
        return resultado.orElse("");
    }

    /**
     * Edita el libro seleccionado en la tabla. Solicita al usuario los nuevos datos del libro.
     * Actualiza los valores del libro en la tabla y en el archivo CSV.
     */
    @FXML
    private void editarLibro() {
        Libro libroSeleccionado = tablaLibros.getSelectionModel().getSelectedItem();
        if (libroSeleccionado != null) {
            TextInputDialog dialog = new TextInputDialog(libroSeleccionado.getTitulo().get());
            dialog.setTitle("Editar Libro");
            dialog.setHeaderText("Editar datos del libro seleccionado:");
            dialog.setContentText("Formato: Titulo,Autor,Año,Genero");

            Optional<String> result = dialog.showAndWait();
            result.ifPresent(datos -> {
                String[] partes = datos.split(",");
                if (partes.length == 4) {
                    libroSeleccionado.getTitulo().set(partes[0]);
                    libroSeleccionado.getAutor().set(partes[1]);
                    libroSeleccionado.getAnioPublicacion().set(Integer.parseInt(partes[2]));
                    libroSeleccionado.getGenero().set(partes[3]);
                    tablaLibros.refresh(); // Refresca la tabla para mostrar los cambios
                } else {
                    mostrarAlertaError("Datos incorrectos", "Formato incorrecto. Debe ingresar todos los campos.");
                }
            });
        } else {
            mostrarAlertaError("Sin selección", "Debe seleccionar un libro para editar.");
        }
    }

    /**
     * Elimina el libro seleccionado en la tabla y actualiza el archivo CSV.
     * Muestra una alerta si no se ha seleccionado ningún libro.
     */
    @FXML
    private void eliminarLibro() {
        Libro libroSeleccionado = tablaLibros.getSelectionModel().getSelectedItem();
        if (libroSeleccionado != null) {
            sucursal.getLibros().remove(libroSeleccionado);
            listaLibros.remove(libroSeleccionado);
            try {
                CsvController.guardarLibrosEnCSV(listaLibros, RUTA_LIBROS);
            } catch (IOException e) {
                mostrarAlertaError("Error de Guardado", "No se pudieron guardar los libros en el archivo CSV.");
            }
        } else {
            mostrarAlertaError("Sin selección", "Debe seleccionar un libro para eliminar.");
        }
    }

    /**
     * Muestra una alerta de error con el título y mensaje proporcionados.
     * 
     * @param titulo El título de la alerta de error.
     * @param mensaje El mensaje de la alerta de error.
     */
    private void mostrarAlertaError(String titulo, String mensaje) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
}

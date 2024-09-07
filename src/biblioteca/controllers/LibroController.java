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

public class LibroController {

    @FXML
    private TableView<Libro> tablaLibros;

    @FXML
    private TableColumn<Libro, String> columnaISBN;

    @FXML
    private TableColumn<Libro, String> columnaTitulo;

    @FXML
    private TableColumn<Libro, String> columnaAutor;

    @FXML
    private TableColumn<Libro, Integer> columnaAnio;

    @FXML
    private TableColumn<Libro, String> columnaGenero;

    private ObservableList<Libro> listaLibros;
    private Sucursal sucursal;
    
    private static final String RUTA_LIBROS = "src/biblioteca/db/libros.csv";

    @FXML
    public void initialize() {
        sucursal = new Sucursal("Sucursal Central", "Calle Principal 123");
        try {
            listaLibros = FXCollections.observableArrayList(CsvController.cargarLibrosDesdeCSV(RUTA_LIBROS));
        } catch (IOException e) {
            System.out.println(e);
            mostrarAlertaError("Error de Carga", "No se pudieron cargar los libros desde el archivo CSV.");
        }

        columnaISBN.setCellValueFactory(cellData -> cellData.getValue().ISBNProperty());
        columnaTitulo.setCellValueFactory(cellData -> cellData.getValue().tituloProperty());
        columnaAutor.setCellValueFactory(cellData -> cellData.getValue().autorProperty());
        columnaAnio.setCellValueFactory(cellData -> cellData.getValue().anioPublicacionProperty().asObject());
        columnaGenero.setCellValueFactory(cellData -> cellData.getValue().generoProperty());

        tablaLibros.setItems(listaLibros);
    }

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

    private String obtenerEntrada(String mensaje) {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Entrada");
        dialog.setHeaderText(mensaje);
        Optional<String> resultado = dialog.showAndWait();
        return resultado.orElse("");
    }

    @FXML
    private void editarLibro() {
        Libro libroSeleccionado = tablaLibros.getSelectionModel().getSelectedItem();
        if (libroSeleccionado != null) {
            // Usamos el valor del título para prellenar el diálogo de edición
            TextInputDialog dialog = new TextInputDialog(libroSeleccionado.getTitulo().get());
            dialog.setTitle("Editar Libro");
            dialog.setHeaderText("Editar datos del libro seleccionado:");
            dialog.setContentText("Formato: Titulo,Autor,Año,Genero");

            Optional<String> result = dialog.showAndWait();
            result.ifPresent(datos -> {
                String[] partes = datos.split(",");
                if (partes.length == 4) {
                    // Actualizamos los valores del libro seleccionado
                    libroSeleccionado.getTitulo().set(partes[0]);
                    libroSeleccionado.getAutor().set(partes[1]);
                    libroSeleccionado.getAnioPublicacion().set(Integer.parseInt(partes[2]));
                    libroSeleccionado.getGenero().set(partes[3]);
                    tablaLibros.refresh(); // Refrescamos la tabla para mostrar los cambios
                } else {
                    mostrarAlertaError("Datos incorrectos", "Formato incorrecto. Debe ingresar todos los campos.");
                }
            });
        } else {
            mostrarAlertaError("Sin selección", "Debe seleccionar un libro para editar.");
        }
    }

    @FXML
    private void eliminarLibro() {
        Libro libroSeleccionado = tablaLibros.getSelectionModel().getSelectedItem();
        if (libroSeleccionado != null) {
            sucursal.getLibros().remove(libroSeleccionado);
            listaLibros.remove(libroSeleccionado);
            try {
                CsvController.guardarLibrosEnCSV(listaLibros, RUTA_LIBROS);
            } catch (IOException e) {
                mostrarAlertaError("Error de Guardado", "No se pudieron guardar los miembros en el archivo CSV.");
            }
        } else {
            mostrarAlertaError("Sin selección", "Debe seleccionar un libro para eliminar.");
        }
    }

    private void mostrarAlertaError(String titulo, String mensaje) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
}

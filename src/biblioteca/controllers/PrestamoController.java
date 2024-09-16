package biblioteca.controllers;

import biblioteca.models.Libro;
import biblioteca.models.Miembro;
import biblioteca.models.Prestamo;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.StringProperty;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

/**
 * Controlador para la gestión de préstamos de libros en la biblioteca.
 * 
 * Este controlador se encarga de cargar datos desde archivos CSV, mostrar la lista de préstamos en una tabla,
 * y manejar las acciones de registro y devolución de préstamos.
 * 
 * Autores: Angel Sanabria y Javier Alvarado
 * Fecha de creación: 04/09/2024
 * Fecha de última modificación: 06/09/2024
 */
public class PrestamoController {

    @FXML
    private TableView<Prestamo> tablaPrestamos;

    @FXML
    private TableColumn<Prestamo, String> columnaLibro;

    @FXML
    private TableColumn<Prestamo, String> columnaMiembro;

    @FXML
    private TableColumn<Prestamo, String> columnaFechaPrestamo;

    @FXML
    private TableColumn<Prestamo, String> columnaFechaDevolucion;

    @FXML
    private TableColumn<Prestamo, String> columnaFechaDevolucionReal;

    @FXML
    private TextField textFieldISBN;

    @FXML
    private TextField textFieldMiembroID;

    @FXML
    private TextField textFieldDias;

    private ObservableList<Prestamo> listaPrestamos;
    private ObservableList<Miembro> listaMiembros;
    private ObservableList<Libro> listaLibros;

    // Ruta a los archivos CSV
    private static final String RUTA_LIBROS = "src/biblioteca/db/libros.csv";
    private static final String RUTA_MIEMBROS = "src/biblioteca/db/miembros.csv";
    private static final String RUTA_PRESTAMOS = "src/biblioteca/db/prestamos.csv";

    /**
     * Inicializa el controlador. Carga los datos desde los archivos CSV y configura las columnas de la tabla.
     */
    @FXML
    public void initialize() {
        cargarDatos();
        try {
            // Cargar los libros, miembros y préstamos desde los archivos CSV
            List<Libro> libros = CsvController.cargarLibrosDesdeCSV(RUTA_LIBROS);
            listaMiembros = FXCollections.observableArrayList(CsvController.cargarMiembrosDesdeCSV(RUTA_MIEMBROS));
            listaPrestamos = FXCollections.observableArrayList(CsvController.cargarPrestamosDesdeCSV(RUTA_PRESTAMOS, libros, listaMiembros));
            listaLibros = FXCollections.observableArrayList(libros);

            // Configurar las columnas de la tabla
            columnaLibro.setCellValueFactory(cellData -> {
                Libro libro = cellData.getValue().getLibro();
                return libro != null ? libro.getTitulo() : new SimpleStringProperty("");
            });

            columnaMiembro.setCellValueFactory(cellData -> {
                Miembro miembro = cellData.getValue().getMiembro();
                return miembro != null ? miembro.getNombre() : new SimpleStringProperty("");
            });

            columnaFechaPrestamo.setCellValueFactory(cellData -> {
                LocalDate fechaPrestamo = cellData.getValue().getFechaPrestamo().get();
                return fechaPrestamo != null ? new SimpleStringProperty(fechaPrestamo.toString()) : new SimpleStringProperty("");
            });

            columnaFechaDevolucion.setCellValueFactory(cellData -> {
                LocalDate fechaDevolucion = cellData.getValue().getFechaDevolucionEsperada().get();
                return fechaDevolucion != null ? new SimpleStringProperty(fechaDevolucion.toString()) : new SimpleStringProperty("");
            });

            columnaFechaDevolucionReal.setCellValueFactory(cellData -> {
                LocalDate fechaDevolucionReal = cellData.getValue().getFechaDevolucionReal().get();
                return fechaDevolucionReal != null ? new SimpleStringProperty(fechaDevolucionReal.toString()) : new SimpleStringProperty("");
            });

            // Añadir los préstamos a la tabla
            tablaPrestamos.setItems(listaPrestamos);

        } catch (IOException e) {
            mostrarAlertaError("Error al cargar datos", "No se pudieron cargar los datos desde los archivos CSV.");
        }
    }

    /**
     * Carga los datos desde los archivos CSV y actualiza la tabla de préstamos.
     */
    public void cargarDatos() {
        try {
            // Cargar los libros, miembros y préstamos desde los archivos CSV
            List<Libro> libros = CsvController.cargarLibrosDesdeCSV(RUTA_LIBROS);
            listaMiembros = FXCollections.observableArrayList(CsvController.cargarMiembrosDesdeCSV(RUTA_MIEMBROS));
            listaPrestamos = FXCollections.observableArrayList(CsvController.cargarPrestamosDesdeCSV(RUTA_PRESTAMOS, libros, listaMiembros));
            listaLibros = FXCollections.observableArrayList(libros);
            // Actualizar la tabla con los préstamos
            tablaPrestamos.setItems(listaPrestamos);

        } catch (IOException e) {
            mostrarAlertaError("Error al cargar datos", "No se pudieron cargar los datos desde los archivos CSV.");
        }
    }

    /**
     * Registra un nuevo préstamo de libro.
     * Valida los campos de entrada, verifica la disponibilidad del libro, y guarda el préstamo en el archivo CSV.
     */
    @FXML
    private void registrarPrestamo() {
        cargarDatos();
        String isbn = textFieldISBN.getText().trim();
        String miembroID = textFieldMiembroID.getText().trim();
        String diasPrestamoTexto = textFieldDias.getText().trim();
        if (isbn.isEmpty() || miembroID.isEmpty() || diasPrestamoTexto.isEmpty()) {
            mostrarAlertaError("Campos vacíos", "Por favor, complete todos los campos.");
            return;
        }

        try {
            int diasPrestamo = Integer.parseInt(diasPrestamoTexto);
            Libro libro = buscarLibroPorISBN(isbn);
            Miembro miembro = buscarMiembroPorID(miembroID);
            if (libro != null && miembro != null && libro.isDisponible()) {
                // Verificar si el libro ya está prestado y no ha sido devuelto
                if (esLibroEnPrestamo(libro)) {
                    mostrarAlertaError("Libro ya prestado", "El libro ya está en préstamo y no ha sido devuelto.");
                    return;
                }
                LocalDate fechaPrestamo = LocalDate.now();
                LocalDate fechaDevolucionEsperada = fechaPrestamo.plusDays(diasPrestamo);
                Prestamo nuevoPrestamo = new Prestamo(libro, miembro, fechaPrestamo, fechaDevolucionEsperada);
                listaPrestamos.add(nuevoPrestamo);
                EstadisticasController estadisticas = new EstadisticasController();
                estadisticas.cargarDatos();
                try {
                    CsvController.guardarPrestamosEnCSV(listaPrestamos, RUTA_PRESTAMOS);
                } catch (IOException e) {
                    mostrarAlertaError("Error al guardar", "No se pudo guardar el préstamo en el archivo CSV.");
                }

                limpiarCampos();
            } else {
                mostrarAlertaError("Datos incorrectos", "Libro no disponible o miembro no encontrado.");
            }
        } catch (NumberFormatException e) {
            mostrarAlertaError("Error de formato", "El campo de días de préstamo debe ser un número válido.");
        }
    }

    /**
     * Devuelve un libro prestado. Actualiza la fecha de devolución real y la disponibilidad del libro.
     * 
     * Valida que el libro esté en préstamo y no se haya devuelto aún.
     */
    @FXML
    private void devolverLibro() {
        cargarDatos();
        String isbn = textFieldISBN.getText().trim();
        if (isbn.isEmpty()) {
            mostrarAlertaError("Campo vacío", "Por favor, complete el campo ISBN.");
            return;
        }

        Libro libro = buscarLibroPorISBN(isbn);
        if (libro != null) {
            for (Prestamo prestamo : listaPrestamos) {
                if (prestamo.getLibro().equals(libro) && prestamo.getFechaDevolucionReal().get() == null) {
                    prestamo.setFechaDevolucionReal(LocalDate.now());
                    libro.setDisponible(true);
                    try {
                        CsvController.guardarPrestamosEnCSV(listaPrestamos, RUTA_PRESTAMOS);
                        CsvController.guardarLibrosEnCSV(listaLibros, RUTA_LIBROS);
                    } catch (IOException e) {
                        mostrarAlertaError("Error al guardar", "No se pudo actualizar el archivo CSV.");
                    }
                    mostrarAlertaInformacion("Préstamo devuelto", "El libro ha sido devuelto correctamente.");
                    return;
                }
            }
            mostrarAlertaError("Préstamo no encontrado", "No se encontró un préstamo activo para el libro con el ISBN proporcionado.");
        } else {
            mostrarAlertaError("Libro no encontrado", "No se encontró un libro con el ISBN proporcionado.");
        }
    }

    /**
     * Verifica si un libro está actualmente en préstamo y no ha sido devuelto.
     *
     * @param libro El libro a verificar.
     * @return true si el libro está en préstamo; false en caso contrario.
     */
    private boolean esLibroEnPrestamo(Libro libro) {
        for (Prestamo prestamo : listaPrestamos) {
            if (prestamo.getLibro().equals(libro) && prestamo.getFechaDevolucionReal().get() == null) {
                return true;
            }
        }
        return false;
    }

    /**
     * Busca un libro por su ISBN en la lista de libros.
     *
     * @param isbn El ISBN del libro a buscar.
     * @return El libro correspondiente al ISBN proporcionado, o null si no se encuentra.
     */
    private Libro buscarLibroPorISBN(String isbn) {
        for (Libro libro : listaLibros) {
            if (libro.getISBN().get().equals(isbn)) {
                return libro;
            }
        }
        return null;
    }

    /**
     * Busca un miembro por su ID en la lista de miembros.
     *
     * @param id El ID del miembro a buscar.
     * @return El miembro correspondiente al ID proporcionado, o null si no se encuentra.
     */
    private Miembro buscarMiembroPorID(String id) {
        for (Miembro miembro : listaMiembros) {
            if (miembro.getId().get().equals(id)) {
                return miembro;
            }
        }
        return null;
    }

    /**
     * Muestra un mensaje de alerta de error.
     *
     * @param titulo   El título de la alerta.
     * @param mensaje  El mensaje de la alerta.
     */
    private void mostrarAlertaError(String titulo, String mensaje) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }

    /**
     * Muestra un mensaje de alerta de información.
     *
     * @param titulo   El título de la alerta.
     * @param mensaje  El mensaje de la alerta.
     */
    private void mostrarAlertaInformacion(String titulo, String mensaje) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }

    /**
     * Limpia los campos de entrada.
     */
    private void limpiarCampos() {
        textFieldISBN.clear();
        textFieldMiembroID.clear();
        textFieldDias.clear();
    }
}

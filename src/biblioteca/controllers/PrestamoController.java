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
                LocalDate fechaPrestamo = LocalDate.now();
                LocalDate fechaDevolucionEsperada = fechaPrestamo.plusDays(diasPrestamo);
                Prestamo nuevoPrestamo = new Prestamo(libro, miembro, fechaPrestamo, fechaDevolucionEsperada);
                listaPrestamos.add(nuevoPrestamo);

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

    private void limpiarCampos() {
        textFieldISBN.clear();
        textFieldMiembroID.clear();
        textFieldDias.clear();
    }

    private Libro buscarLibroPorISBN(String isbn) {
        for (Libro libro : listaLibros) {
            if (libro.getISBN().get().equals(isbn)) {
                return libro;
            }
        }
        return null;
    }

    private Miembro buscarMiembroPorID(String id) {
        for (Miembro miembro : listaMiembros) {
            if (miembro.getId().get().equals(id)) {
                return miembro;
            }
        }
        return null;
    }

    private void mostrarAlertaError(String titulo, String mensaje) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }

    @FXML
    private void devolverLibro() {
        Prestamo prestamoSeleccionado = tablaPrestamos.getSelectionModel().getSelectedItem();
        if (prestamoSeleccionado != null) {
            // Actualizar la fecha de devolución real a la fecha de hoy
            prestamoSeleccionado.setFechaDevolucionReal(LocalDate.now());

            // Cambiar la disponibilidad del libro a true
            prestamoSeleccionado.getLibro().setDisponible(true);

            // No eliminar el préstamo de la lista, solo actualizar
            tablaPrestamos.refresh();

            try {
                // Guardar la lista actualizada en el archivo CSV
                CsvController.guardarPrestamosEnCSV(listaPrestamos, RUTA_PRESTAMOS);

                // Actualizar el estado de los libros en el archivo CSV de libros
                List<Libro> libros = CsvController.cargarLibrosDesdeCSV(RUTA_LIBROS);
                for (Libro libro : libros) {
                    if (libro.getISBN().get().equals(prestamoSeleccionado.getLibro().getISBN().get())) {
                        libro.setDisponible(true); // Actualizar disponibilidad
                    }
                }
                CsvController.guardarLibrosEnCSV(libros, RUTA_LIBROS); // Guardar cambios

            } catch (IOException e) {
                mostrarAlertaError("Error al guardar", "No se pudo guardar la devolución en el archivo CSV.");
            }
        } else {
            mostrarAlertaError("No se seleccionó ningún préstamo", "Por favor, seleccione un préstamo para devolver.");
        }
    }
}

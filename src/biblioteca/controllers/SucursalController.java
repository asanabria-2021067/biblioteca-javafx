package biblioteca.controllers;

import biblioteca.models.Sucursal;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;

import java.io.IOException;

public class SucursalController {

    @FXML
    private TableView<Sucursal> tablaSucursales;

    @FXML
    private TableColumn<Sucursal, String> columnaNombre;

    @FXML
    private TableColumn<Sucursal, String> columnaDireccion;

    @FXML
    private TextField textFieldNombre;

    @FXML
    private TextField textFieldDireccion;

    private ObservableList<Sucursal> listaSucursales;

    private static final String RUTA_SUCURSALES = "src/biblioteca/db/sucursales.csv";

    @FXML
    public void initialize() {
        try {
            listaSucursales = FXCollections.observableArrayList(CsvController.cargarSucursalesDesdeCSV(RUTA_SUCURSALES));
        } catch (IOException e) {
            mostrarAlertaError("Error de Carga", "No se pudieron cargar las sucursales desde el archivo CSV.");
        }

        columnaNombre.setCellValueFactory(cellData -> cellData.getValue().getNombre());
        columnaDireccion.setCellValueFactory(cellData -> cellData.getValue().getDireccion());

        tablaSucursales.setItems(listaSucursales);
    }

    @FXML
    private void handleAgregarSucursal() {
        String nombre = textFieldNombre.getText();
        String direccion = textFieldDireccion.getText();

        if (nombre.isEmpty() || direccion.isEmpty()) {
            mostrarAlertaError("Campos vacíos", "Debe completar todos los campos.");
            return;
        }

        Sucursal nuevaSucursal = new Sucursal(nombre, direccion);
        listaSucursales.add(nuevaSucursal);

        try {
            CsvController.guardarSucursalesEnCSV(listaSucursales, RUTA_SUCURSALES);
        } catch (IOException e) {
            mostrarAlertaError("Error de Guardado", "No se pudieron guardar las sucursales en el archivo CSV.");
        }

        // Limpiar campos después de agregar la sucursal
        textFieldNombre.clear();
        textFieldDireccion.clear();
    }

    @FXML
    private void eliminarSucursal() {
        Sucursal sucursalSeleccionada = tablaSucursales.getSelectionModel().getSelectedItem();
        if (sucursalSeleccionada != null) {
            listaSucursales.remove(sucursalSeleccionada);
            try {
                CsvController.guardarSucursalesEnCSV(listaSucursales, RUTA_SUCURSALES);
            } catch (IOException e) {
                mostrarAlertaError("Error de Guardado", "No se pudieron guardar las sucursales en el archivo CSV.");
            }
        } else {
            mostrarAlertaError("Sin selección", "Debe seleccionar una sucursal para eliminar.");
        }
    }

    private void mostrarAlertaError(String titulo, String mensaje) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
}

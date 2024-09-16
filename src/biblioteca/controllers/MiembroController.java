package biblioteca.controllers;

import biblioteca.models.Miembro;
import biblioteca.models.Sucursal;
import java.io.IOException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TextInputDialog;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import java.util.Optional;

/**
 * Controlador para la gestión de miembros en la aplicación de biblioteca. 
 * Permite visualizar, agregar, editar y eliminar miembros desde la interfaz gráfica.
 * 
 * @author Angel Sanabria, Javier Alvarado
 * @version 1.0
 * @since 2024-09-04
 * @lastModified 2024-09-06
 */
public class MiembroController {

    @FXML
    private TableView<Miembro> tablaMiembros; // Tabla que muestra la lista de miembros

    @FXML
    private TableColumn<Miembro, String> columnaID; // Columna para el ID del miembro

    @FXML
    private TableColumn<Miembro, String> columnaNombre; // Columna para el nombre del miembro

    @FXML
    private TableColumn<Miembro, String> columnaEmail; // Columna para el email del miembro

    @FXML
    private TableColumn<Miembro, String> columnaTelefono; // Columna para el teléfono del miembro

    @FXML
    private TextField textFieldId; // Campo de texto para el ID del miembro

    @FXML
    private TextField textFieldNombre; // Campo de texto para el nombre del miembro

    @FXML
    private TextField textFieldEmail; // Campo de texto para el email del miembro

    @FXML
    private TextField textFieldTelefono; // Campo de texto para el teléfono del miembro

    private ObservableList<Miembro> listaMiembros; // Lista observable de miembros
    private Sucursal sucursal; // Sucursal asociada

    private static final String RUTA_MIEMBROS = "src/biblioteca/db/miembros.csv"; // Ruta del archivo CSV

    /**
     * Inicializa el controlador. Carga los miembros desde el archivo CSV y configura las columnas de la tabla.
     */
    @FXML
    public void initialize() {
        sucursal = new Sucursal("Sucursal Central", "Calle Principal 123");
        try {
            listaMiembros = FXCollections.observableArrayList(CsvController.cargarMiembrosDesdeCSV(RUTA_MIEMBROS));
        } catch (IOException e) {
            mostrarAlertaError("Error de Carga", "No se pudieron cargar los miembros desde el archivo CSV.");
        }

        columnaID.setCellValueFactory(cellData -> cellData.getValue().getId());
        columnaNombre.setCellValueFactory(cellData -> cellData.getValue().getNombre());
        columnaEmail.setCellValueFactory(cellData -> cellData.getValue().getEmail());
        columnaTelefono.setCellValueFactory(cellData -> cellData.getValue().getTelefono());

        tablaMiembros.setItems(listaMiembros);
    }

    /**
     * Carga los datos de un miembro en los campos de texto.
     * 
     * @param miembro El miembro del cual se van a cargar los datos.
     */
    private void cargarDatosEnCampos(Miembro miembro) {
        if (miembro != null) {
            textFieldId.setText(miembro.getId().get());
            textFieldNombre.setText(miembro.getNombre().get());
            textFieldEmail.setText(miembro.getEmail().get());
            textFieldTelefono.setText(miembro.getTelefono().get());
        } else {
            // Limpiar campos si no hay miembro seleccionado
            textFieldId.clear();
            textFieldNombre.clear();
            textFieldEmail.clear();
            textFieldTelefono.clear();
        }
    }

    /**
     * Edita el miembro seleccionado en la tabla con los datos ingresados en los campos de texto.
     */
    @FXML
    private void editarMiembro() {
        Miembro miembroSeleccionado = tablaMiembros.getSelectionModel().getSelectedItem();
        if (miembroSeleccionado != null) {
            miembroSeleccionado.getNombre().set(textFieldNombre.getText());
            miembroSeleccionado.getEmail().set(textFieldEmail.getText());
            miembroSeleccionado.getTelefono().set(textFieldTelefono.getText());
            tablaMiembros.refresh();
            try {
                CsvController.guardarMiembrosEnCSV(listaMiembros, RUTA_MIEMBROS);
            } catch (IOException e) {
                mostrarAlertaError("Error de Guardado", "No se pudieron guardar los miembros en el archivo CSV.");
            }
        } else {
            mostrarAlertaError("Sin selección", "Debe seleccionar un miembro para editar.");
        }
    }

    /**
     * Agrega un nuevo miembro a la lista y guarda los cambios en el archivo CSV.
     */
    @FXML
    private void handleAgregarMiembro() {
        String id = textFieldId.getText();
        String nombre = textFieldNombre.getText();
        String email = textFieldEmail.getText();
        String telefono = textFieldTelefono.getText();

        if (id.isEmpty() || nombre.isEmpty() || email.isEmpty() || telefono.isEmpty()) {
            mostrarAlertaError("Campos vacíos", "Debe completar todos los campos.");
            return;
        }

        Miembro nuevoMiembro = new Miembro(id, nombre, email, telefono);
        listaMiembros.add(nuevoMiembro);
        try {
            CsvController.guardarMiembrosEnCSV(listaMiembros, RUTA_MIEMBROS);
        } catch (IOException e) {
            mostrarAlertaError("Error de Guardado", "No se pudieron guardar los miembros en el archivo CSV.");
        }
        // Limpiar campos después de agregar el miembro
        textFieldId.clear();
        textFieldNombre.clear();
        textFieldEmail.clear();
        textFieldTelefono.clear();
    }

    /**
     * Muestra una alerta de error con el título y mensaje proporcionados.
     * 
     * @param titulo El título de la alerta.
     * @param mensaje El mensaje de la alerta.
     */
    private void mostrarAlertaError(String titulo, String mensaje) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }

    /**
     * Elimina el miembro seleccionado de la lista y guarda los cambios en el archivo CSV.
     */
    @FXML
    private void eliminarMiembro() {
        Miembro miembroSeleccionado = tablaMiembros.getSelectionModel().getSelectedItem();
        if (miembroSeleccionado != null) {
            listaMiembros.remove(miembroSeleccionado);
            try {
                CsvController.guardarMiembrosEnCSV(listaMiembros, RUTA_MIEMBROS);
            } catch (IOException e) {
                mostrarAlertaError("Error de Guardado", "No se pudieron guardar los miembros en el archivo CSV.");
            }
        } else {
            mostrarAlertaError("Sin selección", "Debe seleccionar un miembro para eliminar.");
        }
    }
}

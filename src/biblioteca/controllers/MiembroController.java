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

public class MiembroController {

    @FXML
    private TableView<Miembro> tablaMiembros;

    @FXML
    private TableColumn<Miembro, String> columnaID;

    @FXML
    private TableColumn<Miembro, String> columnaNombre;

    @FXML
    private TableColumn<Miembro, String> columnaEmail;

    @FXML
    private TableColumn<Miembro, String> columnaTelefono;

    @FXML
    private TextField textFieldId;

    @FXML
    private TextField textFieldNombre;

    @FXML
    private TextField textFieldEmail;

    @FXML
    private TextField textFieldTelefono;

    private ObservableList<Miembro> listaMiembros;
    private Sucursal sucursal;

    private static final String RUTA_MIEMBROS = "src/biblioteca/db/miembros.csv";
    
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

    private void mostrarAlertaError(String titulo, String mensaje) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }

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


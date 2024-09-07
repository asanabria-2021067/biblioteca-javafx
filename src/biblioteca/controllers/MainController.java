package biblioteca.controllers;

import biblioteca.models.Sucursal;
import java.io.IOException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.TabPane;

public class MainController {

    @FXML
    private TabPane mainTabPane;

    private Sucursal sucursalActual;

    public void initialize() {
        sucursalActual = new Sucursal("Sucursal Central", "Calle Principal 123");
         mainTabPane.getSelectionModel().selectedItemProperty().addListener((observable, oldTab, newTab) -> {
            if (newTab != null) {
                String tabText = newTab.getText();
                switch (tabText) {
                    case "Libros":
                        // Lógica para actualizar la vista de libros si es necesario
                        break;
                    case "Miembros":
                        // Lógica para actualizar la vista de miembros si es necesario
                        break;
                    case "Préstamos":
                        // Lógica para actualizar la vista de préstamos
                        PrestamoController prestamoController = (PrestamoController) getController("/biblioteca/views/PrestamoView.fxml");
                        if (prestamoController != null) {
                            prestamoController.cargarDatos();
                        }
                        break;
                    case "Estadísticas":
                        // Lógica para actualizar la vista de estadísticas si es necesario
                        break;
                }
            }
        });
    }

    // Métodos para cambiar entre pestañas
    public void mostrarVistaLibros() {
        mainTabPane.getSelectionModel().select(0);  // Asume que la primera pestaña es la de libros
    }

    public void mostrarVistaMiembros() {
        mainTabPane.getSelectionModel().select(1);  // Asume que la segunda pestaña es la de miembros
    }

    public void mostrarVistaPrestamos() {
        mainTabPane.getSelectionModel().select(2);  // Asume que la tercera pestaña es la de préstamos
    }

    public void mostrarVistaEstadisticas() {
        mainTabPane.getSelectionModel().select(3);  // Asume que la cuarta pestaña es la de estadísticas
    }

    // Getters y Setters
    public Sucursal getSucursalActual() {
        return sucursalActual;
    }
    private Object getController(String fxmlFile) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFile));
            Parent root = loader.load();
            return loader.getController();
        } catch (IOException e) {
            e.printStackTrace(); // Manejo de errores, puedes mostrar una alerta o loguear el error
            return null;
        }
    }
}

package biblioteca.controllers;

import biblioteca.models.Sucursal;
import java.io.IOException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.TabPane;

/**
 * Controlador principal para la aplicación de biblioteca. Maneja la navegación entre diferentes vistas a través de las pestañas del TabPane.
 * 
 * @author Angel Sanabria, Javier Alvarado
 * @version 1.0
 * @since 2024-09-04
 * @lastModified 2024-09-06
 */
public class MainController {

    @FXML
    private TabPane mainTabPane; // TabPane que contiene las diferentes vistas de la aplicación

    private Sucursal sucursalActual; // Sucursal actualmente seleccionada

    /**
     * Inicializa el controlador. Configura un listener para el cambio de pestañas.
     */
    @FXML
    public void initialize() {
        mainTabPane.getSelectionModel().selectedItemProperty().addListener((observable, oldTab, newTab) -> {
            if (newTab != null) {
                // Acción a realizar cuando se cambia de pestaña
            }
        });
    }

    /**
     * Muestra la vista de libros seleccionando la primera pestaña del TabPane.
     */
    public void mostrarVistaLibros() {
        mainTabPane.getSelectionModel().select(0);  // Asume que la primera pestaña es la de libros
    }

    /**
     * Muestra la vista de miembros seleccionando la segunda pestaña del TabPane.
     */
    public void mostrarVistaMiembros() {
        mainTabPane.getSelectionModel().select(1);  // Asume que la segunda pestaña es la de miembros
    }

    /**
     * Muestra la vista de préstamos seleccionando la tercera pestaña del TabPane.
     */
    public void mostrarVistaPrestamos() {
        mainTabPane.getSelectionModel().select(2);  // Asume que la tercera pestaña es la de préstamos
    }

    /**
     * Muestra la vista de estadísticas seleccionando la cuarta pestaña del TabPane.
     */
    public void mostrarVistaEstadisticas() {
        mainTabPane.getSelectionModel().select(3);  // Asume que la cuarta pestaña es la de estadísticas
    }

    /**
     * Obtiene la sucursal actualmente seleccionada.
     * 
     * @return La sucursal actualmente seleccionada.
     */
    public Sucursal getSucursalActual() {
        return sucursalActual;
    }

    /**
     * Carga un controlador de una vista FXML especificada.
     * 
     * @param fxmlFile El archivo FXML que contiene la vista.
     * @return El controlador de la vista cargada, o null si ocurre un error.
     */
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

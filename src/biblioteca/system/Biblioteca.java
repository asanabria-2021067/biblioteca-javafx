package biblioteca.system;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * La clase principal para el sistema de gestión de la biblioteca.
 * 
 * Esta clase extiende `Application` y es responsable de iniciar la aplicación JavaFX,
 * cargar el diseño raíz y mostrar la interfaz de usuario principal.
 * 
 * Autor: Angel Sanabria, Javier Alvarado
 * Fecha de creación: 06/09/2024
 * Fecha de última modificación: 06/09/2024
 */
public class Biblioteca extends Application {

    private Stage primaryStage;

    /**
     * Configura el escenario principal y carga el diseño raíz de la interfaz de usuario.
     * 
     * @param primaryStage El escenario principal de la aplicación.
     */
    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("Sistema de Biblioteca");

        initRootLayout();
    }

    /**
     * Inicializa el diseño raíz y carga el archivo FXML correspondiente.
     * Establece la escena y muestra el escenario principal.
     */
    public void initRootLayout() {
        try {
            FXMLLoader loader = new FXMLLoader();
            // Asegúrate de que la ruta sea correcta
            loader.setLocation(Biblioteca.class.getResource("../views/MainView.fxml"));
            AnchorPane rootLayout = loader.load();

            Scene scene = new Scene(rootLayout);
            primaryStage.setScene(scene);
            primaryStage.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Método principal que inicia la aplicación.
     * 
     * @param args Los argumentos de la línea de comandos.
     */
    public static void main(String[] args) {
        launch(args);
    }
}

package biblioteca.controllers;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.Map;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.PieChart;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;

/**
 * Controlador para la vista de estadísticas de la aplicación de biblioteca.
 * Maneja la visualización de gráficos y tablas que muestran estadísticas sobre
 * libros y préstamos.
 * 
 * @author Angel Sanabria, Javier Alvarado
 * @version 1.0
 * @since 2024-09-04
 * @lastModified 2024-09-06
 */
public class EstadisticasController {

    @FXML
    private PieChart pieChart; // Gráfico circular para mostrar estadísticas de géneros y duración de préstamos
    
    @FXML
    private VBox panelGraficas; // Contenedor para los gráficos
    
    @FXML
    private VBox panelTabla; // Contenedor para la tabla de estadísticas
    
    @FXML
    private BarChart<String, Number> barChartPrestamos; // Gráfico de barras para préstamos
    
    @FXML
    private TableView<Estadistica> tablaEstadisticas; // Tabla para mostrar estadísticas detalladas
    
    @FXML
    private TableColumn<Estadistica, String> columnaDescripcion; // Columna para descripciones en la tabla
    
    @FXML
    private TableColumn<Estadistica, String> columnaValor; // Columna para valores en la tabla

    // Mapas para almacenar datos sobre libros, géneros, títulos y préstamos
    private Map<String, String> generos = new HashMap<>();
    private Map<String, LocalDate> prestamos = new HashMap<>();
    private Map<String, LocalDate> libros = new HashMap<>();
    private Map<String, String> titulos = new HashMap<>();
    private Map<String, Long> prestamosDuracion = new HashMap<>();

    /**
     * Constructor que carga los datos iniciales.
     */
    public EstadisticasController() {
        cargarDatos();
    }

    /**
     * Inicializa el controlador. Configura las columnas de la tabla y carga los datos.
     */
    @FXML
    public void initialize() {
        columnaDescripcion.setCellValueFactory(new PropertyValueFactory<>("descripcion"));
        columnaValor.setCellValueFactory(new PropertyValueFactory<>("valor"));

        // Mostrar la tabla de estadísticas inicialmente
        cargarTablaEstadisticas();
        cargarDatos();
    }

    /**
     * Muestra el gráfico de géneros en el PieChart.
     * Este método cuenta la cantidad de libros por género y actualiza el PieChart.
     */
    @FXML
    public void mostrarGraficoGeneros() {
        cargarDatos();
        pieChart.setVisible(true);
        pieChart.getData().clear(); // Limpiar los datos anteriores

        // Contar la cantidad de libros por género
        Map<String, Integer> generoCount = new HashMap<>();
        for (String genero : generos.values()) {
            generoCount.put(genero, generoCount.getOrDefault(genero, 0) + 1);
        }

        // Preparar los datos para el PieChart
        ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList();
        for (Map.Entry<String, Integer> entry : generoCount.entrySet()) {
            pieChartData.add(new PieChart.Data(entry.getKey(), entry.getValue()));
        }

        pieChart.setData(pieChartData);
    }

    /**
     * Muestra el gráfico de duración de préstamos en el PieChart.
     * Este método utiliza la duración de los préstamos para actualizar el PieChart.
     */
    @FXML
    public void mostrarGraficoPrestamos() {
        cargarDatos();
        pieChart.setVisible(true);
        pieChart.getData().clear(); // Limpiar los datos anteriores

        // Preparar los datos para el PieChart con duración de préstamos
        ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList();
        for (Map.Entry<String, Long> entry : prestamosDuracion.entrySet()) {
            String isbn = entry.getKey();
            String titulo = titulos.get(isbn); // Obtener el título usando el ISBN
            Long dias = entry.getValue();
            pieChartData.add(new PieChart.Data(titulo + " - " + dias + " días", dias));
        }

        pieChart.setData(pieChartData);
    }

    /**
     * Muestra la tabla de estadísticas y oculta los gráficos.
     * Este método oculta el panel de gráficos y muestra el panel de la tabla.
     */
    @FXML
    public void mostrarTabla() {
        // Oculta el gráfico de barras y muestra la tabla
        panelGraficas.setVisible(false);
        panelTabla.setVisible(true);
    }

    /**
     * Carga los datos desde archivos CSV y los almacena en las estructuras de datos.
     * Este método lee los datos de libros y préstamos desde los archivos correspondientes
     * y actualiza los mapas internos.
     */
    public void cargarDatos() {
        // Cargar datos de libros desde archivo CSV
        try (BufferedReader br = new BufferedReader(new FileReader("src/biblioteca/db/libros.csv"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] datos = line.split(",");
                if (datos.length >= 5 && !"ISBN".equals(datos[0])) { // Verificar que hay suficientes columnas y no es el encabezado
                    libros.put(datos[0], LocalDate.now()); // Suponemos que los libros están disponibles
                    titulos.put(datos[0], datos[1]); // Guardar el título del libro
                    generos.put(datos[0], datos[4]);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Cargar datos de préstamos desde archivo CSV
        try (BufferedReader br = new BufferedReader(new FileReader("src/biblioteca/db/prestamos.csv"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] datos = line.split(",");
                if (datos.length >= 3 && !"ISBN".equals(datos[0])) { // Verificar que hay suficientes columnas y no es el encabezado
                    prestamos.put(datos[0], LocalDate.parse(datos[2]));
                    calcularDuracionPrestamo(datos[0], LocalDate.parse(datos[2]));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Calcula la duración del préstamo en días desde la fecha de préstamo hasta la fecha actual.
     * 
     * @param isbn ISBN del libro prestado.
     * @param fechaPrestamo Fecha en la que se realizó el préstamo.
     */
    private void calcularDuracionPrestamo(String isbn, LocalDate fechaPrestamo) {
        LocalDate fechaHoy = LocalDate.now();
        long diasPrestado = ChronoUnit.DAYS.between(fechaPrestamo, fechaHoy);
        prestamosDuracion.put(isbn, diasPrestado);
    }

    /**
     * Carga los datos en la tabla de estadísticas, mostrando la duración de los préstamos.
     * Este método calcula los días prestados para cada libro y actualiza la tabla con esta información.
     */
    public void cargarTablaEstadisticas() {
        cargarDatos();
        ObservableList<Estadistica> estadisticas = FXCollections.observableArrayList();

        // Calcular y agregar estadísticas sobre libros prestados y disponibles
        for (String isbn : prestamos.keySet()) {
            LocalDate fechaPrestamo = prestamos.get(isbn);
            LocalDate fechaHoy = LocalDate.now();

            long diasPrestado = ChronoUnit.DAYS.between(fechaPrestamo, fechaHoy);

            // Agregar a la tabla los días que lleva prestado
            estadisticas.add(new Estadistica("Libro ISBN " + isbn, "Días: " + diasPrestado));
        }
        tablaEstadisticas.setItems(estadisticas);
    }

    /**
     * Clase interna para representar una estadística con descripción y valor.
     */
    public static class Estadistica {

        private final String descripcion;
        private final String valor;

        /**
         * Crea una nueva instancia de Estadistica con la descripción y el valor proporcionados.
         * 
         * @param descripcion Descripción de la estadística.
         * @param valor Valor asociado con la estadística.
         */
        public Estadistica(String descripcion, String valor) {
            this.descripcion = descripcion;
            this.valor = valor;
        }

        /**
         * Obtiene la descripción de la estadística.
         * 
         * @return Descripción de la estadística.
         */
        public String getDescripcion() {
            return descripcion;
        }

        /**
         * Obtiene el valor de la estadística.
         * 
         * @return Valor de la estadística.
         */
        public String getValor() {
            return valor;
        }
    }
}

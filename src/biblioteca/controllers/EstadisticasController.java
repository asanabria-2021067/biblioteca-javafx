package biblioteca.controllers;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;

public class EstadisticasController {

    @FXML
    private PieChart pieChart;
    @FXML
    private VBox panelGraficas;
    @FXML
    private VBox panelTabla;
    @FXML
    private BarChart<String, Number> barChartPrestamos;
    @FXML
    private TableView<Estadistica> tablaEstadisticas;
    @FXML
    private TableColumn<Estadistica, String> columnaDescripcion;
    @FXML
    private TableColumn<Estadistica, String> columnaValor;
    private Map<String, String> generos = new HashMap<>();
    private Map<String, LocalDate> prestamos = new HashMap<>();
    private Map<String, LocalDate> libros = new HashMap<>();
    private Map<String, String> titulos = new HashMap<>();
    private Map<String, Long> prestamosDuracion = new HashMap<>();

    public EstadisticasController() {
        cargarDatos();
    }

    @FXML
    public void initialize() {
        columnaDescripcion.setCellValueFactory(new PropertyValueFactory<>("descripcion"));
        columnaValor.setCellValueFactory(new PropertyValueFactory<>("valor"));

        // Mostrar la tabla de estadísticas inicialmente
        cargarTablaEstadisticas();
        cargarDatos();
    }

    @FXML
    public void mostrarGraficoGeneros() {
        cargarDatos();
        pieChart.setVisible(true);
        pieChart.getData().clear(); // Limpiar los datos anteriores

        Map<String, Integer> generoCount = new HashMap<>();

        for (String genero : generos.values()) {
            generoCount.put(genero, generoCount.getOrDefault(genero, 0) + 1);
        }


        ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList();
        for (Map.Entry<String, Integer> entry : generoCount.entrySet()) {
            pieChartData.add(new PieChart.Data(entry.getKey(), entry.getValue()));
        }

        pieChart.setData(pieChartData);
    }

    // Mostrar gráfica de tiempo de préstamos (usando títulos)
    @FXML
    public void mostrarGraficoPrestamos() {
        cargarDatos();
        pieChart.setVisible(true);
        pieChart.getData().clear(); // Limpiar los datos anteriores

        ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList();

        for (Map.Entry<String, Long> entry : prestamosDuracion.entrySet()) {
            String isbn = entry.getKey();
            String titulo = titulos.get(isbn); // Obtener el título usando el ISBN
            Long dias = entry.getValue();
            pieChartData.add(new PieChart.Data(titulo + " - " + dias + " días", dias));
        }

        pieChart.setData(pieChartData);
    }

    @FXML
    public void mostrarTabla() {
        // Oculta el gráfico de barras y muestra la tabla
        panelGraficas.setVisible(false);
        panelTabla.setVisible(true);
    }

   public void cargarDatos() {
    // Cargar libros
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

    // Cargar prestamos
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

    private void calcularDuracionPrestamo(String isbn, LocalDate fechaPrestamo) {
        LocalDate fechaHoy = LocalDate.now();
        long diasPrestado = ChronoUnit.DAYS.between(fechaPrestamo, fechaHoy);
        prestamosDuracion.put(isbn, diasPrestado);
    }

    public void cargarTablaEstadisticas() {
        cargarDatos();
        ObservableList<Estadistica> estadisticas = FXCollections.observableArrayList();

        // Calcular y agregar estadísticas sobre libros prestados y disponibles
        for (String isbn : prestamos.keySet()) {
            LocalDate fechaPrestamo = prestamos.get(isbn);
            LocalDate fechaHoy = LocalDate.now();

            long diasPrestado = ChronoUnit.DAYS.between(fechaPrestamo, fechaHoy);

            // Agregar a la tabla los días que lleva prestado
            estadisticas.add(new Estadistica("Libro ISBN " + isbn, "Días prestado: " + diasPrestado));
        }
        cargarDatos();
        tablaEstadisticas.setItems(estadisticas);
    }

    public static class Estadistica {

        private final String descripcion;
        private final String valor;

        public Estadistica(String descripcion, String valor) {
            this.descripcion = descripcion;
            this.valor = valor;
        }

        public String getDescripcion() {
            return descripcion;
        }

        public String getValor() {
            return valor;
        }
    }
}

package biblioteca.controllers;

import java.io.*;
import java.util.*;
import biblioteca.models.*;
import java.time.LocalDate;

/**
 * Clase para manejar la lectura y escritura de archivos CSV relacionados con la biblioteca.
 * 
 * Esta clase proporciona métodos para guardar y cargar datos de libros, miembros, préstamos y sucursales en archivos CSV.
 * 
 * @author Angel Sanabria, Javier Alvarado
 * @version 1.0
 * @since 2024-09-04
 * @lastModified 2024-09-06
 */
public class CsvController {

    /**
     * Guarda una lista de libros en un archivo CSV.
     * 
     * @param libros Lista de objetos {@link Libro} que se van a guardar en el archivo.
     * @param archivo Ruta del archivo CSV donde se guardarán los datos.
     * @throws IOException Si ocurre un error al escribir en el archivo.
     */
    public static void guardarLibrosEnCSV(List<Libro> libros, String archivo) throws IOException {
        try (PrintWriter writer = new PrintWriter(new FileWriter(archivo))) {
            writer.println("ISBN,Titulo,Autor,Año de Publicación,Genero"); // Agregar encabezado
            for (Libro libro : libros) {
                writer.println(libro.getISBN().get() + ","
                        + libro.getTitulo().get() + ","
                        + libro.getAutor().get() + ","
                        + libro.getAnioPublicacion().get() + ","
                        + libro.getGenero().get());
            }
        } catch (IOException e) {
            System.out.println("Error al guardar libros: " + e.getMessage());
            throw e;
        }
    }

    /**
     * Carga una lista de libros desde un archivo CSV.
     * 
     * @param archivo Ruta del archivo CSV desde el cual se cargarán los datos.
     * @return Lista de objetos {@link Libro} cargados desde el archivo.
     * @throws IOException Si ocurre un error al leer el archivo.
     */
    public static List<Libro> cargarLibrosDesdeCSV(String archivo) throws IOException {
        List<Libro> libros = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(archivo))) {
            String linea;
            reader.readLine(); // Saltar la línea de encabezado
            while ((linea = reader.readLine()) != null) {
                String[] partes = linea.split(",");
                if (partes.length == 5) {
                    try {
                        // Convertir el año de publicación a entero
                        int anoPublicacion = Integer.parseInt(partes[3].trim());
                        Libro libro = new Libro(partes[0].trim(), partes[1].trim(), partes[2].trim(), anoPublicacion, partes[4].trim());
                        libros.add(libro);
                    } catch (NumberFormatException e) {
                        System.out.println("Error al convertir el año de publicación: " + partes[3]);
                    }
                } else {
                    System.out.println("Formato incorrecto en la línea: " + linea);
                }
            }
        } catch (IOException e) {
            System.out.println("Error al cargar libros: " + e.getMessage());
            throw e;
        }
        return libros;
    }

    /**
     * Guarda una lista de miembros en un archivo CSV.
     * 
     * @param miembros Lista de objetos {@link Miembro} que se van a guardar en el archivo.
     * @param archivo Ruta del archivo CSV donde se guardarán los datos.
     * @throws IOException Si ocurre un error al escribir en el archivo.
     */
    public static void guardarMiembrosEnCSV(List<Miembro> miembros, String archivo) throws IOException {
        try (PrintWriter writer = new PrintWriter(new FileWriter(archivo))) {
            writer.println("ID,Nombre,Email,Telefono"); // Agregar encabezado
            for (Miembro miembro : miembros) {
                writer.println(miembro.getId().get() + ","
                        + miembro.getNombre().get() + ","
                        + miembro.getEmail().get() + ","
                        + miembro.getTelefono().get());
            }
        } catch (IOException e) {
            System.out.println("Error al guardar miembros: " + e.getMessage());
            throw e;
        }
    }

    /**
     * Carga una lista de miembros desde un archivo CSV.
     * 
     * @param archivo Ruta del archivo CSV desde el cual se cargarán los datos.
     * @return Lista de objetos {@link Miembro} cargados desde el archivo.
     * @throws IOException Si ocurre un error al leer el archivo.
     */
    public static List<Miembro> cargarMiembrosDesdeCSV(String archivo) throws IOException {
        List<Miembro> miembros = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(archivo))) {
            String linea;
            reader.readLine(); // Saltar la línea de encabezado
            while ((linea = reader.readLine()) != null) {
                String[] partes = linea.split(",");
                if (partes.length == 4) {
                    Miembro miembro = new Miembro(partes[0].trim(), partes[1].trim(), partes[2].trim(), partes[3].trim());
                    miembros.add(miembro);
                } else {
                    System.out.println("Formato incorrecto en la línea: " + linea);
                }
            }
        } catch (IOException e) {
            System.out.println("Error al cargar miembros: " + e.getMessage());
            throw e;
        }
        return miembros;
    }

    /**
     * Guarda una lista de préstamos en un archivo CSV.
     * 
     * @param prestamos Lista de objetos {@link Prestamo} que se van a guardar en el archivo.
     * @param archivo Ruta del archivo CSV donde se guardarán los datos.
     * @throws IOException Si ocurre un error al escribir en el archivo.
     */
    public static void guardarPrestamosEnCSV(List<Prestamo> prestamos, String archivo) throws IOException {
        try (PrintWriter writer = new PrintWriter(new FileWriter(archivo))) {
            writer.println("ISBN,MiembroID,FechaPrestamo,FechaDevolucionEsperada,FechaDevolucionReal"); // Agregar encabezado
            for (Prestamo prestamo : prestamos) {
                writer.println(prestamo.getLibro().getISBN().get() + ","
                        + prestamo.getMiembro().getId().get() + ","
                        + prestamo.getFechaPrestamo().get() + ","
                        + prestamo.getFechaDevolucionEsperada().get() + ","
                        + (prestamo.getFechaDevolucionReal().get() != null ? prestamo.getFechaDevolucionReal().get() : ""));
            }
        } catch (IOException e) {
            System.out.println("Error al guardar préstamos: " + e.getMessage());
            throw e;
        }
    }

    /**
     * Carga una lista de préstamos desde un archivo CSV.
     * 
     * @param archivo Ruta del archivo CSV desde el cual se cargarán los datos.
     * @param libros Lista de objetos {@link Libro} disponibles para asociar con los préstamos.
     * @param miembros Lista de objetos {@link Miembro} disponibles para asociar con los préstamos.
     * @return Lista de objetos {@link Prestamo} cargados desde el archivo.
     * @throws IOException Si ocurre un error al leer el archivo.
     */
    public static List<Prestamo> cargarPrestamosDesdeCSV(String archivo, List<Libro> libros, List<Miembro> miembros) throws IOException {
        List<Prestamo> prestamos = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(archivo))) {
            String linea;
            reader.readLine(); // Saltar la línea de encabezado
            while ((linea = reader.readLine()) != null) {
                String[] partes = linea.split(",");
                if (partes != null) {
                    // Buscar libro y miembro
                    Libro libro = buscarLibroPorISBN(partes[0].trim(), libros);
                    Miembro miembro = buscarMiembroPorID(partes[1].trim(), miembros);
                    if (libro != null && miembro != null) {
                        LocalDate fechaPrestamo = parseFecha(partes[2].trim());
                        LocalDate fechaDevolucionEsperada = parseFecha(partes[3].trim());
                        LocalDate fechaDevolucionReal = parseFecha(partes.length > 4 ? partes[4].trim() : "");

                        // Crear el préstamo
                        Prestamo prestamo = new Prestamo(libro, miembro, fechaPrestamo, fechaDevolucionEsperada, fechaDevolucionReal);
                        prestamos.add(prestamo);
                    } else {
                        System.out.println("Libro o miembro no encontrado para la línea: " + linea);
                    }
                } else {
                    System.out.println("Formato incorrecto en la línea: " + linea);
                }
            }
        } catch (IOException e) {
            System.out.println("Error al cargar préstamos: " + e.getMessage());
            throw e;
        }
        return prestamos;
    }

    /**
     * Guarda una lista de sucursales en un archivo CSV.
     * 
     * @param sucursales Lista de objetos {@link Sucursal} que se van a guardar en el archivo.
     * @param archivo Ruta del archivo CSV donde se guardarán los datos.
     * @throws IOException Si ocurre un error al escribir en el archivo.
     */
    public static void guardarSucursalesEnCSV(List<Sucursal> sucursales, String archivo) throws IOException {
        try (PrintWriter writer = new PrintWriter(new FileWriter(archivo))) {
            writer.println("Nombre,Direccion"); // Agregar encabezado
            for (Sucursal sucursal : sucursales) {
                writer.println(sucursal.getNombre().get() + "," + sucursal.getDireccion().get());
            }
        } catch (IOException e) {
            System.out.println("Error al guardar sucursales: " + e.getMessage());
            throw e;
        }
    }

    /**
     * Carga una lista de sucursales desde un archivo CSV.
     * 
     * @param archivo Ruta del archivo CSV desde el cual se cargarán los datos.
     * @return Lista de objetos {@link Sucursal} cargados desde el archivo.
     * @throws IOException Si ocurre un error al leer el archivo.
     */
    public static List<Sucursal> cargarSucursalesDesdeCSV(String archivo) throws IOException {
        List<Sucursal> sucursales = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(archivo))) {
            String linea;
            reader.readLine(); // Saltar la línea de encabezado
            while ((linea = reader.readLine()) != null) {
                String[] partes = linea.split(",");
                if (partes.length == 2) {
                    Sucursal sucursal = new Sucursal(partes[0].trim(), partes[1].trim());
                    sucursales.add(sucursal);
                } else {
                    System.out.println("Formato incorrecto en la línea: " + linea);
                }
            }
        } catch (IOException e) {
            System.out.println("Error al cargar sucursales: " + e.getMessage());
            throw e;
        }
        return sucursales;
    }

    /**
     * Analiza una cadena de texto y la convierte en un objeto {@link LocalDate}.
     * 
     * @param fecha Cadena de texto que representa una fecha en formato "yyyy-MM-dd".
     * @return Objeto {@link LocalDate} correspondiente a la fecha proporcionada.
     */
    private static LocalDate parseFecha(String fecha) {
        try {
            return LocalDate.parse(fecha);
        } catch (Exception e) {
            System.out.println("Error al analizar la fecha: " + fecha);
            return null;
        }
    }

    /**
     * Busca un libro por su ISBN en una lista de libros.
     * 
     * @param isbn ISBN del libro que se busca.
     * @param libros Lista de objetos {@link Libro} donde se realizará la búsqueda.
     * @return Objeto {@link Libro} correspondiente al ISBN proporcionado, o null si no se encuentra.
     */
    private static Libro buscarLibroPorISBN(String isbn, List<Libro> libros) {
        for (Libro libro : libros) {
            if (libro.getISBN().get().equals(isbn)) {
                return libro;
            }
        }
        return null;
    }

    /**
     * Busca un miembro por su ID en una lista de miembros.
     * 
     * @param id ID del miembro que se busca.
     * @param miembros Lista de objetos {@link Miembro} donde se realizará la búsqueda.
     * @return Objeto {@link Miembro} correspondiente al ID proporcionado, o null si no se encuentra.
     */
    private static Miembro buscarMiembroPorID(String id, List<Miembro> miembros) {
        for (Miembro miembro : miembros) {
            if (miembro.getId().get().equals(id)) {
                return miembro;
            }
        }
        return null;
    }
}

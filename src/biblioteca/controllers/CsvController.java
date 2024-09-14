package biblioteca.controllers;

import java.io.*;
import java.util.*;
import biblioteca.models.*;
import java.time.LocalDate;

/**
 * Clase para manejar la lectura y escritura de archivos CSV.
 *
 * @author angel
 */
public class CsvController {

    // Guardar libros en un archivo CSV
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

    // Cargar libros desde un archivo CSV
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

    // Guardar miembros en un archivo CSV
    public static void guardarMiembrosEnCSV(List<Miembro> miembros, String archivo) throws IOException {
        try (PrintWriter writer = new PrintWriter(new FileWriter(archivo))) {
            for (Miembro miembro : miembros) {
                writer.println(miembro.getId().get() + "," + miembro.getNombre().get() + ","
                        + miembro.getEmail().get() + "," + miembro.getTelefono().get());
            }
        } catch (IOException e) {
            System.out.println("Error al guardar miembros: " + e.getMessage());
            throw e;
        }
    }

    // Cargar miembros desde un archivo CSV
    public static List<Miembro> cargarMiembrosDesdeCSV(String archivo) throws IOException {
        List<Miembro> miembros = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(archivo))) {
            String linea;
            while ((linea = reader.readLine()) != null) {
                String[] partes = linea.split(",");
                if (partes.length == 4) {
                    Miembro miembro = new Miembro(partes[0], partes[1], partes[2], partes[3]);
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

    // Guardar préstamos en un archivo CSV
    public static void guardarPrestamosEnCSV(List<Prestamo> prestamos, String archivo) throws IOException {
        try (PrintWriter writer = new PrintWriter(new FileWriter(archivo))) {
            writer.println("ISBN,MiembroID,FechaPrestamo,FechaDevolucionEsperada,FechaDevolucionReal"); // Encabezado
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

// Parsear fecha con manejo de valores nulos
    private static LocalDate parseFecha(String fecha) {
        if (fecha == null || fecha.trim().isEmpty()) {
            return null;
        }
        try {
            return LocalDate.parse(fecha);
        } catch (Exception e) {
            System.out.println("Error al parsear la fecha: " + fecha);
            return null;
        }
    }

// Buscar libro por ISBN en la lista de libros
    private static Libro buscarLibroPorISBN(String isbn, List<Libro> libros) {
        for (Libro libro : libros) {
            if (libro.getISBN().get().equals(isbn)) {
                return libro;
            }
        }
        return null;
    }

// Buscar miembro por ID en la lista de miembros
    private static Miembro buscarMiembroPorID(String id, List<Miembro> miembros) {
        for (Miembro miembro : miembros) {
            if (miembro.getId().get().equals(id)) {
                return miembro;
            }
        }
        return null;
    }
// Guardar sucursales en un archivo CSV (solo nombre y dirección)
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

    // Cargar sucursales desde un archivo CSV
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

}

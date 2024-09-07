package biblioteca.models;

import java.util.ArrayList;
import java.util.List;

/**
 * Representa una sucursal de la biblioteca.
 * @author angel
 */
public class Sucursal {
    private String nombre;
    private String direccion;
    private List<Libro> libros;
    private List<Prestamo> prestamos;
    private List<Miembro> miembros;

    /**
     * Constructor de la sucursal con todos los parámetros.
     * @param nombre Nombre de la sucursal.
     * @param direccion Dirección de la sucursal.
     * @param libros Lista de libros disponibles en la sucursal.
     * @param prestamos Lista de préstamos registrados en la sucursal.
     */
    public Sucursal(String nombre, String direccion, List<Libro> libros, List<Prestamo> prestamos, List<Miembro> miembros) {
        this.nombre = nombre;
        this.direccion = direccion;
        this.libros = libros;
        this.prestamos = prestamos;
        this.miembros = miembros;
    }

    public Sucursal(String nombre, String direccion) {
        this.nombre = nombre;
        this.direccion = direccion;
        this.libros = new ArrayList<>();
        this.prestamos = new ArrayList<>();
        this.miembros = new ArrayList<>(); // Inicializa la lista de miembros
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public List<Libro> getLibros() {
        return libros;
    }

    public void setLibros(List<Libro> libros) {
        this.libros = libros;
    }

    public List<Prestamo> getPrestamos() {
        return prestamos;
    }

    public void setPrestamos(List<Prestamo> prestamos) {
        this.prestamos = prestamos;
    }
    
        public List<Miembro> getMiembros() {
        return miembros;
    }

    public void setMiembros(List<Miembro> miembros) {
        this.miembros = miembros;
    }

    public void agregarMiembro(Miembro nuevoMiembro) {
        miembros.add(nuevoMiembro);
    }

    @Override
    public String toString() {
        return "Sucursal{" + "nombre=" + nombre + ", direccion=" + direccion + ", libros=" + libros + ", prestamos=" + prestamos + '}';
    }

    /**
     * Agrega un nuevo libro a la lista de libros.
     * @param nuevoLibro Libro a agregar.
     */
    public void agregarLibro(Libro nuevoLibro) {
        libros.add(nuevoLibro);
    }

    /**
     * Registra un nuevo préstamo en la sucursal.
     * @param prestamo El préstamo a registrar.
     */
    public void registrarPrestamo(Prestamo prestamo) {
        prestamos.add(prestamo);
        prestamo.getLibro().setDisponible(false); // Marca el libro como no disponible
    }
}

package biblioteca.models;

import java.util.ArrayList;
import java.util.List;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * Representa una sucursal de la biblioteca.
 * 
 * Esta clase gestiona la información de una sucursal de biblioteca, incluyendo el nombre, la dirección,
 * y las listas de libros, préstamos y miembros asociados a la sucursal.
 * 
 * Autor: Angel Sanabria, Javier Alvarado
 * Fecha de creación: 06/09/2024
 * Fecha de última modificación: 06/09/2024
 */
public class Sucursal {
    private StringProperty nombre;
    private StringProperty direccion;
    private List<Libro> libros;
    private List<Prestamo> prestamos;
    private List<Miembro> miembros;

    /**
     * Constructor de la sucursal con todos los parámetros.
     * 
     * @param nombre       Nombre de la sucursal.
     * @param direccion    Dirección de la sucursal.
     * @param libros       Lista de libros disponibles en la sucursal.
     * @param prestamos    Lista de préstamos registrados en la sucursal.
     * @param miembros     Lista de miembros registrados en la sucursal.
     */
    public Sucursal(String nombre, String direccion, List<Libro> libros, List<Prestamo> prestamos, List<Miembro> miembros) {
        this.nombre = new SimpleStringProperty(nombre);
        this.direccion = new SimpleStringProperty(direccion);
        this.libros = libros;
        this.prestamos = prestamos;
        this.miembros = miembros;
    }

    /**
     * Constructor de la sucursal con nombre y dirección. Inicializa listas vacías para libros, préstamos y miembros.
     * 
     * @param nombre    Nombre de la sucursal.
     * @param direccion Dirección de la sucursal.
     */
    public Sucursal(String nombre, String direccion) {
        this.nombre = new SimpleStringProperty(nombre);
        this.direccion = new SimpleStringProperty(direccion);
        this.libros = new ArrayList<>();
        this.prestamos = new ArrayList<>();
        this.miembros = new ArrayList<>();
    }

    /**
     * Obtiene el nombre de la sucursal.
     * 
     * @return El nombre de la sucursal.
     */
    public StringProperty getNombre() {
        return nombre;
    }

    /**
     * Establece el nombre de la sucursal.
     * 
     * @param nombre El nuevo nombre de la sucursal.
     */
    public void setNombre(StringProperty nombre) {
        this.nombre = nombre;
    }

    /**
     * Obtiene la dirección de la sucursal.
     * 
     * @return La dirección de la sucursal.
     */
    public StringProperty getDireccion() {
        return direccion;
    }

    /**
     * Establece la dirección de la sucursal.
     * 
     * @param direccion La nueva dirección de la sucursal.
     */
    public void setDireccion(StringProperty direccion) {
        this.direccion = direccion;
    }

    /**
     * Obtiene la lista de libros disponibles en la sucursal.
     * 
     * @return La lista de libros.
     */
    public List<Libro> getLibros() {
        return libros;
    }

    /**
     * Establece la lista de libros disponibles en la sucursal.
     * 
     * @param libros La nueva lista de libros.
     */
    public void setLibros(List<Libro> libros) {
        this.libros = libros;
    }

    /**
     * Obtiene la lista de préstamos registrados en la sucursal.
     * 
     * @return La lista de préstamos.
     */
    public List<Prestamo> getPrestamos() {
        return prestamos;
    }

    /**
     * Establece la lista de préstamos registrados en la sucursal.
     * 
     * @param prestamos La nueva lista de préstamos.
     */
    public void setPrestamos(List<Prestamo> prestamos) {
        this.prestamos = prestamos;
    }
    
    /**
     * Obtiene la lista de miembros registrados en la sucursal.
     * 
     * @return La lista de miembros.
     */
    public List<Miembro> getMiembros() {
        return miembros;
    }

    /**
     * Establece la lista de miembros registrados en la sucursal.
     * 
     * @param miembros La nueva lista de miembros.
     */
    public void setMiembros(List<Miembro> miembros) {
        this.miembros = miembros;
    }

    /**
     * Agrega un nuevo miembro a la lista de miembros de la sucursal.
     * 
     * @param nuevoMiembro El nuevo miembro a agregar.
     */
    public void agregarMiembro(Miembro nuevoMiembro) {
        miembros.add(nuevoMiembro);
    }

    /**
     * Agrega un nuevo libro a la lista de libros de la sucursal.
     * 
     * @param nuevoLibro El libro a agregar.
     */
    public void agregarLibro(Libro nuevoLibro) {
        libros.add(nuevoLibro);
    }

    /**
     * Registra un nuevo préstamo en la sucursal y marca el libro como no disponible.
     * 
     * @param prestamo El préstamo a registrar.
     */
    public void registrarPrestamo(Prestamo prestamo) {
        prestamos.add(prestamo);
        prestamo.getLibro().setDisponible(false); // Marca el libro como no disponible
    }

    /**
     * Devuelve una representación en cadena de la sucursal.
     * 
     * @return Una cadena con la representación de la sucursal.
     */
    @Override
    public String toString() {
        return "Sucursal{" +
                "nombre=" + nombre.get() +
                ", direccion=" + direccion.get() +
                ", libros=" + libros +
                ", prestamos=" + prestamos +
                ", miembros=" + miembros +
                '}';
    }
}

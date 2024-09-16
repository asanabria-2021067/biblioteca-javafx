package biblioteca.models;

import java.time.LocalDate;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;

/**
 * Representa un préstamo de un libro a un miembro de la biblioteca.
 * 
 * Esta clase gestiona la información relacionada con el libro prestado, el miembro que recibe el préstamo,
 * y las fechas asociadas al préstamo, incluyendo la fecha real de devolución si está disponible.
 * 
 * Autor: Angel Sanabria, Javier Alvarado
 * Fecha de creación: 06/09/2024
 * Fecha de última modificación: 06/09/2024
 */
public class Prestamo {

    private Libro libro;
    private Miembro miembro;
    private ObjectProperty<LocalDate> fechaPrestamo;
    private ObjectProperty<LocalDate> fechaDevolucionEsperada;
    private ObjectProperty<LocalDate> fechaDevolucionReal;

    /**
     * Crea una nueva instancia de Prestamo con los detalles especificados.
     * 
     * @param libro                   El libro que se está prestando.
     * @param miembro                 El miembro que recibe el préstamo.
     * @param fechaPrestamo           La fecha en la que se realizó el préstamo.
     * @param fechaDevolucionEsperada La fecha en la que se espera que el libro sea devuelto.
     * @param fechaDevolucionReal     La fecha real en la que el libro fue devuelto (puede ser null si no se ha devuelto aún).
     */
    public Prestamo(Libro libro, Miembro miembro, LocalDate fechaPrestamo, LocalDate fechaDevolucionEsperada, LocalDate fechaDevolucionReal) {
        this.libro = libro;
        this.miembro = miembro;
        this.fechaPrestamo = new SimpleObjectProperty<>(fechaPrestamo);
        this.fechaDevolucionEsperada = new SimpleObjectProperty<>(fechaDevolucionEsperada);
        this.fechaDevolucionReal = (fechaDevolucionReal != null) 
            ? new SimpleObjectProperty<>(fechaDevolucionReal) 
            : new SimpleObjectProperty<>();
    }

    /**
     * Crea una nueva instancia de Prestamo con los detalles especificados, sin fecha de devolución real.
     * 
     * @param libro                   El libro que se está prestando.
     * @param miembro                 El miembro que recibe el préstamo.
     * @param fechaPrestamo           La fecha en la que se realizó el préstamo.
     * @param fechaDevolucionEsperada La fecha en la que se espera que el libro sea devuelto.
     */
    public Prestamo(Libro libro, Miembro miembro, LocalDate fechaPrestamo, LocalDate fechaDevolucionEsperada) {
        this(libro, miembro, fechaPrestamo, fechaDevolucionEsperada, null);
    }

    /**
     * Obtiene el libro que se está prestando.
     * 
     * @return El libro prestado.
     */
    public Libro getLibro() {
        return libro;
    }

    /**
     * Establece el libro que se está prestando.
     * 
     * @param libro El libro a establecer.
     */
    public void setLibro(Libro libro) {
        this.libro = libro;
    }

    /**
     * Obtiene el miembro que recibe el préstamo.
     * 
     * @return El miembro que recibe el préstamo.
     */
    public Miembro getMiembro() {
        return miembro;
    }

    /**
     * Establece el miembro que recibe el préstamo.
     * 
     * @param miembro El miembro a establecer.
     */
    public void setMiembro(Miembro miembro) {
        this.miembro = miembro;
    }

    /**
     * Obtiene la propiedad de la fecha en la que se realizó el préstamo.
     * 
     * @return La propiedad de la fecha de préstamo.
     */
    public ObjectProperty<LocalDate> getFechaPrestamo() {
        return fechaPrestamo;
    }

    /**
     * Establece la propiedad de la fecha en la que se realizó el préstamo.
     * 
     * @param fechaPrestamo La nueva propiedad de la fecha de préstamo.
     */
    public void setFechaPrestamo(ObjectProperty<LocalDate> fechaPrestamo) {
        this.fechaPrestamo = fechaPrestamo;
    }

    /**
     * Obtiene la propiedad de la fecha en la que se espera que el libro sea devuelto.
     * 
     * @return La propiedad de la fecha de devolución esperada.
     */
    public ObjectProperty<LocalDate> getFechaDevolucionEsperada() {
        return fechaDevolucionEsperada;
    }

    /**
     * Establece la propiedad de la fecha en la que se espera que el libro sea devuelto.
     * 
     * @param fechaDevolucionEsperada La nueva propiedad de la fecha de devolución esperada.
     */
    public void setFechaDevolucionEsperada(ObjectProperty<LocalDate> fechaDevolucionEsperada) {
        this.fechaDevolucionEsperada = fechaDevolucionEsperada;
    }

    /**
     * Obtiene la propiedad de la fecha real en la que el libro fue devuelto.
     * 
     * @return La propiedad de la fecha de devolución real.
     */
    public ObjectProperty<LocalDate> getFechaDevolucionReal() {
        return fechaDevolucionReal;
    }

    /**
     * Establece la fecha real en la que el libro fue devuelto.
     * 
     * @param fechaDevolucionReal La nueva fecha de devolución real.
     */
    public void setFechaDevolucionReal(LocalDate fechaDevolucionReal) {
        this.fechaDevolucionReal.set(fechaDevolucionReal);
    }

    /**
     * Devuelve una representación en cadena del préstamo.
     * 
     * @return Una cadena con la representación del préstamo.
     */
    @Override
    public String toString() {
        return "Prestamo{" +
                "libro=" + libro +
                ", miembro=" + miembro +
                ", fechaPrestamo=" + fechaPrestamo.get() +
                ", fechaDevolucionEsperada=" + fechaDevolucionEsperada.get() +
                ", fechaDevolucionReal=" + fechaDevolucionReal.get() +
                '}';
    }
}

package biblioteca.models;

import java.time.LocalDate;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;

/**
 *
 * @author angel
 */
public class Prestamo {

    private Libro libro;
    private Miembro miembro;
    private ObjectProperty<LocalDate> fechaPrestamo;
    private ObjectProperty<LocalDate> fechaDevolucionEsperada;
    private ObjectProperty<LocalDate> fechaDevolucionReal;

    public Prestamo(Libro libro, Miembro miembro, LocalDate fechaPrestamo, LocalDate fechaDevolucionEsperada, LocalDate fechaDevolucionReal) {
        this.libro = libro;
        this.miembro = miembro;
        this.fechaPrestamo = new SimpleObjectProperty<>(fechaPrestamo);
        this.fechaDevolucionEsperada = new SimpleObjectProperty<>(fechaDevolucionEsperada);
        this.fechaDevolucionReal = (fechaDevolucionReal != null) 
            ? new SimpleObjectProperty<>(fechaDevolucionReal) 
            : new SimpleObjectProperty<>();
    }

    public Prestamo(Libro libro, Miembro miembro, LocalDate fechaPrestamo, LocalDate fechaDevolucionEsperada) {
        this(libro, miembro, fechaPrestamo, fechaDevolucionEsperada, null);
    }

    public Libro getLibro() {
        return libro;
    }

    public void setLibro(Libro libro) {
        this.libro = libro;
    }

    public Miembro getMiembro() {
        return miembro;
    }

    public void setMiembro(Miembro miembro) {
        this.miembro = miembro;
    }

    public ObjectProperty<LocalDate> getFechaPrestamo() {
        return fechaPrestamo;
    }

    public void setFechaPrestamo(ObjectProperty<LocalDate> fechaPrestamo) {
        this.fechaPrestamo = fechaPrestamo;
    }

    public ObjectProperty<LocalDate> getFechaDevolucionEsperada() {
        return fechaDevolucionEsperada;
    }

    public void setFechaDevolucionEsperada(ObjectProperty<LocalDate> fechaDevolucionEsperada) {
        this.fechaDevolucionEsperada = fechaDevolucionEsperada;
    }

    public ObjectProperty<LocalDate> getFechaDevolucionReal() {
        return fechaDevolucionReal;
    }

    public void setFechaDevolucionReal(LocalDate fechaDevolucionReal) {
        this.fechaDevolucionReal.set(fechaDevolucionReal);
    }

    @Override
    public String toString() {
        return "Prestamo{" + "libro=" + libro + ", miembro=" + miembro + ", fechaPrestamo=" + fechaPrestamo.get() + ", fechaDevolucionEsperada=" + fechaDevolucionEsperada.get() + ", fechaDevolucionReal=" + fechaDevolucionReal.get() + '}';
    }
}

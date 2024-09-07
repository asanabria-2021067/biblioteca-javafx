package biblioteca.models;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author angel
 */
public class Libro {

    private StringProperty ISBN;
    private StringProperty titulo;
    private StringProperty autor;
    private IntegerProperty anioPublicacion;
    private StringProperty genero;
    private boolean disponible;

    public Libro(String ISBN, String titulo, String autor, int anioPublicacion, String genero) {
        this.ISBN = new SimpleStringProperty(ISBN);
        this.titulo = new SimpleStringProperty(titulo);
        this.autor = new SimpleStringProperty(autor);
        this.anioPublicacion = new SimpleIntegerProperty(anioPublicacion);
        this.genero = new SimpleStringProperty(genero);
        this.disponible = true;
    }

    public StringProperty ISBNProperty() {
        return ISBN;
    }

    public StringProperty tituloProperty() {
        return titulo;
    }

    public StringProperty autorProperty() {
        return autor;
    }

    public IntegerProperty anioPublicacionProperty() {
        return anioPublicacion;
    }

    public StringProperty generoProperty() {
        return genero;
    }

    public StringProperty getISBN() {
        return ISBN;
    }

    public void setISBN(StringProperty ISBN) {
        this.ISBN = ISBN;
    }

    public StringProperty getTitulo() {
        return titulo;
    }

    public void setTitulo(StringProperty titulo) {
        this.titulo = titulo;
    }

    public StringProperty getAutor() {
        return autor;
    }

    public void setAutor(StringProperty autor) {
        this.autor = autor;
    }

    public IntegerProperty getAnioPublicacion() {
        return anioPublicacion;
    }

    public void setAnioPublicacion(IntegerProperty anioPublicacion) {
        this.anioPublicacion = anioPublicacion;
    }

    public StringProperty getGenero() {
        return genero;
    }

    public void setGenero(StringProperty genero) {
        this.genero = genero;
    }

    public boolean isDisponible() {
        return disponible;
    }

    public void setDisponible(boolean disponible) {
        this.disponible = disponible;
    }

    @Override
    public String toString() {
        return "Libro{" + "ISBN=" + ISBN + ", titulo=" + titulo + ", autor=" + autor + ", anioPublicacion=" + anioPublicacion + ", genero=" + genero + ", disponible=" + disponible + '}';
    }
}

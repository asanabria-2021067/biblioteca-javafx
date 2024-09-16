package biblioteca.models;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * Representa un libro en la biblioteca con propiedades como ISBN, título, autor, 
 * año de publicación, género y disponibilidad.
 * 
 * Esta clase utiliza propiedades de JavaFX para la integración con la interfaz gráfica.
 * 
 * Autor: Angel Sanabria y Javier Alvarado
 * Fecha de creación: 04/09/2024
 * Fecha de última modificación: 06/09/2024
 */
public class Libro {

    private StringProperty ISBN;
    private StringProperty titulo;
    private StringProperty autor;
    private IntegerProperty anioPublicacion;
    private StringProperty genero;
    private boolean disponible;

    /**
     * Crea una nueva instancia de Libro con los detalles especificados.
     * 
     * @param ISBN          El ISBN del libro.
     * @param titulo        El título del libro.
     * @param autor         El autor del libro.
     * @param anioPublicacion El año de publicación del libro.
     * @param genero        El género del libro.
     */
    public Libro(String ISBN, String titulo, String autor, int anioPublicacion, String genero) {
        this.ISBN = new SimpleStringProperty(ISBN);
        this.titulo = new SimpleStringProperty(titulo);
        this.autor = new SimpleStringProperty(autor);
        this.anioPublicacion = new SimpleIntegerProperty(anioPublicacion);
        this.genero = new SimpleStringProperty(genero);
        this.disponible = true;
    }

    /**
     * Obtiene la propiedad de ISBN del libro.
     * 
     * @return La propiedad de ISBN.
     */
    public StringProperty ISBNProperty() {
        return ISBN;
    }

    /**
     * Obtiene la propiedad de título del libro.
     * 
     * @return La propiedad de título.
     */
    public StringProperty tituloProperty() {
        return titulo;
    }

    /**
     * Obtiene la propiedad de autor del libro.
     * 
     * @return La propiedad de autor.
     */
    public StringProperty autorProperty() {
        return autor;
    }

    /**
     * Obtiene la propiedad de año de publicación del libro.
     * 
     * @return La propiedad de año de publicación.
     */
    public IntegerProperty anioPublicacionProperty() {
        return anioPublicacion;
    }

    /**
     * Obtiene la propiedad de género del libro.
     * 
     * @return La propiedad de género.
     */
    public StringProperty generoProperty() {
        return genero;
    }

    /**
     * Obtiene el ISBN del libro.
     * 
     * @return El ISBN del libro.
     */
    public StringProperty getISBN() {
        return ISBN;
    }

    /**
     * Establece el ISBN del libro.
     * 
     * @param ISBN La nueva propiedad de ISBN.
     */
    public void setISBN(StringProperty ISBN) {
        this.ISBN = ISBN;
    }

    /**
     * Obtiene el título del libro.
     * 
     * @return El título del libro.
     */
    public StringProperty getTitulo() {
        return titulo;
    }

    /**
     * Establece el título del libro.
     * 
     * @param titulo La nueva propiedad de título.
     */
    public void setTitulo(StringProperty titulo) {
        this.titulo = titulo;
    }

    /**
     * Obtiene el autor del libro.
     * 
     * @return El autor del libro.
     */
    public StringProperty getAutor() {
        return autor;
    }

    /**
     * Establece el autor del libro.
     * 
     * @param autor La nueva propiedad de autor.
     */
    public void setAutor(StringProperty autor) {
        this.autor = autor;
    }

    /**
     * Obtiene el año de publicación del libro.
     * 
     * @return El año de publicación del libro.
     */
    public IntegerProperty getAnioPublicacion() {
        return anioPublicacion;
    }

    /**
     * Establece el año de publicación del libro.
     * 
     * @param anioPublicacion La nueva propiedad de año de publicación.
     */
    public void setAnioPublicacion(IntegerProperty anioPublicacion) {
        this.anioPublicacion = anioPublicacion;
    }

    /**
     * Obtiene el género del libro.
     * 
     * @return El género del libro.
     */
    public StringProperty getGenero() {
        return genero;
    }

    /**
     * Establece el género del libro.
     * 
     * @param genero La nueva propiedad de género.
     */
    public void setGenero(StringProperty genero) {
        this.genero = genero;
    }

    /**
     * Obtiene la disponibilidad del libro.
     * 
     * @return Verdadero si el libro está disponible, falso en caso contrario.
     */
    public boolean isDisponible() {
        return disponible;
    }

    /**
     * Establece la disponibilidad del libro.
     * 
     * @param disponible La nueva disponibilidad del libro.
     */
    public void setDisponible(boolean disponible) {
        this.disponible = disponible;
    }

    /**
     * Devuelve una representación en cadena del libro.
     * 
     * @return Una cadena con la representación del libro.
     */
    @Override
    public String toString() {
        return "Libro{" +
                "ISBN=" + ISBN.get() +
                ", titulo=" + titulo.get() +
                ", autor=" + autor.get() +
                ", anioPublicacion=" + anioPublicacion.get() +
                ", genero=" + genero.get() +
                ", disponible=" + disponible +
                '}';
    }
}

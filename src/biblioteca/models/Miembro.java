package biblioteca.models;

import javafx.beans.property.StringProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 * Representa un miembro de la biblioteca con propiedades como ID, nombre, 
 * correo electrónico y teléfono.
 * 
 * Esta clase utiliza propiedades de JavaFX para la integración con la interfaz gráfica.
 * 
 * Autor: Angel Sanabria, Javier Alvarado
 * Fecha de creación: 06/09/2024
 * Fecha de última modificación: 06/09/2024
 */
public class Miembro {

    private StringProperty id;
    private StringProperty nombre;
    private StringProperty email;
    private StringProperty telefono; 

    /**
     * Crea una nueva instancia de Miembro con los detalles especificados.
     * 
     * @param id       El ID del miembro.
     * @param nombre   El nombre del miembro.
     * @param email    El correo electrónico del miembro.
     * @param telefono El teléfono del miembro.
     */
    public Miembro(String id, String nombre, String email, String telefono) {
        this.id = new SimpleStringProperty(id);
        this.nombre = new SimpleStringProperty(nombre);
        this.email = new SimpleStringProperty(email);
        this.telefono = new SimpleStringProperty(telefono);
    }

    /**
     * Obtiene la propiedad de ID del miembro.
     * 
     * @return La propiedad de ID.
     */
    public StringProperty getId() {
        return id;
    }

    /**
     * Establece el ID del miembro.
     * 
     * @param id La nueva propiedad de ID.
     */
    public void setId(StringProperty id) {
        this.id = id;
    }

    /**
     * Obtiene la propiedad de nombre del miembro.
     * 
     * @return La propiedad de nombre.
     */
    public StringProperty getNombre() {
        return nombre;
    }

    /**
     * Establece el nombre del miembro.
     * 
     * @param nombre La nueva propiedad de nombre.
     */
    public void setNombre(StringProperty nombre) {
        this.nombre = nombre;
    }

    /**
     * Obtiene la propiedad de correo electrónico del miembro.
     * 
     * @return La propiedad de correo electrónico.
     */
    public StringProperty getEmail() {
        return email;
    }

    /**
     * Establece el correo electrónico del miembro.
     * 
     * @param email La nueva propiedad de correo electrónico.
     */
    public void setEmail(StringProperty email) {
        this.email = email;
    }

    /**
     * Obtiene la propiedad de teléfono del miembro.
     * 
     * @return La propiedad de teléfono.
     */
    public StringProperty getTelefono() {
        return telefono;
    }

    /**
     * Establece el teléfono del miembro.
     * 
     * @param telefono La nueva propiedad de teléfono.
     */
    public void setTelefono(StringProperty telefono) {
        this.telefono = telefono;
    }

    /**
     * Devuelve una representación en cadena del miembro.
     * 
     * @return Una cadena con la representación del miembro.
     */
    @Override
    public String toString() {
        return "Miembro{" +
                "id=" + id.get() +
                ", nombre=" + nombre.get() +
                ", email=" + email.get() +
                ", telefono=" + telefono.get() +
                '}';
    }

    
}

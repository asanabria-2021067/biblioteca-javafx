package biblioteca.models;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
/**
 *
 * @author angel
 */
public class Miembro {
    private StringProperty id;
    private StringProperty nombre;
    private StringProperty email;
    private StringProperty telefono; 

    public Miembro(String id, String nombre, String email, String telefono) {
        this.id = new SimpleStringProperty(id);
        this.nombre = new SimpleStringProperty(nombre);
        this.email = new SimpleStringProperty(email);
        this.telefono = new SimpleStringProperty(telefono);
    }

    public StringProperty getId() {
        return id;
    }

    public void setId(StringProperty id) {
        this.id = id;
    }

    public StringProperty getNombre() {
        return nombre;
    }

    public void setNombre(StringProperty nombre) {
        this.nombre = nombre;
    }

    public StringProperty getEmail() {
        return email;
    }

    public void setEmail(StringProperty email) {
        this.email = email;
    }

    public StringProperty getTelefono() {
        return telefono;
    }

    public void setTelefono(StringProperty telefono) {
        this.telefono = telefono;
    }

    @Override
    public String toString() {
        return "Miembro{" + "id=" + id + ", nombre=" + nombre + ", email=" + email + ", telefono=" + telefono + '}';
    }

    
}

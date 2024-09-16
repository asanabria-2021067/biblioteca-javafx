package biblioteca.models;

/**
 * Representa una estadística con una descripción y un valor asociado.
 * 
 * Esta clase se utiliza para almacenar y gestionar estadísticas en el sistema.
 * 
 * Autor: Angel Sanabria y Javier Alvarado
 * Fecha de creación: 04/09/2024
 * Fecha de última modificación: 06/09/2024
 */
public class Estadistica {

    private String descripcion;
    private String valor;

    /**
     * Crea una nueva instancia de Estadistica con la descripción y el valor especificados.
     * 
     * @param descripcion La descripción de la estadística.
     * @param valor       El valor de la estadística.
     */
    public Estadistica(String descripcion, String valor) {
        this.descripcion = descripcion;
        this.valor = valor;
    }

    /**
     * Obtiene la descripción de la estadística.
     * 
     * @return La descripción de la estadística.
     */
    public String getDescripcion() {
        return descripcion;
    }

    /**
     * Establece la descripción de la estadística.
     * 
     * @param descripcion La nueva descripción de la estadística.
     */
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    /**
     * Obtiene el valor de la estadística.
     * 
     * @return El valor de la estadística.
     */
    public String getValor() {
        return valor;
    }

    /**
     * Establece el valor de la estadística.
     * 
     * @param valor El nuevo valor de la estadística.
     */
    public void setValor(String valor) {
        this.valor = valor;
    }
}


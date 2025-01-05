package inventoryChef;

import datos.Archivo;
import java.util.List;

/**
 * Clase que representa un alimento en el sistema de inventario.
 */
public class Alimento {
    private String nombre;
    private double precio;
    private String categoria;
    private int cantidad;

    /**
     * Constructor por defecto para la clase Alimento.
     * Inicializa un objeto Alimento sin valores específicos.
     */
    public Alimento() {
    }

    /**
     * Constructor parametrizado para inicializar un alimento con datos específicos.
     *
     * @param nombre    Nombre del alimento.
     * @param precio    Precio del alimento.
     * @param categoria Categoría del alimento (e.g., "Frutas", "Carnes").
     * @param cantidad  Cantidad disponible del alimento en el inventario.
     */
    public Alimento(String nombre, double precio, String categoria, int cantidad) {
        this.nombre = nombre;
        this.precio = precio;
        this.categoria = categoria;
        this.cantidad = cantidad;
    }

    /**
     * Obtiene el nombre del alimento.
     *
     * @return Nombre del alimento.
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Obtiene el precio del alimento.
     *
     * @return Precio del alimento.
     */
    public double getPrecio() {
        return precio;
    }

    /**
     * Establece un nuevo precio para el alimento.
     *
     * @param precio Nuevo precio del alimento.
     */
    public void setPrecio(double precio) {
        this.precio = precio;
    }

    /**
     * Obtiene la cantidad disponible del alimento en el inventario.
     *
     * @return Cantidad disponible del alimento.
     */
    public int getCantidad() {
        return cantidad;
    }

    /**
     * Establece una nueva cantidad para el alimento en el inventario.
     *
     * @param cantidad Nueva cantidad del alimento.
     */
    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }
}

package inventoryChef;

import datos.Archivo;
import java.util.List;

public class Alimento {
    private String nombre;
    private double precio;
    private String categoria;
    private int cantidad;

    public Alimento() {
    }

    public Alimento(String nombre, double precio, String categoria, int cantidad) {
        this.nombre = nombre;
        this.precio = precio;
        this.categoria = categoria;
        this.cantidad = cantidad;
    }

    public String getNombre() {
        return nombre;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public int getCantidad() {
        return cantidad;
    }
}

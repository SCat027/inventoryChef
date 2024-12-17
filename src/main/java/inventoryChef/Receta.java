package inventoryChef;

import java.util.List;

public class Receta {
    private String nombre;
    private List<Ingrediente> ingredientes; // Lista de ingredientes con cantidades
    private String instrucciones;

    public Receta(String nombre, List<Ingrediente> ingredientes, String instrucciones) {
        this.nombre = nombre;
        this.ingredientes = ingredientes;
        this.instrucciones = instrucciones;
    }

    public String getNombre() {
        return nombre;
    }

    public List<Ingrediente> getIngredientes() {
        return ingredientes;
    }

    public String getInstrucciones() {
        return instrucciones;
    }

    public void setInstrucciones(String instrucciones) {
        this.instrucciones = instrucciones;
    }
}

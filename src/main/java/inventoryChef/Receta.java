package inventoryChef;

import java.util.List;

/**
 * Clase Receta que representa una receta de cocina.
 * Contiene un nombre, una lista de ingredientes y las instrucciones para su preparación.
 */
public class Receta {
    private String nombre;
    private List<Ingrediente> ingredientes;
    private String instrucciones;

    /**
     * Constructor por defecto.
     */
    public Receta() {
    }

    /**
     * Constructor parametrizado para inicializar una receta con un nombre, ingredientes e instrucciones.
     *
     * @param nombre        Nombre de la receta.
     * @param ingredientes  Lista de ingredientes necesarios para la receta.
     * @param instrucciones Instrucciones para preparar la receta.
     */
    public Receta(String nombre, List<Ingrediente> ingredientes, String instrucciones) {
        this.nombre = nombre;
        this.ingredientes = ingredientes;
        this.instrucciones = instrucciones;
    }

    /**
     * Obtiene el nombre de la receta.
     *
     * @return Nombre de la receta.
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Obtiene la lista de ingredientes necesarios para la receta.
     *
     * @return Lista de objetos Ingrediente.
     */
    public List<Ingrediente> getIngredientes() {
        return ingredientes;
    }

    /**
     * Obtiene las instrucciones para preparar la receta.
     *
     * @return Instrucciones de la receta.
     */
    public String getInstrucciones() {
        return instrucciones;
    }

    /**
     * Establece nuevas instrucciones para preparar la receta.
     *
     * @param instrucciones Nuevas instrucciones de preparación.
     */
    public void setInstrucciones(String instrucciones) {
        this.instrucciones = instrucciones;
    }
}

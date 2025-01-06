package inventoryChef;

/**
 * Clase Ingrediente que representa un ingrediente usado en recetas
 * Cada ingrediente está compuesto por un alimento y una cantidad específica
 */
public class Ingrediente {
    private Alimento alimento;
    private int cantidad;

    /**
     * Constructor por defecto
     */
    public Ingrediente() {
    }

    /**
     * Constructor parametrizado para inicializar un ingrediente con un alimento y una cantidad
     *
     * @param alimento Objeto Alimento que representa el alimento asociado al ingrediente
     * @param cantidad Cantidad del alimento requerida como ingrediente
     */
    public Ingrediente(Alimento alimento, int cantidad) {
        this.alimento = alimento;
        this.cantidad = cantidad;
    }

    /**
     * Obtiene el alimento asociado a este ingrediente
     *
     * @return Objeto Alimento asociado
     */
    public Alimento getAlimento() {
        return alimento;
    }

    /**
     * Establece el alimento asociado a este ingrediente
     *
     * @param alimento Objeto Alimento que será asociado
     */
    public void setAlimento(Alimento alimento) {
        this.alimento = alimento;
    }

    /**
     * Obtiene la cantidad del alimento requerida como ingrediente
     *
     * @return Cantidad del alimento
     */
    public int getCantidad() {
        return cantidad;
    }

    /**
     * Establece la cantidad del alimento requerida como ingrediente
     *
     * @param cantidad Nueva cantidad del alimento
     */
    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }
}

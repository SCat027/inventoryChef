package inventoryChef;

public class Ingrediente {
    private Alimento alimento;
    private int cantidad;

    public Ingrediente() {
    }

    public Ingrediente(Alimento alimento, int cantidad) {
        this.alimento = alimento;
        this.cantidad = cantidad;
    }

    public Alimento getAlimento() {
        return alimento;
    }

    public void setAlimento(Alimento alimento) {
        this.alimento = alimento;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }
}

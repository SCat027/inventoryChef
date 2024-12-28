package inventoryChef;

import datos.Archivo;
import java.util.List;

public class Reponedor extends Usuario {
    public Reponedor() {
        super(); // Llama al constructor por defecto de Usuario
        this.rol = "Reponedor";
    }
    public Reponedor(String nombre, String correo, int edad, String id, String contrasena) { super(nombre, correo, edad, id, contrasena);super.rol = "Reponedor"; }


    public void consultarAlmacen() {
        List<Alimento> almacen = cargarAlmacen();
        if (almacen == null || almacen.isEmpty()) {
            System.out.println("No hay productos en el almacén.");
            return; }
        System.out.println("Productos en almacén:");
        almacen.forEach(a -> System.out.println(a.getNombre() + " - $" + a.getPrecio())); }



    public String añadirProducto(Alimento producto) {
        List<Alimento> almacen = cargarAlmacen();
        if (almacen == null) return "No se pudo cargar el almacén.";
        if (productoExiste(almacen, producto.getNombre())) return "El producto ya existe.";
        almacen.add(producto);
        guardarAlmacen(almacen);
        return "Producto añadido correctamente.";}



    public boolean eliminarProducto(String nombreProducto) {
        List<Alimento> almacen = cargarAlmacen();
        if (almacen == null || almacen.isEmpty()) throw new IllegalStateException("No hay productos para eliminar.");
        if (!almacen.removeIf(a -> a.getNombre().equalsIgnoreCase(nombreProducto))) {
            System.out.println("El producto \"" + nombreProducto + "\" no existe.");
            return false; }
        guardarAlmacen(almacen);
        System.out.println("Producto \"" + nombreProducto + "\" eliminado correctamente.");
        return true; }



    public boolean editarProductoPrecio(String nombre, double nuevoPrecio) {
        List<Alimento> almacen = cargarAlmacen();
        if (almacen == null) throw new IllegalStateException("No se pudo cargar el almacén.");
        Alimento producto = buscarProducto(almacen, nombre);
        if (producto == null) {
            System.out.println("El producto \"" + nombre + "\" no existe.");
            return false;
        }
        producto.setPrecio(nuevoPrecio);
        guardarAlmacen(almacen);
        System.out.println("Precio del producto \"" + nombre + "\" editado correctamente a $" + nuevoPrecio);
        return true;
    }

    public boolean editarProductoCantidad(String nombre, int nuevaCantidad) {
        List<Alimento> almacen = cargarAlmacen();
        if (almacen == null) throw new IllegalStateException("No se pudo cargar el almacén.");
        Alimento producto = buscarProducto(almacen, nombre);
        if (producto == null) {
            System.out.println("El producto \"" + nombre + "\" no existe.");
            return false;
        }
        producto.setCantidad(nuevaCantidad);
        guardarAlmacen(almacen);
        System.out.println("Cantidad del producto \"" + nombre + "\" editada correctamente a " + nuevaCantidad);
        return true;
    }

    // ---------------- Métodos Auxiliares ----------------

    private List<Alimento> cargarAlmacen() {
        return Archivo.cargarAlimentos();
    }

    private void guardarAlmacen(List<Alimento> almacen) {
        Archivo.guardarAlimentos(almacen);
    }

    private boolean productoExiste(List<Alimento> almacen, String nombre) {
        return almacen.stream().anyMatch(a -> a.getNombre().equalsIgnoreCase(nombre));
    }

    private Alimento buscarProducto(List<Alimento> almacen, String nombre) {
        return almacen.stream()
                .filter(a -> a.getNombre().equalsIgnoreCase(nombre))
                .findFirst()
                .orElse(null);
    }
}

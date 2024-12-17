package inventoryChef;

import datos.Archivo;
import java.util.List;

public class Reponedor extends Usuario {

    public Reponedor(String nombre, String correo, int edad) {
        super(nombre, correo, edad);
    }

    public void consultarAlmacen() {
        List<Alimento> almacen = cargarAlmacen();
        if (almacen != null && !almacen.isEmpty()) {
            System.out.println("Productos en almacén:");
            almacen.forEach(a -> System.out.println(a.getNombre() + " - $" + a.getPrecio()));
        } else {
            System.out.println("No hay productos en el almacén.");
        }
    }

    public boolean añadirProducto(Alimento producto) {
        List<Alimento> almacen = cargarAlmacen();
        if (productoExiste(almacen, producto.getNombre())) {
            System.out.println("El producto \"" + producto.getNombre() + "\" ya existe.");
            return false;
        }
        almacen.add(producto);
        guardarAlmacen(almacen);
        System.out.println("Producto \"" + producto.getNombre() + "\" añadido correctamente.");
        return true;
    }

    public boolean eliminarProducto(String nombreProducto) {
        List<Alimento> almacen = cargarAlmacen();
        if (almacen == null || almacen.isEmpty()) {
            System.out.println("No hay productos para eliminar.");
            return false;
        }

        boolean productoEliminado = almacen.removeIf(a -> a.getNombre().equalsIgnoreCase(nombreProducto));
        if (productoEliminado) {
            guardarAlmacen(almacen);
            System.out.println("Producto \"" + nombreProducto + "\" eliminado correctamente.");
            return true;
        } else {
            System.out.println("El producto \"" + nombreProducto + "\" no existe.");
            return false;
        }
    }

    public boolean editarProductoPrecio(String nombre, double nuevoPrecio) {
        List<Alimento> almacen = cargarAlmacen();
        Alimento producto = buscarProducto(almacen, nombre);

        if (producto != null) {
            producto.setPrecio(nuevoPrecio);
            guardarAlmacen(almacen);
            System.out.println("Precio del producto \"" + nombre + "\" editado correctamente a $" + nuevoPrecio);
            return true;
        } else {
            System.out.println("El producto \"" + nombre + "\" no existe.");
            return false;
        }
    }

    public boolean editarProductoCantidad(String nombre, int nuevaCantidad) {
        List<Alimento> almacen = cargarAlmacen();
        Alimento producto = buscarProducto(almacen, nombre);

        if (producto != null) {
            producto.setCantidad(nuevaCantidad);
            guardarAlmacen(almacen);
            System.out.println("Cantidad del producto \"" + nombre + "\" editada correctamente a " + nuevaCantidad);
            return true;
        } else {
            System.out.println("El producto \"" + nombre + "\" no existe.");
            return false;
        }
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

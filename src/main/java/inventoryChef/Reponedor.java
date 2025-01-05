package inventoryChef;

import datos.Archivo;
import java.util.List;

/**
 * Clase Reponedor que extiende de Usuario.
 * Representa un usuario con rol de Reponedor que gestiona el almacén de alimentos.
 */
public class Reponedor extends Usuario {

    /**
     * Constructor por defecto que asigna el rol "Reponedor".
     */
    public Reponedor() {
        super();
        this.rol = "Reponedor";
    }

    /**
     * Constructor parametrizado para inicializar un reponedor con sus atributos básicos.
     *
     * @param nombre     Nombre del reponedor.
     * @param correo     Correo del reponedor.
     * @param edad       Edad del reponedor.
     * @param id         ID del reponedor.
     * @param contrasena Contraseña del reponedor.
     */
    public Reponedor(String nombre, String correo, int edad, String id, String contrasena) {
        super(nombre, correo, edad, id, contrasena);
        super.rol = "Reponedor";
    }

    /**
     * Consulta y muestra todos los productos en el almacén.
     */
    public void consultarAlmacen() {
        List<Alimento> almacen = cargarAlmacen();
        if (almacen == null || almacen.isEmpty()) {
            System.out.println("No hay productos en el almacén.");
            return;
        }
        System.out.println("Productos en almacén:");
        almacen.forEach(a -> System.out.println(a.getNombre() + " - $" + a.getPrecio()));
    }

    /**
     * Añade un nuevo producto al almacén.
     *
     * @param producto Objeto Alimento a añadir.
     * @return Mensaje indicando el resultado de la operación.
     */
    public String añadirProducto(Alimento producto) {
        List<Alimento> almacen = cargarAlmacen();
        if (almacen == null) return "No se pudo cargar el almacén.";
        if (productoExiste(almacen, producto.getNombre())) return "El producto ya existe.";
        almacen.add(producto);
        guardarAlmacen(almacen);
        return "Producto añadido correctamente.";
    }

    /**
     * Elimina un producto del almacén por su nombre.
     *
     * @param nombreProducto Nombre del producto a eliminar.
     * @return true si el producto fue eliminado, false en caso contrario.
     */
    public static boolean eliminarProducto(String nombreProducto) {
        List<Alimento> almacen = cargarAlmacen();
        if (almacen == null || almacen.isEmpty())
            throw new IllegalStateException("No hay productos para eliminar.");
        if (!almacen.removeIf(a -> a.getNombre().equalsIgnoreCase(nombreProducto))) {
            System.out.println("El producto \"" + nombreProducto + "\" no existe.");
            return false;
        }
        guardarAlmacen(almacen);
        System.out.println("Producto \"" + nombreProducto + "\" eliminado correctamente.");
        return true;
    }

    /**
     * Edita el precio de un producto en el almacén.
     *
     * @param nombre     Nombre del producto a editar.
     * @param nuevoPrecio Nuevo precio del producto.
     * @return true si la edición fue exitosa, false en caso contrario.
     */
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

    /**
     * Edita la cantidad de un producto en el almacén.
     *
     * @param nombre        Nombre del producto a editar.
     * @param nuevaCantidad Nueva cantidad del producto.
     * @return true si la edición fue exitosa, false en caso contrario.
     */
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

    /**
     * Carga la lista de alimentos desde el archivo de almacenamiento.
     *
     * @return Lista de alimentos en el almacén.
     */
    private static List<Alimento> cargarAlmacen() {
        return Archivo.cargarAlimentos();
    }

    /**
     * Guarda la lista de alimentos en el archivo de almacenamiento.
     *
     * @param almacen Lista de alimentos a guardar.
     */
    private static void guardarAlmacen(List<Alimento> almacen) {
        Archivo.guardarAlimentos(almacen);
    }

    /**
     * Verifica si un producto ya existe en el almacén.
     *
     * @param almacen Lista de alimentos en el almacén.
     * @param nombre  Nombre del producto a verificar.
     * @return true si el producto existe, false en caso contrario.
     */
    private boolean productoExiste(List<Alimento> almacen, String nombre) {
        return almacen.stream().anyMatch(a -> a.getNombre().equalsIgnoreCase(nombre));
    }

    /**
     * Busca un producto en el almacén por su nombre.
     *
     * @param almacen Lista de alimentos en el almacén.
     * @param nombre  Nombre del producto a buscar.
     * @return Objeto Alimento si se encuentra, null en caso contrario.
     */
    private Alimento buscarProducto(List<Alimento> almacen, String nombre) {
        return almacen.stream()
                .filter(a -> a.getNombre().equalsIgnoreCase(nombre))
                .findFirst()
                .orElse(null);
    }
}

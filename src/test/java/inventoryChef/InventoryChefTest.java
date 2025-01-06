package inventoryChef;

import datos.Archivo;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Clase de prueba unitaria para probar las funcionalidades de la clase InventoryChef.
 * Esta clase realiza pruebas de los métodos de creación, eliminación y gestión de usuarios, recetas y productos.
 */
public class InventoryChefTest {

    // Datos predeterminados de los archivos
    private static String USUARIOS_JSON = """
    [
        {"nombre":"Eduardo","correo":"eduardo@gmail.com","edad":21,"id":"1001","contrasena":"eduardocrack12","rol":"Admin"},
        {"nombre":"pedrito","correo":"pedrito@gmail.com","edad":24,"id":"3001","contrasena":"pedritopro14","rol":"Reponedor"},
        {"nombre":"jorge","correo":"jorge@gmail.com","edad":78,"id":"1002","contrasena":"jorge78crack","rol":"Admin"},
        {"nombre":"Pepe","correo":"pepetroll777@gmail.com","edad":34,"id":"2001","contrasena":"lol321","rol":"Chef"}
    ]
    """;

    private static String ALMACEN_JSON = """
    [
        {"nombre":"Platano","precio":550.0,"cantidad":990}
    ]
    """;

    private static String RECETAS_JSON = """
    [
        {"nombre":"platano frito","ingredientes":[{"alimento":{"nombre":"Platano","precio":550.0,"cantidad":30},"cantidad":10}],"instrucciones":"agarras un platano y lo fries y te lo comes"}
    ]
    """;

    /**
     * Metodo que se ejecuta después de cada prueba para limpiar los archivos generados.
     * Los archivos `usuarios.json`, `almacen.json` y `recetas.json` se eliminan y luego se vuelven a crear con los datos predeterminados.
     */
    @AfterEach
    void limpiarArchivos() {
        // Eliminar archivos generados durante la prueba
        new File("usuarios.json").delete();
        new File("almacen.json").delete();
        new File("recetas.json").delete();

        // Crear el archivo usuarios.json con los datos predefinidos
        try (FileWriter writer = new FileWriter("usuarios.json")) {
            writer.write(USUARIOS_JSON);
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Crear el archivo almacen.json con los datos predeterminados
        try (FileWriter writer = new FileWriter("almacen.json")) {
            writer.write(ALMACEN_JSON);
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Crear el archivo recetas.json con los datos predeterminados
        try (FileWriter writer = new FileWriter("recetas.json")) {
            writer.write(RECETAS_JSON);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Metodo que se ejecuta antes de cada prueba para limpiar y configurar el estado inicial.
     * Inicializa los archivos con listas vacías para los usuarios, alimentos y recetas.
     */
    @BeforeEach
    void setUp() {
        Archivo.guardarUsuarios(new ArrayList<>());
        Archivo.guardarAlimentos(new ArrayList<>());
        Archivo.guardarRecetas(new ArrayList<>());
    }

    /**
     * Prueba para verificar la creación y consulta de usuarios.
     * Se prueba la funcionalidad de los métodos:
     * - Admin.crearUsuario()
     * - Usuario.getRol()
     */
    @Test
    void testCrearYConsultarUsuario() {
        // Se hace el Admin
        Admin admin = new Admin("ElAdmin", "admin@test.com", 30, "7897", "waho12");

        // Creamos el usuario
        admin.crearUsuario("Chefsito", "chef@test.com", 25, "0027", "wija092", "Chef");

        List<Usuario> usuarios = Archivo.cargarUsuarios();

        // Se comprueba que existe un usuario en el archivo json
        assertEquals(1, usuarios.size());

        // Lectura
        assertEquals("Chef", usuarios.get(0).getRol());
    }

    /**
     * Prueba para verificar la eliminación de un usuario.
     * Se prueba la funcionalidad de los métodos:
     * - admin.crearUsuario()
     * - admin.eliminarUsuario()
     */
    @Test
    void testEliminarUsuario() {
        // Se hace el Admin
        Admin admin = new Admin("ElAdmin", "admin@test.com", 30, "7897", "waho12");

        // Creamos el usuario
        admin.crearUsuario("Chefsito", "chef@test.com", 25, "0027", "wija092", "Chef");
        admin.eliminarUsuario("Chefsito");
        List<Usuario> usuarios = Archivo.cargarUsuarios();

        // Se verifica que el usuario ya no existe
        assertEquals(0, usuarios.size());
    }

    /**
     * Prueba para verificar la creación de una receta.
     * Se prueba la funcionalidad de los métodos:
     * - Archivo.guardarAlimentos()
     * - chef.crearReceta()
     */
    @Test
    void testCrearReceta() {
        // Se crea el chef
        Chef chef = new Chef("ChefUser", "chef@test.com", 25, "chef1", "password");

        // Se crea el alimento (Cabe recordar que los alimentos son los existentes en bodega y los ingredientes parte de las recetas)
        Alimento tomate = new Alimento("Tomate", 0.5, "Vegetal", 10);

        // Se guarda en alimentos la lista con "tomate"
        Archivo.guardarAlimentos(List.of(tomate));

        // Se crea un ingrediente tomate, de cantidad 5
        Ingrediente ingredienteTomate = new Ingrediente(tomate, 5);

        // Se crea una receta, en sus ingredientes tiene el objeto de ingredienteTomate, el cual tiene el objeto de alimento tomate.
        Receta receta = new Receta("Ensalada", List.of(ingredienteTomate), "Mezclar los ingredientes.");
        chef.crearReceta(receta);

        // Cargamos las recetas
        List<Receta> recetas = Archivo.cargarRecetas();

        // Vemos que coincida con la que creamos anteriormente
        assertEquals(1, recetas.size()); // Tamaño mayor a 0
        assertEquals("Ensalada", recetas.get(0).getNombre()); // Que la receta tenga su nombre de "Ensalada"
    }

    /**
     * Prueba para verificar la acción de hacer una receta.
     * Se prueba la funcionalidad de los métodos:
     * - Archivo.guardarAlimentos()
     * - Alimento.getCantidad()
     * - chef.haceReceta()
     */
    @Test
    void testHacerReceta() {
        Chef chef = new Chef("ChefUser", "chef@test.com", 25, "chef1", "password");

        // Se crea el tomate (Cantidad en el almacen)
        Alimento tomate = new Alimento("Tomate", 0.5, "Vegetal", 10);
        Archivo.guardarAlimentos(List.of(tomate));

        // Se crea el ingrediente (Cantidad necesaria para la receta)
        Ingrediente ingrediente = new Ingrediente(tomate, 5);
        Receta receta = new Receta("Ensalada", List.of(ingrediente), "Mezclar los ingredientes.");
        chef.crearReceta(receta);

        chef.haceReceta("Ensalada");

        List<Alimento> almacen = Archivo.cargarAlimentos();

        // Como tenemos 10 tomates y usamos 5, el objeto de alimento tomate deberia de tener una cantidad de 5
        assertEquals(5, almacen.get(0).getCantidad());
    }

    /**
     * Prueba para verificar la adición de un producto al almacén.
     * Se prueba la funcionalidad de los métodos:
     * - reponedor.añadirProducto()
     */
    @Test
    void testAñadirProductoAlmacen() {
        Reponedor reponedor = new Reponedor("Reponedor", "reponedor@test.com", 30, "rep1", "password");
        Alimento producto = new Alimento("Lechuga", 1.0, "Vegetal", 20);

        String resultado = reponedor.añadirProducto(producto);
        assertEquals("Producto añadido correctamente.", resultado);

        List<Alimento> almacen = Archivo.cargarAlimentos();
        assertEquals(1, almacen.size());
        assertEquals("Lechuga", almacen.get(0).getNombre());
    }

    /**
     * Prueba para verificar la eliminación de un producto existente en el almacén.
     * Se prueba la funcionalidad de los métodos:
     * - reponedor.añadirProducto()
     * - reponedor.eliminarProducto()
     */
    @Test
    void testEliminarProductoExistente() {
        List<Alimento> alimentosIniciales = new ArrayList<>();
        alimentosIniciales.add(new Alimento("Manzana", 1.5, "Fruta", 50));
        alimentosIniciales.add(new Alimento("Pan", 0.8, "Panadería", 100));
        alimentosIniciales.add(new Alimento("Tomate", 1.2, "Verdura", 30));
        Archivo.guardarAlimentos(alimentosIniciales);

        // Crear Reponedor
        Reponedor reponedor = new Reponedor("Carlos", "carlos@example.com", 28, "R001", "contrasena123");

        // Eliminar el producto "Tomate" utilizando el metodo del Reponedor
        boolean resultado = reponedor.eliminarProducto("Tomate");

        // Verificar que el producto fue eliminado
        assertTrue(resultado, "El producto debería haberse eliminado correctamente");

        // Cargar los alimentos desde el archivo para verificar que "Tomate" ha sido eliminado
        List<Alimento> alimentosCargados = Archivo.cargarAlimentos();
        assertEquals(2, alimentosCargados.size(), "El número de productos en el almacén debería ser 2.");
        assertFalse(alimentosCargados.stream().anyMatch(a -> a.getNombre().equalsIgnoreCase("Tomate")), "El producto \"Tomate\" debería haber sido eliminado.");
    }

    /**
     * Prueba para verificar la eliminación de un producto inexistente en el almacén.
     * Se prueba la funcionalidad de los métodos:
     * - reponedor.añadirProducto()
     * - reponedor.eliminarProducto()
     */
    @Test
    void testEliminarProductoInexistente() {
        List<Alimento> alimentosIniciales = new ArrayList<>();
        alimentosIniciales.add(new Alimento("Manzana", 1.5, "Fruta", 50));
        alimentosIniciales.add(new Alimento("Pan", 0.8, "Panadería", 100));
        alimentosIniciales.add(new Alimento("Tomate", 1.2, "Verdura", 30));
        Archivo.guardarAlimentos(alimentosIniciales);

        Reponedor reponedor = new Reponedor("Carlos", "carlos@cuak.com", 28, "45745", "contrasenawa123");

        // Intentar eliminar un producto que no existe
        boolean resultado = reponedor.eliminarProducto("Cebolla");

        // Verificar que el producto no fue eliminado
        assertFalse(resultado, "El producto no debería haberse eliminado porque no existe");

        // Cargar los alimentos desde el archivo para verificar que el inventario no cambió
        List<Alimento> alimentosCargados = Archivo.cargarAlimentos();
        assertEquals(3, alimentosCargados.size(), "El número de productos en el almacén debería seguir siendo 3");
    }

    /**
     * Prueba para verificar la eliminación de un producto cuando el almacén está vacío.
     * Se prueba la funcionalidad de los métodos:
     * - reponedor.añadirProducto()
     * - reponedor.eliminarProducto()
     */
    @Test
    void testEliminarProductoDeAlmacenVacio() {
        // Eliminar todos los productos para dejar el almacén vacío
        Archivo.guardarAlimentos(new ArrayList<>());

        // Intentar eliminar un producto que no está
        Exception exception = assertThrows(IllegalStateException.class, () -> {
            Reponedor.eliminarProducto("Tomate");
        });

        // Verificar que se lanza una excepción porque no hay productos para eliminar
        assertEquals("No hay productos para eliminar.", exception.getMessage());
    }
}

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

public class InventoryChefTest {

    // Los datos predeterminados de usuarios, esto se hace para poder modificar los archivos a gusto durante las pruebas
    private static String USUARIOS_JSON = """
    [
        {"nombre":"Eduardo","correo":"eduardo@gmail.com","edad":21,"id":"1001","contrasena":"eduardocrack12","rol":"Admin"},
        {"nombre":"pedrito","correo":"pedrito@gmail.com","edad":24,"id":"3001","contrasena":"pedritopro14","rol":"Reponedor"},
        {"nombre":"jorge","correo":"jorge@gmail.com","edad":78,"id":"1002","contrasena":"jorge78crack","rol":"Admin"},
        {"nombre":"Pepe","correo":"pepetroll777@gmail.com","edad":34,"id":"2001","contrasena":"lol321","rol":"Chef"}
    ]
    """;

    // Se ejecuta DESPUES de cada prueba para limpiar y generar el archivo usuarios.json
    @AfterEach
    void limpiarArchivos() {
        // Eliminar archivos generados durante la prueba
        new File("almacen.json").delete();
        new File("recetas.json").delete();

        // Se crea el archivo usuarios.json con los datos predefinidos
        // Esto para que el programa pueda iniciar con normalidad
        try (FileWriter writer = new FileWriter("usuarios.json")) {
            writer.write(USUARIOS_JSON);
        } catch (IOException e) { e.printStackTrace(); }
    }

    // Se limpian los archivos anteriormente creados
    @BeforeEach
    void setUp() {
        Archivo.guardarUsuarios(new ArrayList<>());
        Archivo.guardarAlimentos(new ArrayList<>());
        Archivo.guardarRecetas(new ArrayList<>());
    }

    // Se prueban los metodos de:
    // - Admin.crearUsuario()
    // - Usuario.getRol()
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

    // Se prueban los metodos de:
    // - admin.crearUsuario()
    // - admin.eliminarUsuario()
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

    // Se prueban los metodos de:
    // - Archivo.guardarAlimentos()
    // - chef.crearReceta()
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

    // Se prueban los metodos de:
    // - Archivo.guardarAlimentos()
    // - Alimento.getCantidad()
    // - chef.haceReceta()
    @Test
    void testHacerReceta() {
        Chef chef = new Chef("ChefUser", "chef@test.com", 25, "chef1", "password");

        // Se crea el tomate (Cantidad en el almacen)
        // - Cantidad 10
        Alimento tomate = new Alimento("Tomate", 0.5, "Vegetal", 10);
        Archivo.guardarAlimentos(List.of(tomate));

        // Se crea el ingrediente (Cantidad necesaria para la receta)
        // - Cantidad 5
        Ingrediente ingrediente = new Ingrediente(tomate, 5);
        Receta receta = new Receta("Ensalada", List.of(ingrediente), "Mezclar los ingredientes.");
        chef.crearReceta(receta);

        chef.haceReceta("Ensalada");

        List<Alimento> almacen = Archivo.cargarAlimentos();

        // Como tenemos 10 tomates y usamos 5, el objeto de alimento tomate deberia de tener una cantidad de 5
        assertEquals(5, almacen.get(0).getCantidad());
    }

    // Se prueban los metodos de:
    // - reponedor.añadirProducto()
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

    // Se prueban los metodos de:
    // - reponedor.añadirProducto()
    // - reponedor.eliminarProducto()
    @Test
    void testEliminarProductoExistente() {
        // Asegurarse de que haya productos en el almacén antes de realizar la prueba
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
        assertTrue(resultado, "El producto debería haberse eliminado correctamente (Osea = True)");

        // Cargar los alimentos desde el archivo para verificar que "Tomate" ha sido eliminado
        List<Alimento> alimentosCargados = Archivo.cargarAlimentos();
        assertEquals(2, alimentosCargados.size(), "El número de productos en el almacén debería ser 2.");
        assertFalse(alimentosCargados.stream().anyMatch(a -> a.getNombre().equalsIgnoreCase("Tomate")), "El producto \"Tomate\" debería haber sido eliminado.");
    }

    // Se prueban los metodos de:
    // - reponedor.añadirProducto()
    // - reponedor.eliminarProducto()
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
        assertFalse(resultado, "El producto no debería haberse eliminado porque no existe (Osea = False)");

        // Cargar los alimentos desde el archivo para verificar que el inventario no cambió
        List<Alimento> alimentosCargados = Archivo.cargarAlimentos();
        assertEquals(3, alimentosCargados.size(), "El número de productos en el almacén debería seguir siendo 3");
    }

    @Test
    void testEliminarProductoDeAlmacenVacio() {
        // Eliminar todos los productos para dejar el almacén vacío
        Archivo.guardarAlimentos(new ArrayList<>());

        // Intentar eliminar un producto que no esta
        Exception exception = assertThrows(IllegalStateException.class, () -> {
            Reponedor.eliminarProducto("Tomate");
        });

        // Verificar que se lanza una excepción porque no hay productos para eliminar
        assertEquals("No hay productos para eliminar.", exception.getMessage(), "Se debería lanzar una excepción si no hay productos");
    }
}

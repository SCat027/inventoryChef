package datos;

import inventoryChef.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Clase de prueba unitaria para la clase Archivo.
 * Esta clase se encarga de probar los métodos de lectura y escritura de archivos JSON
 * que gestionan la información de alimentos, recetas y usuarios.
 */
class ArchivoTest {

    // Alimentos que se guardarán en el JSON "almacen.json"
    private Alimento lechuga, tomate, agua, sal, manzana, pan;

    // Ingredientes para las recetas
    private Ingrediente IngredienteLechuga, IngredienteTomate, ingredienteAwa, ingrediente4;

    // Listas de datos
    private List<Receta> recetas;
    private List<Alimento> alimentos;
    private List<Usuario> usuarios;

    // Datos predeterminados de usuarios, para poder modificar los archivos durante las pruebas
    private static String USUARIOS_JSON = """
    [
        {"nombre":"Eduardo","correo":"eduardo@gmail.com","edad":21,"id":"1001","contrasena":"eduardocrack12","rol":"Admin"},
        {"nombre":"pedrito","correo":"pedrito@gmail.com","edad":24,"id":"3001","contrasena":"pedritopro14","rol":"Reponedor"},
        {"nombre":"jorge","correo":"jorge@gmail.com","edad":78,"id":"1002","contrasena":"jorge78crack","rol":"Admin"},
        {"nombre":"Pepe","correo":"pepetroll777@gmail.com","edad":34,"id":"2001","contrasena":"lol321","rol":"Chef"}
    ]
    """;

    /**
     * Metodo que se ejecuta antes de cada prueba para preparar los datos.
     * Inicializa los objetos de tipo Alimento, Ingrediente, Receta y Usuario.
     */
    @BeforeEach
    void setUp() {
        // Crear los objetos de tipo "Alimento"
        lechuga = new Alimento("Lechuga", 1.2, "Verdura", 50);
        tomate = new Alimento("Tomate", 1.5, "Verdura", 30);
        agua = new Alimento("Agua", 0.0, "Bebida", 1000);
        sal = new Alimento("Sal", 0.2, "Condimento", 200);
        manzana = new Alimento("Manzana", 1.5, "Fruta", 50);
        pan = new Alimento("Pan", 0.8, "Panadería", 100);

        // Crear los objetos de tipo "Ingrediente"
        IngredienteLechuga = new Ingrediente(lechuga, 1);
        IngredienteTomate = new Ingrediente(tomate, 2);
        ingredienteAwa = new Ingrediente(agua, 1);
        ingrediente4 = new Ingrediente(sal, 1);

        // Añadir recetas
        recetas = new ArrayList<>();
        recetas.add(new Receta("Ensalada", List.of(IngredienteLechuga, IngredienteTomate), "Lava y mezcla los ingredientes."));
        recetas.add(new Receta("Sopa", List.of(ingredienteAwa, ingrediente4), "Hierve el agua y lito"));

        // Añadir alimentos
        alimentos = new ArrayList<>();
        alimentos.add(manzana);
        alimentos.add(pan);

        // Añadir usuarios
        usuarios = new ArrayList<>();
        usuarios.add(new Usuario("Juan Pérez", "juan@example.com", 30, "U123", "password1"));
        usuarios.add(new Usuario("Ana López", "ana@example.com", 25, "U124", "password2"));
    }

    /**
     * Metodo que se ejecuta después de cada prueba para limpiar y restaurar los archivos necesarios.
     * Elimina los archivos generados durante las pruebas y genera el archivo 'usuarios.json' con datos predeterminados.
     */
    @AfterEach
    void limpiarArchivos() {
        // Eliminar archivos generados durante la prueba
        new File("almacen.json").delete();
        new File("recetas.json").delete();

        // Se crea el archivo usuarios.json con los datos predefinidos
        try (FileWriter writer = new FileWriter("usuarios.json")) {
            writer.write(USUARIOS_JSON);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Prueba para verificar que los alimentos se guardan correctamente en el archivo "almacen.json".
     * Guarda una lista de alimentos y luego verifica que el archivo exista y no esté vacío.
     */
    @Test
    void guardarAlimentos() {
        // Guarda la lista de alimentos en el archivo json
        Archivo.guardarAlimentos(alimentos);
        File archivo = new File("almacen.json");

        // Verifica que el archivo exista
        assertTrue(archivo.exists());

        // Verifica que el archivo "almacen.json" no esté vacío (su longitud es mayor a 0)
        assertTrue(archivo.length() > 0);
    }

    /**
     * Prueba para verificar que los alimentos se cargan correctamente desde el archivo "almacen.json".
     * Carga los alimentos desde el archivo y comprueba que los datos leídos sean correctos.
     */
    @Test
    void cargarAlimentos() {
        Archivo.guardarAlimentos(alimentos);
        List<Alimento> alimentosCargados = Archivo.cargarAlimentos();

        // Verifica que la lista cargada no sea nula
        assertNotNull(alimentosCargados);

        // Verifica que el tamaño de la lista cargada sea 2, lo que indica que se cargaron los dos alimentos
        assertEquals(2, alimentosCargados.size());

        // Verifica los datos que se guardaron en el archivo (el alimento 1)
        assertEquals("Manzana", alimentosCargados.get(0).getNombre());
        assertEquals(1.5, alimentosCargados.get(0).getPrecio());
        assertEquals(50, alimentosCargados.get(0).getCantidad());
    }

    /**
     * Prueba para verificar que los usuarios se guardan correctamente en el archivo "usuarios.json".
     * Guarda la lista de usuarios y luego verifica que el archivo exista y no esté vacío.
     */
    @Test
    void guardarUsuarios() {
        // Guarda la lista de usuarios en el archivo
        Archivo.guardarUsuarios(usuarios);
        File archivo = new File("usuarios.json");

        // Verifica que el archivo "usuarios.json" existe
        assertTrue(archivo.exists());

        // Verifica que el archivo "usuarios.json" tiene una longitud mayor a 0
        assertTrue(archivo.length() > 0);
    }

    /**
     * Prueba para cargar los usuarios desde un archivo JSON.
     * Guarda los usuarios en un archivo y luego los carga para verificar que los datos sean correctos.
     */
    @Test
    void cargarUsuarios() {
        // Guarda los usuarios en el archivo JSON usando el metodo de la clase Archivo
        Archivo.guardarUsuarios(usuarios);

        // Carga los usuarios desde el archivo JSON
        List<Usuario> usuariosCargados = Archivo.cargarUsuarios();

        // Verifica que la lista cargada no sea nula
        assertNotNull(usuariosCargados);

        // Verifica que el tamaño de la lista cargada sea igual a 2
        assertEquals(2, usuariosCargados.size());

        // Comprueba los datos del primer usuario cargado
        Usuario usuario1 = usuariosCargados.get(0);
        assertEquals("Juan Pérez", usuario1.getNombre());
        assertEquals("juan@example.com", usuario1.getCorreo());
        assertEquals(30, usuario1.getEdad());
        assertEquals("U123", usuario1.getId());
        assertEquals("password1", usuario1.getContrasena());

        // Comprueba los datos del segundo usuario cargado
        Usuario usuario2 = usuariosCargados.get(1);
        assertEquals("Ana López", usuario2.getNombre());
        assertEquals("ana@example.com", usuario2.getCorreo());
        assertEquals(25, usuario2.getEdad());
        assertEquals("U124", usuario2.getId());
        assertEquals("password2", usuario2.getContrasena());
    }

    /**
     * Prueba para verificar que las recetas se guardan correctamente en el archivo "recetas.json".
     * Guarda una lista de recetas y luego verifica que el archivo exista y no esté vacío.
     */
    @Test
    void guardarRecetas() {
        // Guarda la lista de recetas en el archivo JSON usando el metodo de la clase Archivo
        Archivo.guardarRecetas(recetas);
        File archivo = new File("recetas.json");

        // Verifica que el archivo exista
        assertTrue(archivo.exists());

        // Verifica que el archivo no esté vacío (su longitud es mayor a 0)
        assertTrue(archivo.length() > 0);
    }

    /**
     * Prueba para cargar las recetas desde un archivo JSON.
     * Guarda las recetas en un archivo y luego las carga para verificar que los datos sean correctos.
     */
    @Test
    void cargarRecetas() {
        // Guarda las recetas en el archivo
        Archivo.guardarRecetas(recetas);
        List<Receta> recetasCargadas = Archivo.cargarRecetas();

        // Verifica que la lista cargada no sea nula
        assertNotNull(recetasCargadas);

        // Verifica que el tamaño de la lista cargada sea igual a 2
        assertEquals(2, recetasCargadas.size());

        // Comprueba los datos de la primera receta cargada
        Receta receta1 = recetasCargadas.get(0);
        assertEquals("Ensalada", receta1.getNombre());
        assertEquals(2, receta1.getIngredientes().size());
        assertEquals("Lava y mezcla los ingredientes.", receta1.getInstrucciones());

        // Comprueba los datos de la segunda receta cargada
        Receta receta2 = recetasCargadas.get(1);
        assertEquals("Sopa", receta2.getNombre());
        assertEquals(2, receta2.getIngredientes().size());
        assertEquals("Hierve el agua y lito", receta2.getInstrucciones());
    }
}

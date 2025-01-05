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

class ArchivoTest {
    // Alimentos que se guardarán en el JSON "almacen.json"
    private Alimento lechuga, tomate, agua, sal, manzana, pan;
    // Ingredientes para las recetas
    private Ingrediente IngredienteLechuga, IngredienteTomate, ingredienteAwa, ingrediente4;
    // Listas de datos
    private List<Receta> recetas;
    private List<Alimento> alimentos;
    private List<Usuario> usuarios;

    // Los datos predeterminados de usuarios, esto se hace para poder modificar los archivos a gusto durante las pruebas
    private static String USUARIOS_JSON = """
    [
        {"nombre":"Eduardo","correo":"eduardo@gmail.com","edad":21,"id":"1001","contrasena":"eduardocrack12","rol":"Admin"},
        {"nombre":"pedrito","correo":"pedrito@gmail.com","edad":24,"id":"3001","contrasena":"pedritopro14","rol":"Reponedor"},
        {"nombre":"jorge","correo":"jorge@gmail.com","edad":78,"id":"1002","contrasena":"jorge78crack","rol":"Admin"},
        {"nombre":"Pepe","correo":"pepetroll777@gmail.com","edad":34,"id":"2001","contrasena":"lol321","rol":"Chef"}
    ]
    """;



    // Se ejecuta ANTES de cada prueba para preparar los datos
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



    // Se ejecuta DESPUES de cada prueba para limpiar y generar el archivo usuarios.json
    @AfterEach
    void limpiarArchivos() {
        // Eliminar archivos generados durante la prueba
        new File("almacen.json").delete();
        new File("recetas.json").delete();

        // Se crea el archivo usuarios.json con los datos predefinidos
        //Esto para que el programa pueda iniciar con normalidad
        try (FileWriter writer = new FileWriter("usuarios.json")) {
            writer.write(USUARIOS_JSON);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Se crea el "almacen.json" y se revisa que el archivo tenga una cantidad de datos guardados dentro mayor a 0
    @Test
    void guardarAlimentos() {
        // Guarda la lista de alimentos en el archivo JSON usando el metodo de la clase Archivo
        Archivo.guardarAlimentos(alimentos);

        // Crea una referencia al archivo "almacen.json"
        File archivo = new File("almacen.json");

        // Verifica que el archivo "almacen.json" exista
        assertTrue(archivo.exists());

        // Verifica que el archivo "almacen.json" no esté vacío (su longitud es mayor a 0)
        assertTrue(archivo.length() > 0);
    }



    // Prueba para verificar que los alimentos se cargan correctamente desde el archivo "almacen.json"
    @Test
    void cargarAlimentos() {
        // Guarda la lista de alimentos en el archivo JSON usando el metodo de la clase Archivo
        Archivo.guardarAlimentos(alimentos);

        // Carga los alimentos desde el archivo JSON
        List<Alimento> alimentosCargados = Archivo.cargarAlimentos();

        // Verifica que la lista cargada no sea nula (es decir, que se haya cargado correctamente)
        assertNotNull(alimentosCargados);

        // Verifica que el tamaño de la lista cargada sea 2, lo que indica que se cargaron los dos alimentos
        assertEquals(2, alimentosCargados.size());

        // Verifica que el primer alimento cargado tenga el nombre "Manzana"
        assertEquals("Manzana", alimentosCargados.get(0).getNombre());

        // Verifica que el precio del primer alimento cargado sea 1.5
        assertEquals(1.5, alimentosCargados.get(0).getPrecio());

        // Verifica que la cantidad del primer alimento cargado sea 50
        assertEquals(50, alimentosCargados.get(0).getCantidad());
    }


    // Prueba para verificar que los usuarios se guardan correctamente en el archivo "usuarios.json"
    @Test
    void guardarUsuarios() {
        // Guarda la lista de usuarios en el archivo JSON usando el metodo de la clase Archivo
        Archivo.guardarUsuarios(usuarios);

        // Crea un objeto File para acceder al archivo "usuarios.json"
        File archivo = new File("usuarios.json");

        // Verifica que el archivo "usuarios.json" existe después de guardar los usuarios
        assertTrue(archivo.exists());

        // Verifica que el archivo "usuarios.json" tiene una longitud mayor a 0, lo que indica que contiene datos
        assertTrue(archivo.length() > 0);
    }


    // Prueba para cargar los usuarios desde un archivo JSON
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

    // Prueba para verificar que las recetas se guardan correctamente en el archivo "recetas.json"
    @Test
    void guardarRecetas() {
        // Guarda la lista de recetas en el archivo JSON usando el metodo de la clase Archivo
        Archivo.guardarRecetas(recetas);

        // Crea una referencia al archivo "recetas.json"
        File archivo = new File("recetas.json");

        // Verifica que el archivo exista
        assertTrue(archivo.exists());

        // Verifica que el archivo no esté vacío (su longitud es mayor a 0)
        assertTrue(archivo.length() > 0);
    }


    // Prueba para cargar las recetas desde un archivo JSON
    @Test
    void cargarRecetas() {
        // Guarda las recetas en el archivo JSON usando el metodo de la clase Archivo
        Archivo.guardarRecetas(recetas);

        // Carga las recetas desde el archivo JSON
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
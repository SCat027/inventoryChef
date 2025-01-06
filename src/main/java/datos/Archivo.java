package datos;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import com.fasterxml.jackson.databind.ObjectMapper;
import inventoryChef.*;

/**
 * Clase para manejar la lectura y escritura de datos relacionados con los alimentos,
 * usuarios y recetas utilizando archivos JSON
 */
public class Archivo {
    private static final ObjectMapper mapper = new ObjectMapper();

    /**
     * Guarda la lista de alimentos en un archivo JSON
     *
     * @param alimentos Lista de alimentos a guardar
     */
    public static void guardarAlimentos(List<Alimento> alimentos) {
        try {
            mapper.writeValue(new File("almacen.json"), alimentos);
            System.out.println("Data de alimentos guardada en: almacen.json");
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage() + " al guardar la data en: almacen.json");
        }
    }

    /**
     * Carga la lista de alimentos desde el archivo JSON
     *
     * @return Lista de alimentos cargada desde el archivo. Si ocurre un error, devuelve una lista vacía
     */
    public static List<Alimento> cargarAlimentos() {
        ObjectMapper mapper = new ObjectMapper(); // Instancia del mapper
        File archivo = new File("almacen.json");
        try {
            if (!archivo.exists()) {
                List<Usuario> usuariosIniciales = new ArrayList<>();
                mapper.writeValue(archivo, usuariosIniciales);
                System.out.println("El archivo 'almacen.json' no existía. Se creó con una lista inicial.");
            }
            return mapper.readValue(
                    new File("almacen.json"),
                    mapper.getTypeFactory().constructCollectionType(List.class, Alimento.class)
            );
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage() + " al cargar la data de: almacen.json");
            return new ArrayList<>();
        }
    }

    /**
     * Guarda la lista de usuarios en un archivo JSON
     *
     * @param usuarios Lista de usuarios a guardar
     */
    public static void guardarUsuarios(List<Usuario> usuarios) {
        try {
            mapper.writeValue(new File("usuarios.json"), usuarios);
            System.out.println("Data de usuarios guardada en: usuarios.json");
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage() + " al guardar la data en: usuarios.json");
        }
    }

    /**
     * Carga la lista de usuarios desde el archivo JSON
     *
     * @return Lista de usuarios cargada desde el archivo. Si ocurre un error, devuelve una lista vacía
     */
    public static List<Usuario> cargarUsuarios() {
        ObjectMapper mapper = new ObjectMapper(); // Instancia del mapper
        File archivo = new File("usuarios.json");

        try {
            if (!archivo.exists()) {
                // Crear archivo con lista vacía (o datos predeterminados)
                List<Usuario> usuariosIniciales = new ArrayList<>();
                mapper.writeValue(archivo, usuariosIniciales);
                System.out.println("El archivo 'usuarios.json' no existía. Se creó con una lista inicial.");
            }
            return mapper.readValue(
                    archivo,
                    mapper.getTypeFactory().constructCollectionType(List.class, Usuario.class)
            );
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage() + " al cargar la data de: usuarios.json");
            return new ArrayList<>(); // Retorna una lista vacía en caso de error
        }
    }

    /**
     * Guarda la lista de recetas en un archivo JSON
     *
     * @param recetas Lista de recetas a guardar
     */
    public static void guardarRecetas(List<Receta> recetas) {
        try {
            mapper.writeValue(new File("recetas.json"), recetas);
            System.out.println("Recetas guardadas correctamente.");
        } catch (Exception e) {
            System.out.println("Error al guardar las recetas: " + e.getMessage());
        }
    }

    /**
     * Carga la lista de recetas desde el archivo JSON
     *
     * @return Lista de recetas cargada desde el archivo. Si ocurre un error, devuelve null
     */
    public static List<Receta> cargarRecetas() {
        ObjectMapper mapper = new ObjectMapper();
        File archivo = new File("recetas.json");
        try {
            if (!archivo.exists()) {
                List<Receta> recetas = new ArrayList<>();
                mapper.writeValue(archivo, recetas);
                System.out.println("El archivo 'recetas.json' no existía. Se creó con una lista inicial.");
            }
            return mapper.readValue(new File("recetas.json"),
                    mapper.getTypeFactory().constructCollectionType(List.class, Receta.class));
        } catch (Exception e) {
            System.out.println("Error al cargar las recetas: " + e.getMessage());
            return null;
        }
    }
}

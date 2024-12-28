package datos;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import com.fasterxml.jackson.databind.ObjectMapper;
import inventoryChef.*;

public class Archivo {
    private static final ObjectMapper mapper = new ObjectMapper();

    public static void guardarAlimentos(List<Alimento> alimentos) {
        try {
            mapper.writeValue(new File("almacen.json"), alimentos);
            System.out.println("Data de alimentos guardada en: almacen.json");
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage() + " al guardar la data en: almacen.json");
        }
    }

    public static List<Alimento> cargarAlimentos() {
        try {
            return mapper.readValue(
                    new File("almacen.json"),
                    mapper.getTypeFactory().constructCollectionType(List.class, Alimento.class)
            );
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage() + " al cargar la data de: almacen.json");
            return null;
        }
    }

    public static void guardarUsuarios(List<Usuario> usuarios) {
        try {
            mapper.writeValue(new File("usuarios.json"), usuarios);
            System.out.println("Data de usuarios guardada en: usuarios.json");
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage() + " al guardar la data en: usuarios.json");
        }
    }

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

    public static void guardarRecetas(List<Receta> recetas) {
        try {
            mapper.writeValue(new File("recetas.json"), recetas);
            System.out.println("Recetas guardadas correctamente.");
        } catch (Exception e) {
            System.out.println("Error al guardar las recetas: " + e.getMessage());
        }
    }

    public static List<Receta> cargarRecetas() {
        try {
            return mapper.readValue(new File("recetas.json"),
                    mapper.getTypeFactory().constructCollectionType(List.class, Receta.class));
        } catch (Exception e) {
            System.out.println("Error al cargar las recetas: " + e.getMessage());
            return null;
        }
    }
}



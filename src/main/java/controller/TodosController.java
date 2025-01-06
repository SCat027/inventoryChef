package controller;

import inventoryChef.Alimento;
import inventoryChef.Receta;
import inventoryChef.Usuario;
import util.InicioSesion;
import datos.Archivo;
import java.util.List;

/**
 * Clase TodosController que centraliza operaciones relacionadas con usuarios
 * autenticación, generación de IDs, gestión de alimentos y recetas
 */
public class TodosController {

    /**
     * Valida las credenciales de inicio de sesión de un usuario
     *
     * @param id Identificador del usuario
     * @param contrasena Contraseña del usuario
     * @return Usuario autenticado si las credenciales son correctas, null en caso contrario
     */
    public Usuario validarInicio(String id, String contrasena) {
        InicioSesion inicioSesion = new InicioSesion();
        return inicioSesion.autenticar(id, contrasena);
    }

    /**
     * Autentica y devuelve el rol de un usuario
     *
     * @param usuario Usuario a autenticar
     * @return Rol del usuario como cadena de texto
     */
    public String autenticarRol(Usuario usuario) {
        InicioSesion inicioSesion = new InicioSesion();
        System.out.println(inicioSesion.entregarRol(usuario));
        return inicioSesion.entregarRol(usuario);
    }

    /**
     * Genera un nuevo identificador único basado en el rol del usuario
     *
     * @param rol Rol del usuario (e.g., "admin", "reponedor", "chef")
     * @return Nuevo identificador único.
     * @throws IllegalArgumentException Si el rol proporcionado no es válido
     */
    public int generarIdPorRol(String rol) {
        int prefijo;
        switch (rol.toLowerCase()) {
            case "admin":
                prefijo = 1000;
                break;
            case "reponedor":
                prefijo = 3000;
                break;
            case "chef":
                prefijo = 2000;
                break;
            default:
                throw new IllegalArgumentException("Rol no válido.");
        }

        List<Integer> ids = Archivo.cargarUsuarios().stream()
                .filter(u -> u.getRol().equalsIgnoreCase(rol))
                .map(u -> Integer.parseInt(u.getId()))
                .toList();
        int nuevoId = prefijo + 1;
        while (ids.contains(nuevoId)) {
            nuevoId++;
        }
        return nuevoId;
    }

    /**
     * Carga todos los usuarios desde el archivo de almacenamiento
     *
     * @return Lista de usuarios
     */
    public List<Usuario> cargarUsuarios() {
        return Archivo.cargarUsuarios();
    }

    /**
     * Carga todos los alimentos desde el almacén
     *
     * @return Lista de alimentos
     */
    public List<Alimento> cargarAlmacen() {
        return Archivo.cargarAlimentos();
    }

    /**
     * Busca un producto alimenticio por su nombre en el almacén
     *
     * @param nombre Nombre del alimento a buscar
     * @return Alimento encontrado, o null si no se encuentra
     */
    public Alimento buscarProductoPorNombre(String nombre) {
        List<Alimento> almacen = cargarAlmacen();
        if (almacen == null || almacen.isEmpty()) {
            return null;
        }
        return almacen.stream()
                .filter(a -> a.getNombre().equalsIgnoreCase(nombre))
                .findFirst()
                .orElse(null);
    }

    /**
     * Carga todas las recetas desde el archivo de almacenamiento
     *
     * @return Lista de recetas
     */
    public List<Receta> cargarRecetas() {
        return Archivo.cargarRecetas();
    }

    /**
     * Busca una receta por su nombre en el archivo de recetas
     *
     * @param nombre Nombre de la receta a buscar
     * @return Receta encontrada, o null si no se encuentra
     */
    public Receta buscarRecetaPorNombre(String nombre) {
        List<Receta> recetas = cargarRecetas();
        return recetas.stream()
                .filter(r -> r.getNombre().equalsIgnoreCase(nombre))
                .findFirst()
                .orElse(null);
    }
}
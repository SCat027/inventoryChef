package inventoryChef;

import datos.Archivo;
import java.util.List;

/**
 * Clase Admin que representa un administrador del sistema.
 * Extiende la clase Usuario y agrega funcionalidades específicas para administrar usuarios.
 */
public class Admin extends Usuario {

    /**
     * Constructor por defecto que inicializa un administrador con valores predeterminados.
     * Establece el rol como "Admin".
     */
    public Admin() {
        super(); // Llama al constructor por defecto de la clase Usuario.
        this.rol = "Admin";
    }

    /**
     * Constructor parametrizado que inicializa un administrador con información específica.
     *
     * @param nombre      Nombre del administrador.
     * @param correo      Correo electrónico del administrador.
     * @param edad        Edad del administrador.
     * @param id          ID del administrador.
     * @param contrasena  Contraseña del administrador.
     */
    public Admin(String nombre, String correo, int edad, String id, String contrasena) {
        super(nombre, correo, edad, id, contrasena); // Llama al constructor de Usuario.
        super.rol = "Admin";
    }

    /**
     * Consulta la lista de usuarios registrados en el sistema y los imprime en consola.
     * Incluye el nombre y el tipo (clase) de cada usuario.
     */
    public void consultarUsuarios() {
        List<Usuario> usuarios = cargarUsuarios(); // Carga los usuarios desde el archivo.
        if (usuarios != null) {
            System.out.println("Lista de usuarios:");
            usuarios.forEach(u -> System.out.println(u.getNombre() + " - " + u.getClass().getSimpleName()));
        }
    }

    /**
     * Crea un nuevo usuario en el sistema con el rol especificado.
     *
     * @param nombre      Nombre del usuario.
     * @param correo      Correo electrónico del usuario.
     * @param edad        Edad del usuario.
     * @param id          ID del usuario.
     * @param contrasena  Contraseña del usuario.
     * @param rol         Rol del usuario (Admin, Chef o Reponedor).
     * @throws IllegalStateException    Si no se pudo cargar la lista de usuarios.
     * @throws IllegalArgumentException Si el usuario con el ID especificado ya existe.
     */
    public void crearUsuario(String nombre, String correo, int edad, String id, String contrasena, String rol) {
        List<Usuario> usuarios = cargarUsuarios();
        if (usuarios == null) {
            throw new IllegalStateException("No se pudo cargar la lista de usuarios.");
        }

        if (usuarioExiste(usuarios, id)) {
            throw new IllegalArgumentException("El usuario con ID \"" + id + "\" ya existe.");
        }

        if (rol.equals("Admin")) {
            usuarios.add(new Admin(nombre, correo, edad, id, contrasena));
        } else if (rol.equals("Chef")) {
            usuarios.add(new Chef(nombre, correo, edad, id, contrasena));
        } else if (rol.equals("Reponedor")) {
            usuarios.add(new Reponedor(nombre, correo, edad, id, contrasena));
        }

        guardarUsuarios(usuarios);
        System.out.println("Usuario \"" + nombre + "\" creado correctamente.");
    }

    /**
     * Elimina un usuario del sistema por su nombre.
     *
     * @param nombre Nombre del usuario a eliminar.
     * @throws IllegalStateException    Si no hay usuarios registrados.
     * @throws IllegalArgumentException Si el usuario no existe.
     */
    public void eliminarUsuario(String nombre) {
        List<Usuario> usuarios = cargarUsuarios();
        if (usuarios == null || usuarios.isEmpty()) {
            throw new IllegalStateException("No hay usuarios registrados.");
        }

        boolean eliminado = usuarios.removeIf(u -> u.getNombre().equalsIgnoreCase(nombre));
        if (!eliminado) {
            throw new IllegalArgumentException("El usuario \"" + nombre + "\" no existe.");
        }

        guardarUsuarios(usuarios);
        System.out.println("Usuario \"" + nombre + "\" eliminado correctamente.");
    }

    /**
     * Edita la información de un usuario existente.
     *
     * @param nombre       Nombre del usuario a editar.
     * @param nuevoCorreo  Nuevo correo electrónico del usuario.
     * @param nuevaEdad    Nueva edad del usuario.
     * @throws IllegalStateException    Si no hay usuarios registrados.
     * @throws IllegalArgumentException Si el usuario no existe.
     */
    public void editarInformacionUsuario(String nombre, String nuevoCorreo, int nuevaEdad) {
        List<Usuario> usuarios = cargarUsuarios();
        if (usuarios == null || usuarios.isEmpty()) {
            throw new IllegalStateException("No hay usuarios registrados.");
        }

        Usuario usuario = buscarUsuario(usuarios, nombre);
        if (usuario == null) {
            throw new IllegalArgumentException("El usuario \"" + nombre + "\" no existe.");
        }

        usuario.setCorreo(nuevoCorreo);
        usuario.setEdad(nuevaEdad);
        guardarUsuarios(usuarios);
        System.out.println("Información del usuario \"" + nombre + "\" editada correctamente.");
    }

    // ---------------- Métodos Auxiliares ----------------

    /**
     * Carga la lista de usuarios desde el archivo de almacenamiento.
     *
     * @return Lista de usuarios, o null si no se pudo cargar.
     */
    public List<Usuario> cargarUsuarios() {
        return Archivo.cargarUsuarios();
    }

    /**
     * Guarda la lista de usuarios en el archivo de almacenamiento.
     *
     * @param usuarios Lista de usuarios a guardar.
     */
    public void guardarUsuarios(List<Usuario> usuarios) {
        Archivo.guardarUsuarios(usuarios);
    }

    /**
     * Verifica si existe un usuario con el mismo nombre en la lista.
     *
     * @param usuarios Lista de usuarios.
     * @param nombre   Nombre del usuario a buscar.
     * @return true si existe un usuario con el nombre dado; false en caso contrario.
     */
    public boolean usuarioExiste(List<Usuario> usuarios, String nombre) {
        return usuarios.stream().anyMatch(u -> u.getNombre().equalsIgnoreCase(nombre));
    }

    /**
     * Busca un usuario por su nombre en la lista.
     *
     * @param usuarios Lista de usuarios.
     * @param nombre   Nombre del usuario a buscar.
     * @return El usuario encontrado, o null si no existe.
     */
    public Usuario buscarUsuario(List<Usuario> usuarios, String nombre) {
        return usuarios.stream()
                .filter(u -> u.getNombre().equalsIgnoreCase(nombre))
                .findFirst()
                .orElse(null);
    }
}

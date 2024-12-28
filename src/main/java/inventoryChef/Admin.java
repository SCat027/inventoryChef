package inventoryChef;

import datos.Archivo;
import java.util.List;

public class Admin extends Usuario {
    public Admin() {
        super(); // Llama al constructor por defecto de Usuario
        this.rol = "Admin";
    }
    public Admin(String nombre, String correo, int edad, String id, String contrasena) {
        super(nombre, correo, edad, id, contrasena);
        super.rol = "Admin";
    }

    public void consultarUsuarios() {
        List<Usuario> usuarios = cargarUsuarios();
        if (usuarios != null) {
            System.out.println("Lista de usuarios:");
            usuarios.forEach(u -> System.out.println(u.getNombre() + " - " + u.getClass().getSimpleName()));
        }
    }

    public void crearUsuario(String nombre, String correo, int edad, String id, String contrasena, String rol) {
        List<Usuario> usuarios = cargarUsuarios();
        if (usuarios == null) {
            throw new IllegalStateException("No se pudo cargar la lista de usuarios.");
        }

        if (usuarioExiste(usuarios, id)) {
            throw new IllegalArgumentException("El usuario con ID \"" + id + "\" ya existe.");
        }
        if (rol.equals("Admin")){
            Admin admin = new Admin(nombre, correo, edad, id, contrasena);
            usuarios.add(admin);
            guardarUsuarios(usuarios);
        } else if (rol.equals("Chef")) {
            Chef chef = new Chef(nombre, correo, edad, id, contrasena);
            usuarios.add(chef);
            guardarUsuarios(usuarios);
        } else if (rol.equals("Reponedor")) {
            Reponedor reponedor = new Reponedor(nombre, correo, edad, id, contrasena);
            usuarios.add(reponedor);
            guardarUsuarios(usuarios);
        }
        System.out.println("Usuario \"" + nombre + "\" creado correctamente.");
    }

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

    public  List<Usuario> cargarUsuarios() {
        return Archivo.cargarUsuarios();
    }

    public void guardarUsuarios(List<Usuario> usuarios) {
        Archivo.guardarUsuarios(usuarios);
    }

    public boolean usuarioExiste(List<Usuario> usuarios, String nombre) {
        return usuarios.stream().anyMatch(u -> u.getNombre().equalsIgnoreCase(nombre));
    }

    public Usuario buscarUsuario(List<Usuario> usuarios, String nombre) {
        return usuarios.stream()
                .filter(u -> u.getNombre().equalsIgnoreCase(nombre))
                .findFirst()
                .orElse(null);
    }
}

package inventoryChef;

import datos.Archivo;
import java.util.List;

public class Admin extends Usuario {

    public Admin(String nombre, String correo, int edad) {
        super(nombre, correo, edad);
        super.rol = "Admin";
    }

    public void consultarUsuarios() {
        List<Usuario> usuarios = cargarUsuarios();
        if (usuarios != null) {
            System.out.println("Lista de usuarios:");
            usuarios.forEach(u -> System.out.println(u.getNombre() + " - " + u.getClass().getSimpleName()));
        }
    }

    public boolean añadirUsuario(Usuario usuario) {
        List<Usuario> usuarios = cargarUsuarios();
        if (usuarios == null) return false;

        if (usuarioExiste(usuarios, usuario.getNombre())) {
            System.out.println("El usuario \"" + usuario.getNombre() + "\" ya existe.");
            return false;
        }

        usuarios.add(usuario);
        guardarUsuarios(usuarios);
        System.out.println("Usuario \"" + usuario.getNombre() + "\" añadido correctamente.");
        return true;
    }

    public boolean eliminarUsuario(String nombre) {
        List<Usuario> usuarios = cargarUsuarios();
        if (usuarios == null || usuarios.isEmpty()) {
            System.out.println("No hay usuarios registrados.");
            return false;
        }

        if (usuarios.removeIf(u -> u.getNombre().equalsIgnoreCase(nombre))) {
            guardarUsuarios(usuarios);
            System.out.println("Usuario \"" + nombre + "\" eliminado correctamente.");
            return true;
        } else {
            System.out.println("El usuario \"" + nombre + "\" no existe.");
            return false;
        }
    }

    public boolean editarInformacionUsuario(String nombre, String nuevoCorreo, int nuevaEdad) {
        List<Usuario> usuarios = cargarUsuarios();
        if (usuarios == null || usuarios.isEmpty()) {
            System.out.println("No hay usuarios registrados.");
            return false;
        }

        Usuario usuario = buscarUsuario(usuarios, nombre);
        if (usuario != null) {
            usuario.setCorreo(nuevoCorreo);
            usuario.setEdad(nuevaEdad);
            guardarUsuarios(usuarios);
            System.out.println("Información del usuario \"" + nombre + "\" editada correctamente.");
            return true;
        } else {
            System.out.println("El usuario \"" + nombre + "\" no existe.");
            return false;
        }
    }

    // ---------------- Métodos Auxiliares ----------------

    private List<Usuario> cargarUsuarios() {
        return Archivo.cargarUsuarios();
    }

    private void guardarUsuarios(List<Usuario> usuarios) {
        Archivo.guardarUsuarios(usuarios);
    }

    private boolean usuarioExiste(List<Usuario> usuarios, String nombre) {
        return usuarios.stream().anyMatch(u -> u.getNombre().equalsIgnoreCase(nombre));
    }

    private Usuario buscarUsuario(List<Usuario> usuarios, String nombre) {
        return usuarios.stream()
                .filter(u -> u.getNombre().equalsIgnoreCase(nombre))
                .findFirst()
                .orElse(null);
    }
}

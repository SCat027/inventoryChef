package controller;

import inventoryChef.Alimento;
import inventoryChef.Usuario;
import util.InicioSesion;
import datos.Archivo;
import java.util.List;

public class  TodosController {
    public Usuario validarInicio(String id, String contrasena) {
        InicioSesion inicioSesion = new InicioSesion();
        return  inicioSesion.autenticar(id,contrasena);
    }
    public String autenticarRol(Usuario usuario){
        InicioSesion inicioSesion = new InicioSesion();
        System.out.println(inicioSesion.entregarRol(usuario));
        return  inicioSesion.entregarRol(usuario);
    }
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
    public List<Usuario> cargarUsuarios(){
        return Archivo.cargarUsuarios();
    }
    public List<Alimento> cargarAlmacen(){
        return Archivo.cargarAlimentos();
    }
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


}

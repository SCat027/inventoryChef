package util;

import datos.Archivo;
import inventoryChef.Admin;
import inventoryChef.Chef;
import inventoryChef.Reponedor;
import inventoryChef.Usuario;

import java.util.List;


public class InicioSesion {
    private List<Usuario> usuarios;

    public InicioSesion(){
        usuarios = Archivo.cargarUsuarios();
    }
    public Usuario autenticar(String id, String contrasena) {
        for(Usuario user:usuarios){
            if(user.getId().equals(id) && user.getContrasena().equals(contrasena)){
                if(user.getRol().equals("Admin")){
                    return new Admin(user.getNombre(), user.getCorreo(), user.getEdad(), user.getId(), user.getContrasena());
                } else if (user.getRol().equals("Chef")) {
                    return new Chef(user.getNombre(), user.getCorreo(), user.getEdad(), user.getId(), user.getContrasena());
                } else if (user.getRol().equals("Reponedor")) {
                    return new Reponedor(user.getNombre(), user.getCorreo(), user.getEdad(), user.getId(), user.getContrasena());
                }
            }
        } return null;
    }
    public String entregarRol(Usuario user) {
        return user.getRol();
    }

}

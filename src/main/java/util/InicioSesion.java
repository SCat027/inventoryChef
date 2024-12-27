package util;

import datos.Archivo;
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
                return user;
            }
        } return null;
    }
    public String entregarRol(Usuario user) {
        return user.getRol();
    }

}

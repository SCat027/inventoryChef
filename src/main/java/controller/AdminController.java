package controller;

import datos.Archivo;
import inventoryChef.Usuario;
import inventoryChef.Admin;

import java.util.List;

public class AdminController {
    private List<Usuario> listaUsuarios;
    private Admin admin;

    public AdminController( Admin admin) {
        this.admin = admin;
        listaUsuarios = cargarUsuarios();
    }

    public List<Usuario> getUsuarios() {
        return listaUsuarios;
    }

    public void addUsuario(Usuario usuario) {
        admin.añadirUsuario(usuario);
    }
    public List<Usuario> cargarUsuarios(){
        listaUsuarios = Archivo.cargarUsuarios();
        return listaUsuarios;
    }
}


package controller;

import inventoryChef.Usuario;
import util.InicioSesion;

public class TodosController {
    public Usuario validarInicio(String id, String contrasena) {
        InicioSesion inicioSesion = new InicioSesion();
        return  inicioSesion.autenticar(id,contrasena);
    }
    public String autenticarRol(Usuario usuario){
        InicioSesion inicioSesion = new InicioSesion();
        return  inicioSesion.entregarRol(usuario);
    }

}

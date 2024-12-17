package controller;


import inventoryChef.Usuario;

import java.util.ArrayList;
import java.util.List;

public class AdminController {
    private List<Usuario> listaUsuarios;

    public AdminController() {
        // Simulación de usuarios existentes
        listaUsuarios = new ArrayList<>();
        listaUsuarios.add(new Usuario(1, "Juan Pérez", "Administrador", "1234"));
        listaUsuarios.add(new Usuario(2, "María López", "Editor", "abcd"));
    }

    // Obtener lista de usuarios
    public List<Usuario> getUsuarios() {
        return listaUsuarios;
    }

    // Agregar un nuevo usuario
    public void addUsuario(Usuario usuario) {
        listaUsuarios.add(usuario);
    }
}


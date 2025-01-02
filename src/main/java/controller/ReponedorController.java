package controller;

import inventoryChef.Alimento;
import datos.Archivo;
import inventoryChef.Ingrediente;
import inventoryChef.Receta;

import javax.swing.*;
import java.util.List;


public class ReponedorController {

    public List<Alimento> cargarAlimentos() {
       return Archivo.cargarAlimentos();
    }
}


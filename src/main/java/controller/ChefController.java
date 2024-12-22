package controller;

import datos.Archivo;
import inventoryChef.Alimento;
import inventoryChef.Ingrediente;
import inventoryChef.Receta;
import inventoryChef.Chef;

import java.util.List;

public class ChefController {
    private Chef chef;
    public ChefController(Chef chef){
        this.chef =chef;
    }
    public List<Alimento> cargarAlimentos() {
        return Archivo.cargarAlimentos();
    }
    public Receta crearReceta(String nombre, List<Ingrediente> ingredientes, String instrucciones) {
        return new Receta(nombre, ingredientes, instrucciones);
    }
    public Boolean agregarReceta(Receta receta){
        return chef.crearReceta(receta);
    }
}

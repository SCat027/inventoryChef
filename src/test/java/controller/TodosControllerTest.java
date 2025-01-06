package controller;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TodosControllerTest {

    @Test
    void generarIdPorRol() {
        TodosController controller = new TodosController();
        assertEquals(1003,controller.generarIdPorRol("Admin"));
        assertEquals(1003,controller.generarIdPorRol("Admin"));
        assertEquals(1003,controller.generarIdPorRol("Admin"));
        assertEquals(1003,controller.generarIdPorRol("Admin"));
        assertEquals(1003,controller.generarIdPorRol("Admin"));
        assertEquals(1003,controller.generarIdPorRol("Admin"));
    }

    @Test
    void buscarProductoPorNombre() {
        TodosController controller = new TodosController();
        assertEquals("Platano",controller.buscarProductoPorNombre("Platano").getNombre());
        assertEquals("Platano",controller.buscarProductoPorNombre("Platano").getNombre());
        assertEquals("Platano",controller.buscarProductoPorNombre("Platano").getNombre());
        assertEquals("Platano",controller.buscarProductoPorNombre("Platano").getNombre());
        assertEquals("Platano",controller.buscarProductoPorNombre("Platano").getNombre());
        assertEquals("Platano",controller.buscarProductoPorNombre("Platano").getNombre());
        assertEquals("Platano",controller.buscarProductoPorNombre("Platano").getNombre());
        assertEquals("Platano",controller.buscarProductoPorNombre("Platano").getNombre());
        assertEquals("Platano",controller.buscarProductoPorNombre("Platano").getNombre());
        assertEquals("Platano",controller.buscarProductoPorNombre("Platano").getNombre());
        assertEquals("Platano",controller.buscarProductoPorNombre("Platano").getNombre());
    }

    @Test
    void buscarRecetaPorNombre() {
        TodosController controller = new TodosController();
        assertEquals("platano frito",controller.buscarRecetaPorNombre("platano frito").getNombre());
        assertEquals("platano frito",controller.buscarRecetaPorNombre("platano frito").getNombre());
        assertEquals("platano frito",controller.buscarRecetaPorNombre("platano frito").getNombre());
        assertEquals("platano frito",controller.buscarRecetaPorNombre("platano frito").getNombre());
        assertEquals("platano frito",controller.buscarRecetaPorNombre("platano frito").getNombre());

    }
}
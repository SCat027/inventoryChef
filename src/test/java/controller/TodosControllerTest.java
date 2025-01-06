package controller;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Clase de prueba para el controlador de la aplicación "TodosController".
 * Contiene métodos de prueba para verificar el comportamiento de los métodos
 * del controlador relacionados con la generación de ID por rol y la búsqueda
 * de productos y recetas por nombre.
 */
class TodosControllerTest {

    /**
     * Prueba el metodo generarIdPorRol() de la clase TodosController.
     * Verifica que, para el rol "Admin", el ID generado sea siempre 1003.
     */
    @Test
    void generarIdPorRol() {
        TodosController controller = new TodosController();

        // Verifica que el ID generado para el rol "Admin" sea siempre 1003
        assertEquals(1003,controller.generarIdPorRol("Admin"));
        assertEquals(1003,controller.generarIdPorRol("Admin"));
        assertEquals(1003,controller.generarIdPorRol("Admin"));
        assertEquals(1003,controller.generarIdPorRol("Admin"));
        assertEquals(1003,controller.generarIdPorRol("Admin"));
        assertEquals(1003,controller.generarIdPorRol("Admin"));
    }

    /**
     * Prueba el metodo buscarProductoPorNombre() de la clase TodosController.
     * Verifica que, al buscar el producto "Platano", se retorne un objeto Producto
     * cuyo nombre sea "Platano".
     */
    @Test
    void buscarProductoPorNombre() {
        TodosController controller = new TodosController();

        // Verifica que al buscar el producto "Platano", se retorne un producto con el nombre "Platano"
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

    /**
     * Prueba el metodo buscarRecetaPorNombre() de la clase TodosController.
     * Verifica que, al buscar la receta "platano frito", se retorne una receta
     * cuyo nombre sea "platano frito".
     */
    @Test
    void buscarRecetaPorNombre() {
        TodosController controller = new TodosController();

        // Verifica que al buscar la receta "platano frito", se retorne una receta con el nombre "platano frito"
        assertEquals("platano frito",controller.buscarRecetaPorNombre("platano frito").getNombre());
        assertEquals("platano frito",controller.buscarRecetaPorNombre("platano frito").getNombre());
        assertEquals("platano frito",controller.buscarRecetaPorNombre("platano frito").getNombre());
        assertEquals("platano frito",controller.buscarRecetaPorNombre("platano frito").getNombre());
        assertEquals("platano frito",controller.buscarRecetaPorNombre("platano frito").getNombre());
    }
}

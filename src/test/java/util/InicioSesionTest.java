package util;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Clase de prueba unitaria para la clase {@link InicioSesion}.
 *
 * Esta clase verifica el correcto funcionamiento del metodo {@code autenticar}
 * de la clase {@code InicioSesion}.
 */
class InicioSesionTest {

    /**
     * Prueba unitaria para el metodo {@link InicioSesion#autenticar(String, String)}.
     *
     * Verifica que el metodo {@code autenticar} devuelva un objeto con los valores
     * esperados (nombre, rol y edad) al proporcionar credenciales válidas.
     */
    @Test
    void autenticar() {
        // Instancia de la clase InicioSesion
        InicioSesion inicioSesion = new InicioSesion();

        // Verifica que el nombre del usuario autenticado sea "Eduardo"
        assertEquals("Eduardo", inicioSesion.autenticar("1001", "eduardocrack12").getNombre());

        // Verifica que el rol del usuario autenticado sea "Admin"
        assertEquals("Admin", inicioSesion.autenticar("1001", "eduardocrack12").getRol());

        // Verifica que la edad del usuario autenticado sea 21
        assertEquals(21, inicioSesion.autenticar("1001", "eduardocrack12").getEdad());
    }
}

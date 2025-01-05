package util;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class InicioSesionTest {

    @Test
    void autenticar() {
        InicioSesion inicioSesion = new InicioSesion();
        assertEquals("Eduardo",inicioSesion.autenticar("1001","eduardocrack12").getNombre());
        assertEquals("Admin",inicioSesion.autenticar("1001","eduardocrack12").getRol());
        assertEquals(21,inicioSesion.autenticar("1001","eduardocrack12").getEdad());
    }
}
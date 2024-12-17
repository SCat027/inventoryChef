package inventoryChef;

public class Usuario {
    private String nombre;
    private String correo;
    private int edad;

    public Usuario(String nombre, String correo, int edad) {
        this.nombre = nombre;
        this.correo = correo;
        this.edad = edad;
    }

    public String getNombre() {
        return nombre;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }

    public void consultarInformacion() {
        System.out.println("Nombre: " + nombre + ", Correo: " + correo + ", Edad: " + edad);
    }
}

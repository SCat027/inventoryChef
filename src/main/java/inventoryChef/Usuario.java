package inventoryChef;

public class Usuario {


    protected String rol;
    private String nombre;
    private String correo;
    private int edad;

    public Usuario(String nombre, String correo, int edad) {
        this.nombre = nombre;
        this.correo = correo;
        this.edad = edad;
        this.rol = "Usuario";
    }

    public String getRol() { return rol; }

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

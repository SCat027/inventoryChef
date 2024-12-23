package inventoryChef;

public class Usuario {
    private String nombre;
    private String correo;
    private int edad;
    private String id;
    private String contrasena;

    public Usuario(String nombre, String correo, int edad, String id, String contrasena) {
        this.nombre = nombre;
        this.correo = correo;
        this.edad = edad;
        this.id = id;
        this.contrasena = contrasena;
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

    public String getId() {
        return id;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void consultarInformacion() {
        System.out.println("Nombre: " + nombre + ", Correo: " + correo + ", Edad: " + edad);
    }
}

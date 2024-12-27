package inventoryChef;

public class Usuario {
    private String nombre;
    private String correo;
    private int edad;
    private String id;
    private String contrasena;
    protected String rol;



    public Usuario(String nombre, String correo, int edad, String id, String contrasena) {
        this.nombre = nombre;
        this.correo = correo;
        this.edad = edad;
        this.id = id;
        this.contrasena = contrasena;
        this.rol = "Usuario";
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
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

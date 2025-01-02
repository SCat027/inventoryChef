package inventoryChef;

public class Usuario {

    private int id;
    protected String rol;
    private String nombre;
    private String correo;
    private int edad;

    private String clave;

    public Usuario(String nombre, String correo, int edad, String clave, int id) {
        this.nombre = nombre;
        this.correo = correo;
        this.id = id;
        this.edad = edad;
        this.clave = clave;
        this.rol = "Usuario";
    }

    public String getClave() {
        return clave;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }
  
    public String getRol() { return rol; }

    public String getNombre() {
        return nombre;
    }

    public String getCorreo() {
        return correo;
    }
    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public int getEdad() {
        return edad;
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

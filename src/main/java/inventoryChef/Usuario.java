package inventoryChef;

/**
 * Clase base Usuario que representa un usuario genérico en el sistema.
 * Contiene atributos y métodos comunes a todos los tipos de usuarios.
 */
public class Usuario {
    private String nombre;
    private String correo;
    private int edad;
    private String id;
    private String contrasena;
    protected String rol;

    /**
     * Constructor por defecto.
     */
    public Usuario() {
    }

    /**
     * Constructor parametrizado para inicializar un usuario con sus atributos básicos.
     *
     * @param nombre     Nombre del usuario.
     * @param correo     Correo electrónico del usuario.
     * @param edad       Edad del usuario.
     * @param id         Identificación única del usuario.
     * @param contrasena Contraseña del usuario.
     */
    public Usuario(String nombre, String correo, int edad, String id, String contrasena) {
        this.nombre = nombre;
        this.correo = correo;
        this.edad = edad;
        this.id = id;
        this.contrasena = contrasena;
        this.rol = "Usuario";
    }

    /**
     * Obtiene el correo electrónico del usuario.
     *
     * @return Correo electrónico del usuario.
     */
    public String getCorreo() {
        return correo;
    }

    /**
     * Establece el correo electrónico del usuario.
     *
     * @param correo Nuevo correo electrónico del usuario.
     */
    public void setCorreo(String correo) {
        this.correo = correo;
    }

    /**
     * Obtiene la edad del usuario.
     *
     * @return Edad del usuario.
     */
    public int getEdad() {
        return edad;
    }

    /**
     * Establece la edad del usuario.
     *
     * @param edad Nueva edad del usuario.
     */
    public void setEdad(int edad) {
        this.edad = edad;
    }

    /**
     * Obtiene el rol del usuario.
     *
     * @return Rol del usuario.
     */
    public String getRol() {
        return rol;
    }

    /**
     * Establece el rol del usuario.
     *
     * @param rol Nuevo rol del usuario.
     */
    public void setRol(String rol) {
        this.rol = rol;
    }

    /**
     * Obtiene el nombre del usuario.
     *
     * @return Nombre del usuario.
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Obtiene el ID del usuario.
     *
     * @return ID del usuario.
     */
    public String getId() {
        return id;
    }

    /**
     * Obtiene la contraseña del usuario.
     *
     * @return Contraseña del usuario.
     */
    public String getContrasena() {
        return contrasena;
    }

    /**
     * Consulta y muestra la información básica del usuario.
     */
    public void consultarInformacion() {
        System.out.println("Nombre: " + nombre + ", Correo: " + correo + ", Edad: " + edad);
    }
}

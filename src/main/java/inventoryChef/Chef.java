package inventoryChef;

import datos.Archivo;
import java.util.List;

/**
 * Clase Chef que extiende la clase Usuario
 * Representa un chef en el sistema, con funcionalidades relacionadas con las recetas
 */
public class Chef extends Usuario {

    /**
     * Constructor parametrizado para inicializar un Chef con datos específicos
     *
     * @param nombre      Nombre
     * @param correo      Correo electrónico
     * @param edad        Edad
     * @param id          ID
     * @param contrasena  Contraseña
     */
    public Chef(String nombre, String correo, int edad, String id, String contrasena) {
        super(nombre, correo, edad, id, contrasena);
        super.rol = "Chef";
    }

    /**
     * Consulta y muestra en consola las recetas disponibles
     * Incluye el nombre, ingredientes y las instrucciones de cada receta
     */
    public void consultarRecetas() {
        List<Receta> recetas = cargarRecetas();
        if (recetas != null && !recetas.isEmpty()) {
            System.out.println("Recetas disponibles:");
            recetas.forEach(receta -> {
                System.out.println("Nombre: " + receta.getNombre());
                System.out.println("Ingredientes:");
                receta.getIngredientes().forEach(ingrediente -> {
                    System.out.println("- " + ingrediente.getAlimento().getNombre() +
                            " (Cantidad: " + ingrediente.getCantidad() + ")");
                });
                System.out.println("Instrucciones: " + receta.getInstrucciones());
                System.out.println();
            });
        } else {
            System.out.println("No hay recetas disponibles.");
        }
    }

    /**
     * Crea una nueva receta y la guarda en el sistema
     *
     * @param receta Objeto Receta a crear
     * @throws IllegalStateException    Si no se pudo cargar la lista de recetas
     * @throws IllegalArgumentException Si la receta ya existe
     */
    public void crearReceta(Receta receta) {
        List<Receta> recetas = cargarRecetas();
        if (recetas == null) {
            throw new IllegalStateException("No se pudo cargar la lista de recetas.");
        }
        if (recetaExiste(recetas, receta.getNombre())) {
            throw new IllegalArgumentException("La receta \"" + receta.getNombre() + "\" ya existe.");
        }
        recetas.add(receta);
        guardarRecetas(recetas);
        System.out.println("Receta \"" + receta.getNombre() + "\" creada correctamente.");
    }

    /**
     * Elimina una receta existente por su nombre
     *
     * @param nombreReceta Nombre de la receta a eliminar
     * @throws IllegalStateException    Si no hay recetas registradas
     * @throws IllegalArgumentException Si la receta no existe
     */
    public void eliminarReceta(String nombreReceta) {
        List<Receta> recetas = cargarRecetas();
        if (recetas == null || recetas.isEmpty()) {
            throw new IllegalStateException("No hay recetas para eliminar.");
        }
        boolean recetaEliminada = recetas.removeIf(r -> r.getNombre().equalsIgnoreCase(nombreReceta));
        if (!recetaEliminada) {
            throw new IllegalArgumentException("La receta \"" + nombreReceta + "\" no existe.");
        }
        guardarRecetas(recetas);
        System.out.println("Receta \"" + nombreReceta + "\" eliminada correctamente.");
    }

    /**
     * Edita las instrucciones de una receta existente
     *
     * @param nombre             Nombre de la receta
     * @param nuevasInstrucciones Nuevas instrucciones de la receta
     * @throws IllegalStateException    Si no se pudo cargar la lista de recetas
     * @throws IllegalArgumentException Si la receta no existe
     */
    public void editarRecetaInstrucciones(String nombre, String nuevasInstrucciones) {
        List<Receta> recetas = cargarRecetas();
        if (recetas == null) {
            throw new IllegalStateException("No se pudo cargar la lista de recetas.");
        }
        Receta receta = buscarReceta(recetas, nombre);
        if (receta == null) {
            throw new IllegalArgumentException("La receta \"" + nombre + "\" no existe.");
        }

        receta.setInstrucciones(nuevasInstrucciones);
        guardarRecetas(recetas);
        System.out.println("Receta \"" + nombre + "\" actualizada correctamente.");
    }

    /**
     * Elabora una receta, descontando los ingredientes utilizados del almacén
     *
     * @param nombreReceta Nombre de la receta a elaborar
     * @throws IllegalStateException    Si no se pudo cargar la lista de recetas o el almacén
     * @throws IllegalArgumentException Si la receta no existe o no hay ingredientes suficientes
     */
    public void haceReceta(String nombreReceta) {
        List<Receta> recetas = cargarRecetas();
        if (recetas == null) {
            throw new IllegalStateException("No se pudo cargar la lista de recetas.");
        }
        List<Alimento> almacen = Archivo.cargarAlimentos();
        if (almacen == null) {
            throw new IllegalStateException("No se pudo cargar el almacén de alimentos.");
        }
        Receta receta = buscarReceta(recetas, nombreReceta);
        if (receta == null || !hayIngredientesSuficientes(receta, almacen)) {
            throw new IllegalArgumentException("No es posible elaborar la receta \"" + nombreReceta + "\".");
        }

        receta.getIngredientes().forEach(ingrediente -> {
            Alimento alimento = buscarAlimento(almacen, ingrediente.getAlimento().getNombre());
            if (alimento != null) {
                alimento.setCantidad(alimento.getCantidad() - ingrediente.getCantidad());
            }
        });
        guardarAlmacen(almacen);
        System.out.println("Receta \"" + nombreReceta + "\" elaborada exitosamente.");
    }

    /**
     * Verifica si hay suficientes ingredientes en el almacén para elaborar una receta.
     *
     * @param receta  Receta a verificar.
     * @param almacen Lista de alimentos disponibles en el almacén.
     * @return true si hay suficientes ingredientes; false en caso contrario.
     */
    private boolean hayIngredientesSuficientes(Receta receta, List<Alimento> almacen) {
        return receta.getIngredientes().stream().allMatch(ingrediente -> {
            Alimento alimento = buscarAlimento(almacen, ingrediente.getAlimento().getNombre());
            return alimento != null && alimento.getCantidad() >= ingrediente.getCantidad();
        });
    }

    // ---------------- Métodos Auxiliares ----------------

    /**
     * Guarda los cambios realizados en el almacén de alimentos.
     *
     * @param almacen Lista de alimentos actualizada.
     */
    private void guardarAlmacen(List<Alimento> almacen) {
        Archivo.guardarAlimentos(almacen);
        System.out.println("Almacén actualizado correctamente.");
    }

    /**
     * Carga la lista de recetas desde el archivo de almacenamiento.
     *
     * @return Lista de recetas.
     */
    private List<Receta> cargarRecetas() {
        return Archivo.cargarRecetas();
    }

    /**
     * Guarda la lista de recetas en el archivo de almacenamiento.
     *
     * @param recetas Lista de recetas a guardar.
     */
    private void guardarRecetas(List<Receta> recetas) {
        Archivo.guardarRecetas(recetas);
    }

    /**
     * Verifica si una receta existe por su nombre.
     *
     * @param recetas Lista de recetas existentes.
     * @param nombre  Nombre de la receta a buscar.
     * @return true si la receta existe; false en caso contrario.
     */
    private boolean recetaExiste(List<Receta> recetas, String nombre) {
        return recetas.stream().anyMatch(r -> r.getNombre().equalsIgnoreCase(nombre));
    }

    /**
     * Busca una receta por su nombre en la lista de recetas.
     *
     * @param recetas Lista de recetas existentes.
     * @param nombre  Nombre de la receta a buscar.
     * @return Objeto Receta si existe; null en caso contrario.
     */
    private Receta buscarReceta(List<Receta> recetas, String nombre) {
        return recetas.stream()
                .filter(r -> r.getNombre().equalsIgnoreCase(nombre))
                .findFirst()
                .orElse(null);
    }

    /**
     * Busca un alimento por su nombre en el almacén.
     *
     * @param almacen Lista de alimentos disponibles en el almacén.
     * @param nombre  Nombre del alimento a buscar.
     * @return Objeto Alimento si existe; null en caso contrario.
     */
    private Alimento buscarAlimento(List<Alimento> almacen, String nombre) {
        return almacen.stream()
                .filter(a -> a.getNombre().equalsIgnoreCase(nombre))
                .findFirst()
                .orElse(null);
    }
}

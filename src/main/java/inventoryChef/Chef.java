package inventoryChef;

import datos.Archivo;
import java.util.List;

public class Chef extends Usuario {

    public Chef(String nombre, String correo, int edad, String id, String contrasena) { super(nombre, correo, edad, id, contrasena);super.rol = "Chef"; }

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
            }); } else { System.out.println("No hay recetas disponibles."); }}


    //Creamos una receta
    //Parametro de objeto Receta
    public void crearReceta(Receta receta) {
        List<Receta> recetas = cargarRecetas();
        if (recetas == null) {
            throw new IllegalStateException("No se pudo cargar la lista de recetas."); }
        if (recetaExiste(recetas, receta.getNombre())) {
            throw new IllegalArgumentException("La receta \"" + receta.getNombre() + "\" ya existe.");}
        recetas.add(receta);
        guardarRecetas(recetas);
        System.out.println("Receta \"" + receta.getNombre() + "\" creada correctamente."); }


    //Eliminamos una receta por su nombre
    //En crearReceta() ya se revisa que los nombres de las recetas no se repitan
    public void eliminarReceta(String nombreReceta) {
        List<Receta> recetas = cargarRecetas();
        if (recetas == null || recetas.isEmpty()) {
            throw new IllegalStateException("No hay recetas para eliminar."); }
        boolean recetaEliminada = recetas.removeIf(r -> r.getNombre().equalsIgnoreCase(nombreReceta));
        if (!recetaEliminada) {
            throw new IllegalArgumentException("La receta \"" + nombreReceta + "\" no existe."); }
        guardarRecetas(recetas);
        System.out.println("Receta \"" + nombreReceta + "\" eliminada correctamente."); }


    //Se editan las Instrucciones de una receta (bleh :p)
    public void editarRecetaInstrucciones(String nombre, String nuevasInstrucciones) {
        List<Receta> recetas = cargarRecetas();
        if (recetas == null) {
            throw new IllegalStateException("No se pudo cargar la lista de recetas.");}
        Receta receta = buscarReceta(recetas, nombre);
        if (receta == null) {
            throw new IllegalArgumentException("La receta \"" + nombre + "\" no existe.");}

        receta.setInstrucciones(nuevasInstrucciones);
        guardarRecetas(recetas);
        System.out.println("Receta \"" + nombre + "\" actualizada correctamente.");}



    //Aqi le dejamos al chef hacer un platillo (receta), despues de crearlo la cantidad de ingredientes usados tiene que ser descartado
    // de la cantidad de alimentos del tipo de dicho ingrdiente
    // alimentos- 10 Tomates
    // ingrediente- 5 tomates
    // despues de hacer receta -> alimentos- 5 tomates

    public void haceReceta(String nombreReceta) {
        List<Receta> recetas = cargarRecetas();
        if (recetas == null) {
            throw new IllegalStateException("No se pudo cargar la lista de recetas.");}
        List<Alimento> almacen = Archivo.cargarAlimentos();
        if (almacen == null) {
            throw new IllegalStateException("No se pudo cargar el almacén de alimentos."); }
        Receta receta = buscarReceta(recetas, nombreReceta);
        if (receta == null || !hayIngredientesSuficientes(receta, almacen)) {
            throw new IllegalArgumentException("No es posible elaborar la receta \"" + nombreReceta + "\"."); }

        receta.getIngredientes().forEach(ingrediente -> {
            Alimento alimento = buscarAlimento(almacen, ingrediente.getAlimento().getNombre());
            if (alimento != null) {
                alimento.setCantidad(alimento.getCantidad() - ingrediente.getCantidad()); }});
        guardarAlmacen(almacen);
        System.out.println("Receta \"" + nombreReceta + "\" elaborada exitosamente.");}



    //Lo usamos para saber si existen los sufiecientes ingredientes en el almacen, comparando uno en uno
    private boolean hayIngredientesSuficientes(Receta receta, List<Alimento> almacen) {
        return receta.getIngredientes().stream().allMatch(ingrediente -> {
            Alimento alimento = buscarAlimento(almacen, ingrediente.getAlimento().getNombre());
            return alimento != null && alimento.getCantidad() >= ingrediente.getCantidad();
        }); }


    // ---------------- Métodos Auxiliares ----------------

    private void guardarAlmacen(List<Alimento> almacen) {
        Archivo.guardarAlimentos(almacen);
        System.out.println("Almacén actualizado correctamente."); }

    private List<Receta> cargarRecetas() {
        return Archivo.cargarRecetas();
    }

    private void guardarRecetas(List<Receta> recetas) {
        Archivo.guardarRecetas(recetas);
    }

    private boolean recetaExiste(List<Receta> recetas, String nombre) {
        return recetas.stream().anyMatch(r -> r.getNombre().equalsIgnoreCase(nombre)); }

    private Receta buscarReceta(List<Receta> recetas, String nombre) {
        return recetas.stream()
                .filter(r -> r.getNombre().equalsIgnoreCase(nombre))
                .findFirst()
                .orElse(null); }

    private Alimento buscarAlimento(List<Alimento> almacen, String nombre) {
        return almacen.stream()
                .filter(a -> a.getNombre().equalsIgnoreCase(nombre))
                .findFirst()
                .orElse(null); }


}

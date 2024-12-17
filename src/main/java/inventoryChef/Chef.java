package inventoryChef;

import datos.Archivo;
import java.util.List;

public class Chef extends Usuario {

    public Chef(String nombre, String correo, int edad) {
        super(nombre, correo, edad);
    }

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

    public boolean crearReceta(Receta receta) {
        List<Receta> recetas = cargarRecetas();

        if (recetaExiste(recetas, receta.getNombre())) {
            System.out.println("La receta \"" + receta.getNombre() + "\" ya existe.");
            return false;
        }

        recetas.add(receta);
        guardarRecetas(recetas);
        System.out.println("Receta \"" + receta.getNombre() + "\" creada correctamente.");
        return true;
    }

    public boolean eliminarReceta(String nombreReceta) {
        List<Receta> recetas = cargarRecetas();
        if (recetas == null || recetas.isEmpty()) {
            System.out.println("No hay recetas para eliminar.");
            return false;
        }

        boolean recetaEliminada = recetas.removeIf(r -> r.getNombre().equalsIgnoreCase(nombreReceta));
        if (recetaEliminada) {
            guardarRecetas(recetas);
            System.out.println("Receta \"" + nombreReceta + "\" eliminada correctamente.");
            return true;
        } else {
            System.out.println("La receta \"" + nombreReceta + "\" no existe.");
            return false;
        }
    }

    public boolean editarReceta(String nombre, String nuevasInstrucciones) {
        List<Receta> recetas = cargarRecetas();
        Receta receta = buscarReceta(recetas, nombre);

        if (receta != null) {
            receta.setInstrucciones(nuevasInstrucciones);
            guardarRecetas(recetas);
            System.out.println("Receta \"" + nombre + "\" actualizada correctamente.");
            return true;
        } else {
            System.out.println("La receta \"" + nombre + "\" no existe.");
            return false;
        }
    }

    public boolean haceReceta(String nombreReceta) {
        List<Receta> recetas = cargarRecetas();
        List<Alimento> almacen = Archivo.cargarAlimentos();

        Receta receta = buscarReceta(recetas, nombreReceta);

        if (!esRecetaValida(receta)) {
            System.out.println("La receta \"" + nombreReceta + "\" no existe.");
            return false;
        }

        if (!hayIngredientesSuficientes(receta, almacen)) {
            return false;
        }

        restarIngredientes(receta, almacen);
        guardarAlmacen(almacen);

        System.out.println("Receta \"" + nombreReceta + "\" elaborada exitosamente.");
        return true;
    }

    private boolean esRecetaValida(Receta receta) {
        return receta != null;
    }

    private boolean hayIngredientesSuficientes(Receta receta, List<Alimento> almacen) {
        for (Ingrediente ingrediente : receta.getIngredientes()) {
            Alimento alimentoAlmacen = buscarAlimento(almacen, ingrediente.getAlimento().getNombre());
            if (alimentoAlmacen == null || alimentoAlmacen.getCantidad() < ingrediente.getCantidad()) {
                System.out.println("No hay suficiente cantidad de " + ingrediente.getAlimento().getNombre() +
                        " para elaborar la receta.");
                return false;
            }
        }
        return true;
    }

    private void restarIngredientes(Receta receta, List<Alimento> almacen) {
        for (Ingrediente ingrediente : receta.getIngredientes()) {
            Alimento alimentoAlmacen = buscarAlimento(almacen, ingrediente.getAlimento().getNombre());
            if (alimentoAlmacen != null) {
                alimentoAlmacen.setCantidad(alimentoAlmacen.getCantidad() - ingrediente.getCantidad());
            }
        }
    }

    private void guardarAlmacen(List<Alimento> almacen) {
        Archivo.guardarAlimentos(almacen);
        System.out.println("Almacén actualizado correctamente.");
    }
    
    // ---------------- Métodos Auxiliares ----------------

    private List<Receta> cargarRecetas() {
        return Archivo.cargarRecetas();
    }

    private void guardarRecetas(List<Receta> recetas) {
        Archivo.guardarRecetas(recetas);
    }

    private boolean recetaExiste(List<Receta> recetas, String nombre) {
        return recetas.stream().anyMatch(r -> r.getNombre().equalsIgnoreCase(nombre));
    }

    private Receta buscarReceta(List<Receta> recetas, String nombre) {
        return recetas.stream()
                .filter(r -> r.getNombre().equalsIgnoreCase(nombre))
                .findFirst()
                .orElse(null);
    }

    private Alimento buscarAlimento(List<Alimento> almacen, String nombre) {
        return almacen.stream()
                .filter(a -> a.getNombre().equalsIgnoreCase(nombre))
                .findFirst()
                .orElse(null);
    }
}

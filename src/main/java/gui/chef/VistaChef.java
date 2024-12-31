package gui.chef;

import inventoryChef.Alimento;
import inventoryChef.Chef;
import inventoryChef.Ingrediente;
import inventoryChef.Receta;
import controller.TodosController;
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class VistaChef extends JFrame {
    private Chef chef;
    private TodosController controller;

    public VistaChef(Chef chef) {
        this.chef = chef;
        this.controller = new TodosController();
        iniciarChef();

    }
    private  void iniciarChef(){
        setTitle("Gestión de Recetas - Chef");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new GridLayout(5, 1, 10, 10));

        JButton consultarRecetasBtn = new JButton("Consultar Recetas");
        JButton crearRecetaBtn = new JButton("Crear Receta");
        JButton editarRecetaBtn = new JButton("Editar Instrucciones");
        JButton eliminarRecetaBtn = new JButton("Eliminar Receta");
        JButton hacerRecetaBtn = new JButton("Hacer Receta");

        mainPanel.add(consultarRecetasBtn);
        mainPanel.add(crearRecetaBtn);
        mainPanel.add(editarRecetaBtn);
        mainPanel.add(eliminarRecetaBtn);
        mainPanel.add(hacerRecetaBtn);

        add(mainPanel, BorderLayout.CENTER);

        // Botones con acciones
        consultarRecetasBtn.addActionListener(e -> consultarRecetas());
        crearRecetaBtn.addActionListener(e -> crearReceta());
        editarRecetaBtn.addActionListener(e -> editarReceta());
        eliminarRecetaBtn.addActionListener(e -> eliminarReceta());
        hacerRecetaBtn.addActionListener(e -> hacerReceta());

        setVisible(true);
    }

    private void consultarRecetas() {
        List<Receta> recetas = controller.cargarRecetas();
        if (recetas == null || recetas.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No hay recetas disponibles.");
            return;
        }

        StringBuilder sb = new StringBuilder("Recetas disponibles:\n");
        for (Receta receta : recetas) {
            sb.append("Nombre: ").append(receta.getNombre()).append("\n");
            sb.append("Ingredientes:\n");
            for (Ingrediente ing : receta.getIngredientes()) {
                sb.append("- ").append(ing.getAlimento().getNombre())
                        .append(" (Cantidad: ").append(ing.getCantidad()).append(")\n");
            }
            sb.append("Instrucciones: ").append(receta.getInstrucciones()).append("\n\n");
        }
        JOptionPane.showMessageDialog(this, sb.toString());
    }

    private void crearReceta() {
        String nombre = JOptionPane.showInputDialog("Ingrese el nombre de la receta:");
        if (nombre == null || nombre.trim().isEmpty()) return;

        String instrucciones = JOptionPane.showInputDialog("Ingrese las instrucciones:");
        if (instrucciones == null || instrucciones.trim().isEmpty()) return;

        List<Ingrediente> ingredientes = new ArrayList<>();
        List<Alimento> almacen = controller.cargarAlmacen();

        if (almacen == null || almacen.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No hay alimentos disponibles en el almacén.");
            return;
        }

        int opcion = JOptionPane.showConfirmDialog(this, "¿Agregar ingredientes?");
        while (opcion == JOptionPane.YES_OPTION) {
            String[] alimentosNombres = almacen.stream().map(Alimento::getNombre).toArray(String[]::new);
            String alimentoSeleccionado = (String) JOptionPane.showInputDialog(
                    this,
                    "Seleccione un alimento:",
                    "Agregar Ingrediente",
                    JOptionPane.QUESTION_MESSAGE,
                    null,
                    alimentosNombres,
                    alimentosNombres[0]);

            if (alimentoSeleccionado == null) break;

            Alimento alimento = almacen.stream()
                    .filter(a -> a.getNombre().equalsIgnoreCase(alimentoSeleccionado))
                    .findFirst()
                    .orElse(null);

            if (alimento == null) {
                JOptionPane.showMessageDialog(this, "Alimento no encontrado.");
                break;
            }

            String cantidadStr = JOptionPane.showInputDialog("Cantidad de " + alimentoSeleccionado + ":");
            if (cantidadStr == null || cantidadStr.trim().isEmpty()) break;

            try {
                int cantidad = Integer.parseInt(cantidadStr);
                if (cantidad <= 0 || cantidad > alimento.getCantidad()) {
                    JOptionPane.showMessageDialog(this, "Cantidad inválida o excede el inventario disponible.");
                } else {
                    ingredientes.add(new Ingrediente(alimento, cantidad));
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Cantidad inválida.");
            }
            opcion = JOptionPane.showConfirmDialog(this, "¿Agregar otro ingrediente?");
        }

        try {
            Receta receta = new Receta(nombre, ingredientes, instrucciones);
            chef.crearReceta(receta);
            JOptionPane.showMessageDialog(this, "Receta creada con éxito.");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error al crear receta: " + ex.getMessage());
        }
    }

    private void editarReceta() {
        String nombre = JOptionPane.showInputDialog("Ingrese el nombre de la receta:");
        if (nombre == null || nombre.trim().isEmpty()) return;

        String nuevasInstrucciones = JOptionPane.showInputDialog("Ingrese las nuevas instrucciones:");
        if (nuevasInstrucciones == null || nuevasInstrucciones.trim().isEmpty()) return;

        try {
            chef.editarRecetaInstrucciones(nombre, nuevasInstrucciones);
            JOptionPane.showMessageDialog(this, "Instrucciones actualizadas con éxito.");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error al editar receta: " + ex.getMessage());
        }
    }

    private void eliminarReceta() {
        String nombre = JOptionPane.showInputDialog("Ingrese el nombre de la receta a eliminar:");
        if (nombre == null || nombre.trim().isEmpty()) return;

        try {
            chef.eliminarReceta(nombre);
            JOptionPane.showMessageDialog(this, "Receta eliminada con éxito.");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error al eliminar receta: " + ex.getMessage());
        }
    }

    private void hacerReceta() {
        String nombre = JOptionPane.showInputDialog("Ingrese el nombre de la receta a preparar:");
        if (nombre == null || nombre.trim().isEmpty()) return;

        try {
            chef.haceReceta(nombre);
            JOptionPane.showMessageDialog(this, "Receta preparada con éxito.");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error al preparar receta: " + ex.getMessage());
        }
    }
}



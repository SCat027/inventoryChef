package gui.chef;
import inventoryChef.Alimento;
import inventoryChef.Chef;
import inventoryChef.Ingrediente;
import inventoryChef.Receta;
import controller.TodosController;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class VistaChef extends JFrame {
    private Chef chef;
    private TodosController controller;
    private JTable recipeTable;
    private DefaultTableModel tableModel;

    public VistaChef(Chef chef) {
        this.chef = chef;
        this.controller = new TodosController();
        iniciarChef();
    }

    private void iniciarChef() {
        setTitle("Gestión de Recetas - Chef");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton agregarRecetaBtn = new JButton("Agregar Receta");
        agregarRecetaBtn.addActionListener(e -> crearReceta());
        topPanel.add(agregarRecetaBtn);
        add(topPanel, BorderLayout.NORTH);

        tableModel = new DefaultTableModel(new Object[]{"Nombre de la Receta"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        recipeTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(recipeTable);
        add(scrollPane, BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));

        JButton verDetallesBtn = new JButton("Ver Detalles");
        JButton eliminarRecetaBtn = new JButton("Eliminar Receta");
        JButton hacerRecetaBtn = new JButton("Hacer Receta");

        verDetallesBtn.addActionListener(e -> verDetalles());
        eliminarRecetaBtn.addActionListener(e -> eliminarReceta());
        hacerRecetaBtn.addActionListener(e -> hacerReceta());

        bottomPanel.add(verDetallesBtn);
        bottomPanel.add(eliminarRecetaBtn);
        bottomPanel.add(hacerRecetaBtn);

        add(bottomPanel, BorderLayout.SOUTH);

        cargarRecetas();
        setVisible(true);
    }

    private void cargarRecetas() {
        tableModel.setRowCount(0); // Limpiar la tabla
        List<Receta> recetas = controller.cargarRecetas();
        if (recetas != null && !recetas.isEmpty()) {
            for (Receta receta : recetas) {
                tableModel.addRow(new Object[]{receta.getNombre()});
            }
        }
    }

    private void hacerReceta() {
        int selectedRow = recipeTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Seleccione una receta para hacer.");
            return;
        }
        String recetaNombre = (String) tableModel.getValueAt(selectedRow, 0);
        try{
            chef.haceReceta(recetaNombre);
            JOptionPane.showMessageDialog(this, "Se realizo la receta con exito");
        }catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error al hacer receta: " + ex.getMessage());
        }

    }

    private void verDetalles() {
        int selectedRow = recipeTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Seleccione una receta para ver detalles.");
            return;
        }

        String recetaNombre = (String) tableModel.getValueAt(selectedRow, 0);
        Receta receta = controller.buscarRecetaPorNombre(recetaNombre);

        if (receta == null) {
            JOptionPane.showMessageDialog(this, "Receta no encontrada.");
            return;
        }

        JDialog detallesDialog = new JDialog(this, "Detalles de la Receta", true);
        detallesDialog.setSize(400, 300);
        detallesDialog.setLayout(new BorderLayout());

        JTextArea detallesArea = new JTextArea();
        detallesArea.setEditable(false);
        StringBuilder sb = new StringBuilder();
        sb.append("Nombre: ").append(receta.getNombre()).append("\n");
        sb.append("Ingredientes:\n");
        for (Ingrediente ing : receta.getIngredientes()) {
            sb.append("- ").append(ing.getAlimento().getNombre())
                    .append(" (Cantidad: ").append(ing.getCantidad()).append(")\n");
        }
        sb.append("\nInstrucciones:\n").append(receta.getInstrucciones()).append("\n");
        detallesArea.setText(sb.toString());

        detallesDialog.add(new JScrollPane(detallesArea), BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton cerrarBtn = new JButton("Cerrar");
        JButton editarRecetaBtn = new JButton("Editar");
        editarRecetaBtn.addActionListener(e -> editarReceta());
        cerrarBtn.addActionListener(e -> detallesDialog.dispose());

        buttonPanel.add(editarRecetaBtn);
        buttonPanel.add(cerrarBtn);
        detallesDialog.add(buttonPanel, BorderLayout.SOUTH);
        detallesDialog.setVisible(true);
    }

    private void eliminarReceta() {
        int selectedRow = recipeTable.getSelectedRow(); // Obtener la fila seleccionada
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Seleccione una receta para eliminar.");
            return;
        }

        String nombreReceta = (String) tableModel.getValueAt(selectedRow, 0); // Asumiendo que la columna 0 tiene el nombre
        Receta recetaSeleccionada = controller.buscarRecetaPorNombre(nombreReceta);

        if (recetaSeleccionada == null) {
            JOptionPane.showMessageDialog(this, "No se encontró la receta seleccionada.");
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(
                this,
                "¿Está seguro de que desea eliminar la receta \"" + nombreReceta + "\"?",
                "Confirmar eliminación",
                JOptionPane.YES_NO_OPTION
        );

        if (confirm == JOptionPane.YES_OPTION) {
            try {
                chef.eliminarReceta(recetaSeleccionada.getNombre());
                JOptionPane.showMessageDialog(this, "Receta eliminada con éxito.");
                cargarRecetas(); // Refrescar la tabla
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error al eliminar receta: " + ex.getMessage());
            }
        }
    }

    private void crearReceta() {
        String nombre = JOptionPane.showInputDialog(this, "Ingrese el nombre de la receta:");
        if (nombre == null || nombre.trim().isEmpty()) return;

        String instrucciones = JOptionPane.showInputDialog(this, "Ingrese las instrucciones:");
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
            cargarRecetas();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error al crear receta: " + ex.getMessage());
        }
    }
    private void editarReceta() {
        int selectedRow = recipeTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Seleccione una receta para editar.");
            return;
        }
        String nombreReceta = (String) tableModel.getValueAt(selectedRow, 0);
        String nuevasInstrucciones = JOptionPane.showInputDialog("Ingrese las nuevas instrucciones:");
        if (nuevasInstrucciones == null || nuevasInstrucciones.trim().isEmpty()) return;
        try {
            chef.editarRecetaInstrucciones(nombreReceta, nuevasInstrucciones);
            JOptionPane.showMessageDialog(this, "Instrucciones actualizadas con éxito.");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error al editar receta: " + ex.getMessage());
        }
    }
}





package guis.chef;

import org.example.models.Ingrediente;
import org.example.models.Receta;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class AgregarReceta extends JFrame {
    private JTextField recetaNombreField;
    private JTextField ingredienteNombreField;
    private JTextField cantidadField;
    private JTextField unidadField;
    private DefaultListModel<String> ingredientesListModel;
    private JList<String> ingredientesList;
    private ArrayList<Ingrediente> ingredientes;

    public AgregarReceta() {
        setTitle("Agregar Receta");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        ingredientes = new ArrayList<>();

        // Panel principal
        JPanel mainPanel = new JPanel(new BorderLayout());

        // Panel para el nombre de la receta
        JPanel recetaPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel recetaLabel = new JLabel("Nombre de la Receta: ");
        recetaNombreField = new JTextField(20);
        recetaPanel.add(recetaLabel);
        recetaPanel.add(recetaNombreField);

        mainPanel.add(recetaPanel, BorderLayout.NORTH);

        // Panel para agregar ingredientes
        JPanel ingredientePanel = new JPanel(new GridLayout(2, 4, 10, 10));
        ingredientePanel.setBorder(BorderFactory.createTitledBorder("Agregar Ingrediente"));

        ingredientePanel.add(new JLabel("Nombre:"));
        ingredienteNombreField = new JTextField();
        ingredientePanel.add(ingredienteNombreField);

        ingredientePanel.add(new JLabel("Cantidad:"));
        cantidadField = new JTextField();
        ingredientePanel.add(cantidadField);

        ingredientePanel.add(new JLabel("Unidad:"));
        unidadField = new JTextField();
        ingredientePanel.add(unidadField);

        JButton agregarIngredienteBtn = new JButton("Agregar Ingrediente");
        ingredientePanel.add(agregarIngredienteBtn);

        mainPanel.add(ingredientePanel, BorderLayout.CENTER);

        // Lista de ingredientes agregados
        JPanel listaPanel = new JPanel(new BorderLayout());
        listaPanel.setBorder(BorderFactory.createTitledBorder("Ingredientes de la Receta"));

        ingredientesListModel = new DefaultListModel<>();
        ingredientesList = new JList<>(ingredientesListModel);
        JScrollPane scrollPane = new JScrollPane(ingredientesList);

        listaPanel.add(scrollPane, BorderLayout.CENTER);

        mainPanel.add(listaPanel, BorderLayout.SOUTH);

        add(mainPanel, BorderLayout.CENTER);

        // Botón para guardar la receta
        JButton guardarRecetaBtn = new JButton("Guardar Receta");
        add(guardarRecetaBtn, BorderLayout.SOUTH);

        // Acción para agregar un ingrediente
        agregarIngredienteBtn.addActionListener(e -> {
            String nombre = ingredienteNombreField.getText();
            String cantidadText = cantidadField.getText();
            String unidad = unidadField.getText();

            if (nombre.isEmpty() || cantidadText.isEmpty() || unidad.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Todos los campos del ingrediente son obligatorios.",
                        "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            try {
                double cantidad = Double.parseDouble(cantidadText);
                Ingrediente ingrediente = new Ingrediente(nombre, cantidad, unidad);
                ingredientes.add(ingrediente);
                ingredientesListModel.addElement(ingrediente.toString());

                // Limpiar campos
                ingredienteNombreField.setText("");
                cantidadField.setText("");
                unidadField.setText("");
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "La cantidad debe ser un número válido.",
                        "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        // Acción para guardar la receta completa
        guardarRecetaBtn.addActionListener(e -> {
            String nombreReceta = recetaNombreField.getText();

            if (nombreReceta.isEmpty()) {
                JOptionPane.showMessageDialog(this, "El nombre de la receta no puede estar vacío.",
                        "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (ingredientes.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Debe agregar al menos un ingrediente.",
                        "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            Receta receta = new Receta(nombreReceta, ingredientes);
            JOptionPane.showMessageDialog(this, "Receta guardada:\n" + receta.toString(),
                    "Éxito", JOptionPane.INFORMATION_MESSAGE);

            // Reiniciar la GUI
            recetaNombreField.setText("");
            ingredientes.clear();
            ingredientesListModel.clear();
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            AgregarReceta gui = new AgregarReceta();
            gui.setVisible(true);
        });
    }
}

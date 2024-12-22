package guis.chef;

import controller.ChefController;
import inventoryChef.Alimento;
import inventoryChef.Chef;
import inventoryChef.Ingrediente;
import inventoryChef.Receta;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.ArrayList;

public class AgregarReceta extends JFrame{
    private JFrame frame;
    private ChefController controller;
    private List<Alimento> alimentos;

    public AgregarReceta(Chef chef) {
        controller = new ChefController(chef);
        alimentos = controller.cargarAlimentos();
        inicializarUI();
    }

    private void inicializarUI() {
        // Crear ventana principal
        frame = new JFrame("Agregar Receta");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 400);
        frame.getContentPane().setBackground(Color.WHITE);

        // Layout principal
        frame.setLayout(new BorderLayout());

        // Panel superior para el nombre de la receta
        JPanel panelSuperior = new JPanel();
        panelSuperior.setBackground(new Color(173, 216, 230)); // Celeste
        panelSuperior.setLayout(new FlowLayout());

        JLabel labelNombre = new JLabel("Nombre de la receta:");
        JTextField textNombre = new JTextField(20);
        panelSuperior.add(labelNombre);
        panelSuperior.add(textNombre);
        frame.add(panelSuperior, BorderLayout.NORTH);

        // Panel central para ingredientes
        JPanel panelCentral = new JPanel();
        panelCentral.setBackground(Color.WHITE);
        panelCentral.setLayout(new BorderLayout());

        JLabel labelIngredientes = new JLabel("Ingredientes:");
        panelCentral.add(labelIngredientes, BorderLayout.NORTH);

        DefaultListModel<String> modeloLista = new DefaultListModel<>();
        JList<String> listaIngredientes = new JList<>(modeloLista);
        JScrollPane scrollLista = new JScrollPane(listaIngredientes);
        panelCentral.add(scrollLista, BorderLayout.CENTER);

        JComboBox<String> comboAlimentos = new JComboBox<>(
                alimentos.stream().map(Alimento::getNombre).toArray(String[]::new)
        );
        JTextField textCantidad = new JTextField(5);
        JButton botonAgregarIngrediente = new JButton("Agregar");

        JPanel panelAgregar = new JPanel();
        panelAgregar.setBackground(Color.LIGHT_GRAY);
        panelAgregar.add(comboAlimentos);
        panelAgregar.add(new JLabel("Cantidad:"));
        panelAgregar.add(textCantidad);
        panelAgregar.add(botonAgregarIngrediente);
        panelCentral.add(panelAgregar, BorderLayout.SOUTH);
        frame.add(panelCentral, BorderLayout.CENTER);

        // Panel inferior para instrucciones y botón guardar
        JPanel panelInferior = new JPanel();
        panelInferior.setBackground(new Color(220, 220, 220)); // Gris claro
        panelInferior.setLayout(new BorderLayout());

        JLabel labelInstrucciones = new JLabel("Instrucciones:");
        JTextArea textInstrucciones = new JTextArea(5, 40);
        JScrollPane scrollInstrucciones = new JScrollPane(textInstrucciones);

        panelInferior.add(labelInstrucciones, BorderLayout.NORTH);
        panelInferior.add(scrollInstrucciones, BorderLayout.CENTER);

        JButton botonGuardar = new JButton("Guardar Receta");
        botonGuardar.setBackground(new Color(173, 216, 230)); // Celeste
        botonGuardar.setFocusPainted(false);
        botonGuardar.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1, true));
        panelInferior.add(botonGuardar, BorderLayout.SOUTH);

        frame.add(panelInferior, BorderLayout.SOUTH);

        // Acción para agregar ingredientes
        botonAgregarIngrediente.addActionListener(e -> {
            String alimentoSeleccionado = (String) comboAlimentos.getSelectedItem();
            String cantidadStr = textCantidad.getText();
            try {
                int cantidad = Integer.parseInt(cantidadStr);
                modeloLista.addElement(alimentoSeleccionado + " - " + cantidad);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(frame, "Cantidad inválida.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        // Acción para guardar receta
        botonGuardar.addActionListener(e -> {
            String nombreReceta = textNombre.getText();
            String instrucciones = textInstrucciones.getText();
            List<Ingrediente> ingredientes = new ArrayList<>();

            for (int i = 0; i < modeloLista.size(); i++) {
                String[] datos = modeloLista.get(i).split(" - ");
                String nombreAlimento = datos[0];
                int cantidad = Integer.parseInt(datos[1]);

                Alimento alimento = alimentos.stream()
                        .filter(a -> a.getNombre().equals(nombreAlimento))
                        .findFirst()
                        .orElse(null);

                if (alimento != null) {
                    ingredientes.add(new Ingrediente(alimento, cantidad));
                }
            }

            Receta receta = controller.crearReceta(nombreReceta, ingredientes, instrucciones);

            JOptionPane.showMessageDialog(frame, "Receta guardada: " + receta.getNombre(), "Éxito", JOptionPane.INFORMATION_MESSAGE);
        });

        // Mostrar la ventana
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        new AgregarReceta();
    }
}

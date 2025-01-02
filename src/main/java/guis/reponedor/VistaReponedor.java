package guis.reponedor;

import controller.ReponedorController;
import inventoryChef.Alimento;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class VistaReponedor extends JFrame {
    private ReponedorController controller;
    private List<Alimento> alimentos;

    public AgregarAlimentos() {
        controller = new ReponedorController();
        alimentos = controller.cargarAlimentos();
        inicializarUI();
    }

    private void inicializarUI() {
        // Configuración de la ventana
        setTitle("Agregar Alimentos");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 300);
        getContentPane().setBackground(Color.WHITE);
        setLayout(new BorderLayout());

        // Panel superior para formulario
        JPanel panelFormulario = new JPanel(new GridLayout(4, 2, 5, 5));
        panelFormulario.setBackground(new Color(173, 216, 230)); // Celeste

        JLabel labelNombre = new JLabel("Nombre:");
        JTextField textNombre = new JTextField();
        JLabel labelPrecio = new JLabel("Precio:");
        JTextField textPrecio = new JTextField();
        JLabel labelCategoria = new JLabel("Categoría:");
        JTextField textCategoria = new JTextField();
        JLabel labelCantidad = new JLabel("Cantidad:");
        JTextField textCantidad = new JTextField();

        panelFormulario.add(labelNombre);
        panelFormulario.add(textNombre);
        panelFormulario.add(labelPrecio);
        panelFormulario.add(textPrecio);
        panelFormulario.add(labelCategoria);
        panelFormulario.add(textCategoria);
        panelFormulario.add(labelCantidad);
        panelFormulario.add(textCantidad);

        add(panelFormulario, BorderLayout.CENTER);

        // Panel inferior para botones
        JPanel panelBotones = new JPanel();
        panelBotones.setBackground(new Color(220, 220, 220)); // Gris claro

        JButton botonAgregar = new JButton("Agregar");
        botonAgregar.setBackground(new Color(173, 216, 230));
        botonAgregar.setFocusPainted(false);
        botonAgregar.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1, true));

        panelBotones.add(botonAgregar);
        add(panelBotones, BorderLayout.SOUTH);

        // Acción para agregar alimento
        botonAgregar.addActionListener(e -> {
            String nombre = textNombre.getText();
            String precioStr = textPrecio.getText();
            String categoria = textCategoria.getText();
            String cantidadStr = textCantidad.getText();

            try {
                int precio = Integer.parseInt(precioStr);
                int cantidad = Integer.parseInt(cantidadStr);

                Alimento nuevoAlimento = new Alimento();
                nuevoAlimento.setNombre(nombre);
                nuevoAlimento.setPrecio(precio);
                nuevoAlimento.setCategoria(categoria);
                nuevoAlimento.setCantidad(cantidad);

                alimentos.add(nuevoAlimento);
                controller.guardarAlimentos(alimentos);

                JOptionPane.showMessageDialog(this, "Alimento agregado exitosamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);

                // Limpiar campos
                textNombre.setText("");
                textPrecio.setText("");
                textCategoria.setText("");
                textCantidad.setText("");
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Precio o cantidad inválidos.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        // Mostrar la ventana
        setVisible(true);
    }

    public static void main(String[] args) {
        new AgregarAlimentos();
    }
}


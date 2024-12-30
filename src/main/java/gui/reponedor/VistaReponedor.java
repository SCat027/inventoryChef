package gui.reponedor;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import inventoryChef.Alimento;
import inventoryChef.Reponedor;
import controller.TodosController;

public class VistaReponedor extends JFrame {
    private TodosController controller;
    private Reponedor reponedor;


    public VistaReponedor(Reponedor reponedor) {
        this.controller = new TodosController();
        this.reponedor = reponedor;
        initializeUI();
    }

    private void initializeUI() {
        JFrame frame = new JFrame("Gestión de Almacén - Reponedor");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);
        frame.setLayout(new BorderLayout());

        JPanel sidePanel = new JPanel();
        sidePanel.setLayout(new GridLayout(5, 1));
        sidePanel.setPreferredSize(new Dimension(200, 600));

        JButton viewButton = new JButton("Consultar Almacén");
        JButton addButton = new JButton("Añadir Producto");
        JButton removeButton = new JButton("Eliminar Producto");
        JButton editPriceButton = new JButton("Editar Precio");
        JButton editQuantityButton = new JButton("Editar Cantidad");

        sidePanel.add(viewButton);
        sidePanel.add(addButton);
        sidePanel.add(removeButton);
        sidePanel.add(editPriceButton);
        sidePanel.add(editQuantityButton);

        frame.add(sidePanel, BorderLayout.WEST);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        frame.add(mainPanel, BorderLayout.CENTER);

        viewButton.addActionListener(e -> showAlmacen(mainPanel));

        addButton.addActionListener(e -> openAddProductWindow());

        removeButton.addActionListener(e -> openRemoveProductWindow());

        editPriceButton.addActionListener(e -> openEditPriceWindow());

        editQuantityButton.addActionListener(e -> openEditQuantityWindow());

        frame.setVisible(true);
    }

    private void showAlmacen(JPanel mainPanel) {
        mainPanel.removeAll();

        List<Alimento> almacen = controller.cargarAlmacen();
        if (almacen == null || almacen.isEmpty()) {
            mainPanel.add(new JLabel("No hay productos en el almacén."), BorderLayout.CENTER);
        } else {
            String[] columnNames = {"Nombre", "Precio", "Cantidad"};
            Object[][] data = new Object[almacen.size()][3];
            for (int i = 0; i < almacen.size(); i++) {
                Alimento a = almacen.get(i);
                data[i][0] = a.getNombre();
                data[i][1] = "$" + a.getPrecio();
                data[i][2] = a.getCantidad();
            }

            JTable table = new JTable(data, columnNames);
            JScrollPane scrollPane = new JScrollPane(table);
            mainPanel.add(scrollPane, BorderLayout.CENTER);
        }

        mainPanel.revalidate();
        mainPanel.repaint();
    }

    // Método para abrir la ventana de añadir producto
    private void openAddProductWindow() {
        JFrame addFrame = new JFrame("Añadir Producto");
        addFrame.setSize(400, 300);
        addFrame.setLayout(new GridLayout(4, 2));

        JTextField nameField = new JTextField();
        JTextField categoryField = new JTextField();
        JTextField priceField = new JTextField();
        JTextField quantityField = new JTextField();

        addFrame.add(new JLabel("Nombre:"));
        addFrame.add(nameField);
        addFrame.add(new JLabel("Categoria:"));
        addFrame.add(categoryField);
        addFrame.add(new JLabel("Precio:"));
        addFrame.add(priceField);
        addFrame.add(new JLabel("Cantidad:"));
        addFrame.add(quantityField);

        JButton addButton = new JButton("Añadir");
        addButton.addActionListener(e -> {
            try {
                String nombre = nameField.getText();
                String categoria = categoryField.getText();
                double precio = Double.parseDouble(priceField.getText());
                int cantidad = Integer.parseInt(quantityField.getText());
                String message = reponedor.añadirProducto(new Alimento(nombre, precio, categoria, cantidad));
                JOptionPane.showMessageDialog(addFrame, message);
                addFrame.dispose();
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(addFrame, "Precio o cantidad inválidos.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
        addFrame.add(addButton);
        addFrame.setVisible(true);
    }

    private void openRemoveProductWindow() {
        JFrame removeFrame = new JFrame("Eliminar Producto");
        removeFrame.setSize(400, 200);
        removeFrame.setLayout(new GridLayout(2, 2));

        JTextField nameField = new JTextField();
        removeFrame.add(new JLabel("Nombre del Producto:"));
        removeFrame.add(nameField);

        JButton removeButton = new JButton("Eliminar");
        removeButton.addActionListener(e -> {
            boolean success = reponedor.eliminarProducto(nameField.getText());
            JOptionPane.showMessageDialog(removeFrame,
                    success ? "Producto eliminado correctamente." : "No se pudo eliminar el producto.");
            removeFrame.dispose();
        });

        removeFrame.add(removeButton);
        removeFrame.setVisible(true);
    }

    // Método para abrir la ventana de editar precio
    private void openEditPriceWindow() {
        JFrame editFrame = new JFrame("Editar Precio");
        editFrame.setSize(400, 200);
        editFrame.setLayout(new GridLayout(3, 2));

        JTextField nameField = new JTextField();
        JTextField priceField = new JTextField();

        editFrame.add(new JLabel("Nombre del Producto:"));
        editFrame.add(nameField);
        editFrame.add(new JLabel("Nuevo Precio:"));
        editFrame.add(priceField);

        JButton editButton = new JButton("Editar");
        editButton.addActionListener(e -> {
            try {
                String nombre = nameField.getText();
                double nuevoPrecio = Double.parseDouble(priceField.getText());
                boolean success = reponedor.editarProductoPrecio(nombre, nuevoPrecio);
                JOptionPane.showMessageDialog(editFrame,
                        success ? "Precio editado correctamente." : "No se pudo editar el precio.");
                editFrame.dispose();
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(editFrame, "Precio inválido.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        editFrame.add(editButton);
        editFrame.setVisible(true);
    }
    private void openEditQuantityWindow() {
        JFrame editFrame = new JFrame("Editar Cantidad");
        editFrame.setSize(400, 200);
        editFrame.setLayout(new GridLayout(3, 2));

        JTextField nameField = new JTextField();
        JTextField quantityField = new JTextField();

        editFrame.add(new JLabel("Nombre del Producto:"));
        editFrame.add(nameField);
        editFrame.add(new JLabel("Nueva Cantidad:"));
        editFrame.add(quantityField);

        JButton editButton = new JButton("Editar");
        editButton.addActionListener(e -> {
            try {
                String nombre = nameField.getText();
                int nuevaCantidad = Integer.parseInt(quantityField.getText());
                boolean success = reponedor.editarProductoCantidad(nombre, nuevaCantidad);
                JOptionPane.showMessageDialog(editFrame,
                        success ? "Cantidad editada correctamente." : "No se pudo editar la cantidad.");
                editFrame.dispose();
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(editFrame, "Cantidad inválida.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
        editFrame.add(editButton);
        editFrame.setVisible(true);
    }
}


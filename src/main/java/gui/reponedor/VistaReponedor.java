/**
 * Representa la interfaz gráfica para un usuario reponedor que gestiona el inventario de productos
 * Permite visualizar, agregar, editar y eliminar productos del inventario
 */
package gui.reponedor;

import inventoryChef.Alimento;
import inventoryChef.Reponedor;
import controller.TodosController;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class VistaReponedor extends JFrame {

    /**
     * Instancia del usuario reponedor asociado a la vista
     */
    private Reponedor reponedor;

    /**
     * Marco principal de la ventana
     */
    private JFrame mainFrame;

    /**
     * Tabla para visualizar los productos
     */
    private JTable productTable;

    /**
     * Modelo de datos para la tabla de productos
     */
    private DefaultTableModel tableModel;

    /**
     * Controlador para gestionar las operaciones del inventario
     */
    private TodosController controller;

    /**
     * Constructor de la clase VistaReponedor
     *
     * @param reponedor Instancia del reponedor que utiliza esta vista
     */
    public VistaReponedor(Reponedor reponedor) {
        this.reponedor = reponedor;
        this.controller = new TodosController();
        iniciar();
    }

    /**
     * Inicializa los componentes de la interfaz gráfica.
     */
    private void iniciar() {
        mainFrame = new JFrame("Bienvenido Reponedor");
        mainFrame.setSize(800, 600);
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Panel superior con título y botón para agregar productos.
        JPanel topPanel = new JPanel(new BorderLayout());
        JLabel titleLabel = new JLabel("Gestión de Inventario", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        topPanel.add(titleLabel, BorderLayout.CENTER);

        JButton addButton = new JButton("+");
        addButton.setFont(new Font("Arial", Font.BOLD, 16));
        addButton.addActionListener(e -> openAddProductWindow());
        topPanel.add(addButton, BorderLayout.EAST);

        mainFrame.add(topPanel, BorderLayout.NORTH);

        // Configuración de la tabla de productos.
        tableModel = new DefaultTableModel(new Object[]{"Seleccionar", "Nombre", "Cantidad", "Precio"}, 0);
        productTable = new JTable(tableModel) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 0;
            }

            @Override
            public Class<?> getColumnClass(int column) {
                return column == 0 ? Boolean.class : String.class;
            }
        };
        JScrollPane tableScrollPane = new JScrollPane(productTable);
        mainFrame.add(tableScrollPane, BorderLayout.CENTER);

        JButton viewDetailsButton = new JButton("Ver Detalles");
        viewDetailsButton.addActionListener(e -> openProductDetailsWindow());
        mainFrame.add(viewDetailsButton, BorderLayout.SOUTH);

        cargarDataProductos();
        mainFrame.setVisible(true);
    }

    /**
     * Carga los datos de los productos desde el controlador a la tabla.
     */
    private void cargarDataProductos() {
        List<Alimento> productos = controller.cargarAlmacen();
        if (productos != null) {
            tableModel.setRowCount(0);
            for (Alimento producto : productos) {
                tableModel.addRow(new Object[]{false, producto.getNombre(), producto.getCantidad(), producto.getPrecio()});
            }
        }
    }

    /**
     * Abre una ventana para mostrar los detalles de un producto seleccionado.
     */
    private void openProductDetailsWindow() {
        int selectedRow = getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(mainFrame, "Seleccione un producto primero.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String productName = (String) tableModel.getValueAt(selectedRow, 1);
        Alimento producto = controller.buscarProductoPorNombre(productName);
        if (producto == null) {
            JOptionPane.showMessageDialog(mainFrame, "Producto no encontrado.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        JFrame detailsFrame = new JFrame("Detalles del Producto");
        detailsFrame.setSize(400, 300);
        detailsFrame.setLayout(new GridLayout(5, 2));

        detailsFrame.add(new JLabel("Nombre:"));
        detailsFrame.add(new JLabel(producto.getNombre()));

        detailsFrame.add(new JLabel("Cantidad:"));
        detailsFrame.add(new JLabel(String.valueOf(producto.getCantidad())));

        detailsFrame.add(new JLabel("Precio:"));
        detailsFrame.add(new JLabel(String.valueOf(producto.getPrecio())));

        JButton editButton = new JButton("Editar");
        editButton.addActionListener(e -> openEditProductWindow(producto));
        detailsFrame.add(editButton);

        JButton deleteButton = new JButton("Eliminar");
        deleteButton.addActionListener(e -> {
            reponedor.eliminarProducto(producto.getNombre());
            detailsFrame.dispose();
            cargarDataProductos();
        });
        detailsFrame.add(deleteButton);

        detailsFrame.setVisible(true);
    }

    /**
     * Abre una ventana para editar un producto seleccionado.
     *
     * @param producto Producto a editar.
     */
    private void openEditProductWindow(Alimento producto) {
        JFrame editFrame = new JFrame("Editar Producto");
        editFrame.setSize(400, 300);
        editFrame.setLayout(new GridLayout(4, 2));

        JTextField quantityField = new JTextField(String.valueOf(producto.getCantidad()));
        JTextField priceField = new JTextField(String.valueOf(producto.getPrecio()));

        editFrame.add(new JLabel("Cantidad:"));
        editFrame.add(quantityField);

        editFrame.add(new JLabel("Precio:"));
        editFrame.add(priceField);

        JButton saveButton = new JButton("Guardar");
        saveButton.addActionListener(e -> {
            try {
                int nuevaCantidad = Integer.parseInt(quantityField.getText());
                double nuevoPrecio = Double.parseDouble(priceField.getText());
                reponedor.editarProductoCantidad(producto.getNombre(), nuevaCantidad);
                reponedor.editarProductoPrecio(producto.getNombre(), nuevoPrecio);
                editFrame.dispose();
                cargarDataProductos();
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(editFrame, "Cantidad o precio no válidos.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
        editFrame.add(saveButton);

        editFrame.setVisible(true);
    }

    /**
     * Abre una ventana para agregar un nuevo producto al inventario.
     */
    private void openAddProductWindow() {
        JFrame addFrame = new JFrame("Agregar Producto");
        addFrame.setSize(400, 300);
        addFrame.setLayout(new GridLayout(4, 2));

        JTextField nameField = new JTextField();
        JTextField categoryField = new JTextField();
        JTextField quantityField = new JTextField();
        JTextField priceField = new JTextField();

        addFrame.add(new JLabel("Nombre:"));
        addFrame.add(nameField);

        addFrame.add(new JLabel("Categoria:"));
        addFrame.add(categoryField);

        addFrame.add(new JLabel("Cantidad:"));
        addFrame.add(quantityField);

        addFrame.add(new JLabel("Precio:"));
        addFrame.add(priceField);

        JButton addButton = new JButton("Agregar");
        addButton.addActionListener(e -> {
            try {
                String nombre = nameField.getText();
                String categoria = categoryField.getText();
                int cantidad = Integer.parseInt(quantityField.getText());
                double precio = Double.parseDouble(priceField.getText());

                if (nombre.isEmpty()) {
                    throw new IllegalArgumentException("El nombre es obligatorio.");
                }
                reponedor.añadirProducto(new Alimento(nombre, precio, categoria, cantidad));
                addFrame.dispose();
                cargarDataProductos();

            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(addFrame, "Cantidad o precio no válidos.", "Error", JOptionPane.ERROR_MESSAGE);
            } catch (IllegalArgumentException ex) {
                JOptionPane.showMessageDialog(addFrame, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        addFrame.add(new JLabel());
        addFrame.add(addButton);

        addFrame.setVisible(true);
    }

    /**
     * Obtiene el índice de la fila seleccionada en la tabla.
     *
     * @return Índice de la fila seleccionada o -1 si no hay ninguna seleccionada.
     */
    private int getSelectedRow() {
        for (int i = 0; i < productTable.getRowCount(); i++) {
            if ((boolean) productTable.getValueAt(i, 0)) {
                return i;
            }
        }
        return -1;
    }
}
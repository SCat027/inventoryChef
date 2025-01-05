package gui.admin;

import javax.swing.*;
import controller.TodosController;
import inventoryChef.Admin;
import inventoryChef.Usuario;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

/**
 * VistaAdmin es una clase que proporciona una interfaz gráfica para que un administrador pueda gestionar usuarios,
 * ver detalles, editar, eliminar y crear nuevos usuarios.
 */
public class VistaAdmin extends JFrame {

    private Admin admin;
    private JFrame mainFrame;
    private JTable userTable;
    private DefaultTableModel tableModel;
    private TodosController controller;

    /**
     * Constructor de VistaAdmin.
     * Inicializa la vista principal para el administrador.
     * @param admin Instancia de la clase Admin que contiene las operaciones del administrador.
     */
    public VistaAdmin(Admin admin) {
        this.admin = admin;
        this.controller = new TodosController();
        iniciar();
    }

    /**
     * Configura e inicializa los componentes gráficos de la ventana principal.
     */
    private void iniciar() {
        mainFrame = new JFrame("Bienvenido Admin");
        mainFrame.setSize(800, 600);
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel topPanel = new JPanel(new BorderLayout());
        JLabel titleLabel = new JLabel("Bienvenido Admin", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        topPanel.add(titleLabel, BorderLayout.CENTER);

        JButton addButton = new JButton("+");
        addButton.setFont(new Font("Arial", Font.BOLD, 16));
        addButton.addActionListener(e -> openCreateUserWindow());
        topPanel.add(addButton, BorderLayout.EAST);

        mainFrame.add(topPanel, BorderLayout.NORTH);

        tableModel = new DefaultTableModel(new Object[]{"Seleccionar", "Nombre", "Rol"}, 0);
        userTable = new JTable(tableModel) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 0;
            }

            @Override
            public Class<?> getColumnClass(int column) {
                return column == 0 ? Boolean.class : String.class;
            }
        };
        JScrollPane tableScrollPane = new JScrollPane(userTable);
        mainFrame.add(tableScrollPane, BorderLayout.CENTER);

        JButton viewDetailsButton = new JButton("Ver Detalles");
        viewDetailsButton.addActionListener(e -> openUserDetailsWindow());
        mainFrame.add(viewDetailsButton, BorderLayout.SOUTH);

        cargarDataUsuario();
        mainFrame.setVisible(true);
    }

    /**
     * Carga la lista de usuarios en la tabla desde el administrador.
     */
    private void cargarDataUsuario() {
        List<Usuario> usuarios = admin.cargarUsuarios();
        if (usuarios != null) {
            tableModel.setRowCount(0);
            for (Usuario user : usuarios) {
                tableModel.addRow(new Object[]{false, user.getNombre(), user.getRol()});
            }
        }
    }

    /**
     * Abre una ventana con los detalles del usuario seleccionado.
     */
    private void openUserDetailsWindow() {
        int selectedRow = getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(mainFrame, "Seleccione un usuario primero.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String userName = (String) tableModel.getValueAt(selectedRow, 1);
        Usuario usuario = admin.buscarUsuario(admin.cargarUsuarios(), userName);
        if (usuario == null) {
            JOptionPane.showMessageDialog(mainFrame, "Usuario no encontrado.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        JFrame detailsFrame = new JFrame("Detalles de Usuario");
        detailsFrame.setSize(400, 300);
        detailsFrame.setLayout(new GridLayout(5, 2));

        detailsFrame.add(new JLabel("Nombre:"));
        detailsFrame.add(new JLabel(usuario.getNombre()));

        detailsFrame.add(new JLabel("Correo:"));
        detailsFrame.add(new JLabel(usuario.getCorreo()));

        detailsFrame.add(new JLabel("Edad:"));
        detailsFrame.add(new JLabel(String.valueOf(usuario.getEdad())));

        detailsFrame.add(new JLabel("ID:"));
        detailsFrame.add(new JLabel(usuario.getId()));

        JButton editButton = new JButton("Editar");
        editButton.addActionListener(e -> openEditUserWindow(usuario));
        detailsFrame.add(editButton);

        JButton deleteButton = new JButton("Eliminar");
        deleteButton.addActionListener(e -> {
            admin.eliminarUsuario(usuario.getNombre());
            detailsFrame.dispose();
            cargarDataUsuario();
        });
        detailsFrame.add(deleteButton);

        detailsFrame.setVisible(true);
    }

    /**
     * Abre una ventana para editar los detalles de un usuario.
     * @param usuario El usuario que se desea editar.
     */
    private void openEditUserWindow(Usuario usuario) {
        JFrame editFrame = new JFrame("Editar Usuario");
        editFrame.setSize(400, 300);
        editFrame.setLayout(new GridLayout(4, 2));

        JTextField emailField = new JTextField(usuario.getCorreo());
        JTextField ageField = new JTextField(String.valueOf(usuario.getEdad()));

        editFrame.add(new JLabel("Correo:"));
        editFrame.add(emailField);

        editFrame.add(new JLabel("Edad:"));
        editFrame.add(ageField);

        JButton saveButton = new JButton("Guardar");
        saveButton.addActionListener(e -> {
            try {
                int nuevaEdad = Integer.parseInt(ageField.getText());
                admin.editarInformacionUsuario(usuario.getNombre(), emailField.getText(), nuevaEdad);
                editFrame.dispose();
                cargarDataUsuario();
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(editFrame, "Edad no válida.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
        editFrame.add(saveButton);

        editFrame.setVisible(true);
    }

    /**
     * Abre una ventana para crear un nuevo usuario.
     */
    private void openCreateUserWindow() {
        JFrame createFrame = new JFrame("Crear Usuario");
        createFrame.setSize(400, 300);
        createFrame.setLayout(new GridLayout(6, 2));

        JTextField nameField = new JTextField();
        JTextField emailField = new JTextField();
        JTextField ageField = new JTextField();
        JPasswordField passwordField = new JPasswordField();

        JComboBox<String> rolComboBox = new JComboBox<>(new String[]{"Admin", "Reponedor", "Chef"});

        createFrame.add(new JLabel("Nombre:"));
        createFrame.add(nameField);

        createFrame.add(new JLabel("Rol:"));
        createFrame.add(rolComboBox);

        createFrame.add(new JLabel("Correo:"));
        createFrame.add(emailField);

        createFrame.add(new JLabel("Edad:"));
        createFrame.add(ageField);

        createFrame.add(new JLabel("Contraseña:"));
        createFrame.add(passwordField);

        JButton createButton = new JButton("Crear");
        createButton.addActionListener(e -> {
            try {
                String nombre = nameField.getText();
                String correo = emailField.getText();
                int edad = Integer.parseInt(ageField.getText());
                String contraseña = new String(passwordField.getPassword());
                String rol = (String) rolComboBox.getSelectedItem();

                if (nombre.isEmpty() || correo.isEmpty() || contraseña.isEmpty() || rol == null) {
                    throw new IllegalArgumentException("Todos los campos son obligatorios.");
                }

                int id = controller.generarIdPorRol(rol);

                admin.crearUsuario(nombre, correo, edad, String.valueOf(id), contraseña, rol);

                createFrame.dispose();
                cargarDataUsuario();

            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(createFrame, "Edad no válida.", "Error", JOptionPane.ERROR_MESSAGE);
            } catch (IllegalArgumentException ex) {
                JOptionPane.showMessageDialog(createFrame, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        createFrame.add(new JLabel());
        createFrame.add(createButton);

        createFrame.setVisible(true);
    }

    /**
     * Obtiene el índice de la fila seleccionada en la tabla.
     * @return El índice de la fila seleccionada o -1 si no hay ninguna seleccionada.
     */
    private int getSelectedRow() {
        for (int i = 0; i < userTable.getRowCount(); i++) {
            if ((boolean) userTable.getValueAt(i, 0)) {
                return i;
            }
        }
        return -1;
    }
}
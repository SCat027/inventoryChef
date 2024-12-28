package gui.admin;

import javax.swing.*;

import controller.TodosController;
import inventoryChef.Admin;
import inventoryChef.Chef;
import inventoryChef.Reponedor;
import inventoryChef.Usuario;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class VistaAdmin extends JFrame {

    private Admin admin;
    private JFrame mainFrame;
    private JTable userTable;
    private DefaultTableModel tableModel;

    public VistaAdmin(Admin admin) {
        this.admin = admin;
        initialize();
    }

    private void initialize() {
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
                return column == 0; // Solo permitir editar la columna "Seleccionar"
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

        loadUserData();
        mainFrame.setVisible(true);
    }

    private void loadUserData() {
        List<Usuario> usuarios = admin.cargarUsuarios();
        if (usuarios != null) {
            tableModel.setRowCount(0);
            for (Usuario user : usuarios) {
                tableModel.addRow(new Object[]{false, user.getNombre(), user.getRol()});
            }
        }
    }

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
            loadUserData();
        });
        detailsFrame.add(deleteButton);

        detailsFrame.setVisible(true);
    }

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
                loadUserData();
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(editFrame, "Edad no válida.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
        editFrame.add(saveButton);

        editFrame.setVisible(true);
    }

    private void openCreateUserWindow() {
        JFrame createFrame = new JFrame("Crear Usuario");
        createFrame.setSize(400, 300);
        createFrame.setLayout(new GridLayout(6, 2));

        JTextField nameField = new JTextField();
        JTextField rolField = new JTextField();
        JTextField emailField = new JTextField();
        JTextField ageField = new JTextField();
        JTextField idField = new JTextField();
        JPasswordField passwordField = new JPasswordField();

        createFrame.add(new JLabel("Nombre:"));
        createFrame.add(nameField);

        createFrame.add(new JLabel("Rol:"));
        createFrame.add(rolField);

        createFrame.add(new JLabel("Correo:"));
        createFrame.add(emailField);

        createFrame.add(new JLabel("Edad:"));
        createFrame.add(ageField);

        createFrame.add(new JLabel("ID:"));
        createFrame.add(idField);

        createFrame.add(new JLabel("Contraseña:"));
        createFrame.add(passwordField);

        JButton createButton = new JButton("Crear");
        createButton.addActionListener(e -> {
            try {
                int edad = Integer.parseInt(ageField.getText());
                admin.crearUsuario(nameField.getText(), emailField.getText(), edad, idField.getText(), new String(passwordField.getPassword()),rolField.getText());
                createFrame.dispose();
                loadUserData();
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(createFrame, "Edad no válida.", "Error", JOptionPane.ERROR_MESSAGE);
            } catch (IllegalArgumentException ex) {
                JOptionPane.showMessageDialog(createFrame, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
        createFrame.add(createButton);

        createFrame.setVisible(true);
    }

    private int getSelectedRow() {
        for (int i = 0; i < userTable.getRowCount(); i++) {
            if ((boolean) userTable.getValueAt(i, 0)) {
                return i;
            }
        }
        return -1;
    }

    public static void main(String[] args) {
        Admin admin = new Admin("Admin", "admin@admin.com", 30, "admin", "1234");
        new VistaAdmin(admin);
    }
}


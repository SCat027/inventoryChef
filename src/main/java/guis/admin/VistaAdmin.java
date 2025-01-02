package guis.admin;

import controller.AdminController;
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

public class VistaAdmin extends JFrame {
    private JTable table;
    private DefaultTableModel tableModel;
    private AdminController adminController;

    public VistaAdmin(Admin admin) {
        adminController = new AdminController(admin); // Inicializa el Controller
        setTitle("Gestión de Usuarios");
        setSize(800, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Panel lateral (menú)
        JPanel menuPanel = new JPanel();
        menuPanel.setLayout(new BoxLayout(menuPanel, BoxLayout.Y_AXIS));
        menuPanel.setPreferredSize(new Dimension(150, getHeight()));
        menuPanel.setBackground(new Color(200, 200, 200));

        JLabel menuLabel = new JLabel("Menú");
        menuLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        menuLabel.setFont(new Font("Arial", Font.BOLD, 16));
        menuPanel.add(menuLabel);

        // Botón para agregar usuario
        JButton btnAgregarUsuario = new JButton("Agregar Usuario");
        btnAgregarUsuario.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnAgregarUsuario.setMaximumSize(new Dimension(130, 40));
        menuPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        menuPanel.add(btnAgregarUsuario);

        add(menuPanel, BorderLayout.WEST);

        // Panel principal (tabla de usuarios)
        tableModel = new DefaultTableModel(new String[]{"Nombre", "Rol"}, 0);
        table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);

        // Cargar usuarios desde el controller
        cargarUsuarios();

        // Acción para agregar usuario
        btnAgregarUsuario.addActionListener(e -> abrirAgregarUsuario());

        // Acción al hacer clic en una fila de la tabla
        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int selectedRow = table.getSelectedRow();
                if (selectedRow != -1) {
                    Usuario usuario = adminController.getUsuarios().get(selectedRow);
                    abrirDetalleUsuario(usuario);
                }
            }
        });
    }

    // Método para cargar usuarios desde el controller
    private void cargarUsuarios() {
        tableModel.setRowCount(0); // Limpia la tabla
        List<Usuario> usuarios = adminController.getUsuarios();
        for (Usuario usuario : usuarios) {
            tableModel.addRow(new Object[]{usuario.getNombre(), usuario.getRol()});
        }
    }

    // Método para abrir la ventana de agregar usuario
    private void abrirAgregarUsuario() {
        JFrame frame = new JFrame("Agregar Usuario");
        frame.setSize(300, 250);
        frame.setLayout(new GridLayout(5, 2, 10, 10));
        frame.setLocationRelativeTo(this);

        JLabel lblNombre = new JLabel("Nombre:");
        JTextField txtNombre = new JTextField();

        JLabel lblEdad = new JLabel("Nombre:");
        JTextField txtEdad = new JTextField();

        JLabel lblCorreo = new JLabel("Correo:");
        JTextField txtCorreo = new JTextField();

        JLabel lblRol = new JLabel("Rol:");
        JTextField txtRol = new JTextField();

        JLabel lblContraseña = new JLabel("Contraseña:");
        JTextField txtContraseña = new JTextField();

        JButton btnGuardar = new JButton("Guardar");

        frame.add(lblNombre);
        frame.add(txtNombre);
        frame.add(lblEdad);
        frame.add(txtEdad);
        frame.add(lblCorreo);
        frame.add(txtCorreo);
        frame.add(lblRol);
        frame.add(txtRol);
        frame.add(lblContraseña);
        frame.add(txtContraseña);
        frame.add(new JLabel());
        frame.add(btnGuardar);

        btnGuardar.addActionListener(e -> {
            String nombre = txtNombre.getText();
            int edad = Integer.parseInt(txtEdad.getText());
            String correo = txtCorreo.getText();
            String rol = txtRol.getText();
            String contraseña = txtContraseña.getText();

            if (!nombre.isEmpty() && !correo.isEmpty() && !rol.isEmpty() && !contraseña.isEmpty()) {

                switch (rol) {
                    case "Chef" -> {
                        int id = adminController.getUsuarios().size() + 1000;
                        Chef chef = new Chef(nombre, correo, edad, contraseña, id);
                        adminController.addUsuario(chef);
                        cargarUsuarios();
                        frame.dispose();
                    }
                    case "Reponedor" -> {
                        int id = adminController.getUsuarios().size() + 2000;
                        Reponedor reponedor = new Reponedor(nombre, correo, edad, contraseña, id);
                        adminController.addUsuario(reponedor);
                        cargarUsuarios();
                        frame.dispose();
                    }
                    case "Admin" -> {
                        int id = adminController.getUsuarios().size() + 3000;
                        Admin admin = new Admin(nombre, correo, edad, contraseña, id);
                        adminController.addUsuario(admin);
                        cargarUsuarios();
                        frame.dispose();
                    }
                }
            } else {
                JOptionPane.showMessageDialog(frame, "Todos los campos son obligatorios", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        frame.setVisible(true);
    }

    // Método para abrir la ventana con detalles del usuario
    private void abrirDetalleUsuario(Usuario usuario) {
        JFrame frame = new JFrame("Detalle de Usuario");
        frame.setSize(300, 200);
        frame.setLayout(new GridLayout(4, 1, 10, 10));
        frame.setLocationRelativeTo(this);

        JLabel lblId = new JLabel("Id: " + usuario.getId());
        JLabel lblNombre = new JLabel("Nombre: " + usuario.getNombre());
        JLabel lblEdad = new JLabel("Edad: " + usuario.getEdad());
        JLabel lblCorreo = new JLabel("Correo: " + usuario.getCorreo());
        JLabel lblRol = new JLabel("Rol: " + usuario.getRol());
        JLabel lblContraseña = new JLabel("Contraseña: " + usuario.getClave());

        frame.add(lblId);
        frame.add(lblNombre);
        frame.add(lblEdad);
        frame.add(lblCorreo);
        frame.add(lblRol);
        frame.add(lblContraseña);

        frame.setVisible(true);
    }

    public static void main(String[] args) {

    }
}



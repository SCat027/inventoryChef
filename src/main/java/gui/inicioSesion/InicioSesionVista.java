package gui.inicioSesion;

import controller.TodosController;
import gui.admin.VistaAdmin;
import gui.chef.VistaChef;
import gui.reponedor.VistaReponedor;
import inventoryChef.Admin;
import inventoryChef.Chef;
import inventoryChef.Reponedor;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Clase que representa la ventana de inicio de sesión para diferentes tipos de usuarios
 * (Chef, Admin y Reponedor) en un sistema de gestión de inventarios
 */
public class InicioSesionVista extends JFrame {

    /**
     * Campo de texto para ingresar el nombre de usuario
     */
    private JTextField usernameField;

    /**
     * Campo de contraseña para ingresar la contraseña del usuario
     */
    private JPasswordField passwordField;

    /**
     * Botón para iniciar sesión
     */
    private JButton loginButton;

    /**
     * Constructor que inicializa la ventana de inicio de sesión
     */
    public InicioSesionVista() {
        setTitle("Inicio de Sesión");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(null);

        // Etiqueta del título principal
        JLabel titleLabel = new JLabel("IC", SwingConstants.CENTER);
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setFont(new Font("Arial", Font.PLAIN, 30));
        titleLabel.setBounds(150, 20, 100, 40);
        add(titleLabel);

        // Campo de texto para el nombre de usuario
        usernameField = new JTextField("Usuario");
        usernameField.setBounds(100, 80, 200, 30);
        usernameField.setForeground(Color.GRAY);
        usernameField.addFocusListener(new java.awt.event.FocusAdapter() {
            @Override
            public void focusGained(java.awt.event.FocusEvent e) {
                if (usernameField.getText().equals("Usuario")) {
                    usernameField.setText("");
                    usernameField.setForeground(Color.BLACK);
                }
            }

            @Override
            public void focusLost(java.awt.event.FocusEvent e) {
                if (usernameField.getText().isEmpty()) {
                    usernameField.setText("Usuario");
                    usernameField.setForeground(Color.GRAY);
                }
            }
        });
        add(usernameField);

        // Campo de contraseña
        passwordField = new JPasswordField("Contraseña");
        passwordField.setEchoChar((char) 0);
        passwordField.setBounds(100, 120, 200, 30);
        passwordField.setForeground(Color.GRAY);
        passwordField.addFocusListener(new java.awt.event.FocusAdapter() {
            @Override
            public void focusGained(java.awt.event.FocusEvent e) {
                if (new String(passwordField.getPassword()).equals("Contraseña")) {
                    passwordField.setText("");
                    passwordField.setEchoChar('•');
                    passwordField.setForeground(Color.BLACK);
                }
            }

            @Override
            public void focusLost(java.awt.event.FocusEvent e) {
                if (new String(passwordField.getPassword()).isEmpty()) {
                    passwordField.setText("Contraseña");
                    passwordField.setEchoChar((char) 0);
                    passwordField.setForeground(Color.GRAY);
                }
            }
        });
        add(passwordField);

        // Botón de inicio de sesión
        loginButton = new JButton("Iniciar");
        loginButton.setBounds(150, 170, 100, 30);
        loginButton.setBackground(new Color(240, 240, 200));
        add(loginButton);

        // Listener para gestionar el evento de inicio de sesión
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                TodosController controller = new TodosController();
                String username = usernameField.getText();
                String password = new String(passwordField.getPassword());

                // Verificar las credenciales y redirigir a la vista correspondiente
                String rol = controller.autenticarRol(controller.validarInicio(username, password));
                switch (rol) {
                    case "Chef":
                        JOptionPane.showMessageDialog(InicioSesionVista.this, "Inicio de sesión exitoso");
                        SwingUtilities.invokeLater(() -> {
                            VistaChef inventarioChef = new VistaChef((Chef) controller.validarInicio(username, password));
                            inventarioChef.setVisible(true);
                        });
                        dispose();
                        break;

                    case "Admin":
                        JOptionPane.showMessageDialog(InicioSesionVista.this, "Inicio de sesión exitoso");
                        SwingUtilities.invokeLater(() -> {
                            VistaAdmin inventarioChef = new VistaAdmin((Admin) controller.validarInicio(username, password));
                            inventarioChef.setVisible(true);
                        });
                        dispose();
                        break;

                    case "Reponedor":
                        JOptionPane.showMessageDialog(InicioSesionVista.this, "Inicio de sesión exitoso");
                        SwingUtilities.invokeLater(() -> {
                            VistaReponedor inventarioChef = new VistaReponedor((Reponedor) controller.validarInicio(username, password));
                            inventarioChef.setVisible(true);
                        });
                        dispose();
                        break;

                    default:
                        JOptionPane.showMessageDialog(InicioSesionVista.this, "Credenciales inválidas", "Error", JOptionPane.ERROR_MESSAGE);
                        break;
                }
            }
        });

        // Configuración de fondo y comportamiento
        getContentPane().setBackground(new Color(173, 216, 230));
        getContentPane().addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {
                getContentPane().requestFocusInWindow();
            }
        });
    }

    /**
     * Metodo principal para ejecutar la ventana de inicio de sesión
     *
     * @param args Argumentos de la línea de comandos
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            InicioSesionVista loginGUI = new InicioSesionVista();
            loginGUI.setVisible(true);
        });
    }
}
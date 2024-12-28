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

public class InicioSesionVista extends JFrame {

    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginButton;

    public InicioSesionVista() {
        setTitle("Inicio de Sesión");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(null);

        JLabel titleLabel = new JLabel("IC", SwingConstants.CENTER);
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setFont(new Font("Arial",Font.PLAIN, 30));
        titleLabel.setBounds(150, 20, 100, 40);
        add(titleLabel);

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

        passwordField = new JPasswordField("Contraseña");
        passwordField.setEchoChar((char) 0);
        passwordField.setBounds(100, 120, 200, 30);
        passwordField.setForeground(Color.GRAY);
        passwordField.addFocusListener(new java.awt.event.FocusAdapter() {
            @Override
            public void focusGained(java.awt.event.FocusEvent e) {
                if (new String(passwordField.getPassword()).equals("Contraseña")) {
                    passwordField.setText("");
                    passwordField.setEchoChar('\u2022');
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

        loginButton = new JButton("Iniciar");
        loginButton.setBounds(150, 170, 100, 30);
        loginButton.setBackground(new Color(240, 240, 200));
        add(loginButton);

        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                TodosController controller = new TodosController();
                String username = usernameField.getText();
                String password = new String(passwordField.getPassword());
                if (controller.autenticarRol(controller.validarInicio(username, password)).equals("Chef")) {
                    JOptionPane.showMessageDialog(InicioSesionVista.this, "Inicio de sesión exitoso");
                    SwingUtilities.invokeLater(() -> {
                        VistaChef inventarioChef = new VistaChef((Chef) controller.validarInicio(username, password));
                        inventarioChef.setVisible(true);
                    });
                    dispose();
                } else if (controller.autenticarRol(controller.validarInicio(username, password)).equals("Admin")) {
                    JOptionPane.showMessageDialog(InicioSesionVista.this, "Inicio de sesión exitoso");
                    SwingUtilities.invokeLater(() -> {
                        VistaAdmin inventarioChef = new VistaAdmin((Admin) controller.validarInicio(username, password));
                        inventarioChef.setVisible(true);
                    });
                    dispose();
                } else if (controller.autenticarRol(controller.validarInicio(username, password)).equals("Reponedor")) {
                    JOptionPane.showMessageDialog(InicioSesionVista.this, "Inicio de sesión exitoso");
                    SwingUtilities.invokeLater(() -> {
                        VistaReponedor inventarioChef = new VistaReponedor((Reponedor) controller.validarInicio(username, password));
                        inventarioChef.setVisible(true);
                    });
                    dispose();

                } else {
                    JOptionPane.showMessageDialog(InicioSesionVista.this, "Credenciales inválidas", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        getContentPane().setBackground(new Color(173, 216, 230));
        getContentPane().addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {
                getContentPane().requestFocusInWindow();
            }
        });
    }
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            InicioSesionVista loginGUI = new InicioSesionVista();
            loginGUI.setVisible(true);
        });
    }
}

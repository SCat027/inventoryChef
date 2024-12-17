package guis.chef;

import org.example.models.Chef;

import javax.swing.*;
import java.awt.*;

public class VistaChef extends JFrame {

    public VistaChef(Chef chef) {
        // Configuración general de la ventana
        setTitle("Recetas - Inventario Chef");
        setSize(1200, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // ----------- Barra Lateral (Menú) ------------
        JPanel menuPanel = new JPanel();
        menuPanel.setLayout(new GridLayout(6, 1));
        menuPanel.setBackground(new Color(220, 220, 220));
        menuPanel.setPreferredSize(new Dimension(300, getHeight()));

        JLabel titleLabel = new JLabel("Bienvenido Usuario", SwingConstants.CENTER);
        titleLabel.setFont(new Font("SansSerif", Font.PLAIN, 18));

        JButton btnVerRecetas = createMenuButton("Agregar Receta");

        menuPanel.add(titleLabel);
        menuPanel.add(btnVerRecetas);

        // ----------- Contenido Principal ------------
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(new Color(190, 215, 240));

        JLabel headerLabel = new JLabel("Recetas", SwingConstants.LEFT);
        headerLabel.setFont(new Font("SansSerif", Font.PLAIN, 50));
        headerLabel.setBorder(BorderFactory.createEmptyBorder(20, 20, 10, 0));
        mainPanel.add(headerLabel, BorderLayout.NORTH);

        // ----------- Sección Scrollable con Recetas ------------
        // Panel contenedor para los bloques de recetas
        JPanel recetasPanel = new JPanel();
        recetasPanel.setLayout(new BoxLayout(recetasPanel, BoxLayout.X_AXIS)); // Layout horizontal
        recetasPanel.setBackground(new Color(190, 215, 240));

        // Simulación de recetas (bloques blancos)
        for (int i = 0; i < 15; i++) { // 15 recetas como ejemplo
            recetasPanel.add(createPlaceholderPanel("Receta " + (i + 1)));
            recetasPanel.add(Box.createRigidArea(new Dimension(20, 0))); // Espacio entre recetas
        }

        // ScrollPane para hacer scroll horizontal
        JScrollPane scrollPane = new JScrollPane(recetasPanel, JScrollPane.VERTICAL_SCROLLBAR_NEVER,
                JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        scrollPane.getHorizontalScrollBar().setUnitIncrement(16); // Velocidad del scroll

        mainPanel.add(scrollPane, BorderLayout.CENTER);

        // ----------- Agregar Componentes al JFrame ------------
        add(menuPanel, BorderLayout.WEST);
        add(mainPanel, BorderLayout.CENTER);
    }

    // Método para crear botones del menú
    private JButton createMenuButton(String text) {
        JButton button = new JButton(text);
        button.setFont(new Font("SansSerif", Font.PLAIN, 18));
        button.setHorizontalAlignment(SwingConstants.LEFT);
        button.setBackground(new Color(220, 220, 220));
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 10));
        return button;
    }

    // Método para crear bloques de marcador de posición (recetas)
    private JPanel createPlaceholderPanel(String title) {
        JPanel panel = new JPanel();
        panel.setPreferredSize(new Dimension(200, 300));
        panel.setBackground(new Color(255, 255, 240));
        panel.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));

        JLabel label = new JLabel(title, SwingConstants.CENTER);
        label.setFont(new Font("SansSerif", Font.PLAIN, 16));
        panel.setLayout(new BorderLayout());
        panel.add(label, BorderLayout.CENTER);

        return panel;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            VistaChef frame = new VistaChef();
            frame.setVisible(true);
        });
    }
}



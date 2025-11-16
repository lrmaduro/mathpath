
package vista;

import java.awt.CardLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class MainFrame extends JFrame {

    // El panel principal que usará CardLayout
    private JPanel mainPanel;
    private CardLayout cardLayout;

    public MainFrame() {
        setTitle("MathPath - Plataforma de Aprendizaje");
        setSize(1024, 768); // Un tamaño más grande para el dashboard
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Creamos el CardLayout y el panel principal
        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);

        // Añadimos el panel principal al JFrame
        add(mainPanel);
    }

    /**
     * Añade un panel (una "tarjeta") al CardLayout.
     * @param panel El JPanel a añadir (ej. LoginView, DashboardDocenteView)
     * @param name El nombre único para esta tarjeta (ej. "LOGIN", "DOCENTE")
     */
    public void addCard(JPanel panel, String name) {
        mainPanel.add(panel, name);
    }

    /**
     * Muestra la tarjeta (panel) con el nombre especificado.
     * @param name El nombre de la tarjeta a mostrar
     */
    public void showCard(String name) {
        cardLayout.show(mainPanel, name);
    }
}

package vista;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.SwingConstants;

public class SplashFrame extends JFrame {

    private JProgressBar progressBar;
    private JLabel statusLabel;
    private final Color BG_COLOR = Color.decode("#80B6FF");

    public SplashFrame() {
        setUndecorated(true);
        setSize(400, 250);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setBackground(BG_COLOR);

        // Main Panel with rounded border look (simulated via painting or just clean
        // layout)
        JPanel mainPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                // Background
                g2d.setColor(BG_COLOR);
                g2d.fillRect(0, 0, getWidth(), getHeight());

                // Optional: Draw a border
                g2d.setColor(new Color(200, 200, 200));
                g2d.drawRect(0, 0, getWidth() - 1, getHeight() - 1);
            }
        };
        mainPanel.setLayout(new BorderLayout());
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Title
        JLabel titleLabel = new JLabel("", SwingConstants.CENTER);

        // Cargar y redimensionar logo
        java.net.URL logoUrl = getClass().getResource("/img/mathpath-logo.png");
        if (logoUrl != null) {
            ImageIcon icon = new ImageIcon(logoUrl);
            java.awt.Image img = icon.getImage().getScaledInstance(150, 150, java.awt.Image.SCALE_SMOOTH);
            titleLabel.setIcon(new ImageIcon(img));
        } else {
            // Fallback text if logo not found
            titleLabel.setText("MathPath");
            titleLabel.setFont(new Font("SansSerif", Font.BOLD, 32));
        }

        titleLabel.setForeground(new Color(50, 50, 50));

        // Subtitle / Loading text
        statusLabel = new JLabel("Iniciando...", SwingConstants.CENTER);
        statusLabel.setFont(new Font("SansSerif", Font.PLAIN, 14));
        statusLabel.setForeground(new Color(100, 100, 100));
        statusLabel.setBorder(BorderFactory.createEmptyBorder(10, 0, 20, 0));

        // Progress Bar
        progressBar = new JProgressBar();
        progressBar.setIndeterminate(true);
        progressBar.setPreferredSize(new Dimension(300, 6));
        progressBar.setForeground(new Color(66, 133, 244)); // Google Blue-ish
        progressBar.setBackground(new Color(230, 230, 230));
        progressBar.setBorderPainted(false);

        // Center Panel for text
        JPanel centerPanel = new JPanel(new BorderLayout());
        centerPanel.setOpaque(false);
        centerPanel.add(titleLabel, BorderLayout.CENTER);
        centerPanel.add(statusLabel, BorderLayout.SOUTH);

        mainPanel.add(centerPanel, BorderLayout.CENTER);
        mainPanel.add(progressBar, BorderLayout.SOUTH);

        setContentPane(mainPanel);
    }

    public void setStatus(String status) {
        statusLabel.setText(status);
    }
}

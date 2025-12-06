package vista;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import java.net.URL;

public class LoginView extends JPanel {

    private JTextField txtUsuario;
    private JPasswordField txtPassword;
    private JButton btnLogin;

    // NUEVO BOTÓN
    private JButton btnRegistro;

    // Colores
    private final Color COLOR_CARD = Color.WHITE; // Blanco (Tarjeta)
    private final Color COLOR_BTN_LOGIN = new Color(52, 152, 219); // Azul Brillante
    private final Color COLOR_TEXT_LINK = new Color(41, 128, 185); // Azul oscuro para el link
    private final Image backgroundImage;

    public LoginView() {
        // 1. Configuración del Panel Principal (El fondo oscuro)
        this.setLayout(new GridBagLayout());
        this.backgroundImage = cargarImagen("Background.png", 1, 1).getImage();

        // esto va de primer parametro dentro de
        // loginCard.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(new
        // Color(0, 0, 0, 50), 1)

        // 2. Crear la "Tarjeta" Central (El cuadro blanco)
        JPanel loginCard = new PanelRedondo(50);
        loginCard.setBorder(new BordeRedondeado(50));
        loginCard.setLayout(new GridBagLayout());
        loginCard.setBackground(COLOR_CARD);
        // // Borde compuesto: Línea sutil + Espacio interno (padding)
        // loginCard.setBorder(BorderFactory.createCompoundBorder(
        // new BordeRedondeado(30), // Borde sutil
        // new EmptyBorder(40, 50, 40, 50) // Margen interno generoso
        // ));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10); // Espacio entre elementos
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // --- TÍTULO ---
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        JLabel lblTitulo = new JLabel("Bienvenido");
        lblTitulo.setFont(new Font("SansSerif", Font.BOLD, 24));
        lblTitulo.setForeground(new Color(44, 62, 80));
        lblTitulo.setHorizontalAlignment(JLabel.CENTER);
        loginCard.add(lblTitulo, gbc);

        // --- USUARIO ---
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        JLabel lblUser = new JLabel("Usuario:");
        lblUser.setFont(new Font("SansSerif", Font.BOLD, 12));
        loginCard.add(lblUser, gbc);

        gbc.gridx = 1;
        txtUsuario = new JTextField(18);
        estilarInput(txtUsuario);
        loginCard.add(txtUsuario, gbc);

        // --- CONTRASEÑA ---
        gbc.gridx = 0;
        gbc.gridy = 2;
        JLabel lblPass = new JLabel("Contraseña:");
        lblPass.setFont(new Font("SansSerif", Font.BOLD, 12));
        loginCard.add(lblPass, gbc);

        gbc.gridx = 1;
        txtPassword = new JPasswordField(18);
        estilarInput(txtPassword);
        loginCard.add(txtPassword, gbc);

        // --- BOTÓN INGRESAR ---
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(25, 10, 10, 10); // Más espacio arriba del botón

        btnLogin = new JButton("INGRESAR AL SISTEMA");
        estilarBotonPrincipal(btnLogin);
        loginCard.add(btnLogin, gbc);

        // --- BOTÓN / ENLACE REGISTRO (NUEVO) ---
        gbc.gridy = 4;
        gbc.insets = new Insets(5, 10, 5, 10);

        btnRegistro = new JButton("¿No tienes cuenta? Regístrate aquí");
        btnRegistro.setFont(new Font("SansSerif", Font.PLAIN, 12));
        btnRegistro.setForeground(COLOR_TEXT_LINK);
        btnRegistro.setBorderPainted(false);
        btnRegistro.setContentAreaFilled(false); // Transparente
        btnRegistro.setFocusPainted(false);
        btnRegistro.setCursor(new Cursor(Cursor.HAND_CURSOR));

        // Efecto hover simple (subrayado simulado o cambio de color)
        btnRegistro.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnRegistro.setForeground(COLOR_BTN_LOGIN);
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnRegistro.setForeground(COLOR_TEXT_LINK);
            }
        });

        loginCard.add(btnRegistro, gbc);

        // Cargar logo en el background
        JLabel logo = new JLabel();
        logo.setIcon(cargarImagen("mathpath-logo.png", 0.7, 0.7));
        logo.setHorizontalAlignment(JLabel.CENTER);
        logo.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 50));
        this.add(logo);

        // 3. Añadir la tarjeta al panel principal (que las centra automáticamente por
        // el GridBagLayout)
        this.add(loginCard);
    }

    // Cargar Imagen Logo
    private ImageIcon cargarImagen(String archivo, double pctX, double pctY) {
        String ruta = "/img/" + archivo;
        URL imgURL = getClass().getResource(ruta);

        if (imgURL != null) {
            ImageIcon icono = new ImageIcon(imgURL);
            Image img = icono.getImage().getScaledInstance((int) Math.round(icono.getIconWidth() * pctX),
                    (int) Math.round(icono.getIconHeight() * pctY), Image.SCALE_SMOOTH);
            return new ImageIcon(img);
        } else {
            System.err.println("No se encontró la imagen: " + ruta);
            return null;
        }

    }

    // --- Estilos Auxiliares ---
    private void estilarInput(JTextField txt) {
        txt.setFont(new Font("SansSerif", Font.PLAIN, 14));
        // Padding interno para que el texto no pegue con el borde
        txt.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.LIGHT_GRAY),
                new EmptyBorder(5, 8, 5, 8)));
    }

    private void estilarBotonPrincipal(JButton btn) {
        btn.setBackground(COLOR_BTN_LOGIN);
        btn.setForeground(Color.WHITE);
        btn.setFont(new Font("SansSerif", Font.BOLD, 14));
        btn.setFocusPainted(false);
        // btn.setBorder(new EmptyBorder(12, 40, 12, 40)); // Botón grande
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
    }

    // --- Métodos para el Controlador ---
    public String getUsuario() {
        return txtUsuario.getText();
    }

    public String getPassword() {
        return new String(txtPassword.getPassword());
    }

    public JTextField getTxtUsuario() {
        return txtUsuario;
    }

    public JPasswordField getTxtPassword() {
        return txtPassword;
    }

    public void addLoginListener(ActionListener listener) {
        btnLogin.addActionListener(listener);
    }

    // ¡IMPORTANTE! Este es el nuevo método que necesita el LoginController
    public void addRegistroListener(ActionListener listener) {
        btnRegistro.addActionListener(listener);
    }

    public void mostrarError(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje, "Error de Acceso", JOptionPane.ERROR_MESSAGE);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g); // Clears the panel for redrawing
        if (backgroundImage != null) {
            int imgW = backgroundImage.getWidth(this);
            int imgH = backgroundImage.getHeight(this);

            if (imgW > 0 && imgH > 0) {
                int panelW = getWidth();
                int panelH = getHeight();

                // Scale to COVER the panel (Aspect Fill)
                double scale = Math.max((double) panelW / imgW, (double) panelH / imgH);

                int newW = (int) (imgW * scale);
                int newH = (int) (imgH * scale);

                // Center the image
                int x = (panelW - newW) / 2;
                int y = (panelH - newH) / 2;

                g.drawImage(backgroundImage, x, y, newW, newH, this);
            }
        }
    }
}

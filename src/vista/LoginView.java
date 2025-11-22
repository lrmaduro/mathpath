package vista;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

public class LoginView extends JPanel {

    private JTextField txtUsuario;
    private JPasswordField txtPassword;
    private JButton btnLogin;
    
    // NUEVO BOTÓN
    private JButton btnRegistro;

    // Colores
    private final Color COLOR_FONDO = new Color(44, 62, 80); // Azul Oscuro (Fondo pantalla)
    private final Color COLOR_CARD = Color.WHITE;            // Blanco (Tarjeta)
    private final Color COLOR_BTN_LOGIN = new Color(52, 152, 219); // Azul Brillante
    private final Color COLOR_TEXT_LINK = new Color(41, 128, 185); // Azul oscuro para el link

    public LoginView() {
        // 1. Configuración del Panel Principal (El fondo oscuro)
        this.setLayout(new GridBagLayout()); 
        this.setBackground(COLOR_FONDO);
        
        // 2. Crear la "Tarjeta" Central (El cuadro blanco)
        JPanel loginCard = new JPanel(new GridBagLayout());
        loginCard.setBackground(COLOR_CARD);
        // Borde compuesto: Línea sutil + Espacio interno (padding)
        loginCard.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(0, 0, 0, 50), 1), // Borde sutil
            new EmptyBorder(40, 50, 40, 50) // Margen interno generoso
        ));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10); // Espacio entre elementos
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // --- TÍTULO ---
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2;
        JLabel lblTitulo = new JLabel("Bienvenido a MathPath");
        lblTitulo.setFont(new Font("SansSerif", Font.BOLD, 24));
        lblTitulo.setForeground(new Color(44, 62, 80));
        lblTitulo.setHorizontalAlignment(JLabel.CENTER);
        loginCard.add(lblTitulo, gbc);

        // --- USUARIO ---
        gbc.gridy = 1; gbc.gridwidth = 1;
        JLabel lblUser = new JLabel("Usuario:");
        lblUser.setFont(new Font("SansSerif", Font.BOLD, 12));
        loginCard.add(lblUser, gbc);

        gbc.gridx = 1; 
        txtUsuario = new JTextField(18);
        estilarInput(txtUsuario);
        loginCard.add(txtUsuario, gbc);

        // --- CONTRASEÑA ---
        gbc.gridx = 0; gbc.gridy = 2;
        JLabel lblPass = new JLabel("Contraseña:");
        lblPass.setFont(new Font("SansSerif", Font.BOLD, 12));
        loginCard.add(lblPass, gbc);

        gbc.gridx = 1; 
        txtPassword = new JPasswordField(18);
        estilarInput(txtPassword);
        loginCard.add(txtPassword, gbc);

        // --- BOTÓN INGRESAR ---
        gbc.gridx = 0; gbc.gridy = 3;
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

        // 3. Añadir la tarjeta al panel principal (que las centra automáticamente por el GridBagLayout)
        this.add(loginCard);
    }

    // --- Estilos Auxiliares ---
    private void estilarInput(JTextField txt) {
        txt.setFont(new Font("SansSerif", Font.PLAIN, 14));
        // Padding interno para que el texto no pegue con el borde
        txt.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(Color.LIGHT_GRAY),
            new EmptyBorder(5, 8, 5, 8)
        ));
    }

    private void estilarBotonPrincipal(JButton btn) {
        btn.setBackground(COLOR_BTN_LOGIN);
        btn.setForeground(Color.WHITE);
        btn.setFont(new Font("SansSerif", Font.BOLD, 14));
        btn.setFocusPainted(false);
        btn.setBorder(new EmptyBorder(12, 40, 12, 40)); // Botón grande
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
    }

    // --- Métodos para el Controlador ---
    public String getUsuario() { return txtUsuario.getText(); }
    public String getPassword() { return new String(txtPassword.getPassword()); }
    
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
}
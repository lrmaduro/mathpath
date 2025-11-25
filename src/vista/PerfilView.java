package vista;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.RenderingHints;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import modelo.Usuario;

public class PerfilView extends JPanel {

    private JTextField txtNombre;
    private JTextField txtUsuario;
    private JPasswordField txtPassword;
    private JButton btnGuardar;
    
    // Colores Dinámicos
    private Color colorFondo;
    private Color colorTarjeta;
    private Color colorBoton;
    private Color colorAvatar;

    public PerfilView(Usuario usuario, boolean esEstudiante) {
        // 1. Configurar Tema según el Rol
        configurarTema(esEstudiante);
        
        this.setLayout(new GridBagLayout());
        this.setBackground(colorFondo);
        
        // 2. Tarjeta Central
        JPanel card = new JPanel(new GridBagLayout());
        card.setBackground(colorTarjeta);
        card.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(0,0,0,20), 1),
            new EmptyBorder(30, 50, 40, 50)
        ));
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        
        // --- AVATAR (Círculo con iniciales) ---
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        
        AvatarPanel avatar = new AvatarPanel(usuario.getNombre());
        avatar.setPreferredSize(new Dimension(100, 100));
        card.add(avatar, gbc);
        
        // Título
        JLabel lblTitulo = new JLabel("Mi Perfil");
        lblTitulo.setFont(new Font("SansSerif", Font.BOLD, 24));
        lblTitulo.setForeground(new Color(80, 80, 80));
        lblTitulo.setHorizontalAlignment(JLabel.CENTER);
        gbc.gridy = 1;
        card.add(lblTitulo, gbc);
        
        // --- FORMULARIO ---
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.WEST;
        
        // Nombre
        gbc.gridy = 2; gbc.gridx = 0;
        card.add(crearLabel("Nombre Completo:"), gbc);
        gbc.gridx = 1;
        txtNombre = new JTextField(usuario.getNombre(), 20);
        estilarInput(txtNombre);
        card.add(txtNombre, gbc);
        
        // Usuario (Solo lectura)
        gbc.gridy = 3; gbc.gridx = 0;
        card.add(crearLabel("Usuario (Login):"), gbc);
        gbc.gridx = 1;
        txtUsuario = new JTextField(usuario.getUsuario(), 20);
        estilarInput(txtUsuario);
        txtUsuario.setEditable(false);
        txtUsuario.setBackground(new Color(240, 240, 240));
        txtUsuario.setForeground(Color.GRAY);
        card.add(txtUsuario, gbc);
        
        // Password
        gbc.gridy = 4; gbc.gridx = 0;
        card.add(crearLabel("Nueva Contraseña:"), gbc);
        gbc.gridx = 1;
        txtPassword = new JPasswordField(20);
        estilarInput(txtPassword);
        card.add(txtPassword, gbc);
        
        // Nota
        gbc.gridy = 5; gbc.gridx = 1;
        JLabel lblNota = new JLabel("<html><small>Deja la contraseña en blanco si no quieres cambiarla.</small></html>");
        lblNota.setForeground(Color.GRAY);
        card.add(lblNota, gbc);
        
        // --- BOTÓN ---
        gbc.gridy = 6; gbc.gridx = 0; gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(30, 0, 0, 0);
        
        btnGuardar = new JButton("Guardar Cambios");
        estilarBoton(btnGuardar);
        card.add(btnGuardar, gbc);
        
        this.add(card);
    }
    
    private void configurarTema(boolean esEstudiante) {
        if (esEstudiante) {
            // Tema Candy (Estudiante)
            colorFondo = new Color(232, 248, 245); // Menta suave
            colorTarjeta = Color.WHITE;
            colorBoton = new Color(245, 183, 177); // Coral
            colorAvatar = new Color(174, 214, 241); // Azulito
        } else {
            // Tema Admin Clean (Docente)
            colorFondo = new Color(236, 240, 241); // Gris
            colorTarjeta = Color.WHITE;
            colorBoton = new Color(46, 134, 193); // Azul serio
            colorAvatar = new Color(52, 73, 94);  // Azul oscuro
        }
    }
    
    // --- Componente Interno para el Avatar Redondo ---
    private class AvatarPanel extends JPanel {
        private String iniciales;
        public AvatarPanel(String nombre) {
            this.setOpaque(false);
            if (nombre != null && !nombre.isEmpty()) {
                String[] partes = nombre.split(" ");
                if (partes.length > 1) {
                    this.iniciales = (partes[0].substring(0,1) + partes[1].substring(0,1)).toUpperCase();
                } else {
                    this.iniciales = nombre.substring(0, Math.min(2, nombre.length())).toUpperCase();
                }
            } else {
                this.iniciales = "?";
            }
        }
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2 = (Graphics2D) g;
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            
            // Círculo Fondo
            g2.setColor(colorAvatar);
            g2.fillOval(0, 0, getWidth(), getHeight());
            
            // Letras
            g2.setColor(Color.WHITE);
            g2.setFont(new Font("SansSerif", Font.BOLD, 36));
            FontMetrics fm = g2.getFontMetrics();
            int x = (getWidth() - fm.stringWidth(iniciales)) / 2;
            int y = ((getHeight() - fm.getHeight()) / 2) + fm.getAscent();
            g2.drawString(iniciales, x, y);
        }
    }
    
    // --- Estilos ---
    private JLabel crearLabel(String texto) {
        JLabel lbl = new JLabel(texto);
        lbl.setFont(new Font("SansSerif", Font.BOLD, 12));
        lbl.setForeground(new Color(80, 80, 80));
        return lbl;
    }
    
    private void estilarInput(JTextField txt) {
        txt.setFont(new Font("SansSerif", Font.PLAIN, 14));
        txt.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(200, 200, 200)),
            new EmptyBorder(8, 8, 8, 8)
        ));
    }
    
    private void estilarBoton(JButton btn) {
        btn.setBackground(colorBoton);
        btn.setForeground(Color.WHITE);
        btn.setFont(new Font("SansSerif", Font.BOLD, 14));
        btn.setFocusPainted(false);
//        btn.setBorder(new EmptyBorder(12, 30, 12, 30));
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
    }
    
    // --- Getters ---
    public String getNombre() { return txtNombre.getText(); }
    public String getPassword() { return new String(txtPassword.getPassword()); }
    public void addGuardarListener(ActionListener al) { btnGuardar.addActionListener(al); }
}
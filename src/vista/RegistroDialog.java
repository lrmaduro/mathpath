package vista;

import controller.EmailService; // Importamos el servicio
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import modelo.Rol;
import modelo.Usuario;

public class RegistroDialog extends JDialog {

    // Campos
    private JTextField txtNombre;
    private JTextField txtUsuario;
    private JTextField txtEmail; // NUEVO CAMPO
    private JPasswordField txtPassword;
    
    // Selección de Rol
    private JRadioButton rbEstudiante;
    private JRadioButton rbDocente;
    private ButtonGroup grupoRol;
    
    // Campo Secreto (Llave Maestra - Opcional si usas solo mail, pero mejor dejarlo por seguridad extra)
    private JLabel lblCodigoMaestro;
    private JPasswordField txtCodigoMaestro;
    
    private JButton btnRegistrar;
    private JButton btnCancelar;
    private Usuario nuevoUsuario = null;
    
    private static final String CLAVE_MAESTRA_REQUERIDA = "PROFE123"; 
    private static final String PATRON_EMAIL = "[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\\.([a-zA-Z]{2,4})+";

    // Colores
    private final Color COLOR_FONDO = new Color(245, 245, 250);
    private final Color COLOR_BTN_ACTION = new Color(52, 152, 219);

    public RegistroDialog(JFrame parent) {
        super(parent, "Crear Nueva Cuenta", true);
        setSize(420, 650); // Un poco más alto para el email
        setLocationRelativeTo(parent);
        setLayout(new BorderLayout());
        
        // --- HEADER ---
        JPanel panelHeader = new JPanel(new BorderLayout());
        panelHeader.setBackground(Color.WHITE);
        panelHeader.setBorder(new EmptyBorder(20, 0, 20, 0));
        
        JLabel lblTitulo = new JLabel("Únete a MathPath");
        lblTitulo.setFont(new Font("SansSerif", Font.BOLD, 22));
        lblTitulo.setForeground(new Color(44, 62, 80));
        lblTitulo.setHorizontalAlignment(JLabel.CENTER);
        panelHeader.add(lblTitulo, BorderLayout.CENTER);
        
        add(panelHeader, BorderLayout.NORTH);
        
        // --- FORMULARIO ---
        JPanel panelForm = new JPanel(new GridBagLayout());
        panelForm.setBackground(COLOR_FONDO);
        panelForm.setBorder(new EmptyBorder(10, 30, 10, 30));
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 0, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0; 
        
        // 1. Nombre
        gbc.gridy = 0; panelForm.add(crearLabel("Nombre Completo:"), gbc);
        gbc.gridy = 1; txtNombre = new JTextField(); estilarInput(txtNombre); panelForm.add(txtNombre, gbc);
        
        // 2. Email (NUEVO)
        gbc.gridy = 2; panelForm.add(crearLabel("Correo Electrónico:"), gbc);
        gbc.gridy = 3; txtEmail = new JTextField(); estilarInput(txtEmail); panelForm.add(txtEmail, gbc);
        
        // 3. Usuario
        gbc.gridy = 4; panelForm.add(crearLabel("Usuario (Login):"), gbc);
        gbc.gridy = 5; txtUsuario = new JTextField(); estilarInput(txtUsuario); panelForm.add(txtUsuario, gbc);
        
        // 4. Password
        gbc.gridy = 6; panelForm.add(crearLabel("Contraseña:"), gbc);
        gbc.gridy = 7; txtPassword = new JPasswordField(); estilarInput(txtPassword); panelForm.add(txtPassword, gbc);
        
        // 5. Rol
        gbc.gridy = 8;
        gbc.insets = new Insets(15, 5, 5, 5);
        panelForm.add(crearLabel("Soy:"), gbc);
        
        JPanel panelRoles = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelRoles.setOpaque(false);
        rbEstudiante = new JRadioButton("Estudiante");
        rbDocente = new JRadioButton("Profesor");
        rbEstudiante.setOpaque(false);
        rbDocente.setOpaque(false);
        rbEstudiante.setSelected(true);
        
        grupoRol = new ButtonGroup(); grupoRol.add(rbEstudiante); grupoRol.add(rbDocente);
        panelRoles.add(rbEstudiante); panelRoles.add(rbDocente);
        
        gbc.gridy = 9; gbc.insets = new Insets(0, 5, 0, 5);
        panelForm.add(panelRoles, gbc);
        
        // 6. Código Maestro
        gbc.gridy = 10; gbc.insets = new Insets(10, 5, 0, 5);
        lblCodigoMaestro = crearLabel("Código Institucional:");
        lblCodigoMaestro.setForeground(new Color(192, 57, 43));
        lblCodigoMaestro.setVisible(false);
        panelForm.add(lblCodigoMaestro, gbc);
        
        gbc.gridy = 11;
        txtCodigoMaestro = new JPasswordField();
        estilarInput(txtCodigoMaestro);
        txtCodigoMaestro.setVisible(false);
        panelForm.add(txtCodigoMaestro, gbc);
        
        ActionListener listenerRoles = e -> {
            boolean esProfe = rbDocente.isSelected();
            lblCodigoMaestro.setVisible(esProfe);
            txtCodigoMaestro.setVisible(esProfe);
            panelForm.revalidate(); panelForm.repaint();
        };
        rbEstudiante.addActionListener(listenerRoles);
        rbDocente.addActionListener(listenerRoles);
        
        add(panelForm, BorderLayout.CENTER);
        
        // --- BOTONES ---
        JPanel panelSur = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 20));
        panelSur.setBackground(Color.WHITE);
        
        btnCancelar = new JButton("Cancelar");
        estilarBoton(btnCancelar, Color.LIGHT_GRAY, Color.BLACK);
        
        btnRegistrar = new JButton("Verificar y Crear");
        estilarBoton(btnRegistrar, COLOR_BTN_ACTION, Color.WHITE);
        
        panelSur.add(btnCancelar);
        panelSur.add(btnRegistrar);
        add(panelSur, BorderLayout.SOUTH);
        
        btnCancelar.addActionListener(e -> dispose());
        btnRegistrar.addActionListener(e -> intentarRegistro());
    }

    private void intentarRegistro() {
        // 1. Validaciones básicas
        if (txtNombre.getText().isEmpty() || txtUsuario.getText().isEmpty() || 
            new String(txtPassword.getPassword()).isEmpty() || txtEmail.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Por favor completa todos los campos.");
            return;
        }
        
        // 2. Validar Llave Maestra si es Docente
        if (rbDocente.isSelected()) {
            String codigoIngresado = new String(txtCodigoMaestro.getPassword());
            if (!codigoIngresado.equals(CLAVE_MAESTRA_REQUERIDA)) {
                JOptionPane.showMessageDialog(this, "Código Institucional incorrecto.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            // --- AQUÍ COMIENZA LA MAGIA DEL EMAIL (Solo si es Docente, como pediste) ---
            iniciarVerificacionEmail(Rol.DOCENTE);
            
        } else {
            // Si es estudiante, registramos directo (o puedes activar email para ellos también)
            finalizarRegistro(Rol.ESTUDIANTE);
        }
    }
    
    private void iniciarVerificacionEmail(Rol rol) {
        // Validar correo
        if (!txtEmail.getText().matches(PATRON_EMAIL))
        {
            JOptionPane.showMessageDialog(this, "Correo Electrónico inválido.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        // Cambiar cursor a espera
        this.setCursor(new Cursor(Cursor.WAIT_CURSOR));
        btnRegistrar.setEnabled(false); // Evitar doble clic
        
        // Tarea en hilo separado para no congelar la interfaz (SwingWorker ideal, pero simplificamos aquí)
        new Thread(() -> {
            EmailService emailService = new EmailService();
            String codigoGenerado = emailService.generarCodigo();
            String emailDestino = txtEmail.getText();
            String nombreUsuario = txtNombre.getText();
            
            boolean enviado = emailService.enviarCorreo(emailDestino, codigoGenerado, nombreUsuario);
            
            // Volver al hilo gráfico para mostrar alertas
            javax.swing.SwingUtilities.invokeLater(() -> {
                this.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
                btnRegistrar.setEnabled(true);
                
                if (enviado) {
                    String codigoIngresado = JOptionPane.showInputDialog(this, 
                            "Hemos enviado un código a " + emailDestino + ".\nIngrésalo para verificar tu identidad:", 
                            "Verificación de Email", 
                            JOptionPane.QUESTION_MESSAGE);
                    
                    if (codigoIngresado != null && codigoIngresado.equals(codigoGenerado)) {
                        finalizarRegistro(rol); // ¡ÉXITO!
                    } else {
                        JOptionPane.showMessageDialog(this, "Código incorrecto.", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(this, "No se pudo enviar el correo.\nRevisa tu conexión o si el email es real.", "Error de Envío", JOptionPane.ERROR_MESSAGE);
                }
            });
        }).start();
    }

    private void finalizarRegistro(Rol rol) {
        String nuevoId = (rol == Rol.DOCENTE ? "d" : "e") + System.currentTimeMillis();
        nuevoUsuario = new Usuario(
                nuevoId, 
                txtNombre.getText(), 
                txtUsuario.getText(), 
                new String(txtPassword.getPassword()), 
                rol
        );
        JOptionPane.showMessageDialog(this, "¡Cuenta Creada Exitosamente!");
        dispose();
    }

    public Usuario getNuevoUsuario() { return nuevoUsuario; }
    
    // --- ESTILOS ---
    private JLabel crearLabel(String txt) {
        JLabel lbl = new JLabel(txt);
        lbl.setFont(new Font("SansSerif", Font.BOLD, 12));
        lbl.setForeground(new Color(100, 100, 100));
        return lbl;
    }
    private void estilarInput(JTextField txt) {
        txt.setFont(new Font("SansSerif", Font.PLAIN, 14));
        txt.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(200, 200, 200)), new EmptyBorder(5, 5, 5, 5)));
    }
    private void estilarBoton(JButton btn, Color bg, Color fg) {
        btn.setBackground(bg); btn.setForeground(fg);
        btn.setFont(new Font("SansSerif", Font.BOLD, 13));
        btn.setFocusPainted(false);
//        btn.setBorder(new EmptyBorder(10, 20, 10, 20));
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
    }
}
package vista;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import modelo.Aula;

public class CrearAulaDialog extends JDialog {

    private JTextField txtNombre;
    private JTextField txtCodigo;
    private JTextField txtDescripcion;
    private JButton btnGuardar;
    private JButton btnCancelar;

    private boolean guardado = false;

    // --- PALETA "ADMIN CLEAN" (Igual que Actividades) ---
    private final Color COLOR_HEADER = new Color(240, 242, 245); // Gris azulado claro
    private final Color COLOR_TEXT_DARK = new Color(50, 60, 70);
    private final Color COLOR_BTN_GUARDAR = new Color(46, 134, 193); // Azul Profesional
    private final Color COLOR_BTN_CANCELAR = new Color(149, 165, 166); // Gris

    public CrearAulaDialog(JFrame parent) {
        super(parent, "Crear Nueva Aula", true);

        this.setSize(450, 400); // Tamaño cómodo

        // Panel Principal (Fondo Blanco)
        JPanel panelContent = new JPanel(new BorderLayout());
        panelContent.setBackground(Color.WHITE);

        // --- 1. CABECERA ---
        JPanel panelHeader = new JPanel(new BorderLayout());
        panelHeader.setBackground(COLOR_HEADER);
        panelHeader.setBorder(new EmptyBorder(20, 20, 20, 20));

        JLabel lblTitulo = new JLabel("Nueva Clase");
        lblTitulo.setFont(new Font("SansSerif", Font.BOLD, 18));
        lblTitulo.setForeground(COLOR_TEXT_DARK);

        JLabel lblSub = new JLabel("Ingresa los datos básicos para identificar el aula.");
        lblSub.setFont(new Font("SansSerif", Font.PLAIN, 12));
        lblSub.setForeground(Color.GRAY);

        panelHeader.add(lblTitulo, BorderLayout.NORTH);
        panelHeader.add(lblSub, BorderLayout.SOUTH);

        panelContent.add(panelHeader, BorderLayout.NORTH);

        // --- 2. FORMULARIO ---
        JPanel panelFormulario = new JPanel(new GridBagLayout());
        panelFormulario.setBackground(Color.WHITE);
        panelFormulario.setBorder(new EmptyBorder(20, 30, 20, 30));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 5, 10, 5); // Espaciado vertical
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Nombre
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 0;
        panelFormulario.add(crearLabel("Nombre del Aula:"), gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        txtNombre = new JTextField();
        estilarInput(txtNombre);
        panelFormulario.add(txtNombre, gbc);

        // Código
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 0;
        panelFormulario.add(crearLabel("Código de Acceso:"), gbc);

        gbc.gridx = 1;
        gbc.gridy = 1;
        txtCodigo = new JTextField();
        estilarInput(txtCodigo);
        panelFormulario.add(txtCodigo, gbc);

        // Descripción
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.weightx = 0;
        panelFormulario.add(crearLabel("Descripción Corta:"), gbc);

        gbc.gridx = 1;
        gbc.gridy = 2;
        txtDescripcion = new JTextField();
        estilarInput(txtDescripcion);
        panelFormulario.add(txtDescripcion, gbc);

        panelContent.add(panelFormulario, BorderLayout.CENTER);

        // --- 3. BOTONES ---
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 15));
        panelBotones.setBackground(COLOR_HEADER); // Footer gris para cerrar visualmente

        btnCancelar = new JButton("Cancelar");
        estilarBoton(btnCancelar, COLOR_BTN_CANCELAR, Color.WHITE);

        btnGuardar = new JButton("Crear Aula");
        estilarBoton(btnGuardar, COLOR_BTN_GUARDAR, Color.WHITE);

        panelBotones.add(btnCancelar);
        panelBotones.add(btnGuardar);

        panelContent.add(panelBotones, BorderLayout.SOUTH);

        // --- Lógica de los Botones ---
        btnGuardar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                // Validar campos
                if (txtNombre.getText().trim().isEmpty() || txtCodigo.getText().trim().isEmpty()
                        || txtDescripcion.getText().trim().isEmpty()) {
                    JOptionPane.showMessageDialog(CrearAulaDialog.this, "Por favor, completa todos los campos.",
                            "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                guardado = true;
                dispose();
            }
        });

        btnCancelar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                guardado = false;
                dispose();
            }
        });

        // Finalizar
        this.setContentPane(panelContent);
        // No usamos pack() aquí porque fijamos un tamaño con setSize
        this.setLocationRelativeTo(parent);
    }

    // --- Métodos de Estilo (Reutilizados para coherencia) ---

    private JLabel crearLabel(String texto) {
        JLabel lbl = new JLabel(texto);
        lbl.setFont(new Font("SansSerif", Font.BOLD, 12));
        lbl.setForeground(COLOR_TEXT_DARK);
        return lbl;
    }

    private void estilarInput(JTextField txt) {
        txt.setFont(new Font("SansSerif", Font.PLAIN, 13));
        txt.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.LIGHT_GRAY),
                new EmptyBorder(8, 8, 8, 8) // Padding interno cómodo
        ));
    }

    private void estilarBoton(JButton btn, Color bg, Color fg) {
        btn.setBackground(bg);
        btn.setForeground(fg);
        btn.setFont(new Font("SansSerif", Font.BOLD, 12));
        btn.setFocusPainted(false);
        // btn.setBorder(new EmptyBorder(10, 25, 10, 25));
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
    }

    // --- Getters (Lógica intacta) ---

    public boolean isGuardado() {
        return guardado;
    }

    public Aula getNuevaAula() {
        String id = "a" + (int) (Math.random() * 1000);
        // Pasamos null en el último parámetro (idDocente), lo pondremos en el
        // controlador
        return new Aula(
                id,
                txtNombre.getText(),
                txtCodigo.getText(),
                txtDescripcion.getText(),
                null);
    }
}
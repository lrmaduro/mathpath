package vista;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.Arrays;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import modelo.Ejercicio;

public class CrearEjercicioDialog extends JDialog {

    private JTextField txtPregunta;
    private JComboBox<String> cmbTema;
    
    // Campos de opciones
    private JTextField txtOpcionA, txtOpcionB, txtOpcionC, txtOpcionD;
    private JRadioButton rbA, rbB, rbC, rbD;
    private ButtonGroup grupoRespuestas;
    
    // Feedback
    private JTextArea txtRetro; 
    
    private JButton btnGuardar, btnCancelar;
    private boolean guardado = false;

    // --- PALETA "ADMIN CLEAN" ---
    private final Color COLOR_HEADER = new Color(240, 242, 245); 
    private final Color COLOR_TEXT_DARK = new Color(50, 60, 70);
    private final Color COLOR_BTN_GUARDAR = new Color(46, 134, 193); // Azul
    private final Color COLOR_BTN_CANCELAR = new Color(149, 165, 166); // Gris

    public CrearEjercicioDialog(JFrame parent, List<String> temas) {
        super(parent, "Crear Nuevo Ejercicio", true);
        this.setSize(600, 650); // Un poco más alto para que quepa todo
        
        // Panel Principal
        JPanel panelContent = new JPanel(new BorderLayout());
        panelContent.setBackground(Color.WHITE);

        // --- 1. CABECERA ---
        JPanel panelHeader = new JPanel(new BorderLayout());
        panelHeader.setBackground(COLOR_HEADER);
        panelHeader.setBorder(new EmptyBorder(20, 20, 20, 20));
        
        JLabel lblTitulo = new JLabel("Nuevo Ejercicio");
        lblTitulo.setFont(new Font("SansSerif", Font.BOLD, 18));
        lblTitulo.setForeground(COLOR_TEXT_DARK);
        
        JLabel lblSub = new JLabel("Define la pregunta, las opciones y la respuesta correcta.");
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
        gbc.insets = new Insets(8, 5, 8, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        
        // --- Fila 0: Pregunta ---
        gbc.gridx = 0; gbc.gridy = 0; gbc.weightx = 0;
        panelFormulario.add(crearLabel("Pregunta:"), gbc);
        
        gbc.gridx = 1; gbc.weightx = 1.0;
        txtPregunta = new JTextField();
        estilarInput(txtPregunta);
        panelFormulario.add(txtPregunta, gbc);
        
        // --- Fila 1: Tema ---
        gbc.gridx = 0; gbc.gridy = 1; gbc.weightx = 0;
        panelFormulario.add(crearLabel("Tema:"), gbc);
        
        gbc.gridx = 1;
        cmbTema = new JComboBox<>(temas.toArray(new String[0]));
        cmbTema.setBackground(Color.WHITE);
        cmbTema.setFont(new Font("SansSerif", Font.PLAIN, 13));
        panelFormulario.add(cmbTema, gbc);

        // --- Separador Visual ---
        gbc.gridx = 0; gbc.gridy = 2; gbc.gridwidth = 2;
        JLabel lblSep = new JLabel("Opciones de Respuesta (Selecciona la correcta)");
        lblSep.setFont(new Font("SansSerif", Font.BOLD, 13));
        lblSep.setForeground(COLOR_BTN_GUARDAR); // Azulito
        lblSep.setBorder(new EmptyBorder(15, 0, 5, 0));
        panelFormulario.add(lblSep, gbc);
        gbc.gridwidth = 1; // Restaurar

        // --- Filas 3-6: Opciones ---
        grupoRespuestas = new ButtonGroup();
        agregarOpcion(panelFormulario, gbc, 3, "A");
        agregarOpcion(panelFormulario, gbc, 4, "B");
        agregarOpcion(panelFormulario, gbc, 5, "C");
        agregarOpcion(panelFormulario, gbc, 6, "D");

        // --- Fila 7: Retroalimentación ---
        gbc.gridx = 0; gbc.gridy = 7; gbc.weightx = 0;
        panelFormulario.add(crearLabel("Feedback (si falla):"), gbc);
        
        gbc.gridx = 1; 
        txtRetro = new JTextArea(3, 20);
        txtRetro.setFont(new Font("SansSerif", Font.PLAIN, 13));
        txtRetro.setLineWrap(true);
        txtRetro.setWrapStyleWord(true);
        // Borde compuesto para darle padding al TextArea
        txtRetro.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(Color.LIGHT_GRAY),
            new EmptyBorder(5, 5, 5, 5)
        ));
        panelFormulario.add(txtRetro, gbc);

        panelContent.add(panelFormulario, BorderLayout.CENTER);

        // --- 3. BOTONES ---
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 15));
        panelBotones.setBackground(COLOR_HEADER);
        
        btnCancelar = new JButton("Cancelar");
        estilarBoton(btnCancelar, COLOR_BTN_CANCELAR, Color.WHITE);
        
        btnGuardar = new JButton("Guardar Ejercicio");
        estilarBoton(btnGuardar, COLOR_BTN_GUARDAR, Color.WHITE);

        panelBotones.add(btnCancelar);
        panelBotones.add(btnGuardar);
        
        panelContent.add(panelBotones, BorderLayout.SOUTH);

        // Lógica Botones
        btnGuardar.addActionListener(e -> { guardado = true; dispose(); });
        btnCancelar.addActionListener(e -> { guardado = false; dispose(); });

        this.setContentPane(panelContent);
        // No usamos pack() porque fijamos setSize
        this.setLocationRelativeTo(parent);
    }
    
    // Método auxiliar modificado para incluir estilo
    private void agregarOpcion(JPanel panel, GridBagConstraints gbc, int y, String letra) {
        gbc.gridx = 0; gbc.gridy = y; gbc.weightx = 0;
        
        JRadioButton rb = new JRadioButton(letra + ")");
        rb.setOpaque(false);
        rb.setBackground(Color.WHITE);
        rb.setFont(new Font("SansSerif", Font.BOLD, 13));
        rb.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        grupoRespuestas.add(rb);
        panel.add(rb, gbc);
        
        gbc.gridx = 1; gbc.weightx = 1.0;
        JTextField txt = new JTextField();
        estilarInput(txt); // Aplicamos el estilo también aquí
        panel.add(txt, gbc);
        
        if (letra.equals("A")) { rbA = rb; txtOpcionA = txt; rb.setSelected(true); } // A por defecto
        else if (letra.equals("B")) { rbB = rb; txtOpcionB = txt; }
        else if (letra.equals("C")) { rbC = rb; txtOpcionC = txt; }
        else if (letra.equals("D")) { rbD = rb; txtOpcionD = txt; }
    }
    
    // --- MÉTODOS DE ESTILO ---
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
            new EmptyBorder(8, 8, 8, 8)
        ));
    }
    
    private void estilarBoton(JButton btn, Color bg, Color fg) {
        btn.setBackground(bg);
        btn.setForeground(fg);
        btn.setFont(new Font("SansSerif", Font.BOLD, 12));
        btn.setFocusPainted(false);
        btn.setBorder(new EmptyBorder(10, 20, 10, 20));
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
    }

    public boolean isGuardado() { return guardado; }
    
    public Ejercicio getNuevoEjercicio() {
        String id = "e" + (int)(Math.random() * 1000); 
        List<String> opciones = Arrays.asList(txtOpcionA.getText(), txtOpcionB.getText(), txtOpcionC.getText(), txtOpcionD.getText());
        
        String clave = "A"; // Default
        if (rbB.isSelected()) clave = "B";
        if (rbC.isSelected()) clave = "C";
        if (rbD.isSelected()) clave = "D";
        
        return new Ejercicio(id, txtPregunta.getText(), opciones, clave, (String)cmbTema.getSelectedItem(), "Opción Múltiple", txtRetro.getText());
    }
}
package vista;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
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
import javax.swing.JTextArea; // Usamos Area para que quepa más texto
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import modelo.Ejercicio;

public class CrearEjercicioDialog extends JDialog {

    private JTextField txtPregunta;
    private JComboBox<String> cmbTema;
    private JComboBox<String> cmbTipo;
    
    // Campos de opciones
    private JTextField txtOpcionA, txtOpcionB, txtOpcionC, txtOpcionD;
    private JRadioButton rbA, rbB, rbC, rbD;
    private ButtonGroup grupoRespuestas;
    
    // NUEVO CAMPO FEEDBACK
    private JTextArea txtRetro; 
    
    private JButton btnGuardar, btnCancelar;
    private boolean guardado = false;

    public CrearEjercicioDialog(JFrame parent, List<String> temas) {
        super(parent, "Crear Nuevo Ejercicio", true);
        
        JPanel panelFormulario = new JPanel(new GridBagLayout());
        panelFormulario.setBorder(new EmptyBorder(10, 10, 10, 10));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        
        // --- Fila 0: Pregunta ---
        gbc.gridx = 0; gbc.gridy = 0;
        panelFormulario.add(new JLabel("Pregunta:"), gbc);
        gbc.gridx = 1; gbc.weightx = 1.0;
        txtPregunta = new JTextField(20);
        panelFormulario.add(txtPregunta, gbc);
        
        // --- Fila 1: Tema y Tipo ---
        gbc.gridx = 0; gbc.gridy = 1; gbc.weightx = 0;
        panelFormulario.add(new JLabel("Tema:"), gbc);
        gbc.gridx = 1;
        cmbTema = new JComboBox<>(temas.toArray(new String[0]));
        panelFormulario.add(cmbTema, gbc);

        // --- Filas 2-5: Opciones ---
        grupoRespuestas = new ButtonGroup();
        agregarOpcion(panelFormulario, gbc, 2, "A");
        agregarOpcion(panelFormulario, gbc, 3, "B");
        agregarOpcion(panelFormulario, gbc, 4, "C");
        agregarOpcion(panelFormulario, gbc, 5, "D");

        // --- Fila 6: Retroalimentación (NUEVO) ---
        gbc.gridx = 0; gbc.gridy = 6;
        panelFormulario.add(new JLabel("Feedback (si falla):"), gbc);
        
        gbc.gridx = 1; 
        txtRetro = new JTextArea(3, 20);
        txtRetro.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        txtRetro.setLineWrap(true);
        panelFormulario.add(txtRetro, gbc);

        // --- Botones ---
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        btnGuardar = new JButton("Guardar Ejercicio");
        btnCancelar = new JButton("Cancelar");
        panelBotones.add(btnGuardar);
        panelBotones.add(btnCancelar);

        // Lógica Botones
        btnGuardar.addActionListener(e -> { guardado = true; dispose(); });
        btnCancelar.addActionListener(e -> { guardado = false; dispose(); });

        add(panelFormulario, BorderLayout.CENTER);
        add(panelBotones, BorderLayout.SOUTH);
        pack();
        setLocationRelativeTo(parent);
    }
    
    // Método auxiliar para no repetir código en las opciones
    private void agregarOpcion(JPanel panel, GridBagConstraints gbc, int y, String letra) {
        gbc.gridx = 0; gbc.gridy = y;
        JRadioButton rb = new JRadioButton(letra + ")");
        grupoRespuestas.add(rb);
        panel.add(rb, gbc);
        
        gbc.gridx = 1;
        JTextField txt = new JTextField();
        panel.add(txt, gbc);
        
        // Asignamos a las variables de clase según la letra
        if (letra.equals("A")) { rbA = rb; txtOpcionA = txt; }
        else if (letra.equals("B")) { rbB = rb; txtOpcionB = txt; }
        else if (letra.equals("C")) { rbC = rb; txtOpcionC = txt; }
        else if (letra.equals("D")) { rbD = rb; txtOpcionD = txt; }
    }

    public boolean isGuardado() { return guardado; }
    
    public Ejercicio getNuevoEjercicio() {
        String id = "e" + (int)(Math.random() * 1000); 
        List<String> opciones = Arrays.asList(txtOpcionA.getText(), txtOpcionB.getText(), txtOpcionC.getText(), txtOpcionD.getText());
        
        String clave = "A"; // Default
        if (rbB.isSelected()) clave = "B";
        if (rbC.isSelected()) clave = "C";
        if (rbD.isSelected()) clave = "D";
        
        // Aquí pasamos el texto del feedback al nuevo constructor
        return new Ejercicio(id, txtPregunta.getText(), opciones, clave, (String)cmbTema.getSelectedItem(), "Opción Múltiple", txtRetro.getText());
    }
}
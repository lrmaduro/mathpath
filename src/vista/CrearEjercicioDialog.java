package vista;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.Arrays;
import java.util.List;
import javax.swing.ButtonGroup; // Nuevo
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton; // Nuevo
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import modelo.Ejercicio;

public class CrearEjercicioDialog extends JDialog {

    private JTextField txtPregunta;
    private JComboBox<String> cmbTema;
    private JComboBox<String> cmbTipo;
    private JButton btnGuardar;
    private JButton btnCancelar;
    
    // --- CAMPOS NUEVOS PARA OPCIONES ---
    private JTextField txtOpcionA;
    private JTextField txtOpcionB;
    private JTextField txtOpcionC;
    private JTextField txtOpcionD;
    
    private JRadioButton rbA, rbB, rbC, rbD; // Para seleccionar la correcta
    private ButtonGroup grupoRespuestas; // Para asegurar que solo se selecciona una
    // --- FIN CAMPOS NUEVOS ---
    
    private boolean guardado = false;

    public CrearEjercicioDialog(JFrame parent, List<String> temas) {
        super(parent, "Crear Nuevo Ejercicio", true);
        
        // Inicialización de controles
        txtPregunta = new JTextField(30);
        cmbTema = new JComboBox<>(temas.toArray(new String[0]));
        cmbTipo = new JComboBox<>(new String[]{"Opción Múltiple"}); // Solo permitimos esta por ahora

        // Inicialización de Opciones
        txtOpcionA = new JTextField(25);
        txtOpcionB = new JTextField(25);
        txtOpcionC = new JTextField(25);
        txtOpcionD = new JTextField(25);
        
        rbA = new JRadioButton("A");
        rbB = new JRadioButton("B");
        rbC = new JRadioButton("C");
        rbD = new JRadioButton("D");
        
        grupoRespuestas = new ButtonGroup();
        grupoRespuestas.add(rbA);
        grupoRespuestas.add(rbB);
        grupoRespuestas.add(rbC);
        grupoRespuestas.add(rbD);
        rbA.setSelected(true); // Seleccionar la primera por defecto

        // Panel de Formulario
        JPanel panelFormulario = new JPanel(new GridBagLayout());
        panelFormulario.setBorder(new EmptyBorder(10, 10, 10, 10));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        
        // Fila 1: Pregunta (Enunciado)
        gbc.gridx = 0; gbc.gridy = 0; gbc.anchor = GridBagConstraints.EAST;
        panelFormulario.add(new JLabel("Enunciado:"), gbc);
        gbc.gridx = 1; gbc.gridy = 0; gbc.gridwidth = 3; gbc.fill = GridBagConstraints.HORIZONTAL;
        panelFormulario.add(txtPregunta, gbc);
        gbc.gridwidth = 1; // Reseteamos el ancho

        // Fila 2: Título Opciones
        gbc.gridx = 0; gbc.gridy = 1; gbc.gridwidth = 4; gbc.anchor = GridBagConstraints.WEST;
        panelFormulario.add(new JLabel("<html><b>Opciones de Respuesta:</b></html>"), gbc);
        gbc.gridwidth = 1; // Reseteamos
        
        // Fila 3: Opción A
        gbc.gridx = 0; gbc.gridy = 2; gbc.anchor = GridBagConstraints.WEST;
        panelFormulario.add(rbA, gbc);
        gbc.gridx = 1; gbc.gridy = 2; gbc.gridwidth = 3; gbc.fill = GridBagConstraints.HORIZONTAL;
        panelFormulario.add(txtOpcionA, gbc);
        gbc.gridwidth = 1;
        
        // Fila 4: Opción B
        gbc.gridx = 0; gbc.gridy = 3; gbc.anchor = GridBagConstraints.WEST;
        panelFormulario.add(rbB, gbc);
        gbc.gridx = 1; gbc.gridy = 3; gbc.gridwidth = 3; gbc.fill = GridBagConstraints.HORIZONTAL;
        panelFormulario.add(txtOpcionB, gbc);
        gbc.gridwidth = 1;

        // Fila 5: Opción C
        gbc.gridx = 0; gbc.gridy = 4; gbc.anchor = GridBagConstraints.WEST;
        panelFormulario.add(rbC, gbc);
        gbc.gridx = 1; gbc.gridy = 4; gbc.gridwidth = 3; gbc.fill = GridBagConstraints.HORIZONTAL;
        panelFormulario.add(txtOpcionC, gbc);
        gbc.gridwidth = 1;

        // Fila 6: Opción D
        gbc.gridx = 0; gbc.gridy = 5; gbc.anchor = GridBagConstraints.WEST;
        panelFormulario.add(rbD, gbc);
        gbc.gridx = 1; gbc.gridy = 5; gbc.gridwidth = 3; gbc.fill = GridBagConstraints.HORIZONTAL;
        panelFormulario.add(txtOpcionD, gbc);
        gbc.gridwidth = 1;
        
        // Fila 7: Tema
        gbc.gridx = 0; gbc.gridy = 6; gbc.anchor = GridBagConstraints.EAST;
        panelFormulario.add(new JLabel("Tema:"), gbc);
        gbc.gridx = 1; gbc.gridy = 6; gbc.gridwidth = 3; gbc.fill = GridBagConstraints.HORIZONTAL;
        panelFormulario.add(cmbTema, gbc);
        gbc.gridwidth = 1;
        
        // Fila 8: Tipo (Solo Opción Múltiple)
        gbc.gridx = 0; gbc.gridy = 7; gbc.anchor = GridBagConstraints.EAST;
        panelFormulario.add(new JLabel("Tipo:"), gbc);
        gbc.gridx = 1; gbc.gridy = 7; gbc.gridwidth = 3; gbc.fill = GridBagConstraints.HORIZONTAL;
        cmbTipo.setEnabled(false); // Deshabilitar porque solo tenemos Opción Múltiple por ahora
        panelFormulario.add(cmbTipo, gbc);
        gbc.gridwidth = 1;


        // Panel de Botones (se mantiene igual)
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        btnGuardar = new JButton("Guardar Ejercicio");
        btnCancelar = new JButton("Cancelar");
        panelBotones.add(btnGuardar);
        panelBotones.add(btnCancelar);

        btnGuardar.addActionListener(e -> { guardado = true; dispose(); });
        btnCancelar.addActionListener(e -> { guardado = false; dispose(); });

        this.setLayout(new BorderLayout());
        this.add(panelFormulario, BorderLayout.CENTER);
        this.add(panelBotones, BorderLayout.SOUTH);
        
        this.pack();
        this.setLocationRelativeTo(parent);
    }

    public boolean isGuardado() { return guardado; }
    
    public Ejercicio getNuevoEjercicio() {
        String id = "e" + (int)(Math.random() * 1000); 
        
        // 1. Recolectar las 4 opciones
        List<String> opciones = Arrays.asList(
                txtOpcionA.getText(),
                txtOpcionB.getText(),
                txtOpcionC.getText(),
                txtOpcionD.getText()
        );
        
        // 2. Determinar la clave correcta
        String clave = "";
        if (rbA.isSelected()) clave = "A";
        else if (rbB.isSelected()) clave = "B";
        else if (rbC.isSelected()) clave = "C";
        else if (rbD.isSelected()) clave = "D";
        
        return new Ejercicio(
                id,
                txtPregunta.getText(),
                opciones,
                clave,
                cmbTema.getSelectedItem().toString(),
                cmbTipo.getSelectedItem().toString()
        );
    }
}
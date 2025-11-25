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
import java.util.ArrayList;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import modelo.Actividad;
import modelo.Ejercicio;

public class CrearActividadDialog extends JDialog {

    private JTextField txtNombre;
    private JComboBox<String> cmbTema;
    private JButton btnGuardar;
    private JButton btnCancelar;
    
    private String idAula;
    private boolean guardado = false;
    
    private List<Ejercicio> ejerciciosDisponibles;
    private List<JCheckBox> listaCheckboxes;

    // Colores Profesionales (Admin Clean)
    private final Color COLOR_HEADER = new Color(240, 242, 245); 
    private final Color COLOR_TEXT_DARK = new Color(50, 60, 70);
    private final Color COLOR_BTN_GUARDAR = new Color(46, 134, 193); 
    private final Color COLOR_BTN_CANCELAR = new Color(149, 165, 166); 

    public CrearActividadDialog(JFrame parent, List<Ejercicio> ejercicios, String idAula) {
        super(parent, "Nueva Actividad", true);
        this.ejerciciosDisponibles = ejercicios;
        this.listaCheckboxes = new ArrayList<>();
        this.idAula = idAula;
        
        this.setSize(500, 600); 
        
        // Panel Principal
        JPanel panelContent = new JPanel(new BorderLayout());
        panelContent.setBackground(Color.WHITE);

        // --- 1. CABECERA ---
        JPanel panelHeader = new JPanel(new BorderLayout());
        panelHeader.setBackground(COLOR_HEADER);
        panelHeader.setBorder(new EmptyBorder(20, 20, 20, 20));
        
        JLabel lblTitulo = new JLabel("Configurar Actividad");
        lblTitulo.setFont(new Font("SansSerif", Font.BOLD, 18));
        lblTitulo.setForeground(COLOR_TEXT_DARK);
        
        JLabel lblSub = new JLabel("Define los detalles y selecciona los ejercicios.");
        lblSub.setFont(new Font("SansSerif", Font.PLAIN, 12));
        lblSub.setForeground(Color.GRAY);
        
        panelHeader.add(lblTitulo, BorderLayout.NORTH);
        panelHeader.add(lblSub, BorderLayout.SOUTH);
        
        panelContent.add(panelHeader, BorderLayout.NORTH);

        // --- 2. FORMULARIO Y SELECCIÓN ---
        JPanel panelCentral = new JPanel(new BorderLayout(0, 15));
        panelCentral.setBackground(Color.WHITE);
        panelCentral.setBorder(new EmptyBorder(20, 20, 10, 20));

        // A. Datos Básicos
        JPanel panelDatos = new JPanel(new GridBagLayout());
        panelDatos.setBackground(Color.WHITE);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 5, 8, 5); 
        gbc.fill = GridBagConstraints.HORIZONTAL;
        
        // Nombre
        gbc.gridx = 0; gbc.gridy = 0; gbc.weightx = 0;
        panelDatos.add(crearLabel("Nombre:"), gbc);
        
        gbc.gridx = 1; gbc.weightx = 1.0;
        txtNombre = new JTextField();
        estilarInput(txtNombre);
        panelDatos.add(txtNombre, gbc);
        
        // Tema
        gbc.gridx = 0; gbc.gridy = 1; gbc.weightx = 0;
        panelDatos.add(crearLabel("Tema:"), gbc);
        
        gbc.gridx = 1;
        String[] temas = {"Aritmética Básica", "Álgebra", "Geometría", "Ecuaciones", "General"};
        cmbTema = new JComboBox<>(temas);
        cmbTema.setBackground(Color.WHITE);
        panelDatos.add(cmbTema, gbc);
        
        panelCentral.add(panelDatos, BorderLayout.NORTH);
        
        // B. Selección de Ejercicios
        JPanel panelSeleccion = new JPanel(new BorderLayout(0, 10));
        panelSeleccion.setBackground(Color.WHITE);
        
        JLabel lblSeleccion = new JLabel("Selecciona los ejercicios a incluir:");
        lblSeleccion.setFont(new Font("SansSerif", Font.BOLD, 13));
        lblSeleccion.setForeground(COLOR_TEXT_DARK);
        panelSeleccion.add(lblSeleccion, BorderLayout.NORTH);
        
        // Contenedor de checkboxes
        JPanel panelCheckboxes = new JPanel();
        panelCheckboxes.setLayout(new BoxLayout(panelCheckboxes, BoxLayout.Y_AXIS));
        panelCheckboxes.setBackground(Color.WHITE);
        
        if (ejercicios.isEmpty()) {
            JLabel lblVacio = new JLabel("No hay ejercicios en el banco.");
            lblVacio.setForeground(Color.GRAY);
            lblVacio.setBorder(new EmptyBorder(10, 10, 10, 10));
            panelCheckboxes.add(lblVacio);
        } else {
            for (Ejercicio ej : ejercicios) {
                String etiqueta = String.format("<html><font color='#2980B9'><b>%s</b></font>: %s</html>", ej.getId(), ej.getPregunta());
                JCheckBox chk = new JCheckBox(etiqueta);
                chk.setOpaque(false);
                chk.setFont(new Font("SansSerif", Font.PLAIN, 13));
                chk.setCursor(new Cursor(Cursor.HAND_CURSOR));
                chk.setBorder(new EmptyBorder(5, 5, 5, 5)); 
                
                listaCheckboxes.add(chk);
                panelCheckboxes.add(chk);
                JSeparator sep = new JSeparator();
                sep.setForeground(new Color(240,240,240));
                panelCheckboxes.add(sep);
            }
        }
        
        JScrollPane scrollEjercicios = new JScrollPane(panelCheckboxes);
        scrollEjercicios.setPreferredSize(new Dimension(400, 250));
        scrollEjercicios.setBorder(BorderFactory.createLineBorder(new Color(220, 220, 220)));
        scrollEjercicios.getVerticalScrollBar().setUnitIncrement(16);
        
        panelSeleccion.add(scrollEjercicios, BorderLayout.CENTER);
        panelCentral.add(panelSeleccion, BorderLayout.CENTER);
        
        panelContent.add(panelCentral, BorderLayout.CENTER);

        // --- 3. BOTONES ---
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 15));
        panelBotones.setBackground(COLOR_HEADER); 
        
        btnCancelar = new JButton("Cancelar");
        estilarBoton(btnCancelar, COLOR_BTN_CANCELAR, Color.WHITE);
        
        btnGuardar = new JButton("Guardar Actividad");
        estilarBoton(btnGuardar, COLOR_BTN_GUARDAR, Color.WHITE);

        panelBotones.add(btnCancelar);
        panelBotones.add(btnGuardar);
        
        // --- ¡AQUÍ ESTABA EL ERROR! FALTABA ESTA LÍNEA: ---
        panelContent.add(panelBotones, BorderLayout.SOUTH);
        // --------------------------------------------------

        // Listeners
        btnGuardar.addActionListener(e -> { guardado = true; dispose(); });
        btnCancelar.addActionListener(e -> { guardado = false; dispose(); });

        this.setContentPane(panelContent);
        // No usamos pack() porque fijamos tamaño
        this.setLocationRelativeTo(parent);
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
            new EmptyBorder(5, 8, 5, 8)
        ));
    }
    
    private void estilarBoton(JButton btn, Color bg, Color fg) {
        btn.setBackground(bg);
        btn.setForeground(fg);
        btn.setFont(new Font("SansSerif", Font.BOLD, 12));
        btn.setFocusPainted(false);
//        btn.setBorder(new EmptyBorder(10, 20, 10, 20));
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
    }

    public boolean isGuardado() {
        return guardado;
    }
    
    public Actividad getNuevaActividad() {
        String id = "act" + (int)(Math.random() * 10000); 
        
        List<String> idsSeleccionados = new ArrayList<>();
        
        for (int i = 0; i < listaCheckboxes.size(); i++) {
            if (listaCheckboxes.get(i).isSelected()) {
                String idEjercicio = ejerciciosDisponibles.get(i).getId();
                idsSeleccionados.add(idEjercicio);
            }
        }
        
        return new Actividad(
                id,
                txtNombre.getText(),
                this.idAula,
                (String)cmbTema.getSelectedItem(),
                idsSeleccionados
        );
    }
}
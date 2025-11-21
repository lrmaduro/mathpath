package vista;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
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
    
    // Listas para manejar la selección
    private List<Ejercicio> ejerciciosDisponibles;
    private List<JCheckBox> listaCheckboxes;

    public CrearActividadDialog(JFrame parent, List<Ejercicio> ejercicios, String idAula) {
        super(parent, "Nueva Actividad", true);
        this.ejerciciosDisponibles = ejercicios;
        this.listaCheckboxes = new ArrayList<>();
        this.idAula = idAula;
        
        // Panel Principal
        JPanel panelFormulario = new JPanel(new BorderLayout(10, 10));
        panelFormulario.setBorder(new EmptyBorder(15, 15, 15, 15));

        // 1. Datos Básicos (Nombre y Tema)
        JPanel panelDatos = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        
        gbc.gridx = 0; gbc.gridy = 0;
        panelDatos.add(new JLabel("Nombre Actividad:"), gbc);
        
        gbc.gridx = 1; gbc.weightx = 1.0;
        txtNombre = new JTextField(20);
        panelDatos.add(txtNombre, gbc);
        
        gbc.gridx = 0; gbc.gridy = 1; gbc.weightx = 0;
        panelDatos.add(new JLabel("Tema:"), gbc);
        
        gbc.gridx = 1;
        // Temas hardcodeados por simplicidad, o podrías pasarlos como parámetro
        String[] temas = {"Aritmética Básica", "Álgebra", "Geometría", "Ecuaciones", "General"};
        cmbTema = new JComboBox<>(temas);
        panelDatos.add(cmbTema, gbc);
        
        panelFormulario.add(panelDatos, BorderLayout.NORTH);
        
        // 2. Selección de Ejercicios (CON SCROLL)
        JLabel lblSeleccion = new JLabel("Selecciona los ejercicios a incluir:");
        lblSeleccion.setBorder(new EmptyBorder(10, 0, 5, 0));
        
        JPanel panelCheckboxes = new JPanel();
        panelCheckboxes.setLayout(new BoxLayout(panelCheckboxes, BoxLayout.Y_AXIS));
        panelCheckboxes.setBackground(Color.WHITE);
        
        // Generamos un CheckBox por cada ejercicio disponible
        if (ejercicios.isEmpty()) {
            panelCheckboxes.add(new JLabel("No hay ejercicios creados aún."));
        } else {
            for (Ejercicio ej : ejercicios) {
                String etiqueta = String.format("<html><b>%s</b>: %s</html>", ej.getId(), ej.getPregunta());
                JCheckBox chk = new JCheckBox(etiqueta);
                chk.setOpaque(false);
                listaCheckboxes.add(chk);
                panelCheckboxes.add(chk);
            }
        }
        
        // Importante: ScrollPane para que si hay 50 ejercicios, se vean todos
        JScrollPane scrollEjercicios = new JScrollPane(panelCheckboxes);
        scrollEjercicios.setPreferredSize(new Dimension(400, 200));
        scrollEjercicios.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
        
        JPanel panelCentro = new JPanel(new BorderLayout());
        panelCentro.add(lblSeleccion, BorderLayout.NORTH);
        panelCentro.add(scrollEjercicios, BorderLayout.CENTER);
        
        panelFormulario.add(panelCentro, BorderLayout.CENTER);

        // 3. Botones
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        btnGuardar = new JButton("Guardar Actividad");
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

    public boolean isGuardado() {
        return guardado;
    }
    
    public Actividad getNuevaActividad() {
        String id = "act" + (int)(Math.random() * 10000); // ID aleatorio
        
        // Recolectamos los IDs de los checkboxes marcados
        List<String> idsSeleccionados = new ArrayList<>();
        
        for (int i = 0; i < listaCheckboxes.size(); i++) {
            if (listaCheckboxes.get(i).isSelected()) {
                // Como las listas están sincronizadas (mismo orden), usamos el índice i
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
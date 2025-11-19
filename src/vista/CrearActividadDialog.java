package vista;

import java.awt.BorderLayout;
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
import javax.swing.JCheckBox; // Nuevo
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane; // Nuevo
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import modelo.Actividad;
import modelo.Ejercicio; // Nuevo

public class CrearActividadDialog extends JDialog {

    private JTextField txtNombre;
    private JComboBox<String> cmbTema;
    private JButton btnGuardar;
    private JButton btnCancelar;
    
    private String idAula;
    private boolean guardado = false;
    
    // --- NUEVOS COMPONENTES PARA EL CHECKLIST ---
    private List<Ejercicio> ejerciciosDisponibles; // Lista completa de ejercicios
    private List<JCheckBox> listaCheckboxes; // Lista de Checkboxes creados
    // --- FIN COMPONENTES NUEVOS ---

    // --- MODIFICAR LA FIRMA DEL CONSTRUCTOR ---
    public CrearActividadDialog(JFrame parent, String idAula, List<String> temas, List<Ejercicio> ejercicios) {
        super(parent, "Crear Nueva Actividad", true);
        this.idAula = idAula;
        this.ejerciciosDisponibles = ejercicios; // Guardamos la lista de ejercicios
        this.listaCheckboxes = new ArrayList<>();
        
        JPanel panelPrincipal = new JPanel(new BorderLayout(10, 10));
        panelPrincipal.setBorder(new EmptyBorder(10, 10, 10, 10));
        
        // --- Panel de Configuración (Nombre y Tema) ---
        JPanel panelConfig = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        
        // Fila 1: Nombre
        gbc.gridx = 0; gbc.gridy = 0; panelConfig.add(new JLabel("Nombre:"), gbc);
        gbc.gridx = 1; gbc.gridy = 0; txtNombre = new JTextField(20); panelConfig.add(txtNombre, gbc);

        // Fila 2: Tema
        gbc.gridx = 0; gbc.gridy = 1; panelConfig.add(new JLabel("Tema:"), gbc);
        gbc.gridx = 1; gbc.gridy = 1; cmbTema = new JComboBox<>(temas.toArray(new String[0])); panelConfig.add(cmbTema, gbc);
        
        panelPrincipal.add(panelConfig, BorderLayout.NORTH);
        
        // --- Panel de Checklist de Ejercicios (CENTRO) ---
        JPanel wrapperChecklist = new JPanel(new BorderLayout());
        wrapperChecklist.setBorder(BorderFactory.createTitledBorder("Seleccionar Ejercicios a Incluir"));
        
        JPanel panelChecklist = new JPanel();
        panelChecklist.setLayout(new BoxLayout(panelChecklist, BoxLayout.Y_AXIS));

        // Rellenar la lista de checkboxes con los ejercicios disponibles
        if (ejercicios != null) {
            for (Ejercicio ej : ejercicios) {
                // Usamos el enunciado y el tema para que sea informativo
                JCheckBox check = new JCheckBox("<html>" + ej.getPregunta() + " <i>(" + ej.getIdTema() + ")</i></html>");
                panelChecklist.add(check);
                listaCheckboxes.add(check);
            }
        }
        
        JScrollPane scrollPane = new JScrollPane(panelChecklist);
        scrollPane.setPreferredSize(new Dimension(400, 200)); // Tamaño fijo para la lista
        wrapperChecklist.add(scrollPane, BorderLayout.CENTER);
        
        panelPrincipal.add(wrapperChecklist, BorderLayout.CENTER);

        // --- Panel de Botones (SUR) ---
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        btnGuardar = new JButton("Guardar");
        btnCancelar = new JButton("Cancelar");
        panelBotones.add(btnGuardar);
        panelBotones.add(btnCancelar);

        btnGuardar.addActionListener(e -> { guardado = true; dispose(); });
        btnCancelar.addActionListener(e -> { guardado = false; dispose(); });

        this.setLayout(new BorderLayout());
        this.add(panelPrincipal, BorderLayout.CENTER);
        this.add(panelBotones, BorderLayout.SOUTH);
        
        this.pack();
        this.setLocationRelativeTo(parent);
    }

    public boolean isGuardado() {
        return guardado;
    }
    
    // --- MODIFICAR getNuevaActividad() ---
    public Actividad getNuevaActividad() {
        String id = "act" + (int)(Math.random() * 1000); 
        
        // Recolectar los IDs de los ejercicios seleccionados
        List<String> idsSeleccionados = new ArrayList<>();
        
        for (int i = 0; i < listaCheckboxes.size(); i++) {
            if (listaCheckboxes.get(i).isSelected()) {
                // Obtenemos el ID del ejercicio correspondiente
                String idEjercicio = ejerciciosDisponibles.get(i).getId();
                idsSeleccionados.add(idEjercicio);
            }
        }
        
        return new Actividad(
                id,
                txtNombre.getText(),
                this.idAula,
                cmbTema.getSelectedItem().toString(),
                idsSeleccionados // Pasamos la nueva lista
        );
    }
}
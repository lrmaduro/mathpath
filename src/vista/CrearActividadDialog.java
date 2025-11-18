package vista;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import modelo.Actividad;

public class CrearActividadDialog extends JDialog {

    private JTextField txtNombre;
    private JComboBox<String> cmbTema;
    private JButton btnGuardar;
    private JButton btnCancelar;
    
    private boolean guardado = false;
    private String idAula;

    public CrearActividadDialog(JFrame parent, String idAula, List<String> temas) {
        super(parent, "Crear Nueva Actividad", true);
        this.idAula = idAula; // Guardamos el ID del aula a la que pertenece
        
        JPanel panelFormulario = new JPanel(new GridBagLayout());
        panelFormulario.setBorder(new EmptyBorder(10, 10, 10, 10));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Fila 1: Nombre
        gbc.gridx = 0; gbc.gridy = 0;
        panelFormulario.add(new JLabel("Nombre:"), gbc);
        gbc.gridx = 1; gbc.gridy = 0;
        txtNombre = new JTextField(20);
        panelFormulario.add(txtNombre, gbc);

        // Fila 2: Tema
        gbc.gridx = 0; gbc.gridy = 1;
        panelFormulario.add(new JLabel("Tema:"), gbc);
        gbc.gridx = 1; gbc.gridy = 1;
        String[] arrayTemas = temas.toArray(new String[0]);
        cmbTema = new JComboBox<>(arrayTemas);
        panelFormulario.add(cmbTema, gbc);
        
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        btnGuardar = new JButton("Guardar");
        btnCancelar = new JButton("Cancelar");
        panelBotones.add(btnGuardar);
        panelBotones.add(btnCancelar);

        btnGuardar.addActionListener(e -> {
            guardado = true;
            dispose();
        });
        
        btnCancelar.addActionListener(e -> {
            guardado = false;
            dispose();
        });

        this.setLayout(new BorderLayout());
        this.add(panelFormulario, BorderLayout.CENTER);
        this.add(panelBotones, BorderLayout.SOUTH);
        
        this.pack();
        this.setLocationRelativeTo(parent);
    }

    // --- Métodos para el Controlador ---
    public boolean isGuardado() {
        return guardado;
    }
    
    public Actividad getNuevaActividad() {
        String id = "act" + (int)(Math.random() * 1000); // ID aleatorio

        // --- MODIFICA ESTA LÍNEA ---
        // String temaSeleccionado = txtTema.getText();
        String temaSeleccionado = cmbTema.getSelectedItem().toString();
        // --- FIN DE LA MODIFICACIÓN ---

        return new Actividad(
                id,
                txtNombre.getText(),
                this.idAula,
                temaSeleccionado // Usamos la nueva variable
        );
    }
}
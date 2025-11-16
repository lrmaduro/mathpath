package vista;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
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
    
    // Esta variable nos dirá si el usuario presionó "Guardar"
    private boolean guardado = false;

    public CrearAulaDialog(JFrame parent) {
        // 'super(parent, true)' crea un diálogo MODAL
        // (bloquea la ventana principal hasta que se cierre)
        super(parent, "Crear Nueva Aula", true);
        
        // --- Configuración del Panel de Formulario ---
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

        // Fila 2: Código
        gbc.gridx = 0; gbc.gridy = 1;
        panelFormulario.add(new JLabel("Código:"), gbc);
        gbc.gridx = 1; gbc.gridy = 1;
        txtCodigo = new JTextField(20);
        panelFormulario.add(txtCodigo, gbc);

        // Fila 3: Descripción
        gbc.gridx = 0; gbc.gridy = 2;
        panelFormulario.add(new JLabel("Descripción:"), gbc);
        gbc.gridx = 1; gbc.gridy = 2;
        txtDescripcion = new JTextField(20);
        panelFormulario.add(txtDescripcion, gbc);
        
        // --- Configuración del Panel de Botones ---
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        btnGuardar = new JButton("Guardar");
        btnCancelar = new JButton("Cancelar");
        panelBotones.add(btnGuardar);
        panelBotones.add(btnCancelar);

        // --- Lógica de los Botones ---
        
        btnGuardar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // (Aquí iría la validación, ej. que los campos no estén vacíos)
                guardado = true;
                dispose(); // Cierra el diálogo
            }
        });
        
        btnCancelar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                guardado = false;
                dispose(); // Cierra el diálogo
            }
        });

        // --- Ensamblaje del Diálogo ---
        this.setLayout(new BorderLayout());
        this.add(panelFormulario, BorderLayout.CENTER);
        this.add(panelBotones, BorderLayout.SOUTH);
        
        this.pack(); // Ajusta el tamaño automáticamente
        this.setLocationRelativeTo(parent); // Lo centra sobre la ventana principal
    }

    // --- Métodos para el Controlador ---

    /**
     * El controlador llamará a esto después de que se cierre el diálogo
     * para saber si el usuario presionó "Guardar".
     */
    public boolean isGuardado() {
        return guardado;
    }
    
    /**
     * El controlador llamará a esto para obtener los datos del formulario
     * y crear el objeto Aula.
     */
    public Aula getNuevaAula() {
        // (Aquí faltaría un ID, pero por ahora lo simulamos)
        String id = "a" + (int)(Math.random() * 1000); // ID aleatorio
        return new Aula(
                id,
                txtNombre.getText(),
                txtCodigo.getText(),
                txtDescripcion.getText()
        );
    }
}
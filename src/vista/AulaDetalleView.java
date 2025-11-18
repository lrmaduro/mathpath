package vista;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout; // Nuevo
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane; // Nuevo
import javax.swing.border.EmptyBorder;
import modelo.Aula;

public class AulaDetalleView extends JPanel {

    private JLabel lblTituloAula;
    private JLabel lblCodigoAula;
    private JButton btnVolver;    
    public JButton btnCrearActividad;
    public JPanel panelListaActividades; // Lo hacemos público

    public AulaDetalleView() {
        this.setLayout(new BorderLayout(10, 10));
        this.setBorder(new EmptyBorder(10, 10, 10, 10));
        this.setOpaque(false); // Para que tome el fondo blanco
        
        // --- Panel de Cabecera (Título y botón Volver) ---
        JPanel panelCabecera = new JPanel(new BorderLayout());
        panelCabecera.setOpaque(false);
        
        btnVolver = new JButton("<- Volver a Mis Aulas");
        panelCabecera.add(btnVolver, BorderLayout.WEST);
        
        lblTituloAula = new JLabel("Nombre del Aula");
        lblTituloAula.setFont(lblTituloAula.getFont().deriveFont(Font.BOLD, 24.0f));
        lblTituloAula.setHorizontalAlignment(JLabel.CENTER);
        panelCabecera.add(lblTituloAula, BorderLayout.CENTER);
        
        // --- Panel de Contenido (Código y Actividades) ---
        JPanel panelContenido = new JPanel(new BorderLayout(10, 10));
        panelContenido.setOpaque(false);
        
        lblCodigoAula = new JLabel("Código: XXX");
        panelContenido.add(lblCodigoAula, BorderLayout.NORTH);
        
        // --- NUEVO: Panel para la lista de actividades ---
        // Usamos BoxLayout para apilarlas verticalmente
        panelListaActividades = new JPanel();
        panelListaActividades.setLayout(new BoxLayout(panelListaActividades, BoxLayout.Y_AXIS));
        panelListaActividades.setBackground(Color.WHITE);
        
        // Lo metemos en un JScrollPane para que haya scroll si hay muchas
        JScrollPane scrollPane = new JScrollPane(panelListaActividades);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Actividades"));
        
        JPanel panelBotonCrear = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        panelBotonCrear.setOpaque(false);
        btnCrearActividad = new JButton("+ Crear Actividad");
        panelBotonCrear.add(btnCrearActividad);
        
        panelContenido.add(scrollPane, BorderLayout.CENTER);
        panelContenido.add(panelBotonCrear, BorderLayout.SOUTH);
        // --- FIN DE LO NUEVO ---
        
        // --- Ensamblaje ---
        this.add(panelCabecera, BorderLayout.NORTH);
        this.add(panelContenido, BorderLayout.CENTER);
    }
    
    // --- Métodos para el Controlador ---
    
    /**
     * El controlador llamará a esto para "cargar" los datos
     * del aula seleccionada en esta vista.
     */
    public void actualizarDatos(Aula aula) {
        lblTituloAula.setText(aula.getNombre());
        lblCodigoAula.setText("Código de invitación: " + aula.getCodigo());
        
        // Limpiamos el panel por si acaso
        panelListaActividades.removeAll();
        panelListaActividades.revalidate();
        panelListaActividades.repaint();
    }
    
    public void addVolverListener(ActionListener listener) {
        btnVolver.addActionListener(listener);
    }
    
    public void addCrearActividadListener(ActionListener listener) {
        btnCrearActividad.addActionListener(listener);
    }
}
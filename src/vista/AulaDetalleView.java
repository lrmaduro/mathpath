package vista;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import modelo.Aula;

public class AulaDetalleView extends JPanel {

    private JLabel lblTituloAula;
    private JLabel lblCodigoAula;
    private JButton btnVolver;     
    
    // Componentes públicos para el controlador
    public JButton btnCrearActividad;
    public JPanel panelListaActividades; 

    // Paleta Pastel (Coherente con el Dashboard)
    private final Color COLOR_FONDO_PANEL = new Color(250, 250, 250);
    private final Color COLOR_TEXTO_TITULO = new Color(44, 62, 80);
    private final Color COLOR_VERDE_ACCION = new Color(169, 223, 191); // Menta
    private final Color COLOR_BADGE_CODIGO = new Color(214, 234, 248); // Azul claro
    private final Color COLOR_TEXTO_CODIGO = new Color(40, 116, 166);

    public AulaDetalleView() {
        this.setLayout(new BorderLayout(20, 20)); // Espaciado general
        this.setBorder(new EmptyBorder(20, 30, 20, 30)); // Márgenes externos
        this.setBackground(COLOR_FONDO_PANEL);
        this.setOpaque(false); // Transparente para heredar el fondo del dashboard
        
        // --- 1. CABECERA (Volver + Título + Código) ---
        JPanel panelCabecera = new JPanel(new BorderLayout());
        panelCabecera.setOpaque(false);
        
        // A. Botón Volver (Estilo minimalista "Link")
        btnVolver = new JButton("← Volver a Mis Clases");
        btnVolver.setFont(new Font("SansSerif", Font.BOLD, 12));
        btnVolver.setForeground(new Color(100, 100, 120));
        btnVolver.setBorder(null);
        btnVolver.setContentAreaFilled(false);
        btnVolver.setFocusPainted(false);
        btnVolver.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnVolver.setHorizontalAlignment(JButton.LEFT);
        
        // B. Título y Badge de Código
        JPanel panelInfo = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 0));
        panelInfo.setOpaque(false);
        
        lblTituloAula = new JLabel("Nombre del Aula");
        lblTituloAula.setFont(new Font("SansSerif", Font.BOLD, 28));
        lblTituloAula.setForeground(COLOR_TEXTO_TITULO);
        
        // Badge del Código (Etiqueta redondeada visualmente)
        lblCodigoAula = new JLabel("CÓDIGO: ???");
        lblCodigoAula.setFont(new Font("Monospaced", Font.BOLD, 14));
        lblCodigoAula.setForeground(COLOR_TEXTO_CODIGO);
        lblCodigoAula.setOpaque(true);
        lblCodigoAula.setBackground(COLOR_BADGE_CODIGO);
        lblCodigoAula.setBorder(new EmptyBorder(5, 10, 5, 10)); // Padding interno
        
        panelInfo.add(lblTituloAula);
        panelInfo.add(lblCodigoAula);
        
        // Armamos la cabecera
        JPanel headerContainer = new JPanel(new BorderLayout(0, 10));
        headerContainer.setOpaque(false);
        headerContainer.add(btnVolver, BorderLayout.NORTH);
        headerContainer.add(panelInfo, BorderLayout.CENTER);
        headerContainer.add(new javax.swing.JSeparator(), BorderLayout.SOUTH); // Línea divisoria sutil
        
        // --- 2. CONTENIDO (Lista de Actividades) ---
        JPanel panelContenido = new JPanel(new BorderLayout(10, 10));
        panelContenido.setOpaque(false);
        panelContenido.setBorder(new EmptyBorder(10, 0, 0, 0));
        
        // Título de la sección
        JLabel lblSubtitulo = new JLabel("Cronograma de Actividades");
        lblSubtitulo.setFont(new Font("SansSerif", Font.BOLD, 18));
        lblSubtitulo.setForeground(new Color(120, 130, 140));
        lblSubtitulo.setBorder(new EmptyBorder(0, 5, 10, 0));
        
        // El panel donde se apilan las actividades
        panelListaActividades = new JPanel();
        panelListaActividades.setLayout(new BoxLayout(panelListaActividades, BoxLayout.Y_AXIS));
        panelListaActividades.setBackground(Color.WHITE);
        // Un borde sutil para la "hoja" de actividades
        panelListaActividades.setBorder(new EmptyBorder(10, 10, 10, 10)); 
        
        // ScrollPane limpio (sin bordes feos)
        JScrollPane scrollPane = new JScrollPane(panelListaActividades);
        scrollPane.setBorder(BorderFactory.createLineBorder(new Color(230, 230, 230))); // Borde muy sutil
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        
        panelContenido.add(lblSubtitulo, BorderLayout.NORTH);
        panelContenido.add(scrollPane, BorderLayout.CENTER);
        
        // --- 3. FOOTER (Botón Crear) ---
        JPanel panelFooter = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        panelFooter.setOpaque(false);
        panelFooter.setBorder(new EmptyBorder(15, 0, 0, 0));
        
        btnCrearActividad = new JButton("+ Nueva Actividad");
        estilarBotonAccion(btnCrearActividad);
        
        panelFooter.add(btnCrearActividad);
        panelContenido.add(panelFooter, BorderLayout.SOUTH);
        
        // --- Ensamblaje Final ---
        this.add(headerContainer, BorderLayout.NORTH);
        this.add(panelContenido, BorderLayout.CENTER);
    }
    
    // Método auxiliar de estilo
    private void estilarBotonAccion(JButton btn) {
        btn.setBackground(COLOR_VERDE_ACCION);
        btn.setForeground(new Color(30, 80, 40)); // Verde oscuro texto
        btn.setFont(new Font("SansSerif", Font.BOLD, 14));
        btn.setFocusPainted(false);
        btn.setBorder(new EmptyBorder(10, 20, 10, 20));
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        // Hover simple
        btn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btn.setBackground(new Color(149, 203, 171)); // Un poco más oscuro
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btn.setBackground(COLOR_VERDE_ACCION);
            }
        });
    }
    
    // --- Métodos para el Controlador (Lógica intacta) ---
    
    public void actualizarDatos(Aula aula) {
        lblTituloAula.setText(aula.getNombre());
        lblCodigoAula.setText("CÓDIGO: " + aula.getCodigo());
        
        // Limpiamos el panel y añadimos un espacio arriba
        panelListaActividades.removeAll();
        panelListaActividades.add(Box.createVerticalStrut(5));
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
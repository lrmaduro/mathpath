package vista;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import modelo.Aula;

public class AulaDetalleViewEstudiante extends JPanel {

    private JLabel lblTituloAula;
    private JLabel lblCodigoAula;
    private JButton btnVolver;

    // Componentes públicos para el controlador
    public JButton btnCrearActividad; // Se mantiene por compatibilidad o se oculta
    public JPanel panelPorHacer;
    public JPanel panelHechas;
    public JPanel panelExpiradas;
    private javax.swing.JTabbedPane tabbedPane;

    // Paleta Pastel (Coherente con el Dashboard)
    private final Color COLOR_FONDO_PANEL = new Color(250, 250, 250);
    private final Color COLOR_TEXTO_TITULO = new Color(44, 62, 80);
    // private final Color COLOR_VERDE_ACCION = new Color(169, 223, 191); // Menta
    private final Color COLOR_BADGE_CODIGO = new Color(214, 234, 248); // Azul claro
    private final Color COLOR_TEXTO_CODIGO = new Color(40, 116, 166);

    public AulaDetalleViewEstudiante() {
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

        // --- 2. CONTENIDO (TABS ESTUDIANTE) ---

        // Inicializar paneles
        panelPorHacer = new JPanel();
        panelPorHacer.setLayout(new BoxLayout(panelPorHacer, BoxLayout.Y_AXIS));
        panelPorHacer.setBackground(Color.WHITE);
        panelPorHacer.setBorder(new EmptyBorder(10, 10, 10, 10));

        panelHechas = new JPanel();
        panelHechas.setLayout(new BoxLayout(panelHechas, BoxLayout.Y_AXIS));
        panelHechas.setBackground(Color.WHITE);
        panelHechas.setBorder(new EmptyBorder(10, 10, 10, 10));

        panelExpiradas = new JPanel();
        panelExpiradas.setLayout(new BoxLayout(panelExpiradas, BoxLayout.Y_AXIS));
        panelExpiradas.setBackground(Color.WHITE);
        panelExpiradas.setBorder(new EmptyBorder(10, 10, 10, 10));

        // Crear TabbedPane
        tabbedPane = new javax.swing.JTabbedPane();
        tabbedPane.setFont(new Font("SansSerif", Font.BOLD, 12));

        tabbedPane.addTab("Por Hacer", crearScroll(panelPorHacer));
        tabbedPane.addTab("Hechas", crearScroll(panelHechas));
        tabbedPane.addTab("Expiradas", crearScroll(panelExpiradas));

        // Botón Crear Actividad (Oculto o no usado, pero declarado para evitar errores
        // si se referencia)
        btnCrearActividad = new JButton("+ Nueva Actividad");
        btnCrearActividad.setVisible(false);

        // --- Ensamblaje Final ---
        this.add(headerContainer, BorderLayout.NORTH);
        this.add(tabbedPane, BorderLayout.CENTER);
    }

    private JScrollPane crearScroll(JPanel panel) {
        JScrollPane scroll = new JScrollPane(panel);
        scroll.setBorder(BorderFactory.createLineBorder(new Color(230, 230, 230)));
        scroll.getVerticalScrollBar().setUnitIncrement(16);
        scroll.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        return scroll;
    }

    // --- Métodos para el Controlador ---

    public void actualizarDatos(Aula aula) {
        lblTituloAula.setText(aula.getNombre());
        lblCodigoAula.setText("CÓDIGO: " + aula.getCodigo());

        // Limpiar paneles
        panelPorHacer.removeAll();
        panelHechas.removeAll();
        panelExpiradas.removeAll();

        this.revalidate();
        this.repaint();
    }

    public void addVolverListener(ActionListener listener) {
        btnVolver.addActionListener(listener);
    }
}

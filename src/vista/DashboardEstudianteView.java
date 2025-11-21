package vista;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import modelo.Usuario;

public class DashboardEstudianteView extends JPanel {
    
    // Componentes principales
    private JPanel panelMenuLateral;
    private JPanel panelContenidoPrincipal;
    private CardLayout cardLayoutContenido;
    
    // Botones del Menú Lateral
    public JButton btnMisAulas;
    public JButton btnNotas; // Diferente al docente (antes Reportes)
    public JButton btnPerfil;
    public JButton btnCerrarSesion;
    
    // --- PANEL 1: MIS AULAS (Grilla de tarjetas) ---
    public JPanel panelAulasContainer; // Aquí añadiremos las AulaCard
    public JButton btnUnirseAula;      // Botón específico de estudiante
    
    // --- PANEL 2: DETALLE AULA (Reutilizado) ---
    private AulaDetalleView panelAulaDetalle;
    
    // Constantes para navegación interna
    public static final String PANEL_MIS_AULAS = "MIS_AULAS";
    public static final String PANEL_AULA_DETALLE = "AULA_DETALLE";

    public DashboardEstudianteView(Usuario estudiante) {
        this.setLayout(new BorderLayout());
        
        // 1. Inicializar Menú Lateral (Igual que docente pero adaptado)
        inicializarMenuLateral(estudiante);
        
        // 2. Inicializar Área de Contenido (CardLayout)
        panelContenidoPrincipal = new JPanel();
        cardLayoutContenido = new CardLayout();
        panelContenidoPrincipal.setLayout(cardLayoutContenido);
        
        // 3. Crear Sub-Paneles
        JPanel panelMisAulas = crearPanelMisAulas();
        panelAulaDetalle = new AulaDetalleView(); // REUTILIZAMOS TU VISTA EXISTENTE
        
        // 4. Añadir paneles al CardLayout
        panelContenidoPrincipal.add(panelMisAulas, PANEL_MIS_AULAS);
        panelContenidoPrincipal.add(panelAulaDetalle, PANEL_AULA_DETALLE);
        
        // 5. Ensamblaje final
        this.add(panelMenuLateral, BorderLayout.WEST);
        this.add(panelContenidoPrincipal, BorderLayout.CENTER);
    }
    
    private void inicializarMenuLateral(Usuario usuario) {
        panelMenuLateral = new JPanel(new GridLayout(10, 1, 5, 5));
        panelMenuLateral.setPreferredSize(new java.awt.Dimension(200, 0));
        panelMenuLateral.setBackground(new Color(50, 50, 50)); // Gris oscuro
        panelMenuLateral.setBorder(new EmptyBorder(20, 10, 20, 10));
        
        JLabel lblBienvenida = new JLabel("Hola, " + usuario.getNombre());
        lblBienvenida.setForeground(Color.WHITE);
        lblBienvenida.setHorizontalAlignment(JLabel.CENTER);
        
        btnMisAulas = crearBotonMenu("Mis Aulas");
        btnNotas = crearBotonMenu("Mis Notas");
        btnPerfil = crearBotonMenu("Mi Perfil");
        btnCerrarSesion = crearBotonMenu("Cerrar Sesión");
        btnCerrarSesion.setBackground(new Color(192, 57, 43)); // Rojo oscuro
        
        panelMenuLateral.add(lblBienvenida);
        panelMenuLateral.add(btnMisAulas);
        panelMenuLateral.add(btnNotas);
        panelMenuLateral.add(btnPerfil);
        panelMenuLateral.add(btnCerrarSesion);
    }
    
    private JButton crearBotonMenu(String texto) {
        JButton btn = new JButton(texto);
        btn.setFocusPainted(false);
        return btn;
    }
    
    private JPanel crearPanelMisAulas() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.WHITE);
        
        // Cabecera: Título y Botón "Unirse"
        JPanel header = new JPanel(new BorderLayout());
        header.setBorder(new EmptyBorder(20, 20, 20, 20));
        header.setBackground(Color.WHITE);
        
        JLabel title = new JLabel("Mis Clases Inscritas");
        title.setFont(new Font("SansSerif", Font.BOLD, 24));
        
        btnUnirseAula = new JButton("+ Unirse a una Clase");
        btnUnirseAula.setBackground(new Color(46, 204, 113)); // Verde
        btnUnirseAula.setForeground(Color.WHITE);
        
        header.add(title, BorderLayout.WEST);
        header.add(btnUnirseAula, BorderLayout.EAST);
        
        // Contenedor de Tarjetas (FlowLayout para que se acomoden)
        panelAulasContainer = new JPanel(new FlowLayout(FlowLayout.LEFT, 20, 20));
        panelAulasContainer.setBackground(Color.WHITE);
        
        JScrollPane scroll = new JScrollPane(panelAulasContainer);
        scroll.setBorder(null);
        
        panel.add(header, BorderLayout.NORTH);
        panel.add(scroll, BorderLayout.CENTER);
        
        return panel;
    }
    
    // --- Getters y Métodos de Utilidad ---
    
    public AulaDetalleView getPanelAulaDetalle() {
        return panelAulaDetalle;
    }
    
    public void showContenidoCard(String name) {
        cardLayoutContenido.show(panelContenidoPrincipal, name);
    }
    
    // Listeners delegados
    public void addUnirseAulaListener(ActionListener al) { btnUnirseAula.addActionListener(al); }
    public void addMisAulasListener(ActionListener al) { btnMisAulas.addActionListener(al); }
    public void addCerrarSesionListener(ActionListener al) { btnCerrarSesion.addActionListener(al); }
}
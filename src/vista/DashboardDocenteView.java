package vista;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import modelo.Usuario;

public class DashboardDocenteView extends JPanel {
    
    // --- ESTRUCTURA PRINCIPAL ---
    private BorderLayout layoutPrincipal;
    private JPanel panelMenuLateral;
    private JPanel panelContenidoPrincipal;
    private CardLayout cardLayoutContenido;

    // --- COMPONENTES PÚBLICOS ---
    public JButton btnMisAulas;
    public JButton btnActividades;
    public JButton btnReportes;
    public JButton btnPerfil;
    public JButton btnCerrarSesion;
    
    // Componentes Actividades
    public JList<String> listaTemas;
    public DefaultListModel<String> listModelTemas;
    public JTextField txtNuevoTema;
    public JButton btnAgregarTema;
    public JTabbedPane tabbedPaneActividades;
    public JPanel panelBancoEjercicios;
    public JPanel panelListaEjercicios;
    public JButton btnCrearEjercicio;
    
    // Componentes Aulas
    public JButton btnCrearAula;
    public JPanel panelAulas; 
    public AulaDetalleView panelAulaDetalle;

    // Constantes
    public static final String PANEL_AULAS = "AULAS";
    public static final String PANEL_ACTIVIDADES = "ACTIVIDADES";
    public static final String PANEL_REPORTES = "REPORTES";
    public static final String PANEL_PERFIL = "PERFIL";
    public static final String PANEL_AULA_DETALLE = "AULA_DETALLE";
    
    // --- 🎨 NUEVA PALETA DE COLORES PASTELES ---
    // Sidebar: Azul Lavanda Suave
    private final Color COLOR_SIDEBAR = new Color(232, 240, 254); 
    // Texto Sidebar: Gris Azulado Oscuro (para contraste)
    private final Color COLOR_TEXTO_MENU = new Color(74, 90, 110); 
    // Fondo Contenido: Blanco Humo (muy limpio)
    private final Color COLOR_FONDO_CONTENIDO = new Color(250, 250, 250); 
    // Fondo del Panel Aulas: Beige/Crema suave ("Cariño" visual)
    private final Color COLOR_FONDO_AULAS = new Color(254, 249, 231); 
    // Botones Acción: Verde Menta Pastel
    private final Color COLOR_BTN_ACCION = new Color(169, 223, 191); 
    // Botón Salir: Rosa Salmón Pastel
    private final Color COLOR_BTN_SALIR = new Color(245, 183, 177); 

    public DashboardDocenteView(Usuario docente) {
        layoutPrincipal = new BorderLayout(0, 0);
        this.setLayout(layoutPrincipal);
        
        // 1. INICIALIZAR MENÚ LATERAL
        inicializarMenuLateral(docente);
        
        // 2. INICIALIZAR CONTENIDO
        cardLayoutContenido = new CardLayout();
        panelContenidoPrincipal = new JPanel(cardLayoutContenido);
        panelContenidoPrincipal.setBackground(COLOR_FONDO_CONTENIDO);

        // 3. CREAR PANELES
        JPanel wrapperAulas = crearPanelAulas(); // Aquí está el diseño mejorado
        JPanel panelActividades = crearPanelActividades();
        JPanel panelReportes = crearPanelSimple("Reportes y Estadísticas");
        JPanel panelPerfil = crearPanelSimple("Perfil de Usuario: " + docente.getNombre());
        
        panelAulaDetalle = new AulaDetalleView();
        
        // 4. AÑADIR AL LAYOUT
        panelContenidoPrincipal.add(wrapperAulas, PANEL_AULAS);
        panelContenidoPrincipal.add(panelActividades, PANEL_ACTIVIDADES);
        panelContenidoPrincipal.add(panelReportes, PANEL_REPORTES);
        panelContenidoPrincipal.add(panelPerfil, PANEL_PERFIL);
        panelContenidoPrincipal.add(panelAulaDetalle, PANEL_AULA_DETALLE);
        
        this.add(panelMenuLateral, BorderLayout.WEST);
        this.add(panelContenidoPrincipal, BorderLayout.CENTER);
    }
    
    private void inicializarMenuLateral(Usuario usuario) {
        panelMenuLateral = new JPanel();
        panelMenuLateral.setLayout(new BoxLayout(panelMenuLateral, BoxLayout.Y_AXIS));
        panelMenuLateral.setPreferredSize(new Dimension(240, 0));
        panelMenuLateral.setBackground(COLOR_SIDEBAR);
        // Borde derecho sutil para separar
        panelMenuLateral.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 1, new Color(220, 220, 230)));

        // -- Info Usuario --
        JPanel panelUser = new JPanel(new GridLayout(2, 1));
        panelUser.setOpaque(false);
        panelUser.setBorder(new EmptyBorder(30, 25, 30, 20));
        
        JLabel lblHola = new JLabel("¡Hola de nuevo!");
        lblHola.setForeground(new Color(120, 120, 140));
        lblHola.setFont(new Font("SansSerif", Font.PLAIN, 12));
        
        JLabel lblNombre = new JLabel(usuario.getNombre());
        lblNombre.setForeground(COLOR_TEXTO_MENU);
        lblNombre.setFont(new Font("SansSerif", Font.BOLD, 18));
        
        panelUser.add(lblHola);
        panelUser.add(lblNombre);
        panelMenuLateral.add(panelUser);
        
        // -- Botones --
        btnMisAulas = crearBotonMenu("Mis Aulas");
        btnActividades = crearBotonMenu("Actividades");
        btnReportes = crearBotonMenu("Reportes");
        btnPerfil = crearBotonMenu("Perfil");
        
        panelMenuLateral.add(btnMisAulas);
        panelMenuLateral.add(btnActividades);
        panelMenuLateral.add(btnReportes);
        panelMenuLateral.add(btnPerfil);
        
        panelMenuLateral.add(javax.swing.Box.createVerticalGlue());
        
        // -- Botón Salir --
        btnCerrarSesion = crearBotonMenu("Cerrar Sesión");
        // Estilo especial para salir (más pequeño o color distinto)
        btnCerrarSesion.setBackground(COLOR_BTN_SALIR);
        btnCerrarSesion.setForeground(new Color(150, 50, 50)); // Texto rojo oscuro
        panelMenuLateral.add(btnCerrarSesion);
        panelMenuLateral.add(javax.swing.Box.createVerticalStrut(20));
    }

    private JButton crearBotonMenu(String texto) {
        JButton btn = new JButton(texto);
        btn.setMaximumSize(new Dimension(Integer.MAX_VALUE, 50));
        btn.setAlignmentX(CENTER_ALIGNMENT);
        btn.setFont(new Font("SansSerif", Font.BOLD, 14));
        btn.setForeground(COLOR_TEXTO_MENU);
        btn.setBackground(COLOR_SIDEBAR);
        btn.setBorder(new EmptyBorder(12, 25, 12, 20));
        btn.setFocusPainted(false);
        btn.setContentAreaFilled(false); 
        btn.setOpaque(true); 
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.setHorizontalAlignment(JButton.LEFT);
        
        // Hover Effect Pastel: Se oscurece un poquito el tono pastel
        btn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                if(!btn.getText().equals("Cerrar Sesión")) 
                    btn.setBackground(new Color(214, 234, 248)); // Azulito más fuerte
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                 if(!btn.getText().equals("Cerrar Sesión")) 
                     btn.setBackground(COLOR_SIDEBAR);
            }
        });
        return btn;
    }
    
    private JPanel crearPanelAulas() {
        JPanel wrapper = new JPanel(new BorderLayout());
        wrapper.setBackground(COLOR_FONDO_AULAS); // Color Crema de fondo
        
        // Header Transparente sobre el fondo crema
        JPanel header = new JPanel(new BorderLayout());
        header.setOpaque(false); 
        header.setBorder(new EmptyBorder(30, 40, 10, 40));
        
        JLabel title = new JLabel("Mis Clases");
        title.setFont(new Font("SansSerif", Font.BOLD, 26));
        title.setForeground(new Color(93, 109, 126)); // Gris azulado
        
        btnCrearAula = new JButton("+ Nueva Aula");
        estilarBotonAccion(btnCrearAula, COLOR_BTN_ACCION); // Verde Menta
        btnCrearAula.setForeground(new Color(30, 80, 40)); // Texto verde oscuro para contraste
        
        header.add(title, BorderLayout.WEST);
        header.add(btnCrearAula, BorderLayout.EAST);
        
        // Contenedor de Tarjetas
        // Aquí es donde ponemos el "cariño": márgenes amplios y alineación
        panelAulas = new JPanel(new FlowLayout(FlowLayout.LEFT, 25, 25));
        panelAulas.setOpaque(false); // Para que se vea el color crema del fondo
        
        JScrollPane scroll = new JScrollPane(panelAulas);
        scroll.setBorder(null);
        scroll.getViewport().setOpaque(false); // Importante para ver el fondo
        scroll.setOpaque(false);
        
        wrapper.add(header, BorderLayout.NORTH);
        wrapper.add(scroll, BorderLayout.CENTER);
        
        return wrapper;
    }
    
    private JPanel crearPanelActividades() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(COLOR_FONDO_CONTENIDO); // Fondo general suave
        
        // --- 1. HEADER (Título "Biblioteca de Recursos") ---
        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(Color.WHITE);
        // Borde inferior sutil
        header.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(230, 230, 230)),
            new EmptyBorder(25, 40, 25, 40)
        ));
        
        JLabel title = new JLabel("Biblioteca de Recursos");
        title.setFont(new Font("SansSerif", Font.BOLD, 26));
        title.setForeground(new Color(93, 109, 126)); // Gris Azulado
        
        JLabel subtitle = new JLabel("Gestiona tus temas y banco de preguntas");
        subtitle.setFont(new Font("SansSerif", Font.PLAIN, 14));
        subtitle.setForeground(new Color(160, 160, 160));
        
        JPanel panelTitulos = new JPanel(new GridLayout(2, 1));
        panelTitulos.setOpaque(false);
        panelTitulos.add(title);
        panelTitulos.add(subtitle);
        
        header.add(panelTitulos, BorderLayout.WEST);
        panel.add(header, BorderLayout.NORTH);

        // --- 2. TABS (Pestañas) ---
        tabbedPaneActividades = new JTabbedPane();
        tabbedPaneActividades.setFont(new Font("SansSerif", Font.BOLD, 14));
        tabbedPaneActividades.setBackground(Color.WHITE);
        tabbedPaneActividades.setBorder(new EmptyBorder(15, 30, 15, 30)); // Margen externo para que no pegue al borde
        
        // --- TAB 1: TEMAS ---
        // Usamos un panel redondeado simulado con bordes blancos
        JPanel panelTemas = new JPanel(new BorderLayout(15, 15));
        panelTemas.setBackground(Color.WHITE);
        panelTemas.setBorder(new EmptyBorder(20, 20, 20, 20));
        
        // Lista estilizada
        listModelTemas = new DefaultListModel<>();
        listaTemas = new JList<>(listModelTemas);
        estilarLista(listaTemas); // <--- Método auxiliar (ver abajo)
        
        JScrollPane scrollTemas = new JScrollPane(listaTemas);
        scrollTemas.setBorder(BorderFactory.createLineBorder(new Color(240, 240, 240)));
        panelTemas.add(scrollTemas, BorderLayout.CENTER);
        
        // Panel inferior (Input + Botón)
        JPanel panelAddTema = new JPanel(new BorderLayout(10, 0));
        panelAddTema.setOpaque(false);
        panelAddTema.setBorder(new EmptyBorder(10, 0, 0, 0));
        
        txtNuevoTema = new JTextField();
        estilarCampoTexto(txtNuevoTema); // <--- Método auxiliar
        
        btnAgregarTema = new JButton("Añadir Tema");
        estilarBotonAccion(btnAgregarTema, new Color(174, 214, 241)); // Azul Pastel
        btnAgregarTema.setForeground(new Color(40, 90, 130)); // Texto oscuro para contraste
        
        panelAddTema.add(new JLabel("Nuevo Tema: "), BorderLayout.WEST);
        panelAddTema.add(txtNuevoTema, BorderLayout.CENTER);
        panelAddTema.add(btnAgregarTema, BorderLayout.EAST);
        panelTemas.add(panelAddTema, BorderLayout.SOUTH);
        
        // --- TAB 2: EJERCICIOS ---
        JPanel panelBancoEjercicios = new JPanel(new BorderLayout(15, 15));
        panelBancoEjercicios.setBackground(Color.WHITE);
        panelBancoEjercicios.setBorder(new EmptyBorder(20, 20, 20, 20));
        
        panelListaEjercicios = new JPanel();
        panelListaEjercicios.setLayout(new BoxLayout(panelListaEjercicios, BoxLayout.Y_AXIS));
        panelListaEjercicios.setBackground(Color.WHITE);
        
        JPanel wrapperLista = new JPanel(new BorderLayout());
        wrapperLista.setBackground(Color.WHITE);
        wrapperLista.add(panelListaEjercicios, BorderLayout.NORTH);
        
        JScrollPane scrollEjercicios = new JScrollPane(wrapperLista);
        scrollEjercicios.setBorder(BorderFactory.createLineBorder(new Color(240, 240, 240)));
        scrollEjercicios.getVerticalScrollBar().setUnitIncrement(16);
        
        panelBancoEjercicios.add(scrollEjercicios, BorderLayout.CENTER);
        
        btnCrearEjercicio = new JButton("+ Crear Nuevo Ejercicio");
        estilarBotonAccion(btnCrearEjercicio, COLOR_BTN_ACCION); // Verde Pastel
        btnCrearEjercicio.setForeground(new Color(30, 80, 40));
        
        JPanel panelBtnEj = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        panelBtnEj.setBackground(Color.WHITE);
        panelBtnEj.add(btnCrearEjercicio);
        panelBancoEjercicios.add(panelBtnEj, BorderLayout.SOUTH);

        // Añadimos las tabs
        tabbedPaneActividades.addTab("Gestión de Temas", panelTemas);
        tabbedPaneActividades.addTab("Banco de Ejercicios", panelBancoEjercicios);
        
        panel.add(tabbedPaneActividades, BorderLayout.CENTER);
        return panel;
    }

    // --- MÉTODOS AUXILIARES DE ESTILO (Agrégalos al final de la clase) ---

    private void estilarCampoTexto(JTextField txt) {
        txt.setFont(new Font("SansSerif", Font.PLAIN, 14));
        txt.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(200, 200, 200)), 
            new EmptyBorder(8, 10, 8, 10) // Padding interno
        ));
        txt.setBackground(new Color(250, 252, 254)); // Azulito muy pálido casi blanco
    }

    private void estilarLista(JList list) {
        list.setFont(new Font("SansSerif", Font.PLAIN, 15));
        list.setFixedCellHeight(40); // Filas más altas y cómodas
        list.setSelectionBackground(new Color(232, 240, 254)); // Azul selección Google style
        list.setSelectionForeground(new Color(44, 62, 80));
        list.setBackground(Color.WHITE);
        list.setBorder(new EmptyBorder(5, 5, 5, 5));
    }
    
    private JPanel crearPanelSimple(String texto) {
        JPanel p = new JPanel();
        p.setBackground(COLOR_FONDO_CONTENIDO);
        p.add(new JLabel(texto));
        return p;
    }
    
    private void estilarBotonAccion(JButton btn, Color colorFondo) {
        btn.setBackground(colorFondo);
        btn.setFont(new Font("SansSerif", Font.BOLD, 14));
        btn.setFocusPainted(false);
        btn.setBorder(new EmptyBorder(10, 20, 10, 20));
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
    }

    // --- DELEGADOS ---
    public void showContenidoCard(String panelName) { cardLayoutContenido.show(panelContenidoPrincipal, panelName); }
    public void addMisAulasListener(ActionListener al) { btnMisAulas.addActionListener(al); }
    public void addActividadesListener(ActionListener al) { btnActividades.addActionListener(al); }
    public void addReportesListener(ActionListener al) { btnReportes.addActionListener(al); }
    public void addPerfilListener(ActionListener al) { btnPerfil.addActionListener(al); }
    public void addCerrarSesionListener(ActionListener al) { btnCerrarSesion.addActionListener(al); }
    public void addCrearAulaListener(ActionListener al) { btnCrearAula.addActionListener(al); }
    public void addAgregarTemaListener(ActionListener al) { btnAgregarTema.addActionListener(al); }
    public void addCrearEjercicioListener(ActionListener al) { btnCrearEjercicio.addActionListener(al); }
}
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
    public javax.swing.JComboBox<String> cmbFiltroTemaEjercicios; // Nuevo Filtro
    public JButton btnCrearEjercicio;

    // Componentes Aulas
    public JButton btnCrearAula;
    public JPanel panelAulas;
    public AulaDetalleViewDocente panelAulaDetalle;

    // --- ETIQUETA DE USUARIO (Para actualizar el nombre) ---
    private JLabel lblNombreUsuario;

    public ReportesView panelReportesView;
    public PerfilView panelPerfilView;

    // Constantes
    public static final String PANEL_AULAS = "AULAS";
    public static final String PANEL_ACTIVIDADES = "ACTIVIDADES";
    public static final String PANEL_REPORTES = "REPORTES";
    public static final String PANEL_PERFIL = "PERFIL";
    public static final String PANEL_AULA_DETALLE = "AULA_DETALLE";

    // --- 🎨 PALETA DE COLORES PASTELES ---
    private final Color COLOR_SIDEBAR = new Color(232, 240, 254);
    private final Color COLOR_TEXTO_MENU = new Color(74, 90, 110);
    private final Color COLOR_FONDO_CONTENIDO = new Color(250, 250, 250);
    private final Color COLOR_FONDO_AULAS = new Color(254, 249, 231);
    private final Color COLOR_BTN_ACCION = new Color(169, 223, 191);
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
        JPanel wrapperAulas = crearPanelAulas();
        JPanel panelActividades = crearPanelActividades();
        panelReportesView = new ReportesView();
        panelPerfilView = new PerfilView(docente, false);

        panelAulaDetalle = new AulaDetalleViewDocente();

        // 4. AÑADIR AL LAYOUT
        panelContenidoPrincipal.add(wrapperAulas, PANEL_AULAS);
        panelContenidoPrincipal.add(panelActividades, PANEL_ACTIVIDADES);
        panelContenidoPrincipal.add(panelReportesView, PANEL_REPORTES);
        panelContenidoPrincipal.add(panelPerfilView, PANEL_PERFIL);
        panelContenidoPrincipal.add(panelAulaDetalle, PANEL_AULA_DETALLE);

        this.add(panelMenuLateral, BorderLayout.WEST);
        this.add(panelContenidoPrincipal, BorderLayout.CENTER);
    }

    private void inicializarMenuLateral(Usuario usuario) {
        panelMenuLateral = new JPanel();
        panelMenuLateral.setLayout(new BoxLayout(panelMenuLateral, BoxLayout.Y_AXIS));
        panelMenuLateral.setPreferredSize(new Dimension(240, 0));
        panelMenuLateral.setBackground(COLOR_SIDEBAR);
        panelMenuLateral.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 1, new Color(220, 220, 230)));

        // -- Info Usuario --
        JPanel panelUser = new JPanel(new GridLayout(2, 1));
        panelUser.setOpaque(false);
        panelUser.setBorder(new EmptyBorder(30, 25, 30, 20));

        JLabel lblHola = new JLabel("¡Hola de nuevo!");
        lblHola.setForeground(new Color(120, 120, 140));
        lblHola.setFont(new Font("SansSerif", Font.PLAIN, 12));

        // --- CORRECCIÓN AQUÍ: Usamos la variable de clase ---
        lblNombreUsuario = new JLabel(usuario == null ? "" : usuario.getNombre());
        lblNombreUsuario.setForeground(COLOR_TEXTO_MENU);
        lblNombreUsuario.setFont(new Font("SansSerif", Font.BOLD, 18));

        panelUser.add(lblHola);
        panelUser.add(lblNombreUsuario); // Añadimos la variable de clase
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
        btnCerrarSesion.setBackground(COLOR_BTN_SALIR);
        btnCerrarSesion.setForeground(new Color(150, 50, 50));
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
        // btn.setFocusPainted(false);
        // btn.setContentAreaFilled(false);
        // btn.setOpaque(true);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.setHorizontalAlignment(JButton.LEFT);

        btn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                if (!btn.getText().equals("Cerrar Sesión"))
                    btn.setBackground(new Color(214, 234, 248));
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                if (!btn.getText().equals("Cerrar Sesión"))
                    btn.setBackground(COLOR_SIDEBAR);
            }
        });
        return btn;
    }

    private JPanel crearPanelAulas() {
        JPanel wrapper = new JPanel(new BorderLayout());
        wrapper.setBackground(COLOR_FONDO_AULAS);

        JPanel header = new JPanel(new BorderLayout());
        header.setOpaque(false);
        header.setBorder(new EmptyBorder(30, 40, 10, 40));

        JLabel title = new JLabel("Mis Clases");
        title.setFont(new Font("SansSerif", Font.BOLD, 26));
        title.setForeground(new Color(93, 109, 126));

        btnCrearAula = new JButton("+ Nueva Aula");
        estilarBotonAccion(btnCrearAula, COLOR_BTN_ACCION);
        btnCrearAula.setForeground(new Color(30, 80, 40));

        header.add(title, BorderLayout.WEST);
        header.add(btnCrearAula, BorderLayout.EAST);

        panelAulas = new JPanel(new GridLayout(0, 3, 25, 25));
        panelAulas.setOpaque(false);

        // Wrapper para alinear al TOP
        JPanel panelAulasWrapper = new JPanel(new BorderLayout());
        panelAulasWrapper.setOpaque(false);

        // Wrapper para evitar estiramiento horizontal (FlowLayout respeta tamaño
        // preferido)
        JPanel gridConstraintPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        gridConstraintPanel.setOpaque(false);
        gridConstraintPanel.setBorder(new EmptyBorder(0, 30, 0, 0)); // Padding izquierdo
        gridConstraintPanel.add(panelAulas);

        panelAulasWrapper.add(gridConstraintPanel, BorderLayout.NORTH);

        JScrollPane scroll = new JScrollPane(panelAulasWrapper);
        scroll.setBorder(null);
        scroll.getViewport().setOpaque(false);
        scroll.setOpaque(false);

        wrapper.add(header, BorderLayout.NORTH);
        wrapper.add(scroll, BorderLayout.CENTER);

        return wrapper;
    }

    private JPanel crearPanelActividades() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(COLOR_FONDO_CONTENIDO);

        // HEADER Biblioteca
        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(Color.WHITE);
        header.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(230, 230, 230)),
                new EmptyBorder(25, 40, 25, 40)));

        JLabel title = new JLabel("Biblioteca de Recursos");
        title.setFont(new Font("SansSerif", Font.BOLD, 26));
        title.setForeground(new Color(93, 109, 126));

        JLabel subtitle = new JLabel("Gestiona tus temas y banco de preguntas");
        subtitle.setFont(new Font("SansSerif", Font.PLAIN, 14));
        subtitle.setForeground(new Color(160, 160, 160));

        JPanel panelTitulos = new JPanel(new GridLayout(2, 1));
        panelTitulos.setOpaque(false);
        panelTitulos.add(title);
        panelTitulos.add(subtitle);

        header.add(panelTitulos, BorderLayout.WEST);
        panel.add(header, BorderLayout.NORTH);

        // TABS
        tabbedPaneActividades = new JTabbedPane();
        tabbedPaneActividades.setFont(new Font("SansSerif", Font.BOLD, 14));
        tabbedPaneActividades.setBackground(Color.WHITE);
        tabbedPaneActividades.setBorder(new EmptyBorder(15, 30, 15, 30));

        // TAB 1: TEMAS
        JPanel panelTemas = new JPanel(new BorderLayout(15, 15));
        panelTemas.setBackground(Color.WHITE);
        panelTemas.setBorder(new EmptyBorder(20, 20, 20, 20));

        listModelTemas = new DefaultListModel<>();
        listaTemas = new JList<>(listModelTemas);
        estilarLista(listaTemas);

        JScrollPane scrollTemas = new JScrollPane(listaTemas);
        scrollTemas.setBorder(BorderFactory.createLineBorder(new Color(240, 240, 240)));
        panelTemas.add(scrollTemas, BorderLayout.CENTER);

        JPanel panelAddTema = new JPanel(new BorderLayout(10, 0));
        panelAddTema.setOpaque(false);
        panelAddTema.setBorder(new EmptyBorder(10, 0, 0, 0));

        txtNuevoTema = new JTextField();
        estilarCampoTexto(txtNuevoTema);

        btnAgregarTema = new JButton("Añadir Tema");
        estilarBotonAccion(btnAgregarTema, new Color(174, 214, 241));
        btnAgregarTema.setForeground(new Color(40, 90, 130));

        panelAddTema.add(new JLabel("Nuevo Tema: "), BorderLayout.WEST);
        panelAddTema.add(txtNuevoTema, BorderLayout.CENTER);
        panelAddTema.add(btnAgregarTema, BorderLayout.EAST);
        panelTemas.add(panelAddTema, BorderLayout.SOUTH);

        // TAB 2: EJERCICIOS
        JPanel panelBancoEjercicios = new JPanel(new BorderLayout(15, 15));
        panelBancoEjercicios.setBackground(Color.WHITE);
        panelBancoEjercicios.setBorder(new EmptyBorder(20, 20, 20, 20));

        // --- FILTRO POR TEMA ---
        JPanel panelFiltro = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelFiltro.setBackground(Color.WHITE);
        panelFiltro.add(new JLabel("Filtrar por Tema:"));

        cmbFiltroTemaEjercicios = new javax.swing.JComboBox<>();
        cmbFiltroTemaEjercicios.setPreferredSize(new Dimension(200, 30));
        panelFiltro.add(cmbFiltroTemaEjercicios);

        panelBancoEjercicios.add(panelFiltro, BorderLayout.NORTH);
        // -----------------------

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
        estilarBotonAccion(btnCrearEjercicio, COLOR_BTN_ACCION);
        btnCrearEjercicio.setForeground(new Color(30, 80, 40));

        JPanel panelBtnEj = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        panelBtnEj.setBackground(Color.WHITE);
        panelBtnEj.add(btnCrearEjercicio);
        panelBancoEjercicios.add(panelBtnEj, BorderLayout.SOUTH);

        tabbedPaneActividades.addTab("Gestión de Temas", panelTemas);
        tabbedPaneActividades.addTab("Banco de Ejercicios", panelBancoEjercicios);

        panel.add(tabbedPaneActividades, BorderLayout.CENTER);
        return panel;
    }

    // --- MÉTODOS AUXILIARES DE ESTILO ---

    private void estilarCampoTexto(JTextField txt) {
        txt.setFont(new Font("SansSerif", Font.PLAIN, 14));
        txt.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200)),
                new EmptyBorder(8, 10, 8, 10)));
        txt.setBackground(new Color(250, 252, 254));
    }

    private void estilarLista(JList list) {
        list.setFont(new Font("SansSerif", Font.PLAIN, 15));
        list.setFixedCellHeight(40);
        list.setSelectionBackground(new Color(232, 240, 254));
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
        // btn.setBorder(new EmptyBorder(10, 20, 10, 20));
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
    }

    // --- DELEGADOS ---
    public void showContenidoCard(String panelName) {
        cardLayoutContenido.show(panelContenidoPrincipal, panelName);
    }

    public void addMisAulasListener(ActionListener al) {
        btnMisAulas.addActionListener(al);
    }

    public void addActividadesListener(ActionListener al) {
        btnActividades.addActionListener(al);
    }

    public void addReportesListener(ActionListener al) {
        btnReportes.addActionListener(al);
    }

    public void addPerfilListener(ActionListener al) {
        btnPerfil.addActionListener(al);
    }

    public void addCerrarSesionListener(ActionListener al) {
        btnCerrarSesion.addActionListener(al);
    }

    public void addCrearAulaListener(ActionListener al) {
        btnCrearAula.addActionListener(al);
    }

    public void addAgregarTemaListener(ActionListener al) {
        btnAgregarTema.addActionListener(al);
    }

    public void addCrearEjercicioListener(ActionListener al) {
        btnCrearEjercicio.addActionListener(al);
    }

    // --- MÉTODO CLAVE PARA QUE FUNCIONE EL CAMBIO DE USUARIO ---
    public void actualizarUsuario(Usuario nuevoUsuario) {
        if (lblNombreUsuario != null) {
            lblNombreUsuario.setText(nuevoUsuario.getNombre());
            // Aquí puedes actualizar más cosas si lo necesitas
        }
    }
}
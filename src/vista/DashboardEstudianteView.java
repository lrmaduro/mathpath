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
import java.util.Random;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
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
    public JButton btnNotas;
    public JButton btnPerfil;
    public JButton btnCerrarSesion;

    // Etiqueta de Saludo
    private JLabel lblSaludo;

    // --- PANEL 1: MIS AULAS ---
    public JPanel panelAulasContainer;
    public JButton btnUnirseAula;

    // --- PANEL 2: DETALLE AULA ---
    private AulaDetalleViewEstudiante panelAulaDetalle;

    // --- PANEL 3: PERFIL ---
    public PerfilView panelPerfilView; // NUEVO

    // Constantes
    public static final String PANEL_MIS_AULAS = "MIS_AULAS";
    public static final String PANEL_AULA_DETALLE = "AULA_DETALLE";
    public static final String PANEL_PERFIL = "PERFIL"; // Constante para Perfil
    public static final String PANEL_NOTAS = "NOTAS"; // Constante para Notas

    public JPanel panelNotas;
    private javax.swing.table.DefaultTableModel tableModelNotas;

    private JPanel crearPanelNotas() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setOpaque(false);
        panel.setBorder(new EmptyBorder(30, 30, 30, 30));

        // Título
        JLabel lblTitulo = new JLabel("Mis Calificaciones");
        lblTitulo.setFont(new Font("SansSerif", Font.BOLD, 28));
        lblTitulo.setForeground(new Color(44, 62, 80));
        lblTitulo.setBorder(new EmptyBorder(0, 0, 20, 0));

        // Tabla
        String[] columnas = { "Fecha", "Actividad", "Nota" };
        tableModelNotas = new javax.swing.table.DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        javax.swing.JTable tablaNotas = new javax.swing.JTable(tableModelNotas);
        tablaNotas.setRowHeight(30);
        tablaNotas.getTableHeader().setFont(new Font("SansSerif", Font.BOLD, 14));
        tablaNotas.getTableHeader().setBackground(new Color(230, 230, 230));
        tablaNotas.setFont(new Font("SansSerif", Font.PLAIN, 14));

        JScrollPane scroll = new JScrollPane(tablaNotas);
        scroll.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200)));

        panel.add(lblTitulo, BorderLayout.NORTH);
        panel.add(scroll, BorderLayout.CENTER);

        return panel;
    }

    public void actualizarTablaNotas(java.util.List<Object[]> datos) {
        tableModelNotas.setRowCount(0);
        for (Object[] fila : datos) {
            tableModelNotas.addRow(fila);
        }
    }

    // --- 🎨 PALETA "CANDY PASTEL" ---
    private final Color COLOR_FONDO_BG = new Color(232, 248, 245);
    private final Color COLOR_SIDEBAR_BG = new Color(244, 236, 247);
    private final Color COLOR_BTN_MENU_BG = Color.WHITE;
    private final Color COLOR_BTN_MENU_TEXT = new Color(100, 90, 110);
    private final Color COLOR_ACCENT_CORAL = new Color(245, 183, 177);
    private final Color COLOR_ACCENT_TEXT = new Color(90, 60, 60);

    // Nombres de tus mascotas
    private final String[] NOMBRES_MASCOTAS = { "mascota_1.png", "mascota_2.png", "mascota_3.png", "mascota_4.png",
            "mascota_5.png", "mascota_6.png" };

    public DashboardEstudianteView(Usuario estudiante) {
        this.setLayout(new BorderLayout());

        // 1. Inicializar Menú Lateral
        inicializarMenuLateral(estudiante);

        // 2. Inicializar Área de Contenido con Fondo de Imagen
        cardLayoutContenido = new CardLayout();
        panelContenidoPrincipal = new JPanel(cardLayoutContenido) {
            private ImageIcon backgroundImage;

            {
                // Load the background image
                String ruta = "/img/Background chiqui.png";
                java.net.URL imgURL = getClass().getResource(ruta);
                if (imgURL != null) {
                    backgroundImage = new ImageIcon(imgURL);
                } else {
                    System.err.println("No se encontró la imagen de fondo: " + ruta);
                }
            }

            @Override
            protected void paintComponent(java.awt.Graphics g) {
                super.paintComponent(g);
                if (backgroundImage != null) {
                    // Draw the background image scaled to fill the panel
                    g.drawImage(backgroundImage.getImage(), 0, 0, getWidth(), getHeight(), this);
                }
            }
        };
        panelContenidoPrincipal.setOpaque(false); // Make panel transparent so background shows

        // 3. Crear Sub-Paneles
        JPanel panelMisAulas = crearPanelMisAulas();
        panelAulaDetalle = new AulaDetalleViewEstudiante();
        panelAulaDetalle.setOpaque(false);

        // Panel Notas
        panelNotas = crearPanelNotas();

        // Panel Perfil (true = Es Estudiante)
        panelPerfilView = new PerfilView(estudiante, true);

        // 4. Añadir paneles
        panelContenidoPrincipal.add(panelMisAulas, PANEL_MIS_AULAS);
        panelContenidoPrincipal.add(panelAulaDetalle, PANEL_AULA_DETALLE);
        panelContenidoPrincipal.add(panelPerfilView, PANEL_PERFIL);
        panelContenidoPrincipal.add(panelNotas, PANEL_NOTAS);

        // 5. Ensamblaje final
        this.add(panelMenuLateral, BorderLayout.WEST);
        this.add(panelContenidoPrincipal, BorderLayout.CENTER);
    }

    private void inicializarMenuLateral(Usuario usuario) {
        panelMenuLateral = new JPanel();
        panelMenuLateral.setLayout(new BoxLayout(panelMenuLateral, BoxLayout.Y_AXIS));
        panelMenuLateral.setPreferredSize(new Dimension(260, 0));
        panelMenuLateral.setBackground(COLOR_SIDEBAR_BG);
        panelMenuLateral.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 2, Color.WHITE));

        // --- ZONA DE PERFIL ---
        JPanel panelPerfilInfo = new JPanel();
        panelPerfilInfo.setLayout(new BoxLayout(panelPerfilInfo, BoxLayout.Y_AXIS));
        panelPerfilInfo.setOpaque(false);
        panelPerfilInfo.setBorder(new EmptyBorder(40, 20, 30, 20));

        lblSaludo = new JLabel("¡Hola, " + (usuario == null ? "" : usuario.getNombre()) + "!");
        lblSaludo.setFont(new Font("SansSerif", Font.BOLD, 22));
        lblSaludo.setForeground(new Color(81, 46, 95));
        lblSaludo.setAlignmentX(CENTER_ALIGNMENT);

        JLabel lblSubtitulo = new JLabel("Estudiante");
        lblSubtitulo.setFont(new Font("SansSerif", Font.ITALIC, 14));
        lblSubtitulo.setForeground(new Color(142, 68, 173));
        lblSubtitulo.setAlignmentX(CENTER_ALIGNMENT);

        panelPerfilInfo.add(lblSaludo);
        panelPerfilInfo.add(Box.createVerticalStrut(5));
        panelPerfilInfo.add(lblSubtitulo);

        // --- ZONA DE MENÚ ---
        btnMisAulas = crearBotonMenu("Mis Clases", new Color(0xFEC534));
        btnNotas = crearBotonMenu("Mis Notas", new Color(0x8CBD46));
        btnPerfil = crearBotonMenu("Mi Perfil", new Color(0x80B6FF));

        panelMenuLateral.add(panelPerfilInfo);
        panelMenuLateral.add(btnMisAulas);
        panelMenuLateral.add(Box.createVerticalStrut(10));
        panelMenuLateral.add(btnNotas);
        panelMenuLateral.add(Box.createVerticalStrut(10));
        panelMenuLateral.add(btnPerfil);

        // --- ESPACIO PARA MASCOTA ALEATORIA ---
        panelMenuLateral.add(Box.createVerticalGlue());

        JPanel panelMascota = new JPanel();
        panelMascota.setOpaque(false);
        panelMascota.setPreferredSize(new Dimension(200, 180)); // Espacio suficiente

        JLabel lblMascotaImg = new JLabel();

        // Cargar Mascota Random
        String nombreMascotaRandom = NOMBRES_MASCOTAS[new Random().nextInt(NOMBRES_MASCOTAS.length)];
        ImageIcon icono = cargarIcono(nombreMascotaRandom, 160, 160); // Tamaño 160x160

        if (icono != null) {
            lblMascotaImg.setIcon(icono);
        } else {
            // Fallback si no hay imagen
            lblMascotaImg.setText("<html><center style='color:#A569BD'>[Tu Mascota]<br>ʕ•ᴥ•ʔ</center></html>");
            lblMascotaImg.setFont(new Font("Monospaced", Font.BOLD, 14));
        }

        panelMascota.add(lblMascotaImg);
        panelMenuLateral.add(panelMascota);

        // --- BOTÓN SALIR ---
        btnCerrarSesion = new JButton("Cerrar Sesión");
        estilarBotonAccion(btnCerrarSesion, new Color(255, 255, 255), new Color(231, 76, 60));
        btnCerrarSesion.setBorder(BorderFactory.createLineBorder(new Color(250, 219, 216), 2));
        btnCerrarSesion.setMaximumSize(new Dimension(180, 45));
        btnCerrarSesion.setAlignmentX(CENTER_ALIGNMENT);

        JPanel panelSalida = new JPanel();
        panelSalida.setOpaque(false);
        panelSalida.setBorder(new EmptyBorder(10, 20, 30, 20));
        panelSalida.add(btnCerrarSesion);

        panelMenuLateral.add(panelSalida);
    }

    private JButton crearBotonMenu(String texto, Color bgColor) {
        JButton btn = new JButton(texto);
        btn.setFont(new Font("SansSerif", Font.BOLD, 15));
        btn.setForeground(Color.WHITE);
        btn.setBackground(bgColor);

        btn.setFocusPainted(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.setMaximumSize(new Dimension(220, 1000));
        btn.setAlignmentX(CENTER_ALIGNMENT);
        // btn.setBorder(new EmptyBorder(15, 20, 15, 20));

        // Store the original color for hover effect
        Color originalColor = bgColor;
        btn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                // Slightly darken on hover
                btn.setBackground(originalColor.darker());
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                btn.setBackground(originalColor);
            }
        });
        return btn;
    }

    private JPanel crearPanelMisAulas() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setOpaque(false);

        JPanel header = new JPanel(new BorderLayout());
        header.setBorder(new EmptyBorder(40, 40, 20, 40));
        header.setOpaque(false);

        JPanel titulos = new JPanel(new BorderLayout());
        titulos.setOpaque(false);

        JLabel title = new JLabel("Mis Clases");
        title.setFont(new Font("SansSerif", Font.BOLD, 34));
        title.setForeground(new Color(0, 0, 0));

        JLabel subtitle = new JLabel("Continúa tu aventura de aprendizaje");
        subtitle.setFont(new Font("SansSerif", Font.PLAIN, 16));
        subtitle.setForeground(new Color(0, 0, 0));

        titulos.add(title, BorderLayout.NORTH);
        titulos.add(subtitle, BorderLayout.SOUTH);

        btnUnirseAula = new JButton("+ Unirse a Clase");
        estilarBotonAccion(btnUnirseAula, COLOR_ACCENT_CORAL, COLOR_ACCENT_TEXT);
        btnUnirseAula.setPreferredSize(new Dimension(200, 50));

        header.add(titulos, BorderLayout.WEST);
        header.add(btnUnirseAula, BorderLayout.EAST);

        panelAulasContainer = new JPanel(new GridLayout(0, 3, 25, 25));
        panelAulasContainer.setOpaque(false);

        // Wrapper para alinear al TOP
        JPanel panelAulasWrapper = new JPanel(new BorderLayout());
        panelAulasWrapper.setOpaque(false);

        // Wrapper para evitar estiramiento horizontal (FlowLayout respeta tamaño
        // preferido)
        JPanel gridConstraintPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        gridConstraintPanel.setOpaque(false);
        gridConstraintPanel.setBorder(new EmptyBorder(0, 30, 0, 0)); // Padding izquierdo
        gridConstraintPanel.add(panelAulasContainer);

        panelAulasWrapper.add(gridConstraintPanel, BorderLayout.NORTH);

        JScrollPane scroll = new JScrollPane(panelAulasWrapper);
        scroll.setBorder(null);
        scroll.getViewport().setOpaque(false);
        scroll.setOpaque(false);
        scroll.getVerticalScrollBar().setUnitIncrement(16);

        panel.add(header, BorderLayout.NORTH);
        panel.add(scroll, BorderLayout.CENTER);

        return panel;
    }

    // --- Método Auxiliar para Cargar y Escalar Imágenes ---
    private ImageIcon cargarIcono(String nombreArchivo, int ancho, int alto) {
        String ruta = "/img/" + nombreArchivo;
        java.net.URL imgURL = getClass().getResource(ruta);

        if (imgURL != null) {
            ImageIcon icono = new ImageIcon(imgURL);
            java.awt.Image img = icono.getImage().getScaledInstance(ancho, alto, java.awt.Image.SCALE_SMOOTH);
            return new ImageIcon(img);
        } else {
            System.err.println("No se encontró la imagen: " + ruta);
            return null;
        }
    }

    private void estilarBotonAccion(JButton btn, Color bg, Color fg) {
        btn.setBackground(bg);
        btn.setForeground(fg);
        btn.setFont(new Font("SansSerif", Font.BOLD, 14));
        btn.setFocusPainted(false);
        btn.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(bg.darker(), 1),
                new EmptyBorder(10, 20, 10, 20)));
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
    }

    public AulaDetalleViewEstudiante getPanelAulaDetalle() {
        return panelAulaDetalle;
    }

    public void showContenidoCard(String name) {
        cardLayoutContenido.show(panelContenidoPrincipal, name);
    }

    public void addUnirseAulaListener(ActionListener al) {
        btnUnirseAula.addActionListener(al);
    }

    public void addMisAulasListener(ActionListener al) {
        btnMisAulas.addActionListener(al);
    }

    public void addCerrarSesionListener(ActionListener al) {
        btnCerrarSesion.addActionListener(al);
    }

    public void actualizarUsuario(Usuario nuevoEstudiante) {
        if (lblSaludo != null) {
            lblSaludo.setText("¡Hola, " + nuevoEstudiante.getNombre() + "!");
        }
    }
}
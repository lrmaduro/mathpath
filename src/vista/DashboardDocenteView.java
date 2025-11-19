package vista;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Cursor; // NUEVO: Para la manito del mouse
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon; // NUEVO: Para los íconos
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
    
    private BorderLayout layoutPrincipal;
    
    private JPanel panelMenuLateral;
    public JButton btnMisAulas;
    public JButton btnActividades;
    public JButton btnReportes;
    public JButton btnPerfil;
    public JButton btnCerrarSesion;
    public JList<String> listaTemas;
    public DefaultListModel<String> listModelTemas;
    public JTextField txtNuevoTema;
    public JButton btnAgregarTema;
    public JButton btnCrearAula;
    public JTabbedPane tabbedPaneActividades;
    public JPanel panelBancoEjercicios;
    public JPanel panelListaEjercicios;
    public JButton btnCrearEjercicio;
    private JPanel panelContenidoPrincipal;
    private CardLayout cardLayoutContenido;
    public JPanel panelAulas;
    public AulaDetalleView panelAulaDetalle;

    public static final String PANEL_AULAS = "AULAS";
    public static final String PANEL_ACTIVIDADES = "ACTIVIDADES";
    public static final String PANEL_REPORTES = "REPORTES";
    public static final String PANEL_PERFIL = "PERFIL";
    public static final String PANEL_AULA_DETALLE = "AULA_DETALLE";
    
    private ImageIcon cargarIcono(String ruta, int tamano) {
//        try {
//            Image img = javax.imageio.ImageIO.read(getClass().getResource(ruta));
//            if (img != null) {
//                Image imgEscalada = img.getScaledInstance(tamano, tamano, Image.SCALE_SMOOTH);
//                return new ImageIcon(imgEscalada);
//            }
//        } catch (Exception e) {
//            System.err.println("Error al cargar el icono: " + ruta);
//            e.printStackTrace(); // <-- AÑADE ESTA LÍNEA
//            return new ImageIcon(new java.awt.image.BufferedImage(tamano, tamano, java.awt.image.BufferedImage.TYPE_INT_ARGB));
//        }
        return new ImageIcon(new java.awt.image.BufferedImage(tamano, tamano, java.awt.image.BufferedImage.TYPE_INT_ARGB));
    }

    public DashboardDocenteView(Usuario docente) {
        layoutPrincipal = new BorderLayout(5, 5);
        this.setLayout(layoutPrincipal);
        this.setBorder(new EmptyBorder(10, 10, 10, 10));
        
        // 2. Crear el Panel del Menú Lateral (OESTE)
        panelMenuLateral = new JPanel();
        panelMenuLateral.setLayout(new BoxLayout(panelMenuLateral, BoxLayout.Y_AXIS));
        
        // NUEVO: Le ponemos un color de fondo (Usa los colores de tu diseño)
        panelMenuLateral.setBackground(new Color(240, 245, 249)); // Un gris claro
        panelMenuLateral.setBorder(new EmptyBorder(15, 10, 15, 10)); // Margen interno

        // NUEVO: Cargamos el ícono
        // Asegúrate de que el path sea correcto y el archivo exista
        // El "/" al inicio busca desde la raíz de "Source Packages"
        ImageIcon iconoAulas = cargarIcono("/image.iconos/icono_aula.png", 24);
        
        // Añadir botones al menú
        btnMisAulas = new JButton("Mis Aulas");
        
        // NUEVO: Añadimos el ícono al botón
        btnMisAulas.setIcon(iconoAulas);
        
        btnActividades = new JButton("Actividades");
        btnReportes = new JButton("Reportes");
        btnPerfil = new JButton("Perfil");
        btnCerrarSesion = new JButton("Cerrar Sesión");
        
        // NUEVO: Creamos un Array de los botones para aplicarles el estilo
        JButton[] botonesMenu = {btnMisAulas, btnActividades, btnReportes, btnPerfil, btnCerrarSesion};
        
        for (JButton btn : botonesMenu) {
            // NUEVO: Aplicamos el estilo a cada botón
            btn.setBackground(panelMenuLateral.getBackground()); // Mismo fondo del panel
            btn.setBorder(new EmptyBorder(10, 15, 10, 15)); // Margen interno del botón
            btn.setOpaque(false); // Lo hacemos transparente
            btn.setContentAreaFilled(false); // Quitamos el pintado de fondo
            btn.setBorderPainted(false); // Quitamos el borde
            btn.setFocusPainted(false); // Quitamos el recuadro de foco
            btn.setCursor(new Cursor(Cursor.HAND_CURSOR)); // Manito del mouse
            
            // NUEVO: Alineamos el ícono y texto a la izquierda
            btn.setHorizontalAlignment(JButton.LEFT);
            btn.setIconTextGap(10); // Separación entre ícono y texto
            
            panelMenuLateral.add(btn); // Añadimos el botón al panel
        }
        
        // 3. Crear el Panel de Contenido Principal (CENTRO)
        cardLayoutContenido = new CardLayout();
        panelContenidoPrincipal = new JPanel(cardLayoutContenido);
        panelContenidoPrincipal.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        // NUEVO: Fondo blanco para el contenido
        panelContenidoPrincipal.setBackground(Color.WHITE); 

        // 4. Crear los paneles "placeholder" para cada sección
        
        panelAulas = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10)); 
        panelAulas.setOpaque(false); // Fondo transparente
        
        // A. Creamos el panel "wrapper" principal para esta sección
        JPanel wrapperAulas = new JPanel(new BorderLayout(10, 10));
        wrapperAulas.setOpaque(false); // Transparente
        
        // B. Creamos el panel de cabecera (para el botón)
        JPanel panelHeaderAulas = new JPanel(new FlowLayout(FlowLayout.RIGHT)); // Alineado a la derecha
        panelHeaderAulas.setOpaque(false);
        btnCrearAula = new JButton("+ Crear Nueva Aula");
        // (Aquí puedes darle estilo al botón después)
        panelHeaderAulas.add(btnCrearAula);
        
        // D. Armamos el wrapper
        wrapperAulas.add(panelHeaderAulas, BorderLayout.NORTH); // Botón arriba
        wrapperAulas.add(panelAulas, BorderLayout.CENTER); // Tarjetas en el centro
        
        JPanel panelActividades = new JPanel(new BorderLayout());
        panelActividades.setOpaque(false);

        tabbedPaneActividades = new JTabbedPane();

        // --- Pestaña 1: GESTIÓN DE TEMAS (Lo que ya tenías) ---
        JPanel panelGestionTemas = new JPanel(new BorderLayout(10, 10));
        panelGestionTemas.setBorder(new EmptyBorder(10, 10, 10, 10));

        JLabel lblTituloTemas = new JLabel("Gestionar Temas (Recursos de Actividad)");
        lblTituloTemas.setFont(lblTituloTemas.getFont().deriveFont(Font.BOLD, 20.0f));
        panelGestionTemas.add(lblTituloTemas, BorderLayout.NORTH);

        listModelTemas = new DefaultListModel<>();
        listaTemas = new JList<>(listModelTemas);
        JScrollPane scrollTemas = new JScrollPane(listaTemas);
        panelGestionTemas.add(scrollTemas, BorderLayout.CENTER);

        // Panel Añadir Tema (Sur)
        JPanel panelAddTema = new JPanel(new BorderLayout(5, 5));
        txtNuevoTema = new JTextField();
        btnAgregarTema = new JButton("Añadir Tema");
        panelAddTema.add(new JLabel("Nuevo Tema:"), BorderLayout.WEST);
        panelAddTema.add(txtNuevoTema, BorderLayout.CENTER);
        panelAddTema.add(btnAgregarTema, BorderLayout.EAST);
        panelGestionTemas.add(panelAddTema, BorderLayout.SOUTH);

        // --- Pestaña 2: BANCO DE EJERCICIOS (Nueva) ---
        panelBancoEjercicios = new JPanel(new BorderLayout(10, 10));
        panelBancoEjercicios.setBorder(new EmptyBorder(10, 10, 10, 10));

        JLabel lblTituloEjercicios = new JLabel("Banco de Ejercicios");
        lblTituloEjercicios.setFont(lblTituloEjercicios.getFont().deriveFont(Font.BOLD, 20.0f));
        panelBancoEjercicios.add(lblTituloEjercicios, BorderLayout.NORTH);

        // Lista de Ejercicios
        panelListaEjercicios = new JPanel();
        panelListaEjercicios.setLayout(new BoxLayout(panelListaEjercicios, BoxLayout.Y_AXIS));
        JScrollPane scrollEjercicios = new JScrollPane(panelListaEjercicios);
        panelBancoEjercicios.add(scrollEjercicios, BorderLayout.CENTER);

        // Botón Crear Ejercicio (Abajo)
        JPanel panelBotonEjercicios = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        btnCrearEjercicio = new JButton("+ Crear Nuevo Ejercicio");
        panelBotonEjercicios.add(btnCrearEjercicio);
        panelBancoEjercicios.add(panelBotonEjercicios, BorderLayout.SOUTH);

        // Añadir las pestañas al JTabbedPane
        tabbedPaneActividades.addTab("Gestionar Temas", panelGestionTemas);
        tabbedPaneActividades.addTab("Banco de Ejercicios", panelBancoEjercicios);

        // Añadir el JTabbedPane al panel principal
        panelActividades.add(tabbedPaneActividades, BorderLayout.CENTER);
        
        JPanel panelReportes = new JPanel();
        panelReportes.add(new JLabel("Sección: REPORTES (Aquí irán los gráficos)"));
        panelReportes.setOpaque(false);
        
        JPanel panelPerfil = new JPanel();
        panelPerfil.add(new JLabel("Sección: PERFIL (Aquí irá la info del docente)"));
        panelPerfil.setOpaque(false);
        
        panelAulaDetalle = new AulaDetalleView();
        panelAulaDetalle.setOpaque(false);

        // 5. Añadir los paneles "placeholder" al CardLayout
        panelContenidoPrincipal.add(wrapperAulas, PANEL_AULAS);
        panelContenidoPrincipal.add(panelActividades, PANEL_ACTIVIDADES);
        panelContenidoPrincipal.add(panelReportes, PANEL_REPORTES);
        panelContenidoPrincipal.add(panelPerfil, PANEL_PERFIL);
        panelContenidoPrincipal.add(panelAulaDetalle, PANEL_AULA_DETALLE);
        
        // 6. Añadir el menú y el contenido al panel principal
        this.add(panelMenuLateral, BorderLayout.WEST);
        this.add(panelContenidoPrincipal, BorderLayout.CENTER);
    }
    
    
    // --- Métodos para el Controlador (ESTOS NO CAMBIAN) ---
    
    public void showContenidoCard(String panelName) {
        cardLayoutContenido.show(panelContenidoPrincipal, panelName);
    }
    
    public void addMisAulasListener(ActionListener listener) {
        btnMisAulas.addActionListener(listener);
    }
    
    public void addActividadesListener(ActionListener listener) {
        btnActividades.addActionListener(listener);
    }
    
    public void addReportesListener(ActionListener listener) {
        btnReportes.addActionListener(listener);
    }
    
    public void addPerfilListener(ActionListener listener) {
        btnPerfil.addActionListener(listener);
    }
    
    public void addCerrarSesionListener(ActionListener listener) {
        btnCerrarSesion.addActionListener(listener);
    }
    
    public void addCrearAulaListener(ActionListener listener) {
        btnCrearAula.addActionListener(listener);
    }
    
    public void addAgregarTemaListener(ActionListener listener) {
        btnAgregarTema.addActionListener(listener);
    }
    public void addCrearEjercicioListener(ActionListener listener) {
        btnCrearEjercicio.addActionListener(listener);
    }
}
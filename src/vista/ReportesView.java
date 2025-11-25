package vista;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import modelo.Actividad;
import modelo.Aula;

public class ReportesView extends JPanel {

    private JComboBox<String> cmbAulas;
    public JComboBox<String> cmbActividades; // Combo NUEVO
    private JTable tablaReportes;
    private DefaultTableModel modeloTabla;
    public JButton btnExportar; 
    
    private JLabel lblPromedio;
    private JLabel lblTotalAlumnos;
    
    // Gráficas de Distribución (Histograma)
    private JPanel panelDistribucionContenido;
    
    // Listas auxiliares para mantener referencia a los objetos reales
    private List<Aula> listaAulasActuales; 
    private List<Actividad> listaActividadesActuales;

    // --- PALETA DE COLORES ---
    private final Color COLOR_FONDO = new Color(250, 250, 250);
    private final Color COLOR_HEADER_TABLA = new Color(232, 240, 254); 
    private final Color COLOR_SELECCION = new Color(214, 234, 248);
    
    // Colores Semánticos para el Histograma
    private final Color COL_CRITICO = new Color(231, 76, 60);   // Rojo
    private final Color COL_REGULAR = new Color(243, 156, 18);  // Naranja
    private final Color COL_BUENO = new Color(52, 152, 219);    // Azul
    private final Color COL_EXCELENTE = new Color(39, 174, 96); // Verde

    public ReportesView() {
        this.setLayout(new BorderLayout(20, 20));
        this.setBackground(COLOR_FONDO);
        this.setBorder(new EmptyBorder(20, 30, 20, 30));

        // --- 1. BARRA SUPERIOR (Corregida con GridLayout para evitar solapamiento) ---
        JPanel panelTop = new JPanel(new GridLayout(2, 1, 0, 10)); 
        panelTop.setOpaque(false);
        
        // Fila 1: Título
        JLabel lblTitulo = new JLabel("Análisis de Resultados");
        lblTitulo.setFont(new Font("SansSerif", Font.BOLD, 24));
        lblTitulo.setForeground(new Color(44, 62, 80));
        panelTop.add(lblTitulo);
        
        // Fila 2: Filtros
        JPanel panelFiltros = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        panelFiltros.setOpaque(false);
        
        // A. Filtro Aula
        panelFiltros.add(new JLabel("Aula: "));
        cmbAulas = new JComboBox<>();
        estilarCombo(cmbAulas);
        panelFiltros.add(cmbAulas);
        
        panelFiltros.add(Box.createHorizontalStrut(15)); // Espaciador
        
        // B. Filtro Actividad
        panelFiltros.add(new JLabel("Actividad: "));
        cmbActividades = new JComboBox<>();
        estilarCombo(cmbActividades);
        cmbActividades.addItem("- Seleccione Aula -");
        cmbActividades.setEnabled(false); // Desactivado al inicio
        panelFiltros.add(cmbActividades);
        
        panelFiltros.add(Box.createHorizontalStrut(15)); // Espaciador
        
        // C. Botón Exportar
        btnExportar = new JButton("Exportar CSV");
        btnExportar.setBackground(new Color(39, 174, 96)); 
        btnExportar.setForeground(Color.WHITE);
        btnExportar.setFont(new Font("SansSerif", Font.BOLD, 12));
        btnExportar.setFocusPainted(false);
//        btnExportar.setBorder(new EmptyBorder(5, 15, 5, 15));
        btnExportar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        panelFiltros.add(btnExportar);
        
        panelTop.add(panelFiltros);
        
        this.add(panelTop, BorderLayout.NORTH);

        // --- 2. PANEL CENTRAL (Tabla + Histograma) ---
        JPanel panelCentral = new JPanel(new BorderLayout(20, 0));
        panelCentral.setOpaque(false);

        // A. Tabla (Izquierda)
        String[] columnas = {"Estudiante", "Nota", "Estado", "Feedback / Detalle"};
        modeloTabla = new DefaultTableModel(columnas, 0) {
            @Override public boolean isCellEditable(int row, int column) { return false; }
        };
        tablaReportes = new JTable(modeloTabla);
        estilarTabla(tablaReportes); 
        
        JScrollPane scrollTabla = new JScrollPane(tablaReportes);
        scrollTabla.setBorder(BorderFactory.createLineBorder(new Color(230,230,230)));
        scrollTabla.getViewport().setBackground(Color.WHITE);
        
        panelCentral.add(scrollTabla, BorderLayout.CENTER);
        
        // B. Panel de Distribución / Histograma (Derecha)
        JPanel panelDistribucion = new JPanel();
        panelDistribucion.setLayout(new BorderLayout());
        panelDistribucion.setBackground(Color.WHITE);
        panelDistribucion.setPreferredSize(new Dimension(260, 0));
        panelDistribucion.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(230, 230, 230)),
            new EmptyBorder(20, 15, 20, 15)
        ));
        
        JLabel lblDist = new JLabel("Distribución del Grupo");
        lblDist.setFont(new Font("SansSerif", Font.BOLD, 16));
        lblDist.setHorizontalAlignment(JLabel.CENTER);
        panelDistribucion.add(lblDist, BorderLayout.NORTH);
        
        // Contenedor vertical para las barras
        panelDistribucionContenido = new JPanel();
        panelDistribucionContenido.setLayout(new BoxLayout(panelDistribucionContenido, BoxLayout.Y_AXIS));
        panelDistribucionContenido.setOpaque(false);
        panelDistribucion.add(panelDistribucionContenido, BorderLayout.CENTER);
        
        panelCentral.add(panelDistribucion, BorderLayout.EAST);
        
        this.add(panelCentral, BorderLayout.CENTER);
        
        // --- 3. PANEL INFERIOR (Resumen Numérico) ---
        JPanel panelResumen = new JPanel(new GridLayout(1, 4, 20, 0));
        panelResumen.setOpaque(false);
        panelResumen.setBorder(new EmptyBorder(10, 0, 0, 0));
        
        lblPromedio = crearTarjetaResumen("Nota Media", "0.0", new Color(174, 214, 241)); // Azulito
        lblTotalAlumnos = crearTarjetaResumen("Total Registros", "0", new Color(249, 231, 159)); // Amarillito
        
        panelResumen.add(lblPromedio);
        panelResumen.add(lblTotalAlumnos);
        panelResumen.add(new JLabel("")); // Espacio vacío
        panelResumen.add(new JLabel("")); // Espacio vacío
        
        this.add(panelResumen, BorderLayout.SOUTH);
    }
    
    // --- Lógica Visual del Histograma ---
    public void actualizarHistograma(int[] distribucion) {
        panelDistribucionContenido.removeAll();
        panelDistribucionContenido.add(Box.createVerticalStrut(20));
        
        int total = 0;
        for(int n : distribucion) total += n;
        if(total == 0) total = 1; // Evitar división por cero
        
        // Crear las 4 barras (Excelente, Bueno, Regular, Crítico)
        agregarBarraHistograma("Excelente (18-20)", distribucion[3], total, COL_EXCELENTE);
        agregarBarraHistograma("Bueno (15-17)", distribucion[2], total, COL_BUENO);
        agregarBarraHistograma("Regular (11-14)", distribucion[1], total, COL_REGULAR);
        agregarBarraHistograma("Crítico (0-10)", distribucion[0], total, COL_CRITICO);
        
        panelDistribucionContenido.add(Box.createVerticalGlue());
        panelDistribucionContenido.revalidate();
        panelDistribucionContenido.repaint();
    }
    
    private void agregarBarraHistograma(String etiqueta, int valor, int total, Color color) {
        JPanel p = new JPanel(new BorderLayout(5, 5));
        p.setOpaque(false);
        p.setMaximumSize(new Dimension(300, 50));
        
        JLabel lblTitulo = new JLabel(etiqueta + " [" + valor + "]");
        lblTitulo.setFont(new Font("SansSerif", Font.PLAIN, 12));
        
        JProgressBar barra = new JProgressBar(0, total);
        barra.setValue(valor);
        barra.setForeground(color);
        barra.setBackground(new Color(240, 240, 240));
        barra.setBorderPainted(false);
        barra.setPreferredSize(new Dimension(100, 12));
        
        p.add(lblTitulo, BorderLayout.NORTH);
        p.add(barra, BorderLayout.CENTER);
        
        panelDistribucionContenido.add(p);
        panelDistribucionContenido.add(Box.createVerticalStrut(15));
    }

    // --- Métodos de Carga de Datos ---
    
    public void cargarComboAulas(List<Aula> aulas) {
        this.listaAulasActuales = aulas;
        cmbAulas.removeAllItems();
        cmbAulas.addItem("- Selecciona Aula -");
        for (Aula a : aulas) cmbAulas.addItem(a.getNombre());
    }
    
    public void cargarComboActividades(List<Actividad> actividades) {
        this.listaActividadesActuales = actividades;
        cmbActividades.removeAllItems();
        cmbActividades.addItem("Todas las Actividades");
        for (Actividad act : actividades) cmbActividades.addItem(act.getNombre());
        cmbActividades.setEnabled(true);
    }
    
    public Aula getAulaSeleccionada() {
        int index = cmbAulas.getSelectedIndex();
        // Restamos 1 porque el índice 0 es "- Selecciona Aula -"
        if (index > 0 && listaAulasActuales != null && (index - 1) < listaAulasActuales.size()) {
            return listaAulasActuales.get(index - 1);
        }
        return null;
    }
    
    public Actividad getActividadSeleccionada() {
        int index = cmbActividades.getSelectedIndex();
        // Restamos 1 porque el índice 0 es "Todas las Actividades"
        if (index > 0 && listaActividadesActuales != null && (index - 1) < listaActividadesActuales.size()) {
            return listaActividadesActuales.get(index - 1);
        }
        return null;
    }
    
    public void actualizarTabla(List<Object[]> datos, double promedio) {
        modeloTabla.setRowCount(0);
        for (Object[] fila : datos) modeloTabla.addRow(fila);
        
        lblTotalAlumnos.setText("<html><div style='text-align:center;'>Total Registros<br><span style='font-size:18px;'>" + datos.size() + "</span></div></html>");
        lblPromedio.setText("<html><div style='text-align:center;'>Nota Media<br><span style='font-size:18px;'>" + String.format("%.1f", promedio) + "</span></div></html>");
    }
    
    // Sobrecarga por si llamas sin promedio (calculándolo dentro o ignorándolo)
    public void actualizarTabla(List<Object[]> datos) {
        actualizarTabla(datos, 0.0);
        lblPromedio.setText("<html><div style='text-align:center;'>Nota Media<br><span style='font-size:12px;'>(Ver detalle)</span></div></html>");
    }
    
    // --- Estilos ---
    private void estilarCombo(JComboBox cmb) {
        cmb.setPreferredSize(new Dimension(180, 30));
        cmb.setBackground(Color.WHITE);
        cmb.setFont(new Font("SansSerif", Font.PLAIN, 13));
    }
    
    private void estilarTabla(JTable table) {
        table.setRowHeight(35); 
        table.setFont(new Font("SansSerif", Font.PLAIN, 14));
        table.setShowVerticalLines(false);
        table.setGridColor(new Color(230, 230, 230));
        table.setSelectionBackground(COLOR_SELECCION);
        table.setSelectionForeground(Color.BLACK);
        
        JTableHeader header = table.getTableHeader();
        header.setDefaultRenderer(new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                setBackground(COLOR_HEADER_TABLA);
                setForeground(new Color(60, 60, 60));
                setFont(new Font("SansSerif", Font.BOLD, 14));
                setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.LIGHT_GRAY));
                return this;
            }
        });
    }
    
    private JLabel crearTarjetaResumen(String titulo, String valor, Color colorFondo) {
        JLabel lbl = new JLabel("<html><div style='text-align:center;'>" + titulo + "<br><span style='font-size:18px;'>" + valor + "</span></div></html>");
        lbl.setOpaque(true);
        lbl.setBackground(colorFondo);
        lbl.setHorizontalAlignment(JLabel.CENTER);
        lbl.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        lbl.setFont(new Font("SansSerif", Font.PLAIN, 14));
        return lbl;
    }
    
    // Getters y Listeners
    public void addFiltroAulaListener(ActionListener al) { cmbAulas.addActionListener(al); }
    public void addFiltroActividadListener(ActionListener al) { cmbActividades.addActionListener(al); }
    public void addExportarListener(ActionListener al) { btnExportar.addActionListener(al); }
    public JTable getTabla() { return tablaReportes; }
}
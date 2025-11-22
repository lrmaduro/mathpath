package vista;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor; // Importar
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.JButton; // Importar
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import modelo.Aula;

public class ReportesView extends JPanel {

    private JComboBox<String> cmbAulas; 
    private JTable tablaReportes;
    private DefaultTableModel modeloTabla;
    
    // --- NUEVO BOTÓN ---
    public JButton btnExportar; 
    
    private JLabel lblPromedio;
    private JLabel lblTotalAlumnos;

    private final Color COLOR_FONDO = new Color(250, 250, 250);
    private final Color COLOR_HEADER_TABLA = new Color(232, 240, 254); 
    private final Color COLOR_SELECCION = new Color(214, 234, 248);
    
    private List<Aula> listaAulasActuales; 

    public ReportesView() {
        this.setLayout(new BorderLayout(20, 20));
        this.setBackground(COLOR_FONDO);
        this.setBorder(new EmptyBorder(20, 30, 20, 30));

        // --- 1. BARRA SUPERIOR ---
        JPanel panelTop = new JPanel(new BorderLayout());
        panelTop.setOpaque(false);
        
        JLabel lblTitulo = new JLabel("Rendimiento Académico");
        lblTitulo.setFont(new Font("SansSerif", Font.BOLD, 24));
        lblTitulo.setForeground(new Color(44, 62, 80));
        
        // Panel del ComboBox y Botón
        JPanel panelFiltro = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        panelFiltro.setOpaque(false);
        panelFiltro.add(new JLabel("Seleccionar Clase:"));
        
        cmbAulas = new JComboBox<>();
        cmbAulas.setPreferredSize(new Dimension(200, 30));
        cmbAulas.setBackground(Color.WHITE);
        panelFiltro.add(cmbAulas);
        
        // --- NUEVO BOTÓN EXPORTAR ---
        btnExportar = new JButton("Exportar Excel");
        btnExportar.setBackground(new Color(39, 174, 96)); // Verde Excel
        btnExportar.setForeground(Color.WHITE);
        btnExportar.setFont(new Font("SansSerif", Font.BOLD, 12));
        btnExportar.setFocusPainted(false);
        btnExportar.setBorder(new EmptyBorder(8, 15, 8, 15));
        btnExportar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        panelFiltro.add(btnExportar);
        // -----------------------------
        
        panelTop.add(lblTitulo, BorderLayout.WEST);
        panelTop.add(panelFiltro, BorderLayout.EAST);
        
        this.add(panelTop, BorderLayout.NORTH);

        // ... (EL RESTO DEL CÓDIGO SIGUE IGUAL: TABLA Y PANELES INFERIORES) ...
        // ... COPIA EL RESTO DEL CÓDIGO DE LA VEZ PASADA AQUÍ ABAJO ...
        
        // --- 2. TABLA CENTRAL ---
        String[] columnas = {"Estudiante", "Actividad", "Calificación", "Estado"};
        modeloTabla = new DefaultTableModel(columnas, 0) {
            @Override 
            public boolean isCellEditable(int row, int column) { return false; }
        };
        
        tablaReportes = new JTable(modeloTabla);
        estilarTabla(tablaReportes); 
        
        JScrollPane scrollTabla = new JScrollPane(tablaReportes);
        scrollTabla.setBorder(BorderFactory.createLineBorder(new Color(230,230,230)));
        scrollTabla.getViewport().setBackground(Color.WHITE);
        
        this.add(scrollTabla, BorderLayout.CENTER);

        // --- 3. PANEL INFERIOR (Resumen) ---
        JPanel panelResumen = new JPanel(new GridLayout(1, 4, 20, 0));
        panelResumen.setOpaque(false);
        panelResumen.setBorder(new EmptyBorder(10, 0, 0, 0));
        
        lblPromedio = crearTarjetaResumen("Promedio General", "0.0", new Color(169, 223, 191)); 
        lblTotalAlumnos = crearTarjetaResumen("Registros", "0", new Color(249, 231, 159)); 
        
        panelResumen.add(lblPromedio);
        panelResumen.add(lblTotalAlumnos);
        panelResumen.add(new JLabel("")); 
        panelResumen.add(new JLabel(""));
        
        this.add(panelResumen, BorderLayout.SOUTH);
    }
    
    // ... (ESTILOS Y MÉTODOS AUXILIARES IGUAL QUE ANTES) ...
    // ... (estilarTabla, crearTarjetaResumen, cargarComboAulas, getAulaSeleccionada, actualizarTabla, addFiltroListener) ...
    
    // --- COPIA TODO LO QUE TENÍAS ANTES Y AÑADE ESTO AL FINAL: ---
    
    public void addExportarListener(ActionListener al) {
        btnExportar.addActionListener(al);
    }
    
    public JTable getTabla() {
        return tablaReportes;
    }
    
    // --- REPITE LOS MÉTODOS VIEJOS AQUÍ PARA QUE COMPILE SI BORRASTE EL ARCHIVO ---
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
        table.getColumnModel().getColumn(3).setCellRenderer(new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                String estado = (String) value;
                if ("Aprobado".equals(estado)) {
                    setForeground(new Color(39, 174, 96)); 
                    setFont(new Font("SansSerif", Font.BOLD, 14));
                } else {
                    setForeground(new Color(192, 57, 43)); 
                    setFont(new Font("SansSerif", Font.BOLD, 14));
                }
                return c;
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

    public void cargarComboAulas(List<Aula> aulas) {
        this.listaAulasActuales = aulas;
        cmbAulas.removeAllItems();
        for (Aula a : aulas) cmbAulas.addItem(a.getNombre());
    }
    
    public Aula getAulaSeleccionada() {
        int index = cmbAulas.getSelectedIndex();
        if (index >= 0 && listaAulasActuales != null && index < listaAulasActuales.size()) return listaAulasActuales.get(index);
        return null;
    }
    
    public void actualizarTabla(List<Object[]> datos, double promedio) {
        modeloTabla.setRowCount(0);
        for (Object[] fila : datos) modeloTabla.addRow(fila);
        lblTotalAlumnos.setText("<html><div style='text-align:center;'>Registros<br><span style='font-size:18px;'>" + datos.size() + "</span></div></html>");
        lblPromedio.setText("<html><div style='text-align:center;'>Promedio General<br><span style='font-size:18px;'>" + String.format("%.1f", promedio) + "</span></div></html>");
    }
    
    public void addFiltroListener(ActionListener al) { cmbAulas.addActionListener(al); }
}
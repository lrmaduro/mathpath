package controller;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.table.TableModel;
import modelo.Actividad;
import modelo.Aula;
import modelo.Ejercicio;
import modelo.Usuario;
import vista.CrearActividadDialog;
import vista.CrearAulaDialog;
import vista.CrearEjercicioDialog;
import vista.DashboardDocenteView; 
import vista.MainFrame; 
import vista.componentes.AulaCard;

public class DashboardDocenteController {
    
    private MainFrame mainFrame;
    private DashboardDocenteView view;
    private Usuario docente; 
    private AulaService aulaService;
    private ActividadService actividadService;
    private TemaService temaService;
    private EjercicioService ejercicioService;
    
    // --- NUEVO SERVICIO ---
    private ReporteService reporteService;
    
    private Aula aulaActual;

    public DashboardDocenteController(MainFrame mainFrame, DashboardDocenteView view, 
                                      Usuario docente, AulaService aulaService, 
                                      ActividadService actividadService,
                                      TemaService temaService, 
                                      EjercicioService ejercicioService) {
        
        this.mainFrame = mainFrame;
        this.view = view;
        this.docente = docente;
        this.aulaService = aulaService;
        this.actividadService = actividadService;
        this.temaService = temaService;
        this.ejercicioService = ejercicioService;
        
        // Inicializamos el servicio de reportes
        this.reporteService = new ReporteService();
        
        inicializarControlador();
        
        // Carga inicial
        cargarAulas(); 
        cargarTemas();
        cargarEjercicios();
        
        view.showContenidoCard(DashboardDocenteView.PANEL_AULAS);
    }
    
    private void inicializarControlador() {
        // Navegación
        this.view.addMisAulasListener(e -> view.showContenidoCard(DashboardDocenteView.PANEL_AULAS));
        this.view.addActividadesListener(e -> view.showContenidoCard(DashboardDocenteView.PANEL_ACTIVIDADES));
        this.view.addPerfilListener(e -> view.showContenidoCard(DashboardDocenteView.PANEL_PERFIL));
        this.view.addCerrarSesionListener(e -> mainFrame.showCard("LOGIN"));
        
        // --- LÓGICA REPORTES (NUEVO) ---
        this.view.addReportesListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // 1. Cargar las aulas del docente en el combo
                List<Aula> misAulas = aulaService.getAulasPorDocente(docente.getId());
                view.panelReportesView.cargarComboAulas(misAulas);
                
                // 2. Cargar datos por defecto
                actualizarDatosReporte();
                
                // 3. Mostrar panel
                view.showContenidoCard(DashboardDocenteView.PANEL_REPORTES);
            }
        });
        
        // Listener para cambio de filtro en Reportes
        this.view.panelReportesView.addFiltroListener(e -> actualizarDatosReporte());
        
        // Listener para Exportar a Excel/CSV
        this.view.panelReportesView.addExportarListener(e -> exportarReporteCSV());
        // -------------------------------
        
        // Lógica Aulas
        this.view.addCrearAulaListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CrearAulaDialog dialog = new CrearAulaDialog(mainFrame);
                dialog.setVisible(true);

                if (dialog.isGuardado()) {
                    Aula nuevaAula = dialog.getNuevaAula();
                    if (docente != null) {
                        nuevaAula.setIdDocente(docente.getId());
                    }
                    aulaService.addAula(nuevaAula);
                    cargarAulas(); 
                }
            }
        });
        
        this.view.panelAulaDetalle.addVolverListener(e -> view.showContenidoCard(DashboardDocenteView.PANEL_AULAS));
        
        this.view.panelAulaDetalle.addCrearActividadListener(e -> {
             List<String> temas = temaService.getTemas();
             List<Ejercicio> ejercicios = ejercicioService.getTodosLosEjercicios();
             if (aulaActual == null) return;
             CrearActividadDialog dialog = new CrearActividadDialog(mainFrame, ejercicios, aulaActual.getId());
             dialog.setVisible(true);
             if (dialog.isGuardado()) {
                 Actividad nuevaActividad = dialog.getNuevaActividad();
                 if (nuevaActividad.getIdEjercicios().isEmpty()) {
                     JOptionPane.showMessageDialog(mainFrame, "Debe seleccionar al menos un ejercicio.", "Aviso", JOptionPane.WARNING_MESSAGE);
                     return; 
                 }
                 actividadService.addActividad(nuevaActividad);
                 cargarActividades(); 
             }
        });

        this.view.addAgregarTemaListener(e -> {
            String nuevoTema = view.txtNuevoTema.getText();
            if (nuevoTema != null && !nuevoTema.trim().isEmpty()) {
                temaService.addTema(nuevoTema.trim());
                cargarTemas();
                view.txtNuevoTema.setText("");
            }
        });
        
        this.view.addCrearEjercicioListener(e -> {
            List<String> temas = temaService.getTemas(); 
            CrearEjercicioDialog dialog = new CrearEjercicioDialog(mainFrame, temas);
            dialog.setVisible(true);
            if (dialog.isGuardado()) {
                Ejercicio nuevoEjercicio = dialog.getNuevoEjercicio();
                ejercicioService.addEjercicio(nuevoEjercicio);
                cargarEjercicios();
            }
        });
    }

    // --- MÉTODOS DE CARGA ---

    private void cargarAulas() {
        JPanel panel = view.panelAulas;
        panel.removeAll();
        
        List<Aula> aulas;
        if (docente != null) {
            aulas = aulaService.getAulasPorDocente(docente.getId());
        } else {
            aulas = aulaService.getTodasLasAulas(); 
        }

        if (aulas.isEmpty()) {
            JLabel lblVacio = new JLabel("<html><center>No tienes aulas creadas.<br>¡Crea una nueva!</center></html>");
            lblVacio.setForeground(Color.GRAY);
            panel.add(lblVacio);
        } else {
            for (Aula aula : aulas) {
                AulaCard card = new AulaCard(aula);
                card.addVerAulaListener(e -> {
                    aulaActual = aula;
                    view.panelAulaDetalle.actualizarDatos(aulaActual);
                    cargarActividades(); 
                    view.showContenidoCard(DashboardDocenteView.PANEL_AULA_DETALLE);
                });
                panel.add(card);
            }
        }
        panel.revalidate();
        panel.repaint();
    }
    
    private void cargarActividades() {
        if (aulaActual == null) return;
        List<Actividad> actividades = actividadService.getActividadesPorAula(aulaActual.getId());
        JPanel panelLista = view.panelAulaDetalle.panelListaActividades;
        panelLista.removeAll();

        for (Actividad act : actividades) {
            JPanel actPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
            actPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(0,0,1,0, Color.LIGHT_GRAY),
                BorderFactory.createEmptyBorder(5, 5, 5, 5)
            ));
            actPanel.setBackground(Color.WHITE);
            
            JLabel lblInfo = new JLabel("<html><b>" + act.getNombre() + "</b> <span style='color:gray'>(" + act.getTema() + ")</span></html>");
            lblInfo.setPreferredSize(new java.awt.Dimension(300, 20));
            actPanel.add(lblInfo);
            panelLista.add(actPanel);
        }
        panelLista.revalidate();
        panelLista.repaint();
    }

    private void cargarTemas() {
        view.listModelTemas.removeAllElements();
        for (String tema : temaService.getTemas()) {
            view.listModelTemas.addElement(tema);
        }
    }
    
    private void cargarEjercicios() {
        JPanel panelLista = view.panelListaEjercicios;
        panelLista.removeAll();
        List<Ejercicio> ejercicios = ejercicioService.getTodosLosEjercicios();

        for (Ejercicio ej : ejercicios) {
            JPanel ejPanel = new JPanel(new BorderLayout(10, 10));
            ejPanel.setBackground(Color.WHITE);
            ejPanel.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(new Color(200,200,200)),
                    BorderFactory.createEmptyBorder(10, 10, 10, 10)
            ));
            JLabel lblPregunta = new JLabel("<html><font color='#2E86C1'><b>" + ej.getId() + "</b></font>: " + ej.getPregunta() + "</html>");
            lblPregunta.setFont(new java.awt.Font("SansSerif", java.awt.Font.PLAIN, 14));
            ejPanel.add(lblPregunta, BorderLayout.NORTH);
            
            JLabel lblInfo = new JLabel("Tema: " + ej.getIdTema() + "  |  Respuesta: " + ej.getClaveRespuesta());
            lblInfo.setForeground(Color.GRAY);
            ejPanel.add(lblInfo, BorderLayout.CENTER);
            panelLista.add(ejPanel);
            panelLista.add(Box.createVerticalStrut(10)); 
        }
        panelLista.revalidate();
        panelLista.repaint();
    }
    
    // --- MÉTODOS NUEVOS PARA REPORTES ---
    private void actualizarDatosReporte() {
        Aula aulaSel = view.panelReportesView.getAulaSeleccionada();
        if (aulaSel != null) {
            List<Object[]> datos = reporteService.obtenerReportePorAula(aulaSel.getId());
            double promedio = reporteService.calcularPromedioGeneral(datos);
            view.panelReportesView.actualizarTabla(datos, promedio);
        }
    }
    
    private void exportarReporteCSV() {
        JTable tabla = view.panelReportesView.getTabla();
        if (tabla.getRowCount() == 0) {
            JOptionPane.showMessageDialog(mainFrame, "No hay datos para exportar.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Guardar Reporte");
        fileChooser.setSelectedFile(new File("Reporte_Notas.csv")); 
        
        int userSelection = fileChooser.showSaveDialog(mainFrame);
        
        if (userSelection == JFileChooser.APPROVE_OPTION) {
            File fileToSave = fileChooser.getSelectedFile();
            if (!fileToSave.getName().toLowerCase().endsWith(".csv")) {
                fileToSave = new File(fileToSave.getParentFile(), fileToSave.getName() + ".csv");
            }
            
            try (PrintWriter pw = new PrintWriter(new FileWriter(fileToSave, java.nio.charset.StandardCharsets.UTF_8))) {
                pw.write('\ufeff'); // BOM para acentos en Excel
                TableModel model = tabla.getModel();
                
                // Cabeceras
                for (int col = 0; col < model.getColumnCount(); col++) {
                    pw.print(model.getColumnName(col));
                    if (col < model.getColumnCount() - 1) pw.print(",");
                }
                pw.println();
                
                // Datos
                for (int row = 0; row < model.getRowCount(); row++) {
                    for (int col = 0; col < model.getColumnCount(); col++) {
                        Object val = model.getValueAt(row, col);
                        String data = (val == null) ? "" : val.toString();
                        data = data.replace(",", " "); 
                        pw.print(data);
                        if (col < model.getColumnCount() - 1) pw.print(",");
                    }
                    pw.println();
                }
                JOptionPane.showMessageDialog(mainFrame, "Reporte exportado correctamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(mainFrame, "Error al guardar el archivo.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    // --- GESTIÓN DE SESIÓN ---
    public void setUsuarioAutenticado(Usuario nuevoDocente) {
        this.docente = nuevoDocente;
        this.view.actualizarUsuario(nuevoDocente);
        cargarAulas(); 
    }
}
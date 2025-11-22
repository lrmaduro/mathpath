package controller;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import modelo.Actividad;
import modelo.Aula;
import modelo.Ejercicio;
import modelo.Usuario;
import vista.DashboardEstudianteView;
import vista.MainFrame;
import vista.RealizarActividadDialog;
import vista.componentes.AulaCard;

public class DashboardEstudianteController {
    
    private MainFrame mainFrame;
    private DashboardEstudianteView view;
    private Usuario estudiante;
    private AulaService aulaService;
    private ActividadService actividadService;
    private EjercicioService ejercicioService; 
    
    public DashboardEstudianteController(MainFrame mainFrame, DashboardEstudianteView view, 
                                         Usuario estudiante, AulaService aulaService, 
                                         ActividadService actividadService,
                                         EjercicioService ejercicioService) {
        this.mainFrame = mainFrame;
        this.view = view;
        this.estudiante = estudiante;
        this.aulaService = aulaService;
        this.actividadService = actividadService;
        this.ejercicioService = ejercicioService; 
        
        inicializarControlador();
        cargarAulas(); 
    }
    
    private void inicializarControlador() {
        view.addMisAulasListener(e -> {
            cargarAulas();
            view.showContenidoCard(DashboardEstudianteView.PANEL_MIS_AULAS);
        });
        
        view.addCerrarSesionListener(e -> {
            int confirm = JOptionPane.showConfirmDialog(mainFrame, "¿Seguro que deseas salir?", "Cerrar Sesión", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                mainFrame.showCard("LOGIN");
            }
        });
        
        view.addUnirseAulaListener(e -> unirseAAula());
        
        view.getPanelAulaDetalle().addVolverListener(e -> {
            cargarAulas();
            view.showContenidoCard(DashboardEstudianteView.PANEL_MIS_AULAS);
        });
    }
    
    private void cargarAulas() {
        view.panelAulasContainer.removeAll();
        
        // AQUÍ PODRÍAS FILTRAR: Aulas donde el estudiante está inscrito
        // Por ahora mostramos todas para el demo
        List<Aula> aulas = aulaService.getTodasLasAulas();
        
        for (Aula aula : aulas) {
            AulaCard card = new AulaCard(aula);
            card.getBtnVer().addActionListener(e -> abrirAulaDetalle(aula));
            view.panelAulasContainer.add(card);
        }
        
        view.panelAulasContainer.revalidate();
        view.panelAulasContainer.repaint();
    }
    
    private void abrirAulaDetalle(Aula aula) {
        view.getPanelAulaDetalle().actualizarDatos(aula);
        view.getPanelAulaDetalle().btnCrearActividad.setVisible(false); 
        
        cargarActividades(aula);
        view.showContenidoCard(DashboardEstudianteView.PANEL_AULA_DETALLE);
    }
    
    private void cargarActividades(Aula aula) {
        JPanel panelLista = view.getPanelAulaDetalle().panelListaActividades;
        panelLista.removeAll(); 
        
        List<Actividad> actividades = actividadService.getActividadesPorAula(aula.getId());
        
        if (actividades.isEmpty()) {
            panelLista.add(new JLabel("No hay actividades asignadas aún."));
        }
        
        for (Actividad act : actividades) {
            JPanel row = new JPanel(new BorderLayout());
            row.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createMatteBorder(0, 0, 1, 0, Color.LIGHT_GRAY),
                    BorderFactory.createEmptyBorder(10, 10, 10, 10)
            ));
            row.setBackground(Color.WHITE);
            
            String info = String.format("<html><b>%s</b><br/><span style='color:gray'>%s</span></html>", 
                    act.getNombre(), act.getTema());
            row.add(new JLabel(info), BorderLayout.CENTER);
            
            JButton btnResolver = new JButton("Resolver");
            btnResolver.setBackground(new Color(52, 152, 219));
            btnResolver.setForeground(Color.WHITE);
            btnResolver.addActionListener(e -> resolverActividad(act));
            
            row.add(btnResolver, BorderLayout.EAST);
            panelLista.add(row);
        }
        panelLista.revalidate();
        panelLista.repaint();
    }
    
    private void resolverActividad(Actividad actividad) {
        List<Ejercicio> ejerciciosCompletos = new ArrayList<>();
        List<Ejercicio> todos = ejercicioService.getTodosLosEjercicios();
        
        System.out.println("Resolviendo actividad: " + actividad.getNombre());
        
        for (String idEj : actividad.getIdEjercicios()) {
            for (Ejercicio ejReal : todos) {
                if (ejReal.getId().equals(idEj)) {
                    ejerciciosCompletos.add(ejReal);
                    break; 
                }
            }
        }
        
        if (ejerciciosCompletos.isEmpty()) {
            JOptionPane.showMessageDialog(mainFrame, "Esta actividad no tiene ejercicios cargados.");
            return;
        }
        
        RealizarActividadDialog dialog = new RealizarActividadDialog(mainFrame, actividad, ejerciciosCompletos);
        dialog.setVisible(true); 
        
        if (dialog.isFinalizado()) {
            System.out.println("El estudiante sacó: " + dialog.getNotaFinal());
            // Opcional: Guardar en base de datos o historial
        }
    }
    
    private void unirseAAula() {
        String codigo = JOptionPane.showInputDialog(mainFrame, "Ingresa el código del aula:");
        if (codigo != null && !codigo.trim().isEmpty()) {
             // Lógica futura: verificar código y añadir a la lista del alumno
             JOptionPane.showMessageDialog(mainFrame, "Función simulada: Te has unido al aula con código " + codigo);
        }
    }

    // --- ¡ESTE ES EL MÉTODO QUE FALTABA! ---
    // Actualiza la sesión cuando un nuevo estudiante hace login
    public void setUsuarioAutenticado(Usuario nuevoEstudiante) {
        this.estudiante = nuevoEstudiante;
        this.view.actualizarUsuario(nuevoEstudiante);
        
        // Aquí en el futuro recargarías SOLO las aulas de este alumno
        cargarAulas();
    }
}
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
    
    // Simulación: Lista local de IDs de aulas a las que este estudiante se ha unido
    // (En un sistema real, esto vendría de una tabla 'Inscripciones' en base de datos)
    private List<String> misAulasInscritasIds;

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
        
        this.misAulasInscritasIds = new ArrayList<>();
        
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
        
        // --- LÓGICA DE FILTRADO ---
        List<Aula> todas = aulaService.getTodasLasAulas();
        List<Aula> misAulas = new ArrayList<>();
        
        // Filtramos: Solo mostramos las aulas cuyos IDs estén en mi lista de inscripciones
        for (Aula a : todas) {
            if (misAulasInscritasIds.contains(a.getId())) {
                misAulas.add(a);
            }
        }
        
        if (misAulas.isEmpty()) {
            JLabel lblVacio = new JLabel("<html><center>No estás inscrito en ninguna clase.<br>¡Usa el botón 'Unirse'!</center></html>");
            lblVacio.setForeground(Color.GRAY);
            view.panelAulasContainer.add(lblVacio);
        } else {
            for (Aula aula : misAulas) {
                AulaCard card = new AulaCard(aula);
                card.addVerAulaListener(e -> abrirAulaDetalle(aula));
                view.panelAulasContainer.add(card);
            }
        }
        
        view.panelAulasContainer.revalidate();
        view.panelAulasContainer.repaint();
    }
    
    private void abrirAulaDetalle(Aula aula) {
        view.getPanelAulaDetalle().actualizarDatos(aula);
        // Ocultamos botón de crear actividad porque es alumno
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
            // Aquí podrías guardar la nota en un 'NotaService'
        }
    }
    
    private void unirseAAula() {
        String codigoIngresado = JOptionPane.showInputDialog(mainFrame, "Ingresa el código del aula (pídeselo a tu profe):");
        
        if (codigoIngresado != null && !codigoIngresado.trim().isEmpty()) {
            // 1. Buscar si existe un aula con ese código
            Aula aulaEncontrada = null;
            for (Aula a : aulaService.getTodasLasAulas()) {
                if (a.getCodigo().equalsIgnoreCase(codigoIngresado.trim())) {
                    aulaEncontrada = a;
                    break;
                }
            }
            
            if (aulaEncontrada != null) {
                // 2. Verificar si ya estaba inscrito
                if (misAulasInscritasIds.contains(aulaEncontrada.getId())) {
                    JOptionPane.showMessageDialog(mainFrame, "¡Ya estás inscrito en esta clase!", "Aviso", JOptionPane.WARNING_MESSAGE);
                } else {
                    // 3. Inscribirlo (Añadir ID a la lista local)
                    misAulasInscritasIds.add(aulaEncontrada.getId());
                    JOptionPane.showMessageDialog(mainFrame, "¡Éxito! Te has unido a: " + aulaEncontrada.getNombre());
                    cargarAulas(); // Refrescar para que aparezca la tarjeta
                }
            } else {
                JOptionPane.showMessageDialog(mainFrame, "Código no válido. No se encontró ninguna aula.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    public void setUsuarioAutenticado(Usuario nuevoEstudiante) {
        this.estudiante = nuevoEstudiante;
        this.view.actualizarUsuario(nuevoEstudiante);
        
        // Al cambiar de usuario, limpiamos la lista de inscripciones (simulación)
        // En un sistema real, aquí cargaríamos las inscripciones de ESTE alumno desde la BD.
        this.misAulasInscritasIds.clear();
        
        // Si es el usuario "pepito" (demo), le regalamos una inscripción para que no empiece vacío
        if (nuevoEstudiante.getUsuario().equals("pepito")) {
             // Buscamos cualquier aula para dársela
             if (!aulaService.getTodasLasAulas().isEmpty()) {
                 misAulasInscritasIds.add(aulaService.getTodasLasAulas().get(0).getId());
             }
        }
        
        cargarAulas();
    }
}
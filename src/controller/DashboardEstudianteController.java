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
        // Navegación a Mis Aulas
        view.addMisAulasListener(e -> {
            cargarAulas();
            view.showContenidoCard(DashboardEstudianteView.PANEL_MIS_AULAS);
        });
        
        // --- NUEVO: Navegación a Perfil ---
        view.btnPerfil.addActionListener(e -> {
            view.showContenidoCard("PERFIL"); // Asegúrate que en la Vista añadiste el panel con este nombre string
        });
        
        // Cerrar Sesión
        view.addCerrarSesionListener(e -> {
            int confirm = JOptionPane.showConfirmDialog(mainFrame, "¿Seguro que deseas salir?", "Cerrar Sesión", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                mainFrame.showCard("LOGIN");
            }
        });
        
        // Unirse a Aula
        view.addUnirseAulaListener(e -> unirseAAula());
        
        // Volver desde detalle
        view.getPanelAulaDetalle().addVolverListener(e -> {
            cargarAulas();
            view.showContenidoCard(DashboardEstudianteView.PANEL_MIS_AULAS);
        });
        
        // --- NUEVO: Lógica para Guardar Cambios de Perfil ---
        this.view.panelPerfilView.addGuardarListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // 1. Obtener datos del formulario
                String nuevoNombre = view.panelPerfilView.getNombre();
                String nuevaPass = view.panelPerfilView.getPassword();
                
                // 2. Validar
                if (nuevoNombre.isEmpty()) {
                    JOptionPane.showMessageDialog(mainFrame, "El nombre no puede estar vacío.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                
                // 3. Actualizar el objeto Usuario (Estudiante)
                estudiante.setNombre(nuevoNombre);
                if (!nuevaPass.isEmpty()) {
                    estudiante.setPassword(nuevaPass);
                }
                
                // 4. Actualizar la interfaz (Barra lateral)
                view.actualizarUsuario(estudiante);
                
                // 5. Mensaje de éxito
                JOptionPane.showMessageDialog(mainFrame, "¡Tus datos han sido actualizados! ʕ•ᴥ•ʔ", "Perfil Guardado", JOptionPane.INFORMATION_MESSAGE);
            }
        });
    }
    
    private void cargarAulas() {
        view.panelAulasContainer.removeAll();
        
        List<Aula> todas = aulaService.getTodasLasAulas();
        List<Aula> misAulas = new ArrayList<>();
        
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
        }
    }
    
    private void unirseAAula() {
        String codigoIngresado = JOptionPane.showInputDialog(mainFrame, "Ingresa el código del aula (pídeselo a tu profe):");
        
        if (codigoIngresado != null && !codigoIngresado.trim().isEmpty()) {
            Aula aulaEncontrada = null;
            for (Aula a : aulaService.getTodasLasAulas()) {
                if (a.getCodigo().equalsIgnoreCase(codigoIngresado.trim())) {
                    aulaEncontrada = a;
                    break;
                }
            }
            
            if (aulaEncontrada != null) {
                if (misAulasInscritasIds.contains(aulaEncontrada.getId())) {
                    JOptionPane.showMessageDialog(mainFrame, "¡Ya estás inscrito en esta clase!", "Aviso", JOptionPane.WARNING_MESSAGE);
                } else {
                    misAulasInscritasIds.add(aulaEncontrada.getId());
                    JOptionPane.showMessageDialog(mainFrame, "¡Éxito! Te has unido a: " + aulaEncontrada.getNombre());
                    cargarAulas(); 
                }
            } else {
                JOptionPane.showMessageDialog(mainFrame, "Código no válido. No se encontró ninguna aula.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    public void setUsuarioAutenticado(Usuario nuevoEstudiante) {
        this.estudiante = nuevoEstudiante;
        this.view.actualizarUsuario(nuevoEstudiante);
        
        // Limpiamos inscripciones (simulación)
        this.misAulasInscritasIds.clear();
        
        // Regalo para usuario demo
        if (nuevoEstudiante.getUsuario().equals("pepito")) {
             if (!aulaService.getTodasLasAulas().isEmpty()) {
                 misAulasInscritasIds.add(aulaService.getTodasLasAulas().get(0).getId());
             }
        }
        cargarAulas();
    }
}
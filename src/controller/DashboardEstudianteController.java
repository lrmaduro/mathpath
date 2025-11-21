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
    
    // --- IMPORTANTE: Añade EjercicioService al constructor si no lo tenías ---
    private EjercicioService ejercicioService; 
    
    public DashboardEstudianteController(MainFrame mainFrame, DashboardEstudianteView view, 
                                         Usuario estudiante, AulaService aulaService, 
                                         ActividadService actividadService,
                                         EjercicioService ejercicioService) { // <--- AGREGADO AQUÍ
        this.mainFrame = mainFrame;
        this.view = view;
        this.estudiante = estudiante;
        this.aulaService = aulaService;
        this.actividadService = actividadService;
        
        // --- AQUÍ ESTABA EL ERROR ---
        // Antes tenías: this.ejercicioService = new EjercicioService(); 
        // AHORA DEBE SER:
        this.ejercicioService = ejercicioService; 
        // -----------------------------
        
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
        // 1. Pasar datos básicos
        view.getPanelAulaDetalle().actualizarDatos(aula);
        view.getPanelAulaDetalle().btnCrearActividad.setVisible(false); // Ocultar botón profe
        
        // 2. CARGAR ACTIVIDADES (Nuevo método)
        cargarActividades(aula);
        
        // 3. Mostrar vista
        view.showContenidoCard(DashboardEstudianteView.PANEL_AULA_DETALLE);
    }
    
    // --- NUEVO MÉTODO: Dibuja las actividades en la lista ---
    private void cargarActividades(Aula aula) {
        JPanel panelLista = view.getPanelAulaDetalle().panelListaActividades;
        panelLista.removeAll(); // Limpiar anterior
        
        List<Actividad> actividades = actividadService.getActividadesPorAula(aula.getId());
        
        if (actividades.isEmpty()) {
            panelLista.add(new JLabel("No hay actividades asignadas aún."));
        }
        
        for (Actividad act : actividades) {
            // Panel de la fila
            JPanel row = new JPanel(new BorderLayout());
            row.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createMatteBorder(0, 0, 1, 0, Color.LIGHT_GRAY),
                    BorderFactory.createEmptyBorder(10, 10, 10, 10)
            ));
            row.setBackground(Color.WHITE);
            
            // Info de la actividad
            String info = String.format("<html><b>%s</b><br/><span style='color:gray'>%s</span></html>", 
                    act.getNombre(), act.getTema());
            row.add(new JLabel(info), BorderLayout.CENTER);
            
            // Botón Resolver
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
    
    // --- NUEVO MÉTODO: Lanza la ventana de examen ---
    private void resolverActividad(Actividad actividad) {
        // 1. Recuperar los objetos Ejercicio basados en los IDs guardados en la actividad
        List<Ejercicio> ejerciciosCompletos = new ArrayList<>();
        List<Ejercicio> todos = ejercicioService.getTodosLosEjercicios();
        
        // --- DEBUG: Verificación en consola ---
        System.out.println("Resolviendo actividad: " + actividad.getNombre());
        System.out.println("IDs guardados en actividad: " + actividad.getIdEjercicios().size());
        System.out.println("Total ejercicios en sistema: " + todos.size());
        // --------------------------------------
        
        for (String idEj : actividad.getIdEjercicios()) {
            for (Ejercicio ejReal : todos) {
                if (ejReal.getId().equals(idEj)) {
                    ejerciciosCompletos.add(ejReal);
                    break; // Encontrado
                }
            }
        }
        
        // --- DEBUG: Verificación final ---
        System.out.println("Ejercicios encontrados para el examen: " + ejerciciosCompletos.size());
        // ---------------------------------

        if (ejerciciosCompletos.isEmpty()) {
            JOptionPane.showMessageDialog(mainFrame, "Esta actividad no tiene ejercicios cargados.");
            return;
        }
        
        // 2. Abrir el Diálogo
        RealizarActividadDialog dialog = new RealizarActividadDialog(mainFrame, actividad, ejerciciosCompletos);
        dialog.setVisible(true); // Se detiene aquí hasta que termine
        
        // 3. (Opcional) Guardar nota si finalizó
        if (dialog.isFinalizado()) {
            System.out.println("El estudiante sacó: " + dialog.getNotaFinal());
            // Aquí llamarías a un futuro 'NotaService.guardarNota(...)'
        }
    }
    
    private void unirseAAula() {
        String codigo = JOptionPane.showInputDialog(mainFrame, "Ingresa el código del aula:");
        if (codigo != null && !codigo.trim().isEmpty()) {
             // (Lógica de unirse igual que antes...)
             JOptionPane.showMessageDialog(mainFrame, "Función simulada: Te has unido al aula.");
        }
    }
}
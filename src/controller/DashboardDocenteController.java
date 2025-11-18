/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import modelo.Actividad;
import modelo.Aula;
import modelo.Usuario;
import vista.CrearActividadDialog;
import vista.CrearAulaDialog;
import vista.DashboardDocenteView; // Importa la vista
import vista.MainFrame; // Importa el MainFrame
import vista.componentes.AulaCard;

public class DashboardDocenteController {
    
    private MainFrame mainFrame;
    private DashboardDocenteView view;
    private Usuario docente;
    private AulaService aulaService;
    private ActividadService actividadService;
    private TemaService temaService;
    private Aula aulaActual;

    public DashboardDocenteController(MainFrame mainFrame, DashboardDocenteView view, Usuario docente) {
        this.mainFrame = mainFrame;
        this.view = view;
        this.docente = docente;
        this.aulaService = new AulaService();
        this.actividadService = new ActividadService();
        this.temaService = new TemaService();
        
        // Añadir los "listeners" a los botones del menú
        this.view.addMisAulasListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Le pide a la VISTA que muestre el panel de aulas
                view.showContenidoCard(DashboardDocenteView.PANEL_AULAS);
            }
        });
        
        this.view.addActividadesListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                view.showContenidoCard(DashboardDocenteView.PANEL_ACTIVIDADES);
            }
        });
        
        this.view.addReportesListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                view.showContenidoCard(DashboardDocenteView.PANEL_REPORTES);
            }
        });
        
        this.view.addPerfilListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                view.showContenidoCard(DashboardDocenteView.PANEL_PERFIL);
            }
        });
        
        this.view.addCerrarSesionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Le pide al MainFrame que regrese al panel de LOGIN
                mainFrame.showCard("LOGIN"); 
            }
        });
        
        this.view.addCrearAulaListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // 1. Creamos el diálogo. Le pasamos 'mainFrame' como padre
                CrearAulaDialog dialog = new CrearAulaDialog(mainFrame);

                // 2. Lo hacemos visible. El código aquí se detendrá
                //    hasta que el usuario cierre el diálogo.
                dialog.setVisible(true);

                // 3. Cuando el diálogo se cierra, comprobamos si el usuario guardó
                if (dialog.isGuardado()) {

                    // 4. Obtenemos los datos del diálogo
                    Aula nuevaAula = dialog.getNuevaAula();

                    // 5. Le pedimos al servicio que guarde el aula
                    aulaService.addAula(nuevaAula);

                    // 6. ¡Refrescamos nuestra vista de aulas!
                    cargarAulas();
                }
            }
        });
        
        this.view.panelAulaDetalle.addVolverListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Le pedimos a la vista principal que muestre el panel de AULAS
                view.showContenidoCard(DashboardDocenteView.PANEL_AULAS);
            }
        });
        
        this.view.panelAulaDetalle.addCrearActividadListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // 1. OBTENEMOS LOS TEMAS
                List<String> temas = temaService.getTemas();

                // 2. Creamos el diálogo (¡le pasamos la lista de temas!)
                CrearActividadDialog dialog = new CrearActividadDialog(mainFrame, aulaActual.getId(), temas);

                // 3. Lo hacemos visible (esto se queda igual)
                dialog.setVisible(true);

                // 4. Comprobamos si se guardó (esto se queda igual)
                if (dialog.isGuardado()) {
                    Actividad nuevaActividad = dialog.getNuevaActividad();
                    actividadService.addActividad(nuevaActividad);
                    cargarActividades();
                }
            }
        });
        this.view.addAgregarTemaListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // 1. Obtenemos el texto del campo
                String nuevoTema = view.txtNuevoTema.getText();

                // 2. Validamos (que no esté vacío)
                if (nuevoTema != null && !nuevoTema.trim().isEmpty()) {
                    // 3. Lo guardamos en el servicio
                    temaService.addTema(nuevoTema.trim());

                    // 4. Refrescamos la lista
                    cargarTemas();

                    // 5. Limpiamos el campo de texto
                    view.txtNuevoTema.setText("");
                }
            }
        });
        // Mostrar la primera tarjeta por defecto
        view.showContenidoCard(DashboardDocenteView.PANEL_AULAS);
        cargarAulas();
        cargarTemas();
    }
    

    
    // ... (justo antes de la última llave } ) ...

    /**
     * Pide las aulas al servicio y las añade al panel de aulas.
     */
    private void cargarAulas() {
        JPanel panel = view.panelAulas;
        panel.removeAll();
        List<Aula> aulas = aulaService.getTodasLasAulas();

        for (Aula aula : aulas) {
            AulaCard card = new AulaCard(aula);

            // --- AÑADE ESTO ---
            // Le decimos a CADA tarjeta qué hacer cuando se presione "Ver Aula"
            card.addVerAulaListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    // 1. Guardamos el aula actual
                    aulaActual = aula;

                    // 2. Actualiza la info básica (título, código)
                    view.panelAulaDetalle.actualizarDatos(aulaActual);

                    // 3. Carga las actividades de esa aula
                    cargarActividades(); 

                    // 4. Le pide a la vista principal que cambie al panel de detalle
                    view.showContenidoCard(DashboardDocenteView.PANEL_AULA_DETALLE);
                }
            });
            // --- FIN DE LO AÑADIDO ---

            panel.add(card);
        }

        panel.revalidate();
        panel.repaint();
    }
    
    private void cargarActividades() {
        if (aulaActual == null) return; // No hacer nada si no hay aula

        // 1. Pedimos las actividades de ESTA aula al servicio
        List<Actividad> actividades = actividadService.getActividadesPorAula(aulaActual.getId());

        // 2. Obtenemos el panel de la vista de detalle
        JPanel panelLista = view.panelAulaDetalle.panelListaActividades;

        // 3. Limpiamos el panel
        panelLista.removeAll();

        // 4. Creamos los componentes visuales para cada actividad
        for (Actividad act : actividades) {
            JPanel actPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
            actPanel.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
            actPanel.add(new JLabel(act.getNombre() + " (Tema: " + act.getTema() + ")"));
            actPanel.add(new JButton("Editar"));

            panelLista.add(actPanel);
        }

        // 5. Refrescamos el panel
        panelLista.revalidate();
        panelLista.repaint();
    }
    private void cargarTemas() {
        // 1. Limpia la lista actual
        view.listModelTemas.removeAllElements();

        // 2. Pide los temas al servicio
        List<String> temas = temaService.getTemas();

        // 3. Los añade al modelo de la lista
        for (String tema : temas) {
            view.listModelTemas.addElement(tema);
        }
    }
}

package controller;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.time.format.DateTimeFormatter;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import database.dbConnection;
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
    private UsuarioService usuarioService;
    private ActividadService actividadService;
    private EjercicioService ejercicioService;
    private NotaService notaService; // NUEVO

    // Simulación: Lista local de IDs de aulas a las que este estudiante se ha unido
    private List<String> misAulasInscritasIds;
    private java.util.Set<String> actividadesCompletadas = new java.util.HashSet<>(); // Simulación de actividades
    // hechas
    private Aula aulaActual; // Aula que se está viendo actualmente

    public DashboardEstudianteController(MainFrame mainFrame, DashboardEstudianteView view,
            Usuario estudiante, AulaService aulaService,
            ActividadService actividadService,
            EjercicioService ejercicioService, dbConnection db, UsuarioService usuarioService) {
        this.mainFrame = mainFrame;
        this.view = view;
        this.estudiante = estudiante;
        this.aulaService = aulaService;
        this.actividadService = actividadService;
        this.ejercicioService = ejercicioService;
        this.notaService = new NotaService(db); // Inicializar
        this.usuarioService = usuarioService;

        inicializarControlador();

        // Carga inicial asíncrona
        cargarDatosIniciales();
        cargarAulas();

        view.showContenidoCard(DashboardEstudianteView.PANEL_MIS_AULAS);
    }

    private void inicializarControlador() {
        // Configurar vista de estudiante (Pestañas) - YA NO ES NECESARIO, ES NATIVO EN
        // LA CLASE
        // view.getPanelAulaDetalle().configurarVistaEstudiante();

        // Navegación a Mis Aulas
        view.addMisAulasListener(e -> {
            cargarAulas();
            view.showContenidoCard(DashboardEstudianteView.PANEL_MIS_AULAS);
        });

        // Navegación a Mis Notas (NUEVO)
        view.btnNotas.addActionListener(e -> {
            List<Object[]> notas = notaService.obtenerNotasPorEstudiante(estudiante.getId());
            view.actualizarTablaNotas(notas);
            view.showContenidoCard(DashboardEstudianteView.PANEL_NOTAS);
        });

        // --- Navegación a Perfil ---
        view.btnPerfil.addActionListener(e -> {
            view.panelPerfilView.cargarDatos(estudiante);
            view.showContenidoCard("PERFIL"); // Asegúrate que en la Vista añadiste el panel con este nombre string
        });

        // Cerrar Sesión
        view.addCerrarSesionListener(e -> {
            int confirm = JOptionPane.showConfirmDialog(mainFrame, "¿Seguro que deseas salir?", "Cerrar Sesión",
                    JOptionPane.YES_NO_OPTION);
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

        // --- Lógica para Guardar Cambios de Perfil ---
        this.view.panelPerfilView.addGuardarListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // 1. Obtener datos del formulario
                String nuevoNombre = view.panelPerfilView.getNombre();
                String nuevaPass = view.panelPerfilView.getPassword();

                // 2. Validar
                if (nuevoNombre.isEmpty()) {
                    JOptionPane.showMessageDialog(mainFrame, "El nombre no puede estar vacío.", "Error",
                            JOptionPane.ERROR_MESSAGE);
                    return;
                }

                // 3. Actualizar el objeto Usuario (Estudiante)
                estudiante.setNombre(nuevoNombre);
                if (!nuevaPass.isEmpty()) {
                    estudiante.setPassword(nuevaPass);
                }

                // 4. Actualizar la interfaz (Barra lateral)
                view.actualizarUsuario(estudiante);
                usuarioService.actualizarUsuario(estudiante);

                // 5. Mensaje de éxito
                JOptionPane.showMessageDialog(mainFrame, "¡Tus datos han sido actualizados! ʕ•ᴥ•ʔ", "Perfil Guardado",
                        JOptionPane.INFORMATION_MESSAGE);
            }
        });

        // Lógica Música Perfil
        this.view.panelPerfilView.getChkMusica().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                AudioService.getInstance().toggleMusica(view.panelPerfilView.getChkMusica().isSelected());
            }
        });
    }

    private void cargarDatosIniciales() {
        new Thread(() -> {
            this.misAulasInscritasIds = new ArrayList<>();
            if (estudiante != null) {
                List<Aula> todas = aulaService.getAulasPorEstudiante(estudiante.getId());
                for (Aula a : todas) {
                    misAulasInscritasIds.add(a.getId());
                }
            }
        }).start();
    }

    private void cargarAulas() {
        // Mostrar mensaje de carga inmediatamente
        view.panelAulasContainer.removeAll();
        JLabel lblCargando = new JLabel(
                "<html><center>Cargando clases...</center></html>");
        lblCargando.setForeground(Color.GRAY);
        view.panelAulasContainer.add(lblCargando);
        view.panelAulasContainer.revalidate();
        view.panelAulasContainer.repaint();

        new Thread(() -> {
            if (estudiante == null) {
                return;
            }
            List<Aula> todas = aulaService.getAulasPorEstudiante(estudiante.getId());
            List<Aula> misAulas = new ArrayList<>(todas);

            javax.swing.SwingUtilities.invokeLater(() -> {
                view.panelAulasContainer.removeAll();

                if (misAulas.isEmpty()) {
                    JLabel lblVacio = new JLabel(
                            "<html><center>No estás inscrito en ninguna clase.<br>¡Usa el botón 'Unirse'!</center></html>");
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
            });
        }).start();
    }

    private void abrirAulaDetalle(Aula aula) {
        this.aulaActual = aula; // Guardar el aula actual
        view.getPanelAulaDetalle().actualizarDatos(aula);
        view.getPanelAulaDetalle().btnCrearActividad.setVisible(false);

        cargarActividades(aula);
        view.showContenidoCard(DashboardEstudianteView.PANEL_AULA_DETALLE);
    }

    private void cargarActividades(Aula aula) {
        // Mostrar mensajes de carga en todas las pestañas
        view.getPanelAulaDetalle().panelPorHacer.removeAll();
        view.getPanelAulaDetalle().panelHechas.removeAll();
        view.getPanelAulaDetalle().panelExpiradas.removeAll();

        JLabel lblCargando1 = new JLabel("Cargando actividades...");
        lblCargando1.setForeground(Color.GRAY);
        view.getPanelAulaDetalle().panelPorHacer.add(lblCargando1);

        JLabel lblCargando2 = new JLabel("Cargando actividades...");
        lblCargando2.setForeground(Color.GRAY);
        view.getPanelAulaDetalle().panelHechas.add(lblCargando2);

        JLabel lblCargando3 = new JLabel("Cargando actividades...");
        lblCargando3.setForeground(Color.GRAY);
        view.getPanelAulaDetalle().panelExpiradas.add(lblCargando3);

        view.getPanelAulaDetalle().panelPorHacer.revalidate();
        view.getPanelAulaDetalle().panelPorHacer.repaint();
        view.getPanelAulaDetalle().panelHechas.revalidate();
        view.getPanelAulaDetalle().panelHechas.repaint();
        view.getPanelAulaDetalle().panelExpiradas.revalidate();
        view.getPanelAulaDetalle().panelExpiradas.repaint();

        new Thread(() -> {
            List<Actividad> actividades = actividadService.getActividadesPorAula(aula.getId());
            List<String> actividadesCompletadas = actividadService.getActividadesHechas(estudiante.getId());

            javax.swing.SwingUtilities.invokeLater(() -> {
                // Limpiar paneles de pestañas
                view.getPanelAulaDetalle().panelPorHacer.removeAll();
                view.getPanelAulaDetalle().panelHechas.removeAll();
                view.getPanelAulaDetalle().panelExpiradas.removeAll();

                if (actividades.isEmpty()) {
                    view.getPanelAulaDetalle().panelPorHacer.add(new JLabel("No hay actividades asignadas aún."));
                }

                for (Actividad act : actividades) {
                    boolean isExpirada = act.getFechaLimite() != null
                            && java.time.LocalDateTime.now().isAfter(act.getFechaLimite());
                    boolean isHecha = actividadesCompletadas.contains(act.getId());
                    JPanel row = createActivityRow(act, isHecha, isExpirada);

                    // Clasificación

                    if (isHecha) {
                        view.getPanelAulaDetalle().panelHechas.add(row);
                    } else if (isExpirada) {
                        view.getPanelAulaDetalle().panelExpiradas.add(row);
                    } else {
                        view.getPanelAulaDetalle().panelPorHacer.add(row);
                    }
                }

                view.getPanelAulaDetalle().panelPorHacer.revalidate();
                view.getPanelAulaDetalle().panelPorHacer.repaint();
                view.getPanelAulaDetalle().panelHechas.revalidate();
                view.getPanelAulaDetalle().panelHechas.repaint();
                view.getPanelAulaDetalle().panelExpiradas.revalidate();
                view.getPanelAulaDetalle().panelExpiradas.repaint();
            });
        }).start();
    }

    private JPanel createActivityRow(Actividad act, boolean isHecha, boolean isExpirada) {
        JPanel row = new JPanel(new BorderLayout());
        String info;
        row.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(0, 0, 1, 0, Color.LIGHT_GRAY),
                BorderFactory.createEmptyBorder(10, 10, 10, 10)));
        row.setBackground(Color.WHITE);

        // FIJAR TAMAÑO (Igual que en Docente)
        row.setPreferredSize(new java.awt.Dimension(0, 80));
        row.setMaximumSize(new java.awt.Dimension(Integer.MAX_VALUE, 80));

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        String fechaStr = (act.getFechaLimite() != null) ? act.getFechaLimite().format(formatter) : "Sin fecha";

        if (isHecha || isExpirada) {
            info = String.format(
                    "<html><b>%s</b><br/><span style='color:gray'>%s</span><br/><span style='font-size:10px; color:#E74C3C'>Venció: %s</span></html>",
                    act.getNombre(), act.getTema(), fechaStr);
        } else {
            info = String.format(
                    "<html><b>%s</b><br/><span style='color:gray'>%s</span><br/><span style='font-size:10px; color:#E74C3C'>Vence: %s</span></html>",
                    act.getNombre(), act.getTema(), fechaStr);
        }
        row.add(new JLabel(info), BorderLayout.CENTER);

        JButton btnResolver = new JButton("Resolver");
        btnResolver.setBackground(new Color(52, 152, 219));
        btnResolver.setForeground(Color.WHITE);

        // Si ya está hecha, cambiar botón
        // Si ya está hecha, cambiar botón
        if (isHecha) {
            btnResolver.setText("Hecho ✓");
            btnResolver.setBackground(new Color(46, 204, 113));
            btnResolver.setEnabled(false);
        } else if (act.getFechaLimite() != null && java.time.LocalDateTime.now().isAfter(act.getFechaLimite())) {
            btnResolver.setText("Vencido");
            btnResolver.setBackground(Color.GRAY);
            btnResolver.setEnabled(false);
        } else {
            btnResolver.addActionListener(e -> resolverActividad(act));
        }

        row.add(btnResolver, BorderLayout.EAST);
        return row;
    }

    private void resolverActividad(Actividad actividad) {
        // VALIDACIÓN DE FECHA LÍMITE
        if (actividad.getFechaLimite() != null && java.time.LocalDateTime.now().isAfter(actividad.getFechaLimite())) {
            JOptionPane.showMessageDialog(mainFrame,
                    "¡Lo sentimos! El plazo para esta actividad ha vencido el " +
                            actividad.getFechaLimite()
                                    .format(java.time.format.DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")),
                    "Actividad Vencida",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }
        List<Ejercicio> ejerciciosCompletos = ejercicioService.getEjerciciosPorActividad(actividad.getId());

        if (ejerciciosCompletos.isEmpty()) {
            JOptionPane.showMessageDialog(mainFrame, "Esta actividad no tiene ejercicios cargados.");
            return;
        }

        RealizarActividadDialog dialog = new RealizarActividadDialog(mainFrame, actividad, ejerciciosCompletos,
                notaService, estudiante);
        dialog.setVisible(true);

        if (dialog.isFinalizado()) {
            actividadesCompletadas.add(actividad.getId());
            System.out.println("El estudiante sacó: " + dialog.getNotaFinal());

            // Recargar la lista para que se mueva de pestaña
            if (aulaActual != null) {
                cargarActividades(aulaActual);
            }
        }
    }

    private void unirseAAula() {
        String codigoIngresado = JOptionPane.showInputDialog(mainFrame,
                "Ingresa el código del aula (pídeselo a tu profe):");
        for (Aula a : aulaService.getAulasPorEstudiante(estudiante.getId())) {
            misAulasInscritasIds.add(a.getId());
        }

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
                    JOptionPane.showMessageDialog(mainFrame, "¡Ya estás inscrito en esta clase!", "Aviso",
                            JOptionPane.WARNING_MESSAGE);
                } else {
                    misAulasInscritasIds.add(aulaEncontrada.getId());
                    aulaService.inscribirseAAula(aulaEncontrada.getId(), estudiante.getId());
                    JOptionPane.showMessageDialog(mainFrame, "¡Éxito! Te has unido a: " + aulaEncontrada.getNombre());
                    cargarAulas();
                }
            } else {
                JOptionPane.showMessageDialog(mainFrame, "Código no válido. No se encontró ninguna aula.", "Error",
                        JOptionPane.ERROR_MESSAGE);
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
        // Always show the initial panel (Mis Aulas) after login
        view.showContenidoCard(DashboardEstudianteView.PANEL_MIS_AULAS);
    }
}
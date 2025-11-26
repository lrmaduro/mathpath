package controller;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.Box;
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
        view.addCerrarSesionListener(e -> {
            int confirm = JOptionPane.showConfirmDialog(mainFrame, "¿Seguro que deseas salir?", "Cerrar Sesión",
                    JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                mainFrame.showCard("LOGIN");
            }
        });
        // --- LÓGICA REPORTES (CORREGIDA) ---

        // 1. Botón Principal Reportes
        this.view.addReportesListener(e -> {
            List<Aula> misAulas = aulaService.getAulasPorDocente(docente.getId());
            view.panelReportesView.cargarComboAulas(misAulas);
            view.showContenidoCard(DashboardDocenteView.PANEL_REPORTES);
        });

        // 2. Cambio de Aula (Carga actividades y reporte general)
        this.view.panelReportesView.addFiltroAulaListener(e -> {
            Aula aulaSel = view.panelReportesView.getAulaSeleccionada();
            if (aulaSel != null) {
                List<Actividad> acts = actividadService.getActividadesPorAula(aulaSel.getId());
                view.panelReportesView.cargarComboActividades(acts);
                // true = Reporte General
                actualizarDatosReporte(true);
            }
        });

        // 3. Cambio de Actividad (Reporte específico)
        this.view.panelReportesView.addFiltroActividadListener(e -> {
            // false = Reporte Específico
            actualizarDatosReporte(false);
        });

        // 4. Exportar
        this.view.panelReportesView.addExportarListener(e -> exportarReporteCSV());

        // ----------------------------------

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
            if (aulaActual == null)
                return;
            CrearActividadDialog dialog = new CrearActividadDialog(mainFrame, ejercicios, aulaActual.getId());
            dialog.setVisible(true);
            if (dialog.isGuardado()) {
                Actividad nuevaActividad = dialog.getNuevaActividad();
                if (nuevaActividad.getIdEjercicios().isEmpty()) {
                    JOptionPane.showMessageDialog(mainFrame, "Debe seleccionar al menos un ejercicio.", "Aviso",
                            JOptionPane.WARNING_MESSAGE);
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

        this.view.panelPerfilView.addGuardarListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String nuevoNombre = view.panelPerfilView.getNombre();
                String nuevaPass = view.panelPerfilView.getPassword();

                if (nuevoNombre.isEmpty()) {
                    JOptionPane.showMessageDialog(mainFrame, "El nombre no puede estar vacío.");
                    return;
                }

                // Actualizamos el objeto docente
                docente.setNombre(nuevoNombre);
                if (!nuevaPass.isEmpty()) {
                    docente.setPassword(nuevaPass);
                }

                // Actualizamos la interfaz (Barra lateral y título) y Panel con iniciales
                view.actualizarUsuario(docente);
                view.panelPerfilView.actualizarNombre(nuevoNombre);

                JOptionPane.showMessageDialog(mainFrame, "¡Perfil actualizado correctamente!");
            }
        });
    }

    // --- MÉTODOS DE CARGA ---

    private void cargarAulas() {
        view.panelAulas.removeAll();

        List<Aula> aulas;
        if (docente != null) {
            aulas = aulaService.getAulasPorDocente(docente.getId());
        } else {
            aulas = aulaService.getTodasLasAulas();
        }

        if (aulas.isEmpty()) {
            JLabel lblVacio = new JLabel("<html><center>No tienes aulas creadas.<br>¡Crea una nueva!</center></html>");
            lblVacio.setForeground(Color.GRAY);
            view.panelAulas.add(lblVacio);
        } else {
            for (Aula aula : aulas) {
                AulaCard card = new AulaCard(aula);
                card.addVerAulaListener(e -> {
                    aulaActual = aula;
                    view.panelAulaDetalle.actualizarDatos(aulaActual);
                    cargarActividades();
                    view.showContenidoCard(DashboardDocenteView.PANEL_AULA_DETALLE);
                });
                view.panelAulas.add(card);
            }
        }
        view.panelAulas.revalidate();
        view.panelAulas.repaint();
    }

    private void cargarActividades() {
        if (aulaActual == null)
            return;
        List<Actividad> actividades = actividadService.getActividadesPorAula(aulaActual.getId());
        JPanel panelLista = view.panelAulaDetalle.panelListaActividades;
        panelLista.removeAll();

        for (Actividad act : actividades) {
            // Usamos BorderLayout para separar info (izquierda) de botones (derecha)
            JPanel actPanel = new JPanel(new BorderLayout(10, 0));
            actPanel.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createMatteBorder(0, 0, 1, 0, Color.LIGHT_GRAY),
                    BorderFactory.createEmptyBorder(8, 8, 8, 8)));
            actPanel.setBackground(Color.WHITE);

            // FIJAR TAMAÑO para que el scroll funcione bien y no se estiren
            // Ancho 0 para que no fuerce scroll horizontal (BoxLayout lo estirará)
            actPanel.setPreferredSize(new java.awt.Dimension(0, 80));
            actPanel.setMaximumSize(new java.awt.Dimension(Integer.MAX_VALUE, 80));

            // INFO
            java.time.format.DateTimeFormatter formatter = java.time.format.DateTimeFormatter
                    .ofPattern("dd/MM/yyyy HH:mm");
            String fechaStr = (act.getFechaLimite() != null) ? act.getFechaLimite().format(formatter) : "Sin fecha";

            JLabel lblInfo = new JLabel("<html><b>" + act.getNombre() + "</b> <span style='color:gray'>("
                    + act.getTema() + ")</span><br/><span style='font-size:10px; color:#E74C3C'>Vence: " + fechaStr
                    + "</span></html>");
            actPanel.add(lblInfo, BorderLayout.CENTER);

            // BOTONES
            JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
            panelBotones.setOpaque(false);

            // Botón Ver Detalle
            javax.swing.JButton btnDetalle = new javax.swing.JButton("Ver Detalle");
            btnDetalle.setBackground(new Color(52, 152, 219)); // Azul
            btnDetalle.setForeground(Color.WHITE);
            btnDetalle.setFont(new java.awt.Font("SansSerif", java.awt.Font.BOLD, 11));
            btnDetalle.setFocusPainted(false);
            btnDetalle.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
            btnDetalle.addActionListener(e -> {
                String detalle = "ID: " + act.getId() + "\n" +
                        "Nombre: " + act.getNombre() + "\n" +
                        "Tema: " + act.getTema() + "\n" +
                        "Ejercicios: " + act.getIdEjercicios().size();
                JOptionPane.showMessageDialog(mainFrame, detalle, "Detalle de Actividad",
                        JOptionPane.INFORMATION_MESSAGE);
            });

            // Botón Eliminar
            javax.swing.JButton btnEliminar = new javax.swing.JButton("Eliminar");
            btnEliminar.setBackground(new Color(231, 76, 60)); // Rojo
            btnEliminar.setForeground(Color.WHITE);
            btnEliminar.setFont(new java.awt.Font("SansSerif", java.awt.Font.BOLD, 11));
            btnEliminar.setFocusPainted(false);
            btnEliminar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
            btnEliminar.addActionListener(e -> {
                int confirm = JOptionPane.showConfirmDialog(mainFrame,
                        "¿Estás seguro de eliminar la actividad '" + act.getNombre() + "'?",
                        "Confirmar Eliminación",
                        JOptionPane.YES_NO_OPTION);

                if (confirm == JOptionPane.YES_OPTION) {
                    actividadService.eliminarActividad(act.getId());
                    cargarActividades(); // Recargar la lista
                    JOptionPane.showMessageDialog(mainFrame, "Actividad eliminada.");
                }
            });

            panelBotones.add(btnDetalle);
            panelBotones.add(btnEliminar);

            actPanel.add(panelBotones, BorderLayout.EAST);

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
                    BorderFactory.createLineBorder(new Color(200, 200, 200)),
                    BorderFactory.createEmptyBorder(10, 10, 10, 10)));
            JLabel lblPregunta = new JLabel(
                    "<html><font color='#2E86C1'><b>" + ej.getId() + "</b></font>: " + ej.getPregunta() + "</html>");
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

    // --- MÉTODOS CORREGIDOS PARA REPORTES ---

    private void actualizarDatosReporte(boolean esGeneral) {
        Aula aulaSel = view.panelReportesView.getAulaSeleccionada();
        if (aulaSel == null)
            return;

        List<Object[]> datos;
        int indiceColumnaNota; // ¡AQUÍ ESTÁ EL TRUCO!

        Actividad actSel = view.panelReportesView.getActividadSeleccionada();

        if (actSel != null && !esGeneral) {
            // REPORTE POR ACTIVIDAD: La nota está en la columna 1
            datos = reporteService.obtenerReportePorActividad(actSel.getId());
            indiceColumnaNota = 1;
        } else {
            // REPORTE GENERAL: La nota está en la columna 2
            datos = reporteService.obtenerReportePorAula(aulaSel.getId());
            indiceColumnaNota = 2;
        }

        // Actualizar Tabla
        view.panelReportesView.actualizarTabla(datos);

        // Actualizar Histograma pasando el índice correcto para evitar
        // NumberFormatException
        int[] distribucion = reporteService.obtenerDistribucionNotas(datos, indiceColumnaNota);
        view.panelReportesView.actualizarHistograma(distribucion);
    }

    private void exportarReporteCSV() {
        JTable tabla = view.panelReportesView.getTabla();
        if (tabla.getRowCount() == 0) {
            JOptionPane.showMessageDialog(mainFrame, "No hay datos para exportar.", "Aviso",
                    JOptionPane.WARNING_MESSAGE);
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

            try (PrintWriter pw = new PrintWriter(
                    new FileWriter(fileToSave, java.nio.charset.StandardCharsets.UTF_8))) {
                pw.write('\ufeff');
                TableModel model = tabla.getModel();

                for (int col = 0; col < model.getColumnCount(); col++) {
                    pw.print(model.getColumnName(col));
                    if (col < model.getColumnCount() - 1)
                        pw.print(",");
                }
                pw.println();

                for (int row = 0; row < model.getRowCount(); row++) {
                    for (int col = 0; col < model.getColumnCount(); col++) {
                        Object val = model.getValueAt(row, col);
                        String data = (val == null) ? "" : val.toString();
                        data = data.replace(",", " ");
                        pw.print(data);
                        if (col < model.getColumnCount() - 1)
                            pw.print(",");
                    }
                    pw.println();
                }
                JOptionPane.showMessageDialog(mainFrame, "Reporte exportado correctamente.", "Éxito",
                        JOptionPane.INFORMATION_MESSAGE);
            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(mainFrame, "Error al guardar el archivo.", "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    public void setUsuarioAutenticado(Usuario nuevoDocente) {
        this.docente = nuevoDocente;
        this.view.actualizarUsuario(nuevoDocente);
        cargarAulas();
    }
}
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
import modelo.Tema;
import modelo.Usuario;
import vista.CrearActividadDialog;
import vista.CrearAulaDialog;
import vista.CrearEjercicioDialog;
import vista.DashboardDocenteView;
import vista.MainFrame;
import vista.componentes.AulaCard;
import database.dbConnection;

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
    private dbConnection db;
    private UsuarioService usuarioService;

    public DashboardDocenteController(MainFrame mainFrame, DashboardDocenteView view,
            Usuario docente, AulaService aulaService,
            ActividadService actividadService,
            TemaService temaService,
            EjercicioService ejercicioService, dbConnection db) {

        this.db = db;
        this.mainFrame = mainFrame;
        this.view = view;
        this.docente = docente;
        this.aulaService = aulaService;
        this.actividadService = actividadService;
        this.temaService = temaService;
        this.ejercicioService = ejercicioService;
        this.reporteService = new ReporteService(db);
        this.usuarioService = new UsuarioService(db);

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
        this.view.addPerfilListener(e -> {
            view.panelPerfilView.cargarDatos(docente);
            view.showContenidoCard(DashboardDocenteView.PANEL_PERFIL);
        });
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
            List<Tema> temas = temaService.getTemas();
            List<Ejercicio> ejercicios = ejercicioService.getTodosLosEjercicios();
            if (aulaActual == null)
                return;
            CrearActividadDialog dialog = new CrearActividadDialog(mainFrame, ejercicios, temas, aulaActual.getId());
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
            List<Tema> temas = temaService.getTemas();
            CrearEjercicioDialog dialog = new CrearEjercicioDialog(mainFrame, temas);
            dialog.setVisible(true);
            if (dialog.isGuardado()) {
                Ejercicio nuevoEjercicio = dialog.getNuevoEjercicio();
                ejercicioService.addEjercicio(nuevoEjercicio, docente.getId());
                cargarEjercicios();
            }
        });

        // Listener Filtro
        this.view.cmbFiltroTemaEjercicios.addActionListener(e -> cargarEjercicios());

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
                usuarioService.actualizarUsuario(docente);
                view.panelPerfilView.actualizarNombre(nuevoNombre);

                JOptionPane.showMessageDialog(mainFrame, "¡Perfil actualizado correctamente!");
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

    // --- MÉTODOS DE CARGA ---

    private void cargarAulas() {
        view.panelAulas.removeAll();

        List<Aula> aulas;
        if (docente != null) {
            aulas = aulaService.getAulasPorDocente(docente.getId());
        } else {
            aulas = aulaService.getTodasLasAulas();
        }

        if (aulas == null || aulas.isEmpty()) {
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
                    cargarEstudiantes(); // NUEVO: Cargar lista de estudiantes
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

            // Botón Ver Detalle (AHORA EDITAR)
            javax.swing.JButton btnDetalle = new javax.swing.JButton("Editar");
            btnDetalle.setBackground(new Color(52, 152, 219)); // Azul
            btnDetalle.setForeground(Color.WHITE);
            btnDetalle.setFont(new java.awt.Font("SansSerif", java.awt.Font.BOLD, 11));
            btnDetalle.setFocusPainted(false);
            btnDetalle.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
            btnDetalle.addActionListener(e -> {
                List<Ejercicio> ejercicios = ejercicioService.getTodosLosEjercicios();
                List<Tema> temas = temaService.getTemas();
                vista.EditarActividadDialog dialog = new vista.EditarActividadDialog(mainFrame, ejercicios, temas, act);
                dialog.setVisible(true);

                if (dialog.isGuardado()) {
                    Actividad actEditada = dialog.getActividadEditada();
                    actividadService.actualizarActividad(actEditada);
                    cargarActividades(); // Recargar lista
                    JOptionPane.showMessageDialog(mainFrame, "Actividad actualizada correctamente.");
                }
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

    private void cargarEstudiantes() {
        if (aulaActual == null)
            return;

        JPanel panelLista = view.panelAulaDetalle.panelListaEstudiantes;
        panelLista.removeAll();

        List<Usuario> estudiantes = usuarioService.getEstudiantesAula(aulaActual.getId());

        if (estudiantes == null || estudiantes.isEmpty()) {
            JLabel lblInfo = new JLabel(
                    "<html><center>No hay estudiantes inscritos en esta aula.</center></html>");
            lblInfo.setForeground(Color.GRAY);
            lblInfo.setAlignmentX(java.awt.Component.CENTER_ALIGNMENT);
            panelLista.add(Box.createVerticalStrut(20));
            panelLista.add(lblInfo);
        } else {
            for (Usuario est : estudiantes) {
                JPanel estPanel = new JPanel(new BorderLayout(10, 0));
                estPanel.setBorder(BorderFactory.createCompoundBorder(
                        BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(240, 240, 240)),
                        BorderFactory.createEmptyBorder(10, 10, 10, 10)));
                estPanel.setBackground(Color.WHITE);
                estPanel.setMaximumSize(new java.awt.Dimension(Integer.MAX_VALUE, 60));

                // Icono o Avatar (Placeholder)
                JLabel lblAvatar = new JLabel("👤");
                lblAvatar.setFont(new java.awt.Font("Segoe UI Emoji", java.awt.Font.PLAIN, 24));
                estPanel.add(lblAvatar, BorderLayout.WEST);

                // Info Estudiante
                JLabel lblNombre = new JLabel("<html><b>" + est.getNombre() + "</b><br/>"
                        + "<span style='color:gray; font-size:10px'>@" + est.getUsuario() + "</span></html>");
                estPanel.add(lblNombre, BorderLayout.CENTER);

                // Botón Eliminar
                javax.swing.JButton btnEliminar = new javax.swing.JButton("Eliminar");
                btnEliminar.setBackground(new Color(231, 76, 60)); // Rojo
                btnEliminar.setForeground(Color.WHITE);
                btnEliminar.setFont(new java.awt.Font("SansSerif", java.awt.Font.BOLD, 11));
                btnEliminar.setFocusPainted(false);
                btnEliminar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
                btnEliminar.addActionListener(e -> {
                    int confirm = JOptionPane.showConfirmDialog(mainFrame,
                            "¿Estás seguro de eliminar al estudiante '" + est.getNombre() + "' de esta aula?",
                            "Confirmar Eliminación",
                            JOptionPane.YES_NO_OPTION);

                    if (confirm == JOptionPane.YES_OPTION) {
                        boolean exito = aulaService.removerEstudianteDeAula(aulaActual.getId(), est.getId());
                        if (exito) {
                            cargarEstudiantes(); // Recargar la lista
                            JOptionPane.showMessageDialog(mainFrame, "Estudiante eliminado del aula.");
                        } else {
                            JOptionPane.showMessageDialog(mainFrame, "Error al eliminar al estudiante.", "Error",
                                    JOptionPane.ERROR_MESSAGE);
                        }
                    }
                });

                estPanel.add(btnEliminar, BorderLayout.EAST);

                panelLista.add(estPanel);
            }
        }

        panelLista.revalidate();
        panelLista.repaint();
    }

    private void cargarTemas() {
        view.listModelTemas.removeAllElements();
        view.cmbFiltroTemaEjercicios.removeAllItems();
        view.cmbFiltroTemaEjercicios.addItem("Todos");

        for (Tema tema : temaService.getTemas()) {
            view.listModelTemas.addElement(tema.getNombre());
            view.cmbFiltroTemaEjercicios.addItem(tema.getNombre());
        }
    }

    private void cargarEjercicios() {
        JPanel panelLista = view.panelListaEjercicios;
        panelLista.removeAll();
        List<Ejercicio> ejercicios = ejercicioService.getTodosLosEjercicios();

        String filtro = (String) view.cmbFiltroTemaEjercicios.getSelectedItem();
        if (filtro != null && !filtro.equals("Todos")) {
            // Filtrar
            ejercicios = ejercicios.stream()
                    .filter(e -> e.getIdTema().equals(filtro))
                    .toList();
        }

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

            // Botón Ver Detalle
            javax.swing.JButton btnDetalle = new javax.swing.JButton("Ver Detalle");
            btnDetalle.setBackground(new Color(52, 152, 219));
            btnDetalle.setForeground(Color.WHITE);
            btnDetalle.setFocusPainted(false);
            btnDetalle.addActionListener(e -> {
                new vista.VerEjercicioDialog(mainFrame, ej).setVisible(true);
            });

            // Botón Eliminar
            javax.swing.JButton btnEliminar = new javax.swing.JButton("Eliminar");
            btnEliminar.setBackground(new Color(231, 76, 60)); // Rojo
            btnEliminar.setForeground(Color.WHITE);
            btnEliminar.setFocusPainted(false);
            btnEliminar.addActionListener(e -> {
                int confirm = JOptionPane.showConfirmDialog(mainFrame,
                        "¿Estás seguro de eliminar el ejercicio '" + ej.getId() + "'?",
                        "Confirmar Eliminación",
                        JOptionPane.YES_NO_OPTION);

                if (confirm == JOptionPane.YES_OPTION) {
                    ejercicioService.eliminarEjercicio(ej.getId());
                    cargarEjercicios(); // Recargar la lista
                    JOptionPane.showMessageDialog(mainFrame, "Ejercicio eliminado.");
                }
            });

            JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.RIGHT));
            panelBotones.setOpaque(false);
            panelBotones.add(btnDetalle);
            panelBotones.add(btnEliminar);

            ejPanel.add(panelBotones, BorderLayout.EAST);

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
        // Always show the initial panel (Mis Aulas) after login
        view.showContenidoCard(DashboardDocenteView.PANEL_AULAS);
    }
}
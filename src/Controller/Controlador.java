/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller;
import GUI.Docente.*;
import GUI.Estudiante.*;
import GUI.*;
import coil.prototipo.logica.*;
import database.dbConnections;
import java.awt.BorderLayout;
import java.awt.Font;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class Controlador {
    
    // El controlador necesita acceso al Modelo y a la Vista principal
    private final dbConnections db = new dbConnections("jdbc:sqlite:src/database/mathpath.db");
    private MainFrame mainFrame;
    private Estudiante estudianteLogueado;
    private Docente docenteLogueado;
    
    private ListaPreguntas lp;
    
    // (A medida que crezca, también guardará los paneles)
    private InicioSesionPanel loginPanel;
    private SeleccionarAula selAul;
    private DashboardEstudiantePanel dashEst;
    private DashboardDocente dashDoc;
    private AulaPanel aulDoc;
    private ReportesDocente repDoc;
    private AulaInfoPanelDocente aulInfo;
    private String panelPrevio;
    private String panelActual;
    private String panelDashboard;
    private Actividad actividadEnVista;
    private ArrayList<Actividad> la;
    private ArrayList<Pregunta> listaPreg;
    private Pregunta pregActual;
    
    /**
     * Constructor
     */
    public Controlador() {
        // Aún no podemos hacer nada, necesitamos que nos pasen las vistas
        la = new ArrayList<>();
        listaPreg = new ArrayList<>();
    }

    public MainFrame getMainFrame() {
        return mainFrame;
    }

    public Actividad getActividadEnVista() {
        return actividadEnVista;
    }
    
    
    
    
    // --- Métodos "Setter" para conectar las partes ---
   
    
    
    public void setActividadEnVista(Actividad actividadEnVista) {
        this.actividadEnVista = actividadEnVista;
    }

    public void setMainFrame(MainFrame mainFrame) {
        this.mainFrame = mainFrame;
    }
    
    public void setLoginPanel(InicioSesionPanel loginPanel) {
        this.loginPanel = loginPanel;
    }

    public void setSelAul(SeleccionarAula selAul) {
        this.selAul = selAul;
    }

    public void setDashDoc(DashboardDocente dashDoc) {
        this.dashDoc = dashDoc;
    }


    public void setAulDoc(AulaPanel aulDoc) {
        this.aulDoc = aulDoc;
    }

    public void setRepDoc(ReportesDocente repDoc) {
        this.repDoc = repDoc;
    }

    public void setDashEst(DashboardEstudiantePanel dashEst) {
        this.dashEst = dashEst;
    }

    public void setAulInfo(AulaInfoPanelDocente aulInfo) {
        this.aulInfo = aulInfo;
    }
    
    public ListaPreguntas getListaPreguntas() {
        return lp;
    }
    
    
    
    public void inicializarPanelesEstudiante() {
        DashboardEstudiante dashEst = new DashboardEstudiante(this);
        CalificacionesPanelEstudiante calPan = new CalificacionesPanelEstudiante(this);
        ActividadesPanelEstudiante actPan = new ActividadesPanelEstudiante(this);
        ActividadPanelInfoEstudiante actInfoEst = new ActividadPanelInfoEstudiante(this);
        CorrectoPanel corrPan = new CorrectoPanel(this);
        IncorrectoPanel incorrPan = new IncorrectoPanel(this);
        EjercicioPanel ejercicioPanel = new EjercicioPanel(this);
        
        mainFrame.add(dashEst, "dashEst");
        mainFrame.add(ejercicioPanel, "ejerPanel");
        mainFrame.add(calPan, "calPan");
        mainFrame.add(actPan, "actPan");
        mainFrame.add(actInfoEst, "ActPanInfoEst");
        mainFrame.add(corrPan, "CorrPanel");
        mainFrame.add(incorrPan, "IncorrPanel");
    }
    
    public void inicializarPanelesDocente() {
        
    }
    
    public void inicializarPanelesInicio() {
        
    }
    
    public void cargarAulasProf(JList Lista) {
        ArrayList<Aula> la = db.listarAulas(docenteLogueado.getId_docente());
        DefaultListModel<Aula> modeloLista = new DefaultListModel<>();
        
        // Llena el modelo
        for (Aula aula : la) {
            modeloLista.addElement(aula);
        }
        
        // Asigna el modelo al JList
        // (Asumo que tu JList se llama jListAulas)
        Lista.setModel(modeloLista);
    } 

    /**
     * Crea una nueva aula usando los datos del docente logueado y refresca
     * el listado de aulas del docente en el panel correspondiente.
     * Retorna true si la creación fue exitosa.
     */
    public boolean crearAula(String nombre, String descripcion) {
        if (docenteLogueado == null) {
            JOptionPane.showMessageDialog(mainFrame, "No hay docente logueado.", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        String idDocente = docenteLogueado.getId_docente();
        String newId = db.nuevaAulaConDescripcion(idDocente, nombre, descripcion);
        if (newId != null) {
            // Refrescar lista de aulas en el panel de aulas si está registrado
            if (this.aulDoc != null) {
                this.aulDoc.cargarAulas();
            }
            JOptionPane.showMessageDialog(mainFrame, "Aula creada con ID: " + newId, "Aula creada", JOptionPane.INFORMATION_MESSAGE);
            return true;
        } else {
            JOptionPane.showMessageDialog(mainFrame, "Error al crear el aula en la base de datos.", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }

    
    public Estudiante getEstudianteLogueado() {
        return estudianteLogueado;
    }
    
    public Docente getDocenteLogueado() {
        return docenteLogueado;
    }
    
    public void siguientePreg() {
        if (listaPreg.isEmpty())
            ;
        else {
            pregActual = listaPreg.getFirst();
            listaPreg.removeFirst();
            this.cambiarVentana("");
        }
    }

    
    
    public void Login(String username, String password, int type) {
        System.out.println("DB inicializado? " + (db != null));
        System.out.println(mainFrame.getName());
        try {
            if (type == 2) { // Flujo de Estudiante
                estudianteLogueado = db.loginEstudiante(username, password);
                
                if (estudianteLogueado != null) {
                    // ¡Éxito!
//                    JOptionPane.showMessageDialog(mainFrame, "Login exitoso: " + estudianteLogueado.getUsername(), "Login exitoso!", JOptionPane.INFORMATION_MESSAGE);
//                    JOptionPane.showMessageDialog(mainFrame, username, username, type);

                    // El controlador ahora orquesta todo:
                    // 1. Carga las aulas en el panel de selección
                    this.selAul.cargarAulas(estudianteLogueado.getId_usuario());
                    // 2. Le dice al MainFrame que muestre ese panel
                    this.mainFrame.showPanel("dashEst");
                    panelActual = "dashEst";
                    panelDashboard = "dashEst";
                    docenteLogueado = null;
                    
                } else 
                    JOptionPane.showMessageDialog(mainFrame, "Usuario o contraseña incorrecta", "Error en login", JOptionPane.ERROR_MESSAGE);
                
            } else if (type == 1) { // Flujo de Docente
                
                // (Lógica de login de docente aquí...)
                this.docenteLogueado = db.loginDocente(username, password);

                if (this.docenteLogueado != null) {
//                    JOptionPane.showMessageDialog(mainFrame, "Login exitoso: " + this.docenteLogueado.getUsername(), "Login exitoso!", JOptionPane.INFORMATION_MESSAGE);
//                    JOptionPane.showMessageDialog(mainFrame, username, username, type);
                    System.out.println("DOCENTE LOGUEADO: id_docente=" + this.docenteLogueado.getId_docente() + " id_usuario=" + this.docenteLogueado.getId_usuario());
                    // Pre-cargar paneles relacionados si existen (usar id_docente desde el objeto Docente)
                    if (this.aulDoc != null) {
                        this.aulDoc.cargarAulas();
                    }
                    this.mainFrame.showPanel("dashDoc");
                    panelActual = "dashDoc";
                    panelDashboard = "dashDoc";
                    estudianteLogueado = null;

                } else
                    JOptionPane.showMessageDialog(mainFrame, "Usuario o contraseña incorrecta", "Error en login", JOptionPane.ERROR_MESSAGE);
                   
                
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(mainFrame, "Error de conexión: " + e.getMessage());
        }
    }
    
    public void logout() {
        if (JOptionPane.showConfirmDialog(mainFrame, "¿Cerrar Sesión?",
            "Logout", JOptionPane.YES_NO_OPTION, 
            JOptionPane.INFORMATION_MESSAGE) == 0) {
            estudianteLogueado = null;
            docenteLogueado = null;
            JOptionPane.showMessageDialog(mainFrame, "¡Adios, vuelva pronto!", "¡Logout exitoso!", JOptionPane.INFORMATION_MESSAGE);
            this.cambiarVentana("login");
        } 
        
        
    }
    
    public void volverInicio() {
        if (JOptionPane.showConfirmDialog(mainFrame, "¿Cerrar Sesión?",
        "Logout", JOptionPane.YES_NO_OPTION, 
        JOptionPane.INFORMATION_MESSAGE) == 0) {
            estudianteLogueado = null;
            docenteLogueado = null;
            this.cambiarVentana("inicio");
        }
    }
    
    public void cambiarVentana(String constraint) {
        panelPrevio = panelActual;
        mainFrame.showPanel(constraint);
        panelActual = constraint;
    }
    
    public void volverPanelAnterior() {
        this.cambiarVentana(panelPrevio);
    }
    
    public void volverPanelDashboard() {
        this.cambiarVentana(panelDashboard);
    }
    
    public void showAula(Aula aula) {
        if (aulInfo != null) {
            aulInfo.setAula(aula);
            if (mainFrame != null) {
                panelPrevio = panelActual;
                mainFrame.showPanel("aulaInfo");
                panelActual = "aulaInfo";
            }
        } else {
            System.out.println("No hay panel AulaInfo registrado en el controlador.");
        }
    }
    
    public void toggleBoton(JButton boton) {
        boton.setEnabled(!boton.isEnabled());
    }

    /**
     * Edita el nombre y descripción de un aula. Refresca la lista si procede.
     */
    public boolean editarAula(String idAula, String nombre, String descripcion) {
        if (idAula == null) return false;
        boolean ok = db.updateAula(idAula, nombre, descripcion);
        if (ok) {
            if (this.aulDoc != null && this.docenteLogueado != null) {
                this.aulDoc.cargarAulas();
            }
        }
        return ok;
    }

    /**
     * Elimina un aula y regresa al panel de listado si la operación fue exitosa.
     */
    public boolean eliminarAula(String idAula) {
        if (idAula == null) return false;
        boolean ok = db.deleteAula(idAula);
        if (ok) {
            if (this.aulDoc != null && this.docenteLogueado != null) {
                this.aulDoc.cargarAulas();
            }
            if (this.mainFrame != null) {
                this.mainFrame.showPanel("aulDoc");
                panelActual = "aulDoc";
            }
        }
        return ok;
    }
    
    public void llenarTablaNotasDoc(JTable tabla) {
        String[] columnas = {"Fecha", "Estudiante", "Evaluacion", "Aula", "Nota"};
        ListaNotas listaNotas;
        if (estudianteLogueado == null)
            listaNotas = db.listarNotas();
        else
            listaNotas = db.listarNotas(estudianteLogueado.getIdEstudiante());
        ArrayList<Nota> lista = listaNotas.getListaNotas();
        DefaultTableModel dtm = new DefaultTableModel(null, columnas);

        for (Nota n : lista) {
            System.out.println("id_eval = "+n.getId_evaluacion());
            String[] fila = {
                db.buscarFechaNota(String.valueOf(n.getId_estudiante()), String.valueOf(n.getId_evaluacion())),
                db.buscarNombreEst(String.valueOf(n.getId_estudiante())),
                db.buscarNombreEval(String.valueOf(n.getId_evaluacion())),
                db.buscarNomAulaPorEval(String.valueOf(n.getId_evaluacion())),
                String.valueOf(n.getNota())};
            dtm.addRow(fila);
        }
        tabla.setModel(dtm);
    }
    
    public void llenarTablaNotas(JTable tabla) {
        String[] columnas = {"Fecha", "Evaluacion", "Nota"};
        ListaNotas listaNotas;
        if (estudianteLogueado == null)
            listaNotas = db.listarNotas();
        else
            listaNotas = db.listarNotas(estudianteLogueado.getIdEstudiante());
        ArrayList<Nota> lista = listaNotas.getListaNotas();
        DefaultTableModel dtm = new DefaultTableModel(null, columnas);

        for (Nota n : lista) {
            System.out.println("id_eval = "+n.getId_evaluacion());
            String[] fila = {db.buscarFechaNota(String.valueOf(n.getId_estudiante()), String.valueOf(n.getId_evaluacion())),
            db.buscarNombreEval(String.valueOf(n.getId_evaluacion())),
            String.valueOf(n.getNota())};
            dtm.addRow(fila);
        }
        tabla.setModel(dtm);
    }
    
    public void llenarListaActividades(JList lista) {
        DefaultListModel<String> modeloLista = new DefaultListModel<>();
        ArrayList<String> actividades = new ArrayList<>();
        actividades.add("Actividad 1");
        actividades.add("Actividad 2");
        actividades.add("Actividad 3");
        modeloLista.addAll(actividades);
        lista.setModel(modeloLista);
    }
    
    
    public void llenarTablaActividades(JTable tabla) {
        String[] columnas = {"Nombre", "Descripcion", "Fecha Pautada"};
        DefaultTableModel dtm = new DefaultTableModel(null, columnas);
        for (Actividad a : la) {
            String[] row = {a.getNombre(), a.getDescripcion(), a.getFecha_limite().toString()};
            dtm.addRow(row);
        }
        tabla.setModel(dtm);
    }
    
    public void setListaActividades() {
        la = new ArrayList<>();
        la.add(new Actividad("1", "Sumas y restas", "Actividad 1 de sumas y restas.", LocalDateTime.of(2025, Month.DECEMBER, 12, 10, 0), 20, "Operaciones de suma y resta"));
        la.add(new Actividad("2", "Reconocimiento de patrones matemáticos", "Actividad 1 de reconocimiento de patrones.", LocalDateTime.of(2025, Month.OCTOBER, 6, 15, 0), 20, "Reconocimiento de patrones"));
        la.add(new Actividad("3", "Operaciones con ecuaciones", "Actividad 1 sobre ecuaciones.", LocalDateTime.of(2025, Month.NOVEMBER, 23, 14, 30), 20, "Ecuaciones"));
    }
    
    public void rmActividad(String nombre) {
        for (Actividad a : la) {
            if (a.getNombre().equals(nombre)) {
                la.remove(a);
                break;
            }
        }
    }
    
    public void addActividad(String id, String nombre, String desc, String fecha, String tema) {
        la.add(new Actividad(id, nombre, desc, LocalDateTime.parse(fecha), 20, tema));
    }
    
    public void llenarListaEjercicios(JList lista) {
        DefaultListModel<Pregunta> modeloLista = new DefaultListModel<>();
        modeloLista.addAll(listaPreg);
        lista.setModel(modeloLista);        
    }
    
    public void setLP() {
        listaPreg = new ArrayList<>();
        ArrayList<Respuesta> rl1 = new ArrayList<>();
        rl1.add(new Respuesta("7", false));
        rl1.add(new Respuesta("10", true));
        rl1.add(new Respuesta("15", false));
        rl1.add(new Respuesta("12", false));
        ArrayList<Respuesta> rl2 = new ArrayList<>();
        rl2.add(new Respuesta("1", false));
        rl2.add(new Respuesta("6", true));
        rl2.add(new Respuesta("5", true));
        rl2.add(new Respuesta("7", false)); 
        ArrayList<Respuesta> rl3 = new ArrayList<>();
        rl3.add(new Respuesta("4", true));
        rl3.add(new Respuesta("22", true));
        rl3.add(new Respuesta("5", true));
        rl3.add(new Respuesta("3", false));
        listaPreg.add(new Pregunta(1, "5 x 2", rl1, 2));
        listaPreg.add(new Pregunta(2, "1, 2, 3, 4, __", rl1, 2));
        listaPreg.add(new Pregunta(3, "2 + 2", rl1, 2));
    }
    
    public void LlenarCBPreguntas(JComboBox cb, Tema t) {
        cb.removeAllItems();
        ListaPreguntas l = db.listarEjercicios(t.getIdTema());
        ArrayList<Pregunta> lp = l.getListaPreg();
        for (Pregunta i : lp) {
            cb.addItem(i);
        }
    }
    
    public void LlenarCBTemas(JComboBox cb) {
        cb.removeAllItems();
        ListaTemas lt = db.listarTemas();
        ArrayList<Tema> l = lt.getListaTemas();
        for (Tema t : l) {
            cb.addItem(t);
        }   
    }
    
    public void AulaSeleccionada(Aula aula) {
        if (aula != null) {
            System.out.println("CONTROLADOR: Aula seleccionada: " + aula.getNombre() + " (ID: " + aula.getId_aula() + ")");
            
            // Futuro:
            // 1. Cargar los datos de ESA aula (temas, etc.)
            // 2. mainFrame.showPanel("panelDelAula");
        }
    }
    
    public PreguntaChecklistPanel agregarChecklistPreguntas(JPanel contenedor) {        
        PreguntaChecklistPanel checklistPanel = new PreguntaChecklistPanel(listaPreg);

        contenedor.setLayout(new BorderLayout());
        contenedor.add(checklistPanel, BorderLayout.CENTER);
        contenedor.revalidate();
        contenedor.repaint();
        return checklistPanel;
    }
    
    public void fetchPreguntas() {
        lp = db.listarEjercicios("1");
    }
    
    public void verActInfo(String[] Act) {
        actividadEnVista = new Actividad();
        actividadEnVista.setNombre(Act[0]);
        actividadEnVista.setDescripcion(Act[1]);
        actividadEnVista.setFecha_limite(LocalDateTime.parse(Act[2]));
        this.cambiarVentana("ActPanInfoDoc");
    }
    
    public void crearActividad() {
        if (JOptionPane.showConfirmDialog(mainFrame, "¿Crear Actividad?",
                "Confirmación de Creación de Actividad", JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE) == 0)
            JOptionPane.showMessageDialog(mainFrame, "Actividad creada exitosamente.", "Creación Exitosa", JOptionPane.INFORMATION_MESSAGE);
        else
            JOptionPane.showMessageDialog(mainFrame, "Cancelada la creación de actividad.", "Cancelación de Creación", JOptionPane.ERROR_MESSAGE);
    }
}

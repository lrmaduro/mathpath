/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller;
import GUI.Docente.ReportesDocente;
import GUI.Docente.ActividadesPanelDocente;
import GUI.Docente.DashboardDocente;
import GUI.Docente.AulasPanelDocente;
import GUI.Docente.AulaInfoPanelDocente;
import GUI.Estudiante.DashboardEstudiante;
import GUI.*;
import coil.prototipo.logica.*;
import database.dbConnections;
import java.util.ArrayList;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class Controlador {
    
    // El controlador necesita acceso al Modelo y a la Vista principal
    private final dbConnections db = new dbConnections("jdbc:sqlite:src/database/mathpath.db");
    private MainFrame mainFrame;
    private Estudiante estudianteLogueado;
    private Docente docenteLogueado;
    
    // (A medida que crezca, también guardará los paneles)
    private LoginPanel loginPanel;
    private SeleccionarAula selAul;
    private DashboardEstudiante dashEst;
    private DashboardDocente dashDoc;
    private ActividadesPanelDocente actDoc;
    private AulasPanelDocente aulDoc;
    private ReportesDocente repDoc;
    private AulaInfoPanelDocente aulInfo;
    private String panelPrevio;
    private String panelActual;
    private String panelDashboard;
    
    
    /**
     * Constructor
     */
    public Controlador() {
        // Aún no podemos hacer nada, necesitamos que nos pasen las vistas
    }
    
    // --- Métodos "Setter" para conectar las partes ---
   
    
    public void setMainFrame(MainFrame mainFrame) {
        this.mainFrame = mainFrame;
    }
    
    public void setLoginPanel(LoginPanel loginPanel) {
        this.loginPanel = loginPanel;
    }

    public void setSelAul(SeleccionarAula selAul) {
        this.selAul = selAul;
    }

    public void setDashDoc(DashboardDocente dashDoc) {
        this.dashDoc = dashDoc;
    }

    public void setActDoc(ActividadesPanelDocente actDoc) {
        this.actDoc = actDoc;
    }

    public void setAulDoc(AulasPanelDocente aulDoc) {
        this.aulDoc = aulDoc;
    }

    public void setRepDoc(ReportesDocente repDoc) {
        this.repDoc = repDoc;
    }

    public void setDashEst(DashboardEstudiante dashEst) {
        this.dashEst = dashEst;
    }

    public void setAulInfo(AulaInfoPanelDocente aulInfo) {
        this.aulInfo = aulInfo;
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
                this.aulDoc.cargarAulas(idDocente);
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
                        this.aulDoc.cargarAulas(this.docenteLogueado.getId_docente());
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
        estudianteLogueado = null;
        docenteLogueado = null;
        this.cambiarVentana("login");
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
                this.aulDoc.cargarAulas(this.docenteLogueado.getId_docente());
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
                this.aulDoc.cargarAulas(this.docenteLogueado.getId_docente());
            }
            if (this.mainFrame != null) {
                this.mainFrame.showPanel("aulDoc");
                panelActual = "aulDoc";
            }
        }
        return ok;
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
}

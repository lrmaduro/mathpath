/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller;
import GUI.*;
import coil.prototipo.logica.Aula;
import database.dbConnections;
import coil.prototipo.logica.Estudiante;
import coil.prototipo.logica.Docente;
import coil.prototipo.logica.ListaNotas;
import coil.prototipo.logica.Nota;
import java.util.ArrayList;
import javax.swing.JButton;
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
                    
                } else 
                    JOptionPane.showMessageDialog(mainFrame, "Usuario o contraseña incorrecta", "Error en login", JOptionPane.ERROR_MESSAGE);
                
            } else if (type == 1) { // Flujo de Docente
                
                // (Lógica de login de docente aquí...)
                this.docenteLogueado = db.loginDocente(username, password);

                if (this.docenteLogueado != null) {
                    JOptionPane.showMessageDialog(mainFrame, "Login exitoso: " + this.docenteLogueado.getUsername(), "Login exitoso!", JOptionPane.INFORMATION_MESSAGE);
                    JOptionPane.showMessageDialog(mainFrame, username, username, type);
                    System.out.println("DOCENTE LOGUEADO: id_docente=" + this.docenteLogueado.getId_docente() + " id_usuario=" + this.docenteLogueado.getId_usuario());
                    // Pre-cargar paneles relacionados si existen (usar id_docente desde el objeto Docente)
                    if (this.aulDoc != null) {
                        this.aulDoc.cargarAulas(this.docenteLogueado.getId_docente());
                    }
                    this.mainFrame.showPanel("dashDoc");
                    panelActual = "dashDoc";
                } else
                    JOptionPane.showMessageDialog(mainFrame, "Usuario o contraseña incorrecta", "Error en login", JOptionPane.ERROR_MESSAGE);
                   
                
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(mainFrame, "Error de conexión: " + e.getMessage());
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
    
    public void llenarTablaNotas(JTable tabla) {
        String[] columnas = {"Fecha", "Evaluacion", "Nota"};
        ListaNotas listaNotas = db.listarNotas(estudianteLogueado.getIdEstudiante());
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
    
    public void AulaSeleccionada(Aula aula) {
        if (aula != null) {
            System.out.println("CONTROLADOR: Aula seleccionada: " + aula.getNombre() + " (ID: " + aula.getId_aula() + ")");
            
            // Futuro:
            // 1. Cargar los datos de ESA aula (temas, etc.)
            // 2. mainFrame.showPanel("panelDelAula");
        }
    }
}

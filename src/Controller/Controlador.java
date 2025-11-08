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

    public void setDashEst(DashboardEstudiante dashEst) {
        this.dashEst = dashEst;
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
                Docente docenteLogueado = db.loginDocente(username, password);
                
                if (docenteLogueado != null) {
                    JOptionPane.showMessageDialog(mainFrame, "Login exitoso: " + docenteLogueado.getUsername(), "Login exitoso!", JOptionPane.INFORMATION_MESSAGE);
                    JOptionPane.showMessageDialog(mainFrame, username, username, type);

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

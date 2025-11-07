/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller;
import GUI.LoginPanel;
import GUI.MainFrame;
import GUI.SeleccionarAula; // Necesitarás esto
import coil.prototipo.logica.Aula;
import database.dbConnections;
import coil.prototipo.logica.Estudiante;
import coil.prototipo.logica.Docente;
import javax.swing.JOptionPane;

public class Controlador {
    
    // El controlador necesita acceso al Modelo y a la Vista principal
    private dbConnections db;
    private MainFrame mainFrame;
    
    // (A medida que crezca, también guardará los paneles)
    private LoginPanel loginPanel;
    private SeleccionarAula selAul;
    
    
    /**
     * Constructor
     */
    public Controlador() {
        // Aún no podemos hacer nada, necesitamos que nos pasen las vistas
    }
    
    // --- Métodos "Setter" para conectar las partes ---
    
    public void setDb(dbConnections db) {
        this.db = db;
    }
    
    public void setMainFrame(MainFrame mainFrame) {
        this.mainFrame = mainFrame;
    }
    
    public void setLoginPanel(LoginPanel loginPanel) {
        this.loginPanel = loginPanel;
    }

    public void setSelAul(SeleccionarAula selAul) {
        this.selAul = selAul;
    }

    
    public void Login(String username, String password, int type) {
        
        try {
            if (type == 2) { // Flujo de Estudiante
                
                Estudiante estudianteLogueado = db.loginEstudiante(username, password);
                
                if (estudianteLogueado != null) {
                    // ¡Éxito!
                    JOptionPane.showMessageDialog(mainFrame, "Login exitoso: " + estudianteLogueado.getUsername());
                    
                    // El controlador ahora orquesta todo:
                    // 1. Carga las aulas en el panel de selección
                    this.selAul.cargarAulas(estudianteLogueado.getId_usuario());
                    // 2. Le dice al MainFrame que muestre ese panel
                    this.mainFrame.showPanel("selAul");
                    
                } else {
                    JOptionPane.showMessageDialog(mainFrame, "Usuario o contraseña incorrecta");
                }
                
            } else if (type == 1) { // Flujo de Docente
                
                // (Lógica de login de docente aquí...)
                
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(mainFrame, "Error de conexión: " + e.getMessage());
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

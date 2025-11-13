
package GUI;
    
import Controller.Controlador;
import GUI.Estudiante.*;
import GUI.Docente.*;
import database.dbConnections;
import coil.prototipo.logica.Estudiante;
import coil.prototipo.logica.Aula;
import java.awt.CardLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class MainFrame extends javax.swing.JFrame {
    
    private CardLayout cardLayout;
    private JPanel mainPanel;
    
    private Controlador controlador;
    private dbConnections db;

    public MainFrame() {
        initComponents();

        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);
        controlador = new Controlador();

        // Instancia de panel de login
        LoginPanel login = new LoginPanel(controlador);
        
        // Instancias de paneles de estudiante
        SeleccionarAula selAul = new SeleccionarAula(controlador);
        EjercicioPanel ejercicioPanel = new EjercicioPanel(controlador);
        DashboardEstudiante dashEst = new DashboardEstudiante(controlador);
        GestionAulas gesAul = new GestionAulas();
        CalificacionesPanelEstudiante calPan = new CalificacionesPanelEstudiante(controlador);
        ActividadesPanelEstudiante actPan = new ActividadesPanelEstudiante(controlador);
        DashboardDocente dashDoc = new DashboardDocente(controlador);
        AulasPanelDocente aulDoc = new AulasPanelDocente(controlador);
        ActividadesPanelDocente actDoc = new ActividadesPanelDocente(controlador);
        ReportesDocente repDoc = new ReportesDocente(controlador);
        AulaInfoPanelDocente aulaInfo = new AulaInfoPanelDocente(controlador);
        CrearAulaPanelDocente crearAula = new CrearAulaPanelDocente(controlador);
        ActividadPanelInfoDocente actInfoDoc = new ActividadPanelInfoDocente(controlador);
        ActividadPanelInfoEstudiante actInfoEst = new ActividadPanelInfoEstudiante(controlador);
        CorrectoPanel corrPan = new CorrectoPanel(controlador);
        IncorrectoPanel incorrPan = new IncorrectoPanel(controlador);
        UnirseAulaPanel uniiAul = new UnirseAulaPanel(controlador);

        // Agregar al contenedor principal
        mainPanel.add(login, "login");
        mainPanel.add(gesAul, "gesAul");
        mainPanel.add(selAul, "selAul");
        mainPanel.add(dashEst, "dashEst");
        mainPanel.add(ejercicioPanel, "ejerPanel");
        mainPanel.add(calPan, "calPan");
        mainPanel.add(actPan, "actPan");
        mainPanel.add(dashDoc, "dashDoc");
        mainPanel.add(aulDoc, "aulDoc");
        mainPanel.add(aulaInfo, "aulaInfo");
        mainPanel.add(crearAula, "crearAula");
        mainPanel.add(actDoc, "actDoc");
        mainPanel.add(repDoc, "repDoc");
        mainPanel.add(actInfoDoc, "ActPanInfoDoc");
        mainPanel.add(actInfoEst, "ActPanInfoEst");
        mainPanel.add(corrPan, "CorrPanel");
        mainPanel.add(incorrPan, "IncorrPanel");
         
        controlador.setMainFrame(this);
        controlador.setLoginPanel(login);
        controlador.setSelAul(selAul);
        controlador.setDashEst(dashEst);
        controlador.setAulInfo(aulaInfo);
        controlador.setAulDoc(aulDoc);

        // Añadir al frame
        this.setContentPane(mainPanel);
        this.setSize(600, 400);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public void showPanel(String name) {
        cardLayout.show(mainPanel, name);

    }
    
 
    // Si quieres arrancar desde aquí:
    public static void main(String[] args) {
        java.awt.EventQueue.invokeLater(() -> {
            MainFrame mf = new MainFrame();
            mf.setVisible(true);
        });
    }
    
    
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(MainFrame.class.getName());

    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 623, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 450, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}

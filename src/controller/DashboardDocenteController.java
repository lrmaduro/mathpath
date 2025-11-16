/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import modelo.Aula;
import modelo.Usuario;
import vista.CrearAulaDialog;
import vista.DashboardDocenteView; // Importa la vista
import vista.MainFrame; // Importa el MainFrame
import vista.componentes.AulaCard;

public class DashboardDocenteController {
    
    private MainFrame mainFrame;
    private DashboardDocenteView view;
    private Usuario docente;
    private AulaService aulaService;

    public DashboardDocenteController(MainFrame mainFrame, DashboardDocenteView view, Usuario docente) {
        this.mainFrame = mainFrame;
        this.view = view;
        this.docente = docente;
        this.aulaService = new AulaService();
        
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
        // Mostrar la primera tarjeta por defecto
        view.showContenidoCard(DashboardDocenteView.PANEL_AULAS);
        cargarAulas();
    }
    
    // ... (justo antes de la última llave } ) ...

    /**
     * Pide las aulas al servicio y las añade al panel de aulas.
     */
    private void cargarAulas() {
        // 1. Obtenemos el panel de la vista
        JPanel panel = view.panelAulas;

        // (Opcional) Limpiamos el panel por si tenía algo antes
        panel.removeAll();

        // 2. Pedimos los datos al servicio
        List<Aula> aulas = aulaService.getTodasLasAulas();

        // 3. Creamos una tarjeta por cada aula y la añadimos al panel
        for (Aula aula : aulas) {
            AulaCard card = new AulaCard(aula);
            panel.add(card);
        }

        // 4. Refrescamos el panel para que muestre las tarjetas
        panel.revalidate();
        panel.repaint();
    }
}

package controller;

import javax.swing.SwingUtilities;
import modelo.Usuario;
import vista.DashboardDocenteView;
import vista.DashboardEstudianteView;
import vista.LoginView;
import vista.MainFrame;

public class Lanzador {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                // 1. Crear los servicios ("Base de Datos")
                UsuarioService usuarioService = new UsuarioService();
                AulaService aulaService = new AulaService();
                ActividadService actividadService = new ActividadService();
                TemaService temaService = new TemaService();
                EjercicioService ejercicioService = new EjercicioService();
                
                // 2. Crear la ventana principal
                MainFrame mainFrame = new MainFrame();

                // 3. Usuarios Placeholder (para iniciar las vistas, luego se actualizarán)
                Usuario docentePlaceholder = usuarioService.validarLogin("profe", "123");
                Usuario estudiantePlaceholder = usuarioService.validarLogin("pepito", "456");

                // 4. Crear las VISTAS
                LoginView loginView = new LoginView();
                DashboardDocenteView docenteView = new DashboardDocenteView(docentePlaceholder);
                DashboardEstudianteView estudianteView = new DashboardEstudianteView(estudiantePlaceholder);

                // 5. Añadir tarjetas al MainFrame
                mainFrame.addCard(loginView, "LOGIN");
                mainFrame.addCard(docenteView, "DOCENTE");
                mainFrame.addCard(estudianteView, "ESTUDIANTE");

                // 6. INICIAR CONTROLADORES
                // IMPORTANTE: El orden cambia. Primero creamos los de los dashboards
                
                // A) Controlador Docente
                DashboardDocenteController docController = new DashboardDocenteController(
                    mainFrame, 
                    docenteView, 
                    docentePlaceholder, 
                    aulaService, 
                    actividadService, 
                    temaService, 
                    ejercicioService
                );
                
                // B) Controlador Estudiante
                DashboardEstudianteController estController = new DashboardEstudianteController(
                    mainFrame, 
                    estudianteView, 
                    estudiantePlaceholder, 
                    aulaService, 
                    actividadService, 
                    ejercicioService
                );
                
                // C) Controlador de Login (Ahora recibe los otros dos para poder avisarles)
                new LoginController(
                    mainFrame, 
                    loginView, 
                    usuarioService, 
                    docController, 
                    estController
                );

                // 7. Arrancar
                mainFrame.showCard("LOGIN");
                mainFrame.setVisible(true);
            }
        });
    }
}
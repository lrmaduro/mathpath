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
                // 1. INSTANCIAR TODOS LOS SERVICIOS ("Base de datos en memoria")
                UsuarioService usuarioService = new UsuarioService();
                AulaService aulaService = new AulaService();
                ActividadService actividadService = new ActividadService();
                // Agregamos los que faltaban:
                TemaService temaService = new TemaService();
                EjercicioService ejercicioService = new EjercicioService();

                // 2. Crear la ventana principal
                MainFrame mainFrame = new MainFrame();

                // 3. Usuarios Placeholder
                Usuario docentePlaceholder = usuarioService.validarLogin("profe", "123");
                Usuario estudiantePlaceholder = usuarioService.validarLogin("pepito", "456");

                // 4. Crear las Vistas
                LoginView loginView = new LoginView();
                DashboardDocenteView docenteView = new DashboardDocenteView(docentePlaceholder);
                DashboardEstudianteView estudianteView = new DashboardEstudianteView(estudiantePlaceholder);

                // 5. Añadir tarjetas al MainFrame
                mainFrame.addCard(loginView, "LOGIN");
                mainFrame.addCard(docenteView, "DOCENTE");
                mainFrame.addCard(estudianteView, "ESTUDIANTE");

                // 6. INICIAR CONTROLADORES

                // A) Controlador de Login
                new LoginController(mainFrame, loginView, usuarioService);

                // B) Controlador Docente (AHORA SÍ COINCIDE)
                // Le pasamos todos los servicios creados arriba
                new DashboardDocenteController(
                    mainFrame, 
                    docenteView, 
                    docentePlaceholder, 
                    aulaService, 
                    actividadService, 
                    temaService, 
                    ejercicioService
                );

                // C) Controlador Estudiante (Este lleva menos cosas)
                new DashboardEstudianteController(
                    mainFrame, 
                    estudianteView, 
                    estudiantePlaceholder, 
                    aulaService, 
                    actividadService,
                    ejercicioService // <--- AGREGAMOS ESTE ARGUMENTO AL FINAL
                );

                // 7. Arrancar
                mainFrame.showCard("LOGIN");
                mainFrame.setVisible(true);
            }
        });
    }
}
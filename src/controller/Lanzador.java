package controller;

import javax.swing.SwingUtilities;
import modelo.Usuario;
import vista.DashboardDocenteView;
import vista.DashboardEstudianteView;
import vista.LoginView;
import vista.MainFrame;
import database.dbConnection;

public class Lanzador {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                if (!util.NetworkUtils.isConnected()) {
                    javax.swing.JOptionPane.showMessageDialog(null,
                            "No hay conexión a internet. Por favor, verifica tu conexión.",
                            "Error de Conexión",
                            javax.swing.JOptionPane.ERROR_MESSAGE);
                    // Stop execution if no internet
                    return;
                }

                dbConnection db = new dbConnection();
                if (db.getConnection() == null) {
                    javax.swing.JOptionPane.showMessageDialog(null,
                            "No se pudo conectar a la base de datos.",
                            "Error de Base de Datos",
                            javax.swing.JOptionPane.ERROR_MESSAGE);
                    return;
                }

                // 1. Crear los servicios ("Base de Datos")
                // AudioService.getInstance().iniciarMusica(); // SE INICIA EN EL LOGIN
                UsuarioService usuarioService = new UsuarioService(db);
                AulaService aulaService = new AulaService(db);
                ActividadService actividadService = new ActividadService(db);
                TemaService temaService = new TemaService(db);
                EjercicioService ejercicioService = new EjercicioService(db);
                NotaService notaService = new NotaService(db);

                // Asignar ceros a actividades vencidas
                notaService.asignarCerosAActividadesVencidas();

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
                        ejercicioService, db);

                // B) Controlador Estudiante
                DashboardEstudianteController estController = new DashboardEstudianteController(
                        mainFrame,
                        estudianteView,
                        estudiantePlaceholder,
                        aulaService,
                        actividadService,
                        ejercicioService, db, usuarioService);

                // C) Controlador de Login (Ahora recibe los otros dos para poder avisarles)
                new LoginController(
                        mainFrame,
                        loginView,
                        usuarioService,
                        docController,
                        estController);

                // 7. Arrancar
                mainFrame.showCard("LOGIN");
                mainFrame.setVisible(true);
            }
        });
    }
}
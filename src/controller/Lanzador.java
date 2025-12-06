package controller;

import javax.swing.SwingUtilities;
import util.NetworkUtils;
import vista.DashboardDocenteView;
import vista.DashboardEstudianteView;
import vista.LoginView;
import vista.MainFrame;
import database.dbConnection;

public class Lanzador {
    public static void main(String[] args) {
        System.setProperty("apple.awt.application.name", "MathPath");

        try {
            java.net.URL logoUrl = Lanzador.class.getResource("/img/mathpath-logo.png");
            if (logoUrl != null) {
                java.awt.Image image = java.awt.Toolkit.getDefaultToolkit().getImage(logoUrl);

                if (java.awt.Taskbar.isTaskbarSupported()) {
                    java.awt.Taskbar taskbar = java.awt.Taskbar.getTaskbar();
                    if (taskbar.isSupported(java.awt.Taskbar.Feature.ICON_IMAGE)) {
                        taskbar.setIconImage(image);
                    }
                }
            }
        } catch (Exception e) {
            System.err.println("No se pudo establecer el icono en el Dock de macOS: " + e.getMessage());
        }

        // 1. Mostrar Splash Screen inmediatamente en el EDT
        SwingUtilities.invokeLater(() -> {
            vista.SplashFrame splash = new vista.SplashFrame();
            splash.setVisible(true);

            // 2. Iniciar carga en segundo plano
            new Thread(() -> {
                try {
                    splash.setStatus("Verificando conexión...");
                    if (!NetworkUtils.isConnected()) {
                        SwingUtilities.invokeLater(() -> {
                            splash.dispose();
                            javax.swing.JOptionPane.showMessageDialog(null,
                                    "No hay conexión a internet. Por favor, verifica tu conexión.",
                                    "Error de Conexión",
                                    javax.swing.JOptionPane.ERROR_MESSAGE);
                        });
                        return;
                    }

                    splash.setStatus("Conectando a base de datos...");
                    dbConnection db = new dbConnection(false);
                    if (db.getConnection() == null) {
                        SwingUtilities.invokeLater(() -> {
                            splash.dispose();
                            javax.swing.JOptionPane.showMessageDialog(null,
                                    "No se pudo conectar a la base de datos.",
                                    "Error de Base de Datos",
                                    javax.swing.JOptionPane.ERROR_MESSAGE);
                        });
                        return;
                    }

                    splash.setStatus("Iniciando servicios...");
                    UsuarioService usuarioService = new UsuarioService(db);
                    AulaService aulaService = new AulaService(db);
                    ActividadService actividadService = new ActividadService(db);
                    TemaService temaService = new TemaService(db);
                    EjercicioService ejercicioService = new EjercicioService(db);
                    NotaService notaService = new NotaService(db);

                    splash.setStatus("Procesando datos...");
                    // Asignar ceros a actividades vencidas
                    notaService.asignarCerosAActividadesVencidas();

                    splash.setStatus("Preparando interfaz...");

                    // 3. Finalizar en el EDT
                    SwingUtilities.invokeLater(() -> {
                        MainFrame mainFrame = new MainFrame();

                        // Crear las VISTAS
                        LoginView loginView = new LoginView();
                        DashboardDocenteView docenteView = new DashboardDocenteView(null);
                        DashboardEstudianteView estudianteView = new DashboardEstudianteView(null);

                        // Añadir tarjetas al MainFrame
                        mainFrame.addCard(loginView, "LOGIN");
                        mainFrame.addCard(docenteView, "DOCENTE");
                        mainFrame.addCard(estudianteView, "ESTUDIANTE");

                        // INICIAR CONTROLADORES
                        DashboardDocenteController docController = new DashboardDocenteController(
                                mainFrame,
                                docenteView,
                                null,
                                aulaService,
                                actividadService,
                                temaService,
                                ejercicioService, db);

                        DashboardEstudianteController estController = new DashboardEstudianteController(
                                mainFrame,
                                estudianteView,
                                null,
                                aulaService,
                                actividadService,
                                ejercicioService, db, usuarioService);

                        new LoginController(
                                mainFrame,
                                loginView,
                                usuarioService,
                                docController,
                                estController);

                        // Arrancar
                        mainFrame.showCard("LOGIN");
                        mainFrame.setVisible(true);

                        // Cerrar Splash
                        splash.dispose();
                    });

                } catch (Exception e) {
                    e.printStackTrace();
                    SwingUtilities.invokeLater(() -> {
                        splash.dispose();
                        javax.swing.JOptionPane.showMessageDialog(null,
                                "Ocurrió un error al iniciar la aplicación:\n" + e.getMessage(),
                                "Error Fatal",
                                javax.swing.JOptionPane.ERROR_MESSAGE);
                    });
                }
            }).start();
        });
    }
}
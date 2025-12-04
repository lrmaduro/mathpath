package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;
import modelo.Rol;
import modelo.Usuario;
import util.NetworkUtils;
import vista.LoginView;
import vista.MainFrame;
import vista.RegistroDialog;

public class LoginController {

    private MainFrame mainFrame;
    private LoginView view;
    private UsuarioService usuarioService;

    // --- 1. NUEVAS REFERENCIAS A LOS OTROS CONTROLADORES ---
    private DashboardDocenteController docenteController;
    private DashboardEstudianteController estudianteController;

    // --- 2. CONSTRUCTOR ACTUALIZADO ---
    public LoginController(MainFrame mainFrame, LoginView view, UsuarioService usuarioService,
            DashboardDocenteController docenteController,
            DashboardEstudianteController estudianteController) {

        this.mainFrame = mainFrame;
        this.view = view;
        this.usuarioService = usuarioService;

        // Guardamos las referencias para usarlas luego
        this.docenteController = docenteController;
        this.estudianteController = estudianteController;

        inicializarControlador();
    }

    private void inicializarControlador() {
        // 1. Listener para el botón INGRESAR
        view.addLoginListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                validarUsuario();
            }
        });

        // 2. Listener para el botón REGISTRARSE
        view.addRegistroListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                abrirRegistro();
            }
        });
    }

    private void validarUsuario() {
        if (!NetworkUtils.isConnected()) {
            view.mostrarError("No hay conexión a internet. No se puede iniciar sesión.");
            return;
        }

        String user = view.getUsuario();
        String pass = view.getPassword();

        Usuario usuarioValidado = usuarioService.validarLogin(user, pass);

        if (usuarioValidado != null) {
            // Login exitoso
            AudioService.getInstance().iniciarMusica();

            if (usuarioValidado.getRol() == Rol.DOCENTE) {
                // --- 3. ¡AQUÍ ESTÁ LA MAGIA! ---
                // Actualizamos el dashboard del docente con el usuario que acaba de entrar
                if (docenteController != null) {
                    docenteController.setUsuarioAutenticado(usuarioValidado);
                }

                mainFrame.showCard("DOCENTE");

            } else if (usuarioValidado.getRol() == Rol.ESTUDIANTE) {

                // Hacemos lo mismo para el estudiante
                if (estudianteController != null) {
                    estudianteController.setUsuarioAutenticado(usuarioValidado);
                }

                mainFrame.showCard("ESTUDIANTE");
            }

        } else {
            view.mostrarError("Usuario o contraseña incorrectos.");
        }
    }

    // Método que abre la ventana de registro
    private void abrirRegistro() {
        RegistroDialog dialog = new RegistroDialog(mainFrame);
        dialog.setVisible(true);

        Usuario nuevo = dialog.getNuevoUsuario();

        if (nuevo != null) {
            boolean registrado = usuarioService.registrarUsuario(nuevo);

            if (registrado) {
                JOptionPane.showMessageDialog(mainFrame,
                        "¡Cuenta creada con éxito!\nIngresa con tus nuevas credenciales.",
                        "Registro Exitoso",
                        JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(mainFrame,
                        "El nombre de usuario '" + nuevo.getUsuario() + "' ya existe.",
                        "Error de Registro",
                        JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}
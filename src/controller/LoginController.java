/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import modelo.Rol;
import modelo.Usuario;
import vista.LoginView;
import vista.MainFrame; // Importante: conoce al MainFrame

public class LoginController {

    private MainFrame mainFrame; // El contenedor principal
    private LoginView view; // El panel de login (JPanel)
    private UsuarioService usuarioService; // El servicio de validación

    /**
     * Constructor del Controlador de Login
     * @param mainFrame La ventana principal que maneja los CardLayout
     * @param view El panel de login (la vista que controla)
     * @param usuarioService El servicio que valida los usuarios (la "BD" falsa)
     */
    public LoginController(MainFrame mainFrame, LoginView view, UsuarioService usuarioService) {
        // Guardamos las referencias
        this.mainFrame = mainFrame;
        this.view = view;
        this.usuarioService = usuarioService;

        // Conectamos la VISTA con la LÓGICA
        // Añadimos un ActionListener al botón de la vista
        this.view.addLoginListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Cuando se haga clic, se ejecutará este método
                validarUsuario();
            }
        });
        
        // Este controlador ya no usa 'setVisible(true)',
        // de eso se encarga el Lanzador.
    }

    /**
     * Lógica principal del controlador
     */
    private void validarUsuario() {
        // 1. Obtenemos los datos de la VISTA
        String user = view.getUsuario();
        String pass = view.getPassword();

        // 2. Validamos los datos usando el SERVICIO
        Usuario usuarioValidado = usuarioService.validarLogin(user, pass);

        // 3. Tomamos una decisión
        if (usuarioValidado != null) {
            // ¡Login exitoso!
            
            // 4. Le pedimos al MainFrame que cambie de panel
            // Ya NO usamos view.dispose()
            
            if (usuarioValidado.getRol() == Rol.DOCENTE) {
                // Le pedimos al MainFrame que muestre la tarjeta "DOCENTE"
                mainFrame.showCard("DOCENTE");
                
            } else if (usuarioValidado.getRol() == Rol.ESTUDIANTE) {
                // Le pedimos al MainFrame que muestre la tarjeta "ESTUDIANTE"
                mainFrame.showCard("ESTUDIANTE");
            }
            
        } else {
            // Error en el login
            // Le pedimos a la VISTA que muestre un error
            view.mostrarError("Usuario o contraseña incorrectos.");
        }
    }
}

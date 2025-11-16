/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import javax.swing.SwingUtilities;
import modelo.Usuario;
import vista.DashboardDocenteView;
import vista.DashboardEstudianteView;
import vista.LoginView;
import vista.MainFrame; // <- Importamos el MainFrame

public class Lanzador {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                // 1. Crear el servicio (nuestra "BD" falsa)
                UsuarioService usuarioService = new UsuarioService();

                // 2. Crear la ventana principal (el contenedor)
                MainFrame mainFrame = new MainFrame();

                // 3. Crear las VISTAS (que ahora son JPanels)
                LoginView loginView = new LoginView();
                
                // --- Vistas de "placeholder" ---
                // Nota: Usamos usuarios falsos por ahora para poder crear las vistas.
                // Una mejor forma es crear las vistas "en el momento" en el LoginController,
                // pero esto es más simple para empezar.
                Usuario docentePlaceholder = usuarioService.validarLogin("profe", "123");
                Usuario estudiantePlaceholder = usuarioService.validarLogin("pepito", "456");
                
                DashboardDocenteView docenteView = new DashboardDocenteView(docentePlaceholder);
                DashboardEstudianteView estudianteView = new DashboardEstudianteView(estudiantePlaceholder);

                // 4. Añadir las vistas como "tarjetas" al MainFrame
                mainFrame.addCard(loginView, "LOGIN");
                mainFrame.addCard(docenteView, "DOCENTE");
                mainFrame.addCard(estudianteView, "ESTUDIANTE");

                // 5. Crear los CONTROLADORES y pasarles lo que necesitan
                // El LoginController necesita el MainFrame para poder cambiar de tarjeta
                LoginController loginController = new LoginController(mainFrame, loginView, usuarioService);
                new DashboardDocenteController(mainFrame, docenteView, docentePlaceholder);
                
                // TODO: Crear los otros controladores
                // new DashboardDocenteController(mainFrame, docenteView, ...);

                // 6. Mostrar la primera tarjeta
                mainFrame.showCard("LOGIN");

                // 7. Hacer visible la ventana principal
                mainFrame.setVisible(true);
            }
        });
    }
}

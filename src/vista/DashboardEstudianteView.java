/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package vista;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel; // <- Importamos JPanel
import modelo.Usuario;

public class DashboardEstudianteView extends JPanel { // <- CAMBIO IMPORTANTE

    public DashboardEstudianteView(Usuario estudiante) {
        // Quitamos setTitle, setSize, etc.
        
        // Mostramos un mensaje de bienvenida
        add(new JLabel("¡Hola Estudiante: " + estudiante.getNombre() + "! (Panel)"));
    }
}

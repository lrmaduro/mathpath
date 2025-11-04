/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package coil.prototipo;

import GUI.MainFrame;
import java.awt.EventQueue;
/**
 *
 * @author Luis
 */
public class COILPrototipo {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                // 3. CREA Y MUESTRA TU VENTANA PRINCIPAL
                MainFrame framePrincipal = new MainFrame();
                framePrincipal.setVisible(true);
            }
        });

    }
    
}

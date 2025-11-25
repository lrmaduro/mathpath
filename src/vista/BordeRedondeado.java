/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package vista;

/**
 *
 * @author luisr
 */
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Insets;
import javax.swing.border.Border;

public class BordeRedondeado implements Border {
        private int radio;
        
        BordeRedondeado(int radio) {
            this.radio = radio;
        }
        
        public Insets getBorderInsets(Component c) {
            return new Insets(this.radio, this.radio, this.radio, this.radio);
        }

        public boolean isBorderOpaque() {
            return false;
        }

        public void paintBorder(Component c, Graphics g, int x, int y, int ancho, int alto) {
            g.drawRoundRect(x,y,ancho-1,alto-1,radio,radio);
        }
}

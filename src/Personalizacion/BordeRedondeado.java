package Personalizacion;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.RenderingHints;
import javax.swing.border.Border;


public class BordeRedondeado implements Border {

    private int radio;
    private Color colorBorde;
    private Color colorFondo;

    /**
     * @param radio El radio de las esquinas
     * @param colorFondo El color de Relleno del botón
     * @param colorBorde El color de la Línea del borde
     */
    public BordeRedondeado(int radio, Color colorFondo, Color colorBorde) {
        this.radio = radio;
        this.colorFondo = colorFondo;
        this.colorBorde = colorBorde;
    }

    @Override
    public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
        Graphics2D g2 = (Graphics2D) g.create();
        
        // Habilita el Antialiasing para bordes suaves
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // --- Dibuja el Relleno ---
        g2.setColor(colorFondo);
        // Rellena el rectángulo redondeado
        // (x, y, width-1, height-1) para que el borde se dibuje justo en el límite
        g2.fillRoundRect(x, y, width - 1, height - 1, radio, radio);

        // --- Dibuja la Línea del Borde ---
        g2.setColor(colorBorde);
        // Dibuja el contorno del rectángulo redondeado
        g2.drawRoundRect(x, y, width - 1, height - 1, radio, radio);

        g2.dispose();
    }

    @Override
    public Insets getBorderInsets(Component c) {
        // Devuelve el "relleno" (padding) que el borde añade
        // Esto evita que el texto del botón se pegue a los bordes
        int padding = radio / 2;
        return new Insets(padding, padding, padding, padding);
    }

    @Override
    public boolean isBorderOpaque() {
        // Si el borde rellena su área (en nuestro caso, sí)
        return true;
    }
}
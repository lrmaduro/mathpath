/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package vista;

/**
 *
 * @author luisr
 */
import javax.swing.JPanel;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.RoundRectangle2D;
import java.awt.Shape;

public class PanelRedondo extends JPanel{
    
    private int cornerRadius;

    public PanelRedondo(int radius) {
        this.cornerRadius = radius;
        // Key step: Make the panel transparent so only the painted shape is visible
        setOpaque(false);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g.create();
        // Enable anti-aliasing for smooth corners
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Define the rounded rectangle shape
        Shape roundedRect = new RoundRectangle2D.Double(0, 0, getWidth() - 1, getHeight() - 1, cornerRadius, cornerRadius);

        // Fill the rounded shape with the panel's background color
        g2d.setColor(getBackground());
        g2d.fill(roundedRect);

        g2d.dispose();
    }

    @Override
    // Optionally, paint the border within paintComponent for better control over alignment
    protected void paintBorder(Graphics g) {
        super.paintBorder(g); // This might draw a default border
        // To use a custom drawn border:
        Graphics2D g2d = (Graphics2D) g.create();
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setColor(getForeground()); // Use foreground color for border, for example
        g2d.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, cornerRadius, cornerRadius);
        g2d.dispose();
    }
}


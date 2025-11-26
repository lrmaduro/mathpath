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
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class PanelImagen extends JPanel {
    private Image backgroundImage;

    // Constructor to load the image
    public PanelImagen(String fileName) {
        try {
            backgroundImage = ImageIO.read(new File(fileName));
        } catch (IOException e) {
            System.err.println("Failed to load image: " + fileName);
            e.printStackTrace();
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g); // Clears the panel for redrawing
        if (backgroundImage != null) {
            // Draw the image scaled to the panel's current size
            g.drawImage(backgroundImage, 0, 0, this.getWidth(), this.getHeight(), null);
        }
    }
}

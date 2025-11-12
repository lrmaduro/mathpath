package Personalizacion;

// Importa las clases necesarias
import java.awt.Graphics;
import java.awt.Image;
import javax.swing.ImageIcon;
import javax.swing.JPanel;

/**
 * Un JPanel personalizado que puede mostrar una imagen de fondo.
 * La imagen se escala automáticamente para llenar todo el panel.
 */
public class PanelConFondo extends JPanel {

    private Image imagenFondo;

    /**
     * Constructor. Recibe la ruta de la imagen que se usará como fondo.
     * La ruta debe ser relativa al "classpath" (p.ej., "/recursos/mi_fondo.png").
     */
    public PanelConFondo(String rutaImagen) {
        try {
            // Cargar la imagen
            // Usamos getClass().getResource() para que funcione dentro del JAR
            ImageIcon icono = new ImageIcon(getClass().getResource(rutaImagen));
            this.imagenFondo = icono.getImage();
            
        } catch (Exception e) {
            System.err.println("Error al cargar la imagen de fondo: " + rutaImagen);
            // Opcional: poner un fondo de color si la imagen falla
            // setBackground(Color.RED); 
        }
    }

    /**
     * Este es el método clave que se sobreescribe.
     * Se llama automáticamente cada vez que el panel necesita pintarse.
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g); // Primero pinta el fondo normal del panel

        // Si la imagen se cargó correctamente...
        if (this.imagenFondo != null) {
            // Dibuja la imagen, escalándola para que ocupe TODO el panel
            g.drawImage(
                this.imagenFondo, 
                0, // Posición X
                0, // Posición Y
                getWidth(), // Ancho (el ancho actual del panel)
                getHeight(), // Alto (el alto actual del panel)
                this // El "observador" (siempre 'this' aquí)
            );
        }
    }
}

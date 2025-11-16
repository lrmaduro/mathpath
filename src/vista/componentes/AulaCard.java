package vista.componentes;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import modelo.Aula;

public class AulaCard extends JPanel {
    
    private Aula aula;

    public AulaCard(Aula aula) {
        this.aula = aula;
        
        // Configuración de la tarjeta
        this.setLayout(new BorderLayout(10, 10));
        this.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.GRAY, 1), // Borde exterior
                new EmptyBorder(15, 15, 15, 15) // Margen interior
        ));
        this.setBackground(Color.WHITE);
        this.setPreferredSize(new Dimension(250, 150)); // Tamaño de la tarjeta

        // 1. Título (Nombre del aula)
        JLabel lblNombre = new JLabel(aula.getNombre());
        lblNombre.setFont(lblNombre.getFont().deriveFont(18.0f)); // Fuente más grande
        
        // 2. Descripción
        JLabel lblDescripcion = new JLabel("<html>" + aula.getDescripcion() + "</html>");
        
        // 3. Código
        JLabel lblCodigo = new JLabel("Código: " + aula.getCodigo());
        
        // 4. Botón "Ver"
        JButton btnVer = new JButton("Ver Aula");

        // Añadimos los componentes al panel
        this.add(lblNombre, BorderLayout.NORTH);
        this.add(lblDescripcion, BorderLayout.CENTER);
        
        // Panel inferior para código y botón
        JPanel panelInferior = new JPanel(new BorderLayout());
        panelInferior.setOpaque(false); // Transparente
        panelInferior.add(lblCodigo, BorderLayout.WEST);
        panelInferior.add(btnVer, BorderLayout.EAST);
        
        this.add(panelInferior, BorderLayout.SOUTH);
    }
}
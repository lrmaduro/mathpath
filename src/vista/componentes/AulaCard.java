package vista.componentes;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.util.Random;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.formdev.flatlaf.FlatClientProperties;

import modelo.Aula;

public class AulaCard extends JPanel {

    private Aula aula;
    private JButton btnVer;

    // Paleta de colores aleatorios para la franja superior
    private static final Color[] COLORES_PASTEL = {
            new Color(174, 214, 241), // Azul Pastel
            new Color(169, 223, 191), // Verde Menta
            new Color(249, 231, 159), // Amarillo Suave
            new Color(245, 183, 177), // Rojo/Rosa Pastel
            new Color(210, 180, 222), // Lavanda
            new Color(250, 215, 160) // Melocotón
    };

    public AulaCard(Aula aula) {
        this.aula = aula;

        // --- CONFIGURACIÓN DEL WRAPPER (EL PANEL PRINCIPAL) ---
        // Usamos GridBagLayout para centrar la tarjeta "real" dentro de la celda del
        // GridLayout
        this.setLayout(new java.awt.GridBagLayout());
        this.setOpaque(false); // Transparente para que se vea el fondo del dashboard

        // --- TARJETA REAL (CONTENIDO) ---
        JPanel cardContent = new JPanel(new BorderLayout(0, 0));
        cardContent.setBackground(Color.WHITE);
        // FIJAMOS EL TAMAÑO AQUÍ:
        cardContent.setPreferredSize(new Dimension(260, 170));
        cardContent.setMinimumSize(new Dimension(260, 170));
        cardContent.setMaximumSize(new Dimension(260, 170));

        // BORDE ESTÉTICO:
        cardContent.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createEmptyBorder(0, 0, 0, 0), // Sin margen externo extra aquí
                BorderFactory.createMatteBorder(1, 1, 3, 1, new Color(230, 230, 230))));

        // --- 1. FRANJA DE COLOR SUPERIOR ---
        JPanel panelColor = new JPanel();
        panelColor.setPreferredSize(new Dimension(0, 8)); // Franja delgada
        panelColor.setBackground(obtenerColorAleatorio());
        cardContent.add(panelColor, BorderLayout.NORTH);

        // --- 2. CONTENIDO CENTRAL ---
        JPanel panelCentro = new JPanel(new BorderLayout(5, 5));
        panelCentro.setBackground(Color.WHITE);
        panelCentro.setBorder(new EmptyBorder(15, 15, 5, 15)); // Padding interno

        // Título
        JLabel lblNombre = new JLabel(aula.getNombre());
        lblNombre.setFont(new Font("SansSerif", Font.BOLD, 16));
        lblNombre.setForeground(new Color(80, 80, 100)); // Gris oscuro azulado

        // Descripción (cortada si es muy larga)
        String descTexto = aula.getDescripcion();
        if (descTexto.length() > 50)
            descTexto = descTexto.substring(0, 47) + "...";

        JLabel lblDescripcion = new JLabel("<html><p style='width:180px; color:gray;'>" + descTexto + "</p></html>");
        lblDescripcion.setFont(new Font("SansSerif", Font.PLAIN, 12));
        lblDescripcion.setVerticalAlignment(JLabel.TOP);

        panelCentro.add(lblNombre, BorderLayout.NORTH);
        panelCentro.add(lblDescripcion, BorderLayout.CENTER);

        cardContent.add(panelCentro, BorderLayout.CENTER);

        // --- 3. PIE DE TARJETA (Código y Botón) ---
        JPanel panelInferior = new JPanel(new BorderLayout());
        panelInferior.setBackground(Color.WHITE);
        panelInferior.setBorder(new EmptyBorder(10, 15, 15, 15)); // Margen abajo

        // Código (estilo etiqueta)
        JLabel lblCodigo = new JLabel("CÓD: " + aula.getCodigo());
        lblCodigo.setFont(new Font("Monospaced", Font.BOLD, 11));
        lblCodigo.setForeground(new Color(150, 150, 150));
        lblCodigo.setOpaque(true);
        lblCodigo.setBackground(new Color(245, 245, 245));
        lblCodigo.setBorder(new EmptyBorder(3, 6, 3, 6)); // Un poco de relleno

        // Botón "Ver"
        btnVer = new JButton("Entrar");
        btnVer.putClientProperty(FlatClientProperties.STYLE, "arc: 10");
        btnVer.setFont(new Font("SansSerif", Font.BOLD, 12));
        btnVer.setForeground(Color.WHITE);
        btnVer.setBackground(new Color(100, 180, 240)); // Azul suave
        // btnVer.setBorder(new EmptyBorder(6, 15, 6, 15));
        btnVer.setFocusPainted(false);
        btnVer.setCursor(new Cursor(Cursor.HAND_CURSOR));

        // Hover simple para el botón
        btnVer.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnVer.setBackground(new Color(80, 160, 220));
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnVer.setBackground(new Color(100, 180, 240));
            }
        });

        panelInferior.add(lblCodigo, BorderLayout.WEST);
        panelInferior.add(btnVer, BorderLayout.EAST);

        cardContent.add(panelInferior, BorderLayout.SOUTH);

        // AÑADIR LA TARJETA AL WRAPPER (THIS)
        this.add(cardContent);
    }

    // Método auxiliar para elegir un color al azar
    private Color obtenerColorAleatorio() {
        Random rand = new Random();
        // Usamos el hash del nombre del aula o un random puro
        // Si usas random puro, cada vez que refresques cambiará de color (puede ser
        // divertido)
        return COLORES_PASTEL[rand.nextInt(COLORES_PASTEL.length)];
    }

    // --- MÉTODOS PÚBLICOS (Necesarios para el controlador) ---
    public void addVerAulaListener(ActionListener listener) {
        btnVer.addActionListener(listener);
    }

    public JButton getBtnVer() {
        return btnVer;
    }
}
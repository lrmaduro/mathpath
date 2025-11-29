package vista;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.border.EmptyBorder;

import controller.TemaService;
import modelo.Ejercicio;

public class VerEjercicioDialog extends JDialog {

    private final Color COLOR_HEADER = new Color(240, 242, 245);
    private final Color COLOR_TEXT_DARK = new Color(50, 60, 70);
    private final Color COLOR_BTN_CERRAR = new Color(149, 165, 166);
    private final Color COLOR_CORRECTO = new Color(46, 204, 113); // Verde para la respuesta correcta

    public VerEjercicioDialog(JFrame parent, Ejercicio ejercicio, TemaService temaService) {
        super(parent, "Detalle del Ejercicio", true);
        this.setSize(600, 650);
        this.setLayout(new BorderLayout());

        // --- 1. CABECERA ---
        JPanel panelHeader = new JPanel(new BorderLayout());
        panelHeader.setBackground(COLOR_HEADER);
        panelHeader.setBorder(new EmptyBorder(20, 20, 20, 20));

        JLabel lblTitulo = new JLabel("Detalle del Ejercicio");
        lblTitulo.setFont(new Font("SansSerif", Font.BOLD, 18));
        lblTitulo.setForeground(COLOR_TEXT_DARK);

        JLabel lblSub = new JLabel(
                "ID: " + ejercicio.getId() + " | Tema: " + temaService.getTemaNombre(ejercicio.getIdTema()));
        lblSub.setFont(new Font("SansSerif", Font.PLAIN, 12));
        lblSub.setForeground(Color.GRAY);

        panelHeader.add(lblTitulo, BorderLayout.NORTH);
        panelHeader.add(lblSub, BorderLayout.SOUTH);

        this.add(panelHeader, BorderLayout.NORTH);

        // --- 2. CONTENIDO ---
        JPanel panelContent = new JPanel(new GridBagLayout());
        panelContent.setBackground(Color.WHITE);
        panelContent.setBorder(new EmptyBorder(20, 30, 20, 30));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 5, 8, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        gbc.gridx = 0;
        gbc.gridy = 0;

        // Pregunta
        JLabel lblPregunta = new JLabel("<html><b>Pregunta:</b> " + ejercicio.getPregunta() + "</html>");
        lblPregunta.setFont(new Font("SansSerif", Font.PLAIN, 14));
        panelContent.add(lblPregunta, gbc);

        // Imagen (si existe)
        if (ejercicio.getImagen() != null) {
            gbc.gridy++;
            JLabel lblImg = new JLabel(ejercicio.getImagen());
            lblImg.setHorizontalAlignment(JLabel.CENTER);
            panelContent.add(lblImg, gbc);
        }

        // Opciones
        gbc.gridy++;
        gbc.insets = new Insets(20, 5, 5, 5);
        JLabel lblOpciones = new JLabel("Opciones:");
        lblOpciones.setFont(new Font("SansSerif", Font.BOLD, 13));
        panelContent.add(lblOpciones, gbc);

        gbc.insets = new Insets(5, 5, 5, 5);
        List<String> opciones = ejercicio.getOpciones();
        char letra = 'A';
        String respuestaCorrecta = ejercicio.getClaveRespuesta(); // "A", "B", etc.

        for (int i = 0; i < opciones.size(); i++) {
            gbc.gridy++;
            String letraStr = String.valueOf(letra);
            boolean esCorrecta = letraStr.equals(respuestaCorrecta);

            JLabel lblOp = new JLabel("<html><b>" + letra + ")</b> " + opciones.get(i) + "</html>");
            lblOp.setFont(new Font("SansSerif", Font.PLAIN, 13));
            lblOp.setBorder(new EmptyBorder(5, 10, 5, 10));
            lblOp.setOpaque(true);

            if (esCorrecta) {
                lblOp.setBackground(new Color(235, 250, 235)); // Fondo verde claro
                lblOp.setForeground(new Color(30, 100, 30));
                lblOp.setBorder(BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(COLOR_CORRECTO),
                        new EmptyBorder(5, 10, 5, 10)));
            } else {
                lblOp.setBackground(Color.WHITE);
            }

            panelContent.add(lblOp, gbc);
            letra++;
        }

        // Feedback
        gbc.gridy++;
        gbc.insets = new Insets(20, 5, 5, 5);
        JLabel lblRetro = new JLabel("Retroalimentación:");
        lblRetro.setFont(new Font("SansSerif", Font.BOLD, 13));
        panelContent.add(lblRetro, gbc);

        gbc.gridy++;
        gbc.insets = new Insets(5, 5, 5, 5);
        JTextArea txtRetro = new JTextArea(ejercicio.getRetroalimentacion());
        txtRetro.setFont(new Font("SansSerif", Font.PLAIN, 13));
        txtRetro.setLineWrap(true);
        txtRetro.setWrapStyleWord(true);
        txtRetro.setEditable(false);
        txtRetro.setBackground(new Color(250, 250, 250));
        txtRetro.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.LIGHT_GRAY),
                new EmptyBorder(8, 8, 8, 8)));
        panelContent.add(txtRetro, gbc);

        // Scroll por si es muy largo
        JScrollPane scroll = new JScrollPane(panelContent);
        scroll.setBorder(null);
        this.add(scroll, BorderLayout.CENTER);

        // --- 3. BOTONES ---
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 15));
        panelBotones.setBackground(COLOR_HEADER);

        JButton btnCerrar = new JButton("Cerrar");
        btnCerrar.setBackground(COLOR_BTN_CERRAR);
        btnCerrar.setForeground(Color.WHITE);
        btnCerrar.setFont(new Font("SansSerif", Font.BOLD, 12));
        btnCerrar.setFocusPainted(false);
        btnCerrar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnCerrar.addActionListener(e -> dispose());

        panelBotones.add(btnCerrar);
        this.add(panelBotones, BorderLayout.SOUTH);

        this.setLocationRelativeTo(parent);
    }
}

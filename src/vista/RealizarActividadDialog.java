package vista;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon; // Para las imágenes reales
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import modelo.Actividad;
import modelo.Ejercicio;

public class RealizarActividadDialog extends JDialog {

    // Datos Lógicos
    private List<Ejercicio> ejercicios;
    private int indiceActual = 0;
    private int aciertos = 0;
    private boolean finalizado = false;
    private double notaFinal = 0;

    // Componentes UI
    private JLabel lblProgreso;
    private JLabel lblPregunta;
    private JPanel panelOpciones;
    private ButtonGroup grupoOpciones;
    private JButton btnAccion; 
    
    // --- NUEVOS COMPONENTES VISUALES ---
    private JLabel lblMascota;       // Aquí irá la imagen PNG
    private JPanel panelGloboTexto;  // El "bocadillo" de cómic
    private JLabel lblFeedbackTexto; // El texto dentro del globo
    
    // Estado interno
    private boolean esperandoSiguiente = false;

    // --- PALETA "CANDY STUDENT" ---
    private final Color COLOR_FONDO = new Color(232, 248, 245); // Menta suave
    private final Color COLOR_CARD_PREGUNTA = Color.WHITE;
    private final Color COLOR_BTN_ACCION = new Color(245, 183, 177); // Coral
    private final Color COLOR_TEXTO = new Color(50, 60, 80);
    
    // Colores de Feedback
    private final Color COLOR_GLOBO_NEUTRO = new Color(255, 255, 255);
    private final Color COLOR_GLOBO_BIEN = new Color(213, 245, 227); // Verde menta fuerte
    private final Color COLOR_GLOBO_MAL = new Color(250, 219, 216);  // Rojo suave

    public RealizarActividadDialog(JFrame parent, Actividad actividad, List<Ejercicio> ejercicios) {
        super(parent, "Resolviendo: " + actividad.getNombre(), true);
        this.ejercicios = ejercicios;
        
        setSize(800, 550); // Más ancho para acomodar a la mascota
        setLocationRelativeTo(parent);
        setLayout(new BorderLayout());
        getContentPane().setBackground(COLOR_FONDO);

        // --- 1. HEADER (Barra de Progreso) ---
        JPanel header = new JPanel(new FlowLayout(FlowLayout.LEFT, 20, 15));
        header.setOpaque(false);
        
        lblProgreso = new JLabel("Ejercicio 1 de " + ejercicios.size());
        lblProgreso.setFont(new Font("SansSerif", Font.BOLD, 14));
        lblProgreso.setForeground(new Color(118, 215, 196)); // Verde bonito
        
        // Decoración visual (una barrita simple)
        JPanel barraDecorativa = new JPanel();
        barraDecorativa.setPreferredSize(new Dimension(100, 5));
        barraDecorativa.setBackground(new Color(118, 215, 196));
        
        header.add(lblProgreso);
        header.add(barraDecorativa);
        add(header, BorderLayout.NORTH);
        
        // --- 2. CENTRO (Split: Pregunta Izq | Mascota Der) ---
        JPanel panelCentral = new JPanel(new GridLayout(1, 2, 20, 0)); // 2 columnas
        panelCentral.setOpaque(false);
        panelCentral.setBorder(new EmptyBorder(10, 30, 10, 30));
        
        // >>> COLUMNA IZQUIERDA: La Pregunta <<<
        JPanel cardPregunta = new JPanel();
        cardPregunta.setLayout(new BoxLayout(cardPregunta, BoxLayout.Y_AXIS));
        cardPregunta.setBackground(COLOR_CARD_PREGUNTA);
        cardPregunta.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(220, 220, 220), 1),
            new EmptyBorder(30, 30, 30, 30)
        ));
        
        lblPregunta = new JLabel("Cargando...");
        lblPregunta.setFont(new Font("SansSerif", Font.BOLD, 20));
        lblPregunta.setForeground(COLOR_TEXTO);
        lblPregunta.setAlignmentX(LEFT_ALIGNMENT);
        
        panelOpciones = new JPanel();
        panelOpciones.setLayout(new BoxLayout(panelOpciones, BoxLayout.Y_AXIS));
        panelOpciones.setOpaque(false);
        panelOpciones.setAlignmentX(LEFT_ALIGNMENT);
        
        cardPregunta.add(lblPregunta);
        cardPregunta.add(Box.createVerticalStrut(20)); // Espacio
        cardPregunta.add(panelOpciones);
        cardPregunta.add(Box.createVerticalGlue()); // Empujar contenido arriba
        
        // >>> COLUMNA DERECHA: La Mascota y el Feedback <<<
        JPanel panelMascotaContainer = new JPanel(new BorderLayout());
        panelMascotaContainer.setOpaque(false);
        
        // A. El Globo de Texto (Arriba)
        panelGloboTexto = new JPanel(new BorderLayout());
        panelGloboTexto.setBackground(COLOR_GLOBO_NEUTRO);
        panelGloboTexto.setBorder(new EmptyBorder(15, 15, 15, 15));
        // Efecto redondeado simple (hack visual con borde blanco)
        panelGloboTexto.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(200,200,200), 1, true), // Borde redondeado
            new EmptyBorder(10, 10, 10, 10)
        ));
        
        lblFeedbackTexto = new JLabel("<html>¡Hola! Selecciona la respuesta correcta para avanzar.</html>");
        lblFeedbackTexto.setFont(new Font("SansSerif", Font.ITALIC, 14));
        lblFeedbackTexto.setForeground(Color.GRAY);
        panelGloboTexto.add(lblFeedbackTexto, BorderLayout.CENTER);
        
        // B. La Imagen de la Mascota (Centro/Abajo)
        lblMascota = new JLabel();
        lblMascota.setHorizontalAlignment(SwingConstants.CENTER);
        lblMascota.setVerticalAlignment(SwingConstants.BOTTOM);
        // Placeholder inicial (Texto hasta que pongas la imagen)
        lblMascota.setText("<html><h1 style='color:#A569BD'>[Mascota Neutra]</h1></html>");
        // lblMascota.setIcon(new ImageIcon("ruta/mascota_pensando.png")); // <--- AQUÍ CARGARÁS TU IMAGEN
        
        panelMascotaContainer.add(panelGloboTexto, BorderLayout.NORTH);
        panelMascotaContainer.add(lblMascota, BorderLayout.CENTER);
        
        // Añadir columnas al centro
        panelCentral.add(cardPregunta);
        panelCentral.add(panelMascotaContainer);
        
        add(panelCentral, BorderLayout.CENTER);
        
        // --- 3. FOOTER (Botón de Acción) ---
        JPanel footer = new JPanel(new FlowLayout(FlowLayout.CENTER));
        footer.setOpaque(false);
        footer.setBorder(new EmptyBorder(20, 0, 20, 0));
        
        btnAccion = new JButton("¡Comprobar!");
        estilarBoton(btnAccion);
        btnAccion.addActionListener(e -> procesarAccion());
        
        footer.add(btnAccion);
        add(footer, BorderLayout.SOUTH);
        
        // Inicializar
        cargarPregunta();
    }

    private void cargarPregunta() {
        if (indiceActual >= ejercicios.size()) {
            mostrarResultados();
            return;
        }
        
        Ejercicio ej = ejercicios.get(indiceActual);
        
        // Resetear UI
        lblProgreso.setText("Pregunta " + (indiceActual + 1) + " de " + ejercicios.size());
        lblPregunta.setText("<html>" + ej.getPregunta() + "</html>"); 
        
        panelOpciones.removeAll();
        grupoOpciones = new ButtonGroup();
        
        char letra = 'A';
        for (String op : ej.getOpciones()) {
            JRadioButton rb = new JRadioButton(" " + letra + ") " + op);
            rb.setActionCommand(String.valueOf(letra));
            rb.setFont(new Font("SansSerif", Font.PLAIN, 16));
            rb.setForeground(COLOR_TEXTO);
            rb.setOpaque(false);
            rb.setFocusPainted(false);
            rb.setCursor(new Cursor(Cursor.HAND_CURSOR));
            
            grupoOpciones.add(rb);
            panelOpciones.add(rb);
            panelOpciones.add(Box.createVerticalStrut(15)); // Espacio entre opciones
            letra++;
        }
        
        // RESETEAR ESTADO MASCOTA (Modo Pensando)
        actualizarMascota(EstadoMascota.PENSANDO, "<html>Mmm... ¿Cuál crees que es la respuesta correcta?</html>");
        
        btnAccion.setText("¡Comprobar!");
        btnAccion.setBackground(COLOR_BTN_ACCION);
        esperandoSiguiente = false;
        
        revalidate();
        repaint();
    }
    
    private void procesarAccion() {
        if (esperandoSiguiente) {
            indiceActual++;
            cargarPregunta();
        } else {
            verificarRespuesta();
        }
    }
    
    private void verificarRespuesta() {
        if (grupoOpciones.getSelection() == null) {
            JOptionPane.showMessageDialog(this, "¡Debes elegir una opción!");
            return;
        }
        
        Ejercicio ej = ejercicios.get(indiceActual);
        String seleccion = grupoOpciones.getSelection().getActionCommand();
        boolean esCorrecta = seleccion.equals(ej.getClaveRespuesta());
        
        if (esCorrecta) {
            aciertos++;
            // 

//

            actualizarMascota(EstadoMascota.FELIZ, "<html><b style='color:green'>¡SÍÍÍ! ¡Correcto!</b><br>¡Eres increíble!</html>");
        } else {
            String feedback = ej.getRetroalimentacion();
            if (feedback == null || feedback.isEmpty()) feedback = "Inténtalo mejor la próxima vez.";
            // 
            actualizarMascota(EstadoMascota.TRISTE, "<html><b style='color:red'>Oh no... era la " + ej.getClaveRespuesta() + "</b><br>" + feedback + "</html>");
        }
        
        // Cambiar botón
        btnAccion.setText(indiceActual == ejercicios.size() - 1 ? "Ver Resultados" : "Siguiente Pregunta ->");
        btnAccion.setBackground(new Color(100, 100, 100)); // Gris para siguiente
        esperandoSiguiente = true;
    }
    
    // --- LÓGICA DE LA MASCOTA ---
    private enum EstadoMascota { PENSANDO, FELIZ, TRISTE }
    
    private void actualizarMascota(EstadoMascota estado, String mensaje) {
        lblFeedbackTexto.setText(mensaje);
        
        // Cambiar Colores del Globo
        switch (estado) {
            case PENSANDO:
                panelGloboTexto.setBackground(COLOR_GLOBO_NEUTRO);
                // lblMascota.setIcon(new ImageIcon("src/img/mascota_pensando.png")); 
                lblMascota.setText("<html><h1 style='color:gray'>[Mascota Pensando]</h1></html>");
                break;
            case FELIZ:
                panelGloboTexto.setBackground(COLOR_GLOBO_BIEN);
                // lblMascota.setIcon(new ImageIcon("src/img/mascota_feliz.png")); 
                lblMascota.setText("<html><h1 style='color:green'>[Mascota Feliz]</h1></html>");
                break;
            case TRISTE:
                panelGloboTexto.setBackground(COLOR_GLOBO_MAL);
                // lblMascota.setIcon(new ImageIcon("src/img/mascota_triste.png")); 
                lblMascota.setText("<html><h1 style='color:red'>[Mascota Triste]</h1></html>");
                break;
        }
    }
    
    private void estilarBoton(JButton btn) {
        btn.setBackground(COLOR_BTN_ACCION);
        btn.setForeground(Color.WHITE);
        btn.setFont(new Font("SansSerif", Font.BOLD, 16));
        btn.setFocusPainted(false);
        btn.setBorder(BorderFactory.createCompoundBorder(
             BorderFactory.createLineBorder(new Color(230, 160, 160), 1),
             new EmptyBorder(10, 40, 10, 40)
        ));
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
    }
    
    private void mostrarResultados() {
        notaFinal = ((double) aciertos / ejercicios.size()) * 20;
        finalizado = true;
        // (Aquí podrías poner una imagen de la mascota celebrando con un trofeo)
        String mensaje = String.format("¡Terminaste!\nAciertos: %d/%d\nNota Final: %.1f", aciertos, ejercicios.size(), notaFinal);
        JOptionPane.showMessageDialog(this, mensaje, "¡Buen trabajo!", JOptionPane.INFORMATION_MESSAGE);
        dispose();
    }

    public boolean isFinalizado() { return finalizado; }
    public double getNotaFinal() { return notaFinal; }
}
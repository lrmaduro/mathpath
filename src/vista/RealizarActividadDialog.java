package vista;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.border.EmptyBorder;
import modelo.Actividad;
import modelo.Ejercicio;

public class RealizarActividadDialog extends JDialog {

    // Datos
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
    
    private JPanel panelFeedback;
    private JLabel lblMensajeFeedback;
    
    private JButton btnAccion; // Este botón cambiará entre "Comprobar" y "Siguiente"
    
    // Estado interno
    private boolean esperandoSiguiente = false; // Para saber qué hace el botón

    public RealizarActividadDialog(JFrame parent, Actividad actividad, List<Ejercicio> ejercicios) {
        super(parent, "Actividad: " + actividad.getNombre(), true);
        this.ejercicios = ejercicios;
        
        setSize(500, 400);
        setLocationRelativeTo(parent);
        setLayout(new BorderLayout());
        
        // 1. Barra Superior (Progreso)
        JPanel header = new JPanel(new FlowLayout(FlowLayout.LEFT));
        header.setBackground(new Color(240, 240, 240));
        lblProgreso = new JLabel("Pregunta 1 de " + ejercicios.size());
        lblProgreso.setFont(new Font("SansSerif", Font.BOLD, 12));
        lblProgreso.setForeground(Color.GRAY);
        header.add(lblProgreso);
        add(header, BorderLayout.NORTH);
        
        // 2. Panel Central (Pregunta y Opciones)
        JPanel centro = new JPanel();
        centro.setLayout(new BoxLayout(centro, BoxLayout.Y_AXIS));
        centro.setBorder(new EmptyBorder(20, 20, 20, 20));
        centro.setBackground(Color.WHITE);
        
        lblPregunta = new JLabel("Cargando pregunta...");
        lblPregunta.setFont(new Font("SansSerif", Font.BOLD, 18));
        lblPregunta.setAlignmentX(0); // Alinear a la izquierda
        centro.add(lblPregunta);
        centro.add(javax.swing.Box.createVerticalStrut(20));
        
        panelOpciones = new JPanel();
        panelOpciones.setLayout(new BoxLayout(panelOpciones, BoxLayout.Y_AXIS));
        panelOpciones.setOpaque(false);
        panelOpciones.setAlignmentX(0);
        centro.add(panelOpciones);
        
        // Panel de Feedback (Oculto al inicio)
        panelFeedback = new JPanel(new BorderLayout());
        panelFeedback.setBorder(new EmptyBorder(10, 10, 10, 10));
        panelFeedback.setVisible(false); // Se oculta
        panelFeedback.setAlignmentX(0);
        
        lblMensajeFeedback = new JLabel("");
        panelFeedback.add(lblMensajeFeedback, BorderLayout.CENTER);
        
        centro.add(javax.swing.Box.createVerticalStrut(20));
        centro.add(panelFeedback);
        
        add(centro, BorderLayout.CENTER);
        
        // 3. Panel Inferior (Botón)
        JPanel footer = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        footer.setBackground(Color.WHITE);
        footer.setBorder(new EmptyBorder(10, 10, 10, 10));
        
        btnAccion = new JButton("Comprobar Respuesta");
        btnAccion.setBackground(new Color(52, 152, 219)); // Azul
        btnAccion.setForeground(Color.WHITE);
        btnAccion.setFont(new Font("SansSerif", Font.BOLD, 14));
        
        btnAccion.addActionListener(e -> procesarAccion());
        
        footer.add(btnAccion);
        add(footer, BorderLayout.SOUTH);
        
        // Cargar primera pregunta
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
        lblPregunta.setText("<html>" + ej.getPregunta() + "</html>"); // HTML para salto de línea
        
        panelOpciones.removeAll();
        grupoOpciones = new ButtonGroup();
        
        char letra = 'A';
        for (String op : ej.getOpciones()) {
            JRadioButton rb = new JRadioButton(letra + ") " + op);
            rb.setActionCommand(String.valueOf(letra));
            rb.setFont(new Font("SansSerif", Font.PLAIN, 14));
            rb.setOpaque(false);
            grupoOpciones.add(rb);
            panelOpciones.add(rb);
            panelOpciones.add(javax.swing.Box.createVerticalStrut(10));
            letra++;
        }
        
        // Ocultar feedback y resetear botón
        panelFeedback.setVisible(false);
        btnAccion.setText("Comprobar Respuesta");
        btnAccion.setBackground(new Color(52, 152, 219)); // Azul
        esperandoSiguiente = false;
        
        revalidate();
        repaint();
    }
    
    private void procesarAccion() {
        if (esperandoSiguiente) {
            // El usuario ya vio el feedback y quiere ir a la siguiente
            indiceActual++;
            cargarPregunta();
        } else {
            // El usuario está intentando responder
            verificarRespuesta();
        }
    }
    
    private void verificarRespuesta() {
        if (grupoOpciones.getSelection() == null) {
            JOptionPane.showMessageDialog(this, "Por favor selecciona una opción.");
            return;
        }
        
        Ejercicio ej = ejercicios.get(indiceActual);
        String seleccion = grupoOpciones.getSelection().getActionCommand();
        boolean esCorrecta = seleccion.equals(ej.getClaveRespuesta());
        
        // Mostrar Feedback
        panelFeedback.setVisible(true);
        
        if (esCorrecta) {
            aciertos++;
            panelFeedback.setBackground(new Color(213, 245, 227)); // Verde claro
            lblMensajeFeedback.setText("<html><b style='color:green'>¡Correcto!</b> Muy bien hecho.</html>");
        } else {
            panelFeedback.setBackground(new Color(250, 219, 216)); // Rojo claro
            String feedbackTexto = ej.getRetroalimentacion();
            if (feedbackTexto == null || feedbackTexto.isEmpty()) feedbackTexto = "Sin feedback disponible.";
            
            lblMensajeFeedback.setText("<html><b style='color:red'>Incorrecto.</b><br/>" + feedbackTexto + "</html>");
        }
        
        // Cambiar estado del botón
        btnAccion.setText(indiceActual == ejercicios.size() - 1 ? "Ver Resultados" : "Siguiente Pregunta");
        btnAccion.setBackground(new Color(50, 50, 50)); // Gris oscuro para "Siguiente"
        esperandoSiguiente = true;
    }
    
    private void mostrarResultados() {
        notaFinal = ((double) aciertos / ejercicios.size()) * 20;
        finalizado = true;
        
        String mensaje = String.format("Has terminado.\nAciertos: %d/%d\nNota: %.1f", aciertos, ejercicios.size(), notaFinal);
        JOptionPane.showMessageDialog(this, mensaje, "Actividad Completada", JOptionPane.INFORMATION_MESSAGE);
        dispose();
    }

    public boolean isFinalizado() { return finalizado; }
    public double getNotaFinal() { return notaFinal; }
}
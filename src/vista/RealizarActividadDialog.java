package vista;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.util.List;
import java.util.Random;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
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
    private JButton btnAccion; 
    
    // Componentes Mascota
    private JLabel lblMascota;       
    private JPanel panelGloboTexto;  
    private JLabel lblFeedbackTexto; 
    
    private boolean esperandoSiguiente = false;

    // Colores
    private final Color COLOR_FONDO = new Color(232, 248, 245); 
    private final Color COLOR_CARD_PREGUNTA = Color.WHITE;
    private final Color COLOR_BTN_ACCION = new Color(245, 183, 177); 
    private final Color COLOR_TEXTO = new Color(50, 60, 80);
    
    private final Color COLOR_GLOBO_NEUTRO = new Color(255, 255, 255);
    private final Color COLOR_GLOBO_BIEN = new Color(213, 245, 227); 
    private final Color COLOR_GLOBO_MAL = new Color(250, 219, 216);

    // --- LISTA DE TUS IMÁGENES ---
    // Asegúrate de que estos nombres coincidan con los archivos en src/img
    private final String[] NOMBRES_MASCOTAS = {
        "mascota_1.png","mascota_2.png","mascota_3.png","mascota_4.png","mascota_5.png","mascota_6.png" 
        // Agrega aquí todas las que tengas
    };

    public RealizarActividadDialog(JFrame parent, Actividad actividad, List<Ejercicio> ejercicios) {
        super(parent, "Resolviendo: " + actividad.getNombre(), true);
        this.ejercicios = ejercicios;
        
        setSize(850, 550); 
        setLocationRelativeTo(parent);
        setLayout(new BorderLayout());
        getContentPane().setBackground(COLOR_FONDO);

        // 1. HEADER
        JPanel header = new JPanel(new FlowLayout(FlowLayout.LEFT, 20, 15));
        header.setOpaque(false);
        lblProgreso = new JLabel("Ejercicio 1 de " + ejercicios.size());
        lblProgreso.setFont(new Font("SansSerif", Font.BOLD, 14));
        lblProgreso.setForeground(new Color(118, 215, 196)); 
        header.add(lblProgreso);
        add(header, BorderLayout.NORTH);
        
        // 2. CENTRO
        JPanel panelCentral = new JPanel(new GridLayout(1, 2, 20, 0)); 
        panelCentral.setOpaque(false);
        panelCentral.setBorder(new EmptyBorder(10, 30, 10, 30));
        
        // Izquierda: Pregunta
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
        cardPregunta.add(Box.createVerticalStrut(20));
        cardPregunta.add(panelOpciones);
        cardPregunta.add(Box.createVerticalGlue());
        
        // Derecha: Mascota Random
        JPanel panelMascotaContainer = new JPanel(new BorderLayout());
        panelMascotaContainer.setOpaque(false);
        
        panelGloboTexto = new JPanel(new BorderLayout());
        panelGloboTexto.setBackground(COLOR_GLOBO_NEUTRO);
        panelGloboTexto.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(200,200,200), 1, true),
            new EmptyBorder(15, 15, 15, 15)
        ));
        
        lblFeedbackTexto = new JLabel("...");
        lblFeedbackTexto.setFont(new Font("SansSerif", Font.ITALIC, 14));
        lblFeedbackTexto.setForeground(Color.GRAY);
        panelGloboTexto.add(lblFeedbackTexto, BorderLayout.CENTER);
        
        lblMascota = new JLabel();
        lblMascota.setHorizontalAlignment(SwingConstants.CENTER);
        lblMascota.setVerticalAlignment(SwingConstants.BOTTOM);
        
        panelMascotaContainer.add(panelGloboTexto, BorderLayout.NORTH);
        panelMascotaContainer.add(lblMascota, BorderLayout.CENTER);
        
        panelCentral.add(cardPregunta);
        panelCentral.add(panelMascotaContainer);
        
        add(panelCentral, BorderLayout.CENTER);
        
        // 3. FOOTER
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
        
        // UI Reset
        lblProgreso.setText("Ejercicio " + (indiceActual + 1) + " de " + ejercicios.size());
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
            panelOpciones.add(Box.createVerticalStrut(15)); 
            letra++;
        }
        
        // --- AQUÍ CARGAMOS UNA MASCOTA ALEATORIA NUEVA ---
        mostrarMascotaAleatoria();
        
        // Reseteamos el globo de texto
        panelGloboTexto.setBackground(COLOR_GLOBO_NEUTRO);
        lblFeedbackTexto.setText("<html>¿Cuál crees que es la respuesta?</html>");
        
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
            JOptionPane.showMessageDialog(this, "¡Elige una opción!");
            return;
        }
        
        Ejercicio ej = ejercicios.get(indiceActual);
        String seleccion = grupoOpciones.getSelection().getActionCommand();
        boolean esCorrecta = seleccion.equals(ej.getClaveRespuesta());
        
        if (esCorrecta) {
            aciertos++;
            // Cambiamos SOLO el color y texto del globo, la mascota sigue igual
            panelGloboTexto.setBackground(COLOR_GLOBO_BIEN);
            lblFeedbackTexto.setText("<html><b style='color:green'>¡Muy bien!</b> ¡Acertaste!</html>");
        } else {
            String feedback = ej.getRetroalimentacion();
            if (feedback == null || feedback.isEmpty()) feedback = "Revisa el tema nuevamente.";
            
            panelGloboTexto.setBackground(COLOR_GLOBO_MAL);
            lblFeedbackTexto.setText("<html><b style='color:red'>Ups... era la " + ej.getClaveRespuesta() + "</b><br>" + feedback + "</html>");
        }
        
        btnAccion.setText("Siguiente ->");
        btnAccion.setBackground(new Color(100, 100, 100)); 
        esperandoSiguiente = true;
    }
    
    // --- LÓGICA DE MASCOTA ALEATORIA ---
    
    private void mostrarMascotaAleatoria() {
        Random rand = new Random();
        // Elegir un índice al azar del array de nombres
        String nombreImagen = NOMBRES_MASCOTAS[rand.nextInt(NOMBRES_MASCOTAS.length)];
        
        // Cargar imagen
        ImageIcon icono = cargarImagenMascota(nombreImagen);
        
        if (icono != null) {
            lblMascota.setIcon(icono);
            lblMascota.setText("");
        } else {
            lblMascota.setIcon(null);
            lblMascota.setText("<html><h1>[Mascota]</h1></html>");
        }
    }
    
    private ImageIcon cargarImagenMascota(String nombreArchivo) {
        String ruta = "/img/" + nombreArchivo; 
        java.net.URL imgURL = getClass().getResource(ruta);
        
        if (imgURL != null) {
            ImageIcon iconoOriginal = new ImageIcon(imgURL);
            // Redimensionar (ajusta el 250, 250 al tamaño que prefieras)
            java.awt.Image imagenEscalada = iconoOriginal.getImage().getScaledInstance(200, 200, java.awt.Image.SCALE_SMOOTH);
            return new ImageIcon(imagenEscalada);
        }
        return null;
    }
    
    private void estilarBoton(JButton btn) {
        btn.setBackground(COLOR_BTN_ACCION);
        btn.setForeground(Color.WHITE);
        btn.setFont(new Font("SansSerif", Font.BOLD, 16));
        btn.setFocusPainted(false);
//        btn.setBorder(BorderFactory.createCompoundBorder(
//             BorderFactory.createLineBorder(new Color(230, 160, 160), 1),
//             new EmptyBorder(10, 40, 10, 40)
//        ));
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
    }
    
    private void mostrarResultados() {
        notaFinal = ((double) aciertos / ejercicios.size()) * 20;
        finalizado = true;
        String mensaje = String.format("¡Terminaste!\nAciertos: %d/%d\nNota Final: %.1f", aciertos, ejercicios.size(), notaFinal);
        JOptionPane.showMessageDialog(this, mensaje, "¡Buen trabajo!", JOptionPane.INFORMATION_MESSAGE);
        dispose();
    }

    public boolean isFinalizado() { return finalizado; }
    public double getNotaFinal() { return notaFinal; }
}
package vista;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
import javax.swing.Timer; // IMPORTANTE: Para el reloj
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
    private JLabel lblImagenEjercicio; 
    private JPanel panelOpciones;
    private ButtonGroup grupoOpciones;
    private JButton btnAccion; 
    
    // --- NUEVO: CRONÓMETRO ---
    private JLabel lblCronometro;
    private Timer timer;
    private int segundosRestantes;
    private final int TIEMPO_POR_PREGUNTA = 30; // 30 Segundos por pregunta
    // -------------------------
    
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

    private final String[] NOMBRES_MASCOTAS = {
        "mascota_1.png", "mascota_2.png", "mascota_3.png", "mascota_4.png","mascota_5.png","mascota_6.png" 
    };

    public RealizarActividadDialog(JFrame parent, Actividad actividad, List<Ejercicio> ejercicios) {
        super(parent, "Resolviendo: " + actividad.getNombre(), true);
        this.ejercicios = ejercicios;
        
        setSize(900, 650); // Un poquito más alto para que todo quepa bien
        setLocationRelativeTo(parent);
        setLayout(new BorderLayout());
        getContentPane().setBackground(COLOR_FONDO);

        // 1. HEADER (Progreso + Cronómetro)
        JPanel header = new JPanel(new BorderLayout());
        header.setOpaque(false);
        header.setBorder(new EmptyBorder(15, 20, 15, 20));
        
        lblProgreso = new JLabel("Ejercicio 1 de " + ejercicios.size());
        lblProgreso.setFont(new Font("SansSerif", Font.BOLD, 14));
        lblProgreso.setForeground(new Color(118, 215, 196)); 
        
        // --- NUEVO: Label del Reloj ---
        lblCronometro = new JLabel("⏱️ " + TIEMPO_POR_PREGUNTA + "s");
        lblCronometro.setFont(new Font("Monospaced", Font.BOLD, 16));
        lblCronometro.setForeground(new Color(231, 76, 60)); // Rojo para que resalte
        
        header.add(lblProgreso, BorderLayout.WEST);
        header.add(lblCronometro, BorderLayout.EAST);
        
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
            new EmptyBorder(20, 20, 20, 20)
        ));
        
        lblPregunta = new JLabel("Cargando...");
        lblPregunta.setFont(new Font("SansSerif", Font.BOLD, 18));
        lblPregunta.setForeground(COLOR_TEXTO);
        lblPregunta.setAlignmentX(LEFT_ALIGNMENT);
        
        lblImagenEjercicio = new JLabel();
        lblImagenEjercicio.setAlignmentX(LEFT_ALIGNMENT);
        lblImagenEjercicio.setVisible(false);
        
        panelOpciones = new JPanel();
        panelOpciones.setLayout(new BoxLayout(panelOpciones, BoxLayout.Y_AXIS));
        panelOpciones.setOpaque(false);
        panelOpciones.setAlignmentX(LEFT_ALIGNMENT);
        
        cardPregunta.add(lblPregunta);
        cardPregunta.add(Box.createVerticalStrut(15));
        cardPregunta.add(lblImagenEjercicio);
        cardPregunta.add(Box.createVerticalStrut(15));
        cardPregunta.add(panelOpciones);
        cardPregunta.add(Box.createVerticalGlue());
        
        // Derecha: Mascota
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
        
        // Inicializar Timer y cargar primera pregunta
        configurarTimer();
        cargarPregunta();
    }
    
    // --- NUEVO: CONFIGURACIÓN DEL TIMER ---
    private void configurarTimer() {
        // Se ejecuta cada 1000ms (1 segundo)
        timer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                segundosRestantes--;
                lblCronometro.setText("⏱️ " + segundosRestantes + "s");
                
                // Cambiar color si queda poco tiempo
                if (segundosRestantes <= 10) {
                    lblCronometro.setForeground(Color.RED);
                } else {
                    lblCronometro.setForeground(new Color(231, 76, 60));
                }

                // ¡TIEMPO AGOTADO!
                if (segundosRestantes <= 0) {
                    timer.stop();
                    tiempoAgotado();
                }
            }
        });
    }
    
    private void tiempoAgotado() {
        // Bloquear opciones
        deshabilitarOpciones();
        
        // Mostrar feedback negativo
        panelGloboTexto.setBackground(COLOR_GLOBO_MAL);
        lblFeedbackTexto.setText("<html><b style='color:red'>¡Se acabó el tiempo!</b><br>Intenta ser más rápido la próxima.</html>");
        
        // Actualizar UI
        btnAccion.setText("Siguiente ->");
        btnAccion.setBackground(new Color(100, 100, 100));
        esperandoSiguiente = true;
        
        // Nota: No suma puntos
    }
    
    private void deshabilitarOpciones() {
        java.util.Enumeration<javax.swing.AbstractButton> buttons = grupoOpciones.getElements();
        while (buttons.hasMoreElements()) {
            buttons.nextElement().setEnabled(false);
        }
    }
    // --------------------------------------

    private void cargarPregunta() {
        if (indiceActual >= ejercicios.size()) {
            mostrarResultados();
            return;
        }
        
        Ejercicio ej = ejercicios.get(indiceActual);
        
        // Resetear textos
        lblProgreso.setText("Ejercicio " + (indiceActual + 1) + " de " + ejercicios.size());
        lblPregunta.setText("<html>" + ej.getPregunta() + "</html>"); 
        
        // Imagen
        ImageIcon imgEjercicio = ej.getImagen(); 
        if (imgEjercicio != null) {
            Image imgEscalada = imgEjercicio.getImage().getScaledInstance(250, 150, Image.SCALE_SMOOTH); 
            lblImagenEjercicio.setIcon(new ImageIcon(imgEscalada));
            lblImagenEjercicio.setVisible(true);
        } else {
            lblImagenEjercicio.setVisible(false);
        }
        
        // Opciones
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
        
        // Mascota
        mostrarMascotaAleatoria();
        panelGloboTexto.setBackground(COLOR_GLOBO_NEUTRO);
        lblFeedbackTexto.setText("<html>¡Tienes " + TIEMPO_POR_PREGUNTA + " segundos!<br>¿Cuál es la respuesta?</html>");
        
        btnAccion.setText("¡Comprobar!");
        btnAccion.setBackground(COLOR_BTN_ACCION);
        btnAccion.setEnabled(true);
        esperandoSiguiente = false;
        
        // --- REINICIAR EL TIMER ---
        segundosRestantes = TIEMPO_POR_PREGUNTA;
        lblCronometro.setText("⏱️ " + segundosRestantes + "s");
        lblCronometro.setForeground(new Color(231, 76, 60));
        timer.restart();
        // --------------------------
        
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
        
        // --- DETENER EL TIMER AL RESPONDER ---
        timer.stop();
        // -------------------------------------
        
        Ejercicio ej = ejercicios.get(indiceActual);
        String seleccion = grupoOpciones.getSelection().getActionCommand();
        boolean esCorrecta = seleccion.equals(ej.getClaveRespuesta());
        
        // Bloquear para que no cambie la respuesta
        deshabilitarOpciones();
        
        if (esCorrecta) {
            aciertos++;
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
    
    private void mostrarMascotaAleatoria() {
        Random rand = new Random();
        String nombreImagen = NOMBRES_MASCOTAS[rand.nextInt(NOMBRES_MASCOTAS.length)];
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
            // Escalar la mascota
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
        btn.setBorder(BorderFactory.createCompoundBorder(
             BorderFactory.createLineBorder(new Color(230, 160, 160), 1),
             new EmptyBorder(10, 40, 10, 40)
        ));
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
    }
    
    private void mostrarResultados() {
        // Aseguramos que el timer esté parado
        if(timer != null) timer.stop();
        
        notaFinal = ((double) aciertos / ejercicios.size()) * 20;
        finalizado = true;
        String mensaje = String.format("¡Terminaste!\nAciertos: %d/%d\nNota Final: %.1f", aciertos, ejercicios.size(), notaFinal);
        JOptionPane.showMessageDialog(this, mensaje, "¡Buen trabajo!", JOptionPane.INFORMATION_MESSAGE);
        dispose();
    }
    
    @Override
    public void dispose() {
        if (timer != null) timer.stop(); // Limpieza al cerrar
        super.dispose();
    }

    public boolean isFinalizado() { return finalizado; }
    public double getNotaFinal() { return notaFinal; }
}
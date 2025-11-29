package controller;

import java.io.IOException;
import java.net.URL;
import java.util.Random;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineEvent;
import javax.sound.sampled.LineListener;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class AudioService {

    private static AudioService instancia;
    private Clip clip;
    private boolean encendido = true;

    // Tu lista de canciones
    private final String[] PLAYLIST = {
            "MP_S1.wav",
            "MP_S2.wav",
            "MP_S3.wav",
            "MP_S4.wav",
            "MP_S5.wav",
            "MP_S6.wav",
            "MP_S7.wav"
    };

    private AudioService() {
    }

    public static AudioService getInstance() {
        if (instancia == null) {
            instancia = new AudioService();
        }
        return instancia;
    }

    public void iniciarMusica() {
        // Si ya hay algo sonando, lo cerramos antes de abrir otro
        if (clip != null && clip.isOpen()) {
            clip.close();
        }

        if (!encendido)
            return; // Si está apagado, no hacemos nada

        try {
            // Detener si ya está sonando
            if (clip != null && clip.isRunning()) {
                clip.stop();
                clip.close();
            }

            // Elegimos un índice al azar entre 0 y el tamaño de la lista
            int indiceRandom = new Random().nextInt(PLAYLIST.length);
            String cancionSeleccionada = PLAYLIST[indiceRandom];
            String ruta = "/audio/" + cancionSeleccionada;

            System.out.println("Reproduciendo: " + cancionSeleccionada);

            URL url = getClass().getResource(ruta);
            if (url == null) {
                System.err.println("No se encontró: " + ruta);
                return;
            }

            AudioInputStream audioInput = AudioSystem.getAudioInputStream(url);
            clip = AudioSystem.getClip();
            clip.open(audioInput);

            // Volumen
            FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
            gainControl.setValue(-15.0f);

            // --- 2. EL CAMBIO IMPORTANTE: EL DETECTOR ---
            clip.addLineListener(new LineListener() {
                @Override
                public void update(LineEvent event) {
                    // Si el evento es "STOP" (la canción terminó)...
                    if (event.getType() == LineEvent.Type.STOP) {
                        // Y si el sistema sigue "encendido" (el usuario no lo apagó manualmente)...
                        if (encendido) {
                            clip.close(); // Cerramos la actual
                            iniciarMusica(); // ¡LLAMADA RECURSIVA! Inicia la siguiente
                        }
                    }
                }
            });

            // 3. Ya NO usamos LOOP_CONTINUOUSLY, solo start()
            clip.start();

        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }

    public void toggleMusica() {
        if (clip == null)
            return;

        if (encendido) {
            clip.stop();
            clip.close();
            encendido = false;
        } else {
            clip.loop(Clip.LOOP_CONTINUOUSLY);
            clip.start();
            encendido = true;
            iniciarMusica(); // Arranca una nueva canción
        }
    }

    public boolean isEncendido() {
        return encendido;
    }
}
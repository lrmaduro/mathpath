package controller;

import java.io.IOException;
import java.net.URL;
import java.util.Random; // Importante para elegir al azar
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class AudioService {

    private static AudioService instancia;
    private Clip clip;
    private boolean encendido = true; 

    // --- 1. LISTA DE CANCIONES ---
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
        try {
            // --- 2. SELECCIÓN ALEATORIA ---
            // Elegimos un índice al azar entre 0 y el tamaño de la lista
            int indiceRandom = new Random().nextInt(PLAYLIST.length);
            String cancionSeleccionada = PLAYLIST[indiceRandom];
            
            // Construimos la ruta
            String ruta = "/audio/" + cancionSeleccionada;
            //System.out.println("Reproduciendo: " + cancionSeleccionada); // Para que veas en consola cuál tocó

            URL url = getClass().getResource(ruta);
            
            if (url == null) {
                //System.err.println("No se encontró el archivo de audio: " + ruta);
                return;
            }

            AudioInputStream audioInput = AudioSystem.getAudioInputStream(url);
            clip = AudioSystem.getClip();
            clip.open(audioInput);
            
            // Volumen bajo (-15.0f es un buen volumen de fondo suave)
            FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
            gainControl.setValue(-15.0f); 
            
            if (encendido) {
                clip.loop(Clip.LOOP_CONTINUOUSLY); 
                clip.start();
            }

        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }

    public void toggleMusica() {
        if (clip == null) return;

        if (encendido) {
            clip.stop();
            encendido = false;
        } else {
            clip.loop(Clip.LOOP_CONTINUOUSLY);
            clip.start();
            encendido = true;
        }
    }
    
    public boolean isEncendido() {
        return encendido;
    }
}
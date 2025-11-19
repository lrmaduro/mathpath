package modelo;

import java.util.List;

public class Ejercicio {
    
    private String id;
    private String pregunta;
    private List<String> opciones; // Las 4 opciones de respuesta
    private String claveRespuesta;  // Ej: "A", "B", "C" o "D"
    private String idTema;
    private String tipo;

    // --- NUEVO CONSTRUCTOR ---
    public Ejercicio(String id, String pregunta, List<String> opciones, String claveRespuesta, String idTema, String tipo) {
        this.id = id;
        this.pregunta = pregunta;
        this.opciones = opciones;
        this.claveRespuesta = claveRespuesta;
        this.idTema = idTema;
        this.tipo = tipo;
    }

    // Getters
    public String getId() { return id; }
    public String getPregunta() { return pregunta; }
    public List<String> getOpciones() { return opciones; } // Nuevo
    public String getClaveRespuesta() { return claveRespuesta; } // Nuevo
    public String getIdTema() { return idTema; }
    public String getTipo() { return tipo; }
    
    // Método de utilidad para mostrar la respuesta correcta (opcional)
    public String getRespuestaCorrecta() {
        int index = claveRespuesta.toUpperCase().charAt(0) - 'A';
        if (index >= 0 && index < opciones.size()) {
            return opciones.get(index);
        }
        return "N/A";
    }
}
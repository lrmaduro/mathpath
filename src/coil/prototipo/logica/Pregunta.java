/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package coil.prototipo.logica;

import java.util.ArrayList;


public class Pregunta {
    private int id_pregunta;
    private String enunciado;
    private ArrayList<Respuesta> opciones;
    private int posicionRespuestaCorrecta;
    private Respuesta respuestaCorrecta;

    public Pregunta(int id_pregunta, String enunciado, ArrayList opciones, Respuesta respuesta, int posicionRespuestaCorrecta) {
        this.id_pregunta = id_pregunta;
        this.enunciado = enunciado;
        this.opciones = opciones;
        this.respuestaCorrecta = respuesta;
        this.posicionRespuestaCorrecta = posicionRespuestaCorrecta;
    }

    public Pregunta() {
    }

    public Pregunta(int id_pregunta, String enunciado, ArrayList<Respuesta> opciones, int posicionRespuestaCorrecta) {
        this.id_pregunta = id_pregunta;
        this.enunciado = enunciado;
        this.opciones = opciones;
        this.posicionRespuestaCorrecta = posicionRespuestaCorrecta;
        this.respuestaCorrecta = opciones.get(posicionRespuestaCorrecta);
    }
    
    public String getEnunciado() {
        return enunciado;
    }
    
    public Respuesta getRespuestaCorrecta() {
        return respuestaCorrecta;
    }
    
    public void setEnunciado(String enunciado) {
        this.enunciado = enunciado;
    }

    public void setRespuestaCorrecta(Respuesta respuestaCorrecta) {
        this.respuestaCorrecta = respuestaCorrecta;
    }
    
    public void setRespuestaCorrecta() {
        for (Respuesta r : opciones) {
            if (r.isCorrecta())
                this.respuestaCorrecta = r;
        }
    }

    public ArrayList<Respuesta> getOpciones() {
        return opciones;
    }

    public int getId_pregunta() {
        return id_pregunta;
    }

    public void setId_pregunta(int id_pregunta) {
        this.id_pregunta = id_pregunta;
    }

    public void setOpciones(ArrayList<Respuesta> opciones) {
        this.opciones = opciones;
    }

    public int getPosicionRespuestaCorrecta() {
        return posicionRespuestaCorrecta;
    }

    public void setPosicionRespuestaCorrecta(int posicionRespuestaCorrecta) {
        this.posicionRespuestaCorrecta = posicionRespuestaCorrecta;
    }
    
    @Override
    public String toString() {
        return enunciado;
    }
}

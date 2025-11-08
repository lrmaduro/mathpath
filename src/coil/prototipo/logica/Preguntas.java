/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package coil.prototipo.logica;

import java.util.ArrayList;


public class Preguntas {
    private String id_preguntas;
    public String enunciado;
    public ArrayList<Respuesta> opciones;
    public int posicionRespuestaCorrecta;
    public Respuesta respuestaCorrecta;

    public Preguntas(String id_preguntas, String enunciado, ArrayList opciones, Respuesta respuesta, int posicionRespuestaCorrecta) {
        this.id_preguntas = id_preguntas;
        this.enunciado = enunciado;
        this.opciones = opciones;
        this.respuestaCorrecta = respuesta;
        this.posicionRespuestaCorrecta = posicionRespuestaCorrecta;
    }

    public Preguntas(String id_preguntas, String enunciado, ArrayList<Respuesta> opciones, int posicionRespuestaCorrecta) {
        this.id_preguntas = id_preguntas;
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

    public ArrayList<Respuesta> getOpciones() {
        return opciones;
    }

    public void setOpciones(ArrayList<Respuesta> opciones) {
        this.opciones = opciones;
    }

    public String getId_preguntas() {
        return id_preguntas;
    }

    public void setId_preguntas(String id_preguntas) {
        this.id_preguntas = id_preguntas;
    }

    public int getPosicionRespuestaCorrecta() {
        return posicionRespuestaCorrecta;
    }

    public void setPosicionRespuestaCorrecta(int posicionRespuestaCorrecta) {
        this.posicionRespuestaCorrecta = posicionRespuestaCorrecta;
    }
}

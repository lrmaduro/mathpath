/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package coil.prototipo.logica;

/**
 *
 * @author luisr
 */
public class Nota { 
   private int id_estudiante;
    private int id_evaluacion;
    private float nota;

    public Nota(int id_estudiante, int id_evaluacion, float nota) {
        this.id_estudiante = id_estudiante;
        this.id_evaluacion = id_evaluacion;
        this.nota = nota;
    }

    public Nota() {
    }

    public int getId_estudiante() {
        return id_estudiante;
    }

    public void setId_estudiante(int id_estudiante) {
        this.id_estudiante = id_estudiante;
    }

    public float getNota() {
        return nota;
    }

    public void setNota(float nota) {
        this.nota = nota;
    }

    public int getId_evaluacion() {
        return id_evaluacion;
    }

    public void setId_evaluacion(int id_evaluacion) {
        this.id_evaluacion = id_evaluacion;
    }
    
}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package coil.prototipo.logica;

import java.util.ArrayList;
/**
 *
 * @author luisr
 */
public class ListaNotas {
    private ArrayList<Nota> listaNotas;
    private int id_estudiante;

    public ListaNotas() {
    }

    public ListaNotas(ArrayList<Nota> listaNotas, int id_estudiante) {
        this.listaNotas = listaNotas;
        this.id_estudiante = id_estudiante;
    }

    public ArrayList<Nota> getListaNotas() {
        return listaNotas;
    }

    public void setListaNotas(ArrayList<Nota> listaNotas) {
        this.listaNotas = listaNotas;
    }

    public int getId_estudiante() {
        return id_estudiante;
    }

    public void setId_estudiante(int id_estudiante) {
        this.id_estudiante = id_estudiante;
    }
    
    public void anadirNota(Nota nota) {
        listaNotas.add(nota);
    }
    
    public Nota obtenerNota(int pos) {
        return listaNotas.get(pos);
    }
    
    public void removerNota(Nota nota) {
        listaNotas.remove(nota);
    }
    
    public void removerNota(int pos) {
        listaNotas.remove(pos);
    }
    
}

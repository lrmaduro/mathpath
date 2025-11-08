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
public class ListaEstudiantes {
    private ArrayList<Estudiante> listaEst;

    public ListaEstudiantes() {
    }

    public ListaEstudiantes(ArrayList<Estudiante> listaEst) {
        this.listaEst = listaEst;
    }

    public ArrayList<Estudiante> getListaEst() {
        return listaEst;
    }

    public void setListaEst(ArrayList<Estudiante> listaEst) {
        this.listaEst = listaEst;
    }
    
    public void anadirEst(Estudiante est) {
        listaEst.add(est);
    }
    
    public void borrarEst(Estudiante est) {
        listaEst.remove(est);
    }
    
    public void borrarEst(int pos) {
        listaEst.remove(pos);
    }
    
}

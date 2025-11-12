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
public class ListaPreguntas {
    private ArrayList<Pregunta> listaPreg;

    public ListaPreguntas(ArrayList<Pregunta> listaPreg) {
        this.listaPreg = listaPreg;
    }

    public ListaPreguntas() {
    }

    public ArrayList<Pregunta> getListaPreg() {
        return listaPreg;
    }

    public void setListaPreg(ArrayList<Pregunta> listaPreg) {
        this.listaPreg = listaPreg;
    }
    
    public void anadirPreg(Pregunta p) {
        listaPreg.add(p);
    }
    
    public void removerPreg(int pos) {
        listaPreg.remove(pos);
    }
    
    public void removerPreg(Pregunta p) {
        listaPreg.remove(p);
    }
    
    public Pregunta obtenerPreg(int pos) {
        return listaPreg.get(pos);
    }
    
    @Override
    public String toString() {
        if (listaPreg.size() == 1)
            return "["+listaPreg.getLast()+"]";
        else if (listaPreg.isEmpty())
            return "[]";
        
        String s = "[";
        for (int i = 0; i < listaPreg.size(); i++) {
            s += listaPreg.get(i).getEnunciado() + ", ";
        }
        s += listaPreg.getLast().getEnunciado() + "]";
        return s;
    }
}

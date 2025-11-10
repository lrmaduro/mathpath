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
public class ListaTemas {
    private ArrayList<Tema> listaTemas;

    public ListaTemas() {
    }

    public ListaTemas(ArrayList<Tema> listaTemas) {
        this.listaTemas = listaTemas;
    }

    public ArrayList<Tema> getListaTemas() {
        return listaTemas;
    }

    public void setListaTemas(ArrayList<Tema> listaTemas) {
        this.listaTemas = listaTemas;
    }
    
    public void anadirTema(Tema t) {
        listaTemas.add(t);
    }
    
    public void removerTema(int pos) {
        listaTemas.remove(pos);
    }
    
    public void removerTema(Tema t) {
        listaTemas.remove(t);
    }
    
    public Tema obtenerTema(int pos) {
        return listaTemas.get(pos);
    }
}

package controller; // o 'service'

import java.util.ArrayList;
import java.util.List;

public class TemaService {
    
    // Nuestra lista "fija" de temas
    private List<String> temas;

    public TemaService() {
        temas = new ArrayList<>();
        temas.add("Aritmética Básica");
        temas.add("Álgebra");
        temas.add("Geometría");
        temas.add("Fracciones");
        temas.add("Ecuaciones");
    }
    
    /**
     * Devuelve la lista de todos los temas disponibles.
     */
    public List<String> getTemas() {
        return temas;
    }
    
    public void addTema(String nombreTema) {
        // (En un futuro, aquí comprobarías que no esté duplicado)
        temas.add(nombreTema);
    }
    // (En el futuro, aquí podrías tener un método 'addTema(String tema)')
}
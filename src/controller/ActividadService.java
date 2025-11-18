package controller; // o 'service'

import java.util.ArrayList;
import java.util.List;
import modelo.Actividad;

public class ActividadService {
    
    // Base de datos falsa de todas las actividades
    private List<Actividad> actividades;
    
    public ActividadService() {
        actividades = new ArrayList<>();
        
        // Creamos actividades de prueba
        // Actividades para "Matemáticas 101" (idAula = "a001")
        actividades.add(new Actividad("act001", "Suma y Resta", "a001", "Aritmética Básica"));
        actividades.add(new Actividad("act002", "Multiplicación", "a001", "Aritmética Básica"));
        
        // Actividades para "Álgebra Básica" (idAula = "a002")
        actividades.add(new Actividad("act003", "Ecuaciones 1er Grado", "a002", "Ecuaciones"));
    }
    
    /**
     * Devuelve una lista de actividades que pertenecen
     * a un aula específica.
     */
    public List<Actividad> getActividadesPorAula(String idAula) {
        List<Actividad> resultado = new ArrayList<>();
        for (Actividad act : actividades) {
            if (act.getIdAula().equals(idAula)) {
                resultado.add(act);
            }
        }
        return resultado;
    }
    
    public void addActividad(Actividad nuevaActividad) {
        actividades.add(nuevaActividad);
    }
}
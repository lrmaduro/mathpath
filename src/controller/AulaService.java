package controller;

import java.util.ArrayList;
import java.util.List;
import modelo.Aula;
import modelo.Usuario;

public class AulaService {
    
    // Base de datos falsa de aulas
    private List<Aula> aulas;
    
    public AulaService() {
        aulas = new ArrayList<>();
        
        // Asignamos estas aulas al usuario "d001" (Profesor Jirafales)
        aulas.add(new Aula("a001", "Matemáticas 101", "MATH101", "Curso básico", "d001"));
        aulas.add(new Aula("a002", "Álgebra Básica", "ALG-BAS", "Intro álgebra", "d001"));
        aulas.add(new Aula("a003", "Geometría", "GEO-01", "Figuras", "d001"));
    }
    
    /**
     * Devuelve una lista de TODAS las aulas.
     * (En el futuro, esto debería ser getAulasPorDocente(Usuario docente))
     */
    public List<Aula> getTodasLasAulas() {
        return aulas;
    }
    
    public List<Aula> getAulasPorDocente(String idDocenteBuscado) {
        List<Aula> misAulas = new ArrayList<>();
        for (Aula a : aulas) {
            // Si el ID del dueño coincide con el ID que buscamos...
            if (a.getIdDocente() != null && a.getIdDocente().equals(idDocenteBuscado)) {
                misAulas.add(a);
            }
        }
        return misAulas;
    }
    
    public void addAula(Aula nuevaAula) {
        aulas.add(nuevaAula);
    }
}
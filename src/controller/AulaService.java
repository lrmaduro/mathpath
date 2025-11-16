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
        
        // Creamos aulas de prueba
        aulas.add(new Aula("a001", "Matemáticas 101", "MATH101", "Curso básico de aritmética"));
        aulas.add(new Aula("a002", "Álgebra Básica", "ALG-BAS", "Introducción al álgebra"));
        aulas.add(new Aula("a003", "Geometría Plana", "GEO-PLN", "Figuras y formas"));
    }
    
    /**
     * Devuelve una lista de TODAS las aulas.
     * (En el futuro, esto debería ser getAulasPorDocente(Usuario docente))
     */
    public List<Aula> getTodasLasAulas() {
        return aulas;
    }
    
    public void addAula(Aula nuevaAula) {
        aulas.add(nuevaAula);
    }
}
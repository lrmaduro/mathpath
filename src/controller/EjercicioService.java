package controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import modelo.Ejercicio;

public class EjercicioService {

    private List<Ejercicio> ejercicios;

    public EjercicioService() {
        ejercicios = new ArrayList<>();
        
        // --- EJERCICIOS DE PRUEBA ACTUALIZADOS ---
        List<String> opciones1 = Arrays.asList("7", "8", "10", "12");
        ejercicios.add(new Ejercicio("e001", "¿Cuánto es 5 + 3?", opciones1, "B", "Aritmética Básica", "Opción Múltiple"));
        
        List<String> opciones2 = Arrays.asList("x=2", "x=5", "x=10", "x=1");
        ejercicios.add(new Ejercicio("e002", "Dada la ecuación 2x=10, ¿cuál es el valor de x?", opciones2, "B", "Álgebra", "Opción Múltiple"));
    }

    public List<Ejercicio> getTodosLosEjercicios() {
        return ejercicios;
    }

    // El método addEjercicio se mantiene igual, solo cambia el tipo de dato que recibe
    public void addEjercicio(Ejercicio nuevoEjercicio) {
        ejercicios.add(nuevoEjercicio);
    }
}
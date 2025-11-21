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
        // Notar el último parámetro añadido: "Revisa la suma con los dedos..."
        ejercicios.add(new Ejercicio("e001", "¿Cuánto es 5 + 3?", opciones1, "B", "Aritmética Básica", "Opción Múltiple", "Recuerda: Tienes 5 unidades y agregas 3 más."));
        
        List<String> opciones2 = Arrays.asList("x=2", "x=5", "x=10", "x=1");
        ejercicios.add(new Ejercicio("e002", "2x=10, hallar x", opciones2, "B", "Álgebra", "Opción Múltiple", "Para despejar x, divide ambos lados entre 2."));
    }

    public List<Ejercicio> getTodosLosEjercicios() {
        return ejercicios;
    }

    // El método addEjercicio se mantiene igual, solo cambia el tipo de dato que recibe
    public void addEjercicio(Ejercicio nuevoEjercicio) {
        ejercicios.add(nuevoEjercicio);
    }
}
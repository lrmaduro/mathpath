package controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import modelo.Ejercicio;

public class EjercicioService {

    private List<Ejercicio> ejercicios;

    public EjercicioService() {
        ejercicios = new ArrayList<>();
        
        // --- EJERCICIOS NORMALES (Solo Texto) ---
        List<String> op1 = Arrays.asList("7", "8", "10", "12");
        ejercicios.add(new Ejercicio("e001", "¿Cuánto es 5 + 3?", op1, "B", "Aritmética Básica", "Opción Múltiple", "Usa tus dedos para contar."));
        
        // --- EJERCICIOS CON IMAGEN (PRECARGADOS) ---
        // Aquí usamos el constructor que acepta la ruta de la imagen al final
        
        // Ejercicio 1: Patrones (Usando una de tus imágenes)
        List<String> opPatron = Arrays.asList("Círculo Azul", "Triángulo Rojo", "Cuadrado Verde", "Estrella");
        ejercicios.add(new Ejercicio(
            "e_img_01",                         // ID
            "¿Cual es la figura que tiene lados curvos?", // Pregunta
            opPatron,                           // Opciones
            "B",                                // Respuesta Correcta
            "Patrones y Regularidades",         // Tema
            "Opción Múltiple",                  // Tipo
            "Fíjate en los colores y la forma que se repite.", // Feedback
            "/img/ejercicios/figuras planas y cuerpos geometricos ej.1.png"       // <--- RUTA DE LA IMAGEN (Asegúrate que el nombre coincida)
        ));

        // Ejercicio 2: Geometría
        List<String> opGeo = Arrays.asList("3 lados", "4 lados", "5 lados", "No tiene lados");
        ejercicios.add(new Ejercicio(
            "e_img_02", 
            "Mira esta figura. ¿Cuántos lados tiene?", 
            opGeo, 
            "B", 
            "Geometría", 
            "Opción Múltiple", 
            "Cuenta las líneas rectas que forman la figura.",
            "/img/ejercicios/figuras planas y cuerpos geometricos ej.1.png"          // <--- OTRA IMAGEN
        ));
        
        // Agrega aquí el resto de tus ejercicios del documento...
    }

    public List<Ejercicio> getTodosLosEjercicios() {
        return ejercicios;
    }

    public void addEjercicio(Ejercicio nuevoEjercicio) {
        ejercicios.add(nuevoEjercicio);
    }
}
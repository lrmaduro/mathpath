package controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ReporteService {

    // Simula nombres de estudiantes
    private final String[] NOMBRES = {"Ana García", "Luis Pérez", "María López", "Carlos Ruiz", "Sofía M.", "Jorge T."};
    
    /**
     * Genera una lista de filas para la tabla (Estudiante, Actividad, Nota, Estado)
     * basado en el ID del aula.
     */
    public List<Object[]> obtenerReportePorAula(String idAula) {
        List<Object[]> filas = new ArrayList<>();
        Random rand = new Random();
        
        // Si no hay aula seleccionada, devuelve vacío
        if (idAula == null) return filas;

        // Generamos datos falsos para el ejemplo
        for (String nombre : NOMBRES) {
            // Simulamos 2 o 3 actividades por alumno
            for (int i = 1; i <= 3; i++) {
                String actividad = "Actividad " + i;
                double nota = 5 + (rand.nextDouble() * 15); // Nota entre 5 y 20
                String estado = nota >= 10 ? "Aprobado" : "Reprobado";
                
                filas.add(new Object[]{
                    nombre,
                    actividad,
                    String.format("%.1f", nota), // Nota con 1 decimal
                    estado
                });
            }
        }
        return filas;
    }
    
    public double calcularPromedioGeneral(List<Object[]> filas) {
        if (filas.isEmpty()) return 0.0;
        double suma = 0;
        for (Object[] fila : filas) {
            String notaStr = (String) fila[2];
            suma += Double.parseDouble(notaStr.replace(",", "."));
        }
        return suma / filas.size();
    }
}
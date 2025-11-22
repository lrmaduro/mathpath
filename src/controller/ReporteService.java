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
    public List<Object[]> obtenerReportePorActividad(String idActividad) {
        List<Object[]> filas = new ArrayList<>();
        Random rand = new Random();
        
        // Simulamos notas para esa actividad específica
        for (String nombre : NOMBRES) {
            double nota = 5 + (rand.nextDouble() * 15); // Nota aleatoria 5-20
            String estado = nota >= 10 ? "Aprobado" : "Reprobado";
            
            // Algoritmo simple de feedback simulado
            String feedback = (nota < 10) ? "Reforzar conceptos base" : "Buen desempeño";
            if (nota > 18) feedback = "Excelente dominio";

            filas.add(new Object[]{
                nombre,
                String.format("%.1f", nota),
                estado,
                feedback // Columna extra para actividad
            });
        }
        return filas;
    }

    /**
     * Devuelve un array con la cantidad de alumnos en cada rango:
     * [0]: Crítico (0-10)
     * [1]: Regular (11-14)
     * [2]: Bueno (15-17)
     * [3]: Excelente (18-20)
     */
/**
     * Devuelve un array con la cantidad de alumnos en cada rango.
     * AHORA RECIBE 'indiceNota' para saber en qué columna está el número.
     */
    public int[] obtenerDistribucionNotas(List<Object[]> datos, int indiceNota) {
        int[] dist = {0, 0, 0, 0};
        
        for (Object[] fila : datos) {
            try {
                // Usamos el índice dinámico que recibimos
                String notaStr = (String) fila[indiceNota]; 
                double nota = Double.parseDouble(notaStr.replace(",", "."));
                
                if (nota < 10) dist[0]++;      // Crítico
                else if (nota < 15) dist[1]++; // Regular
                else if (nota < 18) dist[2]++; // Bueno
                else dist[3]++;                // Excelente
            } catch (Exception e) {
                // Si falla el parseo, ignoramos la fila (evita el crash)
                continue; 
            }
        }
        return dist;
    }
    
}
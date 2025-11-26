package controller;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class NotaService {

    public List<Object[]> obtenerNotasPorEstudiante(String idEstudiante) {
        List<Object[]> filas = new ArrayList<>();
        Random rand = new Random();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        // Simulamos 5 actividades con notas
        for (int i = 1; i <= 5; i++) {
            String actividad = "Actividad " + i;
            double nota = 5 + (rand.nextDouble() * 15); // Nota entre 5 y 20
            String fecha = LocalDate.now().minusDays(rand.nextInt(30)).format(formatter); // Fecha aleatoria últimos 30
                                                                                          // días

            filas.add(new Object[] {
                    fecha,
                    actividad,
                    String.format("%.1f", nota)
            });
        }
        return filas;
    }
}

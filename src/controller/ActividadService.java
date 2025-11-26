package controller; // o 'service'

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import modelo.Actividad;

public class ActividadService {

    private List<Actividad> actividades;

    public ActividadService() {
        actividades = new ArrayList<>();

        // Actividades de prueba (actualizadas para incluir IDs de ejercicios y fecha
        // límite)
        List<String> ejMat101 = Arrays.asList("e001"); // Solo el ejercicio 1
        actividades.add(new Actividad("act001", "Suma y Resta", "a001", "Aritmética Básica", ejMat101,
                java.time.LocalDateTime.now().plusDays(7)));

        List<String> ejAlgBasica = Arrays.asList("e002"); // Solo el ejercicio 2
        actividades.add(new Actividad("act002", "Multiplicación", "a001", "Aritmética Básica", ejAlgBasica,
                java.time.LocalDateTime.now().plusDays(3)));
        actividades.add(new Actividad("act003", "Ecuaciones 1er Grado", "a002", "Ecuaciones", ejAlgBasica,
                java.time.LocalDateTime.now().minusDays(1))); // Vencida
    }

    public List<Actividad> getActividadesPorAula(String idAula) {
        List<Actividad> resultado = new ArrayList<>();
        for (Actividad act : actividades) {
            if (act.getIdAula().equals(idAula)) {
                resultado.add(act);
            }
        }
        return resultado;
    }

    // El método addActividad se actualiza para recibir el nuevo objeto Actividad
    public void addActividad(Actividad nuevaActividad) {
        actividades.add(nuevaActividad);
    }

    public void eliminarActividad(String id) {
        actividades.removeIf(act -> act.getId().equals(id));
    }

    public void actualizarActividad(Actividad actividadActualizada) {
        for (int i = 0; i < actividades.size(); i++) {
            if (actividades.get(i).getId().equals(actividadActualizada.getId())) {
                actividades.set(i, actividadActualizada);
                return;
            }
        }
    }
}
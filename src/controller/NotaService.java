package controller;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import database.dbConnection;
import modelo.Nota;

public class NotaService {

    private dbConnection db;

    public NotaService(dbConnection db) {
        this.db = db;
    }

    public List<Object[]> obtenerNotasPorEstudiante(String idEstudiante) {
        List<Object[]> filas = new ArrayList<>();
        List<Nota> notas = db.getNotasEstudiante(idEstudiante);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        for (int i = 0; i < notas.size(); i++) {
            String actividad = db.getNombreAct(notas.get(i).getIdActividad());
            double nota = notas.get(i).getNota();
            String fecha = notas.get(i).getFecha().format(formatter);

            filas.add(new Object[] {
                    fecha,
                    actividad,
                    String.format("%.1f", nota)
            });
        }
        return filas;
    }

    public void addNota(Nota n) {
        db.agregarNota(n);
    }

    public void asignarCerosAActividadesVencidas() {
        List<modelo.Aula> aulas = db.getAulas();
        for (modelo.Aula aula : aulas) {
            List<modelo.Actividad> actividades = db.getActividadesPorAula(aula.getId());
            List<modelo.Usuario> estudiantes = db.getEstudiantesAula(aula.getId());

            for (modelo.Actividad actividad : actividades) {
                if (actividad.getFechaLimite() != null
                        && actividad.getFechaLimite().isBefore(java.time.LocalDateTime.now())) {
                    for (modelo.Usuario estudiante : estudiantes) {
                        if (!tieneNota(estudiante.getId(), actividad.getId())) {
                            String idNota = "n" + (int) (Math.random() * 1000000);
                            Nota nota = new Nota(
                                    idNota,
                                    estudiante.getId(),
                                    actividad.getId(),
                                    0.0,
                                    actividad.getFechaLimite().toLocalDate());
                            addNota(nota);
                        }
                    }
                }
            }
        }
    }

    private boolean tieneNota(String idEstudiante, String idActividad) {
        List<Nota> notas = db.getNotasEstudiante(idEstudiante);
        for (Nota n : notas) {
            if (n.getIdActividad().equals(idActividad)) {
                return true;
            }
        }
        return false;
    }
}

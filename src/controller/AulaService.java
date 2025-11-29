package controller;

import java.util.List;
import database.dbConnection;
import modelo.Aula;

public class AulaService {

    private dbConnection db;

    public AulaService(dbConnection db) {
        this.db = db;
    }

    public List<Aula> getTodasLasAulas() {
        return db.getAulas();
    }

    public List<Aula> getAulasPorEstudiante(String idEstudianteBuscado) {
        return db.getAulasEstudiante(idEstudianteBuscado);
    }

    public List<Aula> getAulasPorDocente(String idDocenteBuscado) {
        return db.getAulasDocente(idDocenteBuscado);
    }

    public void addAula(Aula nuevaAula) {
        db.agregarAula(nuevaAula);
    }

    public void inscribirseAAula(String idAula, String idEstudiante) {
        db.inscribirseAAula(idAula, idEstudiante);
    }

    public boolean removerEstudianteDeAula(String idAula, String idEstudiante) {
        return db.removerEstudianteDeAula(idAula, idEstudiante);
    }
}
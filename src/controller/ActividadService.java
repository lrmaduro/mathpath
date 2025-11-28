package controller; // o 'service'

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import modelo.Actividad;
import database.dbConnection;

public class ActividadService {

    private dbConnection db;

    public ActividadService(dbConnection db) {
        this.db = db;
    }

    public List<Actividad> getActividadesPorAula(String idAula) {
        return db.getActividadesPorAula(idAula);
    }

    public List<String> getActividadesHechas(String idEstudiante) {
        return db.getActividadesHechas(idEstudiante);
    }

    // El método addActividad se actualiza para recibir el nuevo objeto Actividad
    public void addActividad(Actividad nuevaActividad) {
        db.agregarActividad(nuevaActividad);
    }

    public void eliminarActividad(String id) {
        db.eliminarActividad(id);
    }

    public void actualizarActividad(Actividad actividadActualizada) {
        db.actualizarActividad(actividadActualizada);
    }
}
package controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import modelo.Ejercicio;
import database.dbConnection;

public class EjercicioService {

    private dbConnection db;

    public EjercicioService(dbConnection db) {
        this.db = db;
    }

    public List<Ejercicio> getTodosLosEjercicios() {
        return db.getTodosLosEjercicios();
    }

    public List<Ejercicio> getEjerciciosPorActividad(String idActividad) {
        return db.getEjerciciosPorActividad(idActividad);
    }

    public void addEjercicio(Ejercicio nuevoEjercicio, String idDocente) {
        db.agregarEjercicio(nuevoEjercicio, idDocente);
    }

    public void eliminarEjercicio(String id) {
        db.eliminarEjercicio(id);
    }
}
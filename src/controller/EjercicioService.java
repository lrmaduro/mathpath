package controller;

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

    public List<Ejercicio> getEjerciciosPorDocente(String idDocente) {
        return db.getEjercicioDocente(idDocente);
    }

    public List<Ejercicio> getEjerciciosBasicos(String idDocente) {
        return db.getEjerciciosBasico(idDocente);
    }

    public List<Ejercicio> getEjerciciosBasicos(String idDocente, int pagina, int pagTamano) {
        int offset = (pagina - 1) * pagTamano;
        return db.getEjerciciosBasico(idDocente, pagTamano, offset);
    }

    public int getTotalEjerciciosBasicos(String idDocente) {
        return db.countEjerciciosBasico(idDocente);
    }

    public Ejercicio getEjercicioCompleto(String id) {
        return db.getEjercicioCompleto(id);
    }

    public List<Ejercicio> getEjerciciosPorActividad(String idActividad) {
        return db.getEjerciciosPorActividad(idActividad);
    }

    public List<Ejercicio> getEjerciciosPorTema(String idTema) {
        return db.getEjerciciosPorTema(idTema);
    }

    public List<Ejercicio> getEjerciciosPorTema(String idTema, String idDocente) {
        if (idTema.equals("Todos")) {
            return this.getEjerciciosBasicos(idDocente);
        }

        return db.getEjerciciosPorTema(idTema);
    }

    public List<Ejercicio> getEjerciciosPorTema(String idTema, int page, int pageSize) {
        int offset = (page - 1) * pageSize;
        return db.getEjerciciosPorTema(idTema, pageSize, offset);
    }

    public int getTotalEjerciciosPorTema(String idTema) {
        return db.countEjerciciosPorTema(idTema);
    }

    public void addEjercicio(Ejercicio nuevoEjercicio, String idDocente) {
        db.agregarEjercicio(nuevoEjercicio, idDocente);
    }

    public void eliminarEjercicio(String id) {
        db.eliminarEjercicio(id);
    }
}
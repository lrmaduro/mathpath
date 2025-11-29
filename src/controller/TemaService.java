package controller; // o 'service'

import java.util.List;
import modelo.Tema;

import database.dbConnection;

public class TemaService {

    private dbConnection db;

    public TemaService(dbConnection db) {
        this.db = db;
    }

    /**
     * Devuelve la lista de todos los temas disponibles.
     */
    public List<Tema> getTemas() {
        return db.getTemas();
    }

    public String getTemaNombre(String idTema) {
        return db.getTemaNombre(idTema);
    }

    public Tema getTemaPorNombre(String nombre) {
        return db.getTemaPorNombre(nombre);
    }

    public void addTema(String nombreTema) {
        // (En un futuro, aquí comprobarías que no esté duplicado)
        db.agregarTema(nombreTema);
    }
    // (En el futuro, aquí podrías tener un método 'addTema(String tema)')
}
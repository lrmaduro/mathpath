package modelo;

import java.util.List;
import java.time.LocalDateTime;

public class Actividad {

    private String id;
    private String nombre;
    private String idAula;
    private String tema;
    private List<String> idEjercicios; // <-- NUEVO: IDs de los ejercicios incluidos
    private java.time.LocalDateTime fechaLimite; // <-- NUEVO: Fecha límite

    // --- NUEVO CONSTRUCTOR ---
    public Actividad(String id, String nombre, String idAula, String tema, List<String> idEjercicios,
            LocalDateTime fechaLimite) {
        this.id = id;
        this.nombre = nombre;
        this.idAula = idAula;
        this.tema = tema;
        this.idEjercicios = idEjercicios;
        this.fechaLimite = fechaLimite;
    }

    // Getters
    public String getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public String getIdAula() {
        return idAula;
    }

    public String getTema() {
        return tema;
    }

    public List<String> getIdEjercicios() {
        return idEjercicios;
    }

    public java.time.LocalDateTime getFechaLimite() {
        return fechaLimite;
    } // Nuevo Getter

    public void setId(String id) {
        this.id = id;
    }
}
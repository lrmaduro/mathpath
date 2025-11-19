package modelo;

import java.util.List;

public class Actividad {
    
    private String id;
    private String nombre;
    private String idAula; 
    private String tema;
    private List<String> idEjercicios; // <-- NUEVO: IDs de los ejercicios incluidos

    // --- NUEVO CONSTRUCTOR ---
    public Actividad(String id, String nombre, String idAula, String tema, List<String> idEjercicios) {
        this.id = id;
        this.nombre = nombre;
        this.idAula = idAula;
        this.tema = tema;
        this.idEjercicios = idEjercicios;
    }

    // Getters
    public String getId() { return id; }
    public String getNombre() { return nombre; }
    public String getIdAula() { return idAula; }
    public String getTema() { return tema; }
    public List<String> getIdEjercicios() { return idEjercicios; } // Nuevo Getter
}
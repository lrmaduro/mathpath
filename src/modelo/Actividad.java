package modelo;

public class Actividad {
    
    private String id;
    private String nombre;
    private String idAula; // Importante: para saber a qué aula pertenece
    private String tema;
    // (En el futuro: fechaLimite, tiempoEstimado, etc.)

    public Actividad(String id, String nombre, String idAula, String tema) {
        this.id = id;
        this.nombre = nombre;
        this.idAula = idAula;
        this.tema = tema;
    }

    // Getters
    public String getId() { return id; }
    public String getNombre() { return nombre; }
    public String getIdAula() { return idAula; }
    public String getTema() { return tema; }
}
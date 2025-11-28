package modelo;

import java.time.LocalDate;

public class Nota {
    private String id;
    private String idEstudiante;
    private String idActividad;
    private double nota;
    private LocalDate fecha;

    public Nota(String id, String idEstudiante, String idActividad, double nota, String fecha) {
        this.id = id;
        this.idEstudiante = idEstudiante;
        this.idActividad = idActividad;
        this.nota = nota;
        this.fecha = LocalDate.parse(fecha);
    }

    public Nota(String id, String idEstudiante, String idActividad, double nota, LocalDate fecha) {
        this.id = id;
        this.idEstudiante = idEstudiante;
        this.idActividad = idActividad;
        this.nota = nota;
        this.fecha = fecha;
    }

    public Nota(String idEstudiante, String idActividad, double nota, LocalDate fecha) {
        this.idEstudiante = idEstudiante;
        this.idActividad = idActividad;
        this.nota = nota;
        this.fecha = fecha;
    }

    public String getId() {
        return id;
    }

    public String getIdEstudiante() {
        return idEstudiante;
    }

    public String getIdActividad() {
        return idActividad;
    }

    public double getNota() {
        return nota;
    }

    public LocalDate getFecha() {
        return fecha;
    }

}

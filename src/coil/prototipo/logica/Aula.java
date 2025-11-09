/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package coil.prototipo.logica;

import java.util.ArrayList;


public class Aula {
    
    private final String id_aula;
    private String nombre;
    private String descripcion;
    private String codigo_inscripcion;
    private ArrayList Estudiantes;
    private ArrayList Temas;
    private Docente docente;

    public Aula(String id_aula, String nombre) {
        this.id_aula = id_aula;
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getCodigo_inscripcion() {
        return codigo_inscripcion;
    }

    public void setCodigo_inscripcion(String codigo_inscripcion) {
        this.codigo_inscripcion = codigo_inscripcion;
    }

    public ArrayList getEstudiantes() {
        return Estudiantes;
    }

    public void setEstudiantes(ArrayList Estudiantes) {
        this.Estudiantes = Estudiantes;
    }

    public ArrayList getTemas() {
        return Temas;
    }

    public void setTemas(ArrayList Temas) {
        this.Temas = Temas;
    }

    public Docente getDocente() {
        return docente;
    }

    public void setDocente(Docente docente) {
        this.docente = docente;
    }

    public String getId_aula() {
        return id_aula;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    
    @Override
    public String toString() {
        return this.nombre; // Solo muestra el nombre
    }
}

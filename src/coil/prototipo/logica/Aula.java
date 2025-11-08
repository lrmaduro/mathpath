/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package coil.prototipo.logica;

import java.util.ArrayList;


public class Aula {
    
    private final String id_aula;
    public String nombre;
    public String descripcion;
    public String codigo_inscripcion;
    private ArrayList Estudiantes;
    private ArrayList Temas;
    private Docente docente;

    public Aula(String id_aula, String nombre) {
        this.id_aula = id_aula;
        this.nombre = nombre;
    }

    public String getId_aula() {
        return id_aula;
    }

    public String getNombre() {
        return nombre;
    }
    
    @Override
    public String toString() {
        return this.nombre; // Solo muestra el nombre
    }
}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package coil.prototipo.logica;

import java.util.ArrayList;


public class Tema {
    private String idTema;
    private String nombre;
    private String descripcion;
    private ArrayList<Actividad> actividad;

    public Tema(String id_tema, String nombre, String descripcion, ArrayList<Actividad> actividad) {
        this.idTema = id_tema;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.actividad = actividad;
    }

    public void setIdTema(String idTema) {
        this.idTema = idTema;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public void setActividad(ArrayList<Actividad> actividad) {
        this.actividad = actividad;
    }

    public Tema() {
    }

    public String getIdTema() {
        return idTema;
    }

    public String getNombre() {
        return nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public ArrayList<Actividad> getActividad() {
        return actividad;
    }
    
    @Override
    public String toString() {
        return nombre;
    }
  
}

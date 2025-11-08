/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package coil.prototipo.logica;

import java.util.ArrayList;


public class Tema {
    private String id_tema;
    public String nombre;
    public String descripcion;
    public ArrayList<Actividad> actividad;

    public Tema(String id_tema, String nombre, String descripcion, ArrayList<Actividad> actividad) {
        this.id_tema = id_tema;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.actividad = actividad;
    }

    public String getId_tema() {
        return id_tema;
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
  
}

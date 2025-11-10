/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package coil.prototipo.logica;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 *
 * @author Luis
 */
public class Actividad {
    private String id_actividad;
    private String nombre;
    private String descripcion;
    private int tipo_actividad;
    private LocalDateTime fecha_limite;
    private int puntajeMax;
    private ParametrosActividad parametros;
    private String tema;

    public Actividad() {
    }

    public Actividad(String id_actividad, String nombre, String descripcion, int tipo_actividad, LocalDateTime fecha_limite, int puntajeMax, ParametrosActividad parametros) {
        this.id_actividad = id_actividad;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.tipo_actividad = tipo_actividad;
        this.fecha_limite = fecha_limite;
        this.puntajeMax = puntajeMax;
        this.parametros = parametros;
    }

    public String getTema() {
        return tema;
    }

    public void setTema(String tema) {
        this.tema = tema;
    }

    public String getId_actividad() {
        return id_actividad;
    }

    public void setId_actividad(String id_actividad) {
        this.id_actividad = id_actividad;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public int getTipo_actividad() {
        return tipo_actividad;
    }

    public void setTipo_actividad(int tipo_actividad) {
        this.tipo_actividad = tipo_actividad;
    }

    public LocalDateTime getFecha_limite() {
        return fecha_limite;
    }

    public void setFecha_limite(LocalDateTime fecha_limite) {
        this.fecha_limite = fecha_limite;
    }

    public int getPuntajeMax() {
        return puntajeMax;
    }

    public void setPuntajeMax(int puntajeMax) {
        this.puntajeMax = puntajeMax;
    }

    public ParametrosActividad getParametros() {
        return parametros;
    }

    public void setParametros(ParametrosActividad parametros) {
        this.parametros = parametros;
    }
    
    
}

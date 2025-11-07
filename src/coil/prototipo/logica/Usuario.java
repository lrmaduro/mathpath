/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package coil.prototipo.logica;

import java.time.LocalDate;

public abstract class Usuario {
    
    protected String id_usuario;
    protected String username;
    protected String nombre_completo;
    protected String email;
    protected String password;
    protected LocalDate fechaNacimiento;


    public Usuario(String id_usuario, String username, String nombre_completo, String email, String password) {
        this.id_usuario = id_usuario;
        this.username = username;
        this.nombre_completo = nombre_completo;
        this.email = email;
        this.password = password;
    }
    
    
    
    public String getId_usuario() {
        return id_usuario;
    }

    public String getUsername() {
        return username;
    }

    public String getNombre_completo() {
        return nombre_completo;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public LocalDate getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setId_usuario(String id_usuario) {
        this.id_usuario = id_usuario;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setNombre_completo(String nombre_completo) {
        this.nombre_completo = nombre_completo;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setFechaNacimiento(LocalDate fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }
    
    
}

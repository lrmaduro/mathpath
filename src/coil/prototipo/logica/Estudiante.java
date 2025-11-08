/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package coil.prototipo.logica;

public class Estudiante extends Usuario {
    
    private final String codigoEstudiante;

    public Estudiante(String codigoEstudiante, String id_usuario, String username, String nombre_completo, String email, String password) {
        super(id_usuario, username, nombre_completo, email, password);
        this.codigoEstudiante = codigoEstudiante;
    }

    public String getCodigoEstudiante() {
        return codigoEstudiante;
    }
    
    public String getIdEstudiante() {
        return this.getId_usuario();
    }
    
}    



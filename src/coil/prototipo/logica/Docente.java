/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package coil.prototipo.logica;

/**
 *
 * @author Luis
 */
public class Docente extends Usuario{
    
    private String codigoDocente;

    public Docente(String codigoDocente, String id_usuario, String username, String nombre_completo, String email, String password) {
        super(id_usuario, username, nombre_completo, email, password);
        this.codigoDocente = codigoDocente;
    }
    
    
}

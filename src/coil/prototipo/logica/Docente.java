/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package coil.prototipo.logica;


public class Docente extends Usuario{

    private final String id_docente;    // PK de la tabla docentes
    private final String codigoDocente; // código del docente

  
    public Docente(String id_docente, String codigoDocente, String id_usuario, String username, String nombre_completo, String email, String password) {
        super(id_usuario, username, nombre_completo, email, password);
        this.id_docente = id_docente;
        this.codigoDocente = codigoDocente;
    }

    public String getCodigoDocente() {
        return codigoDocente;
    }

    public String getId_docente() {
        return id_docente;
    }
}

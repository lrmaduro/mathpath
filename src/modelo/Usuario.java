/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

public class Usuario {
    
    private String id;
    private String nombre;
    private String usuario;
    private String password; // En un futuro esto debería estar encriptado
    private Rol rol;

    // Constructor
    public Usuario(String id, String nombre, String usuario, String password, Rol rol) {
        this.id = id;
        this.nombre = nombre;
        this.usuario = usuario;
        this.password = password;
        this.rol = rol;
    }

    // Getters (métodos para obtener los datos)
    public String getId() { return id; }
    public String getNombre() { return nombre; }
    public String getUsuario() { return usuario; }
    public String getPassword() { return password; }
    public Rol getRol() { return rol; }
    
    // (Opcional: puedes añadir Setters si necesitas modificar los datos después)
    // public void setNombre(String nombre) { this.nombre = nombre; }
    // ...etc.
}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import java.util.ArrayList;
import java.util.List;
import modelo.Rol;
import modelo.Usuario;

public class UsuarioService {

    // Nuestra base de datos FALSA
    private List<Usuario> usuarios;

    public UsuarioService() {
        // Inicializamos la lista de usuarios
        usuarios = new ArrayList<>();
        
        // Creamos los usuarios de prueba
        usuarios.add(new Usuario("d001", "Profesor Jirafales", "profe", "123", Rol.DOCENTE));
        usuarios.add(new Usuario("e001", "Pepito Pérez", "pepito", "456", Rol.ESTUDIANTE));
        usuarios.add(new Usuario("e002", "Ana Martínez", "anita", "789", Rol.ESTUDIANTE));
    }

    /**
     * Valida el login y devuelve el Usuario si es exitoso, o null si falla.
     */
    public Usuario validarLogin(String username, String password) {
        // Recorre la lista de usuarios
        for (Usuario u : usuarios) {
            // Comprueba si el usuario y la contraseña coinciden
            if (u.getUsuario().equals(username) && u.getPassword().equals(password)) {
                return u; // ¡Éxito! Devolvemos el objeto Usuario completo
            }
        }
        return null; // Falló el login, no se encontró el usuario
    }
}

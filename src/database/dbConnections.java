/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package database;
import coil.prototipo.logica.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;
import java.util.HashMap;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
/**
 *
 * @author Luis
 */
public class dbConnections {
    
    private Connection db;
    private Gson json = new Gson();

    
    public static Connection getConnection() throws SQLException {
        String url = "jdbc:sqlite:mathpath.db;";

        return DriverManager.getConnection(url);
    }
    
    public dbConnections(String url) {
        
        String turl;
        if (url.equals("localhost"))
            turl = "jdbc:mysql://localhost:3306/MATHPATH";
        else
            turl = url;
        
        try {
            db = DriverManager.getConnection(turl);
            System.out.println("Connected Succesfully to database.");
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("Couldn't connect to the database.");
        }
    }
    
    
    public Estudiante loginEstudiante(String username, String password) {
        
        // Esta consulta une 'usuarios' y 'estudiantes'
        String sql = "SELECT e.id_estudiante, u.username, u.password, u.nombre_completo, u.email, e.codigoEstudiante " +
                     "FROM usuarios u " +
                     "JOIN estudiantes e ON u.id_usuarios = e.id_usuario " +
                     "WHERE u.username = ? AND u.password = ?";
        
        try {
            PreparedStatement stmt = db.prepareStatement(sql);
            stmt.setString(1, username);
            stmt.setString(2, password);
            ResultSet rs = stmt.executeQuery();

            // ¿Se encontró una coincidencia?
            if (rs.next()) {
                System.out.println("Login Estudiante Exitoso.");
                // Obtenemos todos los datos
                String id = rs.getString("id_estudiante");
                String nombre = rs.getString("nombre_completo");
                String email = rs.getString("email");
                String codigo = rs.getString("codigoEstudiante");
                
                // ¡Creamos y devolvemos el objeto Estudiante completo!
                return new Estudiante(codigo, id, username, nombre, email, password);
            }

        } catch (SQLException e){
            e.printStackTrace();
        }
        
        // Si no se encontró o hubo un error, devuelve null
        System.out.println("Login Estudiante Fallido.");
        return null; 
    }
    
    public Docente loginDocente(String username, String password) {
        
    // Consulta explícita: seleccionamos columnas clave con alias para evitar dependencias del nombre exacto
    // Asumimos que la columna de PK en 'usuarios' es 'id_usuarios' (como se usa en otras consultas del proyecto)
    String sql = "SELECT " +
             "d.id_docente AS d_id_docente, d.id_usuario AS d_id_usuario, d.codigoDocente AS d_codigo, " +
             "u.id_usuarios AS u_id_usuarios, u.username AS u_username, u.nombre_completo AS u_nombre, u.email AS u_email " +
             "FROM usuarios u " +
             "JOIN docentes d ON d.id_usuario = u.id_usuarios " +
             "WHERE u.username = ? AND u.password = ?";
        
        try {
            PreparedStatement stmt = db.prepareStatement(sql);
            stmt.setString(1, username);
            stmt.setString(2, password);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                System.out.println("Login Docente Exitoso.");
                // Leemos los alias que pedimos
                String idDocente = rs.getString("d_id_docente");
                String docente_id_usuario = rs.getString("d_id_usuario");
                String usuario_id_usuarios = rs.getString("u_id_usuarios");
                String nombre = rs.getString("u_nombre");
                String email = rs.getString("u_email");
                String codigo = rs.getString("d_codigo");

                // Decide qué valor usar como id_usuario (prioriza u_id_usuarios, si no existe usa la referencia en docentes)
                String idUsuario = usuario_id_usuarios != null ? usuario_id_usuarios : docente_id_usuario;

                // Si idDocente viene vacío intenta usar el valor de la tabla docentes (d_id_docente)
                if (idDocente == null) idDocente = rs.getString("d_id_docente");

                System.out.println("Parsed docente ids: idDocente=" + idDocente + ", idUsuario=" + idUsuario + ", codigo=" + codigo);

                return new Docente(idDocente, codigo, idUsuario, username, nombre, email, password);
            }

        } catch (SQLException e){
            e.printStackTrace();
        }
        
        System.out.println("Login Docente Fallido.");
        return null;
    }
    
    //No se usa
    public boolean nuevoProfesor(String username, String password) {
        try {
            PreparedStatement stmt = db.prepareStatement("INSERT INTO profesores (username, password) VALUES (?, ?)");
            stmt.setString(1, username);
            stmt.setString(2, password);
            
            stmt.executeUpdate();
            System.out.println("Profesor insertado");
            return true;
        } catch (SQLException e){
            e.printStackTrace();
        }
        return false;
    }
    
    //No se usa
    public boolean nuevoTema(String nombre, String descripcion) {
        try {
            PreparedStatement stmt = db.prepareStatement("INSERT INTO tema (nombre, descripcion) VALUES (?, ?)");
            stmt.setString(1, nombre);
            stmt.setString(2, descripcion);
            
            stmt.executeUpdate();
            System.out.println("Tema creado");
            return true;
           
        } catch (SQLException e){
            e.printStackTrace();
        }
        return false;
    }
    
    //No se usa
    public boolean nuevoEjercicio(String id_tema, String enunciado, String respuestas, int pos_respuesta_correcta) {
        try {
            PreparedStatement stmt = db.prepareStatement("INSERT INTO ejercicio (id_tema, enunciado, respuestas, pos_respuesta_corr) VALUES (?, ?, ?, ?)");
            stmt.setString(1, id_tema);
            stmt.setString(2, enunciado);
            stmt.setString(3, respuestas);
            stmt.setString(4, String.valueOf(pos_respuesta_correcta));
            stmt.executeUpdate();
            System.out.println("Ejercicio creado");
            return true;
           
        } catch (SQLException e){
            e.printStackTrace();
        }
        return false;
    }
    
    //No se usa
    public boolean nuevaAula(String id_profesor, String nombre) {
        try {
            PreparedStatement stmt = db.prepareStatement("INSERT INTO aula (id_profesor, nombre) VALUES (?, ?)");
            stmt.setString(1, id_profesor);
            stmt.setString(2, nombre);
            stmt.executeUpdate();
            System.out.println("Ejercicio creado");
            return true;
           
        } catch (SQLException e){
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Crea una nueva aula incluyendo descripción. Intenta usar la columna
     * 'id_docente' (si existe) o 'id_profesor' como fallback para compatibilidad.
     */
    public String nuevaAulaConDescripcion(String id_docente, String nombre, String descripcion) {
        // Intentar insertar sin especificar id_aula; la tabla puede generar el id si está definida como AUTOINCREMENT
        String sql1 = "INSERT INTO aula (id_docente, nombre, descripcion) VALUES (?, ?, ?)";
        try (PreparedStatement stmt = db.prepareStatement(sql1)) {
            stmt.setString(1, id_docente);
            stmt.setString(2, nombre);
            stmt.setString(3, descripcion);
            int affected = stmt.executeUpdate();
            if (affected > 0) {
                try (Statement s = db.createStatement(); ResultSet rs = s.executeQuery("SELECT last_insert_rowid();")) {
                    if (rs.next()) {
                        String id = rs.getString(1);
                        System.out.println("Aula creada (id_docente) con id_aula=" + id);
                        return id;
                    }
                }
            }
        } catch (SQLException ex) {
            System.out.println("Inserción con id_docente falló, intentando id_profesor: " + ex.getMessage());
        }

        // Fallback: intentar con id_profesor
        String sql2 = "INSERT INTO aula (id_profesor, nombre, descripcion) VALUES (?, ?, ?)";
        try (PreparedStatement stmt = db.prepareStatement(sql2)) {
            stmt.setString(1, id_docente);
            stmt.setString(2, nombre);
            stmt.setString(3, descripcion);
            int affected = stmt.executeUpdate();
            if (affected > 0) {
                try (Statement s = db.createStatement(); ResultSet rs = s.executeQuery("SELECT last_insert_rowid();")) {
                    if (rs.next()) {
                        String id = rs.getString(1);
                        System.out.println("Aula creada (id_profesor) con id_aula=" + id);
                        return id;
                    }
                }
            }
        } catch (SQLException ex) {
            System.out.println("Inserción con id_profesor también falló: " + ex.getMessage());
            ex.printStackTrace();
        }

        return null;
    }
    
    //No se usa
    public ArrayList<Actividad> listarActividades() {
        ArrayList<Actividad> listaActividades = new ArrayList<>();
        Actividad act;
        
        String sql = "SELECT * FROM evaluaciones e";

        try (PreparedStatement stmt = db.prepareStatement(sql)) { // (Usando try-with-resources)

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                int id_aula = Integer.parseInt(rs.getString("id_salon"));
                String descripcion = rs.getString("descripcion");
                int id_eval = Integer.parseInt(rs.getString("id_evaluacion"));
                String titulo = rs.getString("titulo");
                String fecha = rs.getString("fecha");
                
                act = new Actividad();
                act.setDescripcion(descripcion);
                act.setId_actividad(String.valueOf(id_eval));
                listaActividades.add(act);
            }

        } catch (SQLException e){
            e.printStackTrace();
        }

        return listaActividades;
    }   
    
    //No se usa
    public boolean nuevaEvaluacion(String id_tema, String id_salon, String titulo, String descripcion) {
        try {
            PreparedStatement stmt = db.prepareStatement("INSERT INTO evaluaciones (id_tema, id_salon, titulo, descripcion) VALUES (?, ?, ?, ?)");
            stmt.setString(1, id_tema);
            stmt.setString(2, id_salon);
            stmt.setString(3, titulo);
            stmt.setString(4, descripcion);
            stmt.executeUpdate();
            System.out.println("Evaluación creada");
            return true;
           
        } catch (SQLException e){
            e.printStackTrace();
        }
        return false;
    }
    
    //No se usa
    public boolean nuevoEjercicioEval(String id_eval, String id_ejercicio) {
        try {
            PreparedStatement stmt = db.prepareStatement("INSERT INTO ejercicios_evaluacion (id_ejercicio, id_evaluacion) VALUES (?, ?)");
            stmt.setString(1, id_eval);
            stmt.setString(2, id_ejercicio);
            stmt.executeUpdate();
            System.out.println("Ejercicio en evaluación creado");
            return true;
           
        } catch (SQLException e){
            e.printStackTrace();
        }
        return false;
    }
    
    //No se usa
    public boolean nuevaNota(String id_estudiante, String id_evaluacion, String nota) {
        try {
            PreparedStatement stmt = db.prepareStatement("INSERT INTO notas (id_estudiante, id_evaluacion, nota) VALUES (?, ?, ?)");
            stmt.setString(1, id_estudiante);
            stmt.setString(2, id_evaluacion);
            stmt.setString(3, nota);
            stmt.executeUpdate();
            System.out.println("nota creada");
            return true;
           
        } catch (SQLException e){
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Actualiza nombre y descripción de un aula por id_aula.
     */
    public boolean updateAula(String idAula, String nombre, String descripcion) {
        String sql = "UPDATE aula SET nombre = ?, descripcion = ? WHERE id_aula = ?";
        try (PreparedStatement ps = db.prepareStatement(sql)) {
            ps.setString(1, nombre);
            ps.setString(2, descripcion);
            ps.setString(3, idAula);
            return ps.executeUpdate() > 0;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
    }

    /**
     * Elimina un aula por id_aula.
     */
    public boolean deleteAula(String idAula) {
        String sql = "DELETE FROM aula WHERE id_aula = ?";
        try (PreparedStatement ps = db.prepareStatement(sql)) {
            ps.setString(1, idAula);
            return ps.executeUpdate() > 0;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
    }
    
    public ArrayList<Aula> listarAulas(int id_estudiante) {
        System.out.println(">>> DB: Buscando aulas para estudiante ID: " + id_estudiante); // <-- AÑADE ESTO

        ArrayList<Aula> listaAulas = new ArrayList<>();

        String sql = "SELECT a.id_aula, a.nombre FROM aula a " +
                     "JOIN inscripciones i ON a.id_aula = i.id_aula " +
                     "WHERE i.id_estudiante = ?";

        try (PreparedStatement stmt = db.prepareStatement(sql)) { // (Usando try-with-resources)
            stmt.setInt(1, id_estudiante);

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                String id = rs.getString("id_aula");
                String nombre = rs.getString("nombre");
                System.out.println(">>> DB: Encontrada aula: " + nombre + " (ID: " + id + ")"); // <-- AÑADE ESTO

                Aula aula = new Aula(id, nombre);
                listaAulas.add(aula);
            }

        } catch (SQLException e){
            e.printStackTrace();
        }

        System.out.println(">>> DB: Devolviendo " + listaAulas.size() + " aulas."); // <-- AÑADE ESTO
        return listaAulas;
    }

    
    public ArrayList<Aula> listarAulasDocente(String id_docente) {
        ArrayList<Aula> listaAulas = new ArrayList<>();

        String sql = "SELECT id_aula, nombre FROM aula WHERE id_docente = ?";

        try (PreparedStatement stmt = db.prepareStatement(sql)) {
            stmt.setString(1, id_docente);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                String id = rs.getString("id_aula");
                String nombre = rs.getString("nombre");
                Aula aula = new Aula(id, nombre);
                listaAulas.add(aula);
            }

        } catch (SQLException e){
            e.printStackTrace();
        }

        return listaAulas;
    }
    
    //No se usa
    public ListaTemas listarTemas() {
        ArrayList<Tema> listaTemas = new ArrayList<>();
        Tema t;
        try {
            PreparedStatement stmt = db.prepareStatement("SELECT * FROM temas");
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                t = new Tema();
                t.setDescripcion(rs.getString("descripcion"));
                t.setNombre("nombre");
                t.setIdTema("id_tema");
                listaTemas.add(t);
            }
        
        } catch (SQLException e){
            e.printStackTrace();
        }
        
        return new ListaTemas(listaTemas);
    }
    
    //No se usa
    public ListaPreguntas listarEjercicios(String id_tema) {
        ArrayList<Pregunta> listaEjer = new ArrayList<>();
        ArrayList<Respuesta> listaRespuesta = new ArrayList<>();
        ArrayList<String> respuestas;
        boolean resCorrecta;
        Type listType;
        Pregunta ejercicio;
        try {
            PreparedStatement stmt = db.prepareStatement("SELECT * FROM ejercicio WHERE id_tema = ?");
            stmt.setString(1, id_tema);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                listType = new TypeToken<ArrayList<String>>(){}.getType();
                respuestas = json.fromJson(rs.getString("respuestas"), listType);
                for (int i = 0; i < respuestas.size(); i++) {
                    if (i == Integer.parseInt(rs.getString("pos_respuesta_corr")))
                        resCorrecta = true;
                    else
                        resCorrecta = false;
                    listaRespuesta.add(new Respuesta(respuestas.get(i), resCorrecta));
                }
                ejercicio = new Pregunta();
                ejercicio.setEnunciado(rs.getString("enunciado"));
                ejercicio.setOpciones(listaRespuesta);
                ejercicio.setRespuestaCorrecta();
                ejercicio.setId_pregunta(Integer.parseInt(rs.getString("id_ejercicio")));
                ejercicio.setPosicionRespuestaCorrecta(Integer.parseInt(rs.getString("pos_respuesta_corr")));
                listaEjer.add(ejercicio);
                listaRespuesta.clear();
            }
        
        } catch (SQLException e){
            e.printStackTrace();
        }
        
        return new ListaPreguntas(listaEjer);
    }
    
    public ListaNotas listarNotas(String id_est) {
        ArrayList<Nota> listaNotas = new ArrayList<>();
        Nota nota;
        try {
            PreparedStatement stmt = db.prepareStatement("SELECT * FROM notas WHERE id_estudiante = ?");
            stmt.setString(1, id_est);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                nota = new Nota();
                nota.setId_estudiante(Integer.parseInt(rs.getString("id_estudiante")));
                nota.setId_evaluacion(Integer.parseInt(rs.getString("id_evaluacion")));
                nota.setNota(Float.parseFloat(rs.getString("nota")));
                listaNotas.add(nota);
            }
        
        } catch (SQLException e){
            e.printStackTrace();
        }
        
        return new ListaNotas(listaNotas, Integer.parseInt(id_est));
    }
    
    public ListaNotas listarNotas() {
        ArrayList<Nota> listaNotas = new ArrayList<>();
        Nota nota;
        try {
            PreparedStatement stmt = db.prepareStatement("SELECT * FROM notas");
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                nota = new Nota();
                nota.setId_estudiante(Integer.parseInt(rs.getString("id_estudiante")));
                nota.setId_evaluacion(Integer.parseInt(rs.getString("id_evaluacion")));
                nota.setNota(Float.parseFloat(rs.getString("nota")));
                listaNotas.add(nota);
            }
        
        } catch (SQLException e){
            e.printStackTrace();
        }
        
        return new ListaNotas(listaNotas);
    }
    
    //No se usa
    public String buscarFechaNota(String id_est, String id_eval) {
        String fecha = null;
        
        try {
         PreparedStatement stmt = db.prepareStatement("SELECT e.fecha FROM evaluaciones e "+
                 "JOIN notas n ON e.id_evaluacion = n.id_evaluacion WHERE n.id_estudiante = ? AND n.id_evaluacion = ?");
            stmt.setString(1, id_est);
            stmt.setString(2, id_eval);

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                fecha = rs.getString("fecha");
            }
        } catch (SQLException e){
            e.printStackTrace();
        }
        return fecha;
    }

    public String buscarNombreEval(String id_eval) {
        String nombre = null;
        try {
            PreparedStatement stmt = db.prepareStatement("SELECT titulo FROM evaluaciones WHERE id_evaluacion = ?");
            stmt.setString(1, id_eval);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                nombre = rs.getString("titulo");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return nombre;
    }
    
    
    public static void main(String args[]){
        dbConnections db = new dbConnections("jdbc:sqlite:src/database/mathpath.db");
        db.loginDocente("profe", "789");
//        db.login("Luis", "1234", 2);
//
////        db.nuevoEstudiante("Luis", "1234", 0);
//        System.out.println(db.listarProfesores());
//        System.out.println(db.buscarEstudiante("Luis"));
//        System.out.println("");
        
    }
}

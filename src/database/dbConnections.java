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
/**
 *
 * @author Luis
 */
public class dbConnections {
    
    Connection db;
    
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
    
    public void sendStatement(String query) {
        List<String> results = null;
        try {
            PreparedStatement stmt = db.prepareStatement(query);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                Array rsArray;
                rsArray = rs.getArray("username");
                Object[] objectArray = (Object[]) rsArray.getArray();
                String[] stringArray = Arrays.copyOf(objectArray, objectArray.length, String[].class);
                results = new ArrayList<>(Arrays.asList(stringArray));
            }
        } catch (Exception e)
        {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        
        System.out.println("ArrayList from SQL: " + results);
//        return results;
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
        
        // Esta consulta une 'usuarios' y 'docentes'
        String sql = "SELECT * " +
                     "FROM usuarios u " +
                     "JOIN docentes d ON u.id_usuarios = d.id_usuario " +
                     "WHERE u.username = ? AND u.password = ?";
        
        try {
            PreparedStatement stmt = db.prepareStatement(sql);
            stmt.setString(1, username);
            stmt.setString(2, password);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                System.out.println("Login Docente Exitoso.");
                // Obtenemos todos los datos
                String id = rs.getString("id_usuario");
                String nombre = rs.getString("nombre_completo");
                String email = rs.getString("email");
                String codigo = rs.getString("codigoDocente");
                
                // ¡Creamos y devolvemos el objeto Docente completo!
                return new Docente(codigo, id, username, nombre, email, password);
            }

        } catch (SQLException e){
            e.printStackTrace();
        }
        
        System.out.println("Login Docente Fallido.");
        return null;
    }
    
    public boolean nuevoProfesor(String username, String password) {
        try {
            PreparedStatement stmt = db.prepareStatement("INSERT INTO main.profesores (username, password) VALUES (?, ?)");
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
    
    public boolean nuevoEjercicio(String id_tema, String enunciado, String respuestas, String respuesta_correcta) {
        try {
            PreparedStatement stmt = db.prepareStatement("INSERT INTO ejercicio (id_tema, enunciado, respuestas, respuesta_correcta) VALUES (?, ?, ?, ?)");
            stmt.setString(1, id_tema);
            stmt.setString(2, enunciado);
            stmt.setString(3, respuestas);
            stmt.setString(4, respuesta_correcta);
            stmt.executeUpdate();
            System.out.println("Ejercicio creado");
            return true;
           
        } catch (SQLException e){
            e.printStackTrace();
        }
        return false;
    }
    
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
    
    public ArrayList<HashMap<String, String>> listarEstudiantes(String id_aula) {
        ArrayList<HashMap<String, String>> listaEst = new ArrayList<>();
        HashMap<String, String> estudiante;
        try {
            PreparedStatement stmt = db.prepareStatement("SELECT * FROM estudiantes WHERE id_aula = ?");
            stmt.setString(1, id_aula);
            ResultSet rs = stmt.executeQuery();
            ResultSetMetaData headers = rs.getMetaData();
            int cantColumnas = headers.getColumnCount();
            while (rs.next()) {
                estudiante = new HashMap<>();
                for (int i = 1; i <= cantColumnas; i++) {
                    estudiante.put(headers.getColumnName(i), rs.getString(headers.getColumnName(i)));
                }
                listaEst.add(estudiante);
            }
        
        } catch (SQLException e){
            e.printStackTrace();
        }
        
        return listaEst;
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
    
    public ArrayList<HashMap<String, String>> listarTemas() {
        ArrayList<HashMap<String, String>> listaTemas = new ArrayList<>();
        HashMap<String, String> temas;
        try {
            PreparedStatement stmt = db.prepareStatement("SELECT * FROM temas");
            ResultSet rs = stmt.executeQuery();
            ResultSetMetaData headers = rs.getMetaData();
            int cantColumnas = headers.getColumnCount();
            while (rs.next()) {
                temas = new HashMap<>();
                for (int i = 1; i <= cantColumnas; i++) {
                    temas.put(headers.getColumnName(i), rs.getString(headers.getColumnName(i)));
                }
                listaTemas.add(temas);
            }
        
        } catch (SQLException e){
            e.printStackTrace();
        }
        
        return listaTemas;
    }
    
    public ArrayList<HashMap<String, String>> listarEjercicios(String id_tema) {
        ArrayList<HashMap<String, String>> listaEjer = new ArrayList<>();
        HashMap<String, String> ejercicio;
        try {
            PreparedStatement stmt = db.prepareStatement("SELECT * FROM ejercicio WHERE id_tema = ?");
            stmt.setString(1, id_tema);
            ResultSet rs = stmt.executeQuery();
            ResultSetMetaData headers = rs.getMetaData();
            int cantColumnas = headers.getColumnCount();
            while (rs.next()) {
                ejercicio = new HashMap<>();
                for (int i = 1; i <= cantColumnas; i++) {
                    ejercicio.put(headers.getColumnName(i), rs.getString(headers.getColumnName(i)));
                }
                listaEjer.add(ejercicio);
            }
        
        } catch (SQLException e){
            e.printStackTrace();
        }
        
        return listaEjer;
    }
    
    public ListaNotas listarNotas(String id_est) {
        ArrayList<Nota> listaNotas = new ArrayList<>();
        Nota nota;
        try {
            PreparedStatement stmt = db.prepareStatement("SELECT * FROM notas WHERE id_estudiante = ?");
            stmt.setString(1, id_est);
            ResultSet rs = stmt.executeQuery();
            ResultSetMetaData headers = rs.getMetaData();
            int cantColumnas = headers.getColumnCount();
            while (rs.next()) {
                nota = new Nota();
                for (int i = 1; i <= cantColumnas; i++) {
                    nota.setId_estudiante(Integer.parseInt(rs.getString("id_estudiante")));
                    nota.setId_evaluacion(Integer.parseInt(rs.getString("id_evaluacion")));
                    nota.setNota(Float.parseFloat(rs.getString("nota")));
                }
                listaNotas.add(nota);
            }
        
        } catch (SQLException e){
            e.printStackTrace();
        }
        
        return new ListaNotas(listaNotas, Integer.parseInt(id_est));
    }
    
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

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package database;
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
    
    public boolean login(String username, String password, int tipo) {
        String tabla;
        if (tipo == 1) 
            tabla = "profesores";
        else
            tabla = "estudiantes";
        
        try {
            PreparedStatement stmt = db.prepareStatement("SELECT * FROM " + tabla + " WHERE username = ? AND password = ?");
            stmt.setString(1, username);
            stmt.setString(2, password);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                System.out.println("Login Succesful.");
                return true;
            }
            else
                System.out.println("Login failure.");
        } catch (Exception e)
        {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        
        return false;
        
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
    
    public boolean nuevoEstudiante(String username, String password, int id_aula) {
        try {
            PreparedStatement stmt = db.prepareStatement("INSERT INTO estudiantes (username, password, id_aula) VALUES (?, ?, ?)");
            stmt.setString(1, username);
            stmt.setString(2, password);
            stmt.setString(3, String.valueOf(id_aula));
            
            stmt.executeUpdate();
            System.out.println("Estudiante insertado");
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
    
    
    // Comienzo de los "Select"
    public ArrayList<HashMap<String, String>> listarProfesores() {
        ArrayList<HashMap<String, String>> listaProf = new ArrayList<>();
        HashMap<String, String> prof;
        try {
            PreparedStatement stmt = db.prepareStatement("SELECT * FROM profesores");
            ResultSet rs = stmt.executeQuery();
            ResultSetMetaData headers = rs.getMetaData();
            int cantColumnas = headers.getColumnCount();
            while (rs.next()) {
                prof = new HashMap<String, String>();
                for (int i = 1; i <= cantColumnas; i++) {
                    prof.put(headers.getColumnName(i), rs.getString(headers.getColumnName(i)));
                }
                listaProf.add(prof);
            }
        
        } catch (SQLException e){
            e.printStackTrace();
        }
        
        return listaProf;
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
    
    public ArrayList<HashMap<String, String>> listarAulas(String id_profesor) {
        ArrayList<HashMap<String, String>> listaAulas = new ArrayList<>();
        HashMap<String, String> aula;
        try {
            PreparedStatement stmt = db.prepareStatement("SELECT * FROM aula WHERE id_profesor = ?");
            stmt.setString(1, id_profesor);
            ResultSet rs = stmt.executeQuery();
            ResultSetMetaData headers = rs.getMetaData();
            int cantColumnas = headers.getColumnCount();
            while (rs.next()) {
                aula = new HashMap<>();
                for (int i = 1; i <= cantColumnas; i++) {
                    aula.put(headers.getColumnName(i), rs.getString(headers.getColumnName(i)));
                }
                listaAulas.add(aula);
            }
        
        } catch (SQLException e){
            e.printStackTrace();
        }
        
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
    
    public ArrayList<HashMap<String, String>> listarNotas(String id_est) {
        ArrayList<HashMap<String, String>> listaNotas = new ArrayList<>();
        HashMap<String, String> nota;
        try {
            PreparedStatement stmt = db.prepareStatement("SELECT * FROM notas WHERE id_estudiante = ?");
            stmt.setString(1, id_est);
            ResultSet rs = stmt.executeQuery();
            ResultSetMetaData headers = rs.getMetaData();
            int cantColumnas = headers.getColumnCount();
            while (rs.next()) {
                nota = new HashMap<>();
                for (int i = 1; i <= cantColumnas; i++) {
                    nota.put(headers.getColumnName(i), rs.getString(headers.getColumnName(i)));
                }
                listaNotas.add(nota);
            }
        
        } catch (SQLException e){
            e.printStackTrace();
        }
        
        return listaNotas;
    }
    
    public ArrayList<HashMap<String, String>> listarNotas(int id_eval) {
        ArrayList<HashMap<String, String>> listaNotas = new ArrayList<>();
        HashMap<String, String> nota;
        try {
            PreparedStatement stmt = db.prepareStatement("SELECT * FROM notas WHERE id_estudiante = ?");
            stmt.setString(1, String.valueOf(id_eval));
            ResultSet rs = stmt.executeQuery();
            ResultSetMetaData headers = rs.getMetaData();
            int cantColumnas = headers.getColumnCount();
            while (rs.next()) {
                nota = new HashMap<>();
                for (int i = 1; i <= cantColumnas; i++) {
                    nota.put(headers.getColumnName(i), rs.getString(headers.getColumnName(i)));
                }
                listaNotas.add(nota);
            }
        
        } catch (SQLException e){
            e.printStackTrace();
        }
        
        return listaNotas;
    }
    
    public ArrayList<HashMap<String, String>> listarEval(String id_salon) {
        ArrayList<HashMap<String, String>> listaEval = new ArrayList<>();
        HashMap<String, String> evaluacion;
        try {
            PreparedStatement stmt = db.prepareStatement("SELECT * FROM evaluaciones WHERE id_salon = ?");
            stmt.setString(1, id_salon);
            ResultSet rs = stmt.executeQuery();
            ResultSetMetaData headers = rs.getMetaData();
            int cantColumnas = headers.getColumnCount();
            while (rs.next()) {
                evaluacion = new HashMap<>();
                for (int i = 1; i <= cantColumnas; i++) {
                    evaluacion.put(headers.getColumnName(i), rs.getString(headers.getColumnName(i)));
                }
                listaEval.add(evaluacion);
            }
        
        } catch (SQLException e){
            e.printStackTrace();
        }
        
        return listaEval;
    }
    
        public ArrayList<HashMap<String, String>> listarEval(String id_salon, String id_tema) {
        ArrayList<HashMap<String, String>> listaEval = new ArrayList<>();
        HashMap<String, String> evaluacion;
        try {
            PreparedStatement stmt = db.prepareStatement("SELECT * FROM evaluaciones WHERE id_salon = ? AND id_tema = ?");
            stmt.setString(1, id_salon);
            stmt.setString(2, id_tema);
            ResultSet rs = stmt.executeQuery();
            ResultSetMetaData headers = rs.getMetaData();
            int cantColumnas = headers.getColumnCount();
            while (rs.next()) {
                evaluacion = new HashMap<>();
                for (int i = 1; i <= cantColumnas; i++) {
                    evaluacion.put(headers.getColumnName(i), rs.getString(headers.getColumnName(i)));
                }
                listaEval.add(evaluacion);
            }
        
        } catch (SQLException e){
            e.printStackTrace();
        }
        
        return listaEval;
    }
        
    public ArrayList<HashMap<String, String>> listarEval(int id_tema) {
        ArrayList<HashMap<String, String>> listaEval = new ArrayList<>();
        HashMap<String, String> evaluacion;
        try {
            PreparedStatement stmt = db.prepareStatement("SELECT * FROM evaluaciones WHERE id_tema = ?");
            stmt.setString(1, String.valueOf(id_tema));
            ResultSet rs = stmt.executeQuery();
            ResultSetMetaData headers = rs.getMetaData();
            int cantColumnas = headers.getColumnCount();
            while (rs.next()) {
                evaluacion = new HashMap<>();
                for (int i = 1; i <= cantColumnas; i++) {
                    evaluacion.put(headers.getColumnName(i), rs.getString(headers.getColumnName(i)));
                }
                listaEval.add(evaluacion);
            }
        
        } catch (SQLException e){
            e.printStackTrace();
        }
        
        return listaEval;
    }
    
    public HashMap<String, String> buscarProfesor(String username) {
        HashMap<String, String> prof = new HashMap<>();
        try {
            PreparedStatement stmt = db.prepareStatement("SELECT * FROM profesores WHERE username = ?");
            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();
            ResultSetMetaData headers = rs.getMetaData();
            int cantColumnas = headers.getColumnCount();
            if (rs.next()) {
                for (int i = 1; i <= cantColumnas; i++) {
                    prof.put(headers.getColumnName(i), rs.getString(headers.getColumnName(i)));
                }
            }
        
        } catch (SQLException e){
            e.printStackTrace();
        }
        
        return prof;
    }
    
    public HashMap<String, String> buscarProfesor(int id_prof) {
        HashMap<String, String> prof = new HashMap<>();
        try {
            PreparedStatement stmt = db.prepareStatement("SELECT * FROM profesores WHERE id_profesor = ?");
            stmt.setString(1, String.valueOf(id_prof));
            ResultSet rs = stmt.executeQuery();
            ResultSetMetaData headers = rs.getMetaData();
            int cantColumnas = headers.getColumnCount();
            if (rs.next()) {
                for (int i = 1; i <= cantColumnas; i++) {
                    prof.put(headers.getColumnName(i), rs.getString(headers.getColumnName(i)));
                }
            }
        
        } catch (SQLException e){
            e.printStackTrace();
        }
        
        return prof;
    }
    
    public HashMap<String, String> buscarEstudiante(int id_est) {
        HashMap<String, String> est = new HashMap<>();
        try {
            PreparedStatement stmt = db.prepareStatement("SELECT * FROM estudiantes WHERE id_estudiante = ?");
            stmt.setString(1, String.valueOf(id_est));
            ResultSet rs = stmt.executeQuery();
            ResultSetMetaData headers = rs.getMetaData();
            int cantColumnas = headers.getColumnCount();
            if (rs.next()) {
                for (int i = 1; i <= cantColumnas; i++) {
                    est.put(headers.getColumnName(i), rs.getString(headers.getColumnName(i)));
                }
            }
        
        } catch (SQLException e){
            e.printStackTrace();
        }
        
        return est;
    }
    
    public HashMap<String, String> buscarEstudiante(String username) {
        HashMap<String, String> est = new HashMap<>();
        try {
            PreparedStatement stmt = db.prepareStatement("SELECT * FROM estudiantes WHERE username = ?");
            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();
            ResultSetMetaData headers = rs.getMetaData();
            int cantColumnas = headers.getColumnCount();
            if (rs.next()) {
                for (int i = 1; i <= cantColumnas; i++) {
                    est.put(headers.getColumnName(i), rs.getString(headers.getColumnName(i)));
                }
            }
        
        } catch (SQLException e){
            e.printStackTrace();
        }
        
        return est;
    }
    
    public HashMap<String, String> buscarNota(String id_estudiante, String id_evaluacion) {
        HashMap<String, String> nota = new HashMap<>();
        try {
            PreparedStatement stmt = db.prepareStatement("SELECT * FROM notas WHERE id_estudiante = ? AND id_evaluacion = ?");
            stmt.setString(1, id_estudiante);
            stmt.setString(2, id_evaluacion);
            ResultSet rs = stmt.executeQuery();
            ResultSetMetaData headers = rs.getMetaData();
            int cantColumnas = headers.getColumnCount();
            if (rs.next()) {
                for (int i = 1; i <= cantColumnas; i++) {
                    nota.put(headers.getColumnName(i), rs.getString(headers.getColumnName(i)));
                }
            }
        
        } catch (SQLException e){
            e.printStackTrace();
        }
        
        return nota;
    }
    
    
    
    
    public static void main(String args[]){
        dbConnections db = new dbConnections("jdbc:sqlite:src/database/mathpath.db");
        db.login("prueba", "1234", 1);
        db.login("Luis", "1234", 2);

//        db.nuevoEstudiante("Luis", "1234", 0);
        System.out.println(db.listarProfesores());
        System.out.println(db.buscarEstudiante("Luis"));
        System.out.println("");
        
    }
}

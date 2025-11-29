package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import modelo.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class dbConnection {
    private static final String URL = "jdbc:mysql://3wp6kH6PxmqWFvn.root:Ii2nwMFyKwcx4w0u@gateway01.eu-central-1.prod.aws.tidbcloud.com:4000/mathpath?sslMode=VERIFY_IDENTITY";
    private static final String USER = "3wp6kH6PxmqWFvn.root";
    private static final String PASSWORD = "Ii2nwMFyKwcx4w0u";
    private Connection conn;

    public dbConnection() {
        try {
            this.conn = DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("No se pudo conectar a la base de datos");
            this.conn = null;
        }
    }

    public Connection getConnection() {
        return this.conn;
    }

    public boolean reconnect() {
        try {
            this.conn.close();
            this.conn = DriverManager.getConnection(URL, USER, PASSWORD);
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("No se pudo conectar a la base de datos");
            this.conn = null;
            return false;
        }
    }

    // Métodos para obtener datos
    public List<Usuario> getUsuarios() {
        try {
            String query = "SELECT * FROM usuario";
            Statement stmt = this.conn.createStatement();
            Rol rol;
            ResultSet rs = stmt.executeQuery(query);
            List<Usuario> usuarios = new ArrayList<>();
            while (rs.next()) {
                if (rs.getString("rol").toLowerCase().equals("docente")) {
                    rol = Rol.DOCENTE;
                } else {
                    rol = Rol.ESTUDIANTE;
                }
                Usuario usuario = new Usuario(rs.getString("id"), rs.getString("nombre"), rs.getString("usuario"),
                        rs.getString("password"), rol);
                usuarios.add(usuario);
            }
            return usuarios;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }

    public List<Nota> getNotasEstudiante(String idEstudiante) {
        List<Nota> notas;
        try {
            String query = "SELECT * FROM nota WHERE id_estudiante = ?";
            PreparedStatement stmt = this.conn.prepareStatement(query);
            stmt.setString(1, idEstudiante);
            ResultSet rs = stmt.executeQuery();
            notas = new ArrayList<>();
            while (rs.next()) {
                Nota nota = new Nota(rs.getString("id"), rs.getString("id_estudiante"), rs.getString("id_actividad"),
                        rs.getDouble("valor"), rs.getString("fecha"));
                notas.add(nota);
            }
            return notas;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }

    public List<Object[]> getNotasAula(String idAula) {
        List<Object[]> notas;
        try {
            String query = "SELECT e.nombre, n.valor, a.nombre FROM nota n JOIN actividad a ON n.id_actividad = a.id JOIN usuario e ON n.id_estudiante = e.id WHERE a.id_aula = ?";
            PreparedStatement stmt = this.conn.prepareStatement(query);
            stmt.setString(1, idAula);
            ResultSet rs = stmt.executeQuery();
            notas = new ArrayList<>();
            while (rs.next()) {
                notas.add(new Object[] { rs.getString("nombre"), rs.getDouble("valor"), rs.getString("nombre") });
            }
            return notas;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }

    public List<Object[]> getNotasActividad(String idActividad) {
        List<Object[]> notas;
        try {
            String query = "SELECT e.nombre, n.valor, a.nombre FROM nota n JOIN actividad a ON n.id_actividad = a.id JOIN usuario e ON n.id_estudiante = e.id WHERE a.id = ?";
            PreparedStatement stmt = this.conn.prepareStatement(query);
            stmt.setString(1, idActividad);
            ResultSet rs = stmt.executeQuery();
            notas = new ArrayList<>();
            while (rs.next()) {
                notas.add(new Object[] { rs.getString("nombre"), rs.getDouble("valor"), rs.getString("nombre") });
            }
            return notas;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }

    public List<Aula> getAulasDocente(String idDocente) {
        List<Aula> aulas;
        try {
            String query = "SELECT * FROM aula WHERE id_docente = ?";
            PreparedStatement stmt = this.conn.prepareStatement(query);
            stmt.setString(1, idDocente);
            ResultSet rs = stmt.executeQuery();
            aulas = new ArrayList<>();
            while (rs.next()) {
                Aula aula = new Aula(rs.getString("id"), rs.getString("nombre"), rs.getString("codigo"),
                        rs.getString("descripcion"), rs.getString("id_docente"));
                aulas.add(aula);
            }
            return aulas;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return new ArrayList<>();
    }

    public List<Aula> getAulasEstudiante(String idEstudiante) {
        List<Aula> aulas;
        try {
            String query = "SELECT * FROM aula a JOIN estudiante_aula ea on a.id = ea.id_aula WHERE ea.id_estudiante = ?";
            PreparedStatement stmt = this.conn.prepareStatement(query);
            stmt.setString(1, idEstudiante);
            ResultSet rs = stmt.executeQuery();
            aulas = new ArrayList<>();
            while (rs.next()) {
                Aula aula = new Aula(rs.getString("id"), rs.getString("nombre"), rs.getString("codigo"),
                        rs.getString("descripcion"), rs.getString("id_docente"));
                aulas.add(aula);
            }
            return aulas;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return new ArrayList<>();
    }

    public List<Aula> getAulas() {
        List<Aula> aulas;
        try {
            String query = "SELECT * FROM aula";
            Statement stmt = this.conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            aulas = new ArrayList<>();
            while (rs.next()) {
                Aula aula = new Aula(rs.getString("id"), rs.getString("nombre"), rs.getString("codigo"),
                        rs.getString("descripcion"), rs.getString("id_docente"));
                aulas.add(aula);
            }
            return aulas;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return new ArrayList<>();
    }

    public List<Actividad> getActividadesPorAula(String idAula) {
        List<Actividad> resultado;
        List<String> idEjercicios = new ArrayList<String>() {

        };
        try {
            // Obtener actividades por aula
            String query = "SELECT * FROM actividad WHERE id_aula = ?";
            String queryEjercicios = "SELECT id_ejercicio FROM actividad_ejercicio WHERE id_actividad = ?";
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            PreparedStatement stmt = this.conn.prepareStatement(query);
            stmt.setString(1, idAula);
            ResultSet rs = stmt.executeQuery();
            resultado = new ArrayList<>();
            while (rs.next()) {
                // Obtener ejercicios de la actividad
                PreparedStatement stmtEjercicios = this.conn.prepareStatement(queryEjercicios);
                stmtEjercicios.setString(1, rs.getString("id"));
                ResultSet rsEjercicios = stmtEjercicios.executeQuery();
                while (rsEjercicios.next()) {
                    idEjercicios.add(rsEjercicios.getString("id_ejercicio"));
                }
                Actividad actividad = new Actividad(rs.getString("id"), rs.getString("nombre"), rs.getString("id_aula"),
                        rs.getString("tema"), idEjercicios,
                        LocalDateTime.parse(rs.getString("fecha_limite"), formatter));
                resultado.add(actividad);
            }
            return resultado;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return new ArrayList<>();
    }

    public List<String> getActividadesHechas(String idEstudiante) {
        try {
            String query = "SELECT actividad.id FROM actividad JOIN nota ON actividad.id = nota.id_actividad WHERE id_estudiante = ?";
            PreparedStatement stmt = this.conn.prepareStatement(query);
            stmt.setString(1, idEstudiante);
            ResultSet rs = stmt.executeQuery();
            List<String> actividades = new ArrayList<>();
            while (rs.next()) {
                actividades.add(rs.getString("id"));
            }
            return actividades;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }

    public String getNombreAct(String idActividad) {
        try {
            String query = "SELECT nombre FROM actividad WHERE id = ?";
            PreparedStatement stmt = this.conn.prepareStatement(query);
            stmt.setString(1, idActividad);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getString("nombre");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "";
    }

    public List<Tema> getTemas() {
        List<Tema> temas;
        try {
            String query = "SELECT * FROM tema";
            PreparedStatement stmt = this.conn.prepareStatement(query);
            ResultSet rs = stmt.executeQuery();
            temas = new ArrayList<>();
            while (rs.next()) {
                Tema tema = new Tema(rs.getString("id"), rs.getString("nombre"));
                temas.add(tema);
            }
            return temas;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return new ArrayList<>();
    }

    public String getTemaNombre(String idTema) {
        try {
            String query = "SELECT nombre FROM tema WHERE id = ?";
            PreparedStatement stmt = this.conn.prepareStatement(query);
            stmt.setString(1, idTema);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getString("nombre");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "";
    }

    public Tema getTemaPorNombre(String nombre) {
        try {
            String query = "SELECT * FROM tema WHERE nombre = ?";
            PreparedStatement stmt = this.conn.prepareStatement(query);
            stmt.setString(1, nombre);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Tema(rs.getString("id"), rs.getString("nombre"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Ejercicio> getEjercicioDocente(String docenteId) {
        List<Ejercicio> ejercicios;
        try {
            String queryOpciones = "SELECT * FROM opcion_ejercicio WHERE id_ejercicio = ?";
            String query = "SELECT * FROM ejercicio WHERE id_docente = ? or id_docente = 90001";
            PreparedStatement stmt = this.conn.prepareStatement(query);
            stmt.setString(1, docenteId);
            ResultSet rs = stmt.executeQuery();
            ejercicios = new ArrayList<>();
            while (rs.next()) {
                PreparedStatement stmtOpciones = this.conn.prepareStatement(queryOpciones);
                stmtOpciones.setString(1, rs.getString("id"));
                ResultSet rsOpciones = stmtOpciones.executeQuery();
                List<String> opciones = new ArrayList<>();
                while (rsOpciones.next()) {
                    opciones.add(rsOpciones.getString("texto"));
                }
                Ejercicio ejercicio = new Ejercicio(rs.getString("id"), rs.getString("pregunta"),
                        opciones, rs.getString("clave_respuesta"),
                        rs.getString("id_tema"), rs.getString("tipo"),
                        rs.getString("retroalimentacion"), rs.getString("ruta_imagen"));
                ejercicios.add(ejercicio);
            }
            return ejercicios;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return new ArrayList<>();
    }

    public List<Ejercicio> getTodosLosEjercicios() {
        List<Ejercicio> ejercicios;
        try {
            String queryOpciones = "SELECT * FROM opcion_ejercicio WHERE id_ejercicio = ?";
            String query = "SELECT * FROM ejercicio";
            PreparedStatement stmt = this.conn.prepareStatement(query);
            ResultSet rs = stmt.executeQuery();
            ejercicios = new ArrayList<>();
            while (rs.next()) {
                PreparedStatement stmtOpciones = this.conn.prepareStatement(queryOpciones);
                stmtOpciones.setString(1, rs.getString("id"));
                ResultSet rsOpciones = stmtOpciones.executeQuery();
                List<String> opciones = new ArrayList<>();
                while (rsOpciones.next()) {
                    opciones.add(rsOpciones.getString("texto"));
                }
                Ejercicio ejercicio = new Ejercicio(rs.getString("id"), rs.getString("pregunta"),
                        opciones, rs.getString("clave_respuesta"),
                        rs.getString("id_tema"), rs.getString("tipo"),
                        rs.getString("retroalimentacion"), rs.getString("ruta_imagen"));
                ejercicios.add(ejercicio);
            }
            return ejercicios;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return new ArrayList<>();
    }

    public List<Ejercicio> getEjerciciosPorActividad(String actividadId) {
        try {
            String queryOpciones = "SELECT * FROM opcion_ejercicio WHERE id_ejercicio = ?";
            String query = "SELECT * FROM ejercicio JOIN actividad_ejercicio ON ejercicio.id = actividad_ejercicio.id_ejercicio WHERE actividad_ejercicio.id_actividad = ?";
            PreparedStatement stmt = this.conn.prepareStatement(query);
            stmt.setString(1, actividadId);
            ResultSet rs = stmt.executeQuery();
            List<Ejercicio> ejercicios = new ArrayList<>();
            while (rs.next()) {
                PreparedStatement stmtOpciones = this.conn.prepareStatement(queryOpciones);
                stmtOpciones.setString(1, rs.getString("id"));
                ResultSet rsOpciones = stmtOpciones.executeQuery();
                List<String> opciones = new ArrayList<>();
                while (rsOpciones.next()) {
                    opciones.add(rsOpciones.getString("texto"));
                }
                Ejercicio ejercicio = new Ejercicio(rs.getString("id"), rs.getString("pregunta"),
                        opciones, rs.getString("clave_respuesta"), rs.getString("id_tema"),
                        rs.getString("tipo"), rs.getString("retroalimentacion"),
                        rs.getString("ruta_imagen"));
                ejercicios.add(ejercicio);
            }
            return ejercicios;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }

    public List<Ejercicio> getEjerciciosPorTema(String idTema) {
        try {
            String queryOpciones = "SELECT * FROM opcion_ejercicio WHERE id_ejercicio = ?";
            String query = "SELECT * FROM ejercicio WHERE id_tema = ?";
            PreparedStatement stmt = this.conn.prepareStatement(query);
            stmt.setString(1, idTema);
            ResultSet rs = stmt.executeQuery();
            List<Ejercicio> ejercicios = new ArrayList<>();
            while (rs.next()) {
                PreparedStatement stmtOpciones = this.conn.prepareStatement(queryOpciones);
                stmtOpciones.setString(1, rs.getString("id"));
                ResultSet rsOpciones = stmtOpciones.executeQuery();
                List<String> opciones = new ArrayList<>();
                while (rsOpciones.next()) {
                    opciones.add(rsOpciones.getString("texto"));
                }
                Ejercicio ejercicio = new Ejercicio(rs.getString("id"), rs.getString("pregunta"),
                        opciones, rs.getString("clave_respuesta"), rs.getString("id_tema"),
                        rs.getString("tipo"), rs.getString("retroalimentacion"),
                        rs.getString("ruta_imagen"));
                ejercicios.add(ejercicio);
            }
            return ejercicios;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }

    public List<Usuario> getEstudiantesAula(String idAula) {
        List<Usuario> estudiantes;
        try {
            String query = "SELECT * FROM usuario JOIN estudiante_aula ea ON ea.id_estudiante = usuario.id WHERE ea.id_aula = ?";
            PreparedStatement stmt = this.conn.prepareStatement(query);
            stmt.setString(1, idAula);
            ResultSet rs = stmt.executeQuery();
            estudiantes = new ArrayList<>();
            while (rs.next()) {
                Usuario estudiante = new Usuario(rs.getString("id"), rs.getString("nombre"),
                        rs.getString("usuario"), rs.getString("password"), Rol.ESTUDIANTE);
                estudiantes.add(estudiante);
            }
            return estudiantes;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return new ArrayList<>();
    }

    // Métodos para insertar
    public boolean agregarUsuario(Usuario u) {
        try {
            String query = "INSERT INTO usuario (nombre, usuario, password, rol) VALUES (?, ?, ?, ?)";
            PreparedStatement stmt = this.conn.prepareStatement(query);
            stmt.setString(1, u.getNombre());
            stmt.setString(2, u.getUsuario());
            stmt.setString(3, u.getPassword());
            stmt.setString(4, u.getRol().toString());
            stmt.executeUpdate();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean inscribirseAAula(String idAula, String idEstudiante) {
        try {
            String query = "INSERT INTO estudiante_aula (id_aula, id_estudiante) VALUES (?, ?)";
            PreparedStatement stmt = this.conn.prepareStatement(query);
            stmt.setString(1, idAula);
            stmt.setString(2, idEstudiante);
            stmt.executeUpdate();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean removerEstudianteDeAula(String idAula, String idEstudiante) {
        try {
            String query = "DELETE FROM estudiante_aula WHERE id_aula = ? AND id_estudiante = ?";
            PreparedStatement stmt = this.conn.prepareStatement(query);
            stmt.setString(1, idAula);
            stmt.setString(2, idEstudiante);
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean agregarTema(String nombreTema) {
        try {
            String query = "INSERT INTO tema (nombre) VALUES (?)";
            PreparedStatement stmt = this.conn.prepareStatement(query);
            stmt.setString(1, nombreTema);
            stmt.executeUpdate();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean agregarAula(Aula a) {
        try {
            String query = "INSERT INTO aula (nombre, codigo, descripcion, id_docente) VALUES (?, ?, ?, ?)";
            PreparedStatement stmt = this.conn.prepareStatement(query);
            stmt.setString(1, a.getNombre());
            stmt.setString(2, a.getCodigo());
            stmt.setString(3, a.getDescripcion());
            stmt.setString(4, a.getIdDocente());
            stmt.executeUpdate();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean agregarNota(Nota n) {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            String query = "INSERT INTO nota (id_estudiante, id_actividad, valor, fecha) VALUES (?, ?, ?, ?)";
            PreparedStatement stmt = this.conn.prepareStatement(query);
            stmt.setString(1, n.getIdEstudiante());
            stmt.setString(2, n.getIdActividad());
            stmt.setDouble(3, n.getNota());
            stmt.setString(4, n.getFecha().format(formatter));
            stmt.executeUpdate();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean agregarActividad(Actividad a) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        try {
            String queryEjercicios = "INSERT INTO actividad_ejercicio (id_actividad, id_ejercicio) VALUES (?, ?)";
            String query = "INSERT INTO actividad (nombre, id_aula, tema, fecha_limite) VALUES (?, ?, ?, ?)";
            String queryId = "SELECT LAST_INSERT_ID()";
            PreparedStatement stmtID = this.conn.prepareStatement(queryId);
            PreparedStatement stmt = this.conn.prepareStatement(query);
            stmt.setString(1, a.getNombre());
            stmt.setString(2, a.getIdAula());
            stmt.setString(3, a.getTema());
            stmt.setString(4, a.getFechaLimite().format(formatter));
            stmt.executeUpdate();
            stmtID.executeQuery();
            if (stmtID.getResultSet().next()) {
                a.setId(stmtID.getResultSet().getString(1));
            }
            for (String idEjercicio : a.getIdEjercicios()) {
                PreparedStatement stmtEjercicios = this.conn.prepareStatement(queryEjercicios);
                stmtEjercicios.setString(1, a.getId());
                stmtEjercicios.setString(2, idEjercicio);
                stmtEjercicios.executeUpdate();
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean agregarEjercicio(Ejercicio ejercicio, String idDocente) {
        try {
            String query = "INSERT INTO ejercicio (pregunta, clave_respuesta, id_tema, tipo, ruta_imagen, retroalimentacion, id_docente) VALUES (?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement stmt = this.conn.prepareStatement(query);
            stmt.setString(1, ejercicio.getPregunta());
            stmt.setString(2, ejercicio.getClaveRespuesta());
            stmt.setString(3, ejercicio.getIdTema());
            System.out.println(ejercicio.getIdTema());
            stmt.setString(4, ejercicio.getTipo());
            stmt.setString(5, "");
            stmt.setString(6, ejercicio.getRetroalimentacion());
            stmt.setString(7, idDocente);
            stmt.executeUpdate();
            String queryId = "SELECT LAST_INSERT_ID()";
            PreparedStatement stmtId = this.conn.prepareStatement(queryId);
            ResultSet rs = stmtId.executeQuery();
            String queryOpciones = "INSERT INTO opcion_ejercicio (id_ejercicio, texto) VALUES (?, ?)";
            PreparedStatement stmtOpciones = this.conn.prepareStatement(queryOpciones);
            while (rs.next()) {
                stmtOpciones.setString(1, rs.getString(1));
            }
            for (String opcion : ejercicio.getOpciones()) {
                stmtOpciones.setString(2, opcion);
                stmtOpciones.executeUpdate();
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    // Métodos para eliminar
    public boolean eliminarActividad(String id) {
        try {
            String query = "DELETE FROM actividad WHERE id = ?";
            String queryEjercicios = "DELETE FROM actividad_ejercicio WHERE id_actividad = ?";
            PreparedStatement stmt = this.conn.prepareStatement(query);
            stmt.setString(1, id);
            stmt.executeUpdate();
            PreparedStatement stmtEjercicios = this.conn.prepareStatement(queryEjercicios);
            stmtEjercicios.setString(1, id);
            stmtEjercicios.executeUpdate();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean eliminarEjercicio(String id) {
        try {
            String query = "DELETE FROM ejercicio WHERE id = ?";
            String queryActividadEjercicio = "DELETE FROM actividad_ejercicio WHERE id_ejercicio = ?";
            PreparedStatement stmt = this.conn.prepareStatement(query);
            stmt.setString(1, id);
            stmt.executeUpdate();
            PreparedStatement stmtActividadEjercicio = this.conn.prepareStatement(queryActividadEjercicio);
            stmtActividadEjercicio.setString(1, id);
            stmtActividadEjercicio.executeUpdate();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    // Métodos para actualizar
    public boolean actualizarActividad(Actividad actividadActualizada) {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
            String query = "UPDATE actividad SET nombre = ?, id_aula = ?, tema = ?, fecha_limite = ? WHERE id = ?";
            String queryEjercicios = "DELETE FROM actividad_ejercicio WHERE id_actividad = ?";
            String queryEjerciciosInsert = "INSERT INTO actividad_ejercicio (id_actividad, id_ejercicio) VALUES (?, ?)";
            PreparedStatement stmt = this.conn.prepareStatement(query);
            stmt.setString(1, actividadActualizada.getNombre());
            stmt.setString(2, actividadActualizada.getIdAula());
            stmt.setString(3, actividadActualizada.getTema());
            stmt.setString(4, actividadActualizada.getFechaLimite().format(formatter));
            stmt.setString(5, actividadActualizada.getId());
            stmt.executeUpdate();
            PreparedStatement stmtEjercicios = this.conn.prepareStatement(queryEjercicios);
            stmtEjercicios.setString(1, actividadActualizada.getId());
            stmtEjercicios.executeUpdate();
            for (String idEjercicio : actividadActualizada.getIdEjercicios()) {
                PreparedStatement stmtEjerciciosInsert = this.conn.prepareStatement(queryEjerciciosInsert);
                stmtEjerciciosInsert.setString(1, actividadActualizada.getId());
                stmtEjerciciosInsert.setString(2, idEjercicio);
                stmtEjerciciosInsert.executeUpdate();
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean actualizarUsuario(Usuario usuarioActualizado) {
        try {
            String query = "UPDATE usuario SET nombre = ?, usuario = ?, password = ? WHERE id = ?";
            PreparedStatement stmt = this.conn.prepareStatement(query);
            stmt.setString(1, usuarioActualizado.getNombre());
            stmt.setString(2, usuarioActualizado.getUsuario());
            stmt.setString(3, usuarioActualizado.getPassword());
            stmt.setString(4, usuarioActualizado.getId());
            stmt.executeUpdate();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}

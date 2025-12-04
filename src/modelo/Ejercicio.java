package modelo;

import java.util.List;

import javax.swing.ImageIcon;

public class Ejercicio {

    private String id;
    private String pregunta;
    private List<String> opciones;
    private String claveRespuesta;
    private String idTema;
    private String tipo;
    private ImageIcon imagen;
    // NUEVO CAMPO
    private String retroalimentacion;

    // Constructor actualizado
    public Ejercicio(String id, String pregunta, List<String> opciones,
            String claveRespuesta, String idTema, String tipo, String retroalimentacion) {
        this.id = id;
        this.pregunta = pregunta;
        this.opciones = opciones;
        this.claveRespuesta = claveRespuesta;
        this.idTema = idTema;
        this.tipo = tipo;
        this.retroalimentacion = retroalimentacion;
    }

    public Ejercicio() {
    }

    public Ejercicio(String id, String pregunta, List<String> opciones,
            String claveRespuesta, String idTema, String tipo,
            String retroalimentacion, String rutaImagen) {
        this.id = id;
        this.pregunta = pregunta;
        this.opciones = opciones;
        this.claveRespuesta = claveRespuesta;
        this.idTema = idTema;
        this.tipo = tipo;
        this.retroalimentacion = retroalimentacion;
        this.imagen = cargarImagen(rutaImagen); // Cargar la imagen
    }

    private ImageIcon cargarImagen(String rutaImagen) {
        java.net.URL imgURL = getClass().getResource(rutaImagen);
        if (imgURL != null) {
            return new ImageIcon(imgURL);
        } else {
            System.err.println("No se encontró la imagen: " + rutaImagen);
            return null;
        }
    }

    // Getters
    public String getId() {
        return id;
    }

    public String getPregunta() {
        return pregunta;
    }

    public List<String> getOpciones() {
        return opciones;
    }

    public String getClaveRespuesta() {
        return claveRespuesta;
    }

    public String getIdTema() {
        return idTema;
    }

    public String getTipo() {
        return tipo;
    }

    public String getRetroalimentacion() {
        return retroalimentacion;
    } // Nuevo Getter

    public ImageIcon getImagen() {
        return imagen;
    }
}
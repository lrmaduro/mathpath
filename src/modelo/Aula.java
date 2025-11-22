package modelo;

public class Aula {
    
    private String id;
    private String nombre;
    private String codigo; 
    private String descripcion;
    
    // --- NUEVO CAMPO DE PROPIEDAD ---
    private String idDocente; 

    // Constructor actualizado
    public Aula(String id, String nombre, String codigo, String descripcion, String idDocente) {
        this.id = id;
        this.nombre = nombre;
        this.codigo = codigo;
        this.descripcion = descripcion;
        this.idDocente = idDocente;
    }

    // Getters
    public String getId() { return id; }
    public String getNombre() { return nombre; }
    public String getCodigo() { return codigo; }
    public String getDescripcion() { return descripcion; }
    public String getIdDocente() { return idDocente; } // Nuevo Getter
    
    // Setter necesario para asignarlo después de crear el objeto en el diálogo
    public void setIdDocente(String idDocente) {
        this.idDocente = idDocente;
    }
}
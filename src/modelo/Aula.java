package modelo;

public class Aula {
    
    private String id;
    private String nombre;
    private String codigo; // El código para que se unan los estudiantes
    private String descripcion;
    // (En el futuro, aquí irá un private Usuario docente;)

    public Aula(String id, String nombre, String codigo, String descripcion) {
        this.id = id;
        this.nombre = nombre;
        this.codigo = codigo;
        this.descripcion = descripcion;
    }

    // Getters
    public String getId() { return id; }
    public String getNombre() { return nombre; }
    public String getCodigo() { return codigo; }
    public String getDescripcion() { return descripcion; }
}
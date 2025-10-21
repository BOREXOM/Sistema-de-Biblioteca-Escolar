package negocio;

public class Alumno {
    private int id;
    private String nombre;
    private String matricula;
    private int usuarioId;

    public Alumno(int id, String nombre, String matricula, int usuarioId) {
        this.id = id;
        this.nombre = nombre;
        this.matricula = matricula;
        this.usuarioId = usuarioId;
    }

    // Getters y setters
    public int getId() { return id; }
    public String getNombre() { return nombre; }
    public String getMatricula() { return matricula; }
    public int getUsuarioId() { return usuarioId; }
}
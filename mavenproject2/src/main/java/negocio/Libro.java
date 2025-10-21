package negocio;

public class Libro {
    private int id;
    private String titulo;
    private String autor;
    private String isbn;
    private boolean disponible;

    public Libro(int id, String titulo, String autor, String isbn, boolean disponible) {
        this.id = id;
        this.titulo = titulo;
        this.autor = autor;
        this.isbn = isbn;
        this.disponible = disponible;
    }

    // Getters y setters
    public int getId() { return id; }
    public String getTitulo() { return titulo; }
    public String getAutor() { return autor; }
    public String getIsbn() { return isbn; }
    public boolean isDisponible() { return disponible; }
}

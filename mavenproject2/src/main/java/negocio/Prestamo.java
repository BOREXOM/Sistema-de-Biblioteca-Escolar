package negocio;

import java.util.Date;

public class Prestamo {
    private int id;
    private int alumnoId;
    private int libroId;
    private Date fechaPrestamo;
    private Date fechaDevolucion;
    private double multa;

    public Prestamo(int id, int alumnoId, int libroId, Date fechaPrestamo, Date fechaDevolucion, double multa) {
        this.id = id;
        this.alumnoId = alumnoId;
        this.libroId = libroId;
        this.fechaPrestamo = fechaPrestamo;
        this.fechaDevolucion = fechaDevolucion;
        this.multa = multa;
    }

    // Getters y setters
    public int getId() { return id; }
    public int getAlumnoId() { return alumnoId; }
    public int getLibroId() { return libroId; }
    public Date getFechaPrestamo() { return fechaPrestamo; }
    public Date getFechaDevolucion() { return fechaDevolucion; }
    public double getMulta() { return multa; }
}   
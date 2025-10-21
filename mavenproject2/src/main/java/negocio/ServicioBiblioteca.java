package negocio;

import datos.*;
import java.sql.SQLException;
import java.util.List;

public class ServicioBiblioteca {
    private UsuarioDAO usuarioDAO = new UsuarioDAO();
    private AlumnoDAO alumnoDAO = new AlumnoDAO();
    private LibroDAO libroDAO = new LibroDAO();
    private PrestamoDAO prestamoDAO = new PrestamoDAO();

    public Usuario login(String email, String password, String mac) throws SQLException {
        return usuarioDAO.login(email, password, mac);
    }

    public void registrarAlumno(Alumno alumno) throws SQLException {
        alumnoDAO.insertar(alumno);
    }

    public void registrarLibro(Libro libro) throws SQLException {
        libroDAO.insertar(libro);
    }

    public void registrarPrestamo(Prestamo prestamo) throws SQLException {
        prestamoDAO.registrarPrestamo(prestamo);
    }

    public void registrarDevolucion(int prestamoId, java.util.Date fechaDevolucion) throws SQLException {
        prestamoDAO.registrarDevolucion(prestamoId, fechaDevolucion);
    }

    public List<Alumno> listarAlumnos() throws SQLException {
        return alumnoDAO.listar();
    }

    public List<Libro> listarLibrosDisponibles() throws SQLException {
        return libroDAO.listarDisponibles();
    }

    public List<Prestamo> listarPrestamosPorAlumno(int alumnoId) throws SQLException {
        return prestamoDAO.listarPorAlumno(alumnoId);
    }
}
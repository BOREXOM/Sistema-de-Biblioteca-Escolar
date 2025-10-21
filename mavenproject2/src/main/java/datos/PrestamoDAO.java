package datos;

import negocio.Prestamo;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PrestamoDAO {
    private Connection conn = ConexionDB.getInstancia().getConexion();

    public void registrarPrestamo(Prestamo prestamo) throws SQLException {
        conn.setAutoCommit(false);
        try {
            String sql = "INSERT INTO prestamos (alumno_id, libro_id, fecha_prestamo) VALUES (?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, prestamo.getAlumnoId());
            stmt.setInt(2, prestamo.getLibroId());
            stmt.setDate(3, new java.sql.Date(prestamo.getFechaPrestamo().getTime()));
            stmt.executeUpdate();

            // Marcar libro como no disponible
            new LibroDAO().actualizarDisponibilidad(prestamo.getLibroId(), false);

            conn.commit();
        } catch (SQLException e) {
            conn.rollback();
            throw e;
        } finally {
            conn.setAutoCommit(true);
        }
    }

    public void registrarDevolucion(int prestamoId, java.util.Date fechaDevolucion) throws SQLException {
        conn.setAutoCommit(false);
        try {
            // Calcular multa (usando BibliotecaUtilidades)
            double multa = negocio.BibliotecaUtilidades.calcularMulta(fechaDevolucion, new java.util.Date()); // Fecha actual como límite

            String sql = "UPDATE prestamos SET fecha_devolucion = ?, multa = ? WHERE id = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setDate(1, new java.sql.Date(fechaDevolucion.getTime()));
            stmt.setDouble(2, multa);
            stmt.setInt(3, prestamoId);
            stmt.executeUpdate();

            // Obtener libro_id y marcar como disponible
            String sqlLibro = "SELECT libro_id FROM prestamos WHERE id = ?";
            PreparedStatement stmtLibro = conn.prepareStatement(sqlLibro);
            stmtLibro.setInt(1, prestamoId);
            ResultSet rs = stmtLibro.executeQuery();
            if (rs.next()) {
                new LibroDAO().actualizarDisponibilidad(rs.getInt("libro_id"), true);
            }

            conn.commit();
        } catch (SQLException e) {
            conn.rollback();
            throw e;
        } finally {
            conn.setAutoCommit(true);
        }
    }

    public List<Prestamo> listarPorAlumno(int alumnoId) throws SQLException {
        List<Prestamo> prestamos = new ArrayList<>();
        String sql = "SELECT * FROM prestamos WHERE alumno_id = ?";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setInt(1, alumnoId);
        ResultSet rs = stmt.executeQuery();
        while (rs.next()) {
            prestamos.add(new Prestamo(rs.getInt("id"), rs.getInt("alumno_id"), rs.getInt("libro_id"), rs.getDate("fecha_prestamo"), rs.getDate("fecha_devolucion"), rs.getDouble("multa")));
        }
        return prestamos;
    }
}
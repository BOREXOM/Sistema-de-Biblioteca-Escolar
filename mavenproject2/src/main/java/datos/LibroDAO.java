// LibroDAO.java (DAO para libros)
package datos;
import negocio.Libro;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
public class LibroDAO {
    private Connection conn = ConexionDB.getInstancia().getConexion();
    public void insertar(Libro libro) throws SQLException {
        String sql = "INSERT INTO libros (titulo, autor, isbn, disponible) VALUES (?, ?, ?, ?)";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setString(1, libro.getTitulo());
        stmt.setString(2, libro.getAutor());
        stmt.setString(3, libro.getIsbn());
        stmt.setBoolean(4, libro.isDisponible());
        stmt.executeUpdate();
    }
    public List<Libro> listarDisponibles() throws SQLException {
        List<Libro> libros = new ArrayList<>();
        String sql = "SELECT * FROM libros WHERE disponible = TRUE";
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(sql);
        while (rs.next()) {
            libros.add(new Libro(rs.getInt("id"), rs.getString("titulo"), rs.getString("autor"), rs.getString("isbn"), rs.getBoolean("disponible")));
        }
        return libros;
    }

    // Listar todos los libros (para edición por roles)
    public List<Libro> listarTodos() throws SQLException {
        List<Libro> libros = new ArrayList<>();
        String sql = "SELECT * FROM libros";
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(sql);
        while (rs.next()) {
            libros.add(new Libro(rs.getInt("id"), rs.getString("titulo"), rs.getString("autor"), rs.getString("isbn"), rs.getBoolean("disponible")));
        }
        return libros;
    }

    public void actualizarDisponibilidad(int id, boolean disponible) throws SQLException {
        String sql = "UPDATE libros SET disponible = ? WHERE id = ?";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setBoolean(1, disponible);
        stmt.setInt(2, id);
        stmt.executeUpdate();
    }

    // Actualizar campos del libro (sin tocar disponibilidad)
    public void actualizar(Libro libro) throws SQLException {
        String sql = "UPDATE libros SET titulo = ?, autor = ?, isbn = ? WHERE id = ?";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setString(1, libro.getTitulo());
        stmt.setString(2, libro.getAutor());
        stmt.setString(3, libro.getIsbn());
        stmt.setInt(4, libro.getId());
        stmt.executeUpdate();
    }
}

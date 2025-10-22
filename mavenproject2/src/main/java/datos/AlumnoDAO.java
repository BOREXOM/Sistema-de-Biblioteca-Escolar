package datos;
import negocio.Alumno;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
public class AlumnoDAO {
    private Connection conn = ConexionDB.getInstancia().getConexion();
    public void insertar(Alumno alumno) throws SQLException {
        String sql = "INSERT INTO alumnos (nombre, matricula, usuario_id) VALUES (?, ?, ?)";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setString(1, alumno.getNombre());
        stmt.setString(2, alumno.getMatricula());
        stmt.setInt(3, alumno.getUsuarioId());
        stmt.executeUpdate();
    }
    public List<Alumno> listar() throws SQLException {
        List<Alumno> alumnos = new ArrayList<>();
        String sql = "SELECT * FROM alumnos";
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(sql);
        while (rs.next()) {
            alumnos.add(new Alumno(rs.getInt("id"), rs.getString("nombre"), rs.getString("matricula"), rs.getInt("usuario_id")));
        }
        return alumnos;
    }
    public Alumno buscarPorUsuarioId(int usuarioId) throws SQLException {
        String sql = "SELECT * FROM alumnos WHERE usuario_id = ?";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setInt(1, usuarioId);
        ResultSet rs = stmt.executeQuery();
        if (rs.next()) {
            return new Alumno(rs.getInt("id"), rs.getString("nombre"), rs.getString("matricula"), rs.getInt("usuario_id"));
        }
        return null;
    }
}
package datos;
import negocio.Usuario;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
public class UsuarioDAO {
    private Connection conn = ConexionDB.getInstancia().getConexion();
    public Usuario login(String email, String password, String mac) throws SQLException {
        String sql = "SELECT * FROM usuarios WHERE email = ? AND password = ? AND mac_address = ?";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setString(1, email);
        stmt.setString(2, password);
        stmt.setString(3, mac);
        ResultSet rs = stmt.executeQuery();
        if (rs.next()) {
            return new Usuario(rs.getInt("id"), rs.getString("nombre"), rs.getString("email"), rs.getString("rol"));
        }
        return null;
    }
    // Otros métodos si es necesario (e.g., insertar usuario)
}
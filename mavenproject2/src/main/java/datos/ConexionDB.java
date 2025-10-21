package datos;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
public class ConexionDB {
    private static ConexionDB instancia;
    private Connection conexion;
    private ConexionDB() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            this.conexion = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/biblioteca_escolar?useSSL=false&serverTimezone=UTC",
                "root", "" // Usuario y contraseña de XAMPP (por defecto vacío)
            );
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }
    public static ConexionDB getInstancia() {
        if (instancia == null) {
            instancia = new ConexionDB();
        }
        return instancia;
    }
    public Connection getConexion() {
        return conexion;
    }
}
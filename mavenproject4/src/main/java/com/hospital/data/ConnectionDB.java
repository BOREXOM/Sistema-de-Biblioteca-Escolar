package com.hospital.data;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionDB {
    private static ConnectionDB instance;
    private Connection connection;
    private final String URL = "jdbc:mysql://localhost:3306/hospitaldb?useSSL=false&serverTimezone=UTC";
    private final String USER = "root"; // ajusta si usas otro usuario
    private final String PASSWORD = ""; // ajusta si tienes password en XAMPP

    private ConnectionDB() throws SQLException {
        try {
            // Asegúrate de tener el Connector/J en el proyecto
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (SQLException ex) {
            throw new SQLException("Error conectando a BD: " + ex.getMessage());
        }
    }

    public static ConnectionDB getInstance() throws SQLException {
        if (instance == null || instance.getConnection().isClosed()) {
            instance = new ConnectionDB();
        }
        return instance;
    }

    public Connection getConnection() {
        return connection;
    }
}

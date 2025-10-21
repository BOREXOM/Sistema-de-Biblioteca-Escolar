package com.hospital.data.dao;

import com.hospital.data.ConnectionDB;
import com.hospital.data.model.Paciente;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PacienteDAO {
    public List<Paciente> listar() throws SQLException {
        List<Paciente> lista = new ArrayList<>();
        String sql = "SELECT * FROM pacientes";
        Connection conn = ConnectionDB.getInstance().getConnection();
        try (PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Paciente p = new Paciente();
                p.setId(rs.getInt("id"));
                p.setNombre(rs.getString("nombre"));
                p.setDni(rs.getString("dni"));
                p.setFechaNacimiento(rs.getDate("fecha_nacimiento"));
                p.setTelefono(rs.getString("telefono"));
                p.setHistorial(rs.getString("historial"));
                lista.add(p);
            }
        }
        return lista;
    }

    public Paciente obtenerPorId(int id) throws SQLException {
        String sql = "SELECT * FROM pacientes WHERE id = ?";
        Connection conn = ConnectionDB.getInstance().getConnection();
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Paciente p = new Paciente();
                    p.setId(rs.getInt("id"));
                    p.setNombre(rs.getString("nombre"));
                    p.setDni(rs.getString("dni"));
                    p.setFechaNacimiento(rs.getDate("fecha_nacimiento"));
                    p.setTelefono(rs.getString("telefono"));
                    p.setHistorial(rs.getString("historial"));
                    return p;
                }
            }
        }
        return null;
    }

    public int insertar(Paciente p) throws SQLException {
        String sql = "INSERT INTO pacientes (nombre, dni, fecha_nacimiento, telefono, historial) VALUES (?,?,?,?,?)";
        Connection conn = ConnectionDB.getInstance().getConnection();
        try (PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, p.getNombre());
            ps.setString(2, p.getDni());
            ps.setDate(3, p.getFechaNacimiento());
            ps.setString(4, p.getTelefono());
            ps.setString(5, p.getHistorial());
            ps.executeUpdate();
            try (ResultSet keys = ps.getGeneratedKeys()) {
                if (keys.next()) return keys.getInt(1);
            }
        }
        return -1;
    }

    public boolean actualizar(Paciente p) throws SQLException {
        String sql = "UPDATE pacientes SET nombre=?, dni=?, fecha_nacimiento=?, telefono=?, historial=? WHERE id=?";
        Connection conn = ConnectionDB.getInstance().getConnection();
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, p.getNombre());
            ps.setString(2, p.getDni());
            ps.setDate(3, p.getFechaNacimiento());
            ps.setString(4, p.getTelefono());
            ps.setString(5, p.getHistorial());
            ps.setInt(6, p.getId());
            return ps.executeUpdate() > 0;
        }
    }

    public boolean eliminar(int id) throws SQLException {
        String sql = "DELETE FROM pacientes WHERE id=?";
        Connection conn = ConnectionDB.getInstance().getConnection();
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        }
    }
}

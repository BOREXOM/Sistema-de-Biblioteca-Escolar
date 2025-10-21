package com.hospital.data.dao;

import com.hospital.data.ConnectionDB;
import com.hospital.data.model.Cita;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CitaDAO {
    public List<Cita> listar() throws SQLException {
        List<Cita> lista = new ArrayList<>();
        String sql = "SELECT * FROM citas ORDER BY fecha DESC";
        Connection conn = ConnectionDB.getInstance().getConnection();
        try (PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Cita c = new Cita();
                c.setId(rs.getInt("id"));
                c.setPacienteId(rs.getInt("paciente_id"));
                c.setMedicoId(rs.getInt("medico_id"));
                c.setFecha(rs.getTimestamp("fecha"));
                c.setMotivo(rs.getString("motivo"));
                c.setEstado(rs.getString("estado"));
                lista.add(c);
            }
        }
        return lista;
    }

    public int insertarConHistorial(Cita cita, String nuevaEntradaHistorial) throws SQLException {
        // Este método demuestra el uso de transacción para insertar cita y actualizar historial del paciente
        Connection conn = ConnectionDB.getInstance().getConnection();
        String sqlInsert = "INSERT INTO citas (paciente_id, medico_id, fecha, motivo, estado) VALUES (?,?,?,?,?)";
        String sqlUpdateHist = "UPDATE pacientes SET historial = CONCAT(IFNULL(historial,''), ?) WHERE id = ?";
        try {
            conn.setAutoCommit(false);
            int insertedId = -1;
            try (PreparedStatement ps = conn.prepareStatement(sqlInsert, Statement.RETURN_GENERATED_KEYS)) {
                ps.setInt(1, cita.getPacienteId());
                ps.setInt(2, cita.getMedicoId());
                ps.setTimestamp(3, cita.getFecha());
                ps.setString(4, cita.getMotivo());
                ps.setString(5, cita.getEstado());
                ps.executeUpdate();
                try (ResultSet keys = ps.getGeneratedKeys()) {
                    if (keys.next()) insertedId = keys.getInt(1);
                }
            }

            try (PreparedStatement ps2 = conn.prepareStatement(sqlUpdateHist)) {
                ps2.setString(1, "\n[" + new java.util.Date() + "] " + nuevaEntradaHistorial);
                ps2.setInt(2, cita.getPacienteId());
                ps2.executeUpdate();
            }

            conn.commit();
            return insertedId;
        } catch (SQLException ex) {
            conn.rollback();
            throw ex;
        } finally {
            conn.setAutoCommit(true);
        }
    }
}

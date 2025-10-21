package com.hospital.data.dao;

import com.hospital.data.ConnectionDB;
import com.hospital.data.model.Medico;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MedicoDAO {
    public List<Medico> listar() throws SQLException {
        List<Medico> lista = new ArrayList<>();
        String sql = "SELECT * FROM medicos";
        Connection conn = ConnectionDB.getInstance().getConnection();
        try (PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Medico m = new Medico();
                m.setId(rs.getInt("id"));
                m.setNombre(rs.getString("nombre"));
                m.setEspecialidad(rs.getString("especialidad"));
                m.setTelefono(rs.getString("telefono"));
                lista.add(m);
            }
        }
        return lista;
    }
}


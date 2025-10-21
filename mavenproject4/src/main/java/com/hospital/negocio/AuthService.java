package com.hospital.negocio;

import com.hospital.data.ConnectionDB;
import com.hospital.util.HashUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class AuthService {
    public static class AuthResult {
        public boolean ok;
        public String role;
        public int usuarioId;
        public int medicoId;
        public String message;
    }

    public AuthResult login(String username, String password) {
        AuthResult r = new AuthResult();
        try {
            Connection conn = ConnectionDB.getInstance().getConnection();
            String sql = "SELECT id, password_hash, role, medico_id FROM usuarios WHERE username = ?";
            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setString(1, username);
                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        String storedHash = rs.getString("password_hash");
                        String role = rs.getString("role");
                        int userId = rs.getInt("id");
                        int medicoId = rs.getInt("medico_id");
                        String inputHash = HashUtil.sha256(password);
                        if (storedHash.equalsIgnoreCase(inputHash)) {
                            r.ok = true;
                            r.role = role;
                            r.usuarioId = userId;
                            r.medicoId = medicoId;
                        } else {
                            r.ok = false;
                            r.message = "Contraseña incorrecta";
                        }
                    } else {
                        r.ok = false;
                        r.message = "Usuario no existe";
                    }
                }
            }
        } catch (Exception ex) {
            r.ok = false;
            r.message = "Error: " + ex.getMessage();
        }
        return r;
    }
}

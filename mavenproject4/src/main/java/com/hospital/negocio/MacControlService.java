package com.hospital.negocio;

import com.hospital.data.ConnectionDB;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Enumeration;

public class MacControlService {
    public boolean equipoAutorizado() {
        try {
            String mac = obtenerMacLocal();
            if (mac == null) return false;
            Connection conn = ConnectionDB.getInstance().getConnection();
            String sql = "SELECT activo FROM equipos_autorizados WHERE mac = ? LIMIT 1";
            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setString(1, mac);
                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        return rs.getBoolean("activo");
                    } else {
                        return false;
                    }
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }

    public static String obtenerMacLocal() {
        try {
            Enumeration<NetworkInterface> networks = NetworkInterface.getNetworkInterfaces();
            while (networks.hasMoreElements()) {
                NetworkInterface ni = networks.nextElement();
                byte[] mac = ni.getHardwareAddress();
                if (mac != null && mac.length > 0 && !ni.isLoopback()) {
                    StringBuilder sb = new StringBuilder();
                    for (int i = 0; i < mac.length; i++) {
                        sb.append(String.format("%02X%s", mac[i], (i < mac.length - 1) ? "-" : ""));
                    }
                    return sb.toString();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}

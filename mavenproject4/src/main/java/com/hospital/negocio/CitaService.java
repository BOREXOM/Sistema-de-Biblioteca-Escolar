package com.hospital.negocio;

import com.hospital.data.dao.CitaDAO;
import com.hospital.data.model.Cita;

import java.sql.SQLException;
import java.util.List;

public class CitaService {
    private CitaDAO dao = new CitaDAO();

    public List<Cita> listar() throws SQLException {
        return dao.listar();
    }

    // inserta cita y añade entrada al historial del paciente de forma transaccional
    public int agendarCitaConHistorial(Cita c, String textoHistorial) throws SQLException {
        return dao.insertarConHistorial(c, textoHistorial);
    }
}

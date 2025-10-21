package com.hospital.negocio;

import com.hospital.data.dao.PacienteDAO;
import com.hospital.data.model.Paciente;
import com.hospital.util.Utils;

import java.sql.SQLException;
import java.util.List;

public class PacienteService {
    private PacienteDAO dao = new PacienteDAO();

    public List<Paciente> listar() throws SQLException {
        return dao.listar();
    }

    public int insertar(Paciente p) throws SQLException {
        if (!Utils.validarDNI(p.getDni())) throw new IllegalArgumentException("DNI inválido");
        return dao.insertar(p);
    }

    public boolean actualizar(Paciente p) throws SQLException {
        return dao.actualizar(p);
    }

    public Paciente obtenerPorId(int id) throws SQLException {
        return dao.obtenerPorId(id);
    }
}

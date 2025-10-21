package com.hospital.data.model;

import java.sql.Timestamp;

public class Cita {
    private int id;
    private int pacienteId;
    private int medicoId;
    private Timestamp fecha;
    private String motivo;
    private String estado;

    public Cita() {}

    // getters y setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public int getPacienteId() { return pacienteId; }
    public void setPacienteId(int pacienteId) { this.pacienteId = pacienteId; }
    public int getMedicoId() { return medicoId; }
    public void setMedicoId(int medicoId) { this.medicoId = medicoId; }
    public Timestamp getFecha() { return fecha; }
    public void setFecha(Timestamp fecha) { this.fecha = fecha; }
    public String getMotivo() { return motivo; }
    public void setMotivo(String motivo) { this.motivo = motivo; }
    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }
}

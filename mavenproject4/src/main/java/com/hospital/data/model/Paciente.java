package com.hospital.data.model;

import java.util.Date;

public class Paciente {
    private int id;
    private String nombre;
    private String dni;
    private java.sql.Date fechaNacimiento;
    private String telefono;
    private String historial;

    public Paciente() {}

    public Paciente(int id, String nombre, String dni, java.sql.Date fechaNacimiento, String telefono, String historial) {
        this.id = id; this.nombre = nombre; this.dni = dni; this.fechaNacimiento = fechaNacimiento; this.telefono = telefono; this.historial = historial;
    }

    // getters y setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public String getDni() { return dni; }
    public void setDni(String dni) { this.dni = dni; }
    public java.sql.Date getFechaNacimiento() { return fechaNacimiento; }
    public void setFechaNacimiento(java.sql.Date fechaNacimiento) { this.fechaNacimiento = fechaNacimiento; }
    public String getTelefono() { return telefono; }
    public void setTelefono(String telefono) { this.telefono = telefono; }
    public String getHistorial() { return historial; }
    public void setHistorial(String historial) { this.historial = historial; }
}

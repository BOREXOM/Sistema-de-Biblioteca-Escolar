package com.hospital.util;

import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.util.Date;

public class Utils {
    public static int calcularEdad(java.sql.Date fechaNacimiento) {
        if (fechaNacimiento == null) return 0;
        LocalDate birth = fechaNacimiento.toLocalDate();
        LocalDate today = LocalDate.now();
        return Period.between(birth, today).getYears();
    }

    // Validación básica de DNI (ejemplo simple: longitud y solo dígitos)
    public static boolean validarDNI(String dni) {
        if (dni == null) return false;
        String clean = dni.trim();
        return clean.matches("\\d{6,12}"); // regla sencilla: entre 6 y 12 dígitos
    }
}

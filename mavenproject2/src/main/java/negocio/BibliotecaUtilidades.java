package negocio;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class BibliotecaUtilidades {
    public static boolean validarFecha(String fecha) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            sdf.setLenient(false);
            sdf.parse(fecha);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public static double calcularMulta(Date fechaDevolucion, Date fechaLimite) {
        long diff = fechaDevolucion.getTime() - fechaLimite.getTime();
        long dias = TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);
        return dias > 0 ? dias * 1.0 : 0; // 1 USD por día
    }

    public static String formatearISBN(String isbn) {
        if (isbn.length() == 13) {
            return isbn.substring(0, 3) + "-" + isbn.substring(3, 4) + "-" + isbn.substring(4, 6) + "-" + isbn.substring(6, 12) + "-" + isbn.substring(12);
        }
        return isbn;
    }
}
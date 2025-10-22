package presentacion;

import negocio.Prestamo;
import negocio.ServicioBiblioteca;
import negocio.Usuario;
import negocio.Alumno;
import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;
import java.util.List;

public class VentanaVerPrestamos extends JFrame {
    private Usuario usuario;
    private ServicioBiblioteca servicio = new ServicioBiblioteca();

    public VentanaVerPrestamos(Usuario usuario) {
        this.usuario = usuario;
        setTitle("Mis Préstamos");
        setSize(500, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        getContentPane().setBackground(new Color(173, 216, 230));

        JTextArea txtArea = new JTextArea();
        txtArea.setEditable(false);
        txtArea.setBackground(Color.WHITE);
        try {
            Alumno alumno = servicio.obtenerAlumnoPorUsuarioId(usuario.getId());
            if (alumno == null) {
                txtArea.setText("No se encontró alumno vinculado a tu usuario.");
            } else {
                List<Prestamo> prestamos = servicio.listarPrestamosPorAlumno(alumno.getId());
                if (prestamos.isEmpty()) {
                    txtArea.setText("No tienes préstamos registrados.");
                } else {
                    for (Prestamo p : prestamos) {
                        txtArea.append("ID: " + p.getId() + " | Libro ID: " + p.getLibroId() + " | Fecha: " + p.getFechaPrestamo() + " | Multa: $" + p.getMulta() + "\n");
                    }
                }
            }
        } catch (SQLException ex) {
            txtArea.setText("Error al cargar préstamos: " + ex.getMessage());
        }
        add(new JScrollPane(txtArea), BorderLayout.CENTER);
    }
}
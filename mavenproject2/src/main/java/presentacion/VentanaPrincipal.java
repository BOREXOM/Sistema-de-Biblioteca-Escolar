package presentacion;

import negocio.Usuario;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class VentanaPrincipal extends JFrame {
    private Usuario usuario;

    public VentanaPrincipal(Usuario usuario) {
        this.usuario = usuario;
        setTitle("Biblioteca Escolar - " + usuario.getRol());
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        getContentPane().setBackground(new Color(173, 216, 230));

        // Panel superior con bienvenida
        JPanel panelSuperior = new JPanel();
        panelSuperior.setBackground(new Color(70, 130, 180));
        panelSuperior.add(new JLabel("Bienvenido, " + usuario.getNombre() + " (" + usuario.getRol() + ")"));
        add(panelSuperior, BorderLayout.NORTH);

        // Panel central con botones según rol
        JPanel panelCentral = new JPanel(new GridLayout(3, 2, 10, 10));
        panelCentral.setBackground(new Color(173, 216, 230));

        JButton btnRegistrarAlumno = new JButton("Registrar Alumno");
        JButton btnRegistrarLibro = new JButton("Registrar Libro");
        JButton btnPrestamo = new JButton("Gestionar Préstamo");
        JButton btnVerPrestamos = new JButton("Ver Mis Préstamos");
        JButton btnSalir = new JButton("Salir");

        // Limitar acciones por rol
        if ("Administrador".equals(usuario.getRol()) || "Bibliotecario".equals(usuario.getRol())) {
            panelCentral.add(btnRegistrarAlumno);
            panelCentral.add(btnRegistrarLibro);
        }
        panelCentral.add(btnPrestamo);
        if ("Alumno".equals(usuario.getRol())) {
            panelCentral.add(btnVerPrestamos);
        }
        panelCentral.add(btnSalir);

        // Action listeners
        btnRegistrarAlumno.addActionListener(e -> new VentanaRegistroAlumno().setVisible(true));
        btnRegistrarLibro.addActionListener(e -> new VentanaRegistroLibro().setVisible(true));
        btnPrestamo.addActionListener(e -> new VentanaPrestamo(usuario).setVisible(true));
        btnVerPrestamos.addActionListener(e -> new VentanaVerPrestamos(usuario).setVisible(true));
        btnSalir.addActionListener(e -> System.exit(0));

        add(panelCentral, BorderLayout.CENTER);
    }
}

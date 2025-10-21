package presentacion;

import negocio.Prestamo;
import negocio.ServicioBiblioteca;
import negocio.Usuario;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.Date;

public class VentanaPrestamo extends JFrame {
    private JTextField txtAlumnoId, txtLibroId, txtPrestamoId;
    private JButton btnPrestar, btnDevolver;
    private ServicioBiblioteca servicio = new ServicioBiblioteca();
    private Usuario usuario;

    public VentanaPrestamo(Usuario usuario) {
        this.usuario = usuario;
        setTitle("Gestionar Préstamo");
        setSize(400, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridBagLayout());
        getContentPane().setBackground(new Color(173, 216, 230));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        gbc.gridx = 0; gbc.gridy = 0;
        add(new JLabel("ID Alumno:"), gbc);
        gbc.gridx = 1;
        txtAlumnoId = new JTextField(20);
        add(txtAlumnoId, gbc);

        gbc.gridx = 0; gbc.gridy = 1;
        add(new JLabel("ID Libro:"), gbc);
        gbc.gridx = 1;
        txtLibroId = new JTextField(20);
        add(txtLibroId, gbc);

        gbc.gridx = 0; gbc.gridy = 2; gbc.gridwidth = 2;
        btnPrestar = new JButton("Prestar Libro");
        btnPrestar.setBackground(new Color(70, 130, 180));
        btnPrestar.setForeground(Color.WHITE);
        btnPrestar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    int alumnoId = Integer.parseInt(txtAlumnoId.getText());
                    int libroId = Integer.parseInt(txtLibroId.getText());
                    Prestamo prestamo = new Prestamo(0, alumnoId, libroId, new Date(), null, 0);
                    servicio.registrarPrestamo(prestamo);
                    JOptionPane.showMessageDialog(null, "Préstamo registrado!");
                } catch (SQLException | NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        add(btnPrestar, gbc);

        gbc.gridx = 0; gbc.gridy = 3;
        add(new JLabel("ID Préstamo:"), gbc);
        gbc.gridx = 1;
        txtPrestamoId = new JTextField(20);
        add(txtPrestamoId, gbc);

        gbc.gridx = 0; gbc.gridy = 4; gbc.gridwidth = 2;
        btnDevolver = new JButton("Devolver Libro");
        btnDevolver.setBackground(new Color(70, 130, 180));
        btnDevolver.setForeground(Color.WHITE);
        btnDevolver.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    int prestamoId = Integer.parseInt(txtPrestamoId.getText());
                    servicio.registrarDevolucion(prestamoId, new Date());
                    JOptionPane.showMessageDialog(null, "Devolución registrada con multa calculada!");
                } catch (SQLException | NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        add(btnDevolver, gbc);
    }
}
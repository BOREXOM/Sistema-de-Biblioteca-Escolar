package presentacion;

import negocio.Alumno;
import negocio.ServicioBiblioteca;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

public class VentanaRegistroAlumno extends JFrame {
    private JTextField txtNombre, txtMatricula, txtUsuarioId;
    private JButton btnRegistrar;
    private ServicioBiblioteca servicio = new ServicioBiblioteca();

    public VentanaRegistroAlumno() {
        setTitle("Registrar Alumno");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridBagLayout());
        getContentPane().setBackground(new Color(173, 216, 230));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        gbc.gridx = 0; gbc.gridy = 0;
        add(new JLabel("Nombre:"), gbc);
        gbc.gridx = 1;
        txtNombre = new JTextField(20);
        add(txtNombre, gbc);

        gbc.gridx = 0; gbc.gridy = 1;
        add(new JLabel("Matrícula:"), gbc);
        gbc.gridx = 1;
        txtMatricula = new JTextField(20);
        add(txtMatricula, gbc);

        gbc.gridx = 0; gbc.gridy = 2;
        add(new JLabel("ID Usuario:"), gbc);
        gbc.gridx = 1;
        txtUsuarioId = new JTextField(20);
        add(txtUsuarioId, gbc);

        gbc.gridx = 0; gbc.gridy = 3; gbc.gridwidth = 2;
        btnRegistrar = new JButton("Registrar");
        btnRegistrar.setBackground(new Color(70, 130, 180));
        btnRegistrar.setForeground(Color.WHITE);
        btnRegistrar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String nombre = txtNombre.getText();
                    String matricula = txtMatricula.getText();
                    int usuarioId = Integer.parseInt(txtUsuarioId.getText());
                    Alumno alumno = new Alumno(0, nombre, matricula, usuarioId);
                    servicio.registrarAlumno(alumno);
                    JOptionPane.showMessageDialog(null, "Alumno registrado exitosamente!");
                    dispose();
                } catch (SQLException | NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        add(btnRegistrar, gbc);
    }
}

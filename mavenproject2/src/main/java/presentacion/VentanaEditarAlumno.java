package presentacion;

import negocio.Alumno;
import negocio.ServicioBiblioteca;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

public class VentanaEditarAlumno extends JDialog {
    private final ServicioBiblioteca servicio;
    private final int alumnoId;
    private JTextField txtNombre, txtMatricula, txtUsuarioId;

    public VentanaEditarAlumno(Frame owner, int id, String nombre, String matricula, int usuarioId, ServicioBiblioteca servicio) {
        super(owner, "Editar Alumno", true); // modal
        this.servicio = servicio;
        this.alumnoId = id;
        setSize(420, 300);
        setLocationRelativeTo(owner);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(new GridBagLayout());
        getContentPane().setBackground(new Color(245, 247, 250));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.anchor = GridBagConstraints.WEST;

        gbc.gridx = 0; gbc.gridy = 0;
        add(new JLabel("Nombre:"), gbc);
        gbc.gridx = 1;
        txtNombre = new JTextField(20);
        txtNombre.setText(nombre);
        add(txtNombre, gbc);

        gbc.gridx = 0; gbc.gridy = 1;
        add(new JLabel("Matrícula:"), gbc);
        gbc.gridx = 1;
        txtMatricula = new JTextField(20);
        txtMatricula.setText(matricula);
        add(txtMatricula, gbc);

        gbc.gridx = 0; gbc.gridy = 2;
        add(new JLabel("Usuario ID:"), gbc);
        gbc.gridx = 1;
        txtUsuarioId = new JTextField(20);
        txtUsuarioId.setText(String.valueOf(usuarioId));
        add(txtUsuarioId, gbc);

        gbc.gridx = 0; gbc.gridy = 3; gbc.gridwidth = 2;
        JButton btnGuardar = new JButton("Guardar cambios");
        btnGuardar.setBackground(new Color(41, 128, 185));
        btnGuardar.setForeground(Color.WHITE);
        btnGuardar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String nuevoNombre = txtNombre.getText();
                    String nuevaMatricula = txtMatricula.getText();
                    int nuevoUsuarioId = Integer.parseInt(txtUsuarioId.getText());
                    Alumno actualizado = new Alumno(alumnoId, nuevoNombre, nuevaMatricula, nuevoUsuarioId);
                    servicio.actualizarAlumno(actualizado);
                    JOptionPane.showMessageDialog(VentanaEditarAlumno.this, "Alumno actualizado correctamente");
                    dispose();
                } catch (SQLException | NumberFormatException ex) {
                    JOptionPane.showMessageDialog(VentanaEditarAlumno.this, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        add(btnGuardar, gbc);
    }
}
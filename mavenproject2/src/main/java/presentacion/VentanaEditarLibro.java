package presentacion;

import negocio.Libro;
import negocio.ServicioBiblioteca;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

public class VentanaEditarLibro extends JDialog {
    private final ServicioBiblioteca servicio;
    private final int libroId;
    private JTextField txtTitulo, txtAutor, txtIsbn;

    public VentanaEditarLibro(Frame owner, int id, String titulo, String autor, String isbn, ServicioBiblioteca servicio) {
        super(owner, "Editar Libro", true);
        this.servicio = servicio;
        this.libroId = id;
        setSize(420, 280);
        setLocationRelativeTo(owner);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(new GridBagLayout());
        getContentPane().setBackground(new Color(245, 247, 250));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.anchor = GridBagConstraints.WEST;

        gbc.gridx = 0; gbc.gridy = 0;
        add(new JLabel("Título:"), gbc);
        gbc.gridx = 1;
        txtTitulo = new JTextField(22);
        txtTitulo.setText(titulo);
        add(txtTitulo, gbc);

        gbc.gridx = 0; gbc.gridy = 1;
        add(new JLabel("Autor:"), gbc);
        gbc.gridx = 1;
        txtAutor = new JTextField(22);
        txtAutor.setText(autor);
        add(txtAutor, gbc);

        gbc.gridx = 0; gbc.gridy = 2;
        add(new JLabel("ISBN:"), gbc);
        gbc.gridx = 1;
        txtIsbn = new JTextField(22);
        txtIsbn.setText(isbn);
        add(txtIsbn, gbc);

        gbc.gridx = 0; gbc.gridy = 3; gbc.gridwidth = 2;
        JButton btnGuardar = new JButton("Guardar cambios");
        btnGuardar.setBackground(new Color(41, 128, 185));
        btnGuardar.setForeground(Color.WHITE);
        btnGuardar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String nuevoTitulo = txtTitulo.getText();
                    String nuevoAutor = txtAutor.getText();
                    String nuevoIsbn = txtIsbn.getText();
                    Libro actualizado = new Libro(libroId, nuevoTitulo, nuevoAutor, nuevoIsbn, true /* placeholder, no se usa en actualización */);
                    servicio.actualizarLibro(actualizado);
                    JOptionPane.showMessageDialog(VentanaEditarLibro.this, "Libro actualizado correctamente");
                    dispose();
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(VentanaEditarLibro.this, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        add(btnGuardar, gbc);
    }
}
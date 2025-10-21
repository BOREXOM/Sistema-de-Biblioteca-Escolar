package presentacion;

import negocio.Libro;
import negocio.ServicioBiblioteca;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

public class VentanaRegistroLibro extends JFrame {
    private JTextField txtTitulo, txtAutor, txtIsbn;
    private JButton btnRegistrar;
    private ServicioBiblioteca servicio = new ServicioBiblioteca();

    public VentanaRegistroLibro() {
        setTitle("Registrar Libro");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridBagLayout());
        getContentPane().setBackground(new Color(173, 216, 230));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        gbc.gridx = 0; gbc.gridy = 0;
        add(new JLabel("Título:"), gbc);
        gbc.gridx = 1;
        txtTitulo = new JTextField(20);
        add(txtTitulo, gbc);

        gbc.gridx = 0; gbc.gridy = 1;
        add(new JLabel("Autor:"), gbc);
        gbc.gridx = 1;
        txtAutor = new JTextField(20);
        add(txtAutor, gbc);

        gbc.gridx = 0; gbc.gridy = 2;
        add(new JLabel("ISBN:"), gbc);
        gbc.gridx = 1;
        txtIsbn = new JTextField(20);
        add(txtIsbn, gbc);

        gbc.gridx = 0; gbc.gridy = 3; gbc.gridwidth = 2;
        btnRegistrar = new JButton("Registrar");
        btnRegistrar.setBackground(new Color(70, 130, 180));
        btnRegistrar.setForeground(Color.WHITE);
        btnRegistrar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String titulo = txtTitulo.getText();
                    String autor = txtAutor.getText();
                    String isbn = txtIsbn.getText();
                    Libro libro = new Libro(0, titulo, autor, isbn, true);
                    servicio.registrarLibro(libro);
                    JOptionPane.showMessageDialog(null, "Libro registrado exitosamente!");
                    dispose();
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(null, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        add(btnRegistrar, gbc);
    }
}
package presentacion;

import negocio.ServicioBiblioteca;
import negocio.Usuario;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.sql.SQLException;
import java.util.Enumeration;

public class VentanaLogin extends JFrame {
    private JTextField txtEmail;
    private JPasswordField txtPassword;
    private JButton btnLogin;
    private ServicioBiblioteca servicio = new ServicioBiblioteca();

    public VentanaLogin() {
        setTitle("Login - Biblioteca Escolar");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridBagLayout());
        getContentPane().setBackground(new Color(173, 216, 230)); // Azul claro

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        gbc.gridx = 0; gbc.gridy = 0;
        add(new JLabel("Email:"), gbc);
        gbc.gridx = 1;
        txtEmail = new JTextField(20);
        add(txtEmail, gbc);

        gbc.gridx = 0; gbc.gridy = 1;
        add(new JLabel("Contraseña:"), gbc);  // Aquí estaba cortado - completado
        gbc.gridx = 1;
        txtPassword = new JPasswordField(20);
        add(txtPassword, gbc);

        gbc.gridx = 0; gbc.gridy = 2; gbc.gridwidth = 2;
        btnLogin = new JButton("Iniciar Sesión");
        btnLogin.setBackground(new Color(70, 130, 180)); // Azul oscuro
        btnLogin.setForeground(Color.WHITE);
        btnLogin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String email = txtEmail.getText();
                String password = new String(txtPassword.getPassword());
                String mac = obtenerMAC(); // Obtener MAC del equipo
                try {
                    Usuario usuario = servicio.login(email, password, mac);
                    if (usuario != null) {
                        JOptionPane.showMessageDialog(null, "Bienvenido, " + usuario.getRol() + "!");
                        new VentanaPrincipal(usuario).setVisible(true);
                        dispose(); // Cerrar ventana de login
                    } else {
                        JOptionPane.showMessageDialog(null, "Credenciales o MAC inválidos.", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(null, "Error de conexión: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        add(btnLogin, gbc);

        setVisible(true);
    }

    private String obtenerMAC() {
        try {
            Enumeration<NetworkInterface> networks = NetworkInterface.getNetworkInterfaces();
            while (networks.hasMoreElements()) {
                NetworkInterface network = networks.nextElement();
                byte[] mac = network.getHardwareAddress();
                if (mac != null) {
                    StringBuilder sb = new StringBuilder();
                    for (int i = 0; i < mac.length; i++) {
                        sb.append(String.format("%02X%s", mac[i], (i < mac.length - 1) ? "-" : ""));
                    }
                    return sb.toString();
                }
            }
        } catch (SocketException e) {
            e.printStackTrace();
        }
        return "";
    }
}
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
        setTitle("Biblioteca Escolar - Login");
        setSize(900, 560);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        Color azulPrincipal = new Color(41, 128, 185);
        Color azulClaro = new Color(174, 214, 241);
        Color fondo = new Color(245, 247, 250);
        Color texto = new Color(44, 62, 80);

        // Panel izquierdo (branding)
        JPanel panelIzquierdo = new JPanel();
        panelIzquierdo.setBackground(azulPrincipal);
        panelIzquierdo.setPreferredSize(new Dimension(380, getHeight()));
        panelIzquierdo.setLayout(new BoxLayout(panelIzquierdo, BoxLayout.Y_AXIS));

        JLabel tituloIzq = new JLabel("Biblioteca Escolar", SwingConstants.CENTER);
        tituloIzq.setFont(new Font("Segoe UI Semibold", Font.BOLD, 24));
        tituloIzq.setForeground(Color.WHITE);
        tituloIzq.setAlignmentX(Component.CENTER_ALIGNMENT);
        tituloIzq.setBorder(BorderFactory.createEmptyBorder(40, 0, 10, 0));

        JLabel subtituloIzq = new JLabel("Gestiona libros y préstamos con facilidad", SwingConstants.CENTER);
        subtituloIzq.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        subtituloIzq.setForeground(Color.WHITE);
        subtituloIzq.setAlignmentX(Component.CENTER_ALIGNMENT);

        panelIzquierdo.add(tituloIzq);
        panelIzquierdo.add(subtituloIzq);
        panelIzquierdo.add(Box.createVerticalGlue());

        // Panel derecho (formulario)
        JPanel panelDerecho = new JPanel(new BorderLayout());
        panelDerecho.setBackground(fondo);
        panelDerecho.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));

        JLabel tituloForm = new JLabel("Iniciar sesión");
        tituloForm.setFont(new Font("Segoe UI Semibold", Font.BOLD, 22));
        tituloForm.setForeground(texto);
        panelDerecho.add(tituloForm, BorderLayout.NORTH);

        JPanel form = new JPanel(new GridBagLayout());
        form.setOpaque(false);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.anchor = GridBagConstraints.WEST;

        gbc.gridx = 0; gbc.gridy = 0;
        JLabel lblEmail = new JLabel("Email:");
        lblEmail.setForeground(texto);
        form.add(lblEmail, gbc);
        gbc.gridx = 1;
        txtEmail = new JTextField(24);
        form.add(txtEmail, gbc);

        gbc.gridx = 0; gbc.gridy = 1;
        JLabel lblPass = new JLabel("Contraseña:");
        lblPass.setForeground(texto);
        form.add(lblPass, gbc);
        gbc.gridx = 1;
        txtPassword = new JPasswordField(24);
        form.add(txtPassword, gbc);

        final char defaultEchoChar = txtPassword.getEchoChar();
        gbc.gridx = 1; gbc.gridy = 2;
        JCheckBox chkMostrar = new JCheckBox("Mostrar contraseña");
        chkMostrar.setOpaque(false);
        chkMostrar.setForeground(texto);
        chkMostrar.addActionListener(e -> {
            if (chkMostrar.isSelected()) txtPassword.setEchoChar((char)0);
            else txtPassword.setEchoChar(defaultEchoChar);
        });
        form.add(chkMostrar, gbc);

        gbc.gridx = 0; gbc.gridy = 3; gbc.gridwidth = 2;
        btnLogin = new JButton("Ingresar");
        btnLogin.setBackground(azulPrincipal);
        btnLogin.setForeground(Color.WHITE);
        btnLogin.setFocusPainted(false);
        btnLogin.setPreferredSize(new Dimension(220, 36));
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
        form.add(btnLogin, gbc);

        panelDerecho.add(form, BorderLayout.CENTER);

        add(panelIzquierdo, BorderLayout.WEST);
        add(panelDerecho, BorderLayout.CENTER);

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
package com.hospital.presentacion;

import com.hospital.negocio.AuthService;
import com.hospital.negocio.AuthService.AuthResult;
import com.hospital.negocio.MacControlService;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class LoginFrame extends JFrame {
    private JTextField txtUser;
    private JPasswordField txtPass;
    private AuthService authService = new AuthService();
    private MacControlService macService = new MacControlService();

    public LoginFrame() {
        setTitle("Hospital - Login");
        setSize(420, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        initUI();
    }

    private void initUI() {
        getContentPane().setBackground(Color.WHITE);
        setLayout(new BorderLayout());

        // Panel superior con estilo creativo
        JPanel top = new JPanel(new BorderLayout());
        top.setPreferredSize(new Dimension(0, 110));
        top.setBackground(new Color(33, 150, 243));
        JLabel title = new JLabel("Hospital Management", SwingConstants.CENTER);
        title.setForeground(Color.WHITE);
        title.setFont(new Font("Segoe UI", Font.BOLD, 22));
        top.add(title, BorderLayout.CENTER);

        add(top, BorderLayout.NORTH);

        // Panel central
        JPanel center = new JPanel();
        center.setLayout(new GridBagLayout());
        center.setBackground(Color.WHITE);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8,8,8,8);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel lblUser = new JLabel("Usuario:");
        gbc.gridx=0; gbc.gridy=0; center.add(lblUser, gbc);
        txtUser = new JTextField(20);
        gbc.gridx=1; gbc.gridy=0; center.add(txtUser, gbc);

        JLabel lblPass = new JLabel("Contraseña:");
        gbc.gridx=0; gbc.gridy=1; center.add(lblPass, gbc);
        txtPass = new JPasswordField(20);
        gbc.gridx=1; gbc.gridy=1; center.add(txtPass, gbc);

        JButton btnLogin = new JButton("Ingresar");
        gbc.gridx = 0; gbc.gridy = 2; gbc.gridwidth = 2;
        center.add(btnLogin, gbc);

        add(center, BorderLayout.CENTER);

        // acciones
        btnLogin.addActionListener((ActionEvent e) -> {
            ingresar();
        });
    }

    private void ingresar() {
        String user = txtUser.getText().trim();
        String pass = new String(txtPass.getPassword());

        // comprobar MAC
        boolean autorizado = macService.equipoAutorizado();
        if (!autorizado) {
            JOptionPane.showMessageDialog(this, "Equipo no autorizado (MAC). Contacte al administrador.", "Acceso denegado", JOptionPane.ERROR_MESSAGE);
            return;
        }

        AuthResult res = authService.login(user, pass);
        if (res.ok) {
            // abrir MainFrame
            MainFrame mf = new MainFrame(res.role, res.usuarioId, res.medicoId);
            mf.setVisible(true);
            this.dispose();
        } else {
            JOptionPane.showMessageDialog(this, res.message, "Error de autenticación", JOptionPane.ERROR_MESSAGE);
        }
    }
}

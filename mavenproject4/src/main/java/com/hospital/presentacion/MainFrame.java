package com.hospital.presentacion;

import com.hospital.presentacion.panels.CitasPanel;
import com.hospital.presentacion.panels.MedicosPanel;
import com.hospital.presentacion.panels.PacientesPanel;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {
    private String role;
    private int usuarioId;
    private int medicoId;

    private CardLayout cardLayout = new CardLayout();
    private JPanel mainCards = new JPanel(cardLayout);

    public MainFrame(String role, int usuarioId, int medicoId) {
        this.role = role;
        this.usuarioId = usuarioId;
        this.medicoId = medicoId;
        setTitle("Hospital - Sistema (" + role + ")");
        setSize(1000, 650);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        initUI();
    }

    private void initUI() {
        JPanel sidebar = new JPanel();
        sidebar.setLayout(new BoxLayout(sidebar, BoxLayout.Y_AXIS));
        sidebar.setBackground(new Color(245,245,245));
        sidebar.setPreferredSize(new Dimension(200, 0));

        JButton btnPacientes = new JButton("Pacientes");
        JButton btnCitas = new JButton("Citas");
        JButton btnMedicos = new JButton("Médicos");
        JButton btnSalir = new JButton("Cerrar Sesión");

        sidebar.add(Box.createVerticalStrut(20));
        sidebar.add(btnPacientes);
        sidebar.add(Box.createVerticalStrut(10));
        sidebar.add(btnCitas);
        sidebar.add(Box.createVerticalStrut(10));
        sidebar.add(btnMedicos);
        sidebar.add(Box.createVerticalStrut(20));
        sidebar.add(btnSalir);

        // Panels principales
        PacientesPanel pacientesPanel = new PacientesPanel(role);
        CitasPanel citasPanel = new CitasPanel(role, medicoId);
        MedicosPanel medicosPanel = new MedicosPanel(role);

        mainCards.add(pacientesPanel, "PAC");
        mainCards.add(citasPanel, "CIT");
        mainCards.add(medicosPanel, "MED");

        add(sidebar, BorderLayout.WEST);
        add(mainCards, BorderLayout.CENTER);

        btnPacientes.addActionListener(e -> cardLayout.show(mainCards, "PAC"));
        btnCitas.addActionListener(e -> cardLayout.show(mainCards, "CIT"));
        btnMedicos.addActionListener(e -> cardLayout.show(mainCards, "MED"));
        btnSalir.addActionListener(e -> {
            new LoginFrame().setVisible(true);
            this.dispose();
        });
    }
}

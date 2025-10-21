package com.hospital.presentacion.panels;

import com.hospital.data.model.Cita;
import com.hospital.negocio.CitaService;
import com.hospital.data.dao.MedicoDAO; // no implementado arriba, puedes usar MedicoDAO en su lugar
import com.hospital.data.dao.MedicoDAO;
import com.hospital.data.dao.PacienteDAO;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.Timestamp;
import java.util.List;

public class CitasPanel extends JPanel {
    private CitaService service = new CitaService();
    private JTable tabla;
    private DefaultTableModel modelo;
    private String role;
    private int medicoId;

    public CitasPanel(String role, int medicoId) {
        this.role = role;
        this.medicoId = medicoId;
        setLayout(new BorderLayout());
        JLabel lbl = new JLabel("Gestión de Citas");
        lbl.setFont(new Font("Segoe UI", Font.BOLD, 18));
        add(lbl, BorderLayout.NORTH);
        modelo = new DefaultTableModel(new Object[]{"ID","PacienteID","MédicoID","Fecha","Motivo","Estado"}, 0);
        tabla = new JTable(modelo);
        add(new JScrollPane(tabla), BorderLayout.CENTER);

        JPanel bottom = new JPanel();
        JButton btnNuevo = new JButton("Agendar");
        JButton btnActualizar = new JButton("Actualizar Estado");
        bottom.add(btnNuevo);
        bottom.add(btnActualizar);
        add(bottom, BorderLayout.SOUTH);

        if ("MEDICO".equalsIgnoreCase(role)) {
            // si es médico, puede filtrar o solo ver sus citas; aquí lo dejamos tal cual
        }

        cargar();
        btnNuevo.addActionListener(e -> agendar());
        btnActualizar.addActionListener(e -> actualizarEstado());
    }

    private void cargar() {
        try {
            modelo.setRowCount(0);
            List<Cita> lista = service.listar();
            for (Cita c : lista) {
                modelo.addRow(new Object[]{c.getId(), c.getPacienteId(), c.getMedicoId(), c.getFecha(), c.getMotivo(), c.getEstado()});
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error al cargar citas: " + ex.getMessage());
        }
    }

    private void agendar() {
        try {
            PacienteDAO pdao = new PacienteDAO();
            MedicoDAO mdao = new MedicoDAO();
            List<com.hospital.data.model.Paciente> pacientes = pdao.listar();
            List<com.hospital.data.model.Medico> medicos = mdao.listar();

            String[] pacItems = pacientes.stream().map(p->p.getId()+" - "+p.getNombre()).toArray(String[]::new);
            String[] medItems = medicos.stream().map(m->m.getId()+" - "+m.getNombre()).toArray(String[]::new);
            JComboBox<String> cbPac = new JComboBox<>(pacItems);
            JComboBox<String> cbMed = new JComboBox<>(medItems);
            JTextField fecha = new JTextField("YYYY-MM-DD HH:MM:SS");
            JTextField motivo = new JTextField();

            JPanel panel = new JPanel(new GridLayout(0,1));
            panel.add(new JLabel("Paciente:")); panel.add(cbPac);
            panel.add(new JLabel("Médico:")); panel.add(cbMed);
            panel.add(new JLabel("Fecha (YYYY-MM-DD HH:MM:SS):")); panel.add(fecha);
            panel.add(new JLabel("Motivo:")); panel.add(motivo);

            int res = JOptionPane.showConfirmDialog(this, panel, "Agendar Cita", JOptionPane.OK_CANCEL_OPTION);
            if (res == JOptionPane.OK_OPTION) {
                String pacSel = (String) cbPac.getSelectedItem();
                String medSel = (String) cbMed.getSelectedItem();
                int pacId = Integer.parseInt(pacSel.split(" - ")[0]);
                int medId = Integer.parseInt(medSel.split(" - ")[0]);
                Timestamp ts = Timestamp.valueOf(fecha.getText());
                Cita c = new Cita();
                c.setPacienteId(pacId);
                c.setMedicoId(medId);
                c.setFecha(ts);
                c.setMotivo(motivo.getText());
                c.setEstado("PROGRAMADA");
                String entradaHistorial = "Agendada cita para " + ts + " por: " + motivo.getText();
                service.agendarCitaConHistorial(c, entradaHistorial);
                cargar();
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error agendando: " + ex.getMessage());
        }
    }

    private void actualizarEstado() {
        int sel = tabla.getSelectedRow();
        if (sel < 0) { JOptionPane.showMessageDialog(this, "Seleccione una cita"); return; }
        int id = (int) modelo.getValueAt(sel, 0);
        String[] estados = {"PROGRAMADA","REALIZADA","CANCELADA"};
        String nuevo = (String) JOptionPane.showInputDialog(this, "Seleccione estado", "Actualizar", JOptionPane.PLAIN_MESSAGE, null, estados, estados[0]);
        if (nuevo != null) {
            try {
                // actualizar en BD (simple)
                java.sql.Connection conn = com.hospital.data.ConnectionDB.getInstance().getConnection();
                java.sql.PreparedStatement ps = conn.prepareStatement("UPDATE citas SET estado=? WHERE id=?");
                ps.setString(1, nuevo);
                ps.setInt(2, id);
                ps.executeUpdate();
                cargar();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error actualizando estado: " + ex.getMessage());
            }
        }
    }
}

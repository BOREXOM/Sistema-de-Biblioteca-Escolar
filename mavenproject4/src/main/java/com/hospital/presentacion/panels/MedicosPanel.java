package com.hospital.presentacion.panels;

import com.hospital.data.dao.MedicoDAO;
import com.hospital.data.model.Medico;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class MedicosPanel extends JPanel {
    private MedicoDAO dao = new MedicoDAO();
    private JTable tabla;
    private DefaultTableModel modelo;

    public MedicosPanel(String role) {
        setLayout(new BorderLayout());
        JLabel lbl = new JLabel("Médicos");
        lbl.setFont(new Font("Segoe UI", Font.BOLD, 18));
        add(lbl, BorderLayout.NORTH);
        modelo = new DefaultTableModel(new Object[]{"ID","Nombre","Especialidad","Teléfono"}, 0);
        tabla = new JTable(modelo);
        add(new JScrollPane(tabla), BorderLayout.CENTER);
        cargar();
    }

    private void cargar() {
        try {
            modelo.setRowCount(0);
            List<Medico> lista = dao.listar();
            for (Medico m : lista) {
                modelo.addRow(new Object[]{m.getId(), m.getNombre(), m.getEspecialidad(), m.getTelefono()});
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error cargando médicos: " + ex.getMessage());
        }
    }
}

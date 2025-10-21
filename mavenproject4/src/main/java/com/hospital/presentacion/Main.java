package com.hospital.presentacion;

import javax.swing.SwingUtilities;

public class Main {
    public static void main(String[] args) {
        // Ejecutar GUI en event-dispatch thread
        SwingUtilities.invokeLater(() -> {
            LoginFrame lf = new LoginFrame();
            lf.setVisible(true);
        });
    }
}

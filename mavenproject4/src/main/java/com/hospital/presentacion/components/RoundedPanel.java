package com.hospital.presentacion.components;

import javax.swing.*;
import java.awt.*;

public class RoundedPanel extends JPanel {
    private Color backgroundColor;
    private int cornerRadius = 20;

    public RoundedPanel(LayoutManager lm, int radius, Color bgColor) {
        super(lm);
        cornerRadius = radius;
        backgroundColor = bgColor;
        setOpaque(false);
    }

    public RoundedPanel(int radius, Color bgColor) {
        super();
        cornerRadius = radius;
        backgroundColor = bgColor;
        setOpaque(false);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Dimension arcs = new Dimension(cornerRadius, cornerRadius);
        int width = getWidth();
        int height = getHeight();
        Graphics2D graphics = (Graphics2D) g;
        graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // fondo redondeado
        graphics.setColor(backgroundColor != null ? backgroundColor : getBackground());
        graphics.fillRoundRect(0, 0, width-1, height-1, arcs.width, arcs.height);
        // borde
        graphics.setColor(backgroundColor.darker());
        graphics.drawRoundRect(0, 0, width-1, height-1, arcs.width, arcs.height);
    }
}

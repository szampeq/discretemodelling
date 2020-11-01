package com.krzysztgac.discretemodelling.data;

import javax.swing.*;
import java.awt.*;

public class JCanvasCA extends JPanel {

    int width;
    int height;

    public JCanvasCA(int width, int height){
        this.width = width;
        this.height = height;
    }


    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setBackground(Color.white);
        g2.setColor(Color.black);
        g2.drawLine(251, 251, 251,251);
    }

    @Override
    public void repaint() {
        super.repaint();
    }
}
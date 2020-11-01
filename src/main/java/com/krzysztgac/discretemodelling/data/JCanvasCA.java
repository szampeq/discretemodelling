package com.krzysztgac.discretemodelling.data;

import javax.swing.*;
import java.awt.*;

public class JCanvasCA extends JPanel {

    CA caData;

    public JCanvasCA(CA caData){
        this.caData = caData;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setColor(Color.black);
        g2.drawLine(0, 0, 0, 0);

    }

    @Override
    public void repaint() {
        super.repaint();
    }


}
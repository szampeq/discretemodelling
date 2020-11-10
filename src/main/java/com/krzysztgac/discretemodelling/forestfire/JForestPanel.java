package com.krzysztgac.discretemodelling.forestfire;

import javax.swing.*;
import java.awt.*;

public class JForestPanel extends JPanel {

    public ForestData FD;

    public JForestPanel() {

    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g;

        super.repaint();
    }

    @Override
    public void repaint() {
        super.repaint();
    }

}

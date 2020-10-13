package com.krzysztgac.discretemodelling;

import javax.swing.*;
import java.awt.*;


public class JCanvasPanel extends JPanel {

    DataManager dm;

    public JCanvasPanel(DataManager dm){
        this.dm = dm;
    }


    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.drawImage(dm.bgImg, 0, 0, this);
    }

    @Override
    public void repaint() {
        super.repaint();
    }
}

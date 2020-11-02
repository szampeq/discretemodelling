package com.krzysztgac.discretemodelling.data;

import javax.swing.*;
import java.awt.*;

public class JCanvasCA extends JPanel {

    public CA caData;

    public JCanvasCA(CA caData){
        this.caData = caData;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setColor(Color.black);

        for (int j = 0; j < caData.meshSize; j++) {
            for (int i = 0; i < caData.meshSize; i++) {
                if(caData.matrix[i][j] == 1)
                    g2.drawRect(i, j, 0, 0);
            }
        }

        repaint();
    }

    @Override
    public void repaint() {
        super.repaint();
    }


}
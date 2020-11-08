package com.krzysztgac.discretemodelling.data;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class JGameOfLife extends JPanel {

    public GoL golData;

    public JGameOfLife(GoL golData){
        this.golData = golData;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        int MS = golData.getMeshSize();
        int CS = golData.getCellSize();
        Graphics2D g2 = (Graphics2D) g;


        g2.setColor(Color.RED);
        for (int j = 0; j < MS; j++) {
            for (int i = 0; i < MS; i++) {
                if(golData.matrix[i][j] == 1)
                    g2.fillRect(i * CS + CS, j * CS + CS, CS, CS);
            }
        }

        g2.setColor(Color.black);
        g2.drawRect(CS, CS, MS * CS, MS * CS);
        for (int i = CS; i <= MS * CS; i += CS) {
                g2.drawLine(i, CS, i, MS * CS + CS);
                g2.drawLine(CS, i, MS * CS + CS, i);
        }
        super.repaint();
    }

    @Override
    public void repaint() {
        super.repaint();
    }

}
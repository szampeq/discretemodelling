package com.krzysztgac.discretemodelling.data;

import javax.swing.*;
import java.awt.*;

public class JCanvasCA extends JPanel {

    int meshSize;

    public JCanvasCA(int meshSize){
        this.meshSize = meshSize;
    }


    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setColor(Color.black);
        g2.drawLine(meshSize/2, meshSize/2, meshSize/2,meshSize/2);
    }

    @Override
    public void repaint() {
        super.repaint();
    }

    public void setMeshSize(int meshSize){
        this.meshSize = meshSize;
    }

    public int getMeshSize(){
        return this.meshSize;
    }
}
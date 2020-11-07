package com.krzysztgac.discretemodelling.data;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class JGameOfLife extends JPanel {

    public GoL golData;
    private final List<Point> fillCells;
    int mesh = 0;

    public JGameOfLife(GoL golData){
        this.golData = golData;
        fillCells = new ArrayList<>(25);
        mesh = golData.meshSize*10;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        mesh = golData.meshSize*10;
        for (Point fillCell : fillCells) {
            int cellX = 10 + (fillCell.x * 10);
            int cellY = 10 + (fillCell.y * 10);
            g.setColor(Color.RED);
            g.fillRect(cellX, cellY, 10, 10);
        }
        g.setColor(Color.BLACK);
        g.drawRect(10, 10, mesh, mesh);

        for (int i = 10; i <= mesh; i += 10) {
            g.drawLine(i, 10, i, mesh + 10);
            g.drawLine(10, i, mesh + 10, i);
        }
    }

    public void fillCell(int x, int y) {
        fillCells.add(new Point(x, y));
        repaint();
    }



    @Override
    public void repaint() {
        super.repaint();
    }


}
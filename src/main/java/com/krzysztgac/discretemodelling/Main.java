package com.krzysztgac.discretemodelling;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Main extends JFrame {

    static DataManager dm;
    private JCanvasPanel canvasPanel;
    Utils utils;

    public Main(String title){
        super(title);

        // =================================

        dm = new DataManager();
        canvasPanel = new JCanvasPanel(dm);
        utils = new Utils(dm);

        // ========== IMAGE READER ==========

        try {
            dm.bgImg = ImageIO.read(new File("src/main/resources/Mapa_MD_no_terrain_low_res_dark_Gray.bmp"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        // ===================================

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        mainPanel.add(BorderLayout.CENTER, canvasPanel);

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setContentPane(mainPanel);

        this.setSize(new Dimension(900, 600));
        this.setLocationRelativeTo(null);

        canvasPanel.repaint();
    }

    public static void main(String[] args) {

        Main mw = new Main("Discrete Modelling");
        mw.setVisible(true);

        Utils utils = new Utils(dm);
        utils.darkImage();
        mw.canvasPanel.repaint();

    }
}

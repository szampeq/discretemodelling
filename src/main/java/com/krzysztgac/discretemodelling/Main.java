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
    private JPanel mainPanel;
    private JPanel buttonPanel;
    private JCanvasPanel canvasPanel;
    Utils utils;

    public Main(String title){
        super(title);

        // =================================

        dm = new DataManager();
        canvasPanel = new JCanvasPanel(dm);

        mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());

        // ============ BUTTONS ============

        buttonPanel = new JPanel();

        JButton refreshImage = new JButton("Load/Refresh Image");
        refreshImage.addActionListener(e -> {
            try {
                dm.bgImg = ImageIO.read(new File("src/main/resources/Mapa_MD_no_terrain_low_res_dark_Gray.bmp"));
            } catch (IOException f) {
                f.printStackTrace();
            }
            canvasPanel.repaint();
        });

        JButton brightnessUp = new JButton("Brightness +");
        brightnessUp.addActionListener(e -> {
            utils.imageBrightness(10);
            canvasPanel.repaint();
        });

        JButton brightnessDown = new JButton("Brightness -");
        brightnessDown.addActionListener(e -> {
            utils.imageBrightness(-10);
            canvasPanel.repaint();
        });

        JButton blackAndWhite = new JButton("B & W");
        blackAndWhite.addActionListener(e -> {
            utils.blackAndWhite(175);
            canvasPanel.repaint();
        });

        buttonPanel.add(refreshImage);
        buttonPanel.add(brightnessUp);
        buttonPanel.add(brightnessDown);
        buttonPanel.add(blackAndWhite);

        buttonPanel.setLayout(new GridLayout(7, 2));

        // =========== MAIN PANEL ==========
        mainPanel.add(BorderLayout.CENTER, canvasPanel);
        mainPanel.add(BorderLayout.EAST, buttonPanel);

        utils = new Utils(dm);

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
        mw.canvasPanel.repaint();

    }
}

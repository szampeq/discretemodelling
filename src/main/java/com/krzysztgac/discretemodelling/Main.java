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
    private static JCanvasPanel canvasPanel;
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

        JButton refreshImage = newButton("Refresh Image");
        refreshImage.addActionListener(e -> {
            loadImage();
        });

        JButton brightnessUp = newButton("Brightness +");
        brightnessUp.addActionListener(e -> {
            utils.imageBrightness(10);
            canvasPanel.repaint();
        });

        JButton brightnessDown = newButton("Brightness -");
        brightnessDown.addActionListener(e -> {
            utils.imageBrightness(-10);
            canvasPanel.repaint();
        });

        JButton blackAndWhite = newButton("B & W");
        blackAndWhite.addActionListener(e -> {
            utils.blackAndWhite(175);
            canvasPanel.repaint();
        });

        buttonPanel.add(refreshImage);
        buttonPanel.add(brightnessUp);
        buttonPanel.add(brightnessDown);
        buttonPanel.add(blackAndWhite);

        buttonPanel.setLayout(new GridLayout(4, 1));

        // =========== MAIN PANEL ==========
        mainPanel.add(BorderLayout.CENTER, canvasPanel);
        mainPanel.add(BorderLayout.EAST, buttonPanel);
        canvasPanel.setBackground(Color.DARK_GRAY);
        buttonPanel.setBorder(BorderFactory.createLineBorder(Color.WHITE));
        buttonPanel.setBackground(Color.DARK_GRAY);

        utils = new Utils(dm);

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setContentPane(mainPanel);

        this.setSize(new Dimension(760, 370));
        this.setLocationRelativeTo(null);

        canvasPanel.repaint();
    }

    public static void main(String[] args) {

        Main mw = new Main("Discrete Modelling");
        mw.setVisible(true);
        loadImage();
        canvasPanel.repaint();

    }

    static void loadImage(){
        try {
            dm.bgImg = ImageIO.read(new File("src/main/resources/Mapa_MD_no_terrain_low_res_dark_Gray.bmp"));
        } catch (IOException f) {
            f.printStackTrace();
        }
        canvasPanel.repaint();
    }

    JButton newButton(String text){
        JButton button = new JButton(text);
        button.setBackground(Color.WHITE);
        button.setForeground(Color.DARK_GRAY);
        button.setFont(new Font("Tahoma",Font.BOLD,14));
        buttonPanel.add(button);
        return button;
    }
}

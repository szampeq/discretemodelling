package com.krzysztgac.discretemodelling;

import com.krzysztgac.discretemodelling.data.DataManager;
import com.krzysztgac.discretemodelling.data.JCanvasCA;
import com.krzysztgac.discretemodelling.data.JCanvasPanel;
import com.krzysztgac.discretemodelling.tools.SimpleTools;
import com.krzysztgac.discretemodelling.tools.Utils;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.Objects;

import static com.krzysztgac.discretemodelling.tools.SimpleTools.loadImage;

public class Main extends JFrame {

    static DataManager dm;
    private final JPanel buttonPanel;
    private final JPanel mainPanel;
    private static JCanvasPanel canvasPanel;
    private static JCanvasCA canvasCA;
    static Utils utils;

    public Main(String title){
        super(title);

        // =================================

        dm = new DataManager();
        canvasPanel = new JCanvasPanel(dm);
        canvasCA = new JCanvasCA(251, 251);

        mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        buttonPanel = new JPanel();

        // ============ PROGRAM SETTINGS =========

        String[] settings = {"Image", "CA"};
        JLabel settingsLabel = new JLabel("Select program:");
        JComboBox<String> programSet = new JComboBox<>(settings);
        buttonPanel.add(settingsLabel);
        buttonPanel.add(programSet);

        JOptionPane.showMessageDialog( null, programSet, "Select program", JOptionPane.QUESTION_MESSAGE);
        String program = Objects.requireNonNull(programSet.getSelectedItem()).toString();
        System.out.println(program);

        // ============ BUTTONS ============

        if (program.equals("Image"))
            imageProgram();
        else if (program.equals("CA"))
            caProgram();
        else
            imageProgram();

        // =========== MAIN PANEL ==========

/*
        mainPanel.add(BorderLayout.CENTER, canvasCA);

*/
        mainPanel.add(BorderLayout.EAST, buttonPanel);

        buttonPanel.setBorder(BorderFactory.createLineBorder(Color.WHITE));
        buttonPanel.setBackground(Color.WHITE);

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setContentPane(mainPanel);

        this.setSize(new Dimension(800, 400));
        this.setLocationRelativeTo(null);

        canvasPanel.repaint();
        canvasCA.repaint();
    }

    public static void main(String[] args) {

        Main mw = new Main("Discrete Modelling");
        mw.setVisible(true);
        loadImage(dm, canvasPanel);
        utils = new Utils(dm);
        canvasPanel.repaint();
        canvasCA.repaint();
    }

    public void imageProgram(){
        Button refreshImage = new Button("Refresh Image", buttonPanel);
        refreshImage.button.addActionListener(e -> loadImage(dm, canvasPanel));

        Button brightnessUp = new Button("Brightness +", buttonPanel);
        brightnessUp.button.addActionListener(e -> {
            utils.imageBrightness(10);
            canvasPanel.repaint();
        });

        Button brightnessDown = new Button("Brightness -", buttonPanel);
        brightnessDown.button.addActionListener(e -> {
            utils.imageBrightness(-10);
            canvasPanel.repaint();
        });

        // ============== BINARIZATION ===============

        JLabel binarizationLabel = new JLabel("Binarization treshodl:");
        Integer[] rgb = new Integer[256];
        for (int i = 0; i < 256; i++){
            rgb[i] = i;
        }
        JComboBox<Integer> binarizationValue = new JComboBox<>(rgb);
        binarizationValue.setSelectedItem(rgb[180]);

        buttonPanel.add(binarizationLabel);
        buttonPanel.add(binarizationValue);

        Button binarization = new Button("Binarization", buttonPanel);
        binarization.button.addActionListener(e -> {
            Integer selectedValue = (Integer) binarizationValue.getSelectedItem();
            if (selectedValue != null) {
                utils.binarization(selectedValue);
                canvasPanel.repaint();
            }
        });
        JLabel TEST = new JLabel("TEST:");
        canvasCA.add(TEST);
        // FILTERS

        String[] filters = {"LowPass", "HighPass", "Gauss"};
        JLabel filterLabel = new JLabel("Select filter:");
        JComboBox<String> selectFilter = new JComboBox<>(filters);

        buttonPanel.add(filterLabel);
        buttonPanel.add(selectFilter);

        Button filter = new Button("Filter", buttonPanel);
        filter.button.addActionListener(e -> {
            String selectedFilter =  (String) selectFilter.getSelectedItem();
            assert selectedFilter != null;
            utils.putFilterOn(selectedFilter);
            canvasPanel.repaint();
        });

        Button erosion = new Button("Erosion", buttonPanel);
        erosion.button.addActionListener(e -> {
            utils.erosionDilatation("Erosion");
            canvasPanel.repaint();
        });

        Button dilatation = new Button("Dilatation", buttonPanel);
        dilatation.button.addActionListener(e -> {
            utils.erosionDilatation("Dilatation");
            canvasPanel.repaint();
        });

        Button morphOpening = new Button("Morph. Opening", buttonPanel);
        morphOpening.button.addActionListener(e -> {
            utils.morphologicalOpening();
            canvasPanel.repaint();
        });

        Button morphClosure = new Button("Morph. Closure", buttonPanel);
        morphClosure.button.addActionListener(e -> {
            utils.morphologicalClosure();
            canvasPanel.repaint();
        });

        Button reverse = new Button("Reverse", buttonPanel);
        reverse.button.addActionListener(e -> {
            utils.reverse();
            canvasPanel.repaint();
        });

        buttonPanel.setLayout(new GridLayout(18, 1));

        mainPanel.add(BorderLayout.CENTER, canvasPanel);
        canvasPanel.setBackground(Color.DARK_GRAY);
    }

    public void caProgram(){

    }

}

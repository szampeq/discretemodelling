package com.krzysztgac.discretemodelling;

import com.krzysztgac.discretemodelling.data.DataManager;
import com.krzysztgac.discretemodelling.data.JCanvasCA;
import com.krzysztgac.discretemodelling.data.JCanvasPanel;
import com.krzysztgac.discretemodelling.data.CA;
import com.krzysztgac.discretemodelling.tools.Utils;

import javax.swing.*;
import java.awt.*;
import java.util.Hashtable;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

import static com.krzysztgac.discretemodelling.data.DataManager.programSettings;
import static com.krzysztgac.discretemodelling.tools.SimpleTools.loadImage;

public class Main extends JFrame {

    static DataManager dm;
    private final JPanel buttonPanel;
    private final JPanel mainPanel;
    private static JCanvasPanel canvasPanel;
    static CA caSetup;
    private static JCanvasCA canvasCA;
    static Utils utils;

    public Main(String title){
        super(title);

        // =================================

        dm = new DataManager();
        canvasPanel = new JCanvasPanel(dm);
        caSetup = new CA();
        canvasCA = new JCanvasCA(caSetup);

        mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        buttonPanel = new JPanel();

        // ============ PROGRAM SETTINGS =========

        String setting = programSettings();
        if (setting.equals("Image"))
            imageProgram();
        else if (setting.equals("CA"))
            caProgram();
        else
            imageProgram();

        // =========== MAIN PANEL ==========

        mainPanel.add(BorderLayout.EAST, buttonPanel);

        buttonPanel.setBorder(BorderFactory.createLineBorder(Color.WHITE));
        buttonPanel.setBackground(Color.WHITE);

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setContentPane(mainPanel);

        this.setSize(new Dimension(800, 400));
        this.setLocationRelativeTo(null);

    }

    public static void main(String[] args) {

        Main mw = new Main("Discrete Modelling");
        mw.setVisible(true);
        loadImage(dm, canvasPanel);
        utils = new Utils(dm);
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

        JLabel meshLabel = new JLabel("Select mesh size:");
        Integer[] meshValue = new Integer[512];
        for (int i = 0; i < 512; i++){
            meshValue[i] = i;
        }
        JComboBox<Integer> meshSize = new JComboBox<>(meshValue);
        meshSize.setSelectedItem(meshValue[256]);

        buttonPanel.add(meshLabel);
        buttonPanel.add(meshSize);

        JLabel condtionsLabel = new JLabel("Boundary Conditions:");
        buttonPanel.add(condtionsLabel);

        String[] boundaryConditions = {"Periodic", "1-edges"};
        JComboBox<String> selectCondition = new JComboBox<>(boundaryConditions);
        buttonPanel.add(selectCondition);


        JLabel ruleLabel = new JLabel("Waiting for rule value...");
        buttonPanel.add(ruleLabel);

        JSlider ruleSlider = new JSlider(0, 225);
        AtomicInteger ruleValue = new AtomicInteger();
        ruleSlider.addChangeListener(e-> {
            ruleValue.set(ruleSlider.getValue());
            ruleLabel.setText("Rule: " + ruleValue);
        });

        ruleSlider.setMajorTickSpacing(30);
        ruleSlider.setPaintTicks(true);
        Hashtable<Integer, JLabel> labelTable = new Hashtable<>();
        labelTable.put(0, new JLabel("0") );
        labelTable.put(30, new JLabel("30") );
        labelTable.put(60, new JLabel("60") );
        labelTable.put(90, new JLabel("90") );
        labelTable.put(120, new JLabel("120") );
        labelTable.put(150, new JLabel("150") );
        labelTable.put(180, new JLabel("180") );
        labelTable.put(225, new JLabel("225") );

        ruleSlider.setLabelTable( labelTable );
        ruleSlider.setPaintLabels(true);
        buttonPanel.add(ruleSlider);

        AtomicInteger selectedMesh = new AtomicInteger();
        buttonPanel.setLayout(new GridLayout(7, 1));


        Button run = new Button("Run", buttonPanel);
        run.button.addActionListener(e -> {
            // MESH SIZE
            selectedMesh.set((Integer) meshSize.getSelectedItem());
            canvasCA.caData.setMeshSize(selectedMesh.intValue());
            // RULESET
            canvasCA.caData.setRuleSet(ruleValue.intValue());
            // BOUNDARY CONDITION
            String selectedBoundary =  (String) selectCondition.getSelectedItem();
            canvasCA.caData.setBoundaryPeriodic(selectedBoundary.equals("Periodic"));
            // SET MATRIX
            canvasCA.caData.fillMatrix();
            // REPAINT
            canvasCA.repaint();
        });

        mainPanel.add(canvasCA);

    }

}

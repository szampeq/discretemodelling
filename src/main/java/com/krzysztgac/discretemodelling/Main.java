/*
 Discrete modelling classes (AGH)
         @author  Krzysztof Gąciarz
         Github-project: https://github.com/szampeq/discretemodelling
 */

package com.krzysztgac.discretemodelling;

import com.krzysztgac.discretemodelling.data.*;
import com.krzysztgac.discretemodelling.tools.GamePatternTXT;
import com.krzysztgac.discretemodelling.tools.SaveImage;
import com.krzysztgac.discretemodelling.tools.Utils;

import javax.swing.*;
import javax.swing.filechooser.FileSystemView;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Hashtable;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

import static com.krzysztgac.discretemodelling.data.DataManager.programSettings;
import static com.krzysztgac.discretemodelling.tools.SimpleTools.loadImage;

public class Main extends JFrame {

    static DataManager dm;
    private final JPanel buttonPanel;
    private final JPanel mainPanel;
    private static JCanvasPanel canvasPanel;
    static CA caSetup;
    private static JCanvasCA canvasCA;
    static GoL gameSetup;
    private static JGameOfLife golPanel;
    static Utils utils;

    public Main(String title){
        super(title);

        // =================================

        dm = new DataManager();
        canvasPanel = new JCanvasPanel(dm);
        caSetup = new CA();
        canvasCA = new JCanvasCA(caSetup);
        gameSetup = new GoL();
        golPanel = new JGameOfLife(gameSetup);

        mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        buttonPanel = new JPanel();

        // ============ PROGRAM SETTINGS =========

        String setting = programSettings();
        switch (setting) {
            case "Image":
                imageProgram();
                break;
            case "CA":
                caProgram();
                break;
            case "Game of Life":
                gameOfLife();
                break;
            default:
                break;
        }

        // =========== MAIN PANEL ==========

        mainPanel.add(BorderLayout.EAST, buttonPanel);

        buttonPanel.setBorder(BorderFactory.createLineBorder(Color.WHITE));
        buttonPanel.setBackground(Color.WHITE);

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setContentPane(mainPanel);

        this.setSize(new Dimension(820, 710));
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

        Button saveMap = new Button("Save Map", buttonPanel);
        saveMap.button.addActionListener(e -> {
            SaveImage.takeSnapShot(dm);
        });

        buttonPanel.setLayout(new GridLayout(18, 1));

        mainPanel.add(BorderLayout.CENTER, canvasPanel);
        canvasPanel.setBackground(Color.DARK_GRAY);
    }

    public void caProgram(){

        JLabel meshLabel = new JLabel("Select mesh size:");
        Integer[] meshValue = new Integer[2000];
        for (int i = 0; i < 2000; i++){
            meshValue[i] = i;
        }
        JComboBox<Integer> meshSize = new JComboBox<>(meshValue);
        meshSize.setSelectedItem(meshValue[500]);

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
            canvasCA.caData.generateCA();

            // REPAINT
            canvasCA.repaint();
        });

        mainPanel.add(canvasCA);

    }

    public void gameOfLife(){

        buttonPanel.setLayout(new GridLayout(17, 1));

        JLabel meshLabel = new JLabel("Select mesh size:");
        Integer[] meshValue = new Integer[100];
        for (int i = 1; i <= 100; i++){
            meshValue[i-1] = i;
        }
        JComboBox<Integer> meshSize = new JComboBox<>(meshValue);
        meshSize.setSelectedItem(meshValue[64]);

        buttonPanel.add(meshLabel);
        buttonPanel.add(meshSize);

        JLabel cellSizeLabel = new JLabel("Select cell size:");
        Integer[] cellSizeValue = new Integer[20];
        for (int i = 1; i <= 20; i++){
            cellSizeValue[i-1] = i;
        }
        JComboBox<Integer> cellSize = new JComboBox<>(cellSizeValue);
        cellSize.setSelectedItem(cellSizeValue[9]);

        buttonPanel.add(cellSizeLabel);
        buttonPanel.add(cellSize);

        JLabel conditionsLabel = new JLabel("Initial States:");
        buttonPanel.add(conditionsLabel);

        String[] initialStates = {"Unchanging", "Glider", "Oscilation", "Random", "Empty"};
        JComboBox<String> selectState = new JComboBox<>(initialStates);

        buttonPanel.add(selectState);
        JLabel neighborhoodLabel = new JLabel("Select neighborhood:");
        buttonPanel.add(neighborhoodLabel);

        String[] neighborhoods = {"Moore"};
        JComboBox<String> selectNeighborhood = new JComboBox<>(neighborhoods);

        buttonPanel.add(selectNeighborhood);

        AtomicInteger selectedMesh = new AtomicInteger();
        AtomicInteger selectedCellSize = new AtomicInteger();

        JLabel boardTools = new JLabel("Board Tools: ");
        Button clear = new Button("Clear board", buttonPanel);
        clear.button.addActionListener(e -> {
            golPanel.golData.zeroMatrix();
            golPanel.repaint();
        });
        buttonPanel.add(boardTools);

        AtomicBoolean isBoardCreated = new AtomicBoolean(false);

        Button board = new Button("Create board", buttonPanel);
        board.button.addActionListener(e -> {
            // MESH SIZE
            selectedMesh.set((Integer) meshSize.getSelectedItem());
            golPanel.golData.setMeshSize(selectedMesh.intValue());
            // CELL SIZE
            selectedCellSize.set((Integer) cellSize.getSelectedItem());
            golPanel.golData.setCellSize(selectedCellSize.intValue());
            // INITIAL STATE
            String selectedState =  (String) selectState.getSelectedItem();
            golPanel.golData.setInitialState(selectedState);
            // NEIGHBORHOOD
            // String selectedNeighborhood = (String) selectNeighborhood.getSelectedItem();
            // golPanel.golData.setNeighborhood(selectedNeighborhood);
            // SET MATRIX
            golPanel.golData.fillMatrix();
            //golPanel.golData.generateCA();
            isBoardCreated.set(true);
            // REPAINT
            golPanel.repaint();
        });

        // =========================================================

        JLabel patterns = new JLabel("Save/Load Pattern: ");
        buttonPanel.add(patterns);

        Button saveButton = new Button("Save pattern", buttonPanel);
        saveButton.button.addActionListener(e -> {
            if (isBoardCreated.get())
                GamePatternTXT.savePattern(golPanel.golData);
        });

        Button openButton = new Button("Load pattern", buttonPanel);
        JFileChooser pattern = new JFileChooser(new File("src/main/resources/patterns/"));

        openButton.button.addActionListener(e -> {
            int result = pattern.showOpenDialog(this);
            if (result == JFileChooser.APPROVE_OPTION) {
                File selectedFile = pattern.getSelectedFile();
                String patternPath = selectedFile.getAbsolutePath();
                try {
                    golPanel.golData.setMatrix(GamePatternTXT.openPattern(patternPath));
                } catch (FileNotFoundException fileNotFoundException) {
                    fileNotFoundException.printStackTrace();
                }
            }
        });
        // ==========================================================

        JLabel gameSetup = new JLabel("Run/Stop Game: ");
        buttonPanel.add(gameSetup);
        Button startGame = new Button("Start game!", buttonPanel);
        AtomicBoolean isGameStarted = new AtomicBoolean(false);

        // =============== THREAD TO RUN GAME ================

        final ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();
        executorService.scheduleAtFixedRate(() -> {

            if (isGameStarted.get()) {
                golPanel.golData.cellNeighborhood();
            }

        }, 0, 100, TimeUnit.MILLISECONDS);

        // ========================================================

        startGame.button.addActionListener(e -> {
            if (isBoardCreated.get()) {
                isGameStarted.set(true);
            }
        });

        Button stopGame = new Button("Stop game!", buttonPanel);

        stopGame.button.addActionListener(e -> isGameStarted.set(false));

        // =========================================================

        mainPanel.add(golPanel);

        final int windowXCorrection = 7;
        final int windowYCorrection = 30;

        addMouseListener(new MouseAdapter() {

            @Override
            public void mousePressed(MouseEvent e) {
                int x = e.getX() - (windowXCorrection + golPanel.golData.getCellSize());
                int y = e.getY() - (windowYCorrection + golPanel.golData.getCellSize());

                int cellX = x/golPanel.golData.getCellSize();
                int cellY = y/golPanel.golData.getCellSize();

                if (isBoardCreated.get())
                    golPanel.golData.changeMatrixCell(cellX, cellY);
            }

        });

        addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {

                super.mouseDragged(e);
                int x = e.getX() - (windowXCorrection + golPanel.golData.getCellSize());
                int y = e.getY() - (windowYCorrection + golPanel.golData.getCellSize());

                int cellX = x/golPanel.golData.getCellSize();
                int cellY = y/golPanel.golData.getCellSize();

                if (isBoardCreated.get()){
                    if (SwingUtilities.isRightMouseButton(e))
                        golPanel.golData.drawFillMatrixCell(cellX, cellY, 0);
                    else if (SwingUtilities.isLeftMouseButton(e))
                        golPanel.golData.drawFillMatrixCell(cellX, cellY, 1);
                }

            }
        });

    }

}

package com.krzysztgac.discretemodelling.data;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Objects;

public class DataManager {
    public BufferedImage bgImg;

    public static String programSettings(){
        String[] settings = {"Image", "CA"};
        JComboBox<String> programSet = new JComboBox<>(settings);

        JOptionPane.showMessageDialog( null, programSet, "Select program", JOptionPane.QUESTION_MESSAGE);
        return Objects.requireNonNull(programSet.getSelectedItem()).toString();

    }
}

package com.krzysztgac.discretemodelling.tools;

import java.awt.*;
import java.awt.image.BufferedImage;

public abstract class SimpleTools {

    static double findArrayMin(double[][] array) {
        double min = array[0][0];
        for (int i = 0; i < array.length; i++)
            for (int j = 0; j < array[0].length; j++)
                if (array[i][j] < min)
                    min = array[i][j];
        return min;
    }

    static double findArrayMax(double[][] array) {
        double max = array[0][0];
        for (int i = 0; i < array.length; i++)
            for (int j = 0; j < array[0].length; j++)
                if (array[i][j] > max)
                    max = array[i][j];
        return max;
    }

    static void fillArrayRedColor(double[][] array, BufferedImage img) {
        for (int x = 0; x < img.getWidth(); x++)
            for (int y = 0; y < img.getHeight(); y++) {
                Color readColor = new Color(img.getRGB(x, y));
                int R = readColor.getRed();
                array[x][y] = R;
            }
    }
}

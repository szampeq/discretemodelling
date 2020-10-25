package com.krzysztgac.discretemodelling.tools;

import com.krzysztgac.discretemodelling.data.DataManager;
import com.krzysztgac.discretemodelling.data.JCanvasPanel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public abstract class SimpleTools {

    public static void loadImage(DataManager dm, JCanvasPanel canvasPanel){
        try {
            dm.bgImg = ImageIO.read(new File("src/main/resources/Mapa_MD_no_terrain_low_res_dark_Gray.bmp"));
        } catch (IOException f) {
            f.printStackTrace();
        }
        canvasPanel.repaint();
    }

    static double findArrayMin(double[][] array) {
        double min = array[0][0];
        for (double[] doubles : array)
            for (int j = 0; j < array[0].length; j++)
                if (doubles[j] < min)
                    min = doubles[j];
        return min;
    }

    static double findArrayMax(double[][] array) {
        double max = array[0][0];
        for (double[] doubles : array)
            for (int j = 0; j < array[0].length; j++)
                if (doubles[j] > max)
                    max = doubles[j];
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

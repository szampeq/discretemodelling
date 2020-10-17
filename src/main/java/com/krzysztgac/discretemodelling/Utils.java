package com.krzysztgac.discretemodelling;

import java.awt.*;

public class Utils {

    DataManager dm;

    public Utils(DataManager dm){
        this.dm = dm;
    }

    void imageBrightness(int value) {

        int width = dm.bgImg.getWidth();
        int height = dm.bgImg.getHeight();

        for (int i = 0; i < width; i++){
            for (int j = 0; j < height; j++){

                Color readColor = new Color(dm.bgImg.getRGB(i, j));
                int R = readColor.getRed();
                int G = readColor.getGreen();
                int B = readColor.getBlue();

                if (R + value >=0 && R + value <= 255)
                    R += value;
                if (G + value >=0 && G + value <= 255)
                    G += value;
                if (B + value >=0 && B + value <= 255)
                    B += value;

                int RGB = new Color(R, G, B).getRGB();

                dm.bgImg.setRGB(i, j, RGB);

            }
        }

    }



}

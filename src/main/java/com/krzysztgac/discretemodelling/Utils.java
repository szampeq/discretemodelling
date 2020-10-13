package com.krzysztgac.discretemodelling;

import java.awt.*;

public class Utils {

    DataManager dm;

    public Utils(DataManager dm){
        this.dm = dm;
    }

    void darkImage() {

        int width = dm.bgImg.getWidth();
        int height = dm.bgImg.getHeight();

        for (int i = 0; i < width; i++){
            for (int j = 0; j < height; j++){

                Color readColor = new Color(dm.bgImg.getRGB(i, j));
                int R = readColor.getRed();
                int G = readColor.getGreen();
                int B = readColor.getBlue();

                if (R >= 10)
                    R -= 10;
                if (G >= 10)
                    G -= 10;
                if (B >= 10)
                    B -= 10;

                int RGB = new Color(R, G, B).getRGB();

                dm.bgImg.setRGB(i, j, RGB);

            }
        }

    }



}

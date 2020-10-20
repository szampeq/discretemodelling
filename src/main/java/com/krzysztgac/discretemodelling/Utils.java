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

    void binarization(int value) {

        int width = dm.bgImg.getWidth();
        int height = dm.bgImg.getHeight();

        for (int i = 0; i < width; i++){
            for (int j = 0; j < height; j++){

                Color readColor = new Color(dm.bgImg.getRGB(i, j));
                int R = readColor.getRed();
                int G = readColor.getGreen();
                int B = readColor.getBlue();

                if (R >= value)
                    R = 255;
                else
                    R = 0;
                if (G >= value)
                    G = 255;
                else
                    G = 0;
                if (B >= value)
                    B = 255;
                else
                    B = 0;

                int RGB = new Color(R, G, B).getRGB();

                dm.bgImg.setRGB(i, j, RGB);
            }
        }
    }

    void reverse() {

        int width = dm.bgImg.getWidth();
        int height = dm.bgImg.getHeight();

        for (int i = 0; i < width; i++){
            for (int j = 0; j < height; j++){

                Color readColor = new Color(dm.bgImg.getRGB(i, j));
                int R = 255 - readColor.getRed();
                int G = 255 - readColor.getGreen();
                int B = 255 - readColor.getBlue();


                int RGB = new Color(R, G, B).getRGB();

                dm.bgImg.setRGB(i, j, RGB);
            }
        }
    }

    void putFilterOn(String mask){
        int width = dm.bgImg.getWidth();
        int height = dm.bgImg.getHeight();

        int[][] image = new int[width][height];

        for (int w = 0; w < image.length; w++)
            for (int h = 0; h < image[0].length; h++) {
                image[w][h] = dm.bgImg.getRGB(w, h);
            }

        int[][] lowpassFilter = {{1, 1, 1},
                                 {1, 1, 1},
                                 {1, 1, 1}};

        int[][] highpassFilter = {{1, 4, 1},
                                  {4, 32, 4},
                                  {1, 4, 1}};

        int[][] gaussFilter = {{-1, -1, -1},
                               {-1, 9, -1},
                               {-1, -1, -1}};

        int[][] filter;

        if (mask.equals("LowPass"))
            filter = lowpassFilter;
        else if(mask.equals("HighPass"))
            filter = highpassFilter;
        else
            filter = gaussFilter;


        for (int i = 0; i < image.length; i++){
            for (int j = 0; j < image[i].length; j++){

                int v = 0;

                for (int m = 0; m < filter.length; m++)
                    for (int n = 0; n < filter[0].length; n++){
                        int x = i + m;
                        int y = j + n;

                        if (x < 0 || y < 0) continue;
						if (x > image.length - 1) continue;
						if (y > image[i].length - 1) continue;

                        x = Math.min(x, image.length - 1);
                        y = Math.min(y, image[i].length - 1);

                        v += (image[x][y]) * (filter[n][m]);

                    }

                    dm.bgImg.setRGB(i, j, v);
            }
        }
    }


}

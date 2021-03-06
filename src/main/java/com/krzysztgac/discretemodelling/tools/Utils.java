package com.krzysztgac.discretemodelling.tools;

import com.krzysztgac.discretemodelling.data.DataManager;

import java.awt.*;

import static com.krzysztgac.discretemodelling.tools.SimpleTools.*;


public class Utils {

    DataManager dm;
    int width;
    int height;

    public Utils(DataManager dm){
        this.dm = dm;
        this.width = dm.bgImg.getWidth();
        this.height = dm.bgImg.getHeight();
    }

    public void imageBrightness(int value) {

        for (int i = 0; i < width; i++){
            for (int j = 0; j < height; j++){

                Color readColor = new Color(dm.bgImg.getRGB(i, j));
                int R = readColor.getRed();


                if (R + value >=0 && R + value <= 255)
                    R += value;

                int RGB = new Color(R, R, R).getRGB();
                dm.bgImg.setRGB(i, j, RGB);
            }
        }
    }

    public void binarization(int value) {

        for (int i = 0; i < width; i++){
            for (int j = 0; j < height; j++){

                Color readColor = new Color(dm.bgImg.getRGB(i, j));
                int R = readColor.getRed();

                if (R >= value)
                    R = 255;
                else
                    R = 0;


                int RGB = new Color(R, R, R).getRGB();

                dm.bgImg.setRGB(i, j, RGB);
            }
        }
    }

    public void reverse() {

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

    // CONVOLUTION (LowPass Filter, HighPass Filter and Gauss Filter)

    public void putFilterOn(String mask){

        double[][] image = new double[width][height];

        fillArrayRedColor(image, dm.bgImg);

        double[][] lowpassFilter = {
                    {1.0/9.0, 1.0/9.0, 1.0/9.0},
                    {1.0/9.0, 1.0/9.0, 1.0/9.0},
                    {1.0/9.0, 1.0/9.0, 1.0/9.0}
            };

        double[][] highpassFilter = {
                    {-1.0, -1.0, -1.0},
                    {-1.0, 9.0, -1.0},
                    {-1.0, -1.0, -1.0}
                };

        double[][] gaussFilter = {
                {1.0/16.0, 1.0/8.0, 1.0/16.0},
                {1.0/8.0, 1.0/4.0, 1.0/8.0},
                {1.0/16.0, 1.0/8.0, 1.0/16.0}};

        double[][] filter;

        if (mask.equals("LowPass"))
            filter = lowpassFilter;
        else if(mask.equals("HighPass"))
            filter = highpassFilter;
        else
            filter = gaussFilter;

        for (int x = 0; x < width; x++){
            for (int y = 0; y < height; y++){

                int v = 0; // new RGB value

                for (int m = 0; m < filter.length; m++) // Mask Columns
                    for (int n = 0; n < filter[0].length; n++){ // Mask Rows

                        int i = x - filter.length/2 + m;
                        int j = y - filter[0].length/2 + n;

                        if (i < 0 || j < 0) continue;
                        if (i > width - 1) continue;
                        if (j > height - 1) continue;

                        v += (image[i][j]) * (filter[n][m]);

                    }

                int RGB;
                    if ( v > 0 && v < 256) {
                        RGB = new Color(v, v, v).getRGB();
                        dm.bgImg.setRGB(x, y, RGB);
                    }
            }
        }
    }


    public void erosionDilatation(String function) {

        double[][] image = new double[width][height];
        fillArrayRedColor(image, dm.bgImg);

        double minRGB = findArrayMin(image);
        double maxRGB = findArrayMax(image);
        int flagValue;

        if (function.equals("Dilatation"))
            flagValue = (int)minRGB;
        else
            flagValue = (int)maxRGB;


        for (int x = 1; x < width - 1; x++)
            for (int y = 1; y < height - 1; y++) {

                boolean flag = false;

                for (int m = -1; m <= 1; m++)
                    for (int n = -1; n <= 1; n++) {

                        int i = x + m;
                        int j = y + n;

                        if (i < 0 || j < 0) continue;
                        if (i > width - 1) continue;
                        if (j > height - 1) continue;

                        if (image[i][j] == flagValue) {
                            flag = true;
                            break;
                        }
                    }
                if (flag) {
                    int RGB = new Color(flagValue, flagValue, flagValue).getRGB();
                    dm.bgImg.setRGB(x, y, RGB);
                }
            }
    }

    public void morphologicalOpening(){
        erosionDilatation("Erosion");
        erosionDilatation("Dilatation");
    }

    public void morphologicalClosure(){
        erosionDilatation("Dilatation");
        erosionDilatation("Erosion");
    }



}
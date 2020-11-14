package com.krzysztgac.discretemodelling.tools;

import com.krzysztgac.discretemodelling.data.DataManager;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.xml.crypto.Data;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;

public class SaveImage {
    public static int numerScreena = 0;
    public static void takeSnapShot(DataManager dm){
        BufferedImage bufImage = new BufferedImage(dm.bgImg.getWidth(), dm.bgImg.getHeight(),BufferedImage.TYPE_INT_RGB);
        String numerPliku = Integer.toString(numerScreena);


        for (int x = 0; x < dm.bgImg.getWidth(); x++) {
            for (int y = 0; y < dm.bgImg.getHeight(); y++) {
                    bufImage.setRGB(x, y, dm.bgImg.getRGB(x, y));
            }
        }

        File imageFile = new File("src/main/resources/SaveImages/image"+numerPliku+".jpg");
        numerScreena++;
        System.out.println("Zrobiono zrzut ekranu!");
        try{
            imageFile.createNewFile();
            ImageIO.write(bufImage, "jpg", imageFile);
        }catch(Exception ignored){
        }
    }
}
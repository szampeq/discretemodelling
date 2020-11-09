package com.krzysztgac.discretemodelling.tools;

import com.krzysztgac.discretemodelling.data.GoL;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class GamePatternTXT {
    public static int numberTXT = 0;
    public static void savePattern(GoL gol){

        try
        {
            FileWriter txt = new FileWriter("src/main/resources/patterns/pattern" + numberTXT + ".txt");
            int[][] matrix = gol.getMatrix();
            txt.write(gol.getMeshSize() + "\n");
            for (int i = 0; i < gol.getMeshSize(); i++) {
                for (int j = 0; j < gol.getMeshSize(); j++) {
                    if(matrix[i][j] == 0)
                        txt.write("0");
                    else
                        txt.write("1");
                }
                txt.write("\n");
            }
            System.out.println("Pattern Saved");
            numberTXT++;
            txt.close();

        } catch (IOException ex)
        {
            ex.printStackTrace();
            System.out.println("Error!");
        }

    }

    public static int[][] openPattern(String path) throws FileNotFoundException {

        File file = new File(path);
        Scanner in = new Scanner(file);
        String meshSizeString = in.nextLine();
        int meshSize = Integer.parseInt(meshSizeString);
        int[][] matrix = new int[meshSize][meshSize];

        for (int i = 0; i < meshSize; i++)
        {
            String words = in.next();
            for (int j = 0; j < meshSize; j++)
            {
                char c = words.charAt(j);
                matrix[i][j] = Character.getNumericValue(c);
            }
        }

        return matrix;
    }
}

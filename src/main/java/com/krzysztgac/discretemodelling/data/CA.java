package com.krzysztgac.discretemodelling.data;

import com.krzysztgac.discretemodelling.data.JCanvasCA;

import javax.swing.*;

import java.awt.*;

import static com.krzysztgac.discretemodelling.tools.SimpleTools.numberToBinaryArray;

public class CA {

    int[] ruleSet;
    int meshSize;
    int[][] matrix;


    public CA(int[] ruleSet, int meshSize){
        this.ruleSet = ruleSet;
        this.meshSize = meshSize;
        this.matrix = new int[meshSize][meshSize];
    }

    public CA(){};


}

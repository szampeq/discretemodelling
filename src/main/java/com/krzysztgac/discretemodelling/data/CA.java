package com.krzysztgac.discretemodelling.data;

import com.krzysztgac.discretemodelling.data.JCanvasCA;

import javax.swing.*;

import java.awt.*;

import static com.krzysztgac.discretemodelling.tools.SimpleTools.numberToBinaryArray;

public class CA {

    int[] ruleSet;
    int meshSize;
    int[][] matrix;


    public CA(int rule, int meshSize){
        this.ruleSet = numberToBinaryArray(rule, 8);
        this.meshSize = meshSize;
        this.matrix = new int[meshSize][meshSize];
    }

    public CA(){};

    public int[] getRuleSet() {
        return ruleSet;
    }

    public void setRuleSet(int rule) {
        this.ruleSet = numberToBinaryArray(rule, 8);
    }

    public int getMeshSize() {
        return meshSize;
    }

    public void setMeshSize(int meshSize) {
        this.meshSize = meshSize;
    }


}

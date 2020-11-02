package com.krzysztgac.discretemodelling.data;

import com.krzysztgac.discretemodelling.data.JCanvasCA;

import javax.swing.*;

import java.awt.*;

import static com.krzysztgac.discretemodelling.tools.SimpleTools.numberToBinaryArray;

public class CA {

    int[] ruleSet;
    int meshSize;
    boolean boundaryPeriodic = false;
    int[][] matrix;
    int generation;

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

    public boolean getBoundaryPeriodic() {
        return boundaryPeriodic;
    }

    public void setBoundaryPeriodic(boolean boundaryPeriodic) {
        this.boundaryPeriodic = boundaryPeriodic;
    }

    public void fillMatrix() {
        matrix = new int[meshSize][meshSize];
        for (int i = 0; i < meshSize; i++) {
            for (int j = 0; j < meshSize; j++) {
                matrix[i][j] = 0;
            }
        }

        if (!boundaryPeriodic) {
            for (int i = 0; i < meshSize; i++) {
                for (int j = 0; j < meshSize; j++) {
                    if (i == 0)
                        matrix[i][j] = 1;
                    if (i == meshSize-1)
                        matrix[i][j] = 1;
                }
            }
        }
        matrix[meshSize/2+1][0] = 1;
    }

    public void generateCA() {
        int left, center, right = 0;
        for (int j = 0; j < meshSize-1; j++)
            for (int i = 0; i < meshSize-1; i++) {
                if (boundaryPeriodic) {
                    // LEFT
                        left = i == 0 ? matrix[meshSize - 1][j] : matrix[i-1][j];
                    // CENTER
                    center = matrix[i][j];
                    // RIGHT
                        right = i == (meshSize-1) ? matrix[0][j] : matrix[i+1][j];
                } else { // 1 on edges
                    // LEFT
                        left = i == 0 ? 1 : matrix[i-1][j];
                    // CENTER
                        center = matrix[i][j];
                    // RIGHT
                        right = i == (meshSize-1) ? 1 : matrix[i+1][j];
                }
                matrix[i][j + 1] = rules(left, center, right);
            }
    }

    int rules (int l, int c, int r) {

        String sum = "" + l + c + r;
        int index = Integer.parseInt(sum, 2); // String -> Int -> Binary System
        return ruleSet[index];

    }


}

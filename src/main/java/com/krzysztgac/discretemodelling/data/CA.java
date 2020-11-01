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
        matrix[meshSize/2][meshSize/2] = 1;
    }
}

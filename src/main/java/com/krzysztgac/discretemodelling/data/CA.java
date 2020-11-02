package com.krzysztgac.discretemodelling.data;

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

        if (l == 1 && c == 1 && r == 1)
            return ruleSet[0];
        else if (l == 1 && c == 1 && r == 0)
            return ruleSet[1];
        else if (l == 1 && c == 0 && r == 1)
            return ruleSet[2];
        else if (l == 1 && c == 0 && r == 0)
            return ruleSet[3];
        else if (l == 0 && c == 1 && r == 1)
            return ruleSet[4];
        else if (l == 0 && c == 1 && r == 0)
            return ruleSet[5];
        else if (l == 0 && c == 0 && r == 1)
            return ruleSet[6];
        else
            return ruleSet[7];
    }


}

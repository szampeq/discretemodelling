package com.krzysztgac.discretemodelling.data;

import static com.krzysztgac.discretemodelling.tools.SimpleTools.numberToBinaryArray;

public class GoL {

    int meshSize;
    int[][] matrix;

    public GoL(){};

    public int getMeshSize() {
        return meshSize;
    }

    public void setMeshSize(int meshSize) {
        this.meshSize = meshSize;
    }

    public void fillMatrix() {
        matrix = new int[meshSize][meshSize];
        for (int i = 0; i < meshSize; i++) {
            for (int j = 0; j < meshSize; j++) {
                matrix[i][j] = 0;
            }
        }
        matrix[meshSize/2+1][0] = 1;
    }


}

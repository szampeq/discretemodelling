package com.krzysztgac.discretemodelling.data;

import static com.krzysztgac.discretemodelling.tools.SimpleTools.numberToBinaryArray;

public class GoL {

    int meshSize;
    int cellSize;
    String initialState;
    int[][] matrix;

    public GoL(){
        this.cellSize = 10;
    };

    public int getMeshSize() {
        return meshSize;
    }

    public void setMeshSize(int meshSize) {
        this.meshSize = meshSize;
    }

    public int getCellSize() {
        return cellSize;
    }

    public void setCellSize(int cellSize) {
        this.cellSize = cellSize;
    }

    public String getInitialState() {
        return initialState;
    }

    public void setInitialState(String initialState) {
        this.initialState = initialState;
    }

    public void fillMatrix() {
        matrix = new int[meshSize][meshSize];

        switch (initialState) {
            case "Unchanging":
                unchangingMatrix();
                break;
            case "Glider":
                gliderMatrix();
                break;
            case "Oscilation":
                oscilationMatrix();
                break;
            case "Random":
                randomMatrix();
                break;
            default:
                zeroMatrix();
        }
    }


    void zeroMatrix() {
        for (int i = 0; i < meshSize; i++)
            for (int j = 0; j < meshSize; j++)
                matrix[i][j] = 0;
    }

    void unchangingMatrix() {
        zeroMatrix();
        matrix[meshSize / 2][meshSize / 2 - 1] = 1;
        matrix[meshSize / 2][meshSize / 2 + 1] = 1;
        matrix[meshSize / 2 + 1][meshSize / 2 - 1] = 1;
        matrix[meshSize / 2 + 1][meshSize / 2 + 1] = 1;
        matrix[meshSize / 2 - 1][meshSize / 2] = 1;
        matrix[meshSize / 2 + 2][meshSize / 2] = 1;
    }

    void gliderMatrix() {
        zeroMatrix();
        matrix[meshSize / 2][meshSize / 2 - 1] = 1;
        matrix[meshSize / 2 - 1][meshSize / 2 - 1] = 1;
        matrix[meshSize / 2 - 1][meshSize / 2] = 1;
        matrix[meshSize / 2 - 2][meshSize / 2] = 1;
        matrix[meshSize / 2][meshSize / 2 + 1] = 1;
    }

    void oscilationMatrix() {
        zeroMatrix();
        matrix[meshSize / 2][meshSize / 2 - 1] = 1;
        matrix[meshSize / 2][meshSize / 2] = 1;
        matrix[meshSize / 2][meshSize / 2 + 1] = 1;
    }

    void randomMatrix() {
        zeroMatrix();

    }
}

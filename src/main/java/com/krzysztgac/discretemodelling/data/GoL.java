package com.krzysztgac.discretemodelling.data;

import java.util.concurrent.ThreadLocalRandom;

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


    public void zeroMatrix() {
        for (int i = 0; i < meshSize; i++)
            for (int j = 0; j < meshSize; j++)
                matrix[i][j] = 0;
    }

    void unchangingMatrix() {
        zeroMatrix();
        if (meshSize > 3) {
            matrix[meshSize / 2][meshSize / 2 - 1] = 1;
            matrix[meshSize / 2][meshSize / 2 + 1] = 1;
            matrix[meshSize / 2 + 1][meshSize / 2 - 1] = 1;
            matrix[meshSize / 2 + 1][meshSize / 2 + 1] = 1;
            matrix[meshSize / 2 - 1][meshSize / 2] = 1;
            matrix[meshSize / 2 + 2][meshSize / 2] = 1;
        }
    }

    void gliderMatrix() {
        zeroMatrix();
        if (meshSize > 2) {
            matrix[meshSize / 2 + 1][meshSize / 2 - 1] = 1;
            matrix[meshSize / 2][meshSize / 2 - 1] = 1;
            matrix[meshSize / 2][meshSize / 2] = 1;
            matrix[meshSize / 2 - 1][meshSize / 2] = 1;
            matrix[meshSize / 2 + 1][meshSize / 2 + 1] = 1;
        }
    }

    void oscilationMatrix() {
        zeroMatrix();
        if (meshSize > 2) {
            matrix[meshSize / 2][meshSize / 2 - 1] = 1;
            matrix[meshSize / 2][meshSize / 2] = 1;
            matrix[meshSize / 2][meshSize / 2 + 1] = 1;
        }
    }

    void randomMatrix() {
        zeroMatrix();
        // number of random points (25-75% mesh)
        int randomPoints = ThreadLocalRandom.current().nextInt(meshSize/4, meshSize/4 * 3);
        // to simplify, points can cover each other
        for (int i = 0; i < randomPoints; i++) {
            int randomX = ThreadLocalRandom.current().nextInt(0, meshSize - 1);
            int randomY = ThreadLocalRandom.current().nextInt(0, meshSize - 1);
            matrix[randomX][randomY] = 1;
        }
    }

    public void changeMatrixCell(int x, int y) {
        if (x >= 0 && x < matrix.length && y >= 0 && y < matrix[0].length)
            if (matrix[x][y] == 0)
                matrix[x][y] = 1;
            else
                matrix[x][y] = 0;
    }
}

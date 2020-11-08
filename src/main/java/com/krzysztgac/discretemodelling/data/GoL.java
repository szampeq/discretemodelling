package com.krzysztgac.discretemodelling.data;

import java.awt.*;
import java.util.concurrent.ThreadLocalRandom;



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
        // number of random points (~75% mesh)
        int randomPoints = (meshSize/4 * 3) * (meshSize/4 * 3);
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

    public void cellNeighborhood() {

        int neighborsAlive;
        int[][] newCells = new int[meshSize][meshSize];

        for (int i = 0; i < meshSize-1; i++)
            for (int j = 0; j < meshSize-1; j++) {

                neighborsAlive = 0;

                for (int m = -1; m <= 1; m++)
                    for (int n = -1; n <= 1; n++) {

                        int x = i + m;
                        int y = j + n;

                        if (x < 0 || y < 0) continue;
                        if (x == i && y == j) continue;
                        if (x > meshSize - 1) continue;
                        if (y > meshSize - 1) continue;

                        neighborsAlive += matrix[x][y];

                    }

                // new cell is born
                if (matrix[i][j] == 0 && neighborsAlive == 3)
                    newCells[i][j] = 1;
                // cell in crowd dies
                else if (matrix[i][j] == 1 && neighborsAlive > 3)
                    newCells[i][j] = 0;
                // lonely cell dies
                else if (matrix[i][j] == 1 && neighborsAlive < 2)
                    newCells[i][j] = 0;
                else
                    newCells[i][j] = matrix[i][j];
                }

        matrix = newCells;
    }

}

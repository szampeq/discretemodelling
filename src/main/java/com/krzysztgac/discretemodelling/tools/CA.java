package com.krzysztgac.discretemodelling.tools;

import com.krzysztgac.discretemodelling.data.JCanvasCA;

import javax.swing.*;

import static com.krzysztgac.discretemodelling.tools.SimpleTools.numberToBinaryArray;

public class CA {
    JCanvasCA canvasCA;
    int[] ruleSet;
    int meshSize;

    public CA(int rule, int meshSize) {
        this.ruleSet = numberToBinaryArray(rule, 8);
        this.meshSize = meshSize;
        canvasCA = new JCanvasCA(meshSize);
    }

    public CA(){
        this.meshSize = 0;
        canvasCA = new JCanvasCA(meshSize);
    }

    public JCanvasCA getCanvasCA(){
        return canvasCA;
    }

    public void setMeshSize(int meshSize) {
        this.meshSize = meshSize;
        canvasCA.setMeshSize(meshSize);
    }

    public int getMeshSize() {
        return canvasCA.getMeshSize();
    }

    public void setRuleSet(int rule) {
        this.ruleSet = numberToBinaryArray(rule, 8);
    }

}

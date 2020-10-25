package com.krzysztgac.discretemodelling;

import javax.swing.*;
import java.awt.*;

public class Button extends JButton {
    JButton button;
    String text;

    public Button(String text, JPanel buttonPanel) {
        this.text = text;
        this.button = new JButton(text);
        this.button.setBackground(Color.WHITE);
        this.button.setForeground(Color.DARK_GRAY);
        this.button.setFont(new Font("Tahoma",Font.BOLD,14));
        buttonPanel.add(this.button);
    }

}

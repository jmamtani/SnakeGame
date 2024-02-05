package com.jm;

import javax.swing.JFrame;

public class GameFrame extends JFrame {

    GameFrame() {
        this.add(new GamePanel());
        this.setTitle("Snake");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.pack(); //take JFrame and fit all the components around it
        this.setVisible(true);
        this.setLocationRelativeTo(null); //to have it in the middle of the screen
    }
}

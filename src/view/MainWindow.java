package view;

import controller.GameLoop;

import javax.swing.*;

public class MainWindow extends JFrame {
    public MainWindow() {
        setTitle("Tetris");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);

        GamePanel gamePanel = new GamePanel();
        GameLoop gameLoop = new GameLoop(gamePanel);
        add(gamePanel);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);

        gameLoop.startGame();
    }
}

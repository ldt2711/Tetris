package view;

import controller.GameLoop;
import controller.KeyHandler;
import view.panel.*;

import javax.swing.*;
import java.sql.SQLException;

public class MainWindow extends JFrame {
    private GameLoop gameLoop;
    private GamePanel gamePanel;

    public MainWindow() {
        setTitle("Tetris");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        showMainMenu();
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public GamePanel getGamePanel() {
        return gamePanel;
    }

    public void showMainMenu() {
        try {
            setContentPane(new MenuPanel(this));
            revalidate();
            repaint();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void startNewGame() {
        gamePanel = new GamePanel(this);
        setContentPane(gamePanel);
        revalidate();
        repaint();
        gamePanel.requestFocusInWindow();

        gameLoop = new GameLoop(gamePanel);
        try {
            gameLoop.startGame();
        } catch (RuntimeException e) {
            e.printStackTrace();
        }
    }

    public void continueGame() {
        setContentPane(gamePanel);
        revalidate();
        repaint();
        gamePanel.requestFocusInWindow();

        gameLoop = new GameLoop(gamePanel);
        try {
            gameLoop.startGame();
        } catch (RuntimeException e) {
            e.printStackTrace();
        }
    }

    public void showOptionsPanel(int panel) {
        setContentPane(new OptionsPanel(this, panel));
        revalidate();
        repaint();
    }

    public void showInsertNamePanel() {
        gameLoop.stopGame();
        try {
            setContentPane(new InsertNamePanel(this, gamePanel));
        } catch (RuntimeException e) {
            e.printStackTrace();
        }
        revalidate();
        repaint();
    }

    public void showHelpPanel(int i) {
        setContentPane(new HelpPanel(this, i));
        revalidate();
        repaint();
    }

    public void showPausePanel() {
        gameLoop.stopGame();
        setContentPane(new PausePanel(this, gamePanel));
        revalidate();
        repaint();
    }
}

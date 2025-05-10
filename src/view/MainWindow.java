package view;

import controller.GameLoop;
import view.panel.GamePanel;
import view.panel.InsertNamePanel;
import view.panel.MenuPanel;
import view.panel.OptionsPanel;

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

    public void showOptionsPanel() {
        setContentPane(new OptionsPanel(this));
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

//    public void showHelpPanel() {
//        setContentPane(new HelpPanel(this));
//        revalidate();
//        repaint();
//    }
//
//    public void showHighScorePanel() {
//        setContentPane(new HighScorePanel(this)); // nếu có HighScorePanel riêng
//        revalidate();
//        repaint();
//    }
}

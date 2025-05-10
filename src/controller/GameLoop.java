package controller;

import view.panel.GamePanel;

import java.sql.SQLException;

public class GameLoop implements Runnable{ // for game thread
    final int FPS = 60;
    Thread gameThread; // to run game loop
    GamePanel gp;

    public GameLoop(GamePanel gp) {
        this.gp = gp;
    }

    public void startGame() {
        gameThread = new Thread(this);
        gameThread.start(); // start the thread will call the run method
    }

    public void stopGame() {
        gameThread = null;
    }

    @Override
    public void run() {
        // Game loop
        double drawInterval = 1000000000/FPS;
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;

        while (gameThread != null) {
            currentTime = System.nanoTime();

            delta += (currentTime - lastTime) / drawInterval;
            lastTime = currentTime;

            if (delta >= 1) {
                try {
                    gp.update();
                    gp.repaint();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
                delta--;
            }
        }
    }
}

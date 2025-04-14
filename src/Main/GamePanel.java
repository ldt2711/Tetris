package Main;

import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel implements Runnable { // for gameThread
    public static final int WIDTH = 1280;
    public static final int HEIGHT = 720;
    final int FPS = 60;
    Thread gameThread; // to run game loop
    PlayManager pm;

    public GamePanel() {
        // Game panel settings
        this.setPreferredSize(new Dimension(WIDTH, HEIGHT));
        this.setBackground(Color.black);
        this.setLayout(null);

        // implement key listener
        this.addKeyListener(new KeyHandler());
        this.setFocusable(true);

        pm = new PlayManager();
    }

    public void startGame() {
        gameThread = new Thread(this);
        gameThread.start(); // start the thread will call the run method
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
                update();
                repaint(); // call the paintComponent method
                delta--;
            }
        }
    }

    public void update() { // score, position,...
        // update only when the game is not paused
        if(!KeyHandler.pausePressed && !pm.gameOver) {
            pm.update();
            this.setBackground(Color.black);
        }
        else {
            this.setBackground(Color.darkGray);
        }
    }

    public void paintComponent(Graphics g) { // draw after update, paintComponent is a built-in method to draw in the screen
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g;
        pm.draw(g2);
    }
}

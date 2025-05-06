package view;

import controller.KeyHandler;
import controller.PlayManager;

import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel { // for gameThread
    public static final int WIDTH = 1280;
    public static final int HEIGHT = 720;

    PlayManager pm;
    PlayArea pa;
    DrawState ds = new DrawState();
    DeleteLineEffect dle = new DeleteLineEffect();


    public GamePanel() {
        // Game panel settings
        this.setPreferredSize(new Dimension(WIDTH, HEIGHT));
        this.setBackground(Color.black);
        this.setLayout(null);

        // implement key listener
        this.addKeyListener(new KeyHandler());
        this.setFocusable(true);

        pa = new PlayArea();
        pm = new PlayManager();
    }

    public void update() { // score, position,...
        // update only when the game is not paused
        if(!KeyHandler.pausePressed && !pm.gameOver) {
            if (!pm.isEffectCounterOn()) {
                pm.update();
                pm.getDustParticles().removeIf(DustParticle::isDead);
                for (DustParticle d : pm.getDustParticles()) {
                    d.update();
                }
            }
            this.setBackground(Color.black);
        }
        else {
            this.setBackground(new Color(23, 23, 23, 20));
        }
    }

    public void paintComponent(Graphics g) { // draw after update, paintComponent is a built-in method to draw in the screen
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g;

        // for shaking effect
        int offsetX = 0;
        int offsetY = 0;

        // draw next tetorimino frame
        int x = PlayArea.right_x + 100;
        int y = PlayArea.bottom_y - 200;

        if (pm.getShakeCounter() > 0) {
            offsetX = (int)(Math.random() * 4 - 2); // -2, -1, 0, 1
            offsetY = (int)(Math.random() * 4 - 2);
            pm.setShakeCounter(pm.getShakeCounter() - 1);
        }
        g2.translate(offsetX, offsetY);

        g2.setColor(Color.WHITE);
        g2.setStroke(new BasicStroke(4f));

        g2.drawRect(x, y, 200, 200);
        g2.setFont(new Font("Monospaced", Font.BOLD, 24));
        g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_OFF); // anti-aliasing for the text
        g2.drawString("NEXT", x + 75, y + 50);

        // draw score frame
        g2.drawRect(x, PlayArea.top_y, 250, 300);
        x += 40;
        y = PlayArea.top_y + 90;
        g2.drawString("LEVEL: " + pm.getLevel(), x, y); y += 70;
        g2.drawString("LINES: " + pm.getLines(), x, y); y += 70;
        g2.drawString("SCORES: " + pm.getScore(), x, y);

        // draw the game title
        x = 75;
        y = PlayArea.top_y + 320;
        g2.setColor(Color.white);
        g2.setFont(new Font("Monospaced", Font.BOLD, 60));
        g2.drawString("Tetris", x, y);

        // draw play area
        pa.draw(g2);
        // draw mino
        try {
            ds.draw(g2, pm.getMg());
        } catch (NullPointerException e) {
            System.out.println("Can't find the tetromino");
        }
        // draw clear line effect
        if (pm.isEffectCounterOn()) {
            boolean stillRunning = dle.deleteEffect(PlayArea.left_x, PlayArea.WIDTH, true, pm.getEffectY(), g2);
            if (!stillRunning) {
                pm.finalizeDelete();
            }
        }
        g2.translate(-offsetX, -offsetY);

        // Draw dust particles
        for (DustParticle d : pm.getDustParticles()) {
            d.render(g2);
        }

    }
}
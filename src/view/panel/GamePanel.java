package view.panel;

import controller.ConfigManager;
import controller.KeyHandler;
import controller.PlayManager;
import model.GameState;
import view.MainWindow;
import view.effect.DeleteLineEffect;
import view.DrawState;
import view.effect.DustParticle;
import view.PlayArea;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;
import java.util.Objects;

public class GamePanel extends JPanel { // for gameThread
    public static final int WIDTH = 1280;
    public static final int HEIGHT = 720;
    private static Image playIcon;
    public static final Rectangle playButtonBounds = new Rectangle(180, PlayArea.top_y + 350, 50, 50);

    PlayManager pm;
    DrawState ds = new DrawState();
    DeleteLineEffect dle = new DeleteLineEffect();
    ConfigManager cm;
    private MainWindow mainMenu;

    public GamePanel(MainWindow mainMenu) {
        this.mainMenu = mainMenu;
        GameState.staticBlocks.clear();
        this.cm = new ConfigManager();

        // Game panel settings
        this.setPreferredSize(new Dimension(WIDTH, HEIGHT));
        this.setBackground(Color.black);
        this.setLayout(null);

        // implement key listener
        this.addKeyListener(new KeyHandler(cm));
        this.setFocusable(true);
        this.requestFocusInWindow();
        
        BasicUI.initResources();

        ClassLoader cl = BasicUI.class.getClassLoader();
        playIcon = new ImageIcon(Objects.requireNonNull(cl.getResource("image/icon/play.png"))).getImage();

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (BasicUI.soundButtonBounds.contains(e.getPoint())) {
                    BasicUI.toggleMute();
                    repaint();
                } else if (playButtonBounds.contains(e.getPoint())) {
                    togglePlay();
                    repaint();
                }
            }
        });

        pm = new PlayManager();
    }


    public void togglePlay() {KeyHandler.pausePressed = !KeyHandler.pausePressed;}

    public void update() throws SQLException { // score, position,...
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
        } else if (pm.gameOver) {
            mainMenu.showInsertNamePanel();
        } else {
            mainMenu.showPausePanel();
        }
    }

    public void paintComponent(Graphics g) { // draw after update, paintComponent is a built-in method to draw in the screen
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g;

        // for shaking effect
        int offsetX = 0;
        int offsetY = 0;

        if (pm.getShakeCounter() > 0) {
            offsetX = (int)(Math.random() * 4 - 2); // -2, -1, 0, 1
            offsetY = (int)(Math.random() * 4 - 2);
            pm.setShakeCounter(pm.getShakeCounter() - 1);
        }
        g2.translate(offsetX, offsetY);

        BasicUI.drawGame(g2, pm.getLevel(), pm.getScore(), pm.getLines(), this);

        Image iconToDraw = playIcon;
        g2.drawImage(iconToDraw, playButtonBounds.x, playButtonBounds.y,
                        playButtonBounds.width, playButtonBounds.height, this);
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
        for (int i = pm.getDustParticles().size() - 1; i >= 0; i--) {
            pm.getDustParticles().get(i).render(g2);
        }

    }
}
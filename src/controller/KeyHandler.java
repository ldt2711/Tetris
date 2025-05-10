package controller;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener {
    private final ConfigManager config;

    public static boolean upPressed, downPressed, rightPressed, leftPressed, pausePressed = false, spacePressed;

    public KeyHandler(ConfigManager config) {
        this.config = config;
    }

    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();
        if (key == config.getKeyCode("LEFT")) leftPressed = true;
        if (key == config.getKeyCode("RIGHT")) rightPressed = true;
        if (key == config.getKeyCode("ROTATE")) upPressed = true;
        if (key == config.getKeyCode("PAUSE")) pausePressed = !pausePressed;
        if (key == config.getKeyCode("HARD_DROP")) spacePressed = true;
        if (key == config.getKeyCode("SOFT_DROP")) downPressed = true;
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int key = e.getKeyCode();
        if (key == config.getKeyCode("LEFT")) leftPressed = false;
        if (key == config.getKeyCode("RIGHT")) rightPressed = false;
        if (key == config.getKeyCode("ROTATE")) upPressed = false;
        if (key == config.getKeyCode("PAUSE")) pausePressed = !pausePressed;
        if (key == config.getKeyCode("HARD_DROP")) spacePressed = false;
        if (key == config.getKeyCode("SOFT_DROP")) downPressed = false;
    }
}

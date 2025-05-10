package view.panel;

import view.PlayArea;

import javax.swing.*;
import java.awt.*;
import java.awt.image.ImageObserver;
import java.util.Objects;

public class BasicUI {
    // static resources
    private static boolean isMuted = false;
    private static Image muteIcon;
    private static Image unmuteIcon;
    public static final Rectangle soundButtonBounds = new Rectangle(90, PlayArea.top_y + 350, 50, 50);

    public static void initResources() {
        ClassLoader cl = BasicUI.class.getClassLoader();
        muteIcon = new ImageIcon(Objects.requireNonNull(cl.getResource("image/icon/mute.png"))).getImage();
        unmuteIcon = new ImageIcon(Objects.requireNonNull(cl.getResource("image/icon/unmute.png"))).getImage();
    }

    // toggle mute
    public static void toggleMute() {
        isMuted = !isMuted;
    }

    public static boolean isMuted() {
        return isMuted;
    }

    public static void drawGame(Graphics2D g2, int level, int score, int lines, ImageObserver observer) {
        // draw next tetorimino frame
        int x = PlayArea.right_x + 100;
        int y = PlayArea.bottom_y - 200;

        g2.setColor(Color.WHITE);
        g2.setStroke(new BasicStroke(4f));

        g2.drawRect(x, y, 200, 200);
        g2.setFont(new Font("Monospaced", Font.BOLD, 24));
        g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON); // anti-aliasing for the text
        g2.drawString("NEXT:", x + 50, y + 50);

        // draw score frame
        g2.drawRect(x, PlayArea.top_y, 250, 300);
        x += 40;
        y = PlayArea.top_y + 90;
        g2.drawString("LEVEL: " + level, x, y); y += 70;
        g2.drawString("LINES: " + lines, x, y); y += 70;
        g2.drawString("SCORES: " + score, x, y);

        // draw mute/unmute icon
        Image iconToDraw = isMuted ? muteIcon : unmuteIcon;
        g2.drawImage(iconToDraw, soundButtonBounds.x, soundButtonBounds.y,
                soundButtonBounds.width, soundButtonBounds.height, observer);

        // draw the game title
        x = 75;
        y = PlayArea.top_y + 320;
        g2.setColor(Color.white);
        g2.setFont(new Font("Monospaced", Font.BOLD, 60));
        g2.drawString("Tetris", x, y);

        // draw play area
        PlayArea.draw(g2);
    }
}

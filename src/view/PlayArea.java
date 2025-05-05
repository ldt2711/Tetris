package view;

import java.awt.*;

public class PlayArea {

    public static final int WIDTH = 360;
    public static final int HEIGHT = 600;

    public PlayArea() {
        GamePanel.left_x = (GamePanel.WIDTH/2) - (WIDTH/2);
        GamePanel.right_x = GamePanel.left_x + WIDTH;
        GamePanel.top_y = 50;
        GamePanel.bottom_y = GamePanel.top_y + HEIGHT;
    }

    public void draw(Graphics2D g2) {
        // draw main play area
        g2.setColor(Color.WHITE);
        g2.setStroke(new BasicStroke(4f));
        g2.drawRect(GamePanel.left_x-4, GamePanel.top_y-4, WIDTH+8, HEIGHT+8);
    }
}

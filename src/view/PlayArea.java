package view;

import view.panel.GamePanel;

import java.awt.*;

public class PlayArea {

    public static final int WIDTH = 360;
    public static final int HEIGHT = 600;

    public static int left_x = (GamePanel.WIDTH/2) - (WIDTH/2);
    public static int right_x = left_x + WIDTH;
    public static int top_y = 50;
    public static int bottom_y = top_y + HEIGHT;

    public static void draw(Graphics2D g2) {
        // draw main play area
        g2.setColor(Color.WHITE);
        g2.setStroke(new BasicStroke(4f));
        g2.drawRect(left_x-4, top_y-4, WIDTH+8, HEIGHT+8);
    }
}

package view;

import java.awt.*;

public class PlayArea {

    public static final int WIDTH = 360;
    public static final int HEIGHT = 600;

    public static int left_x;
    public static int right_x;
    public static int top_y;
    public static int bottom_y;

    public PlayArea() {
        left_x = (GamePanel.WIDTH/2) - (WIDTH/2);
        right_x = left_x + WIDTH;
        top_y = 50;
        bottom_y = top_y + HEIGHT;
    }

    public void draw(Graphics2D g2) {
        // draw main play area
        g2.setColor(Color.WHITE);
        g2.setStroke(new BasicStroke(4f));
        g2.drawRect(left_x-4, top_y-4, WIDTH+8, HEIGHT+8);
    }
}

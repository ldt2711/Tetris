package Main;

import mino.*;

import java.awt.*;
import java.util.Random;

public class PlayManager {

    // Play area
    final int WIDTH = 360;
    final int HEIGHT = 600;
    public static int left_x;
    public static int right_x;
    public static int top_y;
    public static int bottom_y;

    // mino
    Mino currentMino;
    final int MINO_START_X;
    final int MINO_START_Y;

    // others
    public static int dropInterval = 60; // mino drops in every 60 frame = 1 sec

    public PlayManager() {
        left_x = (GamePanel.WIDTH/2) - (WIDTH/2);
        right_x = left_x + WIDTH;
        top_y = 50;
        bottom_y = top_y + HEIGHT;

        MINO_START_X = left_x + (WIDTH/2) - Block.SIZE;
        MINO_START_Y = top_y + Block.SIZE;

        // set the starting mino
        currentMino = new Mino_Bar();
        //currentMino = pickMino();
        currentMino.setXY(MINO_START_X, MINO_START_Y);
    }

    private Mino pickMino() {
        // pick a random mino
        Mino mino = null;
        int i = new Random().nextInt(7);
        mino = switch (i) {
            case 0 -> new Mino_LL();
            case 1 -> new Mino_LR();
            case 2 -> new Mino_T();
            case 3 -> new Mino_Bar();
            case 4 -> new Mino_Sqare();
            case 5 -> new Mino_ZR();
            case 6 -> new Mino_ZL();
            default -> mino;
        };
        return mino;
    }

    public void update() {
        currentMino.update();
    }

    public void draw(Graphics2D g2) {
        // draw main play area
        g2.setColor(Color.WHITE);
        g2.setStroke(new BasicStroke(4f));
        g2.drawRect(left_x-4, top_y-4, WIDTH+8, HEIGHT+8);

        // draw next tetorimino frame
        int x = right_x + 100;
        int y = bottom_y - 200;
        g2.drawRect(x, y, 200, 200);
        g2.setFont(new Font("Arial", Font.PLAIN, 30));
        g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON); // anti-aliasing for the text
        g2.drawString("Tiếp theo", x + 40, y + 60);

        // draw the currentMino
        try {
            currentMino.draw(g2);
        } catch (NullPointerException _) {}

        // draw pause
        g2.setColor(Color.yellow);
        g2.setFont(g2.getFont().deriveFont(50f));
        if(KeyHandler.pausePressed) {
            x = left_x + 70;
            y = top_y + 320;
            g2.drawString("Tạm dừng", x, y);
        }
    }
}

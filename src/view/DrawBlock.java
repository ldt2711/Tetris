package view;

import model.mino.Block;

import java.awt.*;

public class DrawBlock {
    public static final int margin = 2;

    public void draw(Graphics2D g2, Block b) {
        g2.setColor(b.getC());
        g2.fillRect(b.getCorX() + margin, b.getCorY() + margin, Block.SIZE - (margin * 2), Block.SIZE - (margin * 2));
    }
}

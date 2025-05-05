package view;

import model.mino.Block;
import model.mino.Mino;

import java.awt.*;

import static view.DrawBlock.margin;

public class DrawMino {
    public void drawBlocks(Graphics2D g2, Mino m) {
        g2.setColor(m.getB()[0].getC());
        for (Block bl : m.getB()) {
            g2.fillRect(bl.getCorX() + margin, bl.getCorY() + margin, Block.SIZE - (margin * 2), Block.SIZE - (margin * 2));
        }
    }

    public void drawGhostBlocks(Graphics2D g2, Mino m) {
        g2.setColor(new Color(m.getB()[0].getC().getRed(), m.getB()[0].getC().getGreen(), m.getB()[0].getC().getBlue(), 50));
        for (Block gb : m.getGhostB()) {
            if (gb.getCorX() >= GamePanel.left_x && gb.getCorY() >= 0) {
                g2.fillRect(gb.getCorX() + margin, gb.getCorY() + margin, Block.SIZE - (margin * 2), Block.SIZE - (margin * 2));
            }
        }
    }
}

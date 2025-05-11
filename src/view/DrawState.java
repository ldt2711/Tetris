package view;

import controller.PlayManager;
import model.GameState;
import model.MinoGenerator;
import model.mino.Block;

import java.awt.*;

public class DrawState {
    DrawBlock db = new DrawBlock();
    DrawMino dm = new DrawMino();

    public void draw(Graphics2D g2, MinoGenerator mg) {
        // draw the currentMino
        try {
            dm.drawBlocks(g2, mg.getCurrentMino());
            dm.drawGhostBlocks(g2, mg.getCurrentMino());
        } catch (NullPointerException e) {throw new NullPointerException();}

        // draw the next mino
        dm.drawBlocks(g2, mg.getNextMino());

        // draw staticBlocks
        for (Block i: PlayManager.staticBlocks) {
            db.draw(g2, i);
        }
    }
}

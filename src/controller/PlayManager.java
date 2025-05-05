package controller;

import model.GameState;
import model.MinoGenerator;
import model.mino.Block;
import view.GamePanel;

import java.util.ArrayList;

public class PlayManager {
    // others
    public static int dropInterval = 60; // mino drops in every 60 frame = 1 sec
    public boolean gameOver;

    // effect
    boolean effectCounterOn;
    int effectCounter;
    ArrayList<Integer> effectY = new ArrayList<>();
    // model
    MinoGenerator mg;
    // controller
    ScoreManager sm = new ScoreManager();
    MinoManager mm = new MinoManager();

    public PlayManager() {
        mg = new MinoGenerator();
    }

    public int getLines() {
        return sm.gs.getLines();
    }

    public int getScore() {
        return sm.gs.getScore();
    }

    public int getLevel() {
        return sm.gs.getLevel();
    }

    public boolean isGameOver() {
        return gameOver;
    }

    public boolean isEffectCounterOn() {
        return effectCounterOn;
    }

    public int getEffectCounter() {
        return effectCounter;
    }

    public ArrayList<Integer> getEffectY() {
        return effectY;
    }

    public MinoGenerator getMg() {
        return mg;
    }

    public ScoreManager getSm() {
        return sm;
    }

    public void setEffectCounterOn(boolean effectCounterOn) {
        this.effectCounterOn = effectCounterOn;
    }

    public void update() {
        // check if the current mino is active
        if (!mg.getCurrentMino().active) {
            // if the mino is not active, put it into staticBlocks
            GameState.staticBlocks.add(mg.getCurrentMino().getB()[0]);
            GameState.staticBlocks.add(mg.getCurrentMino().getB()[1]);
            GameState.staticBlocks.add(mg.getCurrentMino().getB()[2]);
            GameState.staticBlocks.add(mg.getCurrentMino().getB()[3]);

            // check if the game is over
            if (mg.getCurrentMino().getB()[0].getCorX() == mg.MINO_START_X
                    && mg.getCurrentMino().getB()[0].getCorY() == mg.MINO_START_Y) {
                // no space left
                gameOver = true;
            }

            mg.getCurrentMino().deactivating = false; // the mino can still move after hit the floor

            // replace the current mino with the next mino
            mg.setCurrentMino(mg.getNextMino());
            mg.getCurrentMino().setXY(mg.MINO_START_X, mg.MINO_START_Y);
            mg.setNextMino(mg.pickMino());
            mg.getNextMino().setXY(mg.NEXT_MINO_X, mg.NEXT_MINO_Y);

            // when a mino is deactivated, check if line(s) can be deleted
            checkDelete();
        }
        else {
            mm.update(mg.getCurrentMino(), sm);
        }
    }

    public void checkDelete() {
        int x = GamePanel.left_x;
        int y = GamePanel.top_y;
        int blockCount = 0;
        int lineCount = 0;

        while (x < GamePanel.right_x && y < GamePanel.bottom_y) {
            for (Block item: GameState.staticBlocks) {
                if (x == item.getCorX() && y == item.getCorY()) {
                    // increase the count if there is a static block
                    blockCount++;
                }
            }

            // stop condition
            x += Block.SIZE;
            if (x == GamePanel.right_x) {
                // if the count equal 12 that means current y line is filled with blocks so we can delete them
                if (blockCount == 12) {
                    effectCounterOn = true;
                    effectY.add(y); // use array because can delete many lines
                    // use backward for loop to avoid some weird results from the normal for loop
                    for (int i = GameState.staticBlocks.size() - 1; i > -1; i--) {
                        // remove all the blocks in the current y line
                        if (GameState.staticBlocks.get(i).getCorY() == y) {
                            GameState.staticBlocks.remove(i);
                        }
                    }

                    lineCount++;
                    sm.gs.setLines(sm.gs.getLines()+1); // increase when delete a line
                    // drop speed
                    // increase drop speed when the score hits a certain number
                    // max speed is 1
                    if (sm.gs.getLines() % 10 == 0 && dropInterval > 1) { // increase the level when 10 lines are deleted
                        sm.gs.setLevel(sm.gs.getLevel()+1);
                        if (dropInterval > 10) { // dropInterval start is 60 = 60 frames
                            dropInterval -= 10;
                        }
                        else {
                            dropInterval -= 1;
                        }
                    }

                    // slide down blocks that are above the deleted line
                    for (Block staticBlock : GameState.staticBlocks) {
                        // if a block is above deleted line, move it down by the block size
                        if (staticBlock.getCorY() < y) {
                            staticBlock.setY(staticBlock.getCorY() + Block.SIZE);
                        }
                    }
                }
                // reset the count when go to the next row
                blockCount = 0;
                x = GamePanel.left_x;
                y += Block.SIZE;
            }
        }
        sm.calculateScore(sm.gs.getLevel(), lineCount);
    }
}

package controller;

import model.GameState;
import model.MinoGenerator;
import model.mino.Block;
import view.effect.DustParticle;
import view.PlayArea;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class PlayManager {
    // others
    public static int dropInterval = 60; // mino drops in every 60 frame = 1 sec
    public boolean gameOver;
    public static ArrayList<Block> staticBlocks;// store mino when it hits the bottom floor

    // effect
    private boolean effectCounterOn;
    private int shakeCounter = 0;
    ArrayList<Integer> effectY = new ArrayList<>();
    List<DustParticle> dustParticles = new ArrayList<>();
    // model
    MinoGenerator mg;
    // controller
    ScoreManager sm = new ScoreManager();
    MinoManager mm = new MinoManager(this);

    public PlayManager() {
        mg = new MinoGenerator();
        staticBlocks = new ArrayList<>();
    }

    public PlayManager(GameState gameState) {
        sm.gs = gameState;
        assert sm.gs != null;
        mg = new MinoGenerator();
        mg.setCurrentMino(sm.gs.getCurrentMino());
        mg.setNextMino(sm.gs.getNextMino());
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

    public boolean isEffectCounterOn() {
        return effectCounterOn;
    }

    public int getShakeCounter() {
        return shakeCounter;
    }

    public ArrayList<Integer> getEffectY() {
        return effectY;
    }

    public List<DustParticle> getDustParticles() {
        return dustParticles;
    }

    public MinoGenerator getMg() {
        return mg;
    }

    public ScoreManager getSm() {
        return sm;
    }

    public void setShakeCounter(int shakeCounter) {
        this.shakeCounter = shakeCounter;
    }

    public void update() {
        if (effectCounterOn) return;
        // check if the current mino is active
        if (!mg.getCurrentMino().active) {
            // if the mino is not active, put it into staticBlocks
            staticBlocks.add(mg.getCurrentMino().getB()[0]);
            staticBlocks.add(mg.getCurrentMino().getB()[1]);
            staticBlocks.add(mg.getCurrentMino().getB()[2]);
            staticBlocks.add(mg.getCurrentMino().getB()[3]);

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
            sm.gs.setCurrentMino(mg.getCurrentMino());
            sm.gs.setNextMino(mg.getNextMino());
        }
        SaveGame.saveGame(sm.gs);
    }

    public void checkDelete() {
        int y = PlayArea.top_y;
        int blockCount = 0;

        while (y < PlayArea.bottom_y) {
            for (Block item: staticBlocks) {
                if (y == item.getCorY()) {
                    // increase the count if there is a static block
                    blockCount++;
                }
            }

            // stop condition
            // if the count equal 12 that means current y line is filled with blocks so we can delete them
            if (blockCount == 12) {
                effectY.add(y); // use array because can delete many lines
            }
            // reset the count when go to the next row
            blockCount = 0;
            y += Block.SIZE;
            if (!effectY.isEmpty()) {
                effectCounterOn = true;
            }
        }
    }

    public void finalizeDelete() {
        int lineCount = 0;
        shakeCounter = 5;
        for (Integer y: effectY) {
            // use backward for loop to avoid some weird results from the normal for loop
            for (int i = staticBlocks.size() - 1; i > -1; i--) {
                // remove all the blocks in the current y line
                if (staticBlocks.get(i).getCorY() == y) {
                    staticBlocks.remove(i);
                }
            }

            // slide down blocks that are above the deleted line
            for (Block staticBlock : staticBlocks) {
                // if a block is above deleted line, move it down by the block size
                if (staticBlock.getCorY() < y) {
                    staticBlock.setY(staticBlock.getCorY() + Block.SIZE);
                }
            }
            lineCount++;
        }

        // sound effect
        if (lineCount == 1) {
            SoundPlayer.playSound("lineclear.wav");
        } else if (lineCount == 2) {
            SoundPlayer.playSound("lineclear_double.wav");
        } else if (lineCount > 2) {
            SoundPlayer.playSound("lineclear_tetris.wav");
        }

        sm.gs.setLines(sm.gs.getLines() + lineCount); // increase when delete a line
        sm.calculateScore(sm.gs.getLevel(), lineCount);

        // drop speed
        // increase drop speed when the score hits a certain number
        // max speed is 1
        if (sm.gs.getLines() / 10 > sm.gs.getLevel() - 1 && dropInterval > 1) { // increase the level when 10 lines are deleted
            sm.gs.setLevel(sm.gs.getLevel() + 1);
            if (dropInterval > 10) { // dropInterval start is 60 = 60 frames
                dropInterval -= 10;
            } else {
                dropInterval -= 1;
            }
        }

        effectY.clear();
        effectCounterOn = false;
    }

    public void createDust(int x, int y) {
        Random rand = new Random();
        for (int i = 0; i < 4; i++) {
            double angle = rand.nextBoolean() ? Math.toRadians(rand.nextInt(30) + 30)  // left side
                    : Math.toRadians(rand.nextInt(30) - 60); // right side
            double speed = rand.nextDouble() * 3 + 1; // tốc độ từ 1 đến 4
            double vx = Math.cos(angle) * speed;
            double vy = -Math.abs(Math.sin(angle) * speed); // bay lên trên nhẹ

            dustParticles.add(new DustParticle(x, y, vx, vy));
        }
    }

}

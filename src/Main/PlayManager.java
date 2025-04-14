package Main;

import mino.*;

import java.awt.*;
import java.util.ArrayList;
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
    Mino nextMino;
    final int NEXTMINO_X;
    final int NEXTMINO_Y;
    public static ArrayList<Block> staticBlocks = new ArrayList<>(); // store mino when it hits the bottom floor

    // others
    public static int dropInterval = 60; // mino drops in every 60 frame = 1 sec
    boolean gameOver;

    // effect
    boolean effectCounterOn;
    int effectCounter;
    ArrayList<Integer> effectY = new ArrayList<>();

    // score
    int score;
    int level = 1;
    int lines;

    public PlayManager() {
        left_x = (GamePanel.WIDTH/2) - (WIDTH/2);
        right_x = left_x + WIDTH;
        top_y = 50;
        bottom_y = top_y + HEIGHT;

        MINO_START_X = left_x + (WIDTH/2) - Block.SIZE;
        MINO_START_Y = top_y + Block.SIZE;

        NEXTMINO_X = right_x + 175;
        NEXTMINO_Y = top_y + 500;

        // set the starting mino
        currentMino = pickMino();
        currentMino.setXY(MINO_START_X, MINO_START_Y);
        nextMino = pickMino();
        nextMino.setXY(NEXTMINO_X, NEXTMINO_Y);
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
        // check if the current mino is active
        if (!currentMino.active) {
            // if the mino is not active, put it into staticBlocks
            staticBlocks.add(currentMino.getB()[0]);
            staticBlocks.add(currentMino.getB()[1]);
            staticBlocks.add(currentMino.getB()[2]);
            staticBlocks.add(currentMino.getB()[3]);

            // check if the game is over
            if (currentMino.getB()[0].getCorX() == MINO_START_X && currentMino.getB()[0].getCorY() == MINO_START_Y) {
                // no space left
                gameOver = true;
            }

            currentMino.deactivating = false; // the mino can still move after hit the floor

            // replace the current mino with the next mino
            currentMino = nextMino;
            currentMino.setXY(MINO_START_X, MINO_START_Y);
            nextMino = pickMino();
            nextMino.setXY(NEXTMINO_X, NEXTMINO_Y);

            // when a mino is deactivated, check if line(s) can be deleted
            checkDelete();
        }
        else {
            currentMino.update();
        }
    }

    public void checkDelete() {
        int x = left_x;
        int y = top_y;
        int blockCount = 0;
        int lineCount = 0;

        while (x < right_x && y < bottom_y) {
            for (Block item: staticBlocks) {
                if (x == item.getCorX() && y == item.getCorY()) {
                    // increase the count if there is a static block
                    blockCount++;
                }
            }


            // stop condition
            x += Block.SIZE;
            if (x == right_x) {
                // if the count equal 12 that means current y line is filled with blocks so we can delete them
                if (blockCount == 12) {
                    effectCounterOn = true;
                    effectY.add(y); // use array because can delete many lines
                    // use backward for loop to avoid some weird results from the normal for loop
                    for (int i = staticBlocks.size() - 1; i > -1; i--) {
                        // remove all the blocks in the current y line
                        if (staticBlocks.get(i).getCorY() == y) {
                            staticBlocks.remove(i);
                        }
                    }

                    lineCount++;
                    lines++; // increase when delete a line
                    // drop speed
                    // increase drop speed when the score hits a certain number
                    // max speed is 1
                    if (lines % 10 == 0 && dropInterval > 1) { // increase the level when 10 lines are deleted
                        level++;
                        if (dropInterval > 10) { // dropInterval start is 60 = 60 frames
                            dropInterval -= 10;
                        }
                        else {
                            dropInterval -= 1;
                        }
                    }

                    // slide down blocks that are above the deleted line
                    for (Block staticBlock : staticBlocks) {
                        // if a block is above deleted line, move it down by the block size
                        if (staticBlock.getCorY() < y) {
                            staticBlock.setY(staticBlock.getCorY() + Block.SIZE);
                        }
                    }
                }
                // reset the count when go to the next row
                blockCount = 0;
                x = left_x;
                y += Block.SIZE;
            }
        }
        // add score base on lines deleted
        if (lineCount > 0) {
            int singleLineScore = 10 * level;
            score += singleLineScore * lineCount;
        }
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

        // draw score frame
        g2.drawRect(x, top_y, 250, 300);
        x += 40;
        y = top_y + 90;
        g2.drawString("LEVEL: " + level, x, y); y += 70;
        g2.drawString("LINES: " + lines, x, y); y += 70;
        g2.drawString("ĐIỂM: " + score, x, y);

        // draw the currentMino
        try {
            currentMino.draw(g2);
        } catch (NullPointerException _) {}

        // draw the next mino
        nextMino.draw(g2);

        // draw staticBlocks
        for (Block i: staticBlocks) {
            i.draw(g2);
        }

        // draw effect
        if (effectCounterOn) {
            effectCounter++; // frames happen when delete lines

            g2.setColor(Color.red);
            for (Integer i : effectY) {
                g2.fillRect(left_x, i, WIDTH, Block.SIZE);
            }

            if (effectCounter == 10) { // reset everything when it's done
                effectCounter = 0;
                effectCounterOn = false;
                effectY.clear();
            }
        }

        // draw pause
        g2.setColor(Color.yellow);
        g2.setFont(g2.getFont().deriveFont(50f));
        if (gameOver) {
            x = left_x + 25;
            y = top_y + 320;
            g2.drawString("GAME OVER", x, y);
        }
        else if(KeyHandler.pausePressed) {
            x = left_x + 50;
            y = top_y + 320;
            g2.drawString("TẠM DỪNG", x, y);
        }

        // draw the game title
        x = 75;
        y = top_y + 320;
        g2.setColor(Color.white);
        g2.setFont(new Font("Times New Roman", Font.PLAIN, 60));
        g2.drawString("Tetris", x, y);
    }
}

package model;

import model.mino.Block;

import java.util.ArrayList;

public class GameState {

    // score
    int score;
    int level = 1;
    int lines;

    public static ArrayList<Block> staticBlocks = new ArrayList<>(); // store mino when it hits the bottom floor

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getLines() {
        return lines;
    }

    public void setLines(int lines) {
        this.lines = lines;
    }
}

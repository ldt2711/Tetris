package model;

import model.mino.Block;

import java.util.ArrayList;

public class GameState {

    String playerName;

    // score
    int score;
    int level = 1;
    int lines;

    public static ArrayList<Block> staticBlocks = new ArrayList<>(); // store mino when it hits the bottom floor

    public GameState() {}

    public GameState(String playerName, int score) {
        this.playerName = playerName;
        this.score = score;
    }

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

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }
}

package model;

import model.mino.Block;
import model.mino.Mino;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;

public class GameState implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    String playerName;

    // score
    int score;
    int level = 1;
    int lines;

    // mino
    Mino currentMino;
    Mino nextMino;

    public static ArrayList<Block> staticBlocks = new ArrayList<>(); // store mino when it hits the bottom floor

    public GameState() {
    }

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

    public void setCurrentMino(Mino currentMino) {
        this.currentMino = currentMino;
    }

    public void setNextMino(Mino nextMino) {
        this.nextMino = nextMino;
    }

    public Mino getCurrentMino() {
        return currentMino;
    }

    public Mino getNextMino() {
        return nextMino;
    }
}

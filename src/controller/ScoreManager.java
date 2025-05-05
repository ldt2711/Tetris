package controller;

import model.GameState;

public class ScoreManager {
    GameState gs = new GameState();

    public GameState getGs() {
        return gs;
    }

    // add score based on lines deleted and how much block when hard drop
    public void calculateScore(int level, int lineCount) {
        if (lineCount > 0) {
            gs.setScore(gs.getScore() + (10 * level * lineCount * (lineCount + 1))/2 ); // Triangular Number Sequence x(x+1)/2
        }
    }

    public void calculateScore(int dropCount) {
        if (dropCount > 0) {
            gs.setScore(gs.getScore() + dropCount * 2);
        }
    }
}

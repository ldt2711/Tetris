package model;

import model.mino.*;
import view.GamePanel;
import view.PlayArea;

import java.util.Random;

public class MinoGenerator {
    Mino currentMino;
    public final int MINO_START_X;
    public final int MINO_START_Y;

    Mino nextMino;
    public final int NEXT_MINO_X;
    public final int NEXT_MINO_Y;

    public MinoGenerator() {
        MINO_START_X = GamePanel.left_x + (PlayArea.WIDTH/2) - Block.SIZE;
        MINO_START_Y = GamePanel.top_y + Block.SIZE;

        NEXT_MINO_X = GamePanel.right_x + 180;
        NEXT_MINO_Y = GamePanel.top_y + 510;
        currentMino = pickMino();
        currentMino.setXY(MINO_START_X, MINO_START_Y);
        nextMino = pickMino();
        nextMino.setXY(NEXT_MINO_X, NEXT_MINO_Y);
    }

    public Mino getCurrentMino() {
        return currentMino;
    }

    public Mino getNextMino() {
        return nextMino;
    }

    public void setCurrentMino(Mino currentMino) {
        this.currentMino = currentMino;
    }

    public void setNextMino(Mino nextMino) {
        this.nextMino = nextMino;
    }

    public Mino pickMino() {
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
}

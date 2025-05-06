package model;

import model.mino.*;
import view.PlayArea;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MinoGenerator {
    Mino currentMino;
    public final int MINO_START_X;
    public final int MINO_START_Y;

    Mino nextMino;
    public final int NEXT_MINO_X;
    public final int NEXT_MINO_Y;

    List<Mino> minoBag = new ArrayList<>(); // random 7-bag algorithm

    public MinoGenerator() {
        MINO_START_X = PlayArea.left_x + (PlayArea.WIDTH/2) - Block.SIZE;
        MINO_START_Y = PlayArea.top_y + Block.SIZE;

        NEXT_MINO_X = PlayArea.right_x + 180;
        NEXT_MINO_Y = PlayArea.top_y + 510;
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

    private void refillBag() {
        minoBag.clear();
        minoBag.add(new Mino_LL());
        minoBag.add(new Mino_LR());
        minoBag.add(new Mino_ZL());
        minoBag.add(new Mino_ZR());
        minoBag.add(new Mino_T());
        minoBag.add(new Mino_Sqare());
        minoBag.add(new Mino_Bar());

        Collections.shuffle(minoBag); // shuffle the bag
    }

    public Mino pickMino() {
        // pick a random mino
        if(minoBag.isEmpty()) {
            refillBag();
        }
        return minoBag.removeFirst(); // get and remove first tetromino in bag
    }
}

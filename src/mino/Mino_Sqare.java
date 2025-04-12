package mino;

import java.awt.*;

public class Mino_Sqare extends Mino{
    public Mino_Sqare() {
        create(Color.YELLOW);
    }

    public void setXY(int x, int y) {
        // . .
        // . .
        getB()[0].setX(x);
        getB()[0].setY(y);
        getB()[1].setX(x);
        getB()[1].setY(y + Block.SIZE);
        getB()[2].setX(x + Block.SIZE);
        getB()[2].setY(y);
        getB()[3].setX(x + Block.SIZE);
        getB()[3].setY(y + Block.SIZE);
    }
}

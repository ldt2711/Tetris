package model.mino;

import java.awt.*;

public class Mino_LR extends Mino{
    public Mino_LR() {
        create(Color.orange);
    }

    public void setXY(int x, int y) {
        // .    b[1]
        // .    b[0] for rotate
        // . .  b[2], b[3]
        getB()[0].setX(x);
        getB()[0].setY(y);
        getB()[1].setX(x);
        getB()[1].setY(y - Block.SIZE);
        getB()[2].setX(x);
        getB()[2].setY(y + Block.SIZE);
        getB()[3].setX(x + Block.SIZE);
        getB()[3].setY(y + Block.SIZE);
    }

    public void getDirection1() {
        // .
        // . b[0]
        // . .
        getTempB()[0].setX(getB()[0].getCorX());
        getTempB()[0].setY(getB()[0].getCorY());
        getTempB()[1].setX(getB()[0].getCorX());
        getTempB()[1].setY(getB()[0].getCorY() - Block.SIZE);
        getTempB()[2].setX(getB()[0].getCorX());
        getTempB()[2].setY(getB()[0].getCorY() + Block.SIZE);
        getTempB()[3].setX(getB()[0].getCorX() + Block.SIZE);
        getTempB()[3].setY(getB()[0].getCorY() + Block.SIZE);

    }

    public void getDirection2() {
        //     b[0] b[1]
        // .    .    .
        // .
        getTempB()[0].setX(getB()[0].getCorX());
        getTempB()[0].setY(getB()[0].getCorY());
        getTempB()[1].setX(getB()[0].getCorX() + Block.SIZE);
        getTempB()[1].setY(getB()[0].getCorY());
        getTempB()[2].setX(getB()[0].getCorX() - Block.SIZE);
        getTempB()[2].setY(getB()[0].getCorY());
        getTempB()[3].setX(getB()[0].getCorX() - Block.SIZE);
        getTempB()[3].setY(getB()[0].getCorY() + Block.SIZE);

    }

    public void getDirection3() {
        // .    .
        //      . b[0]
        //      . b[1]
        getTempB()[0].setX(getB()[0].getCorX());
        getTempB()[0].setY(getB()[0].getCorY());
        getTempB()[1].setX(getB()[0].getCorX());
        getTempB()[1].setY(getB()[0].getCorY() + Block.SIZE);
        getTempB()[2].setX(getB()[0].getCorX());
        getTempB()[2].setY(getB()[0].getCorY() - Block.SIZE);
        getTempB()[3].setX(getB()[0].getCorX() - Block.SIZE);
        getTempB()[3].setY(getB()[0].getCorY() - Block.SIZE);

    }

    public void getDirection4() {
        //          .
        // .    .   .
        // b[1] b[0]
        getTempB()[0].setX(getB()[0].getCorX());
        getTempB()[0].setY(getB()[0].getCorY());
        getTempB()[1].setX(getB()[0].getCorX() - Block.SIZE);
        getTempB()[1].setY(getB()[0].getCorY());
        getTempB()[2].setX(getB()[0].getCorX() + Block.SIZE);
        getTempB()[2].setY(getB()[0].getCorY());
        getTempB()[3].setX(getB()[0].getCorX() + Block.SIZE);
        getTempB()[3].setY(getB()[0].getCorY() - Block.SIZE);

    }
}

package model.mino;

import java.awt.*;

public class Mino_T extends Mino{
    public Mino_T() {
        create(Color.magenta);
    }

    public void setXY(int x, int y) {
        //      .
        // .    .    .
        //     b[0]
        getB()[0].setX(x);
        getB()[0].setY(y);
        getB()[1].setX(x);
        getB()[1].setY(y - Block.SIZE);
        getB()[2].setX(x - Block.SIZE);
        getB()[2].setY(y);
        getB()[3].setX(x + Block.SIZE);
        getB()[3].setY(y);
    }

    public void getDirection1() {
        //      .
        // .    .    .
        //     b[0]
        getTempB()[0].setX(getB()[0].getCorX());
        getTempB()[0].setY(getB()[0].getCorY());
        getTempB()[1].setX(getB()[0].getCorX());
        getTempB()[1].setY(getB()[0].getCorY() - Block.SIZE);
        getTempB()[2].setX(getB()[0].getCorX() - Block.SIZE);
        getTempB()[2].setY(getB()[0].getCorY());
        getTempB()[3].setX(getB()[0].getCorX() + Block.SIZE);
        getTempB()[3].setY(getB()[0].getCorY());

    }

    public void getDirection2() {
        //      .
        // b[0] .    .
        //      .
        getTempB()[0].setX(getB()[0].getCorX());
        getTempB()[0].setY(getB()[0].getCorY());
        getTempB()[1].setX(getB()[0].getCorX() + Block.SIZE);
        getTempB()[1].setY(getB()[0].getCorY());
        getTempB()[2].setX(getB()[0].getCorX());
        getTempB()[2].setY(getB()[0].getCorY() - Block.SIZE);
        getTempB()[3].setX(getB()[0].getCorX());
        getTempB()[3].setY(getB()[0].getCorY() + Block.SIZE);

    }

    public void getDirection3() {
        //    b[0]
        // .    .   .
        //      .
        getTempB()[0].setX(getB()[0].getCorX());
        getTempB()[0].setY(getB()[0].getCorY());
        getTempB()[1].setX(getB()[0].getCorX());
        getTempB()[1].setY(getB()[0].getCorY() + Block.SIZE);
        getTempB()[2].setX(getB()[0].getCorX() + Block.SIZE);
        getTempB()[2].setY(getB()[0].getCorY());
        getTempB()[3].setX(getB()[0].getCorX() - Block.SIZE);
        getTempB()[3].setY(getB()[0].getCorY());

    }

    public void getDirection4() {
        //      .
        // .    . b[0]
        //      .
        getTempB()[0].setX(getB()[0].getCorX());
        getTempB()[0].setY(getB()[0].getCorY());
        getTempB()[1].setX(getB()[0].getCorX() - Block.SIZE);
        getTempB()[1].setY(getB()[0].getCorY());
        getTempB()[2].setX(getB()[0].getCorX());
        getTempB()[2].setY(getB()[0].getCorY() + Block.SIZE);
        getTempB()[3].setX(getB()[0].getCorX());
        getTempB()[3].setY(getB()[0].getCorY() - Block.SIZE);

    }
}

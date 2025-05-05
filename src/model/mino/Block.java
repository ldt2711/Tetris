package model.mino;

import java.awt.*;

public class Block extends Rectangle {
    private int x, y;
    private Color c;
    public static int SIZE = 30; // block 30x30

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public Color getC() {
        return c;
    }

    public int getCorX() {
        return x;
    }

    public int getCorY() {
        return y;
    }

    public Block(Color c) {
        this.c = c;
    }
}

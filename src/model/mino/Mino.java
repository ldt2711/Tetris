package model.mino;

import java.awt.*;

public class Mino implements IMino{
    private Block[] b = new Block[4]; // every tetromino has 4 blocks
    private Block[] tempB = new Block[4]; // use when rotate and the tetromino touches the wall
    private Block[] ghostB = new Block[4]; // for ghost block
    private int direction = 1; // 4 direction (1,2,3,4)
    public boolean active = true;
    public boolean deactivating; // the tetromino hits the floor and can still move

    public Block[] getB() {
        return b;
    }

    public Block[] getTempB() {
        return tempB;
    }

    public Block[] getGhostB() {return ghostB;}

    public int getDirection() {return direction;}

    public void setDirection(int direction) {this.direction = direction;}

    public void create(Color c) {
        b[0] = new Block(c);
        b[1] = new Block(c);
        b[2] = new Block(c);
        b[3] = new Block(c);
        tempB[0] = new Block(c);
        tempB[1] = new Block(c);
        tempB[2] = new Block(c);
        tempB[3] = new Block(c);
        ghostB[0] = new Block(c);
        ghostB[1] = new Block(c);
        ghostB[2] = new Block(c);
        ghostB[3] = new Block(c);
    }

    public void setXY(int x, int y) {}
    public void getDirection1() {} // default direction
    public void getDirection2() {}
    public void getDirection3() {}
    public void getDirection4() {}
}

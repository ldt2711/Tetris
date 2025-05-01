package mino;

import Main.KeyHandler;
import Main.PlayManager;

import java.awt.*;

public class Mino implements IMino{
    private Block[] b = new Block[4]; // every tetromino has 4 blocks
    private Block[] tempB = new Block[4]; // use when rotate and the tetromino touches the wall
    private Block[] ghostB = new Block[4]; // for ghost block
    int autoDropCounter = 0;
    public int direction = 1; // 4 direction (1,2,3,4)
    boolean leftCollision, rightCollision, bottomCollision;
    public boolean active = true;
    public boolean deactivating; // the tetromino hits the floor and can still move
    int deactivateCounter = 0; // counting for deactivating method

    public Block[] getB() {
        return b;
    }

    public Block[] getTempB() {
        return tempB;
    }

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
    public void updateXY(int direction) {
        checkRotationCollision();

        if(!leftCollision && !rightCollision && !bottomCollision) {
            this.direction = direction;
            b[0].setX(tempB[0].getCorX());
            b[0].setY(tempB[0].getCorY());
            b[1].setX(tempB[1].getCorX());
            b[1].setY(tempB[1].getCorY());
            b[2].setX(tempB[2].getCorX());
            b[2].setY(tempB[2].getCorY());
            b[3].setX(tempB[3].getCorX());
            b[3].setY(tempB[3].getCorY());
        }
    }
    public void getDirection1() {} // default direction
    public void getDirection2() {}
    public void getDirection3() {}
    public void getDirection4() {}
    public void checkMovementCollision() {
        leftCollision = false;
        rightCollision = false;
        bottomCollision = false;

        // check staticBlocks collision
        checkStaticBlockCollision();

        // check frame collision
        // left wall
        for (Block block : b) {
            if (block.getCorX() == PlayManager.left_x) {
                leftCollision = true;
                break;
            }
        }
        // right wall
        for (Block block : b) {
            if (block.getCorX() + Block.SIZE == PlayManager.right_x) {
                rightCollision = true;
                break;
            }
        }
        // bottom floor
        for (Block block : b) {
            if (block.getCorY() + Block.SIZE == PlayManager.bottom_y) {
                bottomCollision = true;
                break;
            }
        }
    }
    public void checkRotationCollision() {
        leftCollision = false;
        rightCollision = false;
        bottomCollision = false;

        // check staticBlocks collision
        checkStaticBlockCollision();

        // check frame collision use tempB because it stores the rotation values
        // left wall
        for (Block block : tempB) {
            if (block.getCorX() < PlayManager.left_x) {
                leftCollision = true;
            }
        }
        // right wall
        for (Block block : tempB) {
            if (block.getCorX() + Block.SIZE > PlayManager.right_x) {
                rightCollision = true;
            }
        }
        // bottom floor
        for (Block block : tempB) {
            if (block.getCorY() + Block.SIZE > PlayManager.bottom_y) {
                bottomCollision = true;
            }
        }
    }

    public void checkStaticBlockCollision() {
        for (Block item1: PlayManager.staticBlocks) {
            int targetX = item1.getCorX();
            int targetY = item1.getCorY();

            // check down
            for (Block item2: b) {
                if (item2.getCorX() == targetX && item2.getCorY() + Block.SIZE == targetY) {
                    bottomCollision = true;
                }
            }
            // check left
            for (Block item2: b) {
                if (item2.getCorX() - Block.SIZE == targetX && item2.getCorY() == targetY) {
                    leftCollision = true;
                }
            }
            for (Block item2: b) {
                if (item2.getCorX() + Block.SIZE == targetX && item2.getCorY() == targetY) {
                    rightCollision = true;
                }
            }
        }
    }

    public void update() {
        if (deactivating) {
            deactivating();
        }

        // move the mino
        if(KeyHandler.upPressed) {
            switch (direction) {
                case 1:
                    getDirection2();
                    break;
                case 2:
                    getDirection3();
                    break;
                case 3:
                    getDirection4();
                    break;
                case 4:
                    getDirection1();
                    break;
            }
            KeyHandler.upPressed = false;
        }

        checkMovementCollision();

        if(KeyHandler.downPressed) {
            // if the mino's bottom is not hitting, it can go down
            if (!bottomCollision) {
                b[0].setY(b[0].getCorY() + Block.SIZE);
                b[1].setY(b[1].getCorY() + Block.SIZE);
                b[2].setY(b[2].getCorY() + Block.SIZE);
                b[3].setY(b[3].getCorY() + Block.SIZE);
                // reset autoDropCounter when move down 1 block
                autoDropCounter = 0;
            }
            KeyHandler.downPressed = false;
        }
        if(KeyHandler.rightPressed) {
            if (!rightCollision) {
                b[0].setX(b[0].getCorX() + Block.SIZE);
                b[1].setX(b[1].getCorX() + Block.SIZE);
                b[2].setX(b[2].getCorX() + Block.SIZE);
                b[3].setX(b[3].getCorX() + Block.SIZE);
            }
            KeyHandler.rightPressed = false;
        }
        if(KeyHandler.leftPressed) {
            if (!leftCollision) {
                b[0].setX(b[0].getCorX() - Block.SIZE);
                b[1].setX(b[1].getCorX() - Block.SIZE);
                b[2].setX(b[2].getCorX() - Block.SIZE);
                b[3].setX(b[3].getCorX() - Block.SIZE);
            }
            KeyHandler.leftPressed = false;
        }

        updateGhostBlock();

        if (KeyHandler.spacePressed) {
            while (true) {
                checkMovementCollision();
                // move the mino till it hits the floor
                if (!bottomCollision) {
                    b[0].setY(b[0].getCorY() + Block.SIZE);
                    b[1].setY(b[1].getCorY() + Block.SIZE);
                    b[2].setY(b[2].getCorY() + Block.SIZE);
                    b[3].setY(b[3].getCorY() + Block.SIZE);
                } else {
                    active = false;
                    break;
                }
            }
            KeyHandler.spacePressed = false;
        }

        // stop the tetromino when it hits the bottom floor
        if (bottomCollision) {
            deactivating = true;
        }
        else {
            autoDropCounter++; // the counter increase every frame until it equals dropInterval
            if (autoDropCounter == PlayManager.dropInterval) {
                b[0].setY(b[0].getCorY() + Block.SIZE);
                b[1].setY(b[1].getCorY() + Block.SIZE);
                b[2].setY(b[2].getCorY() + Block.SIZE);
                b[3].setY(b[3].getCorY() + Block.SIZE);
                autoDropCounter = 0;
            }
        }

    }

    public void updateGhostBlock() {
        // copy position
        for (int i = 0; i < 4; i++) {
            ghostB[i].setX(b[i].getCorX());
            ghostB[i].setY(b[i].getCorY());
        }

        // move down until hit the floor
        while (true) {
            boolean isHit = false;
            for (Block gb: ghostB) {
                int nextY = gb.getCorY() + Block.SIZE;
                if (nextY == PlayManager.bottom_y) {
                    isHit = true;
                    break;
                }

                // check static block
                for (Block sb: PlayManager.staticBlocks) {
                    if (sb.getCorX() == gb.getCorX() && sb.getCorY() == nextY) {
                        isHit = true;
                        break;
                    }
                }
                if (isHit) break;
            }
            if (isHit) break;

            // move ghost blocks down
            for (Block gb: ghostB) {
                gb.setY(gb.getCorY() + Block.SIZE);
            }
        }
    }

    public void deactivating() {
        deactivateCounter++;

        // wait 45 frames until deactivate
        if (deactivateCounter == 45) {
            deactivateCounter = 0;
            checkMovementCollision(); // check if the bottom is still hitting

            // if the bottom is still hitting after 45 frames, deactivate the mino
            if (bottomCollision) {
                active = false;
            }
        }
    }

    public void draw(Graphics2D g2) {
        int margin = 2;
        // draw all the blocks of tetromino
        g2.setColor(b[0].getC());
        for (Block bl : b) {
            g2.fillRect(bl.getCorX() + margin, bl.getCorY() + margin, Block.SIZE - (margin * 2), Block.SIZE - (margin * 2));
        }

        // draw ghost blocks
        g2.setColor(new Color(b[0].getC().getRed(), b[0].getC().getGreen(), b[0].getC().getBlue(), 50));
        // g2.setColor(new Color(200, 200, 200, 120));
        // g2.setStroke(new BasicStroke(2)); // set the stroke for the ghost block
        for (Block gb : ghostB) {
            if (gb.getCorX() >= PlayManager.left_x && gb.getCorY() >= 0) {
                g2.fillRect(gb.getCorX() + margin, gb.getCorY() + margin, Block.SIZE - (margin * 2), Block.SIZE - (margin * 2));
            }
        }

    }

}

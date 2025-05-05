package controller;

import model.mino.Block;
import model.mino.Mino;

public class MovementManager {
    public boolean changeDirection(Mino m) {
        if(KeyHandler.upPressed) {
            switch (m.getDirection()) {
                case 1:
                    m.getDirection2();
                    break;
                case 2:
                    m.getDirection3();
                    break;
                case 3:
                    m.getDirection4();
                    break;
                case 4:
                    m.getDirection1();
                    break;
            }
            KeyHandler.upPressed = false;
            return true;
        }
        return false;
    }

    public boolean softDrop(Mino m) {
        if(KeyHandler.downPressed) {
            m.getB()[0].setY(m.getB()[0].getCorY() + Block.SIZE);
            m.getB()[1].setY(m.getB()[1].getCorY() + Block.SIZE);
            m.getB()[2].setY(m.getB()[2].getCorY() + Block.SIZE);
            m.getB()[3].setY(m.getB()[3].getCorY() + Block.SIZE);
            KeyHandler.downPressed = false;
            return true;
        }
        return false;
    }

    public void moveRight(Mino m, CollisionManager cm) {
        if(KeyHandler.rightPressed) {
            if (!cm.rightCollision) {
                m.getB()[0].setX(m.getB()[0].getCorX() + Block.SIZE);
                m.getB()[1].setX(m.getB()[1].getCorX() + Block.SIZE);
                m.getB()[2].setX(m.getB()[2].getCorX() + Block.SIZE);
                m.getB()[3].setX(m.getB()[3].getCorX() + Block.SIZE);
            }
            KeyHandler.rightPressed = false;
        }
    }

    public void moveLeft(Mino m, CollisionManager cm) {
        if(KeyHandler.leftPressed) {
            if (!cm.leftCollision) {
                m.getB()[0].setX(m.getB()[0].getCorX() - Block.SIZE);
                m.getB()[1].setX(m.getB()[1].getCorX() - Block.SIZE);
                m.getB()[2].setX(m.getB()[2].getCorX() - Block.SIZE);
                m.getB()[3].setX(m.getB()[3].getCorX() - Block.SIZE);
            }
            KeyHandler.leftPressed = false;
        }
    }

    public boolean hardDrop(Mino m, CollisionManager cm, ScoreManager sm) {
        int dropCount = 0;
        if(KeyHandler.spacePressed) {
            while(true) {
                cm.checkMovementCollision(m);
                dropCount++;
                if(cm.bottomCollision) {
                    break;
                }
                m.getB()[0].setY(m.getB()[0].getCorY() + Block.SIZE);
                m.getB()[1].setY(m.getB()[1].getCorY() + Block.SIZE);
                m.getB()[2].setY(m.getB()[2].getCorY() + Block.SIZE);
                m.getB()[3].setY(m.getB()[3].getCorY() + Block.SIZE);
            }
            KeyHandler.spacePressed = false;
            sm.calculateScore(dropCount);
            return true;
        }
        return false;
    }
}

package controller;

import model.GameState;
import model.MinoGenerator;
import model.mino.Block;
import model.mino.Mino;
import view.GamePanel;
import view.PlayArea;

public class MinoManager {

    int autoDropCounter = 0;
    int deactivateCounter = 0; // counting for deactivating method

    CollisionManager cm = new CollisionManager();
    MovementManager mm = new MovementManager();
    PlayManager pm;

    public MinoManager(PlayManager pm) {
        this.pm = pm;
    }

    public void updateXY(int direction, Mino m) {
        cm.checkRotationCollision(m);

        if(!cm.leftCollision && !cm.rightCollision && !cm.bottomCollision) {
            m.setDirection(direction);
            m.getB()[0].setX(m.getTempB()[0].getCorX());
            m.getB()[0].setY(m.getTempB()[0].getCorY());
            m.getB()[1].setX(m.getTempB()[1].getCorX());
            m.getB()[1].setY(m.getTempB()[1].getCorY());
            m.getB()[2].setX(m.getTempB()[2].getCorX());
            m.getB()[2].setY(m.getTempB()[2].getCorY());
            m.getB()[3].setX(m.getTempB()[3].getCorX());
            m.getB()[3].setY(m.getTempB()[3].getCorY());
        }
    }

    public void update(Mino m, ScoreManager sm) {

        // move the mino
        // change direction
        if(mm.changeDirection(m)) {
            m.setDirection((m.getDirection() % 4) + 1);
            updateXY(m.getDirection(), m);
        }

        cm.checkMovementCollision(m);
        // soft drop
        if(!cm.bottomCollision) {
            // if the mino's bottom is not hitting, it can go down
            if(mm.softDrop(m)) {
                // reset autoDropCounter when move down 1 block
                autoDropCounter = 0;
            }
        }
        // move right
        mm.moveRight(m, cm);
        // move left
        mm.moveLeft(m, cm);

       updateGhostBlock(m);

        // hard drop
        if(mm.hardDrop(m, cm, sm)) {
            m.active = false;
            // dust effect
            for (Block b : m.getB()) {
                pm.createDust(b.getCorX(), b.getCorY() + Block.SIZE);
            }
        }

        // stop the tetromino when it hits the bottom floor
        if (cm.bottomCollision) {
            m.deactivating = true;
        }
        else {
            autoDropCounter++; // the counter increase every frame until it equals dropInterval
            if (autoDropCounter >= PlayManager.dropInterval) {
                m.getB()[0].setY(m.getB()[0].getCorY() + Block.SIZE);
                m.getB()[1].setY(m.getB()[1].getCorY() + Block.SIZE);
                m.getB()[2].setY(m.getB()[2].getCorY() + Block.SIZE);
                m.getB()[3].setY(m.getB()[3].getCorY() + Block.SIZE);
                autoDropCounter = 0;
            }
        }
        if (m.deactivating) {
            deactivating(m);
        }
    }

    public void deactivating(Mino m) {
        deactivateCounter++;

        // wait 45 frames until deactivate
        if (deactivateCounter == 45) {
            deactivateCounter = 0;
            cm.checkMovementCollision(m); // check if the bottom is still hitting

            // if the bottom is still hitting after 45 frames, deactivate the mino
            if (cm.bottomCollision) {
                m.active = false;
            }
        }
    }

    public void updateGhostBlock(Mino m) {
        // copy position
        for (int i = 0; i < 4; i++) {
            m.getGhostB()[i].setX(m.getB()[i].getCorX());
            m.getGhostB()[i].setY(m.getB()[i].getCorY());
        }

        // move down until hit the floor
        while (true) {
            boolean isHit = false;
            for (Block gb: m.getGhostB()) {
                int nextY = gb.getCorY() + Block.SIZE;
                if (nextY == PlayArea.bottom_y) {
                    isHit = true;
                    break;
                }

                // check static block
                for (Block sb: GameState.staticBlocks) {
                    if (sb.getCorX() == gb.getCorX() && sb.getCorY() == nextY) {
                        isHit = true;
                        break;
                    }
                }
                if (isHit) break;
            }
            if (isHit) break;

            // move ghost blocks down
            for (Block gb: m.getGhostB()) {
                gb.setY(gb.getCorY() + Block.SIZE);
            }
        }
    }
}

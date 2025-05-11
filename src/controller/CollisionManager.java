package controller;

import model.GameState;
import model.mino.Block;
import model.mino.Mino;
import view.PlayArea;

public class CollisionManager {

    boolean leftCollision, rightCollision, bottomCollision;

    public void checkMovementCollision(Mino mino) {
        leftCollision = false;
        rightCollision = false;
        bottomCollision = false;

        // check staticBlocks collision
        checkStaticBlockCollision(mino);

        // check frame collision
        // left wall
        for (Block block : mino.getB()) {
            if (block.getCorX() == PlayArea.left_x) {
                leftCollision = true;
                break;
            }
        }
        // right wall
        for (Block block : mino.getB()) {
            if (block.getCorX() + Block.SIZE == PlayArea.right_x) {
                rightCollision = true;
                break;
            }
        }
        // bottom floor
        for (Block block : mino.getB()) {
            if (block.getCorY() + Block.SIZE == PlayArea.bottom_y) {
                bottomCollision = true;
                break;
            }
        }
    }
    public void checkRotationCollision(Mino mino) {
        leftCollision = false;
        rightCollision = false;
        bottomCollision = false;

        // check staticBlocks collision
        checkStaticBlockCollision(mino);

        // check frame collision use tempB because it stores the rotation values
        // left wall
        for (Block block : mino.getTempB()) {
            if (block.getCorX() < PlayArea.left_x) {
                leftCollision = true;
            }
        }
        // right wall
        for (Block block : mino.getTempB()) {
            if (block.getCorX() + Block.SIZE > PlayArea.right_x) {
                rightCollision = true;
            }
        }
        // bottom floor
        for (Block block : mino.getTempB()) {
            if (block.getCorY() + Block.SIZE > PlayArea.bottom_y) {
                bottomCollision = true;
            }
        }
    }

    public void checkStaticBlockCollision(Mino mino) {
        for (Block item1: PlayManager.staticBlocks) {
            int targetX = item1.getCorX();
            int targetY = item1.getCorY();

            // check down
            for (Block item2: mino.getB()) {
                if (item2.getCorX() == targetX && item2.getCorY() + Block.SIZE == targetY) {
                    bottomCollision = true;
                }
            }
            // check left
            for (Block item2: mino.getB()) {
                if (item2.getCorX() - Block.SIZE == targetX && item2.getCorY() == targetY) {
                    leftCollision = true;
                }
            }
            for (Block item2: mino.getB()) {
                if (item2.getCorX() + Block.SIZE == targetX && item2.getCorY() == targetY) {
                    rightCollision = true;
                }
            }
        }
    }
}

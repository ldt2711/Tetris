package view;

import model.mino.Block;

import java.awt.*;
import java.util.ArrayList;

public class DeleteLineEffect {
    int effectCounter;

    public boolean deleteEffect(int left_x, int WIDTH, boolean effectCounterOn, ArrayList<Integer> effectY, Graphics2D g2) {
        // draw effect
        if (effectCounterOn) {
            effectCounter++;

            for (Integer i : effectY) {
                float ratio = effectCounter / 10f;
                Color fadeColor = new Color(1.0f, 1.0f - ratio, 1.0f - ratio);

                g2.setColor(fadeColor);
                g2.fillRect(left_x, i, WIDTH, Block.SIZE);
            }
            // reset after 10 frame
            if (effectCounter == 10) {
                effectCounter = 0;
                effectY.clear();
                return false;
            }
            else return true;
        }
        return false;
    }
}

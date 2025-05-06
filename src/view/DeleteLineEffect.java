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

            for (Integer y : effectY) {
                // Alpha dao động để tạo hiệu ứng nhấp nháy
                float alpha = (float) Math.abs(Math.sin(Math.PI * effectCounter / 10)); // từ 0 -> 1 -> 0

                // Vẽ lớp mờ trắng nhấp nháy lên các block
                Composite oldComposite = g2.getComposite();
                g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha));

                g2.setColor(Color.WHITE);
                g2.fillRect(left_x, y, WIDTH, Block.SIZE);

                g2.setComposite(oldComposite); // phục hồi lại transparency gốc
            }
            // reset after 10 frame
            if (effectCounter == 10) {
                effectCounter = 0;
                return false;
            }
            else return true;
        }
        return false;
    }
}

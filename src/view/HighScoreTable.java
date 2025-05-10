package view;

import model.GameState;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class HighScoreTable extends JPanel{
    public HighScoreTable(ArrayList<GameState> state) {
        setLayout(new BorderLayout());
        setMaximumSize(new Dimension(300, 250));
        setBackground(Color.black);
        setBorder(BorderFactory.createLineBorder(Color.white, 3));

        JLabel title = createLabel("HIGH SCORES", 20, Color.black);
        title.setMaximumSize(new Dimension(300, 40));
        add(title, BorderLayout.NORTH);

        JPanel table = new JPanel(new GridLayout(5,2,2,2));
        table.setBorder(BorderFactory.createEmptyBorder(0, 10, 5, 10));
        table.setBackground(Color.black);

        int rowCount = 5;
        for (int i = rowCount - 1; i >= 0; i--) {
            Color color = i%2!=0 ? new Color(23, 23, 23) : new Color(31, 31, 31);
            if (i < state.size()) {
                table.add(createLabel(state.get(i).getPlayerName(), 16, color));
                table.add(createLabel(String.format("%,d", state.get(i).getScore()), 16, color));
            } else {
                table.add(createLabel("", 16, color));
                table.add(createLabel(i + 1 + ",000", 16, color));
            }
        }

        add(table, BorderLayout.CENTER);
        setBorder(BorderFactory.createLineBorder(Color.white, 3));
    }

    private JLabel createLabel(String text, int size, Color color) {
        JLabel label = new JLabel(text, SwingConstants.CENTER);
        label.setOpaque(true);
        label.setBackground(color);
        label.setFont(new Font("Monospaced", Font.BOLD, size));
        label.setForeground(Color.white);
        return label;
    }
}

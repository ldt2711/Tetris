package view.panel;

import view.MainWindow;
import view.PlayArea;
import view.SelectButton;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class HelpPanel extends JPanel {
    private SelectButton doneButton;
    private JLabel label;
    private MainWindow mainMenu;

    public HelpPanel(MainWindow mainMenu, int i) {
        this.mainMenu = mainMenu;

        this.setPreferredSize(new Dimension(GamePanel.WIDTH, GamePanel.HEIGHT));
        this.setBackground(Color.black);
        this.setLayout(null);

        BasicUI.initResources();

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (BasicUI.soundButtonBounds.contains(e.getPoint())) {
                    BasicUI.toggleMute();
                    repaint();
                }
            }
        });

        JPanel content = new JPanel();
        content.setBounds(PlayArea.left_x + 40, PlayArea.top_y + 100, PlayArea.WIDTH - 80, PlayArea.HEIGHT - 200);
        content.setLayout(new BoxLayout(content, BoxLayout.Y_AXIS));
        content.setBackground(Color.black);
        content.setBorder(BorderFactory.createLineBorder(Color.white, 3));

        // label
        label = new JLabel("control keys");
        label.setAlignmentX(Component.CENTER_ALIGNMENT);
        label.setForeground(Color.WHITE);
        label.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));
        label.setFont(new Font("Arial Black", Font.BOLD, 30));
        content.add(Box.createVerticalStrut(30));
        content.add(label);

        // instruction
        JPanel instruction = new JPanel();
        instruction.setLayout(new GridLayout(6, 2, 0, 1));
        instruction.setBorder(BorderFactory.createEmptyBorder(0, 5, 0, 5));
        instruction.setBackground(Color.black);

        instruction.add(createKey("Left arrow "));
        instruction.add(createFunction("Move left"));
        instruction.add(createKey("Right arrow "));
        instruction.add(createFunction("Move right"));
        instruction.add(createKey("Up arrow "));
        instruction.add(createFunction("Rotate"));
        instruction.add(createKey("Down arrow "));
        instruction.add(createFunction("Soft drop"));
        instruction.add(createKey("Space "));
        instruction.add(createFunction("Hard drop"));
        instruction.add(createKey("Esc "));
        instruction.add(createFunction("Pause"));

        content.add(instruction);

        doneButton = new SelectButton("done");
        doneButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        doneButton.setMaximumSize(new Dimension(100, 70));
        doneButton.addActionListener(e -> {
            if (i == 0) {
                mainMenu.showMainMenu();
            } else {
                mainMenu.showPausePanel();
            }
        });
        content.add(doneButton);
        content.add(Box.createVerticalStrut(20));

        this.add(content);
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g.create();
        BasicUI.drawGame(g2, mainMenu.getGamePanel().pm.getLevel(), mainMenu.getGamePanel().pm.getScore(), mainMenu.getGamePanel().pm.getLines(), this);
    }

    private JLabel createKey(String str) {
        JLabel insKey = new JLabel(str);
        insKey.setBackground(Color.black);
        insKey.setForeground(Color.white);
        insKey.setFont(new Font("Monospaced", Font.BOLD, 16));
        insKey.setMaximumSize(new Dimension(50, 50));
        insKey.setHorizontalAlignment(SwingConstants.RIGHT);
        return insKey;
    }

    private JLabel createFunction(String str) {
        JLabel insKey = new JLabel("- " + str);
        insKey.setBackground(Color.black);
        insKey.setForeground(Color.white);
        insKey.setFont(new Font("Monospaced", Font.PLAIN, 16));
        insKey.setMaximumSize(new Dimension(50, 50));
        return insKey;
    }
}

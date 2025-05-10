package view.panel;

import controller.ConfigManager;
import view.MainWindow;
import view.PlayArea;
import view.SelectButton;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.HashMap;
import java.util.Map;

public class OptionsPanel extends JPanel {
    private final Map<String, SelectButton> keyButtons = new HashMap<>();
    private final ConfigManager configManager;
    private MainWindow mainMenu;
    private JPanel content;

    public OptionsPanel(MainWindow mainMenu, int panel) {
        this.mainMenu = mainMenu;
        this.configManager = new ConfigManager();

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

        this.content = loadKeyBind(panel);

        this.add(content);
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g.create();
        BasicUI.drawGame(g2, mainMenu.getGamePanel().pm.getLevel(), mainMenu.getGamePanel().pm.getScore(), mainMenu.getGamePanel().pm.getLines(),this);
    }

    private JPanel loadKeyBind(int panel) {
        JPanel panelContent = new JPanel();
        panelContent.setBounds(PlayArea.left_x + 40, PlayArea.top_y + 100, PlayArea.WIDTH - 80, PlayArea.HEIGHT - 200);
        panelContent.setLayout(new BoxLayout(panelContent, BoxLayout.Y_AXIS));
        panelContent.setBackground(Color.black);
        panelContent.setBorder(BorderFactory.createLineBorder(Color.white, 3));
        panelContent.add(Box.createVerticalStrut(20));

        for (String action : configManager.getAllKeyBindings().keySet()) {
            SelectButton btn = new SelectButton(KeyEvent.getKeyText(configManager.getKeyCode(action)));
            btn.setMaximumSize(new Dimension(150, 50));
            keyButtons.put(action, btn);

            JLabel label = new JLabel(action);
            label.setMaximumSize(new Dimension(80, 50));
            label.setHorizontalAlignment(SwingConstants.LEFT);
            label.setForeground(Color.white);

            JPanel keyChange = new JPanel();
            keyChange.setLayout(new BoxLayout(keyChange, BoxLayout.X_AXIS));
            keyChange.setBackground(Color.black);
            keyChange.add(label);
            keyChange.add(btn);

            btn.addActionListener(e -> {
                btn.setText("Press key...");
                btn.requestFocus();
                btn.addKeyListener(new KeyAdapter() {
                    @Override
                    public void keyPressed(KeyEvent e) {
                        configManager.updateKey(action, e.getKeyCode());
                        btn.setText(KeyEvent.getKeyText(e.getKeyCode()));
                        btn.removeKeyListener(this);
                    }
                });
            });

            keyChange.setAlignmentX(Component.CENTER_ALIGNMENT);
            panelContent.add(Box.createVerticalStrut(20));
            panelContent.add(keyChange);
        }

        addButton(panelContent, panel);
        return panelContent;
    }

    private void addButton(JPanel panelContent, int panel) {
        JPanel bottomButton = new JPanel();
        bottomButton.setLayout(new BoxLayout(bottomButton, BoxLayout.X_AXIS));
        bottomButton.setBackground(Color.black);
        panelContent.add(Box.createVerticalStrut(20));

        SelectButton saveButton = new SelectButton("save");
        saveButton.setMaximumSize(new Dimension(100, 50));
        saveButton.addActionListener(e -> {
            configManager.saveConfig();
            if (panel == 0) {
                mainMenu.showMainMenu();
            } else {
                mainMenu.showPausePanel();
            }
        });
        saveButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        bottomButton.add(saveButton);
        bottomButton.add(Box.createHorizontalStrut(30));

        SelectButton resetButton = new SelectButton("reset");
        resetButton.setMaximumSize(new Dimension(100, 50));
        resetButton.addActionListener(e -> {
            this.remove(content);
            keyButtons.clear();
            configManager.resetToDefault();
            this.content = loadKeyBind(panel);
            this.add(content);
            this.revalidate();
            this.repaint();
        });
        resetButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        bottomButton.add(resetButton);

        panelContent.add(bottomButton);
        panelContent.add(Box.createVerticalStrut(20));
    }
}


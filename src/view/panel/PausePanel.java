package view.panel;

import controller.KeyHandler;
import controller.PlayManager;
import view.MainWindow;
import view.PlayArea;
import view.SelectButton;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Objects;

import static view.panel.GamePanel.playButtonBounds;

public class PausePanel extends JPanel {
    private SelectButton resumeButton, optionsButton, helpButton, quitButton;
    private static Image pauseIcon;
    MainWindow mainMenu;
    PlayManager playManager;

    public PausePanel(MainWindow mainMenu, GamePanel gamePanel) {
        this.mainMenu = mainMenu;
        this.playManager = gamePanel.pm;

        ClassLoader cl = BasicUI.class.getClassLoader();
        pauseIcon = new ImageIcon(Objects.requireNonNull(cl.getResource("image/icon/pause.png"))).getImage();

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
        content.setLayout(new BoxLayout(content, BoxLayout.Y_AXIS));
        content.setBounds(PlayArea.left_x + 40, PlayArea.top_y + 100, PlayArea.WIDTH - 80, PlayArea.HEIGHT - 200);
        content.setBackground(Color.black);
        content.setBorder(BorderFactory.createLineBorder(Color.white, 3));
        content.add(Box.createVerticalStrut(20));

        resumeButton = new SelectButton("resume");
        resumeButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        resumeButton.setMaximumSize(new Dimension(150, 70));
        resumeButton.addActionListener(e -> {
            mainMenu.continueGame();
            KeyHandler.pausePressed = false;
        });
        content.add(resumeButton);
        content.add(Box.createVerticalStrut(20));

        optionsButton = new SelectButton("options");
        optionsButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        optionsButton.setMaximumSize(new Dimension(150, 70));
        optionsButton.addActionListener(e -> mainMenu.showOptionsPanel(1));
        content.add(optionsButton);
        content.add(Box.createVerticalStrut(20));

        helpButton = new SelectButton("help");
        helpButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        helpButton.setMaximumSize(new Dimension(150, 70));
        helpButton.addActionListener(e -> mainMenu.showHelpPanel(1));
        content.add(helpButton);
        content.add(Box.createVerticalStrut(20));

        quitButton = new SelectButton("quit");
        quitButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        quitButton.setMaximumSize(new Dimension(150, 70));
        quitButton.addActionListener(e -> mainMenu.showMainMenu());
        content.add(quitButton);
        content.add(Box.createVerticalStrut(20));

        this.add(content);
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        BasicUI.drawGame(g2, playManager.getLevel(), playManager.getScore(), playManager.getLines(), this);
        Image iconToDraw = pauseIcon;
        g2.drawImage(iconToDraw, playButtonBounds.x, playButtonBounds.y,
                playButtonBounds.width, playButtonBounds.height, this);
    }

    public void pausePress() {
        if (KeyHandler.pausePressed) {
            mainMenu.continueGame();
        }
    }
}

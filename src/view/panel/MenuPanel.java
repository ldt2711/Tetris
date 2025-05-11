package view.panel;

import controller.SQLInOut;
import controller.SaveGame;
import model.GameState;
import view.HighScoreTable;
import view.MainWindow;
import view.PlayArea;
import view.SelectButton;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Objects;

public class MenuPanel extends JPanel {
    private SelectButton newGameButton, continueButton, optionsButton, helpButton;
    private HighScoreTable table;

    public MenuPanel(MainWindow mainApp) throws SQLException {
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

        JPanel mainMenu = new JPanel();
        mainMenu.setBounds(PlayArea.left_x, PlayArea.top_y, PlayArea.WIDTH, PlayArea.HEIGHT);
        mainMenu.setLayout(new BoxLayout(mainMenu, BoxLayout.Y_AXIS));
        mainMenu.setBackground(Color.black);

        // New Game
        newGameButton = new SelectButton("new game");
        newGameButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        newGameButton.setMaximumSize(new Dimension(250, 50));
        newGameButton.addActionListener(e -> mainApp.startNewGame());
        mainMenu.add(Box.createVerticalStrut(80));
        mainMenu.add(newGameButton);

        // Continue
        continueButton = new SelectButton("continue");
        continueButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        continueButton.setMaximumSize(new Dimension(250, 50));
        continueButton.addActionListener(e -> {
            mainApp.load();
        });
        mainMenu.add(Box.createVerticalStrut(30));
        mainMenu.add(continueButton);

        // High Scores Area
        ArrayList<GameState> scores = SQLInOut.getTopScore(5);
        table = new HighScoreTable(scores);
        mainMenu.add(Box.createVerticalStrut(30));
        mainMenu.add(table);

        // Bottom buttons panel
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 0));
        bottomPanel.setMaximumSize(new Dimension(300, 100));
        bottomPanel.setBackground(Color.BLACK);
        
        // Options button
        ImageIcon icon = new ImageIcon(Objects.requireNonNull(getClass().getResource("/resource/image/icon/options.png")));
        Image scaled = icon.getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH);
        optionsButton = new SelectButton(new ImageIcon(scaled));
        optionsButton.setPreferredSize(new Dimension(70, 70));
        optionsButton.addActionListener(e -> mainApp.showOptionsPanel(0));
        bottomPanel.add(optionsButton);

        // Help button
        helpButton = new SelectButton("?");
        helpButton.setFont(new Font("Arial Black", Font.BOLD, 40));
        helpButton.setPreferredSize(new Dimension(70, 70));
        helpButton.addActionListener(e -> mainApp.showHelpPanel(0));
        bottomPanel.add(helpButton);

        mainMenu.add(Box.createVerticalStrut(50));
        mainMenu.add(bottomPanel);

        add(mainMenu);
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        BasicUI.drawGame(g2,0,0,0, this);
    }
}

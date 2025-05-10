package view.panel;

import controller.PlayManager;
import controller.SQLInOut;
import view.MainWindow;
import view.PlaceholderTextField;
import view.PlayArea;
import view.SelectButton;

import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;

public class InsertNamePanel extends JPanel {
    private SelectButton quitButton, submitButton;
    private MainWindow mainMenu;
    private JTextField nameField;
    private PlayManager playManager;

    public InsertNamePanel(MainWindow mainMenu, GamePanel gamePanel) {
        this.mainMenu = mainMenu;
        this.playManager = gamePanel.pm;

        this.setPreferredSize(new Dimension(GamePanel.WIDTH, GamePanel.HEIGHT));
        this.setBackground(Color.black);
        this.setLayout(null);

        BasicUI.initResources();

        JPanel insertNameBox = new JPanel();
        insertNameBox.setBounds(PlayArea.left_x + 40, PlayArea.top_y + 125, PlayArea.WIDTH - 80, PlayArea.HEIGHT - 250);
        insertNameBox.setLayout(new BoxLayout(insertNameBox, BoxLayout.Y_AXIS));
        insertNameBox.setBackground(Color.black);
        insertNameBox.setBorder(BorderFactory.createLineBorder(Color.white, 3));

        // label
        JLabel label = new JLabel("game over");
        label.setAlignmentX(Component.CENTER_ALIGNMENT);
        label.setForeground(Color.WHITE);
        label.setFont(new Font("Monospaced", Font.BOLD, 25));
        insertNameBox.add(Box.createVerticalStrut(30));
        insertNameBox.add(label);

        // text field
        nameField = new PlaceholderTextField("insert your name");
        nameField.setMaximumSize(new Dimension(230, 50));
        nameField.setAlignmentX(Component.CENTER_ALIGNMENT);
        insertNameBox.add(Box.createVerticalStrut(30));
        insertNameBox.add(nameField);

        // bottom button
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 0));
        bottomPanel.setMaximumSize(new Dimension(300, 100));
        bottomPanel.setBackground(Color.BLACK);

        // submit button
        submitButton = new SelectButton("ok");
        submitButton.setPreferredSize(new Dimension(90, 70));
        submitButton.addActionListener(e -> {
            try {
                submitName();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        });
        bottomPanel.add(submitButton);

        // quit button
        quitButton = new SelectButton("quit");
        quitButton.setPreferredSize(new Dimension(90, 70));
        quitButton.addActionListener(e -> mainMenu.showMainMenu());
        bottomPanel.add(quitButton);

        insertNameBox.add(Box.createVerticalStrut(50));
        insertNameBox.add(bottomPanel);

        this.add(insertNameBox);
        SwingUtilities.invokeLater(() -> nameField.requestFocusInWindow());
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        BasicUI.drawGame(g2, playManager.getLevel(), playManager.getScore(), playManager.getLines(), this);
    }

    private void submitName() throws SQLException {
        String name = nameField.getText().trim();
        if (!name.isEmpty()) {
            playManager.getSm().getGs().setPlayerName(name.trim());
            SQLInOut.addScore(playManager.getSm().getGs());
        }
        mainMenu.showMainMenu();
    }
}

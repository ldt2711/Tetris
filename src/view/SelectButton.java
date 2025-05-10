package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

public class SelectButton extends JButton {
    private boolean hover;
    private ImageIcon originalIcon;
    private ImageIcon hoverIcon;

    public SelectButton(String text) {
        super(text);
        init();
    }

    public SelectButton(ImageIcon icon) {
        super(icon);
        this.originalIcon = icon;
        this.hoverIcon = colorizeIcon(icon, Color.BLACK);
        setIcon(originalIcon);
        init();
    }

    private void init() {
        setFocusPainted(false);
        setContentAreaFilled(false);
        setForeground(Color.white);
        setFont(new Font("Monospaced", Font.BOLD, 20));
        setBorder(BorderFactory.createLineBorder(Color.white, 2));
        setOpaque(false);
        setHorizontalAlignment(SwingConstants.CENTER);
        setVerticalAlignment(SwingConstants.CENTER);
        setCursor(new Cursor(Cursor.HAND_CURSOR)); // hand cursor khi hover

        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                hover = true;
                repaint();
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                hover = false;
                repaint();
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                hover = true;
                if (hoverIcon != null) setIcon(hoverIcon);
                repaint();
            }

            @Override
            public void mouseExited(MouseEvent e) {
                hover = false;
                if (originalIcon != null) setIcon(originalIcon);
                repaint();
            }
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();

        if (getModel().isPressed() || hover) {
            g2.setColor(new Color(192, 192, 192));
            g2.fillRect(0, 0, getWidth(), getHeight());
            setForeground(Color.black);
        } else {
            g2.setColor(Color.black);
            g2.fillRect(0, 0, getWidth(), getHeight());
            setForeground(Color.white);
        }

        super.paintComponent(g);
        g2.dispose();
    }

    @Override
    protected void paintBorder(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setColor(Color.white);
        g2.setStroke(new BasicStroke(4f));
        g2.drawRect(0, 0, getWidth() - 1, getHeight() - 1);
        g2.dispose();
    }

    private ImageIcon colorizeIcon(ImageIcon icon, Color color) {
        Image img = icon.getImage();
        int w = icon.getIconWidth();
        int h = icon.getIconHeight();
        BufferedImage coloredImg = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = coloredImg.createGraphics();
        g2.drawImage(img, 0, 0, null);
        g2.setComposite(AlphaComposite.SrcAtop);
        g2.setColor(color);
        g2.fillRect(0, 0, w, h);
        g2.dispose();
        return new ImageIcon(coloredImg);
    }
}

package mino;

import java.awt.*;

public interface IMino {
    public void setXY(int x, int y);
    public void create(Color c);
    public void update();
    public void draw(Graphics2D g2);
    public void getDirection1();
    public void getDirection2();
    public void getDirection3();
    public void getDirection4();
}

package view.effect;

import java.awt.*;

public class DustParticle {
    double x, y;
    double vx, vy; // velocity of dust particles
    int life = 30; // lifespan

    public DustParticle(int x, int y, double vx, double vy) {
        this.x = x;
        this.y = y;
        this.vx = vx;
        this.vy = vy;
    }

    public void update() {
        x += vx;
        y += vy;
        vy += 0.2; // trọng lực kéo xuống
        life--;
    }

    public boolean isDead() {
        return life <= 0;
    }

    public void render(Graphics2D g) {
        g.setColor(new Color(200, 200, 200, life * 8)); // mờ dần
        g.fillOval((int)x, (int)y, 4, 4);
    }
}


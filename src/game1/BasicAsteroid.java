package game1;

import utilities.Vector2D;

import java.awt.*;

import static game1.Constants.*;
import static java.lang.Math.random;

public class BasicAsteroid {
    public static final int RADIUS = 10;
    public static final double MAX_SPEED = 100;

    // variables altered to work with Vector2D

    private Vector2D p; // pos
    private Vector2D v; // velocity

    public BasicAsteroid(Vector2D p, Vector2D v) {
        this.p = p; // utilising Vector2D values
        this.v = v;
    }

    public static BasicAsteroid makeRandomAsteroid() {
        return new BasicAsteroid(new Vector2D(random() * FRAME_HEIGHT, random() * FRAME_WIDTH),
                new Vector2D(2 * (random() - 0.5) * MAX_SPEED, 2 * (random() - 0.5) * MAX_SPEED));
    }

    public void update() {
        p.x += v.x * DT;
        p.y += v.y * DT;
        p.x = (p.x + FRAME_WIDTH) % FRAME_WIDTH;
        p.y = (p.y + FRAME_HEIGHT) % FRAME_HEIGHT;
    }   // altered so updates with use of Vector2D

    public void draw(Graphics2D g) {
        g.setColor(Color.red);
        g.fillOval((int) p.x - RADIUS, (int) p.y - RADIUS, 2 * RADIUS, 2 * RADIUS);
    }
}

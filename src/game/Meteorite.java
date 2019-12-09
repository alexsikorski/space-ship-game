package game;

import utilities.SoundManager;
import utilities.Vector2D;

import java.awt.*;

import static java.lang.Math.random;

public class Meteorite extends GameObject {
    public static final int RADIUS = 7;
    private static final double MAX_SPEED = 110;
    private static final Color COLOR = Color.LIGHT_GRAY;
    private Vector2D direction;


    private Meteorite(Vector2D p, Vector2D v, int r) {
        super(p, v, r);
        this.direction = new Vector2D(1, 0);
        isAsteroid = true;
    }

    static Meteorite makeRandomMeteorite() {
        Meteorite a = new Meteorite(
                new Vector2D(random() * Constants.GAME_FRAME_WIDTH, random() * Constants.GAME_FRAME_HEIGHT), // as 200 is added to make frame height larger
                new Vector2D((1.5 * (random() - 0.5)) * MAX_SPEED, (1.5 * (random() - 0.5)) * MAX_SPEED), 7);
        return a;
    }

    public void hit() {
        SoundManager.hitMeteorite();
        dead = false;
    }

    public void draw(Graphics2D g) {
        g.setColor(COLOR);
        g.fillOval((int) position.x - RADIUS, (int) position.y - RADIUS, 2 * RADIUS, 2 * RADIUS);
    }

    public boolean overlap(GameObject other) {
        if (this instanceof Meteorite && other instanceof Garbage) {
            return false;
        }
        if (this instanceof Meteorite && other instanceof Asteroid) {
            return false;
        }
        if (this.position.x + this.radius + other.radius > other.position.x &&
                this.position.x < other.position.x + this.radius + other.radius &&
                this.position.y + this.radius + other.radius > other.position.y &&
                this.position.y < other.position.y + this.radius + other.radius) {
            return true;
        }

        return false;
    }
}

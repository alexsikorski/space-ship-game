package game;

import utilities.SoundManager;
import utilities.Vector2D;

import java.awt.*;

import static java.lang.Math.random;

public class Garbage extends GameObject {
    public static final int RADIUS = 8;
    private static final double MAX_SPEED = 20;
    private static final Color COLOR = Color.CYAN;
    public static int garbageC = 5; // number of garbage
    public boolean rewardScore = false;
    private Vector2D direction;

    private Garbage(Vector2D p, Vector2D v, int r) {
        super(p, v, r);
        this.direction = new Vector2D(1, 0);
        isPickup = true;
    }

    static Garbage makeRandomDebris() {
        Garbage a = new Garbage(
                new Vector2D(random() * Constants.GAME_FRAME_WIDTH, random() * Constants.GAME_FRAME_HEIGHT), // as 200 is added to make frame height larger
                new Vector2D((1.5 * (random() - 0.5)) * MAX_SPEED, (1.5 * (random() - 0.5)) * MAX_SPEED), 8);
        return a;
    }

    public static int getGarbageCount() {   // returns garbage count
        return garbageC;
    }

    public void pickUp() {
        SoundManager.pickUp();
        dead = true;
        rewardScore = true;
        garbageC--;
    }

    public void draw(Graphics2D g) {
        g.setColor(COLOR);
        g.fillOval((int) position.x - RADIUS, (int) position.y - RADIUS, 2 * RADIUS, 2 * RADIUS);

    }

    public boolean overlap(GameObject other) {
        if (this.position.x + this.radius + other.radius > other.position.x &&
                this.position.x < other.position.x + this.radius + other.radius &&
                this.position.y + this.radius + other.radius > other.position.y &&
                this.position.y < other.position.y + this.radius + other.radius) {
            return false;
        }

        return false;
    }
}
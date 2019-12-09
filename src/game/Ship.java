package game;

import utilities.SoundManager;
import utilities.Vector2D;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.util.Timer;
import java.util.TimerTask;

import static game1.Constants.DT;

public class Ship extends GameObject {
    private static final double STEER_RATE = 2 * (180 / Math.PI) * DT;     // acceleration when thrust is applied
    private static final double MAG_ACC = 150;     // constant speed loss factor
    private static final double DRAG = 0.03;
    private static final Color COLOR = Color.white;


    private static final double DRAWING_SCALE = 5;
    private static final int[] XP = {-2, 0, 2, 0};
    private static final int[] YP = {2, -2, 2, 0};
    private static final int[] XP_THRUST = {-1, 0, 1, 0};
    private static final int[] YP_THRUST = {2, 5, 2, 0};
    static boolean protection = true; // bool for protection against collisions
    public int livesLeft = 3; // lives

    // direction value
    private Vector2D direction;

    // controller
    private Controller ctrl; // controller which provides an Action object in each frame


    Ship(Vector2D p, Vector2D v, int r, Controller ctrl) {
        super(p, v, r);
        this.ctrl = ctrl;
        this.direction = new Vector2D(1, 0);
        isShip = true;
    }

    public static void protectionOff() {
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                protection = false;
                timer.cancel();
            }
        }, 1500, 1500);
    }

    public void hit() {
        if (livesLeft > 0) {
            livesLeft--;
            protection = true;
            protectionOff();
            //position = new Vector2D(50,220);  removed as dont want to reset ship position when hit by an asteroid
            //velocity = new Vector2D();
        }
        if (livesLeft == 0) {
            {
                dead = true;
                return;
            }
        }
    }

    public int getLivesLeft() {
        return livesLeft;
    }

    public void update() {
        super.update();
        //System.out.println(toString()); // testing position for ship
        direction.rotate((STEER_RATE * ctrl.action().turn) * DT);
        velocity.addScaled(direction, (MAG_ACC * DT) * ctrl.action().thrust);
        velocity.mult(1 - DRAG);

        // sounds
        if (ctrl.action().thrust == 1) {
            //System.out.println("Thrust ON");
            SoundManager.startThrust();
        } else {
            //ystem.out.println("Thrust OFF");
            SoundManager.stopThrust();
        }
    }

    public void draw(Graphics2D g) {
        AffineTransform at = g.getTransform();
        g.translate(position.x, position.y);
        double rot = direction.angle() + Math.PI / 2;
        g.rotate(rot);
        g.scale(DRAWING_SCALE, DRAWING_SCALE);
        g.setColor(COLOR);
        g.fillPolygon(XP, YP, XP.length);

        if (protection == true) {    // when protection is active ship turns red
            g.setColor(Color.green);
            g.fillPolygon(XP, YP, XP.length);
        }

        if (ctrl.action().thrust == 1) {
            g.setColor(Color.red);
            g.fillPolygon(XP_THRUST, YP_THRUST, XP_THRUST.length);
        }
        g.setTransform(at);
    }

    public boolean overlap(GameObject other) {

        if (protection == true) {   // if protection is on, collision between hit Asteroids/Meteorites and ship are ignored
            if (this instanceof Ship && other instanceof Asteroid) {
                return false;
            }
            if (this instanceof Ship && other instanceof Meteorite) {
                return false;
            }
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

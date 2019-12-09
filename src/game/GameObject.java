package game;

import utilities.Vector2D;

import java.awt.*;

public abstract class GameObject {
    public Vector2D position;
    public Vector2D velocity;
    public double radius;
    public boolean dead;
    public boolean isPickup;
    public boolean isShip;
    public boolean isAsteroid;

    GameObject(Vector2D p, Vector2D v, int r) {
        this.position = p;
        this.velocity = v;
        this.radius = r;
    }

    public String toString() {
        return this.getClass().getSimpleName() + " (" + position.x + " , " + position.y + ") "; // prints out class name and position of that object
    }

    public void hit() {

    }

    public void pickUp() {

    }

    public void update() {
        position.addScaled(velocity, Constants.DT);
        position.wrap(Constants.GAME_FRAME_WIDTH, Constants.GAME_FRAME_HEIGHT);
    }

    public boolean overlap(GameObject other) {
        if (this.position.x + this.radius + other.radius > other.position.x &&
                this.position.x < other.position.x + this.radius + other.radius &&
                this.position.y + this.radius + other.radius > other.position.y &&
                this.position.y < other.position.y + this.radius + other.radius) {
            return true;
        }
        return false;
    }

    public void collisionHandling(GameObject other) {
        if (this.getClass() != other.getClass() && this.overlap(other)) {
            if (this.isShip && other.isAsteroid) {    // if ship and asteroid collide
                System.out.println("    COLLISION: " + this.getClass() + " and " + other.getClass());    // testing collision
                this.hit();
                other.hit();
            } else if (this.isShip && other.isPickup) { // if ship and pickup collide
                System.out.println("    COLLISION: " + this.getClass() + " and " + other.getClass()); // testing else collision
                this.pickUp();
                other.pickUp();
            }
            //this.pickUp();
            //other.pickUp();
        }
    }

    public abstract void draw(Graphics2D g);
}

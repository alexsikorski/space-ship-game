package game;

import utilities.FileWriter;
import utilities.JEasyFrame;
import utilities.Vector2D;

import java.util.ArrayList;
import java.util.List;

import static game1.Constants.DELAY;

public class Game {
    //public static final int N_INITIAL_ASTEROIDS = 7;
    //public static List<Asteroid> hitAsteroids;

    static Keys ctrl;
    int score = 0;
    int currentScore = 0;
    //int scoreForLifeUp = 500;

    List<GameObject> objects; // list for managing game objects
    List<Asteroid> asteroids; // list for hitAsteroids
    List<Garbage> garbage; // list for garbage

    int asteroidCount = 5; // used to declare how many hitAsteroids to draw
    int meteoriteCount = 3;
    Ship ship;

    int level = 1;


    boolean ranOnce = false; // for file write (want final score to be written once when game updates)
    boolean gameOverState = false;


    Game() {


        objects = new ArrayList<>();
        asteroids = new ArrayList<>();
        garbage = new ArrayList<>();


        //for (int i = 0; i < N_INITIAL_ASTEROIDS; i++) {
        //    hitAsteroids.add(Asteroid.makeRandomAsteroid());
        //}
        ctrl = new Keys();
        ship.protection = true;
        ship.protectionOff();
        ship = new Ship(new Vector2D(50, 220), new Vector2D(), 5, ctrl); //add ship
        objects.add(ship); // adds ship to object array so that it appears when all elements are drawn out.
        startAsteroid(); // adds hitAsteroids to playfield
        startGarbage(); // adds garbage to playfield
    }

    public static void main(String[] args) throws Exception {
        Game game = new Game();
        View view = new View(game);
        new JEasyFrame(view, "CE218 Resit Assignment: Space Junk by 1603930").addKeyListener(game.ctrl);

        while (true) {
            game.update();
            view.repaint();
            Thread.sleep(DELAY);
        }
    }

    public void addScore(int addedS) { // adds score
        score = score + addedS;
        currentScore = currentScore + addedS;
    }

    public int getScore() { // returns total score
        return score;
    }

    public int getCurrentScore() {   // returns level score
        return currentScore;
    }

    public int getAsteroidCount() {  // returns asteroid count
        return asteroidCount;
    }


    public void update() {
        startNewLevel(); // checks if garbage is at 0 and starts next level with more hitAsteroids


        for (GameObject a : objects) { // COLLISIONS
            a.update();
            for (GameObject aa : objects) {
                if (a == aa) {
                    continue;
                }
                a.collisionHandling(aa);
            }
        }
        // alive objects
        List<GameObject> Alive = new ArrayList<>();
        Alive.addAll(objects); // adds all objects to alive list initially

        for (GameObject a : objects) {
            a.update(); // update game
            if (a.dead) {
                Alive.remove(a); // remove dead object from alive list
            }
            if (a instanceof Garbage) {
                if (((Garbage) a).rewardScore) { // if in instance, if space debris rewards score from death
                    addScore(100);   // add score
                    if (currentScore == 500) { // if level score reaches 500
                        ship.livesLeft += 1;    // add a life
                        currentScore = 0;   // then reset current score as once 5 garbage objects are collected the next level begins
                    }
                    System.out.println("+10 SCORE ADDED! Total:" + score); // testing for score
                }
            }
        }
        synchronized (Game.class) {
            objects.clear();    // clears object array
            objects.addAll(Alive); // adds alive objects to objects list

        }
        synchronized (Game.class) {
            asteroids.clear();
            for (GameObject a : Alive) {
                if (a instanceof Asteroid) {
                    asteroids.add((Asteroid) a);
                }
            }
        }
        if (ship.livesLeft == 0 && !ranOnce) {   // if game over and not ran once write final score then make ranOnce true
            // file write
            FileWriter fW = new FileWriter();
            fW.WriteFinalScore(score);
            objects.clear();    // clears objects at end of life as to reset for next game
            ranOnce = true;
            gameOverState = true;
        }
        if (ctrl.action.restart && gameOverState) {   // if restart button is pressed
            gameOverState = false;
            ranOnce = false; // so that file writer runs once more
            restartGame();
        }
    }

    private void startAsteroid() {
        for (int i = 0; i < asteroidCount; i++) {
            objects.add(Asteroid.makeRandomAsteroid());
        }
    }

    private void startGarbage() {
        for (int i = 0; i < Garbage.garbageC; i++) {
            objects.add(Garbage.makeRandomDebris());
        }
    }

    private void startNewLevel() {
        if (Garbage.garbageC == 0) {
            level++; // adds a level each time startNewLevel method is called
            if (level >= 2) {    // start spawning meteorites +1 every level
                objects.add(Meteorite.makeRandomMeteorite());
            }
            Garbage.garbageC = 5; // sets garbage back to 5

            asteroidCount++; // if garbage is at zero, add to total hitAsteroids for next level
            System.out.println(" NEW LEVEL:" + level + ", asteroidCount at: " + asteroidCount);

            Ship.protection = true; // protection is on when more hitAsteroids spawn
            Ship.protectionOff();   // turn protection off after 3 seconds

            objects.add(Asteroid.makeRandomAsteroid());

            startGarbage();
        }
    }

    private void restartGame() {
        System.out.println("RESTART TRUE");
        currentScore = 0;
        score = 0;
        ship.livesLeft = 3;
        Garbage.garbageC = 5;
        asteroidCount = 5; // these values are set to level 1 values
        level = 1;


        Ship.protection = true; // protection is on when more hitAsteroids spawn
        Ship.protectionOff();   // turn protection off after 3 seconds


        ship = new Ship(new Vector2D(50, 220), new Vector2D(), 5, ctrl); //add ship
        objects.add(ship);

        startGarbage();
        startAsteroid();
    }
}

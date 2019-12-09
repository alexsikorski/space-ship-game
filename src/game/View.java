package game;

import utilities.FileReader;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;

public class View extends JComponent {
    // background colour
    public static final Color BG_COLOR = Color.black;
    // bar color
    public static final Color BAR_COLOR = Color.green;
    // font
    Font font = new Font("Consolas", Font.PLAIN, 18);
    Font fontBold = new Font("Consolas", Font.BOLD, 18);
    Font fontLevel = new Font("Consolas", Font.BOLD, 24);
    Font fontGO = new Font("Consolas", Font.BOLD, 36);
    Game game;


    public View(Game game) {
        this.game = game;
    }


    public void paintComponent(Graphics g0) {
        Graphics2D g = (Graphics2D) g0;


        // paint the background
        g.setColor(BG_COLOR);
        g.fillRect(0, 0, getWidth(), getHeight());
        // added rect so that line is drawn between playing field and empty space
        g.setColor(BAR_COLOR);
        g.fillRect(0, 490, 653, 3);
        g.fillRect(650, 0, 3, 490);
        g.setColor(Color.WHITE);
        g.setFont(font);


        FileReader returnList = new FileReader(); // obj for reading txt file
        ArrayList<Integer> freshList = returnList.OpenAndRead();
        Collections.sort(freshList, Collections.reverseOrder());    // sorts descending appropriate to leaderboard

        // draws whats written on file
        int textSpaceY = 50;
        for (int i = 0; i < 20; i++) {
            textSpaceY = textSpaceY + 20;   // spaces out the text
            g.drawString((i + 1) + " PLACE: " + freshList.get(i), 670, textSpaceY);
        }


        synchronized (Game.class) { // THREAD SAFETY
            for (GameObject a : game.objects) {
                a.draw(g);
            }
        }

        g.setColor(Color.DARK_GRAY);
        g.drawString("FINISHED BY 1603930 ON 28/07/2019", 170, 640);
        g.setColor(Color.WHITE);
        g.drawString("TOTAL SCORE: " + Integer.toString(game.getScore()), 50, 550);
        g.drawString("LEVEL SCORE: " + Integer.toString(game.getCurrentScore()), 50, 580);
        g.drawString("LIVES LEFT: " + Integer.toString(game.ship.getLivesLeft()), 430, 550);
        g.drawString("GARBAGE LEFT: " + Integer.toString(Garbage.getGarbageCount()), 430, 580);
        g.drawString("ASTEROIDS: " + Integer.toString(game.getAsteroidCount()), 253, 580);
        g.setFont(fontLevel);
        g.setColor(Color.red);
        g.drawString("LEVEL: " + Integer.toString(game.level), 260, 550);
        g.drawString("HIGH SCORES:", 670, 40);

        if (game.ship.getLivesLeft() == 0) {
            g.setFont(fontGO);
            g.setColor(Color.red);
            g.drawString("GAME OVER", 220, 250);
            g.setFont(font);
            g.setColor(Color.WHITE);
            g.drawString("YOUR SCORE: " + Integer.toString(game.getScore()), 235, 280);
            g.setFont(fontBold);
            g.drawString("PRESS SPACE BAR TO TRY AGAIN!", 170, 310);
        }
    }

    @Override
    public Dimension getPreferredSize() {
        return Constants.FRAME_SIZE;
    }
}

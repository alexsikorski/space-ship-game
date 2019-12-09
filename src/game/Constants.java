package game;

import java.awt.*;

public class Constants {
    public static final int FRAME_HEIGHT = 680;
    public static final int GAME_FRAME_HEIGHT = 480;
    public static final int GAME_FRAME_WIDTH = 640;
    public static final int FRAME_WIDTH = 840;

    public static final Dimension FRAME_SIZE = new Dimension(
            Constants.FRAME_WIDTH, Constants.FRAME_HEIGHT);

    // delay added, sleep time between two frames
    public static final int DELAY = 20; // in milliseconds
    public static final double DT = DELAY / 1000.0; // in seconds
}

package utilities;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.io.File;


public class SoundManager {

    final static String path = "src/sound/";
    public final static Clip thrustClip = getClip("thrust");
    public final static Clip bangLarge = getClip("bangLarge");
    public final static Clip bangSmall = getClip("bangSmall");
    public final static Clip beat2 = getClip("beat2");
    public static Clip[] clips = {thrustClip};
    private static boolean thrusting = false;

    public static void play(Clip clip) {
        clip.setFramePosition(0);
        clip.start();
    }

    private static Clip getClip(String filename) { // method reads and obtains clip
        Clip clip = null;
        try {
            clip = AudioSystem.getClip();
            AudioInputStream sample = AudioSystem.getAudioInputStream(new File(path
                    + filename + ".wav"));
            clip.open(sample);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return clip;
    }

    public static void startThrust() {
        if (!thrusting) {
            thrustClip.loop(Clip.LOOP_CONTINUOUSLY); // continuous loop of soundclip
            thrusting = true;
        }
    }

    public static void stopThrust() {
        thrustClip.stop(); // stops
        thrusting = false;
    }

    public static void hitAsteroids() {
        play(bangLarge);
    }

    public static void hitMeteorite() {
        play(bangSmall);
    }

    public static void pickUp() {
        play(beat2);
    }
}

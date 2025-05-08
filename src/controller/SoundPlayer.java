package controller;

import javax.sound.sampled.*;
import java.io.IOException;
import java.io.InputStream;

public class SoundPlayer {
    private static Clip backgroundClip;

    public static void playSound(String soundPath) {
        try {
            InputStream audioSrc = SoundPlayer.class.getResourceAsStream("/resource/sound/" + soundPath);
            if (audioSrc == null) {
                System.err.println("File not found: " + soundPath);
                return;
            }

            AudioInputStream audioStream = AudioSystem.getAudioInputStream(audioSrc);
            Clip clip = AudioSystem.getClip();
            clip.open(audioStream);
            clip.start();
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }
}

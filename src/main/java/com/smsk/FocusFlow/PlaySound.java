package com.smsk.FocusFlow;

import javax.sound.sampled.*;
import java.io.BufferedInputStream;

public class PlaySound {
    public static void play(String source) {
        try {
            AudioInputStream stream;
            AudioFormat format;
            DataLine.Info info;
            Clip clip;
            var sound = Main.class.getResourceAsStream("/sounds/" + source);
            assert sound != null;
            stream = AudioSystem.getAudioInputStream(new BufferedInputStream(sound));
            format = stream.getFormat();
            info = new DataLine.Info(Clip.class, format);
            clip = (Clip) AudioSystem.getLine(info);
            clip.open(stream);
            clip.start();
        } catch (Exception e) {
            Utils.log("ERROR!" + e.getMessage());
        }
    }
}

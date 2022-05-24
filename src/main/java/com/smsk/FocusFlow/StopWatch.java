package com.smsk.FocusFlow;
import java.util.Timer;
import java.util.TimerTask;

public class StopWatch {
    public static void start(TimerTask task) {
        new Timer().scheduleAtFixedRate(task, 1000, 1000);
    }
}

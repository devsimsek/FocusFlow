package com.smsk.FocusFlow;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.Objects;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

public class FocusFlow {
    private final Window window = new Window();

    private static boolean sessionRunning = true;

    private static boolean isBreak = false;

    private static int sessionCount = 0;

    private static final int breakLength = 5;

    private static final int sessionLength = 15;

    FocusFlow(boolean debug) {
        Utils.debug = debug;
    }

    private void buildUI() {
        var grid = new JPanel();
        // Configure
        grid.setBackground(Color.decode("#264653"));
        grid.setLayout(new BoxLayout(grid, BoxLayout.Y_AXIS));

        // Labels
        JLabel appLabel = new JLabel("FocusFlow");
        appLabel.setForeground(Color.decode("#CBF3F0"));
        appLabel.setFont(new Font(appLabel.getFont().getName(), appLabel.getFont().getStyle(), 15));
        appLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        appLabel.setBorder(new EmptyBorder(20, 10, 0, 0));

        JLabel sessionCountLabel = new JLabel("Session Count");
        sessionCountLabel.setForeground(Color.white);
        sessionCountLabel.setFont(new Font(sessionCountLabel.getFont().getName(), sessionCountLabel.getFont().getStyle(), 12));
        sessionCountLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel stopWatch = new JLabel("00:00");
        stopWatch.setForeground(Color.white);
        stopWatch.setFont(new Font(stopWatch.getFont().getName(), Font.BOLD, 45));
        stopWatch.setAlignmentX(Component.CENTER_ALIGNMENT);

        JButton stopButton = new JButton("Stop");
        stopButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        stopButton.setBorderPainted(false);
        stopButton.setFocusPainted(false);
        stopButton.setContentAreaFilled(false);
        stopButton.setForeground(Color.decode("#CBF3F0"));

        stopButton.addActionListener(e -> {
            if (sessionRunning) {
                sessionRunning = false;
                stopButton.setText("Start");
            } else {
                stopButton.setText("Stop");
                sessionRunning = true;
            }
        });

        grid.add(appLabel);
        grid.add(Box.createVerticalGlue());
        grid.add(stopWatch);
        grid.add(Box.createVerticalGlue());
        grid.add(sessionCountLabel);
        grid.add(Box.createVerticalGlue());
        grid.add(stopButton);

        // Add grid to main panel
        window.add(grid);
        startStopwatch(appLabel, stopWatch, sessionCountLabel, stopButton);
    }

    private void startStopwatch(JLabel status, JLabel timer, JLabel count, JButton button) {
        int delay = 1000;
        int period = 1000;
        int[] interval = new int[1];
        interval[0] = sessionLength * 60;
        StopWatch.start(new TimerTask() {
            @Override
            public void run() {
                if (sessionRunning) {
                    if (interval[0] == 1) {
                        if (!isBreak) {
                            sessionCount++;
                            isBreak = true;
                            interval[0] = breakLength * 60;
                            status.setText("FocusFlow | Break");
                            sessionRunning = false;
                            button.setText("Start");
                            // todo Send notification here
                        } else {
                            interval[0] = sessionLength * 60;
                            status.setText("FocusFlow | Session");
                            sessionRunning = false;
                            button.setText("Start");
                        }
                    }
                    count.setText(sessionCount + " session done.");
                    --interval[0];
                    // todo find a better way to handle this.
                    timer.setText((TimeUnit.SECONDS.toMinutes(interval[0]) < 10 ? "0" + TimeUnit.SECONDS.toMinutes(interval[0]) : TimeUnit.SECONDS.toMinutes(interval[0])) + ":" + ((TimeUnit.SECONDS.toSeconds(interval[0]) - TimeUnit.SECONDS.toMinutes(interval[0]) * 60) < 10 ? "0" + (TimeUnit.SECONDS.toSeconds(interval[0]) - TimeUnit.SECONDS.toMinutes(interval[0]) * 60) : (TimeUnit.SECONDS.toSeconds(interval[0]) - TimeUnit.SECONDS.toMinutes(interval[0]) * 60)));
                    window.repaint();
                }
            }
        });
    }

    public void run() {
        Utils.log("Copyright (C)devsimsek FocusFlow and the authors.");
        Utils.log("https://github.com/devsimsek/FocusFlow");
        Utils.log("Preparing for window build...", "CORE");
        try {
            Benchmark.start(1);
            Utils.log("Starting to build window.", "WINDOW");

            // Configure
            window.setTitle("FocusFlow");
            window.setIcon("AppIcon.png");
            window.setBackground(Color.decode("#264653"));
            window.setSize();
            window.setSize(new Dimension(300, 140));
            window.setUndecorated(true);
            window.roundedCorners();
            window.alwaysOnTop(true);
            window.setResizeable(false);

            // Removes border around the main frame. IDK what causes it but this line fixes.
            window.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));

            // UI components
            buildUI();

            // Build!
            window.build();
            Benchmark.stop(1);
            Utils.log("Window successfully built. Took " + Benchmark.result(1) + " seconds.", "WINDOW");
        } catch (Error e) {
            Utils.log(e.getMessage(), "ERROR!");
        }

    }
}
package com.smsk.FocusFlow;

import javax.swing.*;
import java.awt.*;
import java.util.TimerTask;
import java.util.Timer;
import java.util.concurrent.TimeUnit;

public class FocusFlow {

    private static boolean sessionRunning = true;

    private static boolean isBreak = false;

    private static int sessionCount = 0;

    private static int breakLength = 5;

    private static int sessionLength = 15;

    public static void run() {
        JLabel label = new JLabel("FocusFlow");
        label.setForeground(Color.white);
        label.setFont(new Font(label.getFont().getName(), label.getFont().getStyle(), 15));
        label.setHorizontalAlignment(JLabel.CENTER);

        JLabel analytics = new JLabel("Session Count");
        analytics.setForeground(Color.white);
        analytics.setFont(new Font(label.getFont().getName(), label.getFont().getStyle(), 15));
        analytics.setHorizontalAlignment(JLabel.CENTER);

        JLabel time = new JLabel("00:00");
        time.setForeground(Color.white);
        time.setFont(new Font(time.getFont().getName(), Font.BOLD, 45));
        time.setHorizontalAlignment(JLabel.CENTER);

        JButton button = new JButton("Stop");

        button.addActionListener(e -> {
            if (sessionRunning) {
                sessionRunning = false;
                button.setText("Start");
            } else {
                button.setText("Stop");
                sessionRunning = true;
            }
        });

        Window wind = new Window("FocusFlow", true);
        wind.setBackground(Color.darkGray);
        wind.setSize(300, 120);

        var grid = new JPanel();
        grid.setLayout(new GridLayout(2, 2));
        wind.frame.getContentPane().setLayout(new GridBagLayout());
        grid.setBackground(Color.darkGray);
        grid.add(label, BorderLayout.CENTER);
        grid.add(time, BorderLayout.CENTER);
        grid.add(analytics, BorderLayout.CENTER);
        grid.add(button, BorderLayout.CENTER);
        wind.add(grid);
        wind.build();
        wind.frame.repaint();

        label.setText("FocusFlow | Session");

        final int[] interval = new int[1];
        Timer timer;
        int delay = 1000;
        int period = 1000;
        timer = new Timer();
        interval[0] = sessionLength * 60;
        timer.scheduleAtFixedRate(new TimerTask() {
            public void run() {
                if (sessionRunning) {
                    if (interval[0] == 1) {
                        if (!isBreak) {
                            sessionCount++;
                            isBreak = true;
                            interval[0] = breakLength * 60;
                            label.setText("FocusFlow | Break");
                            sessionRunning = false;
                            button.setText("Start");
                            JOptionPane.showMessageDialog(wind.frame,
                                    "Have a quick break before continue.",
                                    "Break Time!",
                                    JOptionPane.PLAIN_MESSAGE);
                        } else {
                            interval[0] = sessionLength * 60;
                            label.setText("FocusFlow | Session");
                            sessionRunning = false;
                            button.setText("Start");
                        }
                    }
                    analytics.setText("Session Count: " + sessionCount);
                    --interval[0];
                    time.setText(TimeUnit.SECONDS.toMinutes(interval[0]) + ":" + (TimeUnit.SECONDS.toSeconds(interval[0]) - TimeUnit.SECONDS.toMinutes(interval[0]) * 60));
                    wind.frame.repaint();
                }
            }
        }, delay, period);
    }
}
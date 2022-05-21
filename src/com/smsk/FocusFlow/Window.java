package com.smsk.FocusFlow;

import javax.swing.*;
import java.awt.*;

public class Window {
    public JPanel panel = new JPanel();
    public JFrame frame = new JFrame();
    private final String windowTitle;
    protected boolean isVisible;

    Window(String windowTitle, boolean isVisible) {
        System.out.println("Copyright (C)devsimsek, FocusFlow.");
        System.out.println("https://github.com/devsimsek/FocusFlow");
        this.windowTitle = windowTitle;
        this.isVisible = isVisible;
    }

    public void add(Component comp) {
        panel.add(comp);
    }

    public void setBackground(Color color) {
        panel.setBackground(color);
    }

    public void setSize(int x, int y) {
        panel.setPreferredSize(new Dimension(x, y));
        panel.setMaximumSize(panel.getPreferredSize());
        panel.setMinimumSize(panel.getPreferredSize());
    }

    public void build() {
        System.out.println("Ready to build window");
        frame.setTitle(windowTitle);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(panel);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(isVisible);
        frame.setResizable(false);
        frame.toFront();
        frame.setAlwaysOnTop(true);
        System.out.println("Build Complete");
    }
}

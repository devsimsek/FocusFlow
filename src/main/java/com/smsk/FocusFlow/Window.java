package com.smsk.FocusFlow;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.RoundRectangle2D;
import java.net.URL;
import java.util.Objects;

/**
 * Copyright (C)devsimsek.
 *
 * @version v1.0.1
 */
public class Window {

    private final JFrame frame = new JFrame();
    private final JPanel panel = new MotionPanel(frame);
    private boolean isVisible = true;
    private boolean isResizeable = true;

    public void setVisible(boolean visible) {
        this.isVisible = visible;
    }

    public void setResizeable(boolean resizeable) {
        this.isResizeable = resizeable;
    }

    public void toFront() {
        frame.toFront();
    }

    public void alwaysOnTop(boolean alwaysOnTop) {
        frame.setAlwaysOnTop(alwaysOnTop);
    }

    public void setBackground(Color color) {
        panel.setBackground(color);
    }

    public void setSize() {
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (dim.width - frame.getSize().width) / 2;
        int y = (dim.height - frame.getSize().height) / 2;
        frame.setPreferredSize(new Dimension(x, y));
        frame.setMaximumSize(panel.getPreferredSize());
        frame.setMinimumSize(panel.getPreferredSize());
    }

    public void setSize(Dimension dim) {
        frame.setPreferredSize(dim);
        frame.setMaximumSize(panel.getPreferredSize());
        frame.setMinimumSize(panel.getPreferredSize());
    }

    public void setTitle(String title) {
        frame.setTitle(title);
    }

    public void add(Component component) {
        panel.add(component);
    }

    public void setExtendedState(int state) {
        frame.setExtendedState(state);
    }

    public void setFullScreen(boolean fullScreen) {
        if (fullScreen) {
            setUndecorated(true);
            setExtendedState(JFrame.MAXIMIZED_BOTH);
        } else {
            setUndecorated(false);
            setExtendedState(JFrame.NORMAL);
        }
    }

    public void setUndecorated(boolean undecorated) {
        frame.setUndecorated(undecorated);
    }

    public int getWidth() {
        return frame.getWidth();
    }

    public int getHeight() {
        return frame.getHeight();
    }

    public void roundedCorners() {
        frame.setShape(new RoundRectangle2D.Double(0, 0, frame.getPreferredSize().width, frame.getPreferredSize().getHeight(), 20, 20));
    }

    public void setLayout(LayoutManager layoutManager) {
        panel.setLayout(layoutManager);
    }

    public void repaint() {
        frame.repaint();
    }

    public void setIcon(String image) {
        try {
            var icon = new ImageIcon(Objects.requireNonNull(Main.class.getResource("/" + image)));
            frame.setIconImage(icon.getImage());
            if (System.getProperty("os.name").startsWith("Mac") || System.getProperty("os.name").startsWith("Darwin")) {
                Taskbar taskbar = Taskbar.getTaskbar();
                try {
                    taskbar.setIconImage(icon.getImage());
                } catch (final UnsupportedOperationException e) {
                    System.out.println("Can't set taskbar icon.");
                } catch (final SecurityException e) {
                    System.out.println("Warning. Can't set taskbar icon due to security exceptions.");
                }
            }
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }

    public void build() {
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setPreferredSize(frame.getPreferredSize().getSize());
        frame.add(panel);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(isVisible);
        frame.setResizable(isResizeable);
    }
}

class MotionPanel extends JPanel {
    private Point initialClick;

    public MotionPanel(final JFrame parent) {

        addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                initialClick = e.getPoint();
                getComponentAt(initialClick);
            }
        });

        addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {

                // get location of Window
                int thisX = parent.getLocation().x;
                int thisY = parent.getLocation().y;

                // Determine how much the mouse moved since the initial click
                int xMoved = e.getX() - initialClick.x;
                int yMoved = e.getY() - initialClick.y;

                // Move window to this position
                int X = thisX + xMoved;
                int Y = thisY + yMoved;
                parent.setLocation(X, Y);
            }
        });
    }
}
package com.smsk.FocusFlow;

import jdk.jshell.execution.Util;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.Objects;

/**
 * todo add notification builders. use frame.setOpacity();
 */
public class Notification {

    /**
     * Thanks to (<a href="https://stackoverflow.com/a/59167775/14819211">vgr</a>)
     */
    public void sendSystemNotification(String title, String message, String icon) {
        try {
            Image image = new ImageIcon(Objects.requireNonNull(Main.class.getResource("/" + icon))).getImage();
            String os = System.getProperty("os.name");
            if (os.contains("Linux")) {
                ProcessBuilder builder = new ProcessBuilder("zenity", "--notification", "--title=" + title, "--text=" + message);
                try {
                    builder.inheritIO().start();
                } catch (IOException e) {
                    Utils.log(e.getMessage(), "ERROR!");
                }
            } else if (os.contains("Mac")) {
                ProcessBuilder builder = new ProcessBuilder("osascript", "-e", "display notification \"" + message + "\"" + " with title \"" + title + "\" sound name \"submarine\"");
                try {
                    builder.inheritIO().start();
                } catch (IOException e) {
                    Utils.log(e.getMessage(), "ERROR!");
                }
            } else if (SystemTray.isSupported()) {
                SystemTray tray = SystemTray.getSystemTray();

                TrayIcon trayIcon = new TrayIcon(image, "FocusFlow");
                trayIcon.setImageAutoSize(true);
                tray.add(trayIcon);
                trayIcon.displayMessage(title, message, TrayIcon.MessageType.INFO);
            }
        } catch (NullPointerException e) {
            Utils.log(e.getMessage(), "ERROR!");
        } catch (AWTException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * @param title   the title that will be prompted
     * @param message the message that will be prompted
     */
    public static void sendNotification(String title, String message) {
        var container = new JFrame();
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (dim.width - container.getSize().width) / 2;
        int y = (dim.height - container.getSize().height) / 2;
        container.setPreferredSize(new Dimension(x, y));
        container.setAlwaysOnTop(true);
        container.setVisible(false);
        container.toFront();
        container.requestFocus();
        container.pack();
        container.setFocusable(true);
        container.getContentPane().setPreferredSize(container.getPreferredSize().getSize());
        int response = JOptionPane.showOptionDialog(container, message, title, JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, new Object[]{"Yes"}, JOptionPane.YES_OPTION);
        if (response == JOptionPane.YES_OPTION || response == JOptionPane.CLOSED_OPTION) {
            // start break
            container.dispose();
        }
    }
}

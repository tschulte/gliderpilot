/*
 * Created on Oct 12, 2006
 */
package de.gliderpilot;

import java.awt.*;
import java.awt.TrayIcon.MessageType;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.net.URL;

import javax.swing.UIManager;

/**
 * @author tobias
 * 
 */
public class TrayIconTest {

    /**
     * @param args
     * @throws AWTException
     */
    public static void main(String[] args) throws AWTException {
        if (SystemTray.isSupported()) {
            URL imageURL = TrayIconTest.class.getResource("tmp_16.png");
            Image image = Toolkit.getDefaultToolkit().getImage(imageURL);
            PopupMenu menu = new PopupMenu();
            MenuItem item = new MenuItem("Exit");
            item.addActionListener(new ActionListener() {

                public void actionPerformed(ActionEvent pE) {
                    System.exit(0);
                }

            });
            menu.add(item);
            TrayIcon trayIcon = new TrayIcon(image, "Test", menu);
            SystemTray tray = SystemTray.getSystemTray();
            tray.add(trayIcon);

            trayIcon.displayMessage("Caption", "Text", MessageType.INFO);
        }
    }

    @SuppressWarnings("unchecked")
    private static <V> V cast(Object o) {
        return (V) o;
    }

    private static <T extends Object> T get() {
        return cast(new Integer(0));
    }
}

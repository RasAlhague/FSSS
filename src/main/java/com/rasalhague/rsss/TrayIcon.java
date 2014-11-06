package com.rasalhague.rsss;

import java.awt.*;
import java.awt.event.ActionListener;

public class TrayIcon
{
    private java.awt.TrayIcon trayIcon;

    private TrayIcon() { }

    public static TrayIcon getInstance()
    {
        return TrayIconHolder.instance;
    }

    private static class TrayIconHolder
    {
        static TrayIcon instance = new TrayIcon();
    }

    public void initialize()
    {
        if (SystemTray.isSupported())
        {
            // get the SystemTray instance
            SystemTray tray = SystemTray.getSystemTray();
            // load an image
            Image image = Toolkit.getDefaultToolkit()
                                 .getImage(TrayIcon.class.getResource("/FS.png"));

            // create a action listener to listen for default action executed on the tray icon
            ActionListener exitListener = e -> {

                System.exit(0);
            };

            // create a popup menu
            PopupMenu popup = new PopupMenu();

            // create menu item for the default action
            MenuItem exitMenuItem = new MenuItem("Exit");
            exitMenuItem.addActionListener(exitListener);
            popup.add(exitMenuItem);

            // construct a TrayIcon
            trayIcon = new java.awt.TrayIcon(image, "FSSS", popup);
            trayIcon.setImageAutoSize(true);

            // set the TrayIcon properties
//            trayIcon.addActionListener(listener);

            // add the tray image
            try
            {
                tray.add(trayIcon);
            }
            catch (AWTException e)
            {
                System.err.println(e);
            }
        }
        else
        {
            // disable tray option in your application or
            // perform other actions
        }
    }

    public void initCheckButton(Action action)
    {

        ActionListener checkListener = e -> {

            action.perform();
        };

        MenuItem checkMenuItem = new MenuItem("Check");
        checkMenuItem.addActionListener(checkListener);
        trayIcon.getPopupMenu().add(checkMenuItem);
    }
}

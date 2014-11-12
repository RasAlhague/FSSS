package com.rasalhague.rsss;

import tray.SystemTrayAdapter;
import tray.SystemTrayProvider;
import tray.TrayIconAdapter;

import java.awt.*;
import java.awt.event.ActionListener;
import java.net.URL;

public class TrayIcon
{
    private PopupMenu popupMenu;

    private TrayIcon() { }

    public static TrayIcon getInstance()
    {
        return TrayIconHolder.instance;
    }

    public void initialize(ActionListener updBntAction)
    {
        if (SystemTray.isSupported())
        {
            SystemTrayAdapter trayAdapter = new SystemTrayProvider().getSystemTray();
            URL imageUrl = getClass().getResource("/FS.png");
            String tooltip = "";

            // create a popup menu
            popupMenu = new PopupMenu();

            // create menu item for the default action
            MenuItem exitMenuItem = new MenuItem("Exit");
            exitMenuItem.addActionListener(e -> System.exit(0));
            popupMenu.add(exitMenuItem);

            MenuItem checkMenuItem = new MenuItem("Check");
            checkMenuItem.addActionListener(updBntAction);
            popupMenu.add(checkMenuItem);

            // load an image
            Image image = Toolkit.getDefaultToolkit()
                                 .getImage(TrayIcon.class.getResource("/FS.png"));

            TrayIconAdapter trayIconAdapter = trayAdapter.createAndAddTrayIcon(imageUrl, tooltip, popupMenu);
        }
        else
        {
            // disable tray option in your application or
            // perform other actions
        }
    }

    private static class TrayIconHolder
    {
        static TrayIcon instance = new TrayIcon();
    }
}

package com.rasalhague.rsss;

import com.rasalhague.rsss.configuration.ConfigurationManager;

public class EntryPoint
{
    public static void main(String[] args)
    {
        int checkPeriodSec;

        try
        {
            checkPeriodSec = Integer.parseInt(args[0]);

            if (checkPeriodSec < 10)
            {
                throw new NumberFormatException();
            }
        }
        catch (NumberFormatException | ArrayIndexOutOfBoundsException e)
        {
            checkPeriodSec = 3600;
        }


        ConfigurationManager configurationManager = ConfigurationManager.getInstance();

        SeriesChecker seriesChecker = new SeriesChecker();
        seriesChecker.setCheckPeriodSec(checkPeriodSec);
        seriesChecker.addNewSeriesAvailableEvent(new ConfigurationSaver());
        seriesChecker.addNewSeriesAvailableEvent(new Notifier());
        seriesChecker.startChecking();

        TrayIcon.getInstance().initialize(e -> seriesChecker.check());
    }
}

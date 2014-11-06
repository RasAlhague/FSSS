package com.rasalhague.rsss;

import com.rasalhague.rsss.configuration.ConfigurationHolder;
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

        TrayIcon.getInstance().initialize();

        ConfigurationManager configurationManager = ConfigurationManager.getInstance();

        SeriesChecker seriesChecker = new SeriesChecker();
        seriesChecker.setCheckPeriodSec(checkPeriodSec);
        seriesChecker.addNewSeriesAvailableEvent((localSeriesMap, actualSeriesMap, diff) -> {

            configurationManager.saveConfig(new ConfigurationHolder(actualSeriesMap));
        });
        seriesChecker.addNewSeriesAvailableEvent(new Notifier());
        seriesChecker.startChecking();

        TrayIcon.getInstance().initCheckButton(seriesChecker::check);
    }
}

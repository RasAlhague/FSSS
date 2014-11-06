package com.rasalhague.rsss;

import com.rasalhague.rsss.configuration.ConfigurationHolder;
import com.rasalhague.rsss.configuration.ConfigurationManager;

public class EntryPoint
{
    public static void main(String[] args)
    {
        int checkPeriodSec = 0;

        if (args.length == 1)
        {
            checkPeriodSec = Integer.parseInt(args[0]);
        }
        System.out.println(checkPeriodSec);

        TrayIcon.getInstance().initialize();

        ConfigurationManager configurationManager = ConfigurationManager.getInstance();

//        ConfigurationHolder configuration = configurationManager.loadConfig();
//        configuration.getSavedSeriesList()
//                     .put("http://fs.to/flist/i4ELwyjcDdRFIgfbo9wuQUM?folder=367032", new ArrayList<>());
//        configuration.getSavedSeriesList()
//                     .put("http://fs.to/flist/iw4TKfIy2bSFCQWusFnxYs?folder=366695", new ArrayList<>());
//        configurationManager.saveConfig(configuration);

        SeriesChecker seriesChecker = new SeriesChecker();
        seriesChecker.setCheckPeriodSec((checkPeriodSec == 0) ? 3600 : checkPeriodSec);
        seriesChecker.addNewSeriesAvailableEvent((localSeriesMap, actualSeriesMap, diff) -> {

            configurationManager.saveConfig(new ConfigurationHolder(actualSeriesMap));
            System.out.println("diff:   ");
//            System.out.println(diff);
            System.out.println();
        });
        seriesChecker.startChecking();

        Notifier notifier = new Notifier();

        seriesChecker.addNewSeriesAvailableEvent(notifier);
    }
}

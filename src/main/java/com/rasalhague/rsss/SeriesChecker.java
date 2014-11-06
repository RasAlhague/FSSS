package com.rasalhague.rsss;

import com.rasalhague.rsss.configuration.ConfigurationManager;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.*;

public class SeriesChecker
{
    Timer timer;
    private int                           checkPeriodSec                   = 3600;
    private List<NewSeriesAvailableEvent> NewSeriesAvailableEventObservers = new ArrayList<NewSeriesAvailableEvent>();

    public void addNewSeriesAvailableEvent(NewSeriesAvailableEvent observer)
    {
        NewSeriesAvailableEventObservers.add(observer);
    }

    public void removeNewSeriesAvailableEvent(NewSeriesAvailableEvent observer)
    {
        NewSeriesAvailableEventObservers.remove(observer);
    }

    public void notifyNewSeriesAvailableEventObservers(HashMap<String, ArrayList<String>> localSeriesMap,
                                                       HashMap<String, ArrayList<String>> actualSeriesMap,
                                                       HashMap<String, ArrayList<String>> diff)
    {
        for (NewSeriesAvailableEvent listener : NewSeriesAvailableEventObservers)
        {
            listener.newSeriesAvailableEvent(localSeriesMap, actualSeriesMap, diff);
        }
    }

    public int getCheckPeriodSec()
    {
        return checkPeriodSec;
    }

    public void setCheckPeriodSec(int checkPeriodSec)
    {
        this.checkPeriodSec = checkPeriodSec;
    }

    public synchronized void check()
    {
        //get saved series
        HashMap<String, ArrayList<String>> SavedSeriesList = ConfigurationManager.getInstance()
                                                                                 .loadConfig()
                                                                                 .getSavedSeriesList();

        //download actual series list from fs.to
        HashMap<String, ArrayList<String>> downloadedActualSeries = downloadActualSeries(SavedSeriesList.keySet());

        //compare saved series list with downloaded
        //notify if new series have been found
        compareSeries(SavedSeriesList, downloadedActualSeries);
    }

    public void startChecking()
    {
        timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask()
        {
            @Override
            public void run()
            {
                check();
            }
        }, 0, checkPeriodSec * 1000);
    }

    public void stopChecking()
    {
        timer.cancel();
    }

    private void compareSeries(HashMap<String, ArrayList<String>> savedSeriesList,
                               HashMap<String, ArrayList<String>> downloadedActualSeries)
    {
        HashMap<String, ArrayList<String>> diff = new HashMap<>();
        boolean isHaveChanges = false;

        Set<String> loadedFilesSet = savedSeriesList.keySet();
        for (String fileURL : loadedFilesSet)
        {
            if (downloadedActualSeries.containsKey(fileURL))
            {
                ArrayList<String> savedSeries = savedSeriesList.get(fileURL);
                ArrayList<String> actualSeries = downloadedActualSeries.get(fileURL);

                if (savedSeries == null) savedSeries = new ArrayList<>();

                if (actualSeries.size() > savedSeries.size())
                {
                    isHaveChanges = true;
                    diff.put(fileURL, new ArrayList<>());
                    diff.get(fileURL)
                        .addAll(actualSeries.subList(savedSeries.size(), actualSeries.size()));
                }
            }
            else
            {
                System.err.println("WTF");
            }
        }

        if (isHaveChanges)
        {
            notifyNewSeriesAvailableEventObservers(savedSeriesList, downloadedActualSeries, diff);
        }
    }

    private HashMap<String, ArrayList<String>> downloadActualSeries(Set<String> seriesFileURLs)
    {
        HashMap<String, ArrayList<String>> result = new HashMap<>();

        for (String seriesFileURL : seriesFileURLs)
        {
            //            new Thread(() -> {

            ArrayList<String> URLs = new ArrayList<>();
            URL url;
            String s;

            try
            {
                InputStream inputStream = new URL(seriesFileURL).openStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

                while ((s = bufferedReader.readLine()) != null)
                {
                    URLs.add(s);
                }

                result.put(seriesFileURL, URLs);
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
            //            }).start();
        }

        return result;
    }
}

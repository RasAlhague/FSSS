package com.rasalhague.rsss;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class Notifier implements NewSeriesAvailableEvent
{

    @Override
    public void newSeriesAvailableEvent(HashMap<String, ArrayList<String>> localSeriesMap,
                                        HashMap<String, ArrayList<String>> actualSeriesMap,
                                        HashMap<String, ArrayList<String>> diff)
    {
        justOpenAllInVLC(localSeriesMap, actualSeriesMap, diff);
    }

    private void justOpenAllInVLC(HashMap<String, ArrayList<String>> localSeriesMap,
                                  HashMap<String, ArrayList<String>> actualSeriesMap,
                                  HashMap<String, ArrayList<String>> diff)
    {
        diff.forEach((seriesFilePath, seriesPaths) -> {

//            System.out.println(actualSeriesMap.get(seriesFilePath).size());
//            System.out.println(seriesPaths.size());

            if (actualSeriesMap.get(seriesFilePath).size() != seriesPaths.size())
            {
                seriesPaths.forEach(seriesPath -> {

                    try
                    {
                        Runtime.getRuntime()
                               .exec("vlc " + seriesPath);

                    }
                    catch (IOException e)
                    {
                        e.printStackTrace();
                    }
                });
            }
        });
    }
}

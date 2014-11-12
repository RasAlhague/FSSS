package com.rasalhague.rsss;

import com.rasalhague.rsss.configuration.ConfigurationHolder;
import com.rasalhague.rsss.configuration.ConfigurationManager;

import java.util.ArrayList;
import java.util.HashMap;

public class ConfigurationSaver implements NewSeriesAvailableEvent
{
    @Override
    public void newSeriesAvailableEvent(HashMap<String, ArrayList<String>> localSeriesMap,
                                        HashMap<String, ArrayList<String>> actualSeriesMap,
                                        HashMap<String, ArrayList<String>> diff)
    {
        ConfigurationManager.getInstance().saveConfig(new ConfigurationHolder(actualSeriesMap));
    }
}

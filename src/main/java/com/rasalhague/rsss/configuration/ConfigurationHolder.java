package com.rasalhague.rsss.configuration;

import java.util.ArrayList;
import java.util.HashMap;

public class ConfigurationHolder
{
    HashMap<String, ArrayList<String>> SavedSeriesList = new HashMap<>();

    public ConfigurationHolder() {}

    public ConfigurationHolder(HashMap<String, ArrayList<String>> savedSeriesList)
    {
        SavedSeriesList = savedSeriesList;
    }

    public HashMap<String, ArrayList<String>> getSavedSeriesList()
    {
        return SavedSeriesList;
    }

    public void setSavedSeriesList(HashMap<String, ArrayList<String>> savedSeriesList)
    {
        SavedSeriesList = savedSeriesList;
    }

    @Override
    public String toString()
    {
        return "ConfigurationHolder{" +
                "SavedSeriesList=" + SavedSeriesList +
                '}';
    }
}

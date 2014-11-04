package com.rasalhague.rsss;

import java.util.ArrayList;
import java.util.HashMap;

public interface NewSeriesAvailableEvent
{
    void newSeriesAvailableEvent(HashMap<String, ArrayList<String>> localSeriesMap,
                                 HashMap<String, ArrayList<String>> actualSeriesMap,
                                 HashMap<String, ArrayList<String>> diff);
}

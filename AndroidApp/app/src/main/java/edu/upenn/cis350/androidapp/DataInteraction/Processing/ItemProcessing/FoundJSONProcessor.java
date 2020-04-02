package edu.upenn.cis350.androidapp.DataInteraction.Processing.ItemProcessing;

import java.util.*;
import edu.upenn.cis350.androidapp.DataInteraction.Management.ItemManagement.*;
import edu.upenn.cis350.androidapp.DataInteraction.Data.*;


public class FoundJSONProcessor {

    private FoundJSONReader reader = FoundJSONReader.getInstance();


    private FoundJSONProcessor() {}

    private static FoundJSONProcessor instance = new FoundJSONProcessor();

    public static FoundJSONProcessor getInstance() {
        return instance;
    }

    public Collection<FoundItem> getAllFoundItems() {
        return reader.getAllFoundItems();
    }

    public long getLargestId() {
        long max = 0;
        for (FoundItem i : reader.getAllFoundItems()) {
            if (i.getId() > max) {
                max = i.getId();
            }
        }
        return max;
    }

    public FoundItem getFoundItemById(long id) {
        for (FoundItem i : reader.getAllFoundItems()) {
            if (i.getId() == id) {
                return i;
            }
        }
        return null;
    }

}

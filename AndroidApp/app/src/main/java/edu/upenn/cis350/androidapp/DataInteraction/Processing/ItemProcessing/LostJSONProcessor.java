package edu.upenn.cis350.androidapp.DataInteraction.Processing.ItemProcessing;

import java.util.*;
import edu.upenn.cis350.androidapp.DataInteraction.Management.ItemManagement.*;
import edu.upenn.cis350.androidapp.DataInteraction.Data.LostItem;


public class LostJSONProcessor {

    private LostJSONReader reader = LostJSONReader.getInstance();


    private LostJSONProcessor() {}

    private static LostJSONProcessor instance = new LostJSONProcessor();

    public static LostJSONProcessor getInstance() {
        return instance;
    }

    public Collection<LostItem> getAllLostItems() {
        return reader.getAllLostItems();
    }

    public long getLargestId() {
        long max = 0;
        for (LostItem i : reader.getAllLostItems()) {
            if (i.getId() > max) {
                max = i.getId();
            }
        }
        return max;
    }

    public LostItem getLostItemById(long id) {
        for (LostItem i : reader.getAllLostItems()) {
            if (i.getId() == id) {
                return i;
            }
        }
        return null;
    }

}

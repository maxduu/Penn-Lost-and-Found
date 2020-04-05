package edu.upenn.cis350.androidapp.DataInteraction.Management.ItemManagement;
import java.math.*;

import edu.upenn.cis350.androidapp.DataInteraction.Processing.ItemProcessing.FoundJSONProcessor;
import edu.upenn.cis350.androidapp.DataInteraction.Processing.ItemProcessing.LostJSONProcessor;

public class ItemIndexer {
    public static long generateId() {
        return Math.max(FoundJSONProcessor.getInstance().getLargestId(),
                LostJSONProcessor.getInstance().getLargestId()) + 1;
    }
}

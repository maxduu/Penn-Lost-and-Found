package edu.upenn.cis350.androidapp.DataInteraction.Management.ItemManagement;

import java.net.URL;
import java.util.Date;

import edu.upenn.cis350.androidapp.AccessWebTask;

import edu.upenn.cis350.androidapp.DataInteraction.Processing.ItemProcessing.*;

public class FoundJSONWriter {

    private FoundJSONProcessor processor = FoundJSONProcessor.getInstance();

    private FoundJSONWriter() {}

    private static FoundJSONWriter instance = new FoundJSONWriter();

    public static FoundJSONWriter getInstance() {
        return instance;
    }

    public void createFoundItem(long posterId, String category, Date date,
                               double latitude, double longitude, String around) {

        long id = ItemIndexer.generateId();

        try {
            URL url = new URL("http://10.0.2.2:3000/create-found-item?"
                    + "id=" + id + "&posterId=" + posterId +
                    "&category=" + category + "&date=" + date +
                    "&latitude=" + latitude + "&longitude=" + longitude +
                    "&around=" + around);
            AccessWebTask task = new AccessWebTask();
            task.execute(url);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void removeFoundItemById (long id) {
        try {
            URL url = new URL("http://10.0.2.2:3000/remove-found-item?"
                    + "id=" + id);
            AccessWebTask task = new AccessWebTask();
            task.execute(url);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}

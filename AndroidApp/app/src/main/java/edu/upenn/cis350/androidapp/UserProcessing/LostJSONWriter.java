package edu.upenn.cis350.androidapp.UserProcessing;

import java.net.URL;
import java.util.Date;

import edu.upenn.cis350.androidapp.AccessWebTask;

public class LostJSONWriter {

    private LostJSONProcessor processor = LostJSONProcessor.getInstance();

    private LostJSONWriter() {}

    private static LostJSONWriter instance = new LostJSONWriter();

    public static LostJSONWriter getInstance() {
        return instance;
    }

    public void createLostItem(long posterId, String category, Date date,
                               double latitude, double longitude, String around,
                               String attachmentLoc, String description, String additionalInfo) {

        long id = processor.getLargestId() + 1;

        try {
            URL url = new URL("http://10.0.2.2:3000/create-lost-item?"
                    + "id=" + id + "&posterId=" + posterId +
                    "&category=" + category + "&date=" + date +
                    "&latitude=" + latitude + "&longitude=" + longitude +
                    "&around=" + around + "&description=" + description +
                    "&attachmentLoc=" + attachmentLoc + "&additionalInfo="
                    + additionalInfo);
            AccessWebTask task = new AccessWebTask();
            task.execute(url);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}

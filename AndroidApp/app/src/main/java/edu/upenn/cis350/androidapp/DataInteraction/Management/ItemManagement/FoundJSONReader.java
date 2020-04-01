package edu.upenn.cis350.androidapp.DataInteraction.Management.ItemManagement;
import java.net.URL;
import java.util.*;
import org.json.simple.parser.*;
import org.json.simple.*;
import java.text.*;

import edu.upenn.cis350.androidapp.AccessWebTask;
import edu.upenn.cis350.androidapp.DataInteraction.Data.*;

public class FoundJSONReader {

    private FoundJSONReader() {}

    private static FoundJSONReader instance = new FoundJSONReader();

    public static FoundJSONReader getInstance() {
        return instance;
    }

    public Collection<FoundItem> getAllFoundItems() {

        Collection<FoundItem> FoundItems = new HashSet<FoundItem>();
        JSONParser parser = new JSONParser();

        try {
            URL url = new URL("http://10.0.2.2:3000/all-found-items");
            AccessWebTask task = new AccessWebTask();
            task.execute(url);
            JSONObject jo = (JSONObject) parser.parse(task.get());
            JSONArray items = (JSONArray) jo.get("items");

            Iterator iter = items.iterator();

            while (iter.hasNext()) {

                JSONObject item = (JSONObject) iter.next();

                long id = (long) item.get("id");
                long posterId = ((long) item.get("posterId"));
                String category = (String) item.get("category");

                String rawDate = (String) item.get("date");
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
                // Kevin look in discord chat to see what you have to do for SimpleDateFormat
                Date date = null;

                date = dateFormat.parse(rawDate);

                double latitude = Double.valueOf(item.get("latitude").toString());
                double longitude = Double.valueOf(item.get("longitude").toString());
                String around = (String) item.get("around");
                String attachmentLoc = (String) item.get("attachmentLoc");
                String description = (String) item.get("description");
                String additionalInfo = (String) item.get("additionalInfo");

                FoundItem l = new FoundItem(id, posterId, category, date, latitude, longitude,
                        around, attachmentLoc);
                FoundItems.add(l);

            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return FoundItems;

    }

}

package edu.upenn.cis350.androidapp.DataInteraction.Management.ItemManagement;
import java.net.URL;
import java.util.*;
import org.json.simple.parser.*;
import org.json.simple.*;
import java.text.*;

import edu.upenn.cis350.androidapp.AccessWebTask;
import edu.upenn.cis350.androidapp.DataInteraction.Data.*;

public class LostJSONReader {

    private LostJSONReader() {}

    private static LostJSONReader instance = new LostJSONReader();

    public static LostJSONReader getInstance() {
        return instance;
    }

    public Collection<LostItem> getAllLostItems() {

        Collection<LostItem> lostItems = new HashSet<LostItem>();
        JSONParser parser = new JSONParser();

        try {
            URL url = new URL("http://10.0.2.2:3000/all-lost-items");
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
                dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
                // Kevin look in discord chat to see what you have to do for SimpleDateFormat
                Date date = null;

                date = dateFormat.parse(rawDate);

                double latitude = Double.valueOf(item.get("latitude").toString());
                double longitude = Double.valueOf(item.get("longitude").toString());
                String around = (String) item.get("around");
                String attachmentLoc = (String) item.get("attachmentLoc");
                String description = (String) item.get("description");
                String additionalInfo = (String) item.get("additionalInfo");

                LostItem l = new LostItem(id, posterId, category, date, latitude, longitude,
                        around, attachmentLoc, description, additionalInfo);
                lostItems.add(l);

            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return lostItems;

    }

}

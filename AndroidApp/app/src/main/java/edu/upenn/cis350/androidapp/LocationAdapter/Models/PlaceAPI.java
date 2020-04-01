package edu.upenn.cis350.androidapp.LocationAdapter.Models;

import org.json.simple.parser.*;
import org.json.simple.*;

import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;

import edu.upenn.cis350.androidapp.AccessWebTask;

public class PlaceAPI {

    public ArrayList<Place> autoComplete(String input) {

        ArrayList<Place> list = new ArrayList<Place>();
        JSONParser parser = new JSONParser();

        try {
            URL url = new URL("https://api.foursquare.com/v2/venues/search?ll=39.9522,-75.1932&" +
                    "client_id=3JD1CMRDIAIYTO5NSFQ1AQOBUU0B0I0QV0DGT2IWXZBHV3AR&" +
                    "client_secret=3YKIS2LCZINMZTAMTOSE5NKVYAIEEPZJW4MVFQJXDXCUFLEA&" +
                    "v=20200520&query=" + input);
            AccessWebTask task = new AccessWebTask();
            task.execute(url);
            JSONObject jo = (JSONObject) parser.parse(task.get());
            JSONObject response = (JSONObject) jo.get("response");
            JSONArray venues = (JSONArray) response.get("venues");

            Iterator iter = venues.iterator();

            while (iter.hasNext()) {
                JSONObject venue = (JSONObject) iter.next();
                String name = (String) venue.get("name");
                JSONObject location = (JSONObject) venue.get("location");
                double latitude = Double.valueOf(location.get("lat").toString());
                double longitude = Double.valueOf(location.get("lng").toString());
                list.add(new Place(name, latitude, longitude));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
}

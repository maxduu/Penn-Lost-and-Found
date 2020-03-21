package edu.upenn.cis350.androidapp;

import java.net.*;
import android.os.*;
import java.util.*;
import org.json.*;

public class AccessWebTask extends AsyncTask<URL, String, String> {

    protected String doInBackground(URL... urls){

        try {
            // get the first URL from the array
            URL url = urls[0];

            // create connection and send HTTP request
            HttpURLConnection conn = (HttpURLConnection)url.openConnection();

            conn.setRequestMethod("GET");
            conn.connect();

            // read first line of data that is returned
            Scanner in = new Scanner(url.openStream());
            String msg = in.nextLine();

            // use Android JSON library to parse JSON
            JSONObject jo = new JSONObject(msg);

            in.close();
            conn.disconnect();

            return jo.toString();
        }
        catch (Exception e) {
            return e.toString();
        }

    }

    // called after doInBackground finishes
    protected void onPostExecute(String msg) {

    }

}
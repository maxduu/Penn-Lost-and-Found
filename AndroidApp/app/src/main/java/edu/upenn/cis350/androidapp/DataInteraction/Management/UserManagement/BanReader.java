package edu.upenn.cis350.androidapp.DataInteraction.Management.UserManagement;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedList;

import edu.upenn.cis350.androidapp.AccessWebTask;
import edu.upenn.cis350.androidapp.DataInteraction.Data.Account;
import edu.upenn.cis350.androidapp.DataInteraction.Data.Ban;

public class BanReader {

    private BanReader() { };

    private static BanReader instance = new BanReader();

    public static BanReader getInstance() {
        return instance;
    }

    public Ban getBanOfUser(long id) {
        try {
            URL url = new URL("http://10.0.2.2:3000/get-ban?userId=" + id);
            AccessWebTask task = new AccessWebTask();
            task.execute(url);
            JSONParser parser = new JSONParser();
            JSONObject full = (JSONObject) parser.parse(task.get());
            if (full.get("status").equals("success")) {
                JSONObject rawBan = (JSONObject) full.get("ban");
                long userId = (long) rawBan.get("userId");
                String rawDate = (String) rawBan.get("until");
                String message = (String) rawBan.get("message");
                Date date = null;
                SimpleDateFormat dateFormat = new SimpleDateFormat(
                        "yyyy-MM-dd'T'HH:mm:ss.SSSZ");
                try {
                    date = dateFormat.parse(rawDate.replaceAll("Z$", "+0000"));
                } catch (ParseException e) {
                    System.out.println("date parse error");
                }
                Ban ban = new Ban(userId, date, message);
                return ban;
            } else {
                return null;
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return null;
    }



}

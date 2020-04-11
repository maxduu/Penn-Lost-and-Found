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
import edu.upenn.cis350.androidapp.DataInteraction.Data.Ban;
import edu.upenn.cis350.androidapp.DataInteraction.Data.Warning;

public class WarningReader {

    private WarningReader() { };

    private static WarningReader instance = new WarningReader();

    public static WarningReader getInstance() {
        return instance;
    }

    public Collection<Warning> getWarningsOfUser(long id) {
        try {
            Collection<Warning> warnings = new LinkedList<>();
            URL url = new URL("http://10.0.2.2:3000/get-warnings?userId=" + id);
            AccessWebTask task = new AccessWebTask();
            task.execute(url);
            JSONParser parser = new JSONParser();
            JSONObject full = (JSONObject) parser.parse(task.get());
            if (full.get("status").equals("success")) {
                JSONArray infos = (JSONArray) full.get("warnings");
                Iterator iter = infos.iterator();
                while (iter.hasNext()) {
                    JSONObject info = (JSONObject) iter.next();
                    long userId = (long) info.get("userId");
                    boolean seen = (boolean) info.get("seen");
                    String message = (String) info.get("message");
                    Warning warning = new Warning(userId, seen, message);
                    // Should be chronological order of warnings given
                    warnings.add(warning);
                }
                return warnings;
            } else {
                return new LinkedList<Warning>();
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return new LinkedList<Warning>();
    }



}

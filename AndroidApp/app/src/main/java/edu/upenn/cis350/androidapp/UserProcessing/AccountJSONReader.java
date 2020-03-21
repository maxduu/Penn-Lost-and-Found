package edu.upenn.cis350.androidapp.UserProcessing;


import java.io.FileReader;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import edu.upenn.cis350.androidapp.AccessWebTask;

public class AccountJSONReader {

    public AccountJSONReader() { }

    private static AccountJSONReader instance = new AccountJSONReader();

    public static AccountJSONReader getInstance() {
        return instance;
    }

    /**
     * Currently finds all Account objects in a text JSON file.
     * @return a collection of all accounts found in database.
     * @throws Exception
     */
    public Collection<Account> getAllAccounts() throws Exception {
        Collection<Account> accounts = new LinkedList<Account>();
        try {
            URL url = new URL("http://10.0.2.2:3000/all-users");
            AccessWebTask task = new AccessWebTask();
            task.execute(url);
            JSONParser parser = new JSONParser();
            JSONObject full = (JSONObject) parser.parse(task.get());
            JSONArray items = (JSONArray) full.get("items");
            Iterator iter = items.iterator();
            while (iter.hasNext()) {
                JSONObject info = (JSONObject) iter.next();
                long id = (long) info.get("id");
                String username = (String) info.get("username");
                String password = (String) info.get("password");
                JSONArray lostItems = (JSONArray) info.get("lost_items");
                JSONArray foundItems = (JSONArray) info.get("found_items");
                String rawDate = (String) info.get("last_login");
                long status = (long) info.get("status");
                System.out.println(rawDate);
                Date date = null;
                SimpleDateFormat dateFormat = new SimpleDateFormat(
                        "yyyy-MM-dd'T'HH:mm:ss.SSSZ");
                try {
                    date = dateFormat.parse(rawDate.replaceAll("Z$", "+0000"));
                } catch (ParseException e) {
                    System.out.println("date parse error");
                }

                Account acc = new Account(id, username, password,
                        lostItems, foundItems, date, (int) status);
                accounts.add(acc);
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return accounts;
    }


}

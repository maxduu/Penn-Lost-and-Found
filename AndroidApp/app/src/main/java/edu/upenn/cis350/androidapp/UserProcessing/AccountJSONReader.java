package edu.upenn.cis350.androidapp.UserProcessing;


import java.io.FileReader;
import java.util.Date;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class AccountJSONReader {

    private String filename;

    public AccountJSONReader(String name) {
        filename = name;
    }

    private static AccountJSONReader instance = new AccountJSONReader("users.json");

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
        JSONParser parser = new JSONParser();
        try {
            JSONArray infos = (JSONArray) parser.parse(new FileReader(filename));
            Iterator iter = infos.iterator();
            while (iter.hasNext()) {
                JSONObject info = (JSONObject) iter.next();
                long id = (long) info.get("id");
                String username = (String) info.get("username");
                String password = (String) info.get("password");
                String lostItems = (String) info.get("lost_items");
                String foundItems = (String) info.get("found_items");
                long login = (long) info.get("last_login");
                long status = (long) info.get("status");
                Account acc = new Account(id, username, password,
                        toList(lostItems), toList(foundItems), new Date(login), (int) status);
                accounts.add(acc);
            }
        } catch (Exception e) {
            throw e;
        }
        return accounts;
    }

    /**
     * Helper method: converts a string representation of a list to the list object
     * @param items A list of found or lost items
     * @return a List representation of the given items
     */
    private List<Long> toList(String items) {
        if (items.isEmpty() || items.length() < 3) {
            return new LinkedList<Long>();
        }
        List<String> stringList = Arrays.asList(
                items.substring(1, items.length() - 1).split(", "));
        List<Long> longList = new LinkedList<Long>();
        for (String s : stringList) {
            longList.add(Long.valueOf(s));
        }
        return longList;
    }


}

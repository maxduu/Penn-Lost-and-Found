package edu.upenn.cis350.androidapp.UserProcessing;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.Collection;
import java.util.Date;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class AccountJSONWriter {

    private PrintWriter out;

    private AccountJSONWriter(String filename) {
        try {
            FileWriter writer = new FileWriter(filename, true);
            out = new PrintWriter(writer);
        } catch (Exception e) { }
    }

    private static AccountJSONWriter instance = new AccountJSONWriter("users.json");

    public static AccountJSONWriter getInstance() {
        return instance;
    }


    public void addNewAccount(Account user) {
        // Doesn't really work. Implement with mongo
        /*
        JSONObject userDetails = new JSONObject();
        userDetails.put("id", user.getId());
        userDetails.put("username", user.getUsername());
        userDetails.put("password", user.getPassword());
        userDetails.put("lost_items", user.getLostItemsPosted().toString());
        userDetails.put("found_items", user.getFoundItemsPosted().toString());
        userDetails.put("status", user.getStatus());
        if (user.getLastLogin() == null) {
            userDetails.put("last_login", -1);
        } else {
            userDetails.put("last_login", user.getLastLogin().getTime());
        }
        out.append(userDetails.toJSONString());
        out.flush();
        */
        throw new UnsupportedOperationException("Not yet implemented.");
    }

    // to implement with mongo
    public void changePassword(long id, String newPassword) {
        throw new UnsupportedOperationException("Not yet implemented.");
    }

    public void changeStatus(long id, int newStatus) {
        throw new UnsupportedOperationException("Not yet implemented.");
    }

    public void changeLastLogin(long id) {
        throw new UnsupportedOperationException("Not yet implemented.");
    }

    public void addLostItem(long id, long itemId) {
        throw new UnsupportedOperationException("Not yet implemented.");
    }

    public void addFoundItem(long id, long itemId) {
        throw new UnsupportedOperationException("Not yet implemented.");
    }


}


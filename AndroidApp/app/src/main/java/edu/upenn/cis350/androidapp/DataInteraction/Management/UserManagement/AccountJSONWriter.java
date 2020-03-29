package edu.upenn.cis350.androidapp.DataInteraction.Management.UserManagement;

import java.io.PrintWriter;
import java.net.URL;
import java.util.Date;
import java.util.List;

import edu.upenn.cis350.androidapp.AccessWebTask;
import edu.upenn.cis350.androidapp.DataInteraction.Data.Account;

public class AccountJSONWriter {

    private PrintWriter out;

    private AccountJSONWriter() { }

    private static AccountJSONWriter instance = new AccountJSONWriter();

    public static AccountJSONWriter getInstance() {
        return instance;
    }


    public void addNewAccount(Account user) {
        try {
            URL url = new URL("http://10.0.2.2:3000/create-user?"
                    + "id=" + user.getId() + "&username=" + user.getUsername() +
                    "&password=" + user.getPassword() + "&last_login=" + user.getLastLogin() +
                    "&status=" + user.getStatus() +
                    "&lost_items=" + user.getLostItemsPosted() +
                    "&found_items=" + user.getFoundItemsPosted());
            AccessWebTask task = new AccessWebTask();
            task.execute(url);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void changePassword(long id, String newPassword) {
        try {
            URL url = new URL("http://10.0.2.2:3000/update-user?"
                    + "id=" + id + "&password=" + newPassword);
            AccessWebTask task = new AccessWebTask();
            task.execute(url);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void changeStatus(long id, int newStatus) {
        try {
            URL url = new URL("http://10.0.2.2:3000/update-user?"
                    + "id=" + id + "&status=" + newStatus);
            AccessWebTask task = new AccessWebTask();
            task.execute(url);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void updateLastLogin(long id) {
        try {
            URL url = new URL("http://10.0.2.2:3000/update-user?"
                    + "id=" + id + "&last_login=" + new Date());
            AccessWebTask task = new AccessWebTask();
            task.execute(url);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void addLostItem(Account user, long itemId) {
        try {
            List<Long> items = user.getLostItemsPosted();
            items.add(itemId);
            URL url = new URL("http://10.0.2.2:3000/update-user?"
                    + "id=" + user.getId() + "&lost_items=" + items);
            AccessWebTask task = new AccessWebTask();
            task.execute(url);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void addFoundItem(Account user, long itemId) {
        try {
            List<Long> items = user.getFoundItemsPosted();
            items.add(itemId);
            URL url = new URL("http://10.0.2.2:3000/update-user?"
                    + "id=" + user.getId() + "&found_items=" + items);
            AccessWebTask task = new AccessWebTask();
            task.execute(url);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}


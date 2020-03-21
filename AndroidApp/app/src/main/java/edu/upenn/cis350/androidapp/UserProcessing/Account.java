package edu.upenn.cis350.androidapp.UserProcessing;


import java.util.Date;

import java.util.LinkedList;
import java.util.List;

public class Account {

    protected long id;
    protected String username;
    protected String password;
    protected List<Long> lostItemsPosted;
    protected List<Long> foundItemsPosted;
    protected Date lastLogin;
    int status;

    public Account(long id, String username, String password) {
        // Use reader to make new id
        this.id = id;
        this.username = username;
        this.password = password;
        lostItemsPosted = new LinkedList<Long>();
        foundItemsPosted = new LinkedList<Long>();
        status = 1;
    }

    public Account(long id, String username, String password, List<Long> lostItemsPosted,
                   List<Long> foundItemsPosted, Date lastLogin, int status) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.lostItemsPosted = lostItemsPosted;
        this.foundItemsPosted = foundItemsPosted;
        this.lastLogin = lastLogin;
        this.status = status;
    }

    protected void setID(long newId) {
        id = newId;
    }

    public void changePassword(String pass) {
        password = pass;
    }

    public void addLostItem(Long itemId) {
        lostItemsPosted.add(itemId);
    }

    public void addFoundItem(Long itemId) {
        foundItemsPosted.add(itemId);
    }

    public void login() {
        lastLogin = new Date();
    }

    public void banUser() {
        status = 2;
    }

    public void unBanUser() {
        status = 1;
    }

    public long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public List<Long> getLostItemsPosted() {
        return lostItemsPosted;
    }

    public List<Long> getFoundItemsPosted() {
        return foundItemsPosted;
    }

    public Date getLastLogin() {
        return lastLogin;
    }

    public int getStatus() {
        return status;
    }

    public String toString() {
        String answer = "";
        answer += "id: " + id;
        answer += ", username: " + username;
        answer += ", password: " + password;
        answer += ", lost_items: " + lostItemsPosted.toString();
        answer += ", found_items: " + foundItemsPosted.toString();
        answer += ", last_login: " + lastLogin.getTime();
        answer += ", status: " + status;
        return answer;
    }
}


package edu.upenn.cis350.androidapp.DataInteraction.Processing.UserProcessing;

import java.util.Collection;

import edu.upenn.cis350.androidapp.DataInteraction.Data.Account;
import edu.upenn.cis350.androidapp.DataInteraction.Management.UserManagement.AccountJSONReader;
import edu.upenn.cis350.androidapp.DataInteraction.Management.UserManagement.AccountJSONWriter;

public class AccountJSONProcessor {

    private AccountJSONReader reader;
    private AccountJSONWriter writer;

    private AccountJSONProcessor() {
        reader = AccountJSONReader.getInstance();
        writer = AccountJSONWriter.getInstance();
    }

    private static AccountJSONProcessor instance = new AccountJSONProcessor();

    public static AccountJSONProcessor getInstance() {
        return instance;
    }

    public Account getAccount(long userId) { return getAccountFromId(userId); }

    /**
     * Uses the reader to get all Account objects.
     * @return a Collection of all Accounts in the database.
     */
    public Collection<Account> getAllAccounts() {
        try {
            return reader.getAllAccounts();
        } catch (Exception e) {
            System.out.println(e);
            return null;
        }
    }

    /**
     * Creates a new account to put in the database.
     * @param username Username (email) of new user
     * @param password Password of new user
     */
    public void createNewAccount(String username, String password) {
        Account user = new Account(findNewId(), username, password);
        if (!usernameIsNew(username)) {
            throw new IllegalArgumentException("Username already exists");
        }
        writer.addNewAccount(user);
    }


    /**
     * Checks if a given username does not yet exist in database.
     * @param username The username to check.
     * @return True if the username does not exist in database, false otherwise.
     */
    public boolean usernameIsNew(String username) {
        try {
            Collection<Account> accounts = getAllAccounts();
            for (Account a : accounts) {
                if (a.getUsername().equals(username)) {
                    return false;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }

    /**
     * Helper method used to create a new unique id not yet in database.
     * @return an id
     */
    private long findNewId() {
        long id = 1;
        try {
            Collection<Account> existing = getAllAccounts();
            if (existing.size() == 0) {
                return id;
            } else {
                long maxID = -1;
                for (Account a : existing) {
                    if (a.getId() > maxID) {
                        maxID = a.getId();
                    }
                }
                id = maxID + 1;
                return id;
            }
        } catch (Exception e) {
            return 1;
        }
    }

    /**
     * Gets the id given an inputted username
     * @param username Username to get id of
     * @return found id if exists
     */
    public long getIdFromUsername(String username) {
        for (Account a : getAllAccounts()) {
            if (username.equals(a.getUsername())) {
                return a.getId();
            }
        }
        throw new IllegalArgumentException("Username not found.");
    }


    /**
     * Scans the database for the matching username, password combination.
     * @param username Username that is logging in
     * @param password Password
     * @return 0 if successful login, 1 if username not found, and 2 if
     * password doesn't match the username.
     */
    public int attemptLogin(String username, String password) {
        try {
            Collection<Account> accounts = getAllAccounts();
            Account found = null;
            for (Account a : accounts) {
                if (username.equals(a.getUsername())) {
                    found = a;
                    break;
                }
            }
            if (found == null) {
                return 1;
            }
            if (!password.equals(found.getPassword())) {
                return 2;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }


    public void changePassword(long id, String newPassword) {
        writer.changePassword(id, newPassword);
    }

    // No longer used
//    public void banAccount(long id) {
//        writer.changeStatus(id, 2);
//    }
//
//    public void unbanAccount(long id) {
//        writer.changeStatus(id, 1);
//    }

    public void updateLastLogin(long id) {
        writer.updateLastLogin(id);
    }

    public void addLostItem(long id, long itemId) {
        writer.addLostItem(getAccountFromId(id), itemId);
    }

    public void addFoundItem(long id, long itemId) {
        writer.addFoundItem(getAccountFromId(id), itemId);
    }

    private Account getAccountFromId(long id) {
        try {
            Collection<Account> accounts = getAllAccounts();
            for (Account a : accounts) {
                if (id == a.getId()) {
                    return a;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("Account processing " + id);
        throw new IllegalArgumentException("No account with id found");
    }

}


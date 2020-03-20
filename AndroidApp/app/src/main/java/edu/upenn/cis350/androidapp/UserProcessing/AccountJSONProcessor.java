package edu.upenn.cis350.androidapp.UserProcessing;

import java.io.FileReader;

import java.util.Collection;

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

    /**
     * Uses the reader to get all Account objects.
     * @return a Collection of all Accounts in the database.
     */
    public Collection<Account> getAllAccounts() {
        try {
            return reader.getAllAccounts();
        } catch (Exception e) {
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
        try {
            Collection<Account> accounts = reader.getAllAccounts();
            for (Account a : accounts) {
                if (a.getUsername() == user.getUsername()) {
                    throw new IllegalArgumentException("Username already taken.");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        writer.addNewAccount(user);
    }

    /**
     * Helper method used to create a new unique id not yet in database.
     * @return an id
     */
    private long findNewId() {
        long id = 1;
        try {
            Collection<Account> existing = reader.getAllAccounts();
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
            Collection<Account> accounts = reader.getAllAccounts();
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


    // To implement with mongo
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


package edu.upenn.cis350.androidapp.DataInteraction.Data;

public class Warning {

    protected long userId;
    protected boolean seen;
    protected String message;

    public Warning(long userId, boolean seen, String message) {
        this.userId = userId;
        this.seen = seen;
        this.message = message;
    }

    public long getUserId() {
        return userId;
    }

    public boolean isSeen() {
        return seen;
    }

    public String getMessage() {
        return message;
    }

}

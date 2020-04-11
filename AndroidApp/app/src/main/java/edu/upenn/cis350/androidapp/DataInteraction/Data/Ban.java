package edu.upenn.cis350.androidapp.DataInteraction.Data;

import java.util.Date;

public class Ban {

    protected long userId;
    protected Date until;
    protected String message;

    public Ban(long userId, Date until, String message) {
        this.userId = userId;
        this.until = until;
        this.message = message;
    }

    public Date getUntil() {
        return until;
    }

    public long getUserId() {
        return userId;
    }

    public String getMessage() {
        return message;
    }

}

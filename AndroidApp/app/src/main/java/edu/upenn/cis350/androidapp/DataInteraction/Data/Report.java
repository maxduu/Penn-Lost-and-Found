package edu.upenn.cis350.androidapp.DataInteraction.Data;

import java.util.Date;

public class Report {

    protected long reporterId;
    protected long violatorId;
    protected String category;
    protected String message;

    public Report(long reporterId, long violatorId, String category, String message) {
        this.reporterId = reporterId;
        this.violatorId = violatorId;
        this.category = category;
        this.message = message;
    }

    public long getReporterId() {
        return reporterId;
    }

    public long getViolatorId() {
        return violatorId;
    }

    public String getCategory() {
        return category;
    }

    public String getMessage() {
        return message;
    }

}


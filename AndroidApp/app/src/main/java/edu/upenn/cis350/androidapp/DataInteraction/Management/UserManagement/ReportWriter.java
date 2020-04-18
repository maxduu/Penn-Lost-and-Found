package edu.upenn.cis350.androidapp.DataInteraction.Management.UserManagement;

import android.util.Log;

import java.net.URL;
import java.util.Date;

import edu.upenn.cis350.androidapp.AccessWebTask;

import edu.upenn.cis350.androidapp.DataInteraction.Processing.ItemProcessing.*;
import edu.upenn.cis350.androidapp.DataInteraction.Processing.UserProcessing.ReportProcessor;

public class ReportWriter {

    private ReportProcessor processor = ReportProcessor.getInstance();

    private ReportWriter() {}

    private static ReportWriter instance = new ReportWriter();

    public static ReportWriter getInstance() {
        return instance;
    }

    public void createReport(long reporterId, long violatorId, String category, String message) {
        try {
            URL url = new URL("http://10.0.2.2:3000/report?"
                    + "reporterId=" + reporterId + "&violatorId=" + violatorId +
                    "&category=" + category + "&message=" + message);
            AccessWebTask task = new AccessWebTask();
            task.execute(url);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}

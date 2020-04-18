package edu.upenn.cis350.androidapp.DataInteraction.Processing.UserProcessing;

import java.net.URL;
import java.util.Collection;

import edu.upenn.cis350.androidapp.AccessWebTask;
import edu.upenn.cis350.androidapp.DataInteraction.Data.Ban;
import edu.upenn.cis350.androidapp.DataInteraction.Data.Chat;
import edu.upenn.cis350.androidapp.DataInteraction.Data.Report;
import edu.upenn.cis350.androidapp.DataInteraction.Data.Warning;
import edu.upenn.cis350.androidapp.DataInteraction.Management.UserManagement.BanReader;
import edu.upenn.cis350.androidapp.DataInteraction.Management.UserManagement.ReportReader;
import edu.upenn.cis350.androidapp.DataInteraction.Management.UserManagement.WarningReader;

public class ReportProcessor {

    private ReportReader reader;

    private ReportProcessor() {
        this.reader = ReportReader.getInstance();
    }

    private static ReportProcessor instance = new ReportProcessor();

    public static ReportProcessor getInstance() { return instance; }

    public Collection<Report> getReportsOfUser(long id) {
        return reader.getReportsOfUser(id);
    }

    public Collection<Report> getAllReports() {
        return reader.getAllReports();
    }

    public boolean existsDuplicateReport(long reporterId, long violatorId, String category) {
        for (Report r : getReportsOfUser(violatorId)) {
            if (r.getReporterId() == reporterId && r.getCategory().equals(category)) {
                return true;
            }
        }
        return false;
    }

}
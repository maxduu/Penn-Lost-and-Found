package edu.upenn.cis350.androidapp.DataInteraction.Management.UserManagement;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.net.URL;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;

import edu.upenn.cis350.androidapp.AccessWebTask;
import edu.upenn.cis350.androidapp.DataInteraction.Data.Report;

public class ReportReader {

    private ReportReader() { };

    private static ReportReader instance = new ReportReader();

    public static ReportReader getInstance() {
        return instance;
    }

    public Collection<Report> getReportsOfUser(long id) {
        try {
            Collection<Report> reports = new LinkedList<>();
            URL url = new URL("http://10.0.2.2:3000/get-reports?userId=" + id);
            AccessWebTask task = new AccessWebTask();
            task.execute(url);
            JSONParser parser = new JSONParser();
            JSONObject full = (JSONObject) parser.parse(task.get());
            if (full.get("status").equals("success")) {
                JSONArray infos = (JSONArray) full.get("reports");
                Iterator iter = infos.iterator();
                while (iter.hasNext()) {
                    JSONObject info = (JSONObject) iter.next();
                    long reporterId = (long) info.get("reporterId");
                    long violatorId = (long) info.get("violatorId");
                    String category = (String) info.get("category");
                    String message = (String) info.get("message");
                    Report report = new Report(reporterId, violatorId, category, message);
                    reports.add(report);
                }
                return reports;
            } else {
                return new LinkedList<Report>();
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return new LinkedList<Report>();
    }

    public Collection<Report> getAllReports() {
        try {
            Collection<Report> reports = new LinkedList<>();
            URL url = new URL("http://10.0.2.2:3000/all-reports");
            AccessWebTask task = new AccessWebTask();
            task.execute(url);
            JSONParser parser = new JSONParser();
            JSONObject full = (JSONObject) parser.parse(task.get());
            if (full.get("status").equals("success")) {
                JSONArray infos = (JSONArray) full.get("reports");
                Iterator iter = infos.iterator();
                while (iter.hasNext()) {
                    JSONObject info = (JSONObject) iter.next();
                    long reporterId = (long) info.get("reporterId");
                    long violatorId = (long) info.get("violatorId");
                    String category = (String) info.get("category");
                    String message = (String) info.get("message");
                    Report report = new Report(reporterId, violatorId, category, message);
                    reports.add(report);
                }
                return reports;
            } else {
                return new LinkedList<Report>();
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return new LinkedList<Report>();
    }



}

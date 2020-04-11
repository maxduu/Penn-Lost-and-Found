package edu.upenn.cis350.androidapp.DataInteraction.Processing.UserProcessing;

import java.net.URL;
import java.util.Collection;

import edu.upenn.cis350.androidapp.AccessWebTask;
import edu.upenn.cis350.androidapp.DataInteraction.Data.Ban;
import edu.upenn.cis350.androidapp.DataInteraction.Data.Warning;
import edu.upenn.cis350.androidapp.DataInteraction.Management.UserManagement.BanReader;
import edu.upenn.cis350.androidapp.DataInteraction.Management.UserManagement.WarningReader;

public class WarningProcessor {

    private WarningReader reader;

    private WarningProcessor() {
        this.reader = WarningReader.getInstance();
    }

    private static WarningProcessor instance = new WarningProcessor();

    public static WarningProcessor getInstance() { return instance; }

    // Returns empty collection if no warnings found
    public Collection<Warning> getWarningsOfUser(long id) {
        return reader.getWarningsOfUser(id);
    }

    public void changeWarningsToSeen(long id) {
        try {
            URL url = new URL("http://10.0.2.2:3000/see-warnings?userId=" + id);
            AccessWebTask task = new AccessWebTask();
            task.execute(url);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}

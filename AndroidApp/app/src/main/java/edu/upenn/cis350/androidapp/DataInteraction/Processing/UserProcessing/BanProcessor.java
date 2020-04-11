package edu.upenn.cis350.androidapp.DataInteraction.Processing.UserProcessing;

import edu.upenn.cis350.androidapp.DataInteraction.Data.Ban;
import edu.upenn.cis350.androidapp.DataInteraction.Management.UserManagement.BanReader;

public class BanProcessor {

    private BanReader reader;

    private BanProcessor() {
        this.reader = BanReader.getInstance();
    }

    private static BanProcessor instance = new BanProcessor();

    public static BanProcessor getInstance() { return instance; }

    // Returns null if no ban found
    public Ban getBanOfUser(long userId) {
        return reader.getBanOfUser(userId);
    }

}

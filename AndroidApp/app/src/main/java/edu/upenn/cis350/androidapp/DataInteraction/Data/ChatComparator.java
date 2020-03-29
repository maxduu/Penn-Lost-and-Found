package edu.upenn.cis350.androidapp.DataInteraction.Data;

import java.util.Comparator;
import java.util.Date;

public class ChatComparator implements Comparator<Chat> {

    private ChatComparator() { }

    private static ChatComparator instance = new ChatComparator();

    public static ChatComparator getInstance() { return instance; }

    public int compare(Chat c1, Chat c2) {
        Date date1 = c1.getLastActive();
        Date date2 = c2.getLastActive();
        return date2.compareTo(date1);
    }

}
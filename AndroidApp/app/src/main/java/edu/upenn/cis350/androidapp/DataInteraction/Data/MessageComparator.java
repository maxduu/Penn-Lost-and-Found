package edu.upenn.cis350.androidapp.DataInteraction.Data;

import java.util.Comparator;
import java.util.Date;

public class MessageComparator implements Comparator<Message> {

    private MessageComparator() { }

    private static MessageComparator instance = new MessageComparator();

    public static MessageComparator getInstance() { return instance; }

    public int compare(Message m1, Message m2) {
        Date date1 = m1.getTime();
        Date date2 = m2.getTime();
        return date1.compareTo(date2);
    }
}

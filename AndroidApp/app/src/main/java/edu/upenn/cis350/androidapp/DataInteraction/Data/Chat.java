package edu.upenn.cis350.androidapp.DataInteraction.Data;

import java.util.Date;
import java.util.*;
import android.util.Log;

public class Chat {

    protected long id;
    protected long initiatorId;
    protected long receiverId;
    protected Date lastActive;
    protected String item;
    protected List<Long> messages;

    public Chat(long id, long initiatorId, long receiverId, String item, List<Long> messages,
                Date lastVisited) {
        this.id = id;
        this.initiatorId = initiatorId;
        this.receiverId = receiverId;
        this.item = item;
        this.messages = messages;
        this.lastActive= lastVisited;
    }

    public long getId() { return  id; }

    public long getInitiatorId() { return initiatorId; }

    public long getReceiverId() { return receiverId; }

    public Date getLastActive() { return lastActive; }

    public List<Long> getMessages() { return messages; }

    public String getItem() { return item; }

    public void addMessage(Message message) {
        messages.add(message.getId());
        lastActive = message.getTime();
        Log.d("Chat", "added message " + message.getId() + " to chat " + id);
    }

}


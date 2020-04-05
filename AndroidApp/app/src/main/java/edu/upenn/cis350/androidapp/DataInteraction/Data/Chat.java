package edu.upenn.cis350.androidapp.DataInteraction.Data;

import android.util.Log;

import java.util.Date;
import java.util.List;

public class Chat {

    protected long id;
    protected long itemId;
    protected long initiatorId;
    protected long receiverId;
    protected Date lastActive;
    protected String item;
    protected List<Long> messages;

    public Chat(long id, long initiatorId, long receiverId, String item, List<Long> messages,
                Date lastVisited, long itemId) {
        this.id = id;
        this.initiatorId = initiatorId;
        this.receiverId = receiverId;
        this.item = item;
        this.itemId = itemId;
        this.messages = messages;
        this.lastActive= lastVisited;
    }

    public long getItemId() {
        return itemId;
    }

    public void setItemId(long itemId) {
        this.itemId = itemId;
    }

    public long getId() { return  id; }

    public long getInitiatorId() { return initiatorId; }

    public long getReceiverId() { return receiverId; }

    public Date getLastActive() { return lastActive; }

    public List<Long> getMessages() { return messages; }

    public long getLastMessage() { return messages.get(messages.size()-1); }

    public String getItem() { return item; }

    public void addMessage(Message message) {
        messages.add(message.getId());
        lastActive = message.getTime();
        Log.d("Chat", "added message " + message.getId() + " to chat " + id);
    }

}


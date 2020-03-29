package edu.upenn.cis350.androidapp.DataInteraction.Data;

import java.util.Date;

public class Message {

    protected long id;
    protected long receiverId;
    protected long senderId;
    protected long chatId;
    protected Date time;
    protected String text;

    public Message(long id, long senderId, long receiverId, Date time, String text, long chatId) {

        this.id = id;
        this.time = time;
        this.text = text;
        this.senderId = senderId;
        this.receiverId = receiverId;
        this.chatId = chatId;
    }

    public long getId() { return id; }

    public long getSenderId() { return senderId; }

    public long getReceiverId() { return receiverId; }

    public long getChatId() { return chatId; }

    public Date getTime() { return time; }

    public String getText() { return text; }

}

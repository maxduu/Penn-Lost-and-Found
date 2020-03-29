package edu.upenn.cis350.androidapp.DataInteraction.Processing.MessageProcessing;


import android.util.Log;

import edu.upenn.cis350.androidapp.DataInteraction.Data.MessageComparator;
import edu.upenn.cis350.androidapp.DataInteraction.Management.MessageManagement.MessageJSONReader;
import edu.upenn.cis350.androidapp.DataInteraction.Management.MessageManagement.MessageJSONWriter;
import edu.upenn.cis350.androidapp.DataInteraction.Data.Message;

import java.util.*;

public class MessageProcessor {

    private MessageJSONWriter writer;
    private MessageJSONReader reader;

    private Map<Long, Message> idToMessage;

    private MessageProcessor() {
        writer = MessageJSONWriter.getInstance();
        reader = MessageJSONReader.getInstance();
        idToMessage = reader.getAllMessages();
        Log.d("MessageProcessor", "Number of messages: " + idToMessage.size());
    }

    private static MessageProcessor instance = new MessageProcessor();

    public static MessageProcessor getInstance() { return instance; }

    public int getNumMessages() { return idToMessage.keySet().size(); }

    /**
     * @param messageId The Id of the Message to be obtained
     * @return The Message object corresponding to the input Id
     */
    public Message getMessage(long messageId) { return idToMessage.get(messageId); }

    /**
     * @param messageIds List of ids of the messages to be obtained
     * @return The list of Messages corresponding to the ids
     */
    public List<Message> getMessages(List<Long> messageIds) {
        List<Message> messages = new LinkedList<Message>();
        for (long id : messageIds) {
            messages.add(this.getMessage(id));
        }
        messages.sort(MessageComparator.getInstance());
        return messages;
    }

    /**
     * Registers a newly created Message into the database and saves
     * it as part of the correct Chat
     * @param message The Message object to be registered
     */
    public void registerMessage(Message message) {
        long chatId = message.getChatId();
        ChatProcessor chatProcessor = ChatProcessor.getInstance();
        chatProcessor.addMessageToChat(chatId, message);
        writer.postNewMessage(message);
    }

}




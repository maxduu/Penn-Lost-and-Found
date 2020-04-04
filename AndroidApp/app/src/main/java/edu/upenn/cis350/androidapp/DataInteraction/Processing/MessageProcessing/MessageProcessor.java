package edu.upenn.cis350.androidapp.DataInteraction.Processing.MessageProcessing;


import android.util.Log;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import edu.upenn.cis350.androidapp.DataInteraction.Data.Message;
import edu.upenn.cis350.androidapp.DataInteraction.Data.MessageComparator;
import edu.upenn.cis350.androidapp.DataInteraction.Management.MessageManagement.MessageJSONReader;
import edu.upenn.cis350.androidapp.DataInteraction.Management.MessageManagement.MessageJSONWriter;

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

    public int getNumMessages() { return idToMessage.size(); }

    /**
     * @param messageId The Id of the Message to be obtained
     * @return The Message object corresponding to the input Id
     */
    public Message getMessage(long messageId) {
        idToMessage = reader.getAllMessages();
        return idToMessage.get(messageId);
    }

    /**
     * @param messageIds List of ids of the messages to be obtained
     * @return The list of Messages corresponding to the ids
     */
    public List<Message> getMessages(List<Long> messageIds) {
        idToMessage = reader.getAllMessages();
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
        Log.d("MessageProcessor", "registering message " + message.getText());
        long chatId = message.getChatId();
        ChatProcessor chatProcessor = ChatProcessor.getInstance();
        idToMessage.put(message.getId(), message);
        Log.d("MessageProcessor", "number of messages now is " + idToMessage.size());
        chatProcessor.addMessageToChat(chatId, message);
        writer.postNewMessage(message);
    }

    /**
     * Helper method used to create a new unique id not yet in database.
     * @return an id
     */
    public long findNewId() {
        long id = 1;
        Collection<Long> messageIds = idToMessage.keySet();
        try {
            if (messageIds.size() == 0) {
                return id;
            } else {
                long maxID = -1;
                for (long mId : messageIds) {
                    if (mId > maxID) {
                        maxID = mId;
                    }
                }
                id = maxID + 1;
                return id;
            }
        } catch (Exception e) {
            return 1;
        }
    }

}




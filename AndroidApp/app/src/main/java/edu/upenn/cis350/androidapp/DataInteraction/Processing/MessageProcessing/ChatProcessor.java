package edu.upenn.cis350.androidapp.DataInteraction.Processing.MessageProcessing;

import edu.upenn.cis350.androidapp.DataInteraction.Data.ChatComparator;
import edu.upenn.cis350.androidapp.DataInteraction.Management.MessageManagement.ChatJSONReader;
import edu.upenn.cis350.androidapp.DataInteraction.Management.MessageManagement.ChatJSONWriter;
import edu.upenn.cis350.androidapp.DataInteraction.Data.Chat;
import edu.upenn.cis350.androidapp.DataInteraction.Data.Message;
import java.util.*;
import android.util.Log;

public class ChatProcessor {

    private ChatJSONReader reader;
    private ChatJSONWriter writer;

    private Map<Long, Chat> idToChats;
    private Map<Long, List<Long>> userToChats;

    private ChatProcessor() {
        reader = ChatJSONReader.getInstance();
        writer = ChatJSONWriter.getInstance();
        idToChats = reader.getAllChats();
        userToChats = new HashMap<Long, List<Long>>();
        assignUsersToChats();
    }

    private void update() {
        idToChats = reader.getAllChats();
        userToChats = new HashMap<Long, List<Long>>();
        assignUsersToChats();
    }

    private static ChatProcessor instance = new ChatProcessor();

    public static ChatProcessor getInstance() {return instance; }

    /**
     *
     * @param chatId The Id of the Chat to be obtained
     * @return The Chat object corresponding to the input Id
     */
    public Chat getChat(Long chatId) {
        update();
        return idToChats.get(chatId);
    }

    /**
     * Gets the number of chats. Useful for creating new Chats since their
     * id can be 1 greater than the total number of chats to ensure ids are unique.
     * @return The number of chats registered in the system.
     */
    public int getNumChats() { return idToChats.size(); }


    /**
     * Registers a newly created Chat into the database
     * @param chat The Chat object to be registered
     */
    public void registerChat(Chat chat) {
        long id = chat.getId();
        long recieverId = chat.getReceiverId();
        long initiatorId = chat.getInitiatorId();

        assignUserToChat(recieverId, id);
        assignUserToChat(initiatorId, id);

        if (idToChats.keySet().contains(id)) {
            throw new IllegalArgumentException("Chat id already exists");
        }
        idToChats.put(id, chat);
        writer.postNewChat(chat);
    }

    /**
     * Registers a specific Message in a Chat to the database
     * @param chatId The id of the Chat the Message was in
     * @param message The new Message object to be registered
     */
    public void addMessageToChat(long chatId, Message message) {
        Chat targetChat = idToChats.get(chatId);
        if (!targetChat.getMessages().contains(message.getId())) {
            targetChat.addMessage(message);
            idToChats.put(chatId, targetChat);
            writer.postMessageInChat(chatId, message);
            Log.d("ChatProcessor", "Added text " + message.getText() + " to chat " + chatId);
        }
    }

    /**
     *
     * @param userId The id of the User of interest
     * @return A List of Chats which the User is involved in ordered by
     * decreasing order of last active
     */
    public List<Chat> getChatsForUser(long userId) {
        if (!userToChats.keySet().contains(userId)) {
            return new LinkedList<Chat>();
        }
        List<Long> chatIds = userToChats.get(userId);
        List<Chat> chats = new LinkedList<Chat>();
        for (long id : chatIds) {
            chats.add(idToChats.get(id));
        }
        chats.sort(ChatComparator.getInstance());
        return chats;
    }

    /**
     * Creates a mapping from user Ids to a list of ids of the chats they're in
     * for data already in the database before the app is started (like with testing)
     */
    private void assignUsersToChats() {
        Collection<Chat> chats = idToChats.values();
        for (Chat chat : chats) {
            long receiverId = chat.getReceiverId();
            long initId = chat.getInitiatorId();
            long chatId = chat.getId();

            assignUserToChat(receiverId, chatId);
            assignUserToChat(initId, chatId);
        }
    }

    private void assignUserToChat(long userId, long chatId) {
        Log.d("ChatProcessor", "assigning user " + userId + " to chat " + chatId);
        if (userToChats.keySet().contains(userId)) {
            List<Long> userChats = userToChats.get(userId);
            if (userChats.contains(chatId)) {
                throw new IllegalArgumentException("User in same chatId twice");
            } else {
                userChats.add(chatId);
                userToChats.put(userId, userChats);
            }
        } else {
            List<Long> userChats = new LinkedList<Long>();
            userChats.add(chatId);
            userToChats.put(userId, userChats);
        }
    }

    public long findNewId() {
        long id = 1;
        Collection<Long> chatIds = idToChats.keySet();
        try {
            if (chatIds.size() == 0) {
                return id;
            } else {
                long maxID = -1;
                for (long mId : chatIds) {
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

package edu.upenn.cis350.androidapp.DataInteraction.Management.MessageManagement;

import edu.upenn.cis350.androidapp.AccessWebTask;

import java.net.URL;
import org.json.simple.JSONObject;
import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import android.util.Log;

import edu.upenn.cis350.androidapp.DataInteraction.Data.Chat;

public class ChatJSONReader {

    private final String BASE_URL = "http://10.0.2.2:3000/chat/";

    private ChatJSONReader() {}

    private static ChatJSONReader instance = new ChatJSONReader();

    public static ChatJSONReader getInstance() { return instance; }

    public Map<Long, Chat> getAllChats() {
        Map<Long, Chat> idToChat = new HashMap<Long, Chat>();
        JSONParser parser = new JSONParser();

        try {

            URL url = new URL(BASE_URL+"get-all");
            AccessWebTask task = new AccessWebTask();
            task.execute(url);
            JSONObject allChatsJSON = (JSONObject) parser.parse(task.get());
            JSONArray chatsArr = (JSONArray) allChatsJSON.get("chats");
            Iterator iter = chatsArr.iterator();

            //iterator through each of the chat json objects
            while(iter.hasNext()) {
                JSONObject chatJSON = (JSONObject) iter.next();
                //extract fields from JSON
                long id = (long)chatJSON.get("id");
                long initiatorId = (long)chatJSON.get("initiatorId");
                long receiverId = (long)chatJSON.get("receiverId");
                String item = (String)chatJSON.get("item");
                long itemId = (long)chatJSON.get("itemId");

                Log.d("ChatReader", "Got chat with id " + id +
                                                " initiator id " + initiatorId +
                                                " receiver id " + receiverId +
                                               " about item " + item);

                List<Long> messages = new LinkedList<Long>();
                JSONArray messagesJSON = (JSONArray) chatJSON.get("messages");
                for (int i = 0; i < messagesJSON.size(); i++) {
                    messages.add((long)messagesJSON.get(i));
                }
                Log.d("ChatReader", "Got messages with first id " + messages.get(0));

                String rawDate = (String)chatJSON.get("lastActive");
                Log.d("ChatReader", "Last active: " + rawDate);

                if (rawDate == null) {
                    throw new IllegalStateException("LAST ACTIVE FROM DB SHOULD NOT BE NULL");
                }
                Date lastActive = null;
                SimpleDateFormat dateFormat = new SimpleDateFormat(
                        "yyyy-MM-dd'T'HH:mm:ss.SSSZ");
                try {
                    lastActive = dateFormat.parse(rawDate.replaceAll("Z$", "+0000"));
                } catch (ParseException e) {
                    Log.d("ChatReader", "TIME PARSE ERROR");
                }

                Log.d("ChatReader", "Got date " + lastActive.toString());

                //create Chat object and add to map
                Chat newChat = new Chat(id, initiatorId, receiverId, item, messages, lastActive, itemId);
                idToChat.put(id, newChat);

                Log.d("ChatReader", "Added chat " + id + " to map");
            }
        } catch (Exception e) {
            Log.d("ChatReader", "Exception trying to get chats " + e);
        }

        return idToChat;
    }
}

package edu.upenn.cis350.androidapp.DataInteraction.Management.MessageManagement;


import android.util.Log;

import java.net.URL;
import java.text.ParseException;
import java.util.*;

import java.text.SimpleDateFormat;

import org.json.simple.JSONObject;
import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import edu.upenn.cis350.androidapp.AccessWebTask;
import edu.upenn.cis350.androidapp.DataInteraction.Data.Message;

public class MessageJSONReader {

    private final String BASE_URL = "http://10.0.2.2:3000/message/";

    private MessageJSONReader() { }

    private static MessageJSONReader instance = new MessageJSONReader();

    public static MessageJSONReader getInstance() { return instance; }

    public Map<Long, Message> getAllMessages() {

        Map<Long, Message> idToMessages = new HashMap<Long, Message>();
        JSONParser parser = new JSONParser();

        try {

            URL url = new URL(BASE_URL+"/get-all");
            AccessWebTask task = new AccessWebTask();
            task.execute(url);
            JSONObject allMessagesJSON = (JSONObject) parser.parse(task.get());
            JSONArray messagesArr = (JSONArray) allMessagesJSON.get("messages");
            Iterator iter = messagesArr.iterator();

            while(iter.hasNext()) {
                JSONObject messageJSON = (JSONObject) iter.next();

                Log.d("MessageReader", "messageJSON" + messageJSON.toString());

                //extract fields from JSON
                long id = (long)messageJSON.get("id");
                long senderId = (long)messageJSON.get("senderId");
                long receiverId = (long)messageJSON.get("receiverId");
                String text = (String) messageJSON.get("text");
                long chatId = (long)messageJSON.get("chatId");
                Log.d("MessageReader", "Got messageid:" + id +
                        "senderId: " + senderId + " receiverId: " + receiverId +
                        " chatId: " + chatId + " text: " + text);

                String rawTime = (String) messageJSON.get("time");
                Log.d("MessageReader", "rawTime is " + rawTime);
                Date time = null;
                SimpleDateFormat dateFormat = new SimpleDateFormat(
                        "yyyy-MM-dd'T'HH:mm:ss.SSSZ");
                try {
                    time = dateFormat.parse(rawTime.replaceAll("Z$", "+0000"));
                } catch (ParseException e) {
                    Log.d("MessageReader", "time parse error " + e);
                }
                //create Message object and add to map
                Message newMessage = new Message(id, senderId, receiverId, time, text, chatId);
                idToMessages.put(id, newMessage);

                Log.d("MessageReader", "Added messageid:" + id +
                        "from time: " + time  + " to map");
            }
        } catch (Exception e) {
            Log.d("MessageReader", "json parse error " + e);
        }

        return idToMessages;
    }


}

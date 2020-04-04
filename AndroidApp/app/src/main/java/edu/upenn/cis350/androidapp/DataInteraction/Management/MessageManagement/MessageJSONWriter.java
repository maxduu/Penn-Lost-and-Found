package edu.upenn.cis350.androidapp.DataInteraction.Management.MessageManagement;

import android.util.Log;

import java.net.URL;

import edu.upenn.cis350.androidapp.AccessWebTask;
import edu.upenn.cis350.androidapp.DataInteraction.Data.Message;

public class MessageJSONWriter {

    private final String BASE_URL = "http://10.0.2.2:3000/message/";

    private MessageJSONWriter() { }

    private static MessageJSONWriter instance = new MessageJSONWriter();

    public static MessageJSONWriter getInstance() { return  instance; }


    public void postNewMessage(Message message) {


       try {
            URL url = new URL(BASE_URL + "/post?" +
                    "id=" + message.getId() +
                    "&senderId=" + message.getSenderId() +
                    "&receiverId=" + message.getReceiverId() +
                    "&time=" + message.getTime() +
                    "&text=" + message.getText() +
                    "&chatId=" + message.getChatId());
            AccessWebTask task = new AccessWebTask();
            Log.d("MessageWriter", "chatId is " + message.getChatId());
            task.execute(url);
            Log.d("MessageWriter", "posted new message: " + message.getText());
        } catch (Exception e) {
            Log.d("MessageWriter", "error while posting message: " + e);
        }
    }
}

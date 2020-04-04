package edu.upenn.cis350.androidapp.DataInteraction.Management.MessageManagement;

import android.util.Log;

import java.net.URL;
import java.util.Date;

import edu.upenn.cis350.androidapp.AccessWebTask;
import edu.upenn.cis350.androidapp.DataInteraction.Data.Chat;
import edu.upenn.cis350.androidapp.DataInteraction.Data.Message;

public class ChatJSONWriter {

    private final String BASE_URL =  "http://10.0.2.2:3000/chat/";

    private ChatJSONWriter() { }

    private static ChatJSONWriter instance = new ChatJSONWriter();

    public static ChatJSONWriter getInstance() { return instance; }

    public void postNewChat(Chat chat) {

        try {
            URL url = new URL(BASE_URL + "post?"
                    + "id=" + chat.getId()
                    + "&messages=" + chat.getMessages()
                    + "&lastActive=" + chat.getLastActive()
                    + "&initiatorId=" + chat.getInitiatorId()
                    + "&receiverId=" + chat.getReceiverId()
                    + "&item=" + chat.getItem());
            AccessWebTask task = new AccessWebTask();
            task.execute(url);
            Log.d("ChatWriter", "executed post for chat " + chat.getId());
        } catch (Exception e ) {
            Log.d("ChatWriter", "failed to execute patch request for chat");
            e.printStackTrace();
        }
    }

    public void postMessageInChat(long chatId, Message message) {
        long messageId = message.getId();
        Date lastActive = message.getTime();

        try {
            URL url = new URL(BASE_URL + "update?"
                    + "id=" + chatId
                    + "&message=" + messageId
                    + "&lastActive=" + lastActive);
            AccessWebTask task = new AccessWebTask();
            task.execute(url);
            Log.d("ChatWriter", "executed patch for chat " + chatId);
        } catch (Exception e ) {
            Log.d("ChatWriter", "failed to execute patch request for chat");
            e.printStackTrace();
        }
    }
}

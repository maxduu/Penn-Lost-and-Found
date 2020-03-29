package edu.upenn.cis350.androidapp.DataInteraction.Management.MessageManagement;

import android.util.Log;

import org.json.JSONObject;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import java.util.Date;

import edu.upenn.cis350.androidapp.DataInteraction.Data.Message;
import edu.upenn.cis350.androidapp.DataInteraction.Data.Chat;

public class ChatJSONWriter {

    private final String BASE_URL =  "http://10.0.2.2:3000/message/";

    private ChatJSONWriter() { }

    private static ChatJSONWriter instance = new ChatJSONWriter();

    public static ChatJSONWriter getInstance() { return instance; }

    public void postNewChat(Chat chat) {

        OkHttpClient client = new OkHttpClient();
        MediaType JSON = MediaType.parse("application/json;charset=utf-8");
        JSONObject chatJSON = new JSONObject();

        //add fields to chatJSON
        try {

            chatJSON.put("id", chat.getId());
            chatJSON.put("initiatorId", chat.getInitiatorId());
            chatJSON.put("receiverId", chat.getReceiverId());
            chatJSON.put("item", chat.getItem());
            chatJSON.put("lastActive", chat.getLastActive());
            chatJSON.put("messages", chat.getMessages());

        } catch (Exception e) {
            Log.d("OKHTTP", "failed to add fields to chatJSON");
            e.printStackTrace();
        }

        RequestBody body = RequestBody.create(chatJSON.toString(), JSON);
        Request request = new Request.Builder()
                .url(BASE_URL)
                .post(body)
                .build();

        try {
            Response response = client.newCall(request).execute();
        } catch (Exception e ) {
            Log.d("OKHTTP", "failed to execute post request for chat");
            e.printStackTrace();
        }
    }

    public void postMessageInChat(long chatId, Message message) {
        long messageId = message.getId();
        Date lastActive = message.getTime();

        OkHttpClient client = new OkHttpClient();
        MediaType JSON = MediaType.parse("application/json;charset=utf-8");
        JSONObject patchhJSON = new JSONObject();

        //add fields to patchJSON
        try {
            patchhJSON.put("message", messageId);
            patchhJSON.put("lastActive", lastActive);
        } catch (Exception e) {
            Log.d("OKHTTP", "failed to add fields to patchJSON");
            e.printStackTrace();
        }

        Log.d("ChatWriter", "Patch JSON to update chat is: " + patchhJSON.toString());
        RequestBody body = RequestBody.create(patchhJSON.toString(), JSON);
        Request request = new Request.Builder()
                .url(BASE_URL + chatId)
                .post(body)
                .build();

        try {
            Response response = client.newCall(request).execute();

        } catch (Exception e ) {
            Log.d("OKHTTP", "failed to execute patch request for chat");
            e.printStackTrace();
        }
        Log.d("ChatWriter", "executed patch for chat " + chatId);


    }
}

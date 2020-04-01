package edu.upenn.cis350.androidapp.DataInteraction.Management.MessageManagement;

import android.util.Log;

import org.json.JSONObject;
import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;

import edu.upenn.cis350.androidapp.AccessWebTask;
import edu.upenn.cis350.androidapp.DataInteraction.Data.Message;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class MessageJSONWriter {

    private final String BASE_URL = "http://10.0.2.2:3000/message/";

    private MessageJSONWriter() { }

    private static MessageJSONWriter instance = new MessageJSONWriter();

    public static MessageJSONWriter getInstance() { return  instance; }


    public void postNewMessage(Message message) {

       /* OkHttpClient client = new OkHttpClient();
        MediaType JSON = MediaType.parse("application/json;charset=utf-8");*/
       try {
            URL url = new URL(BASE_URL + "/post?" +
                    "id=" + message.getId() +
                    "&senderId=" + message.getSenderId() +
                    "&receiverId=" + message.getReceiverId() +
                    "&time=" + message.getTime() +
                    "&text=" + message.getText() +
                    "&chatId=" + message.getChatId());
            AccessWebTask task = new AccessWebTask();
            task.execute(url);
            Log.d("MessageWriter", "posted new message: " + message.getText());
        } catch (Exception e) {
            Log.d("MessageWriter", "error while posting message: " + e);
        }

        /*JSONObject messageJSON = new JSONObject();

        //add fields to messageJSON
        try {

            messageJSON.put("id", message.getId());
            messageJSON.put("senderId", message.getSenderId());
            messageJSON.put("receiverId", message.getReceiverId());
            messageJSON.put("time", message.getTime());
            messageJSON.put("text", message.getText());
            messageJSON.put("chatId", message.getChatId());

        } catch (Exception e) {
            Log.d("MessageWriter", "failed to add fields to messageJSON");
            e.printStackTrace();
        }
        Log.d("MessageWriter", "new messageJSON created: " + messageJSON.toString());
        RequestBody body = RequestBody.create(messageJSON.toString(), JSON);
        Request request = new Request.Builder()
                .url(BASE_URL)
                .post(body)
                .build();

        try {
            Response response = client.newCall(request).execute();
        } catch (Exception e ) {
            Log.d("OKHTTP", "failed to execute post request for message");
            e.printStackTrace();
        }
        Log.d("MessageWriter", "executed post for message " + message.getText())*/;

    }
}

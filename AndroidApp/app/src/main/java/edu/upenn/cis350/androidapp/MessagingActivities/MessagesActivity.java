package edu.upenn.cis350.androidapp.MessagingActivities;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import edu.upenn.cis350.androidapp.DataInteraction.Data.Chat;
import edu.upenn.cis350.androidapp.DataInteraction.Data.Message;
import edu.upenn.cis350.androidapp.DataInteraction.Processing.MessageProcessing.ChatProcessor;
import edu.upenn.cis350.androidapp.DataInteraction.Processing.MessageProcessing.MessageProcessor;
import edu.upenn.cis350.androidapp.DataInteraction.Processing.UserProcessing.AccountJSONProcessor;
import edu.upenn.cis350.androidapp.R;


public class MessagesActivity extends AppCompatActivity {

    private MessageProcessor messageProcessor;
    private long userId;
    private long otherUserId;
    private long chatId;
    private ArrayList<String> texts;
    private List<Message> messages;
    private ArrayAdapter textAdapter;
    private MessageAdapter adapter;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_messages);

        messageProcessor = MessageProcessor.getInstance();

        userId = getIntent().getLongExtra("userId", -1);
        chatId = getIntent().getLongExtra("chatId", -1);

        Chat chat = ChatProcessor.getInstance().getChat(chatId);

        Long initiatorId = chat.getInitiatorId();
        Long receiverId = chat.getReceiverId();
        if (initiatorId.equals(userId)) {
            otherUserId = (long)receiverId;
        } else {
            otherUserId = (long)initiatorId;
        }
        String email = AccountJSONProcessor.getInstance().getAccount(otherUserId).getUsername();
        String user = email.substring(0, email.indexOf("@"));
        setTitle(user);
        List<Long> messageIds = chat.getMessages();
        messages = messageProcessor.getMessages(messageIds);

        adapter = new MessageAdapter(this, userId, otherUserId, messages);
        ListView textListView = (ListView) findViewById(R.id.textsListView);
        textListView.setAdapter(adapter);
    }


    public void sendText(View view) {

        /*finish();
        startActivity(getIntent());*/

        EditText edit = (EditText) findViewById(R.id.enterMessage);
        String text = edit.getText().toString();
        Log.d("MessagesActivity", "sentText called in chat " + chatId + " with " +
                "message: " + text);
        if (text.length() > 0) {
            MessageProcessor mp = MessageProcessor.getInstance();

            //create new message
            long senderId = userId;
            long receiverId = otherUserId;
            Date time = new Date();
            long id = mp.findNewId();
            Message message = new Message(id, senderId, receiverId, time, text, chatId);
            //register message
            mp.registerMessage(message);
            adapter.add(message);
           /* messages.add(message);
            texts.add(text);
            textAdapter.notifyDataSetChanged();*/
            edit.getText().clear();
        }
    }

}

package edu.upenn.cis350.androidapp.MessagingActivities;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import edu.upenn.cis350.androidapp.DataInteraction.Processing.MessageProcessing.ChatProcessor;
import edu.upenn.cis350.androidapp.DataInteraction.Processing.MessageProcessing.MessageProcessor;
import edu.upenn.cis350.androidapp.R;
import edu.upenn.cis350.androidapp.DataInteraction.Data.Message;
import edu.upenn.cis350.androidapp.DataInteraction.Data.Chat;

import java.util.*;


public class MessagesActivity extends AppCompatActivity {

    private MessageProcessor messageProcessor;
    private long userId;
    private long otherUserId;
    private long chatId;
    private ArrayList<String> texts;
    private List<Message> messages;
    private ArrayAdapter textAdapter;

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

        List<Long> messageIds = chat.getMessages();
        messages = messageProcessor.getMessages(messageIds);

        texts = new ArrayList<String>();
        for (Message m : messages) {
            texts.add(m.getText());
        }

        textAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, texts);
        ListView textListView = (ListView) findViewById(R.id.textsListView);
        textListView.setAdapter(textAdapter);

        textListView.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        String s = "sent by user "  + messages.get(position).getSenderId();
                        Toast.makeText(MessagesActivity.this, s, Toast.LENGTH_LONG).show();
                    }
                }
        );
    }

    public void sendText(View view) {

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
            int id = mp.getNumMessages() + 1;

            Message message = new Message(id, senderId, receiverId, time, text, chatId);
            mp.registerMessage(message);
            messages.add(message);
            texts.add(text);
            textAdapter.notifyDataSetChanged();
            edit.getText().clear();
        }
    }
}

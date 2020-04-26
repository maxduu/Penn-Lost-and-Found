package edu.upenn.cis350.androidapp.MessagingActivities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import edu.upenn.cis350.androidapp.DataInteraction.Data.Chat;
import edu.upenn.cis350.androidapp.DataInteraction.Data.Message;
import edu.upenn.cis350.androidapp.DataInteraction.Processing.MessageProcessing.ChatProcessor;
import edu.upenn.cis350.androidapp.DataInteraction.Processing.MessageProcessing.MessageProcessor;
import edu.upenn.cis350.androidapp.DataInteraction.Processing.UserProcessing.AccountJSONProcessor;
import edu.upenn.cis350.androidapp.DataInteraction.Processing.UserProcessing.ReportProcessor;
import edu.upenn.cis350.androidapp.MainActivity;
import edu.upenn.cis350.androidapp.R;
import edu.upenn.cis350.androidapp.ReportActivity;


public class MessagesActivity extends AppCompatActivity {

    private MessageProcessor messageProcessor;
    private long userId;
    private long otherUserId;
    private long chatId;
    private ArrayList<String> texts;
    private List<Message> messages;
    private ArrayAdapter textAdapter;
    private MessageAdapter adapter;
    private int lastCount;
    private Handler handler;

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
        textListView.setSelection(adapter.getCount() - 1);
        lastCount = messageIds.size();

        TextView header = (TextView) findViewById(R.id.message_header);
        header.setText(user);

        handler = new Handler();
        handler.removeCallbacksAndMessages(null);
        final int delay = 700; //milliseconds

        handler.postDelayed(new Runnable(){
            public void run(){
                update();
                handler.postDelayed(this, delay);
            }
        }, delay);

    }

    public void update() {
        Chat chat = ChatProcessor.getInstance().getChat(chatId);
        List<Long> messageIds = chat.getMessages();
        if (messageIds.size() != lastCount) {
            messages = messageProcessor.getMessages(messageIds);
            adapter = new MessageAdapter(this, userId, otherUserId, messages);
            ListView textListView = (ListView) findViewById(R.id.textsListView);
            textListView.setAdapter(adapter);
            textListView.setSelection(adapter.getCount() - 1);
            lastCount = messageIds.size();
        }
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

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        this.finish();
    }

    public void onReportClick(View v) {
        if (ReportProcessor.getInstance().existsDuplicateReport(userId, otherUserId, "Chat")) {
            Toast.makeText(this,
                    "You have already reported this user!", Toast.LENGTH_LONG).show();
        } else {
            Intent i = new Intent(this, ReportActivity.class);
            i.putExtra("reporterId", userId);
            i.putExtra("violatorId", otherUserId);
            i.putExtra("category", "Chat");
            startActivity(i);
        }
    }

}

package edu.upenn.cis350.androidapp;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import edu.upenn.cis350.androidapp.DataInteraction.Data.Chat;
import edu.upenn.cis350.androidapp.DataInteraction.Data.Message;
import edu.upenn.cis350.androidapp.DataInteraction.Processing.MessageProcessing.ChatProcessor;
import edu.upenn.cis350.androidapp.DataInteraction.Processing.MessageProcessing.MessageProcessor;

public class StartMessageActivity extends AppCompatActivity {

    private String itemCat;
    private String postDate;
    private long posterId;
    private String purpose;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.send_message_screen);
        itemCat = getIntent().getStringExtra("item");
        postDate = getIntent().getStringExtra("postDate");
        posterId = getIntent().getLongExtra("posterId", -1);
        purpose = getIntent().getStringExtra("purpose");
    }

    public void onSendMessage(View view) {
        ChatProcessor cp = ChatProcessor.getInstance();
        long chatId = cp.findNewId();
        long userId = MainActivity.userId;
        MessageProcessor mp = MessageProcessor.getInstance();
        long messageId = mp.findNewId();
        List<Long> messages = new ArrayList<Long>();
        messages.add(messageId);
        Chat chat = new Chat(chatId, userId, posterId, itemCat, messages,
                new Date());
        cp.registerChat(chat);
        String text = null;
        if (purpose.equals("claim")) {
            text = "Hello, I would like to claim the " + itemCat + " that you posted on " + postDate;
        } else {
            text = "Hello, I believe I found the " + itemCat + " that you posted about on " + postDate;
        }

        Message message = new Message(messageId, userId, posterId, new Date(), text, chatId);
        mp.registerMessage(message);
        Toast.makeText(getApplicationContext(),
                "Successfully messaged user!", Toast.LENGTH_LONG).show();
        finish();
    }

}
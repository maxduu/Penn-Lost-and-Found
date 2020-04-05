package edu.upenn.cis350.androidapp;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
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

public class LostItem2 extends AppCompatActivity {

    private String itemCat;
    private String postDate;
    private long posterId;
    private long itemId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lost_item2);
        itemCat = getIntent().getStringExtra("item");
        postDate = getIntent().getStringExtra("postDate");
        posterId = getIntent().getLongExtra("posterId", -1);
        itemId = getIntent().getLongExtra("itemId", -1);
        TextView lostItemCategory = findViewById(R.id.lostItemCategory);
        lostItemCategory.setText(getIntent().getStringExtra("category"));
        TextView lostItemTime = findViewById(R.id.lostItemTime);
        lostItemTime.setText(getIntent().getStringExtra("time"));
        TextView lostItemAround = findViewById(R.id.lostItemAround);
        lostItemAround.setText(getIntent().getStringExtra("location"));
    }

    public void onLostItemMessageClick (View v) {
        EditText et = findViewById(R.id.lostItemText);
        String input = et.getText().toString();
        long userId = MainActivity.userId;
        ChatProcessor cp = ChatProcessor.getInstance();
        long chatId = cp.findNewId();
        MessageProcessor mp = MessageProcessor.getInstance();
        long messageId = mp.findNewId();
        List<Long> messages = new ArrayList<Long>();
        messages.add(messageId);
        Chat chat = new Chat(chatId, userId, posterId, itemCat, messages,
                new Date(), itemId);
        cp.registerChat(chat);
        Message message = new Message(messageId, userId, posterId, new Date(), input, chatId);
        mp.registerMessage(message);
        Toast.makeText(getApplicationContext(),
                "Successfully messaged user!", Toast.LENGTH_LONG).show();
        finish();
    }

}

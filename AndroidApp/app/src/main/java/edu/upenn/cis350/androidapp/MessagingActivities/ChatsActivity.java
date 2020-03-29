package edu.upenn.cis350.androidapp.MessagingActivities;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.util.Log;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;

import edu.upenn.cis350.androidapp.DataInteraction.Processing.MessageProcessing.ChatProcessor;
import edu.upenn.cis350.androidapp.R;
import edu.upenn.cis350.androidapp.DataInteraction.Data.Chat;


import java.util.*;

public class ChatsActivity extends AppCompatActivity {

    private long userId;
    private List<Chat> chats;
    private ChatProcessor chatProcessor;
    private long[] chatIds;
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chats);
        Log.d("ChatsActivity", "Set content view");

        chatProcessor = ChatProcessor.getInstance();
        Log.d("ChatsActivity", "got processor");

        userId = getIntent().getLongExtra("userId", -1);
        chats = chatProcessor.getChatsForUser(userId);
        chatIds = new long[chats.size()];
        final String[] printed = new String[chats.size()];
        if (chats.size() != 0) {

            for (int i = 0; i < chats.size(); i++) {
                chatIds[i] = chats.get(i).getId();
                printed[i] = Long.toString(chatIds[i]);
                System.out.println(chatIds[i]);
            }
        }

        int[] other = new int[3];

        ListAdapter chatIdAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, printed);

        ListView chatListView = (ListView) findViewById(R.id.chatsListView);

        chatListView.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        long chatId = chatIds[position];
                        Intent i = new Intent(ChatsActivity.this, MessagesActivity.class);
                        i.putExtra("userId", userId);
                        i.putExtra("chatId", chatId);
                        startActivity(i);
                    }
                }
        );
        chatListView.setAdapter(chatIdAdapter);

    }


}

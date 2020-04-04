package edu.upenn.cis350.androidapp.MessagingActivities;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import java.util.List;

import edu.upenn.cis350.androidapp.DataInteraction.Data.Chat;
import edu.upenn.cis350.androidapp.DataInteraction.Processing.MessageProcessing.MessageProcessor;
import edu.upenn.cis350.androidapp.DataInteraction.Processing.UserProcessing.AccountJSONProcessor;
import edu.upenn.cis350.androidapp.R;

public class ChatAdapter extends BaseAdapter {

    List<Chat> chats;
    Context context;
    long userId;

    public ChatAdapter(@NonNull Context context, long userId, List<Chat> chats) {
        this.chats = chats;
        this.context = context;
        this.userId = userId;
    }

    public void add(Chat chat) {
        this.chats.add(chat);
        notifyDataSetChanged();;
    }

    @Override
    public int getCount() { return chats.size(); }

    @Override
    public Chat getItem(int position) { return chats.get(position); }

    @Override
    public long getItemId(int position) { return position; }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (chats.size() != 0) {
            ChatViewHolder holder = new ChatViewHolder();
            LayoutInflater chatInflater = (LayoutInflater)
                    context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            Chat chat = chats.get(position);
            convertView = chatInflater.inflate(R.layout.display_chat, null);

            holder.item = (TextView) convertView.findViewById(R.id.itemTextView);
            holder.lastMessage = (TextView) convertView.findViewById(R.id.messageTextView);
            holder.otherUser = (TextView) convertView.findViewById(R.id.usernameTextView);
            convertView.setTag(holder);

            //set item to display
            String item = chat.getItem().toUpperCase();
            holder.item.setText(item);

            //set username to display
            long otherId = getOtherUser(chat);
            Log.d("ChatAdapter", "user id is " + userId + " other id is " + otherId);
            String email = AccountJSONProcessor.getInstance().getAccount(otherId).getUsername();
            String user = email.substring(0, email.indexOf("@"));
            holder.otherUser.setText(user);

            //set last message to display
            long lastmessageId = chat.getLastMessage();
            String lastMessage = MessageProcessor.getInstance().getMessage(lastmessageId).getText();
            holder.lastMessage.setText(lastMessage);
        }
        return convertView;
    }

    private long getOtherUser(Chat chat) {
        Long initiatorId = chat.getInitiatorId();
        Long receiverId = chat.getReceiverId();
        long otherUserId = 0;
        if (initiatorId.equals(userId)) {
            otherUserId = (long)receiverId;
        } else {
            otherUserId = (long)initiatorId;
        }
        return otherUserId;
    }
}

class ChatViewHolder {
    public TextView item;
    public TextView otherUser;
    public TextView lastMessage;
}

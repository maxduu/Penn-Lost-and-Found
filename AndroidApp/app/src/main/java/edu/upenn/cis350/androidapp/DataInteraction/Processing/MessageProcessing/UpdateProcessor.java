package edu.upenn.cis350.androidapp.DataInteraction.Processing.MessageProcessing;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Handler;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import java.util.LinkedList;
import java.util.List;

import edu.upenn.cis350.androidapp.DataInteraction.Data.Account;
import edu.upenn.cis350.androidapp.DataInteraction.Data.Message;
import edu.upenn.cis350.androidapp.DataInteraction.Data.MessageComparator;
import edu.upenn.cis350.androidapp.DataInteraction.Processing.UserProcessing.AccountJSONProcessor;
import edu.upenn.cis350.androidapp.MessagingActivities.ChatsActivity;
import edu.upenn.cis350.androidapp.MessagingActivities.MessagesActivity;
import edu.upenn.cis350.androidapp.R;

public class UpdateProcessor {

    public static final String CHANNEL_ID = "message";
    private NotificationManagerCompat notificationManagerCompat;
    private static Handler uiHandler = new Handler();
    private static Handler updateHandler;
    private ChatProcessor chatProcessor;
    private MessageProcessor messageProcessor;
    private long userId;
    private final int DELAY;
    private Context context;
    List<Message> userMessages;


    public static Handler getUIHandler() { return uiHandler; }

    private UpdateProcessor() {
        updateHandler = new Handler();
        chatProcessor = ChatProcessor.getInstance();
        messageProcessor = MessageProcessor.getInstance();
        userMessages = new LinkedList<>();
        DELAY = 3000;
    }

    private static UpdateProcessor instance = new UpdateProcessor();

    public static UpdateProcessor getInstance() { return instance; }

    public void setUserId(long id) { userId = id; }

    public void setContext(Context ctx) {
        if (context == null) {
            context = ctx;
            notificationManagerCompat = NotificationManagerCompat.from(context);
        }

    }
    public void startChecking() {
        uiHandler.postDelayed(new Runnable(){
            public void run(){
                update();
                uiHandler.postDelayed(this, DELAY);
            }
        }, DELAY);
    }

    private void update() {
        List<Message> newMessages = messageProcessor.getAllMessagesForUser(userId);
        if (userMessages.size() != 0 && newMessages.size() > userMessages.size()) {
            userMessages = newMessages;
            userMessages.sort(MessageComparator.getInstance());
            Message lastMessage = userMessages.get(userMessages.size() - 1);
            if (lastMessage.getSenderId() != userId) {
                createNotification(lastMessage);
            }
        }
    }

    private void testNotification() {
        Intent i = new Intent(context, ChatsActivity.class);
        i.putExtra("userId", userId);
        PendingIntent contenIntent = PendingIntent.getActivity(context, 0,
                i, 0);
        final int colorPrimary = R.color.colorPrimary;
        Notification notification = new NotificationCompat.Builder(context, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_chat_black_24dp)
                .setContentTitle("Testing")
                .setContentText("this do be the test")
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setContentIntent(contenIntent)
                .setColorized(true)
                .setColor(Color.alpha(colorPrimary))
                .setAutoCancel(false)
                .setOnlyAlertOnce(false)
                .build();
        notificationManagerCompat.notify(1, notification);
    }

    private void createNotification(Message message) {
        Intent messageIntent = new Intent(context, MessagesActivity.class);
        messageIntent.putExtra("userId", userId);
        messageIntent.putExtra("chatId", message.getChatId());
        PendingIntent contenIntent = PendingIntent.getActivity(context, 0,
                messageIntent, 0);

        AccountJSONProcessor accountJSONProcessor = AccountJSONProcessor.getInstance();
        Account sender = accountJSONProcessor.getAccount(message.getId());
        String username = sender.getUsername();
        String text = message.getText();
        final int colorPrimary = R.color.colorPrimary;
        Notification notification = new NotificationCompat.Builder(context, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_chat_black_24dp)
                .setContentTitle(username)
                .setContentText(text)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setContentIntent(contenIntent)
                .setColorized(true)
                .setColor(Color.alpha(colorPrimary))
                .setAutoCancel(false)
                .setOnlyAlertOnce(false)
                .build();
        notificationManagerCompat.notify(1, notification);
    }

}

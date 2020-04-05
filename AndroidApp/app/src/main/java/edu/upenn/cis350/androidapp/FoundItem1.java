package edu.upenn.cis350.androidapp;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.Serializable;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Date;

import edu.upenn.cis350.androidapp.DataInteraction.Data.FoundItem;
import edu.upenn.cis350.androidapp.DataInteraction.Processing.ItemProcessing.FoundJSONProcessor;
import edu.upenn.cis350.androidapp.DataInteraction.Processing.MessageProcessing.ChatProcessor;

public class FoundItem1 extends AppCompatActivity {

    private FoundItem item;
    private String category;
    private String time;
    private String location;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_found_item1);
        long itemId = getIntent().getLongExtra("itemId", -1);
        item = FoundJSONProcessor.getInstance().getFoundItemById(itemId);
        TextView foundItemCategory = findViewById(R.id.foundItemCategory);
        category = item.getCategory();
        foundItemCategory.setText(category);
        TextView foundItemTime = findViewById(R.id.foundItemTime);
        Format f = new SimpleDateFormat("MM/dd/yy");
        String date = f.format(item.getDate());
        time = "Found " + date + " (" + setTime(item.getDate()) + ")";
        foundItemTime.setText(time);
        TextView foundItemAround = findViewById(R.id.foundItemAround);
        location = "Around: " + item.getLocation();
        foundItemAround.setText(location);
    }

    public String setTime (Date old) {
        long diff = new Date().getTime() - old.getTime();
        if (diff < 1000) {
            return "now";
        } else if (diff < 60000) {
            return diff / 1000 + " second(s) ago";
        } else if (diff < 3600000) {
            return diff / 60000 + " minute(s) ago";
        } else if (diff < 86400000 * 2) {
            return diff / 3600000 + " hour(s) ago";
        } else {
            return diff / 86400000 + " day(s) ago";
        }
    }

    public void onYourItemMessageUserToClaimClick(View v){
        if (MainActivity.userId == item.getPosterId()) {
            Toast.makeText(getApplicationContext(),
                    "This is your item!", Toast.LENGTH_LONG).show();
        } else if (ChatProcessor.getInstance().existsItemId(MainActivity.userId, item.getId())) {
            Toast.makeText(getApplicationContext(),
                    "You have already messaged this user!", Toast.LENGTH_LONG).show();
        } else {
            Intent i = new Intent(this, FoundItem2.class);
            i.putExtra("item", item.getCategory());
            i.putExtra("posterId", item.getPosterId());
            i.putExtra("postDate", item.getDate().toString());
            i.putExtra("category", category);
            i.putExtra("time", time);
            i.putExtra("location", location);
            i.putExtra("itemId", item.getId());
            startActivity(i);
        }
    }
}


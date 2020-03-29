package edu.upenn.cis350.androidapp;

import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;

import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;


import android.util.Log;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.*;
import android.content.*;
import android.R.style.*;
import edu.upenn.cis350.androidapp.DataInteraction.Processing.UserProcessing.*;



import java.util.*;
import edu.upenn.cis350.androidapp.ui.main.SectionsPagerAdapter;
import edu.upenn.cis350.androidapp.DataInteraction.Management.ItemManagement.*;
import edu.upenn.cis350.androidapp.MessagingActivities.ChatsActivity;
import edu.upenn.cis350.androidapp.DataInteraction.Data.LostItem;

public class MainActivity extends AppCompatActivity {

    private long userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(this, getSupportFragmentManager());
        ViewPager viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(sectionsPagerAdapter);
        TabLayout tabs = findViewById(R.id.tabs);
        tabs.setupWithViewPager(viewPager);
        FloatingActionButton fab = findViewById(R.id.fab);
        userId = getIntent().getLongExtra("userId", -1);
        Toast.makeText(getApplicationContext(),
                "Login ID: " + userId + " Username: " +
                        getIntent().getStringExtra("username"), Toast.LENGTH_LONG).show();

        createLostItemButtons();

    }

    public void createLostItemButtons() {
        Collection<LostItem> lostItems = LostJSONReader.getInstance().getAllLostItems();
        for (LostItem i : lostItems) {
            LinearLayout s = (LinearLayout) findViewById(R.id.lost_items_list);
            Button b = new Button(this);
            b.setGravity(Gravity.LEFT);

            b.setTransformationMethod(null);
            String output = i.getCategory()+ "\n" + i.getLocation() + "\n" + i.getDate().toString();
            b.setText(output);
            b.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v) {
                    // put code on click operation
                }
            });
            s.addView(b);
        }
    }

    public String setDate(Date date) { return ""; }

    public void onPlusClick(View v){
        Intent i = new Intent(this, NewPostActivity.class);
        i.putExtra("userId", userId);
        startActivity(i);
    }

   public void onMessageClick(View v) {
        Intent i = new Intent(this, ChatsActivity.class);
        i.putExtra("userId", userId);
        startActivity(i);
    }

}

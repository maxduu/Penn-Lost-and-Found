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

import java.util.*;
import edu.upenn.cis350.androidapp.ui.main.SectionsPagerAdapter;
import edu.upenn.cis350.androidapp.DataInteraction.Management.ItemManagement.*;
import edu.upenn.cis350.androidapp.MessagingActivities.ChatsActivity;
import edu.upenn.cis350.androidapp.DataInteraction.Data.LostItem;

public class MainActivity extends AppCompatActivity {

    private long userId;
    ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(this, getSupportFragmentManager());
        viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(sectionsPagerAdapter);
        TabLayout tabs = findViewById(R.id.tabs);
        tabs.setupWithViewPager(viewPager);
        FloatingActionButton fab = findViewById(R.id.fab);
        userId = getIntent().getLongExtra("userId", -1);
        Toast.makeText(getApplicationContext(),
                "Login ID: " + userId + " Username: " +
                        getIntent().getStringExtra("username"), Toast.LENGTH_LONG).show();
    }

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

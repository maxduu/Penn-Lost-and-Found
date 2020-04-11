package edu.upenn.cis350.androidapp;

import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabLayout;

import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;


import android.view.View;
import android.widget.*;
import android.content.*;

import java.util.*;

import edu.upenn.cis350.androidapp.DataInteraction.Processing.UserProcessing.WarningProcessor;
import edu.upenn.cis350.androidapp.ui.main.SectionsPagerAdapter;
import edu.upenn.cis350.androidapp.DataInteraction.Management.ItemManagement.*;
import edu.upenn.cis350.androidapp.MessagingActivities.ChatsActivity;
import edu.upenn.cis350.androidapp.DataInteraction.Data.*;

public class MainActivity extends AppCompatActivity {

    public static long userId;
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
        showWarnings();
//        Toast.makeText(getApplicationContext(),
//                "Login ID: " + userId + " Username: " +
//                        getIntent().getStringExtra("username"), Toast.LENGTH_LONG).show();
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

    private void showWarnings() {
        final WarningProcessor warningProcessor = WarningProcessor.getInstance();
        Collection<Warning> warnings = warningProcessor.getWarningsOfUser(userId);

        String text = "";
        for (Warning warning : warnings) {
            if (!warning.isSeen()) {
                text += "\n- " + warning.getMessage();
            }
        }
        if (!text.isEmpty()) {
            Snackbar snackbar = Snackbar.make(findViewById(R.id.mainlayout),
                    "You have a warning from the admins:\n" + text, Snackbar.LENGTH_INDEFINITE)
                    .setAction("Ok, got it.", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            warningProcessor.changeWarningsToSeen(userId);
                        }
                    });
            View snackbarView = snackbar.getView();
            TextView textView = (TextView) snackbarView.findViewById(com.google.android.material.R.id.snackbar_text);
            textView.setMaxLines(10);  // show multiple line
            snackbar.show();
        }
    }

}

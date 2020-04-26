package edu.upenn.cis350.androidapp;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.LightingColorFilter;
import android.os.Bundle;
import android.os.Handler;
import android.view.ContextThemeWrapper;
import android.view.MenuItem;
import android.view.View;
import android.widget.PopupMenu;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabLayout;

import java.util.Collection;

import edu.upenn.cis350.androidapp.DataInteraction.Data.Warning;
import edu.upenn.cis350.androidapp.DataInteraction.Processing.MessageProcessing.UpdateProcessor;
import edu.upenn.cis350.androidapp.DataInteraction.Processing.UserProcessing.WarningProcessor;
import edu.upenn.cis350.androidapp.MessagingActivities.ChatsActivity;
import edu.upenn.cis350.androidapp.ui.main.SectionsPagerAdapter;

public class MainActivity extends AppCompatActivity implements PopupMenu.OnMenuItemClickListener {

    public static long userId;
    ViewPager viewPager;
    String toAdd;

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

        //Configure handlers
        Handler UIHandler = UpdateProcessor.getUIHandler();
        UIHandler.removeCallbacksAndMessages(null);

        UpdateProcessor updateProcessor = UpdateProcessor.getInstance();
        updateProcessor.setContext(this);
        updateProcessor.startChecking();

    }


    public void onPlusClick(View v){
        Context wrapper = new ContextThemeWrapper(this, R.style.PopupMenu);
        PopupMenu popup = new PopupMenu(wrapper, v);
        popup.setOnMenuItemClickListener(this);
        popup.inflate(R.menu.popup_menu);
        popup.show();
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.lost:
                new AlertDialog.Builder(this)
                        .setTitle("Before posting a lost item...")
                        .setMessage("Remember to check found items first to see if someone has already found your item!")

                        // Specifying a listener allows you to take an action before dismissing the dialog.
                        // The dialog is automatically dismissed when a dialog button is clicked.
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                startLost();
                            }
                        })

                        // A null listener allows the button to dismiss the dialog and take no further action.
                        .setNegativeButton(android.R.string.no, null)
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .show()
                        .getWindow().getDecorView().getBackground().setColorFilter(new LightingColorFilter(0xFF000000, 0xFF019787));
                return true;
            case R.id.found:
                new AlertDialog.Builder(this)
                        .setTitle("Before posting a found item...")
                        .setMessage("Remember to check lost items first to see if someone is already looking for it!")

                        // Specifying a listener allows you to take an action before dismissing the dialog.
                        // The dialog is automatically dismissed when a dialog button is clicked.
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                startFound();
                            }
                        })

                        // A null listener allows the button to dismiss the dialog and take no further action.
                        .setNegativeButton(android.R.string.no, null)
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .show()
                        .getWindow().getDecorView().getBackground().setColorFilter(new LightingColorFilter(0xFF000000, 0xFF019787));
                return true;
            default:
                return false;
        }
    }

    protected void startLost() {
        Intent i = new Intent(this, PostLost1Activity.class);
        i.putExtra("userId", userId);
        startActivity(i);
    }

    protected void startFound() {
        Intent i2 = new Intent(this, PostFoundActivity.class);
        i2.putExtra("userId", userId);
        startActivity(i2);
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

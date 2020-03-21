package edu.upenn.cis350.androidapp;

import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabLayout;

import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;
import android.content.*;

import java.net.URL;

import edu.upenn.cis350.androidapp.ui.main.SectionsPagerAdapter;

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

//        // You could make this the Create Posting button
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent i = new Intent(this, NewPostActivity.class);
//            }
//        });
        userId = getIntent().getLongExtra("userId", -1);
        Toast.makeText(getApplicationContext(),
                "Login ID: " + userId + " Username: " +
                        getIntent().getStringExtra("username"), Toast.LENGTH_LONG).show();

        try {
            URL url = new URL("http://10.0.2.2:3000/all-lost-items");
            HttpGetWebTask task = new HttpGetWebTask();
            task.execute(url);
            System.out.println(task.get());
        } catch (Exception e) {
            System.out.println(e);
        }

    }

    public void onPlusClick(View v){
        Intent i = new Intent(this, NewPostActivity.class);
        i.putExtra("userId", userId);
        startActivity(i);
    }

}
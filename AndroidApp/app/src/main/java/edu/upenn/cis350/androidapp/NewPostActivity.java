package edu.upenn.cis350.androidapp;

import androidx.appcompat.app.AppCompatActivity;
import android.os.*;
import android.view.*;
import android.content.*;

public class NewPostActivity extends AppCompatActivity {

    private long userId;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_post);
        userId = getIntent().getLongExtra("userId", -1);
    }

    public void onLoseClick(View v){
        Intent i = new Intent(this, PostLost1Activity.class);
        i.putExtra("userId", userId);
        startActivity(i);
    }

    public void onFindClick(View v){
        Intent i = new Intent(this, PostFoundActivity.class);
        i.putExtra("userId", userId);
        startActivity(i);
    }

}

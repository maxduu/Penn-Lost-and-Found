package edu.upenn.cis350.androidapp;

import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.widget.TextView;

import java.io.Serializable;
import java.text.Format;
import java.text.SimpleDateFormat;

public class LostItem2 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lost_item2);
        TextView lostItemCategory = findViewById(R.id.lostItemCategory);
        lostItemCategory.setText(getIntent().getStringExtra("category"));
        TextView lostItemTime = findViewById(R.id.lostItemTime);
        lostItemTime.setText(getIntent().getStringExtra("time"));
        TextView lostItemAround = findViewById(R.id.lostItemAround);
        lostItemAround.setText(getIntent().getStringExtra("location"));
    }

    public void onSendMessageClick (View v) {

    }

}

package edu.upenn.cis350.androidapp;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

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

    public void onLostItemMessageClick (View v) {

    }

}

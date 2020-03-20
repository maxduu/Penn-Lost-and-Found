package edu.upenn.cis350.androidapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Date;

public class PostFoundActivity extends AppCompatActivity {

    private long posterId;
    private String category = "";
    private String around = "";

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_found);
        posterId = getIntent().getLongExtra("userId", -1);
    }

    public void onMakeFoundClick(View v) {
        Spinner spinner = (Spinner) findViewById(R.id.found_category_spinner);
        if (spinner.getSelectedItem().toString().equals("Other")) {
            EditText other = (EditText) findViewById(R.id.found_other_category);
            category = other.getText().toString();
        } else {
            category = spinner.getSelectedItem().toString();
        }

        EditText location = (EditText) findViewById(R.id.found_location);
        around = location.getText().toString();

        int id = -1;
        Date date = new Date();
        double latitude = -1;
        double longitude = -1;
        String attachmentLoc = "";

        Intent i = new Intent(this, MainActivity.class);
        i.putExtra("userId", posterId);
        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
                | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(i);
    }

}

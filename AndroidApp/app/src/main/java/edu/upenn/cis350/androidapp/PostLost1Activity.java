package edu.upenn.cis350.androidapp;

import android.os.Bundle;
import android.view.*;
import android.widget.*;
import android.content.*;
import androidx.appcompat.app.AppCompatActivity;

public class PostLost1Activity extends AppCompatActivity {

    private long userId;
    private String category = "";
    private String place = "";

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_lost1);
        userId = getIntent().getLongExtra("userId", -1);
    }

    public void onNextLostClick(View v) {
        Spinner spinner = (Spinner) findViewById(R.id.lost_category_spinner);
        if (spinner.getSelectedItem().toString().equals("Other")) {
            EditText other = (EditText) findViewById(R.id.lost_other_category);
            category = other.getText().toString();
        } else {
            category = spinner.getSelectedItem().toString();
        }

        EditText location = (EditText) findViewById(R.id.lost_location);
        place = location.getText().toString();

        Intent i = new Intent(this, PostLost2Activity.class);
        i.putExtra("posterId", userId);
        i.putExtra("category", category);
        i.putExtra("around", place);
        startActivity(i);
    }

}

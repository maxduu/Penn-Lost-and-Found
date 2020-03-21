package edu.upenn.cis350.androidapp;

import android.os.Bundle;
import android.widget.*;
import java.util.*;
import java.text.SimpleDateFormat;
import java.net.*;
import android.content.*;
import android.view.*;
import org.json.*;
import androidx.appcompat.app.AppCompatActivity;

import edu.upenn.cis350.androidapp.UserProcessing.LostJSONWriter;

public class PostLost2Activity extends AppCompatActivity {

    private long posterId;
    private String category;
    private String around;
    private String description;
    private String additionalInfo;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_lost2);
        posterId = getIntent().getLongExtra("posterId",-1);
        category = getIntent().getStringExtra("category");
        around = getIntent().getStringExtra("around");
    }

    public void onMakeLostClick(View v) {
        //TODO find how to incorporate all of the necessary fields
        EditText describe = (EditText) findViewById(R.id.lost_description);
        description = describe.getText().toString();
        EditText addInfoField = (EditText) findViewById(R.id.lost_addinfo);
        additionalInfo = addInfoField.getText().toString();
        int id = 4;
        Date date = new Date();

        // must have decimals!
        double latitude = -1.0;
        double longitude = -1.0;
        String attachmentLoc = "";

        LostJSONWriter.getInstance().createLostItem(posterId, category, date, latitude, longitude,
                around, attachmentLoc, description, additionalInfo);

        Intent i = new Intent(this, MainActivity.class);
        i.putExtra("userId", posterId);
        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(i);
    }

}

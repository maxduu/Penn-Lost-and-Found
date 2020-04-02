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

import edu.upenn.cis350.androidapp.DataInteraction.Management.ItemManagement.*;

public class PostLost2Activity extends AppCompatActivity {

    private long posterId;
    private String category;
    private String around;
    private String description;
    private String additionalInfo;
    private double lat;
    private double lng;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_lost2);
        posterId = getIntent().getLongExtra("posterId",-1);
        category = getIntent().getStringExtra("category");
        around = getIntent().getStringExtra("around");
        lat = getIntent().getDoubleExtra("latitude", 39.9522);
        lng = getIntent().getDoubleExtra("longitude", -75.1932);
    }

    public void onMakeLostClick(View v) {
        //TODO find how to incorporate all of the necessary fields
        EditText describe = (EditText) findViewById(R.id.lost_description);
        description = describe.getText().toString();
        EditText addInfoField = (EditText) findViewById(R.id.lost_addinfo);
        additionalInfo = addInfoField.getText().toString();
        Date date = new Date();

        // must have decimals!
        String attachmentLoc = "";

        LostJSONWriter.getInstance().createLostItem(posterId, category, date, lat, lng,
                around, attachmentLoc, description, additionalInfo);

        Intent i = new Intent(this, MainActivity.class);
        i.putExtra("userId", posterId);
        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(i);
    }

}

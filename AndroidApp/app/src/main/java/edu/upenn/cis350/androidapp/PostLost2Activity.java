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
        int id = 3;
        Date date = new Date();
        double latitude = -1;
        double longitude = -1;
        String attachmentLoc = "";

        try {
            URL url = new URL("https://10.0.2.2:3000/create-lost-item");
            JSONObject jo = new JSONObject();
            jo.put("id", id);
            jo.put("posterId", posterId);
            jo.put("category", category);
            jo.put("date", date);
            jo.put("latitude", latitude);
            jo.put("longitude", longitude);
            jo.put("around", around);
            jo.put("description", description);
            jo.put("attachmentLoc", attachmentLoc);
            jo.put("additionalInfo", additionalInfo);
            HttpPostWebTask task = new HttpPostWebTask(jo);
            task.execute(url);
        } catch (Exception e) {
            e.printStackTrace();
        }
        Intent i = new Intent(this, MainActivity.class);
        i.putExtra("userId", posterId);
        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
                | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(i);
    }

}

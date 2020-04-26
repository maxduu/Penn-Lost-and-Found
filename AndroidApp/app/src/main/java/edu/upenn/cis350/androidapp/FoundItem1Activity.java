package edu.upenn.cis350.androidapp;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Date;

import edu.upenn.cis350.androidapp.DataInteraction.Data.FoundItem;
import edu.upenn.cis350.androidapp.DataInteraction.Processing.ItemProcessing.FoundJSONProcessor;
import edu.upenn.cis350.androidapp.DataInteraction.Processing.MessageProcessing.ChatProcessor;
import edu.upenn.cis350.androidapp.DataInteraction.Processing.UserProcessing.ReportProcessor;

import com.microsoft.maps.MapRenderMode;
import com.microsoft.maps.MapView;
import com.microsoft.maps.Geopoint;
import com.microsoft.maps.MapAnimationKind;
import com.microsoft.maps.MapScene;
import com.microsoft.maps.MapElementLayer;
import com.microsoft.maps.MapIcon;

public class FoundItem1Activity extends AppCompatActivity {

    private FoundItem item;
    private String category;
    private String time;
    private String location;
    private MapView mMapView;
    private Geopoint coords;
    private MapElementLayer mPinLayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_found_item1);
        long itemId = getIntent().getLongExtra("itemId", -1);
        item = FoundJSONProcessor.getInstance().getFoundItemById(itemId);
        TextView foundItemCategory = findViewById(R.id.foundItemCategory);
        category = item.getCategory();
        foundItemCategory.setText(category);
        TextView foundItemTime = findViewById(R.id.foundItemTime);
        Format f = new SimpleDateFormat("MM/dd/yy");
        String date = f.format(item.getDate());
        time = "Found " + date + " (" + setTime(item.getDate()) + ")";
        foundItemTime.setText(time);
        TextView foundItemAround = findViewById(R.id.foundItemAround);
        location = "Around: " + item.getLocation();
        foundItemAround.setText(location);

        double x = item.getLatitude();
        double y = item.getLongitude();
        coords = new Geopoint(x, y);

        mMapView = new MapView(this, MapRenderMode.VECTOR);  // or use MapRenderMode.RASTER for 2D map
        mMapView.setCredentialsKey("Ary1WE5o87TNqAhNpBqH73ihmzRHvFbHw7JCrw8IDmineqq0ErRrzv3l2FjAiW2a");
        ((FrameLayout)findViewById(R.id.map_view)).addView(mMapView);

        mPinLayer = new MapElementLayer();
        mMapView.getLayers().add(mPinLayer);
        MapIcon pushpin = new MapIcon();
        pushpin.setLocation(coords);
        mPinLayer.getElements().add(pushpin);
    }

    @Override
    protected void onStart() {
        super.onStart();
        mMapView.setScene(
                MapScene.createFromLocationAndZoomLevel(coords, 15),
                MapAnimationKind.NONE);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mMapView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mMapView.onPause();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mMapView.onSaveInstanceState(outState);
    }

    @Override
    protected void onStop() {
        super.onStop();
        mMapView.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //mMapView.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mMapView.onLowMemory();
    }

    public String setTime (Date old) {
        long diff = new Date().getTime() - old.getTime();
        if (diff < 1000) {
            return "now";
        } else if (diff < 60000) {
            return diff / 1000 + " second(s) ago";
        } else if (diff < 3600000) {
            return diff / 60000 + " minute(s) ago";
        } else if (diff < 86400000 * 2) {
            return diff / 3600000 + " hour(s) ago";
        } else {
            return diff / 86400000 + " day(s) ago";
        }
    }

    public void onYourItemMessageUserToClaimClick(View v) {
        if (MainActivity.userId == item.getPosterId()) {
            Toast.makeText(getApplicationContext(),
                    "This is your item!", Toast.LENGTH_LONG).show();
        } else if (ChatProcessor.getInstance().existsItemId(MainActivity.userId, item.getId())) {
            Toast.makeText(getApplicationContext(),
                    "You have already messaged this user!", Toast.LENGTH_LONG).show();
        } else {
            Intent i = new Intent(this, FoundItem2Activity.class);
            i.putExtra("item", item.getCategory());
            i.putExtra("posterId", item.getPosterId());
            i.putExtra("postDate", item.getDate().toString());
            i.putExtra("category", category);
            i.putExtra("time", time);
            i.putExtra("location", location);
            i.putExtra("itemId", item.getId());
            startActivity(i);
        }
    }

    public void onReportClick(View v) {
        if (MainActivity.userId == item.getPosterId()) {
            Toast.makeText(getApplicationContext(),
                    "You are reporting yourself!", Toast.LENGTH_LONG).show();
        } else if (ReportProcessor.getInstance().existsDuplicateReport(MainActivity.userId, item.getPosterId(), "Found Items")) {
            Toast.makeText(getApplicationContext(),
                    "You have already reported this user!", Toast.LENGTH_LONG).show();
        } else {
            Intent i = new Intent(this, ReportActivity.class);
            i.putExtra("reporterId", MainActivity.userId);
            i.putExtra("violatorId", item.getPosterId());
            i.putExtra("category", "Found Items");
            startActivity(i);
        }
    }
}


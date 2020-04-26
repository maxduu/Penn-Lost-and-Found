package edu.upenn.cis350.androidapp;

import android.app.Activity;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.*;
import android.widget.*;
import android.content.*;
import androidx.appcompat.app.AppCompatActivity;
import android.widget.AdapterView.*;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.net.URL;

import edu.upenn.cis350.androidapp.LocationAdapter.PlaceAutoSuggestAdapter;

public class PostLost1Activity extends AppCompatActivity {
    private long userId;
    private String category = "";
    private String place = "";
    private double lat = 39.9522;
    private double lng = -75.1932;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_lost1);
        userId = getIntent().getLongExtra("userId", -1);
        AutoCompleteTextView autoCompleteTextView = findViewById(R.id.lost_location);
        final PlaceAutoSuggestAdapter p = new PlaceAutoSuggestAdapter(PostLost1Activity.this, android.R.layout.simple_list_item_1);
        autoCompleteTextView.setAdapter(p);
        autoCompleteTextView.setOnItemClickListener(new OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long rowId) {
                lat = p.getPlace(position).getLat();
                lng = p.getPlace(position).getLng();
            }
        });
        Spinner spinner = (Spinner) findViewById(R.id.lost_category_spinner);
        spinner.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position,
                                       long id) {
                if (parentView.getItemAtPosition(position).toString().equals("Other")) {
                    ((EditText) findViewById(R.id.lost_other_category)).setVisibility(View.VISIBLE);
                } else {
                    ((EditText) findViewById(R.id.lost_other_category)).setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
            }
        });
    }

    public void onNextLostClick(View v) {
        Spinner spinner = (Spinner) findViewById(R.id.lost_category_spinner);
        if (spinner.getSelectedItem().toString().equals("Other")) {
            EditText other = (EditText) findViewById(R.id.lost_other_category);
            category = other.getText().toString();
        } else {
            category = spinner.getSelectedItem().toString();
        }

        AutoCompleteTextView location = (AutoCompleteTextView) findViewById(R.id.lost_location);
        place = location.getText().toString();

        Intent i = new Intent(this, PostLost2Activity.class);
        i.putExtra("posterId", userId);
        i.putExtra("category", category);
        i.putExtra("around", place);
        i.putExtra("latitude", lat);
        i.putExtra("longitude", lng);
        startActivity(i);
    }

}

package edu.upenn.cis350.androidapp;

import android.os.Bundle;
import android.view.*;
import android.widget.*;
import android.content.*;
import androidx.appcompat.app.AppCompatActivity;
import android.widget.AdapterView.*;

import edu.upenn.cis350.androidapp.LocationAdapter.PlaceAutoSuggestAdapter;

public class PostLost1Activity extends AppCompatActivity {

    private long userId;
    private String category = "";
    private String place = "";

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_lost1);
        userId = getIntent().getLongExtra("userId", -1);
        AutoCompleteTextView autoCompleteTextView = findViewById(R.id.lost_location);
        autoCompleteTextView.setAdapter(new PlaceAutoSuggestAdapter(PostLost1Activity.this, android.R.layout.simple_list_item_1));
        Spinner spinner = (Spinner) findViewById(R.id.lost_category_spinner);
        spinner.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position,
                                       long id) {
                if (parentView.getItemAtPosition(position).toString().equals("Other")) {
                    ((TextView) findViewById(R.id.lost_not_category)).setVisibility(View.VISIBLE);
                    ((EditText) findViewById(R.id.lost_other_category)).setVisibility(View.VISIBLE);
                } else {
                    ((TextView) findViewById(R.id.lost_not_category)).setVisibility(View.INVISIBLE);
                    ((EditText) findViewById(R.id.lost_other_category)).setVisibility(View.INVISIBLE);
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parentView){}
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

        EditText location = (EditText) findViewById(R.id.lost_location);
        place = location.getText().toString();

        Intent i = new Intent(this, PostLost2Activity.class);
        i.putExtra("posterId", userId);
        i.putExtra("category", category);
        i.putExtra("around", place);
        startActivity(i);
    }

}

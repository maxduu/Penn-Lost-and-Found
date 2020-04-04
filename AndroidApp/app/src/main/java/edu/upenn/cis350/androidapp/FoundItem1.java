package edu.upenn.cis350.androidapp;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.widget.Button;

import edu.upenn.cis350.androidapp.DataInteraction.Data.FoundItem;
import edu.upenn.cis350.androidapp.DataInteraction.Processing.ItemProcessing.FoundJSONProcessor;

public class FoundItem1 extends AppCompatActivity {

    private FoundItem item;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_found_item1);
        long itemId = getIntent().getLongExtra("itemId", -1);
        item = FoundJSONProcessor.getInstance().getFoundItemById(itemId);
    }

    public void onYourItemMessageUserToClaimClick(View v){
        Intent i = new Intent(this, FoundItem2.class);
        i.putExtra("item", item.getCategory());
        i.putExtra("posterId", item.getPosterId());
        i.putExtra("postDate", item.getDate().toString());
        startActivity(i);
    }
}


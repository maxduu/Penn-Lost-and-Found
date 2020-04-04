package edu.upenn.cis350.androidapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import edu.upenn.cis350.androidapp.DataInteraction.Data.LostItem;
import edu.upenn.cis350.androidapp.DataInteraction.Processing.ItemProcessing.LostJSONProcessor;

public class LostItem1 extends AppCompatActivity {

    private LostItem item;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lost_item1);
        long itemId = getIntent().getLongExtra("itemId", -1);
        item = LostJSONProcessor.getInstance().getLostItemById(itemId);
    }

    public void onFoundItemMessageUserClick(View v){
        if (item.getPosterId() == MainActivity.userId) {
            Toast.makeText(getApplicationContext(),
                    "This was posted by you.", Toast.LENGTH_LONG).show();
        } else {
            Intent i = new Intent(this, StartMessageActivity.class);
            i.putExtra("item", item.getCategory());
            i.putExtra("posterId", item.getPosterId());
            i.putExtra("postDate", item.getDate().toString());
            i.putExtra("purpose", "deliver");
            startActivity(i);
        }
    }
}

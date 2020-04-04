package edu.upenn.cis350.androidapp.ui.main;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import java.util.Collection;
import java.util.Date;

import edu.upenn.cis350.androidapp.DataInteraction.Data.FoundItem;
import edu.upenn.cis350.androidapp.DataInteraction.Data.LostItem;
import edu.upenn.cis350.androidapp.DataInteraction.Management.ItemManagement.FoundJSONReader;
import edu.upenn.cis350.androidapp.DataInteraction.Management.ItemManagement.LostJSONReader;
import edu.upenn.cis350.androidapp.FoundItem1;
import edu.upenn.cis350.androidapp.LostItem1;
import edu.upenn.cis350.androidapp.R;

/**
 * A placeholder fragment containing a simple view.
 */
public class PlaceholderFragment extends Fragment {

    private static final String ARG_SECTION_NUMBER = "section_number";
    private int index = 1;

    private PageViewModel pageViewModel;

    public static PlaceholderFragment newInstance(int index) {
        PlaceholderFragment fragment = new PlaceholderFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(ARG_SECTION_NUMBER, index);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        pageViewModel = ViewModelProviders.of(this).get(PageViewModel.class);
        if (getArguments() != null) {
            index = getArguments().getInt(ARG_SECTION_NUMBER);
        }
        pageViewModel.setIndex(index);
    }

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        LinearLayout items_list = view.findViewById(R.id.items_list);
        if (index == 1) {
            Collection<LostItem> lostItems = LostJSONReader.getInstance().getAllLostItems();
            if (lostItems.isEmpty()) {
                TextView t = new TextView(items_list.getContext());
                t.setGravity(Gravity.CENTER);
                t.setTextColor(Color.GRAY);
                t.setText("Looks like no one has lost anything recently :)");
                items_list.addView(t);
            } else {
                for (LostItem i : lostItems) {
                    Button b = new Button(items_list.getContext());
                    b.setId((int)i.getId());
                    b.setGravity(Gravity.LEFT);
                    int color = Color.parseColor("#8BF44336");
                    b.getBackground().mutate().setColorFilter(new PorterDuffColorFilter(color, PorterDuff.Mode.SRC));
                    b.setTransformationMethod(null);
                    String output = i.getCategory() + "\n" + i.getLocation() + "\nLost " + setTime(i.getDate());
                    b.setText(output);
                    b.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent i = new Intent(v.getContext(), LostItem1.class);
                            i.putExtra("itemId", (long) v.getId());
                            startActivity(i);
                        }
                    });
                    items_list.addView(b);
                }
            }
        } else if (index == 2) {
            Collection<FoundItem> foundItems = FoundJSONReader.getInstance().getAllFoundItems();
            if (foundItems.isEmpty()) {
                TextView t = new TextView(items_list.getContext());
                t.setGravity(Gravity.CENTER);
                t.setTextColor(Color.GRAY);
                t.setText("Sorry, no items found yet :(");
                items_list.addView(t);
            } else {
                for (FoundItem i : foundItems) {
                    Button b = new Button(items_list.getContext());
                    b.setId((int)i.getId());
                    b.setGravity(Gravity.LEFT);
                    int color = Color.parseColor("#6B0347F4");
                    b.getBackground().mutate().setColorFilter(new PorterDuffColorFilter(color, PorterDuff.Mode.SRC));
                    b.setTransformationMethod(null);
                    String output = i.getCategory() + "\n" + i.getLocation() + "\nFound " + setTime(i.getDate());
                    b.setText(output);
                    b.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent i = new Intent(v.getContext(), FoundItem1.class);
                            i.putExtra("itemId", (long) v.getId());
                            startActivity(i);
                        }
                    });
                    items_list.addView(b);
                }
            }
        }
        return view;
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

}
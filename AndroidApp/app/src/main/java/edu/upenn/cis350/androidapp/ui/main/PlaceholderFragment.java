package edu.upenn.cis350.androidapp.ui.main;

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
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;

import edu.upenn.cis350.androidapp.DataInteraction.Data.FoundItem;
import edu.upenn.cis350.androidapp.DataInteraction.Data.LostItem;
import edu.upenn.cis350.androidapp.DataInteraction.Management.ItemManagement.FoundJSONReader;
import edu.upenn.cis350.androidapp.DataInteraction.Management.ItemManagement.LostJSONReader;
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
                    b.setGravity(Gravity.LEFT);
                    int color = Color.parseColor("#8BF44336");
                    b.getBackground().mutate().setColorFilter(new PorterDuffColorFilter(color, PorterDuff.Mode.SRC));
                    b.setTransformationMethod(null);
                    String output = i.getCategory() + "\n" + i.getLocation() + "\nLost " + setDate(i.getDate());
                    b.setText(output);
                    b.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            // put code on click operation
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
                    b.setGravity(Gravity.LEFT);
                    int color = Color.parseColor("#6B0347F4");
                    b.getBackground().mutate().setColorFilter(new PorterDuffColorFilter(color, PorterDuff.Mode.SRC));
                    b.setTransformationMethod(null);
                    String output = i.getCategory() + "\n" + i.getLocation() + "\nFound " + setDate(i.getDate());
                    b.setText(output);
                    b.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            // put code on click operation
                        }
                    });
                    items_list.addView(b);
                }
            }
        }
        return view;
    }

    public String setDate (Date old) {
        long diff = System.currentTimeMillis() - old.getTime() + 7 * 3600 * 1000;
        if (diff < 1000) {
            return "now";
        } else if (diff < 60 * 1000) {
            return diff / 1000 + " second(s) ago";
        } else if (diff < 3600 * 1000) {
            return diff / (60 * 1000) + " minute(s) ago";
        } else if (diff < 216000 * 1000) {
            return diff / (3600 * 1000) + " hour(s) ago";
        } else {
            return diff / (216000 * 1000) + " day(s) ago";
        }
    }

}
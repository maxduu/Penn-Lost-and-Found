package edu.upenn.cis350.androidapp.ui.main;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.LightingColorFilter;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.RelativeSizeSpan;
import android.text.style.StyleSpan;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import edu.upenn.cis350.androidapp.DataInteraction.Data.FoundItem;
import edu.upenn.cis350.androidapp.DataInteraction.Data.LostItem;
import edu.upenn.cis350.androidapp.DataInteraction.Management.ItemManagement.FoundJSONReader;
import edu.upenn.cis350.androidapp.DataInteraction.Management.ItemManagement.FoundJSONWriter;
import edu.upenn.cis350.androidapp.DataInteraction.Management.ItemManagement.LostJSONReader;
import edu.upenn.cis350.androidapp.DataInteraction.Management.ItemManagement.LostJSONWriter;
import edu.upenn.cis350.androidapp.FoundItem1Activity;
import edu.upenn.cis350.androidapp.LoginActivity;
import edu.upenn.cis350.androidapp.LostItem1Activity;
import edu.upenn.cis350.androidapp.MainActivity;
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
        final View view = inflater.inflate(R.layout.fragment_main, container, false);
        final SwipeRefreshLayout pullToRefresh = view.findViewById(R.id.pullToRefresh);
        pullToRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                LinearLayout items_list = view.findViewById(R.id.items_list);
                items_list.removeAllViews();
                createItems(view);
                pullToRefresh.setRefreshing(false);
            }
        });
        return createItems(view);
    }

    private View createItems (final View view) {
        final LinearLayout items_list = view.findViewById(R.id.items_list);
        if (index == 1) {
            Collection<LostItem> lostItemsTemp = LostJSONReader.getInstance().getAllLostItems();
            List <LostItem> lostItems = new ArrayList(lostItemsTemp);
            Collections.sort(lostItems, new Comparator<LostItem>() {
                public int compare(LostItem item, LostItem item1) {
                    return Long.compare(item.getDate().getTime(), item1.getDate().getTime());
                }
            });
            Collections.reverse(lostItems);
            if (lostItems.isEmpty()) {
                TextView t = new TextView(items_list.getContext());
                t.setGravity(Gravity.CENTER);
                t.setTextColor(Color.GRAY);
                t.setText("Looks like no one has lost anything recently :)");
                items_list.addView(t);
            } else {
                for (final LostItem i : lostItems) {
                    final CoordinatorLayout l = new CoordinatorLayout(items_list.getContext());
                    Button b = new Button(items_list.getContext());
                    b.setId((int)i.getId());
                    b.setGravity(Gravity.LEFT);
                    int color = Color.parseColor("#8BF44336");
                    //#FFFF9800
                    b.getBackground().mutate().setColorFilter(new PorterDuffColorFilter(color, PorterDuff.Mode.SRC));
                    b.setTransformationMethod(null);
                    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                            LinearLayout.LayoutParams.MATCH_PARENT,
                            LinearLayout.LayoutParams.WRAP_CONTENT
                    );
                    params.setMargins(0, -15, 0, -12);
                    b.setLayoutParams(params);
                    Spannable output = new SpannableString(i.getCategory() + "\n" + i.getLocation() + "\nLost " + setTime(i.getDate()));
                    output.setSpan(new RelativeSizeSpan(1.7f), 0, i.getCategory().length(),Spannable.SPAN_INCLUSIVE_INCLUSIVE);
                    output.setSpan(new StyleSpan(Typeface.ITALIC), i.getCategory().length(), output.length(),Spannable.SPAN_INCLUSIVE_INCLUSIVE);
                    //String output = i.getCategory() + "\n" + i.getLocation() + "\nLost " + setTime(i.getDate());
                    b.setText(output);
                    b.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent i = new Intent(v.getContext(), LostItem1Activity.class);
                            i.putExtra("itemId", (long) v.getId());
                            startActivity(i);
                        }
                    });
                    l.addView(b);
                    if (MainActivity.userId == i.getPosterId()) {
                        final FloatingActionButton fab = new FloatingActionButton(items_list.getContext());
                        params.setMargins(900, 22, 0, 0);
                        fab.setLayoutParams(params);
                        fab.setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.delete));
                        fab.setBackgroundTintList(ColorStateList.valueOf(Color.RED));
                        fab.setOnClickListener (new View.OnClickListener() {
                            @Override
                            public void onClick(final View v) {
                                new AlertDialog.Builder(v.getContext())
                                        .setTitle("Delete Lost Item Post")
                                        .setMessage("Are you sure you want to permanently remove this post?")
                                        .setIcon(android.R.drawable.ic_dialog_alert)
                                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int whichButton) {
                                                LostJSONWriter.getInstance().removeLostItemById(i.getId());
                                                LinearLayout items_list = view.findViewById(R.id.items_list);
                                                items_list.removeAllViews();
                                                createItems(view);
                                            }
                                        })
                                        .setNegativeButton(android.R.string.no, null)
                                        .show()
                                        .getWindow().getDecorView().getBackground().setColorFilter(new LightingColorFilter(0xFF000000, 0xFF019787));
                            }
                        });
                        l.addView(fab);
                    }
                    items_list.addView(l);
                }
            }
        } else if (index == 2) {
            Collection<FoundItem> foundItemsTemp = FoundJSONReader.getInstance().getAllFoundItems();
            List<FoundItem> foundItems = new ArrayList(foundItemsTemp);
            Collections.sort(foundItems, new Comparator<FoundItem>() {
                public int compare(FoundItem item, FoundItem item1) {
                    return Long.compare(item.getDate().getTime(), item1.getDate().getTime());
                }
            });
            Collections.reverse(foundItems);
            if (foundItems.isEmpty()) {
                TextView t = new TextView(items_list.getContext());
                t.setGravity(Gravity.CENTER);
                t.setTextColor(Color.GRAY);
                t.setText("Sorry, no items found yet :(");
                items_list.addView(t);
            } else {
                for (final FoundItem i : foundItems) {
                    final CoordinatorLayout l = new CoordinatorLayout(items_list.getContext());
                    Button b = new Button(items_list.getContext());
                    b.setId((int)i.getId());
                    b.setGravity(Gravity.LEFT);
                    int color = Color.parseColor("#6B0347F4");
                    b.getBackground().mutate().setColorFilter(new PorterDuffColorFilter(color, PorterDuff.Mode.SRC));
                    b.setTransformationMethod(null);
                    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                            LinearLayout.LayoutParams.MATCH_PARENT,
                            LinearLayout.LayoutParams.WRAP_CONTENT
                    );
                    params.setMargins(0, -15, 0, -12);
                    b.setLayoutParams(params);
                    //String output = i.getCategory() + "\n" + i.getLocation() + "\nFound " + setTime(i.getDate());
                    Spannable output = new SpannableString(i.getCategory() + "\n" + i.getLocation() + "\nFound " + setTime(i.getDate()));
                    output.setSpan(new RelativeSizeSpan(1.7f), 0, i.getCategory().length(),Spannable.SPAN_INCLUSIVE_INCLUSIVE);
                    output.setSpan(new StyleSpan(Typeface.ITALIC), i.getCategory().length(), output.length(),Spannable.SPAN_INCLUSIVE_INCLUSIVE);
                    b.setText(output);
                    b.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent i = new Intent(v.getContext(), FoundItem1Activity.class);
                            i.putExtra("itemId", (long) v.getId());
                            startActivity(i);
                        }
                    });
                    l.addView(b);
                    if (MainActivity.userId == i.getPosterId()) {
                        final FloatingActionButton fab = new FloatingActionButton(items_list.getContext());
                        params.setMargins(900, 22, 0, 0);
                        fab.setLayoutParams(params);
                        fab.setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.delete));
                        fab.setBackgroundTintList(ColorStateList.valueOf(Color.RED));
                        fab.setOnClickListener (new View.OnClickListener() {
                            @Override
                            public void onClick(final View v) {
                                new AlertDialog.Builder(v.getContext())
                                        .setTitle("Delete Found Item Post")
                                        .setMessage("Are you sure you want to permanently remove this post?")
                                        .setIcon(android.R.drawable.ic_dialog_alert)
                                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int whichButton) {
                                                FoundJSONWriter.getInstance().removeFoundItemById(i.getId());
                                                LinearLayout items_list = view.findViewById(R.id.items_list);
                                                items_list.removeAllViews();
                                                createItems(view);
                                            }
                                        })
                                        .setNegativeButton(android.R.string.no, null)
                                        .show()
                                        .getWindow().getDecorView().getBackground().setColorFilter(new LightingColorFilter(0xFF000000, 0xFF019787));
                            }
                        });
                        l.addView(fab);
                    }
                    items_list.addView(l);
                }
            }
        }
        return view;
    }

    private String setTime (Date old) {
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
package edu.upenn.cis350.androidapp.LocationAdapter;

import android.content.Context;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.Filterable;

import androidx.annotation.NonNull;

import org.jetbrains.annotations.NotNull;

import java.util.*;
import edu.upenn.cis350.androidapp.LocationAdapter.Models.*;

public class PlaceAutoSuggestAdapter extends ArrayAdapter implements Filterable {

    ArrayList<String> results;
    Context context;
    PlaceAPI api = new PlaceAPI();
    int resource;

    public PlaceAutoSuggestAdapter(Context context, int resId) {
        super(context, resId);
        this.context = context;
        this.resource = resId;
    }

    @Override
    public int getCount(){
        return results.size();
    }

    public String getItem(int pos){
        return results.get(pos);
    }

    private ArrayList<String> toStringArr(List<Place> l) {
        ArrayList<String> arr = new ArrayList<String>();
        for (Place p : l) {
            arr.add(p.getName());
            if (arr.size() > 4) {
                break;
            }
        }
        return arr;
    }

    @Override
    public Filter getFilter() {
        Filter filter = new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                FilterResults filterResults = new FilterResults();
                if (constraint != null) {
                    results = toStringArr(api.autoComplete(constraint.toString()));
                    filterResults.values = results;
                    filterResults.count = results.size();
                }
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                if (results != null && results.count > 0) {
                    notifyDataSetChanged();
                } else {
                    notifyDataSetInvalidated();
                }
            }
        };

        return filter;
    }

}

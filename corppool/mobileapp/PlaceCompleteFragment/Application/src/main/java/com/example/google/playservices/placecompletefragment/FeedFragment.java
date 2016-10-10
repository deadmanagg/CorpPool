package com.example.google.playservices.placecompletefragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.example.corppool.android.custom.adapter.FeedListAdapter;
import com.example.corppool.model.Feed;
import com.example.corppool.model.Location;
import com.example.google.playservices.placecompletefragment.dummy.DummyContent;
import com.example.google.playservices.placecompletefragment.dummy.DummyContent.DummyItem;

import java.util.List;

/**
 * A fragment representing a list of Items.
 * <p/>
  */
public class FeedFragment extends ListFragment  {

    // TODO: Customize parameter argument names
    private static final String ARG_COLUMN_COUNT = "column-count";
    // TODO: Customize parameters
    private int mColumnCount = 1;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        
       // View view = inflater.inflate(R.layout.fragment_feed_list, container, false);
        View view = inflater.inflate(R.layout.fragment_feed, container, false);
        return view;
    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Feed[] feed = new Feed[2];
        Location startLoc  = new Location();
        startLoc.set_lat(12);
        startLoc.set_long(12);

        feed[0].setStartLoc(startLoc);
        Location endLoc  = new Location();
        startLoc.set_lat(12);
        startLoc.set_long(12);

        feed[0].setStartLoc(endLoc);

        ArrayAdapter adapter = new FeedListAdapter(this.getContext(),feed);
        setListAdapter(adapter);

    }
}

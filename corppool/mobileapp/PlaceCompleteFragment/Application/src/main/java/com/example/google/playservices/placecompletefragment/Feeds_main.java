package com.example.google.playservices.placecompletefragment;

import android.app.ListActivity;
import android.app.ListFragment;
import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.widget.ListView;
import android.widget.TextView;

import com.example.corppool.android.custom.adapter.FeedListAdapter;
import com.example.corppool.model.Feed;
import com.example.corppool.model.Location;
import com.google.android.gms.location.places.Place;

public class Feeds_main extends ListActivity {

    private TextView reqStartLoc;
    private TextView reqEndLoc;

    private TextView reqStartDate;
    private TextView reqStartTime;
    private ListView feedList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feeds_main);

        reqStartLoc = (TextView) findViewById(R.id.reqStartLoc);
        reqEndLoc = (TextView) findViewById(R.id.reqEndLoc);
        reqStartDate = (TextView) findViewById(R.id.reqStartDate);
        reqStartTime = (TextView) findViewById(R.id.reqStartTime);

        //now set all parameters based on main activity content
        loadData();

        //here call Http Get to get all feeds
        loadFeeds();
    }

    private void loadData(){

        Intent i = getIntent();

        //populate values from the request
        reqStartLoc.setText(i.getStringExtra("reqStartLoc"));
        reqEndLoc.setText(i.getStringExtra("reqEndLoc"));

        reqStartDate.setText(i.getStringExtra("date"));
        reqStartTime.setText(i.getStringExtra("time"));

    }

    private void loadFeeds(){

        Feed[] feed = new Feed[6];
        feed[0] = new Feed("Deepansh","FirstAndroidApp","Hopking rd.","Exploration lane","0.3","5");
        feed[1] = new Feed("Yagya","FirstAndroidApp","Hopking rd.","Exploration lane","0.5","10");
        feed[2] = new Feed("Sanjeev","FirstAndroidApp","Waterfold.","Exploration lane","1","15");
        feed[3] = new Feed("Pravesh","FirstAndroidApp","Rolling hills","MiddleBrook road","2","20");
        feed[4] = new Feed("Kapil","FirstAndroidApp","Waterfold.","Exploration lane","1.5","40");
        feed[5] = new Feed("Kapil","FirstAndroidApp","Waterfold.","Exploration lane","1.5","40");


        feedList =(ListView) findViewById(android.R.id.list);
        FeedListAdapter adapter = new FeedListAdapter(this,
                feed);

        feedList.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }


}

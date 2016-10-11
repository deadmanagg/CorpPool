package com.example.google.playservices.placecompletefragment;

import android.app.ListActivity;
import android.app.ListFragment;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.widget.ListView;
import android.widget.TextView;

import com.example.corppool.android.custom.adapter.FeedListAdapter;
import com.example.corppool.model.Feed;
import com.example.corppool.model.Location;
import com.example.corppool.server.ServerInterface;
import com.google.android.gms.location.places.Place;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Feeds_main extends ListActivity {

    private TextView reqStartLoc;
    private TextView reqEndLoc;

    private TextView reqStartDate;
    private TextView reqStartTime;
    private ListView feedList;

    private Feed reqStartFeed = new Feed();

    private List<Feed> feeds = new ArrayList<Feed>();
    private FeedListAdapter adapter;
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

        //populate pojo and use that to populate futher
        reqStartFeed.setStartAddress(i.getStringExtra("reqStartLoc"));
        reqStartFeed.setEndAddress(i.getStringExtra("reqEndLoc"));
        reqStartFeed.setDate(i.getStringExtra("date"));
        reqStartFeed.setTime(i.getStringExtra("time"));

        Location sloc = new Location();
        Location eloc = new Location();
        reqStartFeed.setStartLoc(sloc);
        reqStartFeed.setEndLoc(eloc);

        sloc.setType("Point");
        sloc.set_lat(i.getDoubleExtra("reqStartLat",0));
        sloc.set_long(i.getDoubleExtra("reqStartLong",0));

        eloc.setType("Point");
        eloc.set_lat(i.getDoubleExtra("reqEndLat",0));
        eloc.set_long(i.getDoubleExtra("reqEndLong",0));

        //populate values from the request
        reqStartLoc.setText(reqStartFeed.getStartAddress());
        reqEndLoc.setText(reqStartFeed.getEndAddress());

        reqStartDate.setText(reqStartFeed.getDate());
        reqStartTime.setText(reqStartFeed.getTime());

    }

    private void loadFeeds(){

        /*Feed[] feed = new Feed[6];
        feed[0] = new Feed("Deepansh","FirstAndroidApp","Hopking rd.","Exploration lane","0.3","5");
        feed[1] = new Feed("Yagya","FirstAndroidApp","Hopking rd.","Exploration lane","0.5","10");
        feed[2] = new Feed("Sanjeev","FirstAndroidApp","Waterfold.","Exploration lane","1","15");
        feed[3] = new Feed("Pravesh","FirstAndroidApp","Rolling hills","MiddleBrook road","2","20");
        feed[4] = new Feed("Kapil","FirstAndroidApp","Waterfold.","Exploration lane","1.5","40");
        feed[5] = new Feed("Kapil","FirstAndroidApp","Waterfold.","Exploration lane","1.5","40");
*/

        new GetFeeds().execute();
        feedList =(ListView) findViewById(android.R.id.list);
        adapter= new FeedListAdapter(this,
                feeds,reqStartFeed);

        feedList.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    @Override
    public  void onPause(){
        super.onPause();

        //go back to main activity
        Intent in = new Intent(Feeds_main.this,MainActivity.class);
        startActivity(in);
        //finish();
    }


    private class GetFeeds extends AsyncTask<String, String, JSONArray> {
        private ProgressDialog pDialog;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        @Override
        protected JSONArray doInBackground(String... args) {

            // Getting JSON from URL
            String jsonStr = ServerInterface.GETFeeds(reqStartFeed);
            JSONArray jsonArr = new JSONArray();
                    try {
                        jsonArr = ServerInterface.convertResponseToJSon(jsonStr);
                    }catch(JSONException e){
                        e.printStackTrace();
                    }
            return jsonArr;
        }
        @Override
        protected void onPostExecute(JSONArray json) {

            try {
                for(int i=0;i<json.length();i++){
                    JSONObject js = json.getJSONObject(i);
                    feeds.add(Feed.marshalToFeed(js));
                }
                adapter.notifyDataSetChanged();
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    }

}

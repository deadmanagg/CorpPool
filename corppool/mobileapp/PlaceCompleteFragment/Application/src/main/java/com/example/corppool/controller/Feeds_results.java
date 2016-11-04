package com.example.corppool.controller;

import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.example.corppool.android.custom.adapter.FeedListAdapter;
import com.example.corppool.db.SQLiteHandler;
import com.example.corppool.model.Feed;
import com.example.corppool.model.Location;
import com.example.corppool.server.ServerInterface;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Feeds_results.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Feeds_results#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Feeds_results extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private TextView reqStartLoc;
    private TextView reqEndLoc;

    private TextView reqStartDate;
    private TextView reqStartTime;
    private ListView feedList;

    private Feed reqStartFeed = new Feed();

    private List<Feed> feeds = new ArrayList<Feed>();
    private FeedListAdapter adapter;

    private SQLiteHandler db;
    private OnFragmentInteractionListener mListener;

    public Feeds_results() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment feeds_results.
     */
    // TODO: Rename and change types and number of parameters
    public static Feeds_results newInstance(String param1, String param2) {
        Feeds_results fragment = new Feeds_results();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // SQLite database handler
        db = new SQLiteHandler(getActivity());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {



        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_feeds_results, container, false);
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {

        super.onActivityCreated(savedInstanceState);
        reqStartLoc = (TextView)getActivity(). findViewById(R.id.reqStartLoc);
        reqEndLoc = (TextView)getActivity(). findViewById(R.id.reqEndLoc);
        reqStartDate = (TextView) getActivity().findViewById(R.id.reqStartDate);
        reqStartTime = (TextView) getActivity().findViewById(R.id.reqStartTime);

        //now set all parameters based on main activity content
       // loadData();

        //for now load from db
        loadDataFromDb();

        //here call Http Get to get all feeds
        loadFeeds();

    }
    private void loadData(){

        Intent i =getActivity(). getIntent();

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
        sloc.set_lat(i.getDoubleExtra("reqStartLat", 0));
        sloc.set_long(i.getDoubleExtra("reqStartLong", 0));

        eloc.setType("Point");
        eloc.set_lat(i.getDoubleExtra("reqEndLat", 0));
        eloc.set_long(i.getDoubleExtra("reqEndLong", 0));

        //populate values from the request
        reqStartLoc.setText(reqStartFeed.getStartAddress());
        reqEndLoc.setText(reqStartFeed.getEndAddress());

        //reqStartDate.setText(reqStartFeed.getDate());
        //display date
        reqStartDate.setText(i.getStringExtra("date_display"));

        reqStartTime.setText(reqStartFeed.getTime());

    }

    private void loadDataFromDb(){

      HashMap<String,String> map = db.getFeedDetails();
        //populate pojo and use that to populate futher
        reqStartFeed.setStartAddress(map.get("reqStartLoc"));
        reqStartFeed.setEndAddress(map.get("reqEndLoc"));
        reqStartFeed.setDate(map.get("date"));
        reqStartFeed.setTime(map.get("time"));

        Location sloc = new Location();
        Location eloc = new Location();
        reqStartFeed.setStartLoc(sloc);
        reqStartFeed.setEndLoc(eloc);

        sloc.setType("Point");
        sloc.set_lat(Double.valueOf(map.get("reqStartLat")));
        sloc.set_long(Double.valueOf(map.get("reqStartLong")));

        eloc.setType("Point");
        eloc.set_lat(Double.valueOf(map.get("reqEndLat")));
        eloc.set_long(Double.valueOf(map.get("reqEndLong")));

        //populate values from the request
        reqStartLoc.setText(reqStartFeed.getStartAddress());
        reqEndLoc.setText(reqStartFeed.getEndAddress());

        //reqStartDate.setText(reqStartFeed.getDate());
        //display date
        reqStartDate.setText(map.get("date_display"));

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
        feedList =(ListView) getActivity().findViewById(android.R.id.list);
        adapter= new FeedListAdapter(getActivity(),
                feeds,reqStartFeed);

        feedList.setAdapter(adapter);
        adapter.notifyDataSetChanged();
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
                jsonArr = ServerInterface.convertResponseToJSonArr(jsonStr);
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

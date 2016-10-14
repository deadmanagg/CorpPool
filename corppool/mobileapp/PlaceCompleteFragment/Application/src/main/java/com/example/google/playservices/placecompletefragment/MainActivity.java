/*
 * Copyright (C) 2015 Google Inc. All Rights Reserved.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package com.example.google.playservices.placecompletefragment;

import com.example.corppool.model.Feed;
import com.example.corppool.model.Location;
import com.example.corppool.server.ServerInterface;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocompleteFragment;
import com.google.android.gms.location.places.ui.PlaceSelectionListener;

import com.example.android.common.activities.SampleActivityBase;
import com.example.android.common.logger.Log;
import com.google.android.gms.maps.model.LatLng;

import android.app.Application;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Html;
import android.text.Spanned;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import org.json.JSONObject;

import java.util.Calendar;
import java.util.EventListener;

public class MainActivity extends SampleActivityBase implements PlaceSelectionListener,View.OnClickListener {

    private TextView mPlaceDetailsText;

    private TextView mPlaceAttribution;

    private DatePicker datePicker;
    private TimePicker timePicker;

    private Feed feed;
    private Button postButton;

    private String intent;

    private Place startLoc;
    private Place endLoc;
    private Button findButton;

    private TextView startDate;

    //original Textview will have user friendly values like Today, tommorrow etc
    //this will have actual date value
    private String startDateVal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        // Retrieve the PlaceAutocompleteFragment.
        PlaceAutocompleteFragment startLoc = (PlaceAutocompleteFragment)
                getFragmentManager().findFragmentById(R.id.startLoc);


        // Register a listener to receive callbacks when a place has been selected or an error has
        // occurred.
        startLoc.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(Place place) {

                setStartLocation(place);
            }

            @Override
            public void onError(Status status) {

            }
        });

        // Retrieve the PlaceAutocompleteFragment.
        PlaceAutocompleteFragment endLoc = (PlaceAutocompleteFragment)
                getFragmentManager().findFragmentById(R.id.endLoc);

        // Register a listener to receive callbacks when a place has been selected or an error has
        // occurred.
        endLoc.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(Place place) {
                setEndLocation(place);
            }

            @Override
            public void onError(Status status) {

            }
        });


        // Retrieve the TextViews that will display details about the selected place.
      //  mPlaceDetailsText = (TextView) findViewById(R.id.place_details);
       // mPlaceAttribution = (TextView) findViewById(R.id.place_attribution);
      //  datePicker = (DatePicker)findViewById(R.id.datePicker1);
        timePicker = (TimePicker)findViewById(R.id.timePicker1);
        startDate = (TextView)findViewById(R.id.startDate);

        //set default date in String
        Calendar mcurrentDate = Calendar.getInstance();
        int mYear = mcurrentDate.get(Calendar.YEAR);
        int mMonth = mcurrentDate.get(Calendar.MONTH);
        int mDay = mcurrentDate.get(Calendar.DAY_OF_MONTH);
        startDateVal = ""+mDay+"/"+(mMonth+1)+"/"+mYear;

        //set listener to show dialog for date
        startDate.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                //To show current date in the datepicker
                Calendar mcurrentDate = Calendar.getInstance();
                int mYear = mcurrentDate.get(Calendar.YEAR);
                int mMonth = mcurrentDate.get(Calendar.MONTH);
                int mDay = mcurrentDate.get(Calendar.DAY_OF_MONTH);


                //TODO move date picker object to factory package to avoid setting same config at multiple places
                DatePickerDialog mDatePicker;
                mDatePicker = new DatePickerDialog(MainActivity.this,android.R.style.Theme_Holo_Dialog, new DatePickerDialog.OnDateSetListener() {

                    public void onDateSet(DatePicker datepicker, int selectedyear, int selectedmonth, int selectedday) {
                        // TODO Auto-generated method stub
                        // *      Your code   to get date and time    *//*
                        selectedmonth = selectedmonth + 1;

                        //set value Date Variable as well
                        startDateVal = "" + selectedday + "/" + selectedmonth + "/" + selectedyear;

                        startDate.setText(startDateVal);
                    }
                    }

                    ,mYear, mMonth, mDay);
                mDatePicker.setTitle("Select Date");
                    mDatePicker.getDatePicker().setSpinnersShown(true);
                mDatePicker.getDatePicker().setCalendarViewShown(false);
                mDatePicker.show();


            }
        });
        postButton = (Button)findViewById(R.id.post_button);

        postButton.setOnClickListener(this);
        findButton = (Button)findViewById(R.id.find_button);

        findButton.setOnClickListener(this);
    }

    /**
     * Callback invoked when a place has been selected from the PlaceAutocompleteFragment.
     */
    @Override
    public void onPlaceSelected(Place place) {
        Log.i(TAG, "Place Selected: " + place.getName());

        // Format the returned place's details and display them in the TextView.
        mPlaceDetailsText.setText(formatPlaceDetails(getResources(), place.getName(), place.getId(),
                place.getAddress(), place.getPhoneNumber(), place.getWebsiteUri()));

        CharSequence attributions = place.getAttributions();
        if (!TextUtils.isEmpty(attributions)) {
            mPlaceAttribution.setText(Html.fromHtml(attributions.toString()));
        } else {
            mPlaceAttribution.setText("");
        }
    }

    private void setStartLocation(Place place){

        Location loc = new Location();;
        if(feed==null){
            feed = new Feed();

        }
        loc.setType("Point");
        loc.set_lat(place.getLatLng().latitude);
        loc.set_long(place.getLatLng().longitude);

        feed.setStartLoc(loc);

        startLoc = place;
    }

    private void setEndLocation(Place place){
        Location loc = new Location();;
        if(feed==null){
            feed = new Feed();

        }
        loc.setType("Point");
        loc.set_lat(place.getLatLng().latitude);
        loc.set_long(place.getLatLng().longitude);

        feed.setEndLoc(loc);
        endLoc = place;
    }


    /**
     * Callback invoked when PlaceAutocompleteFragment encounters an error.
     */
    @Override
    public void onError(Status status) {
        Log.e(TAG, "onError: Status = " + status.toString());

        Toast.makeText(this, "Place selection failed: " + status.getStatusMessage(),
                Toast.LENGTH_SHORT).show();
    }

    /**
     * Helper method to format information about a place nicely.
     */
    private static Spanned formatPlaceDetails(Resources res, CharSequence name, String id,
            CharSequence address, CharSequence phoneNumber, Uri websiteUri) {
        Log.e(TAG, res.getString(R.string.place_details, name, id, address, phoneNumber,
                websiteUri));
        return Html.fromHtml(res.getString(R.string.place_details, name, id, address, phoneNumber,
                websiteUri));

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.post_button:
                // do your code
                intent = "POST";
                new PostFeed().execute();
                break;

            case R.id.find_button:
                // do your code
                intent = "FIND";
                //invoke the other activity
                showFeeds();
                break;
            default:
                break;
        }
    }

    private void showFeeds(){

           Intent intent = new Intent(MainActivity.this, Feeds_main.class);
        //TODO, develop a way to pass POJO, instead of each value by itself
        intent.putExtra("name","DeepanshMobile");
        intent.putExtra("userid","FirstAndroidApp");
        //intent.putExtra("date",datePicker.getDayOfMonth()+"/"+(datePicker.getMonth()+1)+"/"+datePicker.getYear());
        intent.putExtra("date_display",startDate.getText().toString());
        intent.putExtra("date",startDateVal);

        intent.putExtra("time",timePicker.getHour()+":"+timePicker.getMinute());

        //Once Pojo serialisze is available, pass whole place object
        intent.putExtra("reqStartLoc",startLoc.getName());
        intent.putExtra("reqEndLoc",endLoc.getName());
        intent.putExtra("reqStartLat",startLoc.getLatLng().latitude);
        intent.putExtra("reqStartLong",startLoc.getLatLng().longitude);
        intent.putExtra("reqEndLat",endLoc.getLatLng().latitude);
        intent.putExtra("reqEndLong",endLoc.getLatLng().longitude);


        startActivity(intent);
        finish();
    }

    private class PostFeed extends AsyncTask<String, Void, Void> {

        private String response;
        protected void onPreExecute() {

            //pDialog.show();

            try {

                feed.setName("DeepanshMobile");
                feed.setUserid("FirstAndroidApp");
                //feed.setDate(datePicker.getDayOfMonth()+"/"+(datePicker.getMonth()+1)+"/"+datePicker.getYear());
                feed.setDate(startDate.getText().toString());
                feed.setTime(timePicker.getHour()+":"+timePicker.getMinute());

                feed.setStartAddress(startLoc.getAddress().toString());
                feed.setEndAddress(endLoc.getAddress().toString());

            } catch (Exception e) {
                e.printStackTrace();
            }

        }
        protected Void doInBackground(String... urls) {

            response = ServerInterface.POSTFeed(feed);

            return null;

        }
        protected void onPostExecute(Void unused) {
            // NOTE: You can call UI Element here.
           // pDialog.dismiss();
            try {
                System.out.println(response);
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

    }
}



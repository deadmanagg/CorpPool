package com.example.corppool.controller;

import android.app.DatePickerDialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;

import android.text.Html;
import android.text.Spanned;
import android.text.TextUtils;
import android.util.Log;
import android.view.InflateException;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.corppool.controller.R;
import com.example.corppool.db.FeedDbHandler;
import com.example.corppool.db.SQLiteHandler;
import com.example.corppool.model.Feed;
import com.example.corppool.model.Location;
import com.example.corppool.server.ServerInterface;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocompleteFragment;
import com.google.android.gms.location.places.ui.PlaceSelectionListener;

import java.util.Calendar;
import java.util.Date;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link AddNewFeed.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link AddNewFeed#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AddNewFeed extends Fragment implements PlaceSelectionListener,View.OnClickListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    public static final String TAG = "AddNewFeed";

    private TextView mPlaceDetailsText;

    private TextView mPlaceAttribution;

    private DatePicker datePicker;
    private TimePicker timePicker;

    private Feed feed = new Feed();
    private Button postButton;

    private Place startLoc;
    private Place endLoc;
    private Button findButton;

    private TextView startDate;

    //original Textview will have user friendly values like Today, tommorrow etc
    //this will have actual date value
    private String startDateVal;

    private OnFragmentInteractionListener mListener;

    //instance of this view, this is to solve issue where back button on another fragment crashing app
    private static View view;
    private FeedDbHandler db;

    public AddNewFeed() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AddNewFeed.
     */
    // TODO: Rename and change types and number of parameters
    public static AddNewFeed newInstance(String param1, String param2) {
        AddNewFeed fragment = new AddNewFeed();
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
        try {
            db = new FeedDbHandler(getActivity());
        }catch(Exception e){
            Log.d(TAG, "onCreate: unable to create db object "+e);
        }

    }

    /**
     * Callback invoked when a place has been selected from the PlaceAutocompleteFragment.
     */
    @Override
    public void onPlaceSelected(Place place) {
        //Log.i(TAG, "Place Selected: " + place.getName());

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

        loc.setType("Point");
        loc.set_lat(place.getLatLng().latitude);
        loc.set_long(place.getLatLng().longitude);

        feed.setStartLoc(loc);
        feed.setStartAddress(place.getAddress().toString());

        startLoc = place;
    }

    private void setEndLocation(Place place){
        Location loc = new Location();;

        loc.setType("Point");
        loc.set_lat(place.getLatLng().latitude);
        loc.set_long(place.getLatLng().longitude);

        feed.setEndLoc(loc);
        endLoc = place;
        feed.setEndAddress(place.getAddress().toString());


    }


    /**
     * Callback invoked when PlaceAutocompleteFragment encounters an error.
     */
    @Override
    public void onError(Status status) {
        //Log.e(TAG, "onError: Status = " + status.toString());


        Toast.makeText(getActivity(), "Place selection failed: " + status.getStatusMessage(),
                Toast.LENGTH_SHORT).show();
    }

    /**
     * Helper method to format information about a place nicely.
     */
    private static Spanned formatPlaceDetails(Resources res, CharSequence name, String id,
                                              CharSequence address, CharSequence phoneNumber, Uri websiteUri) {
        /*Log.e(TAG, res.getString(R.string.place_details, name, id, address, phoneNumber,
                websiteUri));*/
        return Html.fromHtml(res.getString(R.string.place_details, name, id, address, phoneNumber,
                websiteUri));

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.post_button:
                // do your code
                feed.setType(Feed.AvailableTypes.CAROWNER);

                //postfeed has been moved to confirm feed
                //new PostFeed().execute();

                //give control back to main activity
                ((MainActivity)getActivity()).showConfirmPage(feed);
                break;

            case R.id.find_button:
                // do your code
                feed.setType(Feed.AvailableTypes.RIDER);
                addFeedInDb();

                //if logged in, invoke parent method
                if(((MainActivity)getActivity()).isLoggedIn()){
                    ((MainActivity)getActivity()).showTabMyFeeds();
                }
                else {//invoke the other activity
                    showFeeds();
                }
                break;
            default:
                break;
        }
    }

    private void addFeedInDb(){
        //Since this is brand new request, clear old feeds
        db.deleteFeeds();

        //store in db
       db.addFeed(feed);
    }
    private void showFeeds(){

        Intent intent = new Intent( getActivity(), Feeds_main.class);
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
        intent.putExtra("reqStartLat", startLoc.getLatLng().latitude);
        intent.putExtra("reqStartLong", startLoc.getLatLng().longitude);
        intent.putExtra("reqEndLat",endLoc.getLatLng().latitude);
        intent.putExtra("reqEndLong", endLoc.getLatLng().longitude);
        startActivity(intent);
        getActivity().finish();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        try {

            //set the static variable of main activity to track which fragment currently active
            MainActivity.currentView = "add_feed";

            // Inflate the layout for this fragment
            view = inflater.inflate(R.layout.fragment_add_new_feed, container, false);
        } catch (InflateException e) {
        /* map is already there, just return view as it is */

        }
        return view;

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
     * This method is invoked once the activity view is inflated, so all objects
     * are available here on
     */

    @Override
    public void onActivityCreated(Bundle savedInstanceState){

        super.onActivityCreated(savedInstanceState);

        // Retrieve the PlaceAutocompleteFragment.
        PlaceAutocompleteFragment startLoc = (PlaceAutocompleteFragment)
                getChildFragmentManager().findFragmentById(R.id.startLoc);


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
                getChildFragmentManager().findFragmentById(R.id.endLoc);

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
        timePicker = (TimePicker)getActivity().findViewById(R.id.timePicker1);
        startDate = (TextView)getActivity().findViewById(R.id.startDate);

        //set default date in String
        Calendar mcurrentDate = Calendar.getInstance();
        int mYear = mcurrentDate.get(Calendar.YEAR);
        int mMonth = mcurrentDate.get(Calendar.MONTH);
        int mDay = mcurrentDate.get(Calendar.DAY_OF_MONTH);
        startDateVal = ""+mDay+"/"+(mMonth+1)+"/"+mYear;

        feed.setDate(startDateVal);
        feed.setTime(timePicker.getHour() + ":" + timePicker.getMinute());

        feed.setDatetime(mcurrentDate.getTime());
        //set listener to show dialog for date
        startDate.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                //To show current date in the datepicker
                final Calendar mcurrentDate = Calendar.getInstance();
                int mYear = mcurrentDate.get(Calendar.YEAR);
                int mMonth = mcurrentDate.get(Calendar.MONTH);
                int mDay = mcurrentDate.get(Calendar.DAY_OF_MONTH);


                //TODO move date picker object to factory package to avoid setting same config at multiple places
                DatePickerDialog mDatePicker;
                mDatePicker = new DatePickerDialog(getActivity(),android.R.style.Theme_Holo_Dialog, new DatePickerDialog.OnDateSetListener() {

                    public void onDateSet(DatePicker datepicker, int selectedyear, int selectedmonth, int selectedday) {
                        // TODO Auto-generated method stub
                        // *      Your code   to get date and time    *//*
                        selectedmonth = selectedmonth + 1;

                        //set value Date Variable as well
                        startDateVal = "" + selectedday + "/" + selectedmonth + "/" + selectedyear;

                        startDate.setText(startDateVal);

                        //set feed variable
                        feed.setDate(startDateVal);

                        //get the datetime current set on feed
                        Date setFeedDate = feed.getDatetime();

                        //TODO check if null, then it is never set, set instantly
                        //relying on onactivity which should have already been created
                        Calendar setFeedDateObj = Calendar.getInstance();

                        //setting only dd mm yy factors
                        if(setFeedDate!=null){

                            setFeedDateObj.setTimeInMillis(setFeedDate.getTime());
                            //now set date time based on the selected
                         }else{

                            //set new calendar object
                            feed.setDatetime(setFeedDateObj.getTime());
                        }

                        //now set dates details
                        setFeedDateObj.set(selectedyear, selectedmonth, selectedday);

                        //now set this back to feed
                        feed.setDatetime(setFeedDateObj.getTime());
                    }
                }

                        ,mYear, mMonth, mDay);
                mDatePicker.setTitle("Select Date");
                mDatePicker.getDatePicker().setSpinnersShown(true);
                mDatePicker.getDatePicker().setCalendarViewShown(false);
                mDatePicker.show();


            }
        });

        //set listener for time picker
        timePicker.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {

            public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
                feed.setTime(hourOfDay+":"+minute);

                //get the datetime current set on feed
                Date setFeedDate = feed.getDatetime();

                //TODO check if null, then it is never set, set instantly
                //relying on onactivity which should have already been created
                Calendar setFeedTimeObj = Calendar.getInstance();

                //setting only dd mm yy factors
                if(setFeedDate!=null){

                    setFeedTimeObj.setTimeInMillis(setFeedDate.getTime());
                    //now set date time based on the selected
                }else{

                    //set new calendar object
                    feed.setDatetime(setFeedTimeObj.getTime());
                }

                //now set time
                setFeedTimeObj.set(Calendar.HOUR_OF_DAY,hourOfDay);
                setFeedTimeObj.set(Calendar.MINUTE,minute);

                feed.setDatetime(setFeedTimeObj.getTime());
            }
        });

        postButton = (Button)getActivity().findViewById(R.id.post_button);

        postButton.setOnClickListener(this);
        findButton = (Button)getActivity().findViewById(R.id.find_button);

        findButton.setOnClickListener(this);
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


}

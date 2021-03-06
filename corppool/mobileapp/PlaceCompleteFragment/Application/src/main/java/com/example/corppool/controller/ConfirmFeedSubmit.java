package com.example.corppool.controller;


import android.app.Fragment;
import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.corppool.config.AppConstants;
import com.example.corppool.model.Feed;
import com.example.corppool.controller.R;
import com.example.corppool.model.User;
import com.example.corppool.server.ServerInterface;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ConfirmFeedSubmit.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ConfirmFeedSubmit#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ConfirmFeedSubmit extends Fragment implements View.OnClickListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    //Confirm feed information
    private Feed feed;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private TextView reqStartLoc;
    private TextView reqEndLoc;
    private TextView reqStartDate;
    private TextView reqStartTime;

    private Button btnConfirmSubmit;

    private EditText reqEmailId;
    private EditText reqPhoneNum;

    private OnFragmentInteractionListener mListener;

    public ConfirmFeedSubmit() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *

     * @return A new instance of fragment ConfirmFeedSubmit.
     */
    // TODO: Rename and change types and number of parameters
    public static ConfirmFeedSubmit newInstance(Feed feed) {
        ConfirmFeedSubmit fragment = new ConfirmFeedSubmit();
        Bundle args = new Bundle();

        fragment.setFeed(feed);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        //set the static variable of main activity to track which fragment currently active
        MainActivity.currentView = "confirm_feed";

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_confirm_feed_submit, container, false);
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
    public void onActivityCreated(Bundle savedInstanceState) {

        super.onActivityCreated(savedInstanceState);

        reqStartDate = (TextView)getActivity().findViewById(R.id.reqStartDate);
        reqEndLoc = (TextView)getActivity().findViewById(R.id.reqEndLoc);
        reqStartLoc = (TextView)getActivity().findViewById(R.id.reqStartLoc);
        reqStartTime = (TextView)getActivity().findViewById(R.id.reqStartTime);
        //set values
        reqEndLoc.setText(feed.getEndAddress());
        reqStartTime.setText(feed.getTime());
        reqStartLoc.setText(feed.getStartAddress());
        reqStartDate.setText(feed.getDate());

        //attach button variable
        btnConfirmSubmit = (Button)getActivity().findViewById(R.id.confirmfeed_button);

        btnConfirmSubmit.setOnClickListener(this);

        //attach editable text components
        reqEmailId =(EditText)getActivity().findViewById(R.id.reqEmailId);
        reqPhoneNum = (EditText)getActivity().findViewById(R.id.reqPhoneNum);
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
        void CallBackConfirmFeedSuccess(Feed feed);
    }

    private void setFeed(Feed feed){
        this.feed = feed;
    }

    private class PostFeed extends AsyncTask<String, Void, Void> {

        private String response;
        protected void onPreExecute() {

            //pDialog.show();

            try {

                feed.setName(AppConstants.defaulName);
                feed.setUserid(AppConstants.defaulUserId);

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

    @Override
    public void onClick(View v) {
        fillUserInfo();
        //submit feed to the server
        new PostFeed().execute();

        //move to the next step
        mListener.CallBackConfirmFeedSuccess(feed);
    }

    private void fillUserInfo(){
        User user = new User();

        //TODO, all validation of registration etc will go here
        user.setUserid(reqEmailId.getText().toString());
        user.setPhoneNum(reqPhoneNum.getText().toString());
        user.setIsAnonymous(true);
        user.setIsDriver(true);

        feed.setDriver(user);
    }
}

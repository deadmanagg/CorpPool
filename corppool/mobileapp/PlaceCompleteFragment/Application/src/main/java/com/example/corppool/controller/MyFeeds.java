package com.example.corppool.controller;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import android.support.design.widget.TabLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link MyFeeds.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link MyFeeds#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MyFeeds extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private TabLayout tabLayout;

    private static final int TAB_CAR_OWNER = 0;
    private static final int TAB_RIDER = 1;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public MyFeeds() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MyFeeds.
     */
    // TODO: Rename and change types and number of parameters
    public static MyFeeds newInstance(String param1, String param2) {
        MyFeeds fragment = new MyFeeds();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
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


        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_my_feeds, container, false);
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

    private void addTabs() {

        tabLayout = (TabLayout) getActivity().findViewById(R.id.myfeeds_tabs);
        tabLayout.addTab(tabLayout.newTab().setText("As Car Owner"),TAB_CAR_OWNER);
        tabLayout.addTab(tabLayout.newTab().setText("As Rider"),TAB_RIDER);

        //tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                showFragment(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {


            }
        });

        //show default format
        showMyFeedsRider();
    }

    private void showFragment(int pos) {
        switch (pos) {
            case 0:
                //showAddNewFeed();
                break;
            case 1:
                showFeedresults();
                break;
        }
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {

        super.onActivityCreated(savedInstanceState);
        addTabs();
    }

    //function to show results TODO it will not work since no data passed
    private void showFeedresults() {
        Fragment fragment = new Feeds_results();
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.myfeedscontainer, fragment); // fragment container id in first parameter is the  container(Main layout id) of Activity
        transaction.addToBackStack("feedresultsRider");  // this will manage backstack
        transaction.commit();
    }

    public void showMyFeedsCarOwner(){
        tabLayout.getTabAt(TAB_CAR_OWNER).select();
    }

    public void showMyFeedsRider(){
        tabLayout.getTabAt(TAB_RIDER).select();
    }
}

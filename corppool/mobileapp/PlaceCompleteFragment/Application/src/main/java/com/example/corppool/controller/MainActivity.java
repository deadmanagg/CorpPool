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

package com.example.corppool.controller;

import com.example.android.common.activities.SampleActivityBase;
import com.example.corppool.db.SQLiteHandler;
import com.example.corppool.model.Feed;

import android.app.Activity;
import android.app.AlertDialog;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.design.widget.TabLayout;
import android.support.v13.app.FragmentPagerAdapter;
import android.support.v13.app.FragmentStatePagerAdapter;

import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;
import android.widget.Toolbar;

import com.example.corppool.controller.R;
import com.example.corppool.util.SessionUtil;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.Profile;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends SampleActivityBase implements Feeds_results.OnFragmentInteractionListener, ConfirmFeedSubmit.OnFragmentInteractionListener, AddNewFeed.OnFragmentInteractionListener {

    public static String currentView = "";
    CallbackManager callbackManager;

    private Button btnRegister;
    MyPagerAdapter adapterViewPager;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;
    private SessionUtil session;

    private LoginButton loginButton;

    //For clean up during logout
    private SQLiteHandler db;

    private  TabLayout tabLayout;

    private static final int TAB_POS_NEW = 0;
    private static final int TAB_POS_MYFEEDS = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //for fb analytics
        FacebookSdk.sdkInitialize(getApplicationContext());
        AppEventsLogger.activateApp(this);

        setContentView(R.layout.activity_main);

        final Toolbar tool = (Toolbar) findViewById(R.id.tool_bar);

        setActionBar(tool);

        addToolBar();
        //link button of registert
        btnRegister = (Button) findViewById(R.id.register);

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, RegisterActivity.class);
                startActivity(i);
            }
        });

        //tide up button
        linkFacebookButton();

        //default activity
        //  showAddNewFeed();
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();

        //set session
        // Session manager
        session = new SessionUtil(getApplicationContext());


        //invoke hide or show buttons for registration
        showHideRegisterOptions();
    }

    /*private void addToolBar() {

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tab_layout);
       // tabLayout.addTab(tabLayout.newTab().setText("Tab 1"));
       // tabLayout.addTab(tabLayout.newTab().setText("Tab 2"));
        //tabLayout.addTab(tabLayout.newTab().setText("Tab 3"));
       // tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        final ViewPager vpPagerView = (ViewPager) findViewById(R.id.vpPager);
        adapterViewPager = new MyPagerAdapter(getFragmentManager(),MainActivity.this);
        vpPagerView.setAdapter(adapterViewPager);
        tabLayout.setupWithViewPager(vpPagerView);
       // vpPagerView.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
       tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                vpPagerView.setCurrentItem(tab.getPosition());
                vpPagerView.getAdapter().notifyDataSetChanged();
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {


            }
        });
    }*/

    private void addToolBar() {

        tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        tabLayout.addTab(tabLayout.newTab().setText("New"),TAB_POS_NEW);
        tabLayout.addTab(tabLayout.newTab().setText("My Feeds"),TAB_POS_MYFEEDS);

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
        showFragment(0);
    }

    private void showFragment(int pos) {
        switch (pos) {
            case 0:
                showAddNewFeed();
                break;
            case 1:
                showFeedresults();
                break;
        }
    }

    //function to show new feed
    private void showAddNewFeed() {
        Fragment fragment = new AddNewFeed();
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.container, fragment); // fragment container id in first parameter is the  container(Main layout id) of Activity
        transaction.addToBackStack("newfeed");  // this will manage backstack
        transaction.commit();
    }

    //function to show results TODO it will not work since no data passed
    private void showFeedresults() {
        Fragment fragment = new Feeds_results();
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.container, fragment); // fragment container id in first parameter is the  container(Main layout id) of Activity
        transaction.addToBackStack("feedresults");  // this will manage backstack
        transaction.commit();
    }

    public void onFragmentInteraction(Uri uri) {
        //you can leave it empty
        //getFragmentManager().popBackStackImmediate();
    }

    public void showConfirmPage(Feed feed) {

        Fragment fragment = ConfirmFeedSubmit.newInstance(feed);
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.container, fragment); // fragment container id in first parameter is the  container(Main layout id) of Activity
        transaction.addToBackStack(null);  // this will manage backstack
        transaction.commit();
    }


    //Facebook linkage to the button
    public void linkFacebookButton() {
        FacebookSdk.sdkInitialize(getApplicationContext());
        callbackManager = CallbackManager.Factory.create();
        loginButton = (LoginButton) findViewById(R.id.fb_login_button);
        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                AccessToken accessToken = loginResult.getAccessToken();
                Profile profile = Profile.getCurrentProfile();
                //nextActivity(profile);
                Toast.makeText(getApplicationContext(), "Logging in...", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancel() {
            }

            @Override
            public void onError(FacebookException e) {
            }
        });
    }


    //Call back method once confirm feed is successful
    public void CallBackConfirmFeedSuccess(Feed feed) {

        if (!session.isLoggedIn()) {
            dialogForNotLoggedInUser();
        } else {
            dialogForLoggedInUser();
        }

    }

    //show dialog for first timers
    public void dialogForNotLoggedInUser() {

        //TODO check if user logged in

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(getString(R.string.title_route_submit));
        builder.setMessage(getString(R.string.confirm_feed_msg_not_logged_in))
                .setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        //do things

                    }
                });
        AlertDialog alert = builder.create();
        alert.show();

        //now back to the original feed
        showAddNewFeed();
    }

    //show dialog for first timers
    public void dialogForLoggedInUser() {

        //TODO check if user logged in

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(getString(R.string.title_route_submit));
        builder.setMessage(getString(R.string.confirm_feed_msg_logged_in))
                .setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        //do things

                    }
                });
        AlertDialog alert = builder.create();
        alert.show();

        //now back to the original feed
        tabLayout.getTabAt(TAB_POS_MYFEEDS).select();
    }

    @Override
    public void onBackPressed() {

        if (currentView != "add_feed") {

            showAddNewFeed();
            Toast.makeText(this, getString(R.string.msg_exit_on_back), Toast.LENGTH_LONG).show();

        } else {
            super.finish();
        }
    }

    /**
     * Below configurations are used to display tabs
     */

    public static class MyPagerAdapter extends FragmentStatePagerAdapter {
        private static int NUM_ITEMS = 2;
        // private FragmentManager mFragmentManager;
        //private Map<Integer, String> mFragmentTags;
        Context mContext;
        LayoutInflater mLayoutInflater;

        public MyPagerAdapter(FragmentManager fragmentManager, Context ctx) {
            super(fragmentManager);
            //  mFragmentManager = fragmentManager;
            // mFragmentTags = new HashMap<Integer, String>();
            mContext = ctx;
            mLayoutInflater = LayoutInflater.from(mContext);

        }

        // Returns total number of pages
        @Override
        public int getCount() {
            return NUM_ITEMS;
        }

        // Returns the fragment to display for that page
        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0: // Fragment # 0 - This will show FirstFragment
                    return new AddNewFeed();

                case 1: // Fragment # 0 - This will show FirstFragment different title
                    return new Feeds_results();

                default:
                    return new AddNewFeed();
            }
        }

        // Returns the page title for the top indicator
        @Override
        public CharSequence getPageTitle(int position) {
            return "Page " + position;
        }

        @Override
        public int getItemPosition(Object object) {
            return POSITION_NONE;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            Object object = super.instantiateItem(container, position);
            if (object instanceof Fragment) {
                Fragment fragment = (Fragment) object;
                String tag = fragment.getTag();
                //   mFragmentTags.put(position, tag);
            }
            return object;

        }

        @Override
        public Parcelable saveState() {
            return null;
        }

        @Override
        public void restoreState(Parcelable state, ClassLoader loader) {

        }

    }

    private static final int MENU_MYACCOUNT = Menu.FIRST;
    private static final int MENU_LOGOUT = Menu.FIRST + 1;
    //add menu to the activity. This will be called only when user is logged in

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        // Check if user is already logged in or not
        if (!session.isLoggedIn()) {
            return true;
        }
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.mainmenu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    public void showHideRegisterOptions() {

        if (session.isLoggedIn()) {

            btnRegister.setVisibility(View.INVISIBLE);
            loginButton.setVisibility(View.INVISIBLE);
        } else {
            btnRegister.setVisibility(View.VISIBLE);
            loginButton.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.logout:
                confirmLogout();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void cleanUpAndLogout() {

        db = new SQLiteHandler(this);

        //clear feeds
        db.deleteFeeds();

        //clear db user
        db.deleteUsers();

        //now logout
        session.setLogin(false);

        //Clean up server data TODO

        //restart activity
        recreate();
    }

    public void confirmLogout() {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(getString(R.string.title_logout));
        builder.setMessage(getString(R.string.confirm_msg_logout))
                .setNegativeButton("Stay logged in", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        //do nothing, just return

                    }
                }).

                setPositiveButton("Yes, Log out", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                //logout user
                                cleanUpAndLogout();
                            }
                        }

                );
        AlertDialog alert = builder.create();
        alert.show();
    }

}


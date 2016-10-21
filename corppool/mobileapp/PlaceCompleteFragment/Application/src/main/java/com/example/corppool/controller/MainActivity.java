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
import com.example.corppool.model.Feed;

import android.app.Fragment;
import android.app.FragmentTransaction;


import android.net.Uri;
import android.os.Bundle;
import android.widget.Toast;

import com.example.corppool.controller.R;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.Profile;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

public class MainActivity extends SampleActivityBase implements ConfirmFeedSubmit.OnFragmentInteractionListener,AddNewFeed.OnFragmentInteractionListener {

    CallbackManager callbackManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //for fb analytics
        FacebookSdk.sdkInitialize(getApplicationContext());
        AppEventsLogger.activateApp(this);

        setContentView(R.layout.activity_main);


        //tide up button
        linkFacebookButton();

        //default activity
        showAddNewFeed();
    }

    //function to show new feed
    private void showAddNewFeed(){
        Fragment fragment= new AddNewFeed();
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.container, fragment); // fragment container id in first parameter is the  container(Main layout id) of Activity
        transaction.addToBackStack(null);  // this will manage backstack
        transaction.commit();
    }

    public void onFragmentInteraction(Uri uri){
        //you can leave it empty
        showAddNewFeed();
    }
    public void showConfirmPage(Feed feed){

        Fragment fragment= ConfirmFeedSubmit.newInstance(feed);
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.container, fragment); // fragment container id in first parameter is the  container(Main layout id) of Activity
        transaction.addToBackStack(null);  // this will manage backstack
        transaction.commit();
    }


    //Facebook linkage to the button
    public void linkFacebookButton(){
    FacebookSdk.sdkInitialize(getApplicationContext());
    callbackManager = CallbackManager.Factory.create();
    LoginButton loginButton = (LoginButton) findViewById(R.id.fb_login_button);
    loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {  @Override
                                                                                         public void onSuccess(LoginResult loginResult) {
        AccessToken accessToken = loginResult.getAccessToken();
        Profile profile = Profile.getCurrentProfile();
        //nextActivity(profile);
        Toast.makeText(getApplicationContext(), "Logging in...", Toast.LENGTH_SHORT).show();    }

        @Override
        public void onCancel() {
        }

        @Override
        public void onError(FacebookException e) {
        } });
    }
}



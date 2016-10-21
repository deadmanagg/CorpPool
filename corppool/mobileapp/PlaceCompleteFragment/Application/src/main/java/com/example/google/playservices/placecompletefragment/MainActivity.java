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

import com.example.android.common.activities.SampleActivityBase;
import com.example.corppool.model.Feed;

import android.app.Fragment;
import android.app.FragmentTransaction;


import android.net.Uri;
import android.os.Bundle;

public class MainActivity extends SampleActivityBase implements ConfirmFeedSubmit.OnFragmentInteractionListener,AddNewFeed.OnFragmentInteractionListener {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
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


}



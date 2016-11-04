package com.example.corppool.controller;

import android.app.Activity;


import android.app.Fragment;
import android.app.FragmentTransaction;
import android.app.ListActivity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import android.view.MenuItem;


public class Feeds_main extends Activity implements Feeds_results.OnFragmentInteractionListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feeds_main);

        showResults();

       // getActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void showResults(){



            Fragment fragment= new Feeds_results();
            FragmentTransaction transaction = getFragmentManager().beginTransaction();
            transaction.replace(R.id.containerresult, fragment); // fragment container id in first parameter is the  container(Main layout id) of Activity
            transaction.addToBackStack("feedresult");  // this will manage
            // backstack
            transaction.commit();

    }



    @Override
    public  void onPause(){
        super.onPause();

    //    moveBackToHome();

    }

    private void moveBackToHome(){
        //go back to main activity
        Intent in = new Intent(Feeds_main.this,MainActivity.class);
        startActivity(in);

        //NavUtils.navigateUpTo(this, in);
       // NavUtils.navigateUpFromSameTask(this);
        finish();
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                moveBackToHome();

                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void onFragmentInteraction(Uri uri){
        //you can leave it empty
        //getFragmentManager().popBackStackImmediate();
    }

    @Override
    public void onBackPressed(){
     moveBackToHome();
    }
}

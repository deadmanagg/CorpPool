package com.example.corppool.util;

import android.location.Location;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by deepansh on 10/1g1/2016.
 */
public class CommonUtils {

    //catch exception and return blank string
    public static String getJsonValue(JSONObject obj,String key){

        String val = "";
        try{
            val = obj.getString(key);
        }catch(JSONException e){
            val = "";
        }

        return val;
    }

    public static double getDistance(double startLatitude, double startLongitude, double endLatitude, double endLongitude) {
        double distance = 0;
        Location locationA = new Location("A");
        locationA.setLatitude(startLatitude);
        locationA.setLongitude(startLongitude);
        Location locationB = new Location("B");
        locationB.setLatitude(endLatitude);
        locationB.setLongitude(endLongitude);
        distance = locationA.distanceTo(locationB);

        //convert for KMs
        return  Math.round(distance * 1.609344*100.0)/100.0;
    }
}

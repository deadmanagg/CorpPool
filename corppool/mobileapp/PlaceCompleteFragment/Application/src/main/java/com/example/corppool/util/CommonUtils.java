package com.example.corppool.util;

import android.location.Location;

import com.example.corppool.model.TimeDifference;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;
import java.util.InvalidPropertiesFormatException;

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
        //return  Math.round(distance * 1.609344*100.0)/100.0;
        //return (double)Math.round(distance*1000d)/1000d;  //in meters
        return distance;
    }

    public static double getDistanceInKm(double startLatitude, double startLongitude, double endLatitude, double endLongitude) {
        return Math.round((getDistance(startLatitude,startLongitude,endLatitude,endLongitude)/1000)*10)/10.0;
    }

    /**
     *
     * @param date1 Lower date value expected. Generally req Start Date-time in format  YYYY-MM-DD HH:MI
     * @param date2 Higher date value expected. Generally feed Start Date-time in format  YYYY-MM-DD HH:MI
     * @return Object of time difference
     */
    //TODO Improve utility, very error prone for index out of bound
    public static TimeDifference getTimeDifference(String date1,String date2) {


        //from req date
        Calendar c1 = Calendar.getInstance();
        c1.set(Calendar.YEAR,Integer.valueOf(date1.substring(0,3)));
        c1.set(Calendar.MONTH,Integer.valueOf(date1.substring(5, 6)));
        c1.set(Calendar.DAY_OF_MONTH,Integer.valueOf(date1.substring(8, 9)));
        c1.set(Calendar.HOUR_OF_DAY,Integer.valueOf(date1.substring(11,12)));
        c1.set(Calendar.MINUTE,Integer.valueOf(date1.substring(13,14)));

        //from req date
        Calendar c2 = Calendar.getInstance();
        c2.set(Calendar.YEAR,Integer.valueOf(date2.substring(0,3)));
        c2.set(Calendar.MONTH,Integer.valueOf(date2.substring(5, 6)));
        c2.set(Calendar.DAY_OF_MONTH,Integer.valueOf(date2.substring(8, 9)));
        c2.set(Calendar.HOUR_OF_DAY,Integer.valueOf(date2.substring(11,12)));
        c2.set(Calendar.MINUTE,Integer.valueOf(date2.substring(13,14)));

        long diff = c2.getTimeInMillis() -c1.getTimeInMillis();

        long diffSeconds = diff / 1000 % 60;
        long diffMinutes = diff / (60 * 1000) % 60;
        long diffHours = diff / (60 * 60 * 1000) % 24;
        long diffDays = diff / (24 * 60 * 60 * 1000);

        //now convert
        return   new TimeDifference.TimeDifferenceBuilder(diffMinutes).Days(diffDays).Hours(diffHours).build();

    }

    /**
     //TODO * This is not correct place to put this function. Need to change
     * @param textDate date content, such as Today Tomorrow etc needs to be formatted correctly.
     * @return
     */
    public static String convertTextDateToDate(String textDate){

        switch (textDate.toUpperCase()){
            case "TODAY":

        }
        return textDate;
    }

    public static String convertToInternationalDateFormat(String date){
        String finalDate = "";
        String[] datesObj = date.split("/");

        if(datesObj.length==3) {
            finalDate = datesObj[2] + "-" + datesObj[1] + "-" + datesObj[0];
        }
        return finalDate;
    }
}

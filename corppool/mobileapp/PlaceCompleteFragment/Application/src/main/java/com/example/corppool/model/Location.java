package com.example.corppool.model;

import org.json.JSONArray;
import org.json.JSONException;

/**
 * Created by deepansh on 10/9/2016.
 */
public class Location {

    private String type;
    private double[] coordinates;

    private double _lat;
    private double _long;

    public String getType() {
        return type;
    }

    public JSONArray getCoordinates() throws JSONException{

        JSONArray coordinates = new JSONArray();

        //Always Long followed by Lat
        coordinates.put(_long);
        coordinates.put(_lat);

        return coordinates;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setCoordinates(double[] coordinates) {
        this.coordinates = coordinates;
    }

    public double get_lat() {
        return _lat;
    }

    public void set_lat(double _lat) {
        this._lat = _lat;
    }

    public double get_long() {
        return _long;
    }

    public void set_long(double _long) {
        this._long = _long;
    }
}


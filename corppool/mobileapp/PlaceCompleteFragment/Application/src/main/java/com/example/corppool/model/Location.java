package com.example.corppool.model;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by deepansh on 10/9/2016.
 */
public class Location  implements JSONSerializable{

    private String type;
    private double[] coordinates;

    private double _lat;
    private double _long;

    public  Location(){}
    public Location(String type, double _lat, double _long){

        this.type = type;
        this._lat = _lat;
        this._long = _long;
    }

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

    public JSONObject getAsJson() throws JSONException {


        JSONObject locO = new JSONObject();
        locO.put("type", getType());
        locO.put("coordinates", getCoordinates());

        return locO;

    }
    }


package com.example.corppool.model;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by deepansh on 10/9/2016.
 */
public class Feed {

    private String _id;
    private String userid;
    private String name;
    private String type;
    private String city;
    private String date;
    private String time;

    private Location startLoc;
    private Location endLoc;

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public Location getStartLoc() {
        return startLoc;
    }

    public void setStartLoc(Location startLoc) {
        this.startLoc = startLoc;
    }

    public Location getEndLoc() {
        return endLoc;
    }

    public void setEndLoc(Location endLoc) {
        this.endLoc = endLoc;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public JSONObject getAsJson() throws JSONException{

        JSONObject startLocO = new JSONObject();
        startLocO.put("type", startLoc.getType());
        startLocO.put("coordinates", startLoc.getCoordinates());



        JSONObject endLocO = new JSONObject();
        endLocO.put("type", endLoc.getType());
        endLocO.put("coordinates", endLoc.getCoordinates());

        JSONObject o = new JSONObject();
        o.put("name", name);
        o.put("userid", userid);
        o.put("type", type);
        o.put("city", city);
        o.put("date", date);
        o.put("time", time);
        o.put("startLoc", startLocO);
        o.put("endLoc",endLocO);

        return  o;

    }

}
package com.example.corppool.model;

import com.example.corppool.util.CommonUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;
import java.util.List;

/**
 * Created by deepansh on 10/9/2016.
 */
public class Feed implements JSONSerializable {

    private String _id;
    private String userid;
    private String name;
    private String type;
    private String city;
    private String date;
    private String time;
    private Date datetime;

    private Location startLoc;
    private Location endLoc;

    private String startAddress;
    private String endAddress;

    private String nMilesAway;
    private String xMinutesAway;

    private User driver;
    private List<User> rider;

    public enum AvailableTypes{
        RIDER("rider"),
        CAROWNER("owner"),
        UNKNOWN("unknown");

        private String text;

        AvailableTypes(String text) {
            this.text = text;
        }

        public String getText() {
            return this.text;
        }


        public static AvailableTypes fromString(String text) {
            if (text != null) {
                for (AvailableTypes b : AvailableTypes.values()) {
                    if (text.equalsIgnoreCase(b.text)) {
                        return b;
                    }
                }
            }
            return UNKNOWN;
        }
    }

    //below constructor will be used to display feed.
    //this constructor is not useful to send info to server
    public Feed(String name, String userid, String startAddress, String endAddress, String nMilesAway,String xMinutesAway){

        this.name = name;
        this.userid = userid;
        this.startAddress = startAddress;
        this.endAddress = endAddress;
        this.nMilesAway = nMilesAway;
        this.xMinutesAway = xMinutesAway;
    }

    public Feed(){}

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

    public void setType(AvailableTypes type) {
        this.type = type.getText();
    }

    public AvailableTypes getTypeEnum() {

        return AvailableTypes.fromString(this.type);
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

        JSONObject o = new JSONObject();
        o.put("name", name);
        o.put("userid", userid);
        o.put("type", type);
        o.put("city", city);
        o.put("date", date);
        o.put("time", time);
        o.put("startLoc", startLoc.getAsJson());
        o.put("endLoc",endLoc.getAsJson());
        o.put("startAddress",startAddress);
        o.put("endAddress",endAddress);

        //there will be exactly one driver
        o.put("driver",driver.getAsJson());


        return  o;

    }

    public String getStartAddress() {
        return startAddress;
    }

    public void setStartAddress(String startAddress) {
        this.startAddress = startAddress;
    }

    public String getEndAddress() {
        return endAddress;
    }

    public void setEndAddress(String endAddress) {
        this.endAddress = endAddress;
    }

    public String getnMilesAway() {
        return nMilesAway;
    }

    public void setnMilesAway(String nMilesAway) {
        this.nMilesAway = nMilesAway;
    }

    public String getxMinutesAway() {
        return xMinutesAway;
    }

    public void setxMinutesAway(String xMinutesAway) {
        this.xMinutesAway = xMinutesAway;
    }

    public Date getDatetime() {
        return datetime;
    }

    public void setDatetime(Date datetime) {
        this.datetime = datetime;
    }

    //TODO move this to util, using Locker Project utlity funciton to convert JSOn to class object
    public static Feed marshalToFeed(JSONObject json) throws  JSONException{
        Feed feed = new Feed();
        Location startLoc = new Location();

        JSONObject startLocResp = json.getJSONObject("startLoc");
        JSONArray cor = startLocResp.getJSONArray("coordinates");
         startLoc.set_lat(cor.getDouble(1));
        startLoc.set_long(cor.getDouble(0));

        feed.setStartAddress(CommonUtils.getJsonValue(json, "startAddress"));
        feed.setEndAddress(CommonUtils.getJsonValue(json, "endAddress"));

        feed.setStartLoc(startLoc);
        feed.setDate(CommonUtils.getJsonValue(json, "date"));
        feed.setTime(CommonUtils.getJsonValue(json,"time"));
        return feed;
    }

    //TODO, temporary fix, server should handle inclusion/exclusion params
    public JSONObject getAsJsonForSearch() throws  JSONException{
        JSONObject startLocO = new JSONObject();
        startLocO.put("type", startLoc.getType());
        startLocO.put("coordinates", startLoc.getCoordinates());

        JSONObject endLocO = new JSONObject();
        endLocO.put("type", endLoc.getType());
        endLocO.put("coordinates", endLoc.getCoordinates());

        JSONObject o = new JSONObject();
        o.put("startLoc", startLocO);
        o.put("endLoc",endLocO);

        o.put("time",time);
        o.put("date",date);

        o.put("datetime",datetime.getTime());

        return  o;

    }

    public User getDriver() {
        return driver;
    }

    public void setDriver(User driver) {
        this.driver = driver;
    }

    public List<User> getRider() {
        return rider;
    }

    public void setRider(List<User> rider) {
        this.rider = rider;
    }


}

package com.example.corppool.model;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by deepansh on 10/21/2016.
 * Bean Object to keep User's profile
 */
public class User implements  JSONSerializable{

    //TODO this is not complete and will keep on adding new fields
    private String userid;
    private String phoneNum;

    private boolean isLoggedIn;
    private boolean isAnonymous;
    private boolean  isDriver;   //check if it driver of rider

    private boolean accountId;
    private String  password;

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

    public boolean isLoggedIn() {
        return isLoggedIn;
    }

    public void setIsLoggedIn(boolean isLoggedIn) {
        this.isLoggedIn = isLoggedIn;
    }

    public boolean isAnonymous() {
        return isAnonymous;
    }

    public void setIsAnonymous(boolean isAnonymous) {
        this.isAnonymous = isAnonymous;
    }

    public boolean isDriver() {
        return isDriver;
    }

    public void setIsDriver(boolean isDriver) {
        this.isDriver = isDriver;
    }

    public boolean isAccountId() {
        return accountId;
    }

    public void setAccountId(boolean accountId) {
        this.accountId = accountId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public JSONObject getAsJson() throws JSONException {
        JSONObject obj = new JSONObject();
        obj.put("userid",userid);
        obj.put("phoneNum",phoneNum);
        obj.put("password",password);

        //TODO, put more information here

        return obj;
    }
}

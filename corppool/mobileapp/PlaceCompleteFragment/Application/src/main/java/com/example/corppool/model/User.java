package com.example.corppool.model;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by deepansh on 10/21/2016.
 * Bean Object to keep User's profile
 */
public class User implements  JSONSerializable{

    //TODO this is not complete and will keep on adding new fields
    private String emailId;
    private String phoneNum;

    private boolean isLoggedIn;
    private boolean isAnonymous;
    private boolean  isDriver;   //check if it driver of rider

    private boolean accountId;

    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
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

    public JSONObject getAsJson() throws JSONException {
        JSONObject obj = new JSONObject();
        obj.put("emailId",emailId);
        obj.put("phoneNum",phoneNum);

        //TODO, put more information here

        return obj;
    }
}

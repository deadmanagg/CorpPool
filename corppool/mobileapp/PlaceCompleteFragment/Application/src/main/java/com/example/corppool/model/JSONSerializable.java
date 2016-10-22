package com.example.corppool.model;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by deepansh on 10/21/2016.
 * All Bean class that extends this interface must implement this class
 * this way will ensure that each bean implements method to expose
 * attributes as Json
 */
public interface JSONSerializable {

    public JSONObject getAsJson() throws JSONException;
}

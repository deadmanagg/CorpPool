package com.corppool.util;

import java.util.Map.Entry;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;


public class CloningUtil {

	public static JsonElement deepClone(JsonElement el){
	    if (el.isJsonPrimitive() || el.isJsonNull())
	        return el;
	    if (el.isJsonArray()) {
	        JsonArray array = new JsonArray();
	        for(JsonElement arrayEl: el.getAsJsonArray())
	            array.add(deepClone(arrayEl));
	        return array;
	    }
	    if(el.isJsonObject()) {
	        JsonObject obj = new JsonObject();
	        for (Entry<String, JsonElement> entry : el.getAsJsonObject().entrySet()) {
	            obj.add(entry.getKey(), deepClone(entry.getValue()));
	        }
	        return obj;
	    }
	    throw new IllegalArgumentException("JsonElement type " + el.getClass().getName());
	}
}

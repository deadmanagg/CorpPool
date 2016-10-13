package com.corppool.controller;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonIOException;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;

/**
 * All required functions that will be needed for searching
 * single instance of object will be used
 * @author deepansh
 *
 */
public abstract class SearchController<T extends SearchController> {

	protected JsonObject criteria = new JsonObject();
	
	public SearchController(){}
	
	public SearchController(String query){
		
		//TODO can be json element, need to rethink
		criteria = new JsonParser().parse(query).getAsJsonObject();
	}
	
	public JsonObject build() throws JsonParseException,JsonSyntaxException,JsonIOException{
	
		//TODO, validate JsonObject
		return criteria;
	}
	
	//based on builder pattern
	public T  removeCriteria(String key){
			criteria.remove(key);
			return (T)this;
	}
}

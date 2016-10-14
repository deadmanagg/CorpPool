package com.corppool.controller;

import com.corppool.util.CloningUtil;
import com.google.gson.JsonArray;
import com.google.gson.JsonIOException;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSyntaxException;

public class FeedSearchController extends SearchController<FeedSearchController> {

	public FeedSearchController(){
		super();
	}
	
	public FeedSearchController(String query){
		super(query);
	}
	public FeedSearchController removeStartTimeFactor(){
		return	super.removeCriteria("time");
	
	}
	
	public FeedSearchController removeIdFactor(){
		return	super.removeCriteria("_id");
	}
	
	public FeedSearchController removeDefaultFactors(){
		super.removeCriteria("_id");
		super.removeCriteria("userid");
		
		//TODO add more basic filters, which will be applicable to most of the feeds
		return super.removeCriteria("name");
	}
	
	public FeedSearchController addNearStartLocDist(double maxDist){
		
		//Get start location attribute
		JsonObject startLoc = criteria.get("startLoc").getAsJsonObject();
		
		//get coordinates
		JsonArray reqCord = startLoc.get("coordinates").getAsJsonArray();
		
		//Create new JsonObject of the content of start loc, which will be lost
		JsonObject startLocVal = (JsonObject) CloningUtil.deepClone(startLoc);
		
		//Now add MongoDb geo spatial way of searching
		//https://docs.mongodb.com/manual/reference/operator/query/near/
		/*db.places.find(
				   {
				     location:
				       { $near :
				          {
				            $geometry: { type: "Point",  coordinates: [ -73.9667, 40.78 ] },
				            $minDistance: 1000,
				            $maxDistance: 5000
				          }
				       }
				   }
				)*/
		
		JsonObject near = new JsonObject();
		near.add("$geometry", startLocVal);
		near.addProperty("$maxDistance"	, maxDist*1000);  //convert distance to KMs
		
		//add near to original
		startLoc.add("$near", near);
		
		//remove old fields
		startLoc.remove("type");
		startLoc.remove("coordinates");
		
		
		return this;
	}
	
	public FeedSearchController addFutureTime(){
		
		String reqTime = criteria.get("time").getAsString();
		
		//remove time, if already there
		super.removeCriteria("time");
		
		//add comparision to get matching time and greater time only
		//this is temporary fix only. Real solution is to compare both Date and time
		//right now, considering both has to go same day only
		JsonObject reqTimeJs = new JsonObject();
		reqTimeJs.addProperty("$gt", reqTime);
		
		criteria.add("time", reqTimeJs);
		return this;
	}
}

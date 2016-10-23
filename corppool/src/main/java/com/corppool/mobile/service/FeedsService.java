package com.corppool.mobile.service;

import java.net.URLDecoder;
import java.net.URLEncoder;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.corppool.controller.FeedSearchController;
import com.corppool.controller.FeedsController;
import com.corppool.mongodb.dao.MongoDBConnection;
import com.google.gson.JsonObject;
import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.util.JSON;

@Path("/feeds")
public class FeedsService extends MobileServices {

	
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	public Response addFeed(String feed){
		String result = "";
		JsonObject o = new JsonObject();
		try {
			
			BasicDBObject obj = (BasicDBObject) JSON.parse(feed);
			MongoDBConnection.write("Feeds",obj);
			
			//store in db for sending email
			FeedsController cntrl = factory.getFeedsController();
			
			//add new feed
			cntrl.addFeed(obj);
			cntrl.addEmailOutbound();
			
			o.addProperty("status", "Feed Added");
			
		} catch (Exception e) {
			// log to the logger
			e.printStackTrace();
			o.addProperty("status", "Failure");
		}

		return Response.status(200).entity(o.toString()).build();
		
	}
	
	@GET
	@Produces({ MediaType.APPLICATION_JSON })
	@Path("/{query}")
	public String getNearestFeeds(@PathParam("query") String query) {
		String resultJson = "";
		try {
			
			//Decode, once decode is done by server, this is second decoding since
			//client has to encode twice to solve / issues in the url
			query = URLDecoder.decode(query,"UTF-8");
			//filter request
			
			//TODO, use controller to get singleton object
			FeedSearchController cntrl = new FeedSearchController(query);
			
			//remove default criteria
			String filteredQuery = cntrl.removeDefaultFactors().addNearStartLocDist(10).addFutureTime().build().toString();
			
			DBObject o = (DBObject) JSON.parse(filteredQuery);
			BasicDBObject b = (BasicDBObject)o;
			BasicDBList feeds = MongoDBConnection.read("Feeds",b);
			
			resultJson = feeds.toString();
		}
		catch (Exception e) {
			// log to the logger
		e.printStackTrace();
		}

		return resultJson;
	}
}

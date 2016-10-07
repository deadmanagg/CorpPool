package com.corppool.mobile.service;

import java.net.URLDecoder;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.corppool.mongodb.dao.MongoDBConnection;
import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.util.JSON;

@Path("/feeds")
public class FeedsService {

	
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	public Response addFeed(String feed){
		String result = "";
		try {
			
			MongoDBConnection.write("Feeds",(BasicDBObject) JSON.parse(feed));
			
			result = "{status:Feed Added}";
		} catch (Exception e) {
			// log to the logger
			e.printStackTrace();
			result = "{status:Failure}";
		}

		return Response.status(200).entity(result).build();
		
	}
	
	@GET
	@Produces({ MediaType.APPLICATION_JSON })
	@Path("/{query}")
	public String getNearestFeeds(@PathParam("query") String query) {
		String resultJson = "";
		try {
			
			DBObject o = (DBObject) JSON.parse(query);
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

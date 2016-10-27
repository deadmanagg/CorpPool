package com.corppool.mobile.service;

import java.net.URLDecoder;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.corppool.controller.FeedSearchController;
import com.corppool.controller.FeedsController;
import com.corppool.controller.UserController;
import com.corppool.mongodb.dao.MongoDBConnection;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.util.JSON;

@Path("/user")
public class UserManagmentService extends MobileServices{

	@POST
	@Path("/register")
	@Produces(MediaType.APPLICATION_JSON)
	public Response addUser(String user){
	
		String result = "";
		JsonObject o = new JsonObject();
		try {
			
			//store in db for sending email
			UserController contrl = factory.getUserController();
			
			contrl.addNewUser(user);
			o.addProperty("status", "User Added");
			
		} catch (Exception e) {
			// log to the logger
			e.printStackTrace();
			o.addProperty("status", "Failure");
		}

		return Response.status(200).entity(o.toString()).build();
	}
	
	@POST
	@Produces({ MediaType.APPLICATION_JSON })
	@Path("/login")
	public Response login(String query) {
		JsonObject o = new JsonObject();
		try {
			
			//Decode, once decode is done by server, this is second decoding since
			//client has to encode twice to solve / issues in the url
			query = URLDecoder.decode(query,"UTF-8");
			//filter request
			
			//TODO, use controller to get singleton object
			//store in db for sending email
			UserController contrl = factory.getUserController();
			JsonObject logginDetails = new JsonParser().parse(query).getAsJsonObject();
			
			o =contrl.getUserByEmailPassword(logginDetails.get("userid").getAsString(), logginDetails.get("password").getAsString());
			
			
		}
		catch (Exception e) {
			// log to the logger
		e.printStackTrace();
		o.addProperty("error", "Unknown error while retrieving details");
		}

		return Response.status(200).entity(o.toString()).build();
	}
}

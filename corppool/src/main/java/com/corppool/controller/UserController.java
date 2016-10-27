package com.corppool.controller;

import com.corppool.adapter.AndroidMysqlAdapter;
import com.corppool.dao.AppUsersDAO;
import com.corppool.dao.impl.AppUsersDAOImpl;
import com.corppool.model.AppUsers;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.mongodb.util.JSON;

public class UserController extends BasicController {

	private AppUsersDAO dao = AppUsersDAOImpl.newInstance();
	
	public void addNewUser(String json){
		
		//parse json to create app user object
		
		//TODO copy json class from locker to create POJO class using json string
		
		//for now create manually, whichever data is required
		JsonObject jsonObj = new JsonParser().parse(json).getAsJsonObject();
		
		AppUsers user = AndroidMysqlAdapter.converToUser(jsonObj);
				
		//save user
		dao.addNewUser(user);
	}
	
	//This will return blank user, if no user found
	public JsonObject getUserByEmailPassword(String email,String password){
		
		JsonObject userObj = new JsonObject();
		if(dao.isValidEmailPassword(email, password)){
			AppUsers user = dao.getUserByEmail(email);
			
			userObj.addProperty("userid",user.getUserid());
		
		}else{
			userObj.addProperty("error", "Invalid Email or Password");
		}
		
		return userObj;
	}
}

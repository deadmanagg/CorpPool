package com.corppool.adapter;

import com.corppool.model.AppUsers;
import com.google.gson.JsonObject;

public class AndroidMysqlAdapter {

	public static AppUsers converToUser(JsonObject userDroid){
		AppUsers user = new AppUsers();
		
		user.setUserid(getString(userDroid,UserAdapterMapper.androidToSql.get(UserAdapterMapper.AndroidFields.userid).name()));
		user.setPassword(getString(userDroid,UserAdapterMapper.androidToSql.get(UserAdapterMapper.AndroidFields.password).name()));
		user.setUsertype(getString(userDroid,UserAdapterMapper.androidToSql.get(UserAdapterMapper.AndroidFields.usertype).name()));
		
		return user;
	}
	
	
	public static String getString(JsonObject userDroid, String key){
		String val = "";
		
		try{
			val = userDroid.get(key).getAsString();
		}catch(Exception e){
			
		}
		
		return val;
	}
}

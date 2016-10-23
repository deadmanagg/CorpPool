package com.corppool.adapter;

import com.corppool.adapter.FeedsAdapterMapper.MongoFields;
import com.corppool.model.Feeds;
import com.mongodb.BasicDBObject;

public class MongoToMysqlAdapter {

	public static Feeds convertToFeeds(BasicDBObject obj){
		Feeds feed = new Feeds();
		
		//Getting key from Mongo Db based on the mapper
		feed.setMongodb_id(getString(obj,FeedsAdapterMapper.sqlToMongo.get(FeedsAdapterMapper.SqlFields.mongodb_id).name()));
		
		//Get the driver attribute
		BasicDBObject driver = (BasicDBObject) obj.get(MongoFields.driver.name());
		
		//now get email id and phone number
		feed.setDriveremailid(getString(driver,FeedsAdapterMapper.sqlToMongo.get(FeedsAdapterMapper.SqlFields.driveremailid).name()));
		feed.setDriverphonenum(getString(driver,FeedsAdapterMapper.sqlToMongo.get(FeedsAdapterMapper.SqlFields.driverphonenum).name()));
		
		
		return feed;
	}
	
	public static String getString(BasicDBObject obj,String key){
		try{
			return obj.getString(key);
		}catch(Exception e){
			
		}
		
		return "";
	}
}

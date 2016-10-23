package com.corppool.adapter;

import java.util.HashMap;

public class FeedsAdapterMapper {

	public static final HashMap<SqlFields,MongoFields> sqlToMongo  = new HashMap<SqlFields,MongoFields>();
	public static final HashMap<MongoFields,SqlFields>  mongoToSql= new HashMap<MongoFields,SqlFields>();
	
	public static void add(SqlFields key,MongoFields value){
		
		sqlToMongo.put(key, value);
		mongoToSql.put(value, key);
	}
	
	static{
		
		add(SqlFields.mongodb_id,MongoFields._id);
		add(SqlFields.driveremailid,MongoFields.userid);
		add(SqlFields.driveremailid,MongoFields.emailId);
		add(SqlFields.driverphonenum,MongoFields.phoneNum);
		
	}
	
	public static enum SqlFields {
		mongodb_id,
		driveruserid,
		driveremailid,
		driverphonenum,
		datecreated
	}
	
	//fields, which contains value directly is listed
	//this is to consider mongodb as flat json object
	public static enum MongoFields {
		_id,
		userid,
		emailId,
		phoneNum,
		driver
	}
}

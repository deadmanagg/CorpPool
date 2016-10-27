package com.corppool.adapter;

import java.util.HashMap;


public class UserAdapterMapper {

	public static final HashMap<SqlFields,AndroidFields> sqlToanDroid  = new HashMap<SqlFields,AndroidFields>();
	public static final HashMap<AndroidFields,SqlFields>  androidToSql= new HashMap<AndroidFields,SqlFields>();
	
	public static void add(SqlFields key,AndroidFields value){
		
		sqlToanDroid.put(key, value);
		androidToSql.put(value, key);
	}
	
	static{
		
		add(SqlFields.userid,AndroidFields.userid);
		add(SqlFields.password,AndroidFields.password);
		add(SqlFields.usertype,AndroidFields.usertype);
	}
	
	public static enum SqlFields {
		userid,
		password,
		usertype
	}
	
	//fields, which contains value directly is listed
	//this is to consider mongodb as flat json object
	public static enum AndroidFields {
		
		userid,
		password,
		usertype
	}
}

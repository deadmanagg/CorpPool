package com.corppool.model;

import com.mongodb.BasicDBObject;

public class MongoDBQuery {

	BasicDBObject o;
	
	public MongoDBQuery(){
		o = new BasicDBObject();
	}
	
	public MongoDBQuery(BasicDBObject o){
		this.o = o;
	}
	
	public void findNearLoc(double[] coordinates){
		/*o.append("type", type);
		o.append("coordinates",coordinates );
		*/
		o.append("$near",coordinates);
	}
	
	public void addFilter(String key, String value){
		o.append(key, value);
	}
}

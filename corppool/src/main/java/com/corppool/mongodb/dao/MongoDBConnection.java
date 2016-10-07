package com.corppool.mongodb.dao;

import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.BulkWriteOperation;
import com.mongodb.BulkWriteResult;
import com.mongodb.Cursor;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.ParallelScanOptions;
import com.mongodb.ServerAddress;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import java.util.List;
import java.util.Set;

import org.bson.Document;

import static java.util.concurrent.TimeUnit.SECONDS;

import java.util.ArrayList;

// To directly connect to a single MongoDB server (note that this will not auto-discover the primary even
// if it's a member of a replica set:

public class MongoDBConnection {

	private static final MongoClient mongoClient = new MongoClient();

	public static MongoDatabase getDbConnection() {

		return  mongoClient.getDatabase("db");
	}

	public static void write(String collName,  BasicDBObject doc){
		MongoCollection<BasicDBObject> collections = getDbConnection().getCollection(collName, BasicDBObject.class);
		 collections.insertOne(doc);
	}
	
	public static BasicDBList  read(String collName,BasicDBObject query){
		
		final BasicDBList dbList = new BasicDBList();
		
		//iterate through all records and return.
		//TODO add default max size, if not already set
		
		FindIterable<Document> itr=  getDbConnection().getCollection(collName).find(query);
		for(Document doc: itr ){;
			dbList.add(doc);
		}
		
		return dbList;
	}
}

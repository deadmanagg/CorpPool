package com.corppool.controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.corppool.adapter.MongoToMysqlAdapter;
import com.corppool.config.ConfigurationJdbc;
import com.corppool.dao.FeedsDAO;
import com.corppool.dao.impl.FeedsDAOImpl;
import com.corppool.model.Feeds;
import com.mongodb.BasicDBObject;

public class FeedsController extends BasicController{

	private FeedsDAO dao = FeedsDAOImpl.newInstance();
	
	private Feeds feed;
	
	public void addFeed(BasicDBObject obj){
		
		//get converted Feeds object
		feed = MongoToMysqlAdapter.convertToFeeds(obj);
		
		//now insert into mysql db
		dao.addNewFeed(feed);
		
		//the id is stamped back on feed, use this to generate email outbound
		
	}
	
	//TODO move this to email dao
	public void addEmailOutbound(){
		
		// TODO use spring jdbc for further operations
		try {
			Connection conn = ConfigurationJdbc.getPoolConnection().getConnection();

			String insetFeed = "Insert into emailoutbound (objectid,objecttype,emailtype) values(?,?,?) ";

			PreparedStatement stmt = conn.prepareStatement(insetFeed);
			stmt.setString(1,feed.getIdCorpFeeds());
			stmt.setString(2, "corpfeeds");
			stmt.setString(3, "7");
			
			stmt.executeUpdate();
			
			stmt.close();
			conn.close();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

 
		
	}
}

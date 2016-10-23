package com.corppool.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.corppool.config.ConfigurationJdbc;
import com.corppool.dao.FeedsDAO;
import com.corppool.model.Feeds;

public class FeedsDAOImpl implements FeedsDAO {

	private FeedsDAOImpl() {

	}

	public static FeedsDAO newInstance() {
		return new FeedsDAOImpl();
	}

	public void addNewFeed(Feeds feed) {

		// TODO use spring jdbc for further operations
		try {
			Connection conn = ConfigurationJdbc.getPoolConnection().getConnection();

			String insetFeed = "Insert into corpfeeds (mongodb_id,driveruserid,driveremailid,driverphonenum) values(?,?,?,?) ";

			PreparedStatement stmt = conn.prepareStatement(insetFeed, Statement.RETURN_GENERATED_KEYS);
			stmt.setString(1,feed.getMongodb_id());
			stmt.setString(2, feed.getDriveruserid());
			stmt.setString(3, feed.getDriveremailid());
			stmt.setString(4,feed.getDriverphonenum());
			
			stmt.executeUpdate();

			ResultSet rs = stmt.getGeneratedKeys();
			rs.next();
			feed.setIdCorpFeeds(String.valueOf(rs.getInt(1)));
			
			rs.close();
			stmt.close();
			conn.close();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}

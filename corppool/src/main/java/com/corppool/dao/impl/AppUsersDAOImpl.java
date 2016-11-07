package com.corppool.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.corppool.config.ConfigurationJdbc;
import com.corppool.dao.AppUsersDAO;
import com.corppool.model.AppUsers;


/**
 * This is a temporay class only. Just to make registration and login work
 * on mobile device. This has to be rewritten completely
 * @author deepansh
 *
 */
public class AppUsersDAOImpl implements AppUsersDAO {

	private AppUsersDAOImpl() {

	}

	public static AppUsersDAO newInstance() {
		return new AppUsersDAOImpl();
	}

	public void addNewUser(AppUsers user) {

		// TODO use spring jdbc for further operations
		Connection conn = null;
		try {
			conn = ConfigurationJdbc.getPoolConnection().getConnection();

			String insetFeed = "Insert into appusers (userid,password,usertype) values(?,?,?) ";

			PreparedStatement stmt = conn.prepareStatement(insetFeed, Statement.RETURN_GENERATED_KEYS);
			stmt.setString(1,user.getUserid());
			stmt.setString(2, user.getPassword());
			stmt.setString(3, user.getUsertype());
			
			stmt.executeUpdate();
			
			stmt.close();
			
			conn.commit();
			conn.close();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			try {
				conn.rollback();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			e.printStackTrace();
		}finally{
			if(conn!=null){
				try{
				conn.close();
				}catch(Exception e){
					
				}
			}
		}

	}

	@Override
	public AppUsers getUserByEmail(String email) {
		AppUsers user = new AppUsers();
		Connection conn = null;
		try {
			conn = ConfigurationJdbc.getPoolConnection().getConnection();

			String getFeed = "select * from appusers where userid = ? ";

			PreparedStatement stmt = conn.prepareStatement(getFeed);
			stmt.setString(1,user.getUserid());
			
			ResultSet res = stmt.executeQuery();

			while (res.next()){
				user.setUserid(res.getString("userid"));
			}
			
			stmt.close();
			
			conn.commit();
			conn.close();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			try {
				conn.rollback();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			e.printStackTrace();
		}finally{
			if(conn!=null){
				try{
				conn.close();
				}catch(Exception e){
					
				}
			}
		}
		return user;
	}

	@Override
	public boolean isValidEmailPassword(String email, String password) {
		boolean isValid = false;
		Connection conn = null;
		try {
			conn = ConfigurationJdbc.getPoolConnection().getConnection();

			String getFeed = "select * from appusers where userid = ? and password =? ";

			PreparedStatement stmt = conn.prepareStatement(getFeed);
			stmt.setString(1,email);
			stmt.setString(2,password);
			
			ResultSet res = stmt.executeQuery();

			while (res.next()){
				isValid = true;
			}
			
			stmt.close();
			conn.close();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			try{
			conn.close();
			}catch(Exception e){
				
			}
		}
		return isValid;
	}
}

package com.corppool.config;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.apache.tomcat.dbcp.dbcp.BasicDataSource;

public class ConfigurationJdbc {

	//private static final String DBURL = "jdbc:mysql://localhost/corppool_db?user=root&password=root";
	private static final String DBURL = "jdbc:mysql://127.9.110.2:3306/corppool_db?user=adminkd6ihE7&password=beXcWRAMHEM1";
	public static BasicDataSource connectionPool;
	
	// Get connection from db pool
		public synchronized static void setPoolConnection() {
			
			if (connectionPool != null){
				return;
			
			}
			Context initContext = null;
			try {
				initContext = new InitialContext();
			} catch (NamingException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			Context envContext =null;
			try {
				envContext = (Context)initContext.lookup("java:/comp/env");
			} catch (NamingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				connectionPool = (BasicDataSource)envContext.lookup("jdbc/corppool");
			} catch (NamingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}catch(Exception e){
				e.printStackTrace();
			}
			
			if (connectionPool == null){
			connectionPool = new BasicDataSource();

			connectionPool.setDriverClassName("com.mysql.jdbc.Driver");
			connectionPool.setUrl(DBURL);
			connectionPool.setInitialSize(4);
			connectionPool.setDefaultAutoCommit(false);
			}
		}
		
		// Close any open connection
		public synchronized static void closeOpenConnection() {

			// basic conn pool nullify
			if (connectionPool != null) {
				try {
					connectionPool.close();
				} catch (Exception e) {

				} finally {
					connectionPool = null;
				}
			}

		}
		
		public static BasicDataSource getPoolConnection(){
			setPoolConnection();
			
			return connectionPool;
		}
		
}

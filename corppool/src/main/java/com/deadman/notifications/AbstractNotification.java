package com.deadman.notifications;

import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import com.corppool.config.ConfigurationJdbc;

public abstract class AbstractNotification {

	protected boolean defaultStartOnStartup = false;
	
	protected Connection connection;
	
	private String notificationType;
	
	private String sqlGetConfig = "Select configvalue from corppool_setup_config where configname =?";
	private String sqlUpdateConfig = "Update corppool_setup_config set configvalue = ?, modifiedby = 'web', modifieddate = sysdate where configname =?";
	
	private final static Set<Class> notificationTypes = new HashSet<Class>();
	
	public AbstractNotification(String type){
		notificationType = type;
	}
	
	public String getNotificationType(){
		return notificationType;
	}
	
	public void loadInitialConfiguration() throws SQLException{
		
		setDefaultStartUp();
	}
	
	private void setDefaultStartUp() throws SQLException{
		
		if (notificationType.isEmpty()){
			throw new AssertionError("Notification Type is not Set"); 
		}
		connection = ConfigurationJdbc.getPoolConnection().getConnection();
		
		PreparedStatement stm = connection.prepareStatement(sqlGetConfig);
		stm.setString(1, notificationType);
		
		ResultSet rs = stm.executeQuery();
		
		while(rs.next()){
			String configValue = rs.getString(1);
			
			if (configValue.equals("1")){
				defaultStartOnStartup = true;
			}
		}
		
		rs.close();
		stm.close();
		connection.close();
	}
	
	public void saveDefaultStartUpToFalse() throws SQLException{
		if (notificationType.isEmpty()){
			throw new AssertionError("Notification Type is not Set"); 
		}
		
		saveConfig(notificationType,"0");
		
	}
	
	public void saveDefaultStartUpToTrue() throws SQLException{
		if (notificationType.isEmpty()){
			throw new AssertionError("Notification Type is not Set"); 
		}
		
		saveConfig(notificationType,"1");
		
	}
	
	public void saveConfig(String key, String val) throws SQLException{
		
		connection = ConfigurationJdbc.getPoolConnection().getConnection();
		
		PreparedStatement stm = connection.prepareStatement(sqlUpdateConfig);
		stm.setString(1, val);
		stm.setString(2,key);
		
		int result = stm.executeUpdate();
		
		stm.close();
		connection.close();
	}
	
	public static void startNotifications() throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException{
		
		//run through all providers and invoke start methods of each in separate thread
		for (Class clazz: notificationTypes){
			
			//create object of this class
			Object obj = clazz.newInstance();
			obj.getClass().getMethod("startThread").invoke(obj);
			
		}
	}
	
	public static void registerNoticiation(Class className){
		notificationTypes.add(className);
	}
	
	public abstract void startThread() throws Exception;
}

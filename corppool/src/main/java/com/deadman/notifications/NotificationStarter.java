package com.deadman.notifications;

import java.lang.reflect.InvocationTargetException;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class NotificationStarter  implements ServletContextListener {

	@Override
	public void contextInitialized(ServletContextEvent sce) {
		
		
		ServletContext ctx = sce.getServletContext();
    	
    	String notList = ctx.getInitParameter("notificationtypelist");
    	
    	//list is comman separated
    	for (String type : notList.split(",")){
    		
    		Class clazz;
			try {
				clazz = Class.forName(type);
			 AbstractNotification.registerNoticiation(clazz);
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    	}
    	
    	try {
			AbstractNotification.startNotifications();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		// TODO Auto-generated method stub
		
	}


}

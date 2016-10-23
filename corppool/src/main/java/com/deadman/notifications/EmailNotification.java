package com.deadman.notifications;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.corppool.config.ConfigurationJdbc;
import com.corppool.model.Email;



public class EmailNotification extends AbstractNotification implements Runnable{

	private static boolean isEmailRunning = false;
	
	private static String email_username = "deepansh1987@gmail.com";
	private static String email_password = "9891653996";
	private static String email_host = "smtp.gmail.com";
	
	public EmailNotification(){
		super("EMAIL_START_ON_SYSUP");
		
	}
	
	public void startThread() throws Exception{
		
		loadInitialConfiguration();
		setCredentials();
		new Thread(this).start();
		isEmailRunning = defaultStartOnStartup;
	}

	@Override
	public void run() {
	
		//keep running, till application runs
		try{
			
		
		while(true) {
			
		// stop sending emails when flag is set to stop
		while(isEmailRunning){
			
			try{
				
				try {
					SqlConfig sqlConfig = this.new SqlConfig();
					
					// Get list of pending emails
					Email email = new Email(email_username,email_password,email_host);
					
					if (connection ==null || connection.isClosed()){
						connection = ConfigurationJdbc.getPoolConnection().getConnection();
					}
					
					//get result set
					sqlConfig.ps = connection.prepareStatement(sqlConfig.sqlGetPendingEmailsList);
					
					sqlConfig.rs = sqlConfig.ps.executeQuery();
					
					while(sqlConfig.rs.next()){
						
						// reset email and set fresh data
						email.resetEmail();
						email.setEmail(sqlConfig.rs);
						
						EmailSent emailSent = new EmailSent(sqlConfig.rs);
						
						//now send email
						try{
							if(email.sendEmail())							
							emailSent.status = "Sent";
							 	
							else
							emailSent.status = "Invalid";
							
						}catch(Exception e){
							
							//failed to send email, update status to false
							emailSent.errormessage = "Unable to Send Email";
							emailSent.status       = "Failed";
							emailSent.detailerrormessage = e.getMessage();
						}
						
						//update 
						SqlConfig updRs = this.new SqlConfig();
						
						updRs.ps = connection.prepareStatement(updRs.sqlUpdateEmailSent);
						updRs.ps.setString(1,emailSent.status);
						updRs.ps.setString(2,emailSent.errormessage);
						updRs.ps.setString(3,emailSent.detailerrormessage);
						updRs.ps.setString(4,emailSent.emailsentid);
						
						updRs.ps.executeUpdate();
						
						//now delete pending email
						updRs.ps = connection.prepareStatement(updRs.sqlDeletePendingEmail);
						updRs.ps.setString(1, emailSent.idemailpending);
						
						updRs.ps.executeUpdate();
						// connection.commit();
						
						updRs.closeResources();
					}
					
					
					//reset for new email
					
					
					sqlConfig.closeResources();
					Thread.sleep(5000);
				} catch (InterruptedException e1) {
					// TODO Auto-generated catch block
					
				}  //take break for 5 seconds
				catch (SQLException e) {
					// TODO Auto-generated catch block
					throw new Error(e);
				}
				Thread.sleep(5000);  //take break for 5 seconds
				System.out.println(" There goes another email...");
			}catch(Exception e){
				
			}
		}
		
		System.out.println(" Email sending has Stopped...");
		try {
			Thread.sleep(20000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  //take break for 5 seconds
		}
		
		}catch(Error e){
			e.printStackTrace();
			
			System.out.println(" Email Processed has Stopped unexpectedly ");
			isEmailRunning = false;
		}
		
	}
	
	//
	public static boolean isNotificationRunning(){
		return isEmailRunning;
	}
	
	//
	public static void stopNotification(){
		isEmailRunning = false;
	}
	public static void resumeNotification(){
		isEmailRunning = true;
	}
	
	private void setCredentials() throws SQLException{
		connection = ConfigurationJdbc.getPoolConnection().getConnection();
	}
	
	
	class SqlConfig {
		
		private ResultSet rs = null;
		
		private PreparedStatement ps = null;
		
		private void closeResources(){
			
			if(rs!=null){
				try {
					rs.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if(ps!=null){
				try {
					ps.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		
		private final String sqlGetPendingEmailsList = " select * from emailpending t, emailsent tt where t.sentemailfkid = tt.emailsentid";
		
		private final String sqlDeletePendingEmail  = " delete from emailpending where idemailpending = ?";
		
		private final String sqlGetEmailContent = " Select * from emailsent where emailsentid = ? ";
		
		private final String sqlUpdateEmailSent    = "Update emailsent set status = ?, errormessage = ?, detailerrormessage = ?,"
				+ " datesent = sysdate(), numofattempts = ifnull(numofattempts,0)+1 where emailsentid = ? ";
		
		
		
		
	}
	
	public class EmailSent{
		
		private final String emailsentid;
		private String status;
		private String errormessage;
		private String detailerrormessage;
		private final String idemailpending;
		
		private EmailSent(ResultSet rs) throws SQLException{
			emailsentid = rs.getString("emailsentid");
			idemailpending = rs.getString("idemailpending");
		}
	}
	
}

package com.corppool.model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.apache.commons.lang.text.StrSubstitutor;

public class Email {

	// Recipient's email ID needs to be mentioned.
	public String to = "";

	// Sender's email ID needs to be mentioned
	public String from = "";

	// Sender's email ID needs to be mentioned
	public String cc = "";

	// Sender's email ID needs to be mentioned
	public String body = "";

	// Sender's email ID needs to be mentioned
	public String sub = "";

	// Assuming you are sending email from localhost
	String host = "smtp.gmail.com";

	// Get system properties
	Properties properties = System.getProperties();

	// Get the default Session object.
	Session session = null;

	public Email() {
			}
	
	public Email(final String userName,final String  password, String host){
		
		//
		// Setup mail server
				properties.setProperty("mail.smtp.host", host);
				properties.setProperty("mail.smtp.auth", "true");
				properties.setProperty("mail.smtp.starttls.enable", "true");
				properties.put("mail.smtp.port", "587");

				session = Session.getDefaultInstance(properties,
						new javax.mail.Authenticator() {
							protected PasswordAuthentication getPasswordAuthentication() {
								return new PasswordAuthentication(userName, password);
							}
						});

	}

	/**
	 * Replaces content of template by the value using JSONObject
	 */
	public  void formatEmail(ResultSet resultSet,
			Map<String, String> record) throws SQLException

	{
		System.out.println("formatEmail 1 ");
		// Get next record
		
			System.out.println("formatEmail 2 ");
			// Get to list
			to = getRepalcedString(resultSet.getString("tolist"), record);

			// Get bcc list
			cc = getRepalcedString(resultSet.getString("cclist"), record);

			// get cc list
			from = getRepalcedString(resultSet.getString("FROMEMAIL"), record);

			// get subject
			sub = getRepalcedString(resultSet.getString("subject"), record);

			// get body
			body = getRepalcedString(resultSet.getString("body"), record);
		
	}

	/**
	 * Sending email
	 * 
	 * @throws MessagingException
	 */
	public  boolean sendEmail() throws MessagingException {
		try {

			System.out.println("sendEmail 1 ");
			if (!validateEmail()) {
				System.out.println("sendEmail 2 ");
				return false;
			}
			System.out.println("sendEmail 3 ");
			// Create a default MimeMessage object.
			MimeMessage message = new MimeMessage(session);

			// Set From: header field of the header.
			message.setFrom(new InternetAddress(from));

			// Set To: header field of the header.
			message.addRecipient(Message.RecipientType.TO, new InternetAddress(
					to));

			message.addRecipient(Message.RecipientType.CC, new InternetAddress(
					cc));

			// Set Subject: header field
			message.setSubject(sub);

			// Send the actual HTML message, as big as you like
			message.setContent(body, "text/html");

			// Send message
			Transport.send(message);
			System.out.println("Sent message successfully....");
			return true;
		} catch (MessagingException mex) {
			mex.printStackTrace();
			throw mex;
		}
	}

	/**
	 * Replace content of passed string from corresponding json object TODO:
	 * This is under testing, may not be working fully
	 */
	public String getRepalcedString(String in,
			Map<String, String> recordMap) {

		StrSubstitutor s = new StrSubstitutor(recordMap);

		return s.replace(in);
	}

	/**
	 * Check if email is valid.
	 * 
	 */
	public boolean validateEmail() {
		boolean valid = false;

		if (to == null || to.equalsIgnoreCase("") || sub == null
				|| sub.equalsIgnoreCase("")) {
			valid = false;
		} else {
			valid = true;
		}

		return valid;
	}
	
	public void resetEmail(){
		to = "";
		from = "";
		cc = "";
		body = "";
		sub = "";
	}
	
	public void setEmail(String to, String from, String cc, String body, String sub){
		this.to = to;
		this.from = from;
		this.cc = cc;
		this.body = body;
		this.sub = sub;
	}
	
	public void setEmail(ResultSet resultSet) throws SQLException{
		
		
			// Get to list
			to = resultSet.getString("tolist");

			// Get bcc list
			cc = resultSet.getString("cclist");

			// get cc list
			from = resultSet.getString("FROMEMAIL");

			// get subject
			sub = resultSet.getString("subject");

			// get body
			body = resultSet.getString("body");
	}
}

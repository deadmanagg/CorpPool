package com.corppool.dao;

import com.corppool.model.AppUsers;

public interface AppUsersDAO {

	public void addNewUser(AppUsers user);
	public AppUsers getUserByEmail(String email);
	
	public boolean isValidEmailPassword(String email,String password);
}

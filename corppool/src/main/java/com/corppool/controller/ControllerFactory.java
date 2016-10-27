package com.corppool.controller;

public class ControllerFactory {

	private static ControllerFactory INSTANCE;

	private ControllerFactory() {

	}

	public static ControllerFactory newInstance() {
		if (INSTANCE == null) {
			INSTANCE = new ControllerFactory();
		}
		return INSTANCE;
	}
		
	private FeedsController feedsContrl;

	public synchronized FeedsController getFeedsController() {

		
			if (feedsContrl == null) {
				feedsContrl = new FeedsController();
				feedsContrl.setFactory(this);
			}
		

		return feedsContrl;
	}
	
	private UserController userContrl;

	public synchronized UserController getUserController() {

		
			if (userContrl == null) {
				userContrl = new UserController();
				userContrl.setFactory(this);
			}
		

		return userContrl;
	}
}



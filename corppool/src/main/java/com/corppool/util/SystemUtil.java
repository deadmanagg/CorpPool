package com.corppool.util;

public class SystemUtil {

	
	
	//Return true if running platform is windows
	public static boolean isWindows(){
		boolean isWin = true;
		String OS = System.getProperty("os.name").toLowerCase();
		
		if (OS.indexOf("win") >= 0){
			isWin = true;
		}else{
			isWin = false;
		}
		return isWin;
	}
}

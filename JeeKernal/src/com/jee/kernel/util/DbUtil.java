package com.jee.kernel.util;

import java.util.Date;
import java.util.UUID;

public class DbUtil {
	
	public static String generateGUID() {
		String idString = UUID.randomUUID().toString();
		idString = idString.toUpperCase();
		idString = idString.replaceAll("-", "");
		return idString;
	}
	
	public static Date getCurrentDate(){
		return (new Date());
	}

}

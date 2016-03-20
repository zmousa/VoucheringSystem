package com.jee.kernel.util;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.log4j.Logger;

public class WebUtil {

	public static String absolutePath;
	public static String applicationUrl;

	private static Logger log = Logger.getLogger(WebUtil.class);
	
	public static void setApplicationUrl(String applicationUrl) {
		WebUtil.applicationUrl = applicationUrl;
	}

	public static Date parseDate(String dateFormat, String dateValue) {
		Date date = null;
		if (dateFormat != null && dateValue != null && !dateValue.isEmpty()) {
			try {
				dateValue = dateValue.replace(" ", "");
				SimpleDateFormat format = new SimpleDateFormat(dateFormat);
				date = format.parse(dateValue);
			} catch (Exception e) {
				date = null;
				log.error(">>> Invalid date value " + dateValue
						+ " for this format " + dateFormat);
			}
		}
		return date;
	}

	public static String dateToString(Date date, String format) {
		if (format != null && !"".equals(format))
			return (new SimpleDateFormat(format)).format(date);

		return (new SimpleDateFormat("dd/MM/yyyy")).format(date);
	}
}

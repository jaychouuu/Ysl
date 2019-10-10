package com.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {
	private DateUtil() {

	}

	public static String getTimeStamp() {
		Date date = new Date();
		DateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmssS");
		String timeStamp = dateFormat.format(date);
		return timeStamp;

	}

	public static String getNowTime() {

		Date date = new Date();
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		String nowtime = dateFormat.format(date);

		return nowtime;
	}

	public static String getNowTimeHS() {

		Date date = new Date();
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String nowtime = dateFormat.format(date);

		return nowtime;
	}
}

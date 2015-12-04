package com.realtimestudio.transport.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtils {
	
	public static Date getDate(String sDate, String sFormat) throws ParseException
	{
		SimpleDateFormat  format = new SimpleDateFormat(sFormat);
		return format.parse(sDate);
	}
	
	public static String getDateStr(Date date, String sFormat){
		SimpleDateFormat  format = new SimpleDateFormat(sFormat);
		return format.format(date);
		
	}
	
	public static int getYearDiff(Date d1, Date d2){
		return (int) ((d1.getTime() - d2.getTime())/(1000L*3600*24*365));
	}

}

package com.tr.pvs.core.bean;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TimeUtil {
	private final String[] simpleFormat = {
		"%D,yyyyMMdd",
		"%Y,yyyy",
		"%M,MM",
		"%B,dd",
		"%H,HH",
		"%m,mm",
		"%S,ss",
		"%T,HHmmss",
		"%d,dd"
	};
	
	private SimpleDateFormat df;
	
	public String replaceStringTime(String str) {
		Date date = new Date();
		for(int i = 0; i < simpleFormat.length; i++) {
			String[] reStr = simpleFormat[i].split(",");
			df = new SimpleDateFormat(reStr[1]);
			str = str.replace(reStr[0], df.format(date));
		}
		return str;
	}
	
	public String removeTimeCode(String str) {
		for(int i = 0; i < simpleFormat.length; i++) {
			String[] reStr = simpleFormat[i].split(",");
			str = str.replace(reStr[0], "");
		}
		return str;
	}
	
	public String getDateFormat(String yyyyMMdd, String format) {
		SimpleDateFormat df = new SimpleDateFormat("yyyy/MM/dd");
		SimpleDateFormat df2 = new SimpleDateFormat(format);
		Date date = new Date();
		try {
			date = df.parse(yyyyMMdd);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return df2.format(date);
	}
	
	public String getDateFormat(Long yyyyMMdd, String format) {
		SimpleDateFormat df2 = new SimpleDateFormat(format);
		Date date = new Date(yyyyMMdd);
		return df2.format(date);
	}
	
	public String getDateFormat(Date yyyyMMdd, String format) {
		SimpleDateFormat df2 = new SimpleDateFormat(format);
		return df2.format(yyyyMMdd);
	}
	
	private boolean checkFormat(String stringTime, String format) {
		boolean isResult = false;
		SimpleDateFormat df = new SimpleDateFormat(format);
		Date date = new Date();
		try {
			date = df.parse(stringTime);
			isResult = true;
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			return false;
		}
		return isResult;
	}
	
	public Date getDate(String stringTime, String format) {
		Date date = new Date();
		if(checkFormat(stringTime, format)) {
			SimpleDateFormat df = new SimpleDateFormat(format);
			try {
				date = df.parse(stringTime);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
			}
		}
		return date;
	}
	
	public Date getDate2(String stringTime, String format) throws ParseException {
		SimpleDateFormat df = new SimpleDateFormat(format);
		return df.parse(stringTime);
	}
	
	public String getHour(Date date) {
		df = new SimpleDateFormat("HH");
		return df.format(date);
	}
	
	public String getMinute(Date date) {
		df = new SimpleDateFormat("mm");
		return df.format(date);
	}
	
	public String getHour(Long time) {
		df = new SimpleDateFormat("HH");
		return df.format(time);
	}
	
	public String getMinute(Long time) {
		df = new SimpleDateFormat("mm");
		return df.format(time);
	}
}

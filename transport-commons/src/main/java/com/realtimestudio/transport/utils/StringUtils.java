package com.realtimestudio.transport.utils;

public class StringUtils {
	public static String padString(String str, int total, char c, boolean toLeft){
		if(str==null) str="";
		if(str.length()>total) throw new IllegalArgumentException("String length is longer than the total number");
		
		StringBuilder builder = new StringBuilder();
		for(int i=0; i<total-str.length(); i++){
			builder.append(c);
		}
		if(toLeft) builder.append(str);
		else builder.insert(0, str);
		
		return builder.toString();		
	}
	
	public static String removePadFromString(String str, char c, boolean fromLeft){
		if(str == null || str.isEmpty()) return str;
		if(fromLeft){
			int start = 0;
			while(str.charAt(start) == c){
				start++;
			}
			return str.substring(start);
		}
		else{
			int end = str.length()-1;
			while(str.charAt(end) == c){
				end--;
			}
			return str.substring(0, end+1);
		}
	}
	
	public static String padInteger(int num, int total, char c, boolean toLeft){
		return padString(String.valueOf(num), total, c, toLeft);
	}
	
	public static String padShort(short num, int total, char c, boolean toLeft){
		return padString(String.valueOf(num), total, c, toLeft);
	}
	
	public static int parseIntOrDefault(String str, int defVal){
		try{
			return Integer.parseInt(str);
		}
		catch(NumberFormatException e){
			return defVal;
		}
	}
	
	public static boolean parseBooleanOrDefault(String str, boolean defVal){
		try{
			return Boolean.parseBoolean(str);
		}
		catch(NumberFormatException e){
			return defVal;
		}
	}
	
}

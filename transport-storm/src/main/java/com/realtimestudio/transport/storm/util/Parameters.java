package com.realtimestudio.transport.storm.util;

import java.io.IOException;
import java.util.Properties;

public class Parameters {
	private final static String PROPERTIESFILENAME = "storm.properties";
	private final static Properties properties =new Properties();
	
	static{
		try {
			properties.load(ClassLoader.getSystemResourceAsStream(PROPERTIESFILENAME));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static String getProperty (String name){
		return properties.getProperty(name);
	}
	

}

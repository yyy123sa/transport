package com.realtimestudio.transport.event.gps;

import java.util.HashMap;
import java.util.Map;

public enum FieldType {
	STRING("String"), INTEGER("Integer"), SHORT("Short"), DATE("Date")
	, DIRECTION("Direction"), WEATHER("Weather"), FLOAT("Float");
	
	private String type;
	private FieldType(String type){
		this.type = type;
	}
	
	private static Map<String, FieldType> map;
	static{
		map = new HashMap<String, FieldType>();
		for(FieldType fieldType : FieldType.values()){
			map.put(fieldType.type, fieldType);
		}
	}
	public static FieldType getFieldType(String type){
		return map.get(type);
	}

}

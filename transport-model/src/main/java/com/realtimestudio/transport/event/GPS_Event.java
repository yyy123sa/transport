package com.realtimestudio.transport.event;

import static com.realtimestudio.transport.utils.CSVUtils.getRecordsFromFile;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.BiMap;
import com.google.common.collect.ImmutableBiMap;

public class GPS_Event {
	private final static Logger LOGGER = LoggerFactory.getLogger(GPS_Event.class);
	private static BiMap<String, GPS_Event> statusProtoMap ;
	private final static String[] statusProtoCSVHeader = { "name", "value",	"type" };
	private final static String statusProtolCSVName = "GPS_Shaige_Status_Protocol.csv";
	static{
		try (Reader statusFileReader = new InputStreamReader(
				GPS_Event.class.getClassLoader().getResourceAsStream(statusProtolCSVName));) {
			
			List<Map<String, String>> statusRecords = getRecordsFromFile(
					statusFileReader, statusProtoCSVHeader, true);
			ImmutableBiMap.Builder<String, GPS_Event> immutableBiBuilder = new ImmutableBiMap.Builder<>();
			for (Map<String, String> record : statusRecords) {
				GPS_Event event = new GPS_Event(
						record.get(statusProtoCSVHeader[0]),
						Integer.parseInt(record.get(statusProtoCSVHeader[2])));
				immutableBiBuilder.put(record.get(statusProtoCSVHeader[1]), event);
			}
			statusProtoMap = immutableBiBuilder.build();
			LOGGER.debug("status mapping input file is parsed");

		} catch (FileNotFoundException e) {
			LOGGER.error("The status protocol input files are not available", e);
			throw new RuntimeException(e);
		} catch (IOException e) {
			LOGGER.error("The status protocol input files can not be parsed", e);
			throw new RuntimeException(e);
		}
		
	}
	
	public static BiMap<String, GPS_Event> getStatusMap(){
		return statusProtoMap;
	}
	
	
	private String name;
	private GPS_Event_Type type;
	
	public GPS_Event(String name, GPS_Event_Type type){
		this.name=name;
		this.type=type;
	}
	
	public GPS_Event(String name, int iType){
		this.name=name;
		this.type=GPS_Event_Type.getType(iType);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((type == null) ? 0 : type.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		GPS_Event other = (GPS_Event) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (type != other.type)
			return false;
		return true;
	}
	
	@Override
	public String toString(){
		return name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public GPS_Event_Type getType() {
		return type;
	}

	public void setType(GPS_Event_Type type) {
		this.type = type;
	}
	
	

}

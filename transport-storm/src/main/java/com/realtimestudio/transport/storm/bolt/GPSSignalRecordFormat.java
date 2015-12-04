package com.realtimestudio.transport.storm.bolt;

import java.sql.Timestamp;

import org.apache.storm.hdfs.bolt.format.RecordFormat;

import backtype.storm.tuple.Tuple;

import com.realtimestudio.transport.event.GPS_Event;
import com.realtimestudio.transport.model.RoutePoint;
import com.realtimestudio.transport.storm.util.Constants;

public class GPSSignalRecordFormat implements RecordFormat{
	
	private static final long serialVersionUID = -8973186798469864025L;
	
    public static final String DEFAULT_FIELD_DELIMITER = ",";
    public static final String DEFAULT_RECORD_DELIMITER = "\n";
    private String fieldDelimiter = DEFAULT_FIELD_DELIMITER;
    private String recordDelimiter = DEFAULT_RECORD_DELIMITER;
    
    public GPSSignalRecordFormat withFieldDelimiter(String delimiter){
        this.fieldDelimiter = delimiter;
        return this;
    }
    
    public GPSSignalRecordFormat withRecordDelimiter(String delimiter){
        this.recordDelimiter = delimiter;
        return this;
    }

	@Override
	public byte[] format(Tuple tuple) {
		StringBuilder sb = new StringBuilder();
		sb.append(tuple.getValueByField(Constants.CARIDFIELD));
		sb.append(fieldDelimiter);
		
		RoutePoint point = (RoutePoint) tuple.getValueByField(Constants.ROUTEPOINTFIELD);
		sb.append(new Timestamp(point.getTimestamp()) );
		sb.append(fieldDelimiter);
		sb.append(point.getLocation().getLatitude());
		sb.append(fieldDelimiter);
		sb.append(point.getLocation().getLongitude());
		sb.append(fieldDelimiter);
		sb.append(point.getSpeed());
		sb.append(fieldDelimiter);
		sb.append(point.getGasVol());
		sb.append(fieldDelimiter);
		sb.append(point.getWeather().toString());
		sb.append(fieldDelimiter);
		sb.append(point.getPressure());
		sb.append(fieldDelimiter);
		
		int lanecross = 0;
		
		for(GPS_Event event : point.getEvents()){
			if(Constants.LANECROSSING.equals(event.toString())){
				lanecross = 1;
				break;
			}				
		}
		
		sb.append(lanecross);
		sb.append(recordDelimiter);
		//System.out.println(sb.toString());
		
		return sb.toString().getBytes();
		
		
	}

}

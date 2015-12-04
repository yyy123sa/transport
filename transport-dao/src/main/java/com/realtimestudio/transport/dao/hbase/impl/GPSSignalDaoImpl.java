package com.realtimestudio.transport.dao.hbase.impl;

import static com.realtimestudio.transport.dao.Constants.DATA_FAMILY_H;
import static com.realtimestudio.transport.dao.Constants.GAS_H;
import static com.realtimestudio.transport.dao.Constants.GPSSIGNAL_TAB;
import static com.realtimestudio.transport.dao.Constants.LATI_H;
import static com.realtimestudio.transport.dao.Constants.LONG_H;
import static com.realtimestudio.transport.dao.Constants.PRESSURE_H;
import static com.realtimestudio.transport.dao.Constants.SPEED_H;
import static com.realtimestudio.transport.dao.Constants.WEATHER_H;

import java.util.HashSet;
import java.util.Set;

import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.util.Bytes;
import org.springframework.stereotype.Repository;

import com.google.common.collect.BiMap;
import com.realtimestudio.transport.dao.GPSSignalDao;
import com.realtimestudio.transport.event.GPS_Event;
import com.realtimestudio.transport.model.Location;
import com.realtimestudio.transport.model.RoutePoint;
import com.realtimestudio.transport.model.Weather;

@Repository
public class GPSSignalDaoImpl extends CommonDaoImpl<RoutePoint> implements GPSSignalDao {
	private final String tableName = GPSSIGNAL_TAB;
	
    @Override
	protected RoutePoint parseResult(Result result) {
    	if(result==null) return null;
    	
       float lon = Bytes.toFloat(result.getValue(DATA_FAMILY_H, LONG_H));
    	float lat = Bytes.toFloat(result.getValue(DATA_FAMILY_H, LATI_H));
    	short gas = Bytes.toShort(result.getValue(DATA_FAMILY_H, GAS_H));
    	float pressure = Bytes.toFloat(result.getValue(DATA_FAMILY_H, PRESSURE_H));
    	short speed  = Bytes.toShort(result.getValue(DATA_FAMILY_H, SPEED_H));
    	String weather = Bytes.toString(result.getValue(DATA_FAMILY_H, WEATHER_H));
    	long timestamp = result.listCells().get(0).getTimestamp();
    	
    	BiMap<String, GPS_Event> eventMap = GPS_Event.getStatusMap();
    	Set<GPS_Event> events = new HashSet<>();
    	for(GPS_Event event : eventMap.values()){
    		if (result.getValue(DATA_FAMILY_H, Bytes.toBytes(event.toString())) != null){
    			events.add(event);
    		}
    			
    	}
    	
    	return new RoutePoint(new Location(lon, lat), timestamp, speed,
    			null /*direction*/
    			, gas, Weather.valueOf(weather), pressure, events);
    	
	}

	@Override
	protected String getTableName() {
		return tableName;
	}

	@Override
	protected Put buildRow(String carid, RoutePoint point) {
		Put put = new Put(Bytes.toBytes(carid), point.getTimestamp());
		
        put.addColumn(DATA_FAMILY_H, LONG_H, Bytes.toBytes(point.getLocation().getLongitude()));
        put.addColumn(DATA_FAMILY_H, LATI_H, Bytes.toBytes(point.getLocation().getLatitude()));
        put.addColumn(DATA_FAMILY_H, GAS_H, Bytes.toBytes(point.getGasVol()));
        put.addColumn(DATA_FAMILY_H, WEATHER_H, Bytes.toBytes(point.getWeather().toString()));
        put.addColumn(DATA_FAMILY_H, SPEED_H, Bytes.toBytes(point.getSpeed()));
        put.addColumn(DATA_FAMILY_H, PRESSURE_H, Bytes.toBytes(point.getPressure()));
        
        for(GPS_Event event : point.getEvents()){
        	put.addColumn(DATA_FAMILY_H, Bytes.toBytes(event.toString()), Bytes.toBytes(1));
        }

		
		return put;
	}

}

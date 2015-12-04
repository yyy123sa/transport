package com.realtimestudio.transport.event.gps;

import static com.realtimestudio.transport.event.gps.GPSSignalProtocolConfig.DATEFORMAT;
import static com.realtimestudio.transport.event.gps.GPSSignalProtocolConfig.DEFAULTPACKETTYPE;
import static com.realtimestudio.transport.event.gps.GPSSignalProtocolConfig.DIRECTION;
import static com.realtimestudio.transport.event.gps.GPSSignalProtocolConfig.GAS;
import static com.realtimestudio.transport.event.gps.GPSSignalProtocolConfig.GPS_LAT;
import static com.realtimestudio.transport.event.gps.GPSSignalProtocolConfig.GPS_LON;
import static com.realtimestudio.transport.event.gps.GPSSignalProtocolConfig.GPS_TIME;
import static com.realtimestudio.transport.event.gps.GPSSignalProtocolConfig.PACKET_HEAD;
import static com.realtimestudio.transport.event.gps.GPSSignalProtocolConfig.PACKET_TAIL;
import static com.realtimestudio.transport.event.gps.GPSSignalProtocolConfig.PACKET_TYPE;
import static com.realtimestudio.transport.event.gps.GPSSignalProtocolConfig.PRESSURE;
import static com.realtimestudio.transport.event.gps.GPSSignalProtocolConfig.SIM_NO;
import static com.realtimestudio.transport.event.gps.GPSSignalProtocolConfig.SPEED;
import static com.realtimestudio.transport.event.gps.GPSSignalProtocolConfig.STATUS;
import static com.realtimestudio.transport.event.gps.GPSSignalProtocolConfig.WEATHER;
import static com.realtimestudio.transport.event.gps.GPSSignalProtocolConfig.getSignalProtoMap;
import static com.realtimestudio.transport.utils.DateUtils.getDateStr;
import static com.realtimestudio.transport.utils.StringUtils.padInteger;
import static com.realtimestudio.transport.utils.StringUtils.padShort;
import static com.realtimestudio.transport.utils.StringUtils.padString;

import java.util.Date;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.BiMap;
import com.realtimestudio.transport.event.GPSSignalFormatter;
import com.realtimestudio.transport.event.GPS_Event;
import com.realtimestudio.transport.model.RoutePoint;

public class GPSSignalFormatterImpl implements GPSSignalFormatter {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(GPSSignalFormatterImpl.class);
	
	private String carID;
	private RoutePoint point;
	private StringBuilder builder;
	
	public GPSSignalFormatterImpl(String carID, RoutePoint point){
		this.carID = carID;
		this.point = point;
		builder = new StringBuilder();
		
		buildSignal();
	}
	
	private String formatLocationPoint(float f){
		String str = String.format("%.5f", f);
		if(str.charAt(0) !='-') str = "+" + str;
		return str;
	}

	private void buildSignal() {
		for (Map.Entry<String, Integer> entry : getSignalProtoMap().entrySet()) {
			int len = entry.getValue();
			switch (entry.getKey()) {
			case PACKET_HEAD:
				builder.append("(");
				break;
			case PACKET_TYPE:
				builder.append(DEFAULTPACKETTYPE);
				break;
			case PACKET_TAIL:
				builder.append(")");
				break; 
			case SIM_NO:
				builder.append(padString(carID, len, '0', true));
				break;
			case GPS_TIME:
				builder.append(getDateStr(new Date(point.getTimestamp()), DATEFORMAT));
				break;
			case GPS_LAT:
				builder.append(padString(formatLocationPoint(point.getLocation().getLatitude()), len, '0', false));
				break;
			case GPS_LON:
				builder.append(padString(formatLocationPoint(point.getLocation().getLongitude()), len, '0', false));
				break;
			case SPEED:
				builder.append(padShort(point.getSpeed(), len, '0', true));
				break;
			case DIRECTION:
				builder.append(padShort(point.getDirection().getValue(), len, '0', true));				
				break;
			case GAS:
				builder.append(padShort(point.getGasVol(), len, '0', true));
				break;
			case WEATHER:
				builder.append(padInteger(point.getWeather().getId(), len, '0', true));
				break;
			case PRESSURE:
				builder.append(padString(String.format("%.2f", point.getPressure()), len, '0', false));
				break;
			case STATUS:
				buildStatuses();
				break;
			}
		}

	}
	
	private void buildStatuses(){
		BiMap<GPS_Event, String> map = GPS_Event.getStatusMap().inverse();
		for(GPS_Event event : point.getEvents()){
			builder.append(map.get(event));
		}		
	}

	@Override
	public String getSignal() {
		return builder.toString();
	}

}

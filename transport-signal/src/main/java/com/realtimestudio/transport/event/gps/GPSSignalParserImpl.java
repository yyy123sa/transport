package com.realtimestudio.transport.event.gps;

import static com.realtimestudio.transport.event.gps.GPSSignalProtocolConfig.DATEFORMAT;
import static com.realtimestudio.transport.event.gps.GPSSignalProtocolConfig.DIRECTION;
import static com.realtimestudio.transport.event.gps.GPSSignalProtocolConfig.GAS;
import static com.realtimestudio.transport.event.gps.GPSSignalProtocolConfig.GPS_LAT;
import static com.realtimestudio.transport.event.gps.GPSSignalProtocolConfig.GPS_LON;
import static com.realtimestudio.transport.event.gps.GPSSignalProtocolConfig.GPS_TIME;
import static com.realtimestudio.transport.event.gps.GPSSignalProtocolConfig.PRESSURE;
import static com.realtimestudio.transport.event.gps.GPSSignalProtocolConfig.SEPERATORESCAPED;
import static com.realtimestudio.transport.event.gps.GPSSignalProtocolConfig.SIM_NO;
import static com.realtimestudio.transport.event.gps.GPSSignalProtocolConfig.SPEED;
import static com.realtimestudio.transport.event.gps.GPSSignalProtocolConfig.WEATHER;
import static com.realtimestudio.transport.event.gps.GPSSignalProtocolConfig.getFieldDescList;
import static com.realtimestudio.transport.utils.DateUtils.getDate;

import java.text.ParseException;
import java.util.HashSet;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.ImmutableList;
import com.realtimestudio.transport.event.GPSSignalParser;
import com.realtimestudio.transport.event.GPS_Event;
import com.realtimestudio.transport.model.Direction;
import com.realtimestudio.transport.model.Location;
import com.realtimestudio.transport.model.RoutePoint;
import com.realtimestudio.transport.model.Weather;

public class GPSSignalParserImpl implements GPSSignalParser {
	private static final Logger LOGGER = LoggerFactory.getLogger(GPSSignalParserImpl.class);

	private String carID;
	private RoutePoint routePoint;

	private String[] fields;

	private Set<GPS_Event> events;

	public GPSSignalParserImpl(String signal) throws ParseException {
		fields = signal.split(SEPERATORESCAPED);
		events = new HashSet<>();
		
		parse();
	}

	private void parse() throws ParseException {
		long timestamp = 0L;
		float lat = 0F;
		float lon = 0F;
		short speed = 0;
		Direction direction = null;
		short gasVol = 0;
		Weather weather = null;
		float pressure = 0;
		
		ImmutableList<Field> fieldsDesc = getFieldDescList();
		int i = 0;
		for (i=0; i<fieldsDesc.size(); i++) {	
			String str = fields[i];
			switch (fieldsDesc.get(i).getName()) {
			case SIM_NO:
				carID = str;
				break;
			case GPS_TIME:
				timestamp = getDate(str, DATEFORMAT).getTime();
				break;
			case GPS_LAT:
				lat = Float.parseFloat(str);
				break;
			case GPS_LON:
				lon = Float.parseFloat(str);
				break;
			case SPEED:
				speed = Short.parseShort(str);
				break;
			case DIRECTION:
				direction = new Direction(Short.parseShort(str));
				break;
			case GAS:
				gasVol = Short.parseShort(str);
				break;
			case WEATHER:
				weather = Weather.getWeather(Integer.parseInt(str));
				break;
			case PRESSURE:
				pressure = Float.parseFloat(str);
				break;					
			}
		}

		routePoint = new RoutePoint(new Location(lon, lat), timestamp, speed,
				direction, gasVol, weather, pressure, events);
		
		parseStatuses(i);

	}

	private void parseStatuses(int start) {
		if (start>=fields.length)
			return; // no status information


		for(int i=start; i<fields.length; i++) {
			String str = fields[i];
			GPS_Event event = GPS_Event.getStatusMap().get(str);
			if (event == null) LOGGER.error("Not supported status signal: " + str);
			else events.add(event);
		}
	}

	@Override
	public String getCarID() {
		return carID;
	}

	@Override
	public RoutePoint getRoutePoint() {
		return routePoint;
	}

}

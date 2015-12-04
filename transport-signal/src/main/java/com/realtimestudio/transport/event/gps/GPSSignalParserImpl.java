package com.realtimestudio.transport.event.gps;

import static com.realtimestudio.transport.event.gps.GPSSignalProtocolConfig.DATEFORMAT;
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
import static com.realtimestudio.transport.utils.DateUtils.getDate;
import static com.realtimestudio.transport.utils.StringUtils.removePadFromString;

import java.text.ParseException;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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

	private String signal;
	private int pos;
	private int statusLength;
	private Set<GPS_Event> events;

	public GPSSignalParserImpl(String signal) throws ParseException {
		this.signal = signal;
		pos = 0;
		statusLength = 0;
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
		
		for (Map.Entry<String, Integer> entry : getSignalProtoMap().entrySet()) {
			int len = entry.getValue();
			String str = null;
			String fieldName = entry.getKey();
			if(!STATUS.equals(fieldName)){
				str = signal.substring(pos, pos + len);
			}
			
			switch (fieldName) {
			case PACKET_HEAD:
			case PACKET_TYPE:
			case PACKET_TAIL:
				pos += len;
				break; // skip
			case SIM_NO:
				carID = removePadFromString(str, '0', true);
				pos += len;
				break;
			case GPS_TIME:
				timestamp = getDate(str, DATEFORMAT).getTime();
				pos += len;
				break;
			case GPS_LAT:
				lat = Float.parseFloat(str);
				pos += len;
				break;
			case GPS_LON:
				lon = Float.parseFloat(str);
				pos += len;
				break;
			case SPEED:
				speed = Short.parseShort(str);
				pos += len;
				break;
			case DIRECTION:
				direction = new Direction(Short.parseShort(str));
				pos += len;
				break;
			case GAS:
				gasVol = Short.parseShort(str);
				pos += len;
				break;
			case WEATHER:
				weather = Weather.getWeather(Integer.parseInt(str));
				pos += len;
				break;
			case PRESSURE:
				pressure = Float.parseFloat(str);
				pos += len;
				break;					
			case STATUS:
				statusLength = len;
				parseStatuses();
				break;
			}
		}

		routePoint = new RoutePoint(new Location(lon, lat), timestamp, speed,
				direction, gasVol, weather, pressure, events);

	}

	private void parseStatuses() {
		if (statusLength == 0)
			return; // no status information


		while (pos + statusLength <= signal.length()) {
			String str = signal.substring(pos, pos + statusLength);
			pos += statusLength;
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

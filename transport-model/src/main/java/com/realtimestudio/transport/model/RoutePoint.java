package com.realtimestudio.transport.model;

import static com.realtimestudio.transport.utils.DateUtils.getDateStr;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

import com.realtimestudio.transport.event.GPS_Event;

public class RoutePoint implements Serializable{
	private static final long serialVersionUID = 1773071526460852967L;
	
	private Location location;
	private long timestamp;
	private short speed;
	private Direction direction;
	private short gasVol;
	private Weather weather;
	private float pressure;
	
	private Set<GPS_Event> events;
	
	public RoutePoint(){}
	
	public RoutePoint(Location location, long timestamp, short speed,
			Direction direction, short gasVol, Weather weather, float pressure, Set<GPS_Event> events) {
		super();
		this.location = location;
		this.timestamp = timestamp;
		this.speed = speed;
		this.direction = direction;
		this.gasVol = gasVol;
		this.weather = weather;
		this.pressure = pressure;
		
		this.events = events;
	}

	public Location getLocation() {
		return location;
	}

	public long getTimestamp() {
		return timestamp;
	}

	public short getSpeed() {
		return speed;
	}

	public Direction getDirection() {
		return direction;
	}

	public short getGasVol() {
		return gasVol;
	}

	public Set<GPS_Event> getEvents() {
		return events;
	}
	
	public void setLocation(Location location) {
		this.location = location;
	}

	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}

	public void setSpeed(short speed) {
		this.speed = speed;
	}

	public void setDirection(Direction direction) {
		this.direction = direction;
	}

	public void setGasVol(short gasVol) {
		this.gasVol = gasVol;
	}

	public void setEvents(Set<GPS_Event> events) {
		this.events = events;
	}
	
	public void setWeather(Weather weather){
		this.weather = weather;
	}
	
	public Weather getWeather(){
		return weather;
	}
	
	public void setPressure (float pressure){
		this.pressure = pressure;
	}
	
	public float getPressure(){
		return pressure;
	}

	@Override
	public String toString(){
		return String.format("Location=%s, Timestamp=%s, Speed=%dkm/h, Direction=%s, gas=%dL, weather=%s, pressure=%fPSI,events=%s"
				,location.toString()
				,getDateStr(new Date(timestamp), "yyyy-MM-dd,HH:mm:ss")
				,speed
				,direction
				,gasVol
				,weather.toString()
				,pressure
				,events.toString());
	}
	
}

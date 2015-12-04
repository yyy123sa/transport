package com.realtimestudio.transport.event.simulation.Generator.Impl;
/*
 * GAS: Starting from 40+[0,40). Minus 1 for each move
 * Speed: 90 + riskFactor/3 + [0,30)
 */

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import com.realtimestudio.transport.event.gps.GPSSignalFormatterImpl;
import com.realtimestudio.transport.event.simulation.Generator.SignalGenerator;
import com.realtimestudio.transport.model.Car;
import com.realtimestudio.transport.model.Direction;
import com.realtimestudio.transport.model.Driver;
import com.realtimestudio.transport.model.Location;
import com.realtimestudio.transport.model.RoutePoint;
import com.realtimestudio.transport.model.Weather;

public class SignalGeneratorImpl implements SignalGenerator{
	private Driver driver;
	private Car car;
	private List<Location> route;
	private Random random;
	private List<RoutePoint> points;
	private int len;
	private Weather weather;
	
	private static final short BASICGAS = 40;
	private static final short DECREGAS = 1;
	
	private static final short BASICSPEED = 90;
	private static final short SPEEDCHAGERANGE = 30;
	private static final short RISKFACTORDIVIDERSPEED = 3;
	
	private static final float BASICPRESSURE = 32.0F;
	private static final int PRESSURERANGE = 3;
	private static final float DECREPRESSURE = 0.01F;
	
	private static final int TIMEINTERVAL = 60*60*1000;
	
	
	public SignalGeneratorImpl(Driver driver, Car car, List<Location> route){
		this.driver = driver;
		this.car = car;
		this.route = route;
		random = new Random(new Date().getTime());
		len = route.size();
		
		weather = Weather.getWeather(1+random.nextInt(Weather.values().length));
	}
	
	private long getTimestamp(int i){
		//if(i==0) return new Date().getTime();
		//return points.get(i-1).getTimestamp()+TIMEINTERVAL; //every one min
		return new Date().getTime();
	}
	
	private short getSpeed(int i){
		return (short) (BASICSPEED + driver.getAdjRiskFactor()/RISKFACTORDIVIDERSPEED + random.nextInt(SPEEDCHAGERANGE));
	}
	
	private Direction getDirection(int i){
		if(i == len-1){
			return new Direction((short) 0);
		}
		
		float x = route.get(i+1).getLongitude() - route.get(i).getLongitude();
		float y = route.get(i+1).getLatitude() - route.get(i).getLatitude();
		
		short degree = 0;
		
		if(y>0) degree =  (short) ((short) (Math.atan(x/y) + 360) % 360);
		else if(y<0) degree = (short) ((short) Math.atan(x/y) + 180);
		else if(x>0) degree = 90;
		else if(x<0) degree = 270;
		else degree = 0;
		
		return new Direction(degree);			
	}
	
	private short getGasVol(int i){
		if(i==0) return (short) (BASICGAS+random.nextInt(BASICGAS));
		short prev = points.get(i-1).getGasVol();
		return (short) (prev - DECREGAS);		
	}
	
	private Weather getWeather(){
		return weather;
	}
	
	private float getPressure(int i){
		if(i==0) return BASICPRESSURE + random.nextInt(PRESSURERANGE+1);
		float prev = points.get(i-1).getPressure();
		return prev - DECREPRESSURE;
				
	}
	@Override
	public List<String> generateSignals(){
		List<String> signals = new ArrayList<>();
		points = new ArrayList<>(route.size());
		DriverEventGeneratorImpl eventGenerator = new DriverEventGeneratorImpl(driver);
		
		for(int i=0; i<len; i++){
			RoutePoint point = new RoutePoint();
			point.setLocation(route.get(i));
			point.setDirection(getDirection(i));
			point.setEvents(eventGenerator.generateEvents());
			point.setGasVol(getGasVol(i));
			point.setSpeed(getSpeed(i));
			point.setTimestamp(getTimestamp(i));
			point.setWeather(getWeather());
			point.setPressure(getPressure(i));
			points.add(point);
			
			String signal = new GPSSignalFormatterImpl(car.getId(), point).getSignal();
			signals.add(signal);
		}
		
		return signals;		
	}

}

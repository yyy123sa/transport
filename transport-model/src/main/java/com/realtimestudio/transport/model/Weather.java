package com.realtimestudio.transport.model;

public enum Weather {
	SUNNY(1), RAINING(2), FOGGY(3);
	
	private int id;

	
	private Weather(int id){
		this.id = id;
	}
	
	public int getId(){
		return id;
	}
	
	public static Weather getWeather(int i){
		for(Weather weather : Weather.values()){
			if(weather.id == i) return weather;
		}
		return null;
	}
	

}

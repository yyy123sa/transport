package com.realtimestudio.transport.model;

public class Location {
	private float longitude;
	private float latitude;
	
	public Location(float longitude, float latitude) {
		super();
		this.longitude = longitude;
		this.latitude = latitude;
	}

	public float getLongitude() {
		return longitude;
	}

	public float getLatitude() {
		return latitude;
	}
	
	@Override
	public String toString(){
		StringBuilder builder = new StringBuilder();
		
		if(longitude>0) builder.append("E");
		else builder.append("W");
		builder.append(String.format("%.5f", Math.abs(longitude)));
		builder.append(",");
		
		if(latitude>0) builder.append("N");
		else builder.append("S");
		builder.append(String.format("%.5f", Math.abs(latitude)));
		
		return builder.toString();

	}

}

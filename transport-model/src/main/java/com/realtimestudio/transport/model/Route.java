package com.realtimestudio.transport.model;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class Route {
	private String start;
	private String end;
	private double distance;
	
	private List<Location> locations;

	public Route(String start, String end, double distance, List<Location> locations) {
		super();
		this.start = start;
		this.end = end;
		this.setDistance(distance);
		this.locations = locations;
	}

	public String getStart() {
		return start;
	}

	public void setStart(String start) {
		this.start = start;
	}

	public String getEnd() {
		return end;
	}

	public void setEnd(String end) {
		this.end = end;
	}

	public List<Location> getLocations() {
		return locations;
	}

	public void setRoutePoints(List<Location> locations) {
		this.locations = locations;
	}
	
	public double getDistance() {
		return distance;
	}

	public void setDistance(double distance) {
		this.distance = distance;
	}

	public void reverse(){
		String temp = start;
		start = end;
		end = temp;
		
		Collections.reverse(locations);
	}
	
	@Override
	public String toString(){
		return String.format("start=%s; end=%s; distance=%f; firstLocation=%s; lastLocation=%s"
				, start, end, distance, locations.get(0).toString(), locations.get(locations.size()-1).toString());
	}
}

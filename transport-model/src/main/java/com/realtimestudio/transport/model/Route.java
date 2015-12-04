package com.realtimestudio.transport.model;

import java.util.LinkedList;
import java.util.List;

public class Route {
	private String carID;
	private RoutePoint currentPoint;
	private boolean isCompleted;
	
	private List<RoutePoint> routePoints;
	
	public Route(String carID, RoutePoint point){
		this.carID = carID;
		this.currentPoint = point;
		isCompleted = false;
	}
	
	public void completeRoute(){
		isCompleted = true;
	}
	
	public void addRoutePoint(RoutePoint point){
		if(routePoints == null){
			routePoints = new LinkedList<>();
		}
		routePoints.add(point);
	}
	
	public void insertRoutePoint(RoutePoint point){
		if(routePoints == null){
			routePoints = new LinkedList<>();
		}
		routePoints.add(0, point);
	}
	

}

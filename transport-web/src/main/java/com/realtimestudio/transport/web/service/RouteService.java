package com.realtimestudio.transport.web.service;

import com.realtimestudio.transport.model.RoutePoint;

public interface RouteService {
	RoutePoint getLatestRoutePoint(String carID);

}

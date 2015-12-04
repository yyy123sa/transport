package com.realtimestudio.transport.web.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.realtimestudio.transport.dao.GPSSignalDao;
import com.realtimestudio.transport.model.RoutePoint;
import com.realtimestudio.transport.web.service.RouteService;

@Service
public class RouteServiceImp implements RouteService {
	@Autowired
	private GPSSignalDao signalDao;

	@Override
	public RoutePoint getLatestRoutePoint(String carID) {
		return signalDao.findById(carID);
	}

}

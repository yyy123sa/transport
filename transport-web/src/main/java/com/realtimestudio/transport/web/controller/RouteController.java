package com.realtimestudio.transport.web.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.realtimestudio.transport.model.RoutePoint;
import com.realtimestudio.transport.web.service.RouteService;

@RestController
@RequestMapping("/transport/{carID}")
public class RouteController {
	private static final Logger LOGGER = LoggerFactory.getLogger(RouteController.class);
	
	@Autowired
	private RouteService routeService;
	
	@RequestMapping(value = "/lastlocation", method = RequestMethod.GET)
	public ResponseEntity<RoutePoint> getLastLocation(@PathVariable String carID){
		   LOGGER.debug("Request: getLastLocation: " + carID);
	       RoutePoint point = routeService.getLatestRoutePoint(carID);
	       if(point == null){
	    	   LOGGER.error("getLastLocation can not find the car: " + carID);
	    	   return new ResponseEntity<RoutePoint>(HttpStatus.NOT_FOUND);
	       }
	       LOGGER.trace("Response: getLastLocation: "+ point.toString());
	       return new ResponseEntity<RoutePoint>(point, HttpStatus.OK);

	}
	
}

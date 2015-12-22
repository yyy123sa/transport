package com.realtimestudio.transport.web.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.realtimestudio.transport.model.Car;
import com.realtimestudio.transport.web.service.CarService;

@RestController
@RequestMapping("/transport")
public class CarController {
	private static final Logger LOGGER = LoggerFactory.getLogger(CarController.class);
	
	@Autowired
	private CarService carService;
	
	@RequestMapping(method = RequestMethod.GET)
	public List<Car> getAllCars(){
		LOGGER.debug("Request: getAllCars");
		return carService.getAllCars();
	}
	
	@RequestMapping(value = "/{carID}", method = RequestMethod.GET)
	public ResponseEntity<Car> getCar(@PathVariable String carID){
		LOGGER.debug("Request: getCar for " + carID);
		Car car = carService.getCar(carID);
		if(car == null) {
			LOGGER.error("getCar can not find car: " + carID);
			return new ResponseEntity<Car>(HttpStatus.NOT_FOUND);
		}
		LOGGER.trace("Response: " + car.toString());
		return new ResponseEntity<Car>(car, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/car", method = RequestMethod.PUT)
	public ResponseEntity<Car> updateCar(@RequestBody Car car){
		LOGGER.debug("Request: updateCar " + car);
		carService.updateCar(car);
		return new ResponseEntity<Car>(car, HttpStatus.OK);
		
	}

}

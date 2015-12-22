package com.realtimestudio.transport.web.service;

import java.util.List;

import com.realtimestudio.transport.model.Car;

public interface CarService {
	List<Car> getAllCars();
	Car getCar(String carID);
	void updateCar(Car car);
}

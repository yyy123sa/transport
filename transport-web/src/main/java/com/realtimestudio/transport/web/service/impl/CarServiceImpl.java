package com.realtimestudio.transport.web.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.realtimestudio.transport.dao.CarDao;
import com.realtimestudio.transport.model.Car;
import com.realtimestudio.transport.web.service.CarService;

@Service
public class CarServiceImpl implements CarService {
	@Autowired
	private CarDao carDao;

	@Override
	public List<Car> getAllCars()  {
		Map<String, Car> map = carDao.findAll();
		return new ArrayList<Car>(map.values());
	}


	@Override
	public Car getCar(String carID)  {
		return carDao.findById(carID);
	}


	@Override
	public void updateCar(Car car) {
		// TODO Auto-generated method stub
		carDao.put(car.getId(), car);
	}
	
	

}

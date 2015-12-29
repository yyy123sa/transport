package com.realtimestudio.transport.event.simulation.preparer.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import com.realtimestudio.transport.dao.CarDao;
import com.realtimestudio.transport.event.simulation.config.SimulationConfiguration;
import com.realtimestudio.transport.event.simulation.preparer.SimulatorPreparer;
import com.realtimestudio.transport.model.Car;
import com.realtimestudio.transport.model.Driver;

@Component
@Order(1)
public class DBPreparer implements SimulatorPreparer {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(DBPreparer.class);
	
	@Autowired
	private CarDao carDao;

	@Override
	public void prepare() {
		LOGGER.info("Insert car information into database");
		List<Car> cars = SimulationConfiguration.getCars();
		List<Driver> drivers = SimulationConfiguration.getDrivers();
		
		for(int i=0; i<cars.size(); i++){
			Car car = cars.get(i);
			car.setDriver(drivers.get(i));
			try{
				carDao.put(car.getId(), car);
			}
			catch(RuntimeException ie){
				LOGGER.error("Saving car information failed.", ie);
				throw ie;
			}
			
		}

	}

}

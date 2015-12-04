package com.realtimestudio.transport.event.simulator;

import java.util.List;

import org.junit.Test;

import com.realtimestudio.transport.event.simulation.Generator.Impl.SignalGeneratorImpl;
import com.realtimestudio.transport.event.simulation.config.SimulationConfiguration;
import com.realtimestudio.transport.model.Car;
import com.realtimestudio.transport.model.Driver;
import com.realtimestudio.transport.model.Location;

public class SimulationConfigurationTest {

	@Test
	public void test() {
		List<Driver> drivers = SimulationConfiguration.getDrivers();
		for(Driver driver : drivers){
			System.out.println(driver);
		}
		
		List<Car> cars = SimulationConfiguration.getCars();
		for(Car car : cars){
			System.out.println(car);
		}
		
		List<List<Location>> routes = SimulationConfiguration.getRoutes();
		System.out.println(routes.size());
		
		SignalGeneratorImpl simulator = new SignalGeneratorImpl(drivers.get(0), cars.get(0), routes.get(0));
		List<String> signals = simulator.generateSignals();
		System.out.println(signals.size());
		System.out.println(signals);
		
	}

}

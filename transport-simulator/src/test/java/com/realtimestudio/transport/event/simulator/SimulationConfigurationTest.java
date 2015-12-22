package com.realtimestudio.transport.event.simulator;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;

import com.realtimestudio.transport.event.simulation.Generator.Impl.SignalGeneratorImpl;
import com.realtimestudio.transport.event.simulation.config.SimulationConfiguration;
import com.realtimestudio.transport.model.Car;
import com.realtimestudio.transport.model.Driver;
import com.realtimestudio.transport.model.Location;
import com.realtimestudio.transport.model.Route;

public class SimulationConfigurationTest {

	@Test
	public void test() {
		List<Driver> drivers = SimulationConfiguration.getDrivers();
		System.out.println(drivers.size());
		System.out.println(drivers.get(0).getClass().getName());
		
		for(Driver driver : drivers){
			System.out.println(driver);
		}
		
		List<Car> cars = SimulationConfiguration.getCars();
		for(Car car : cars){
			System.out.println(car);
		}
		
		List<Route> routes = SimulationConfiguration.getRoutes();
		for(Route route : routes){
			System.out.println(route);
		}
		
		Route route = routes.get(0);
		String start = route.getStart();
		String end = route.getEnd();
		List<Location> locations = route.getLocations();
		Location first = locations.get(0);
		Location last = locations.get(locations.size()-1);
		
		route.reverse();
		assertTrue(start==route.getEnd());
		assertTrue(end==route.getStart());
		locations = route.getLocations();
		assertTrue(first==locations.get(locations.size()-1));
		assertTrue(last==locations.get(0));
		
	}

}

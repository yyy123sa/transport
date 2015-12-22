package com.realtimestudio.transport.event.simulation.emitter.impl;


import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.realtimestudio.transport.event.simulation.Generator.SignalGenerator;
import com.realtimestudio.transport.event.simulation.Generator.Impl.SignalGeneratorImpl;
import com.realtimestudio.transport.event.simulation.config.SimulationConfiguration;
import com.realtimestudio.transport.event.simulation.emitter.SignalEmitter;
import com.realtimestudio.transport.model.Car;
import com.realtimestudio.transport.model.Driver;
import com.realtimestudio.transport.model.Location;
import com.realtimestudio.transport.model.Route;
import com.realtimestudio.transport.streaming.SignalCollector;
import com.realtimestudio.transport.utils.RandomUtils;
import com.realtimestudio.transport.utils.SpringUtils;

public class SignalEmitterImpl implements SignalEmitter {
	private static final Logger LOGGER = LoggerFactory
			.getLogger(SignalEmitterImpl.class);

	private static List<Car> cars = SimulationConfiguration.getCars();
	private static List<Driver> drivers = SimulationConfiguration.getDrivers();
	private static List<Route> routes = SimulationConfiguration
			.getRoutes();

	private boolean liveForEver;
	private int interval; // sec
	private int num_emitters;
	private List<Integer> randomList;
	
	@Autowired
	private SignalCollector signalCollector;

	public SignalEmitterImpl(boolean liveForEver, int interval, int emitterNum) {
		super();
		this.liveForEver = liveForEver;
		this.interval = interval;
		this.num_emitters = emitterNum;
	}

	//driver->car<-route one-to-one-to-one relationship
	private void init() {
		for(int i=0; i<num_emitters; i++){
			cars.get(i).setDriver(drivers.get(i));
			cars.get(i).setRouteName(routes.get(i).getStart() + "-" + routes.get(i).getEnd());
		}
		//randomList = RandomUtils.getRandomNumbers(0, cars.size()-1, num_emitters);
		//LOGGER.info("Getting random list of cars: " + randomList);
	}

	@Override
	public void emit() {
		LOGGER.info(num_emitters+" cars are going to run "+ (liveForEver ? "for ever" : "for once"));
		ExecutorService threadPools = Executors.newFixedThreadPool(num_emitters);
		try {
			init();
			List<Callable<Long>> threads = new ArrayList<>(num_emitters);
			LOGGER.info("Creating the simulator threads");
			for (int i = 0; i < num_emitters; i++) {
				threads.add(new SignalEmitterThread(cars.get(i), routes.get(i)));
			}

			LOGGER.info("Starting the simulator threads");
			threadPools.invokeAll(threads);
		} catch (InterruptedException e) {
				LOGGER.error("emitter thread is interrupted");
		} finally {
			threadPools.shutdown();
		}
		LOGGER.info("Signal emission is completed");

	}

	private class SignalEmitterThread implements Callable<Long> {
		private long count;
		private Car car;
		private Route route;
		
		public SignalEmitterThread(Car car, Route route) {
			this.car = car;
			this.route = route;
			count = 0;
			LOGGER.info("New Emission thread is initalized: " + toString());
		}
		
		private void onerun(){
			SignalGenerator signalGenerator = new SignalGeneratorImpl(car.getDriver(), car,  route.getLocations());
			List<String> signals = signalGenerator.generateSignals();
				
			for (String signal : signals) {
			       signalCollector.send(car.getId(), signal);
				   try {
					   TimeUnit.SECONDS.sleep(interval);
					   count++;
				   } catch (InterruptedException e) {
					   String errorMessage = "SignalEmitterThead is interrupted: " + toString();
					   LOGGER.error(errorMessage);
					   throw new RuntimeException(errorMessage);
				   }
			    }
		}

		@Override
		public Long call() {
			do{
				onerun();
				route.reverse();
			}while(liveForEver);
			
			return count;
		}
		
		@Override
		public String toString(){
			return String.format("car=%s; route=%s; thread=%s", car.getId(), car.getRouteName(), Thread.currentThread().getName());
		}
	}

}

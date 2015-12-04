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
import com.realtimestudio.transport.streaming.SignalCollector;
import com.realtimestudio.transport.utils.RandomUtils;
import com.realtimestudio.transport.utils.SpringUtils;

public class SignalEmitterImpl implements SignalEmitter {
	private static final Logger LOGGER = LoggerFactory
			.getLogger(SignalEmitterImpl.class);

	private static List<Car> cars = SimulationConfiguration.getCars();
	private static List<Driver> drivers = SimulationConfiguration.getDrivers();
	private static List<List<Location>> routes = SimulationConfiguration
			.getRoutes();

	private boolean liveForEver;
	private int interval; // sec
	private int num_emitters;
	private List<Integer> randomList;
	
	@Autowired
	private SignalCollector signalCollector;

	public SignalEmitterImpl(boolean liveForEver, int interval) {
		super();
		this.liveForEver = liveForEver;
		this.interval = interval;
		num_emitters = routes.size();
	}

	private void init() {
		for(int i=0; i<cars.size(); i++){
			cars.get(i).setDriver(drivers.get(i));
		}
		randomList = RandomUtils.getRandomNumbers(0, cars.size()-1, num_emitters);
		LOGGER.info("Getting random list of cars: " + randomList);
	}

	@Override
	public void emit() {
		LOGGER.info(num_emitters+" cars are going to run "+ (liveForEver ? "for ever" : "for once"));
		ExecutorService threadPools = Executors.newFixedThreadPool(num_emitters);
		try {
			while (true) {
				init();
				List<Callable<Long>> threads = new ArrayList<>(num_emitters);
				LOGGER.info("Generating the signals");
				for (int i = 0; i < num_emitters; i++) {
					SignalGenerator signalGenerator = new SignalGeneratorImpl(cars.get(randomList.get(i)).getDriver(),
							cars.get(randomList.get(i)),  routes.get(i));
					List<String> signals = signalGenerator.generateSignals();
					threads.add(new SignalEmitterThread(signals, cars.get(i).getId()));
				}
				try {
					LOGGER.info("Emitting the singals");
					threadPools.invokeAll(threads);
				} catch (InterruptedException e) {
					LOGGER.error("emitter thread is interrupted");
				}
				if(!liveForEver) break;
				
				
				int sleeptime = interval * 20;
				LOGGER.info(String.format("One round is finished. Another round will start in %d sec", sleeptime));
				try{
					TimeUnit.SECONDS.sleep(sleeptime);
				}
				catch(InterruptedException e){
					LOGGER.error("Inter-round sleep is interrupted");
				}
				
			}
		} finally {
			threadPools.shutdown();
		}
		LOGGER.info("Signal emission is completed");

	}

	private class SignalEmitterThread implements Callable<Long> {
		private List<String> signals;
		private long count;
		private String key;

		public SignalEmitterThread(List<String> signals, String key) {
			this.signals = signals;
			count = 0;
			LOGGER.info(String.format("New Emission thread for the key %s is initalized: %s", key, Thread.currentThread().getName()));
		}

		@Override
		public Long call() {
			for (String signal : signals) {
				if(key == null) signalCollector.send(signal);
				else signalCollector.send(key, signal);
				try {
					TimeUnit.SECONDS.sleep(interval);
					count++;
				} catch (InterruptedException e) {
					LOGGER.error("SignalEmitterThead is interrupted");
				}
			}
			return count;

		}
	}

}

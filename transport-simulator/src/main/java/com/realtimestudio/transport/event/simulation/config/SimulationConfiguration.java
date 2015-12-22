package com.realtimestudio.transport.event.simulation.config;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.lang.reflect.Type;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.reflect.TypeParameter;
import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.realtimestudio.transport.model.Car;
import com.realtimestudio.transport.model.Driver;
import com.realtimestudio.transport.model.Route;

public class SimulationConfiguration {
	private static final Logger LOGGER = LoggerFactory.getLogger(SimulationConfiguration.class);
	
	private static final String carsFile = "cars.json";
	private static final String driversFile = "drivers.json";
	private static final String routesFile = "routes.json";
	
	private static List<Driver> drivers;
	private static List<Car> cars;
	private static List<Route> routes;
	
	static{
		drivers = parseInputFile(driversFile, Driver.class);
		cars = parseInputFile(carsFile, Car.class);
		routes = parseInputFile(routesFile, Route.class);

	}
	
	@SuppressWarnings("serial")
	private static<T> List<T> parseInputFile(String inputFile, Class<T> elementClass){
		try(Reader reader = new InputStreamReader(SimulationConfiguration.class.getClassLoader().getResourceAsStream(inputFile), "UTF-8")){
			Gson gson =new  GsonBuilder().setDateFormat("yyyy-MM-dd").create();
			Type listType = new TypeToken<List<T>>(){}
            .where(new TypeParameter<T>(){}, elementClass).getType();
			LOGGER.info("parsing input file: " + inputFile);
			return gson.fromJson(reader, listType);			
		} catch (FileNotFoundException e) {
			LOGGER.error("The simulation input files are not available: " + inputFile, e);
			throw new RuntimeException(e);
		} catch (IOException e) {
			LOGGER.error("The simulation input files can not be parsed: " + inputFile, e);
			throw new RuntimeException(e);
		}
	}
	
	public static List<Driver> getDrivers(){
		return drivers;
	}
	
	public static List<Car> getCars(){
		return cars;
	}
	
	public static List<Route> getRoutes(){
		return routes;
	}
}

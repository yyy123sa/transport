package com.realtimestudio.transport.event.simulation.config;

import static com.realtimestudio.transport.utils.CSVUtils.getRecordsFromFile;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.realtimestudio.transport.model.Car;
import com.realtimestudio.transport.model.Driver;
import com.realtimestudio.transport.model.Location;

public class SimulationConfiguration {
	private static final Logger LOGGER = LoggerFactory.getLogger(SimulationConfiguration.class);
	
	private static final String carsFile = "cars.json";
	private static final String driversFile = "drivers.json";
	private static final String routesFile = "routes.json";
	
	private static List<Driver> drivers;
	private static List<Car> cars;
	private static List<List<Location>> routes;
	
	static{
		try(Reader carsReader = new InputStreamReader(
				SimulationConfiguration.class.getClassLoader()
				.getResourceAsStream(carsFile), "UTF-8");
			Reader driversReader = new InputStreamReader(
					SimulationConfiguration.class.getClassLoader()
					.getResourceAsStream(driversFile), "UTF-8");
			Reader routesReader = new InputStreamReader(
					SimulationConfiguration.class.getClassLoader()
					.getResourceAsStream(routesFile), "UTF-8")	){
			
			Gson gson =new  GsonBuilder().setDateFormat("yyyy-MM-dd").create();
			
			LOGGER.info("parsing drivers input file");
			drivers = Arrays.asList(gson.fromJson(driversReader, Driver[].class));
			LOGGER.info("parsing cars input file");
			cars = Arrays.asList(gson.fromJson(carsReader, Car[].class));
			
			LOGGER.info("parsing routes input file");
			String[] routeFiles =  gson.fromJson(routesReader, String[].class);
			routes = new ArrayList<>();
			
			for(int i=0; i<routeFiles.length; i++){
				LOGGER.info("parsing route input file: "+routeFiles[i]);
				routes.add(getRoute(routeFiles[i]));
			}
			
			
		} catch (FileNotFoundException e) {
			LOGGER.error("The simulation input files are not available", e);
			throw new RuntimeException(e);
		} catch (IOException e) {
			LOGGER.error("The simulation input files can not be parsed", e);
			throw new RuntimeException(e);
		}
	}
	
	public static List<Driver> getDrivers(){
		return drivers;
	}
	
	public static List<Car> getCars(){
		return cars;
	}
	
	public static List<List<Location>> getRoutes(){
		return routes;
	}
	
	private static List<Location> getRoute(String fileName) {
		try(Reader routeReader = new InputStreamReader(
				SimulationConfiguration.class.getClassLoader()
				.getResourceAsStream(fileName), "UTF-8")){
			String[] map = {"latitude", "longitude"};
			List<Map<String,String>> locations = getRecordsFromFile(routeReader, map, false);
			List<Location> list = new ArrayList<>(locations.size());
			for(Map<String,String> record : locations){
				Location loc = new Location(Float.parseFloat(record.get(map[1])), Float.parseFloat(record.get(map[0])));
				list.add(loc);
			}
			return list;
			
		}catch (FileNotFoundException e) {
			LOGGER.error("The route input files are not available: "+fileName, e);
			throw new RuntimeException(e);
		} catch (IOException e) {
			LOGGER.error("The route input files can not be parsed: "+fileName, e);
			throw new RuntimeException(e);
		}
	}
	
	
	

}

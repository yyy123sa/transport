package com.realtimestudio.transport.model;

public class Car {
	private String id;
	private String model;
	private String color;
	private Integer year;
	private Driver driver;
	private String routeName;
	
	public Car(String id, String model, String color, Integer year) {
		super();
		this.id = id;
		this.model = model;
		this.color = color;
		this.year = year;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}
	
	public Driver getDriver(){
		return driver;
	}
	
	public void setDriver(Driver driver){
		this.driver = driver;
	}
	
	@Override
	public String toString(){
		String str = String.format("Car: ID=%s; model=%s; color=%s; year=%d; route=%s", id, model, color, year, routeName);
		if(driver != null) str += driver.toString();
		return str;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}
	
	public Integer getYear(){
		return year;
	}
	
	public void setYear(Integer year){
		this.year = year;
	}
	
	public String getRouteName(){
		return routeName;
	}
	
	public void setRouteName(String routeName){
		this.routeName = routeName;
	}

}

package com.realtimestudio.transport.model;

public class Car {
	private String id;
	private String model;
	private Driver driver;
	
	public Car(String id, String model) {
		super();
		this.id = id;
		this.model = model;
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
		String str = String.format("Car: ID=%s; model=%s", id, model);
		if(driver != null) str += driver.toString();
		return str;
	}
	
	
	

}

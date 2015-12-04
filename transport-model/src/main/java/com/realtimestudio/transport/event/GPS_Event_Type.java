package com.realtimestudio.transport.event;

public enum GPS_Event_Type {
	DRIVER_ERROR(1), CAR_STATUS(2);
	
	private int id;
	private GPS_Event_Type(int id){
		this.id=id;
	}
	
	public int getID(){
		return id;
	}
	
	public static GPS_Event_Type getType(int i){
		switch(i){
		case 1: return DRIVER_ERROR;
		case 2: return CAR_STATUS;
		default: throw new IllegalArgumentException("Wrong GPS event type");
		}
	}
}

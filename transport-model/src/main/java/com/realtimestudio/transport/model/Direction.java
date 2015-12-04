package com.realtimestudio.transport.model;

public class Direction {
	private String cardinal;
	private short degree;	
	private short value;
	
	public Direction(short val){
		this.value = (short) (val % 360);
		switch(value/90){
		case 0: 
			cardinal = "N";
			break;
		case 1:
			cardinal = "E";
			break;
		case 2:
			cardinal = "S";
			break;
		case 3:
			cardinal = "W";
			break;
		}
		degree = (short) (value % 90);
	}
	
	public short getValue(){
		return value;
	}
	
	@Override
	public String toString(){
		return cardinal+degree; 		
	}	

}

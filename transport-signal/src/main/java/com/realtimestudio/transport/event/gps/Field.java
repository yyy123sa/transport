package com.realtimestudio.transport.event.gps;

public class Field {
	private String name;
	private FieldType type;
	
	public Field(String name, FieldType type) {
		super();
		this.name = name;
		this.type = type;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public FieldType getType() {
		return type;
	}

	public void setType(FieldType type) {
		this.type = type;
	}
	
	@Override
	public String toString(){
		return String.format("[field=%s, type=%s]", name, type);
	}	

}

package com.realtimestudio.transport.model;

import static com.realtimestudio.transport.utils.DateUtils.getDateStr;
import static com.realtimestudio.transport.utils.DateUtils.getYearDiff;

import java.util.Date;

public class Driver {
	private String name;
	private String id;
	private Date dob;
	private Date licenseDate;	
	private int riskFactor;
	
	private int age;
	private int drivingYear;
	private int adjRiskFactor;
	
	public Driver(String name, String id, Date dob, Date licenseDate,
			int riskFactor) {
		super();
		this.name = name;
		this.id = id;
		this.dob = dob;
		this.licenseDate = licenseDate;
		this.riskFactor = riskFactor;		
		
		setAge();
		setDrivingYear();
		setAdjRiskFactor();
	}

	public String getName() {
		return name;
	}

	public String getId() {
		return id;
	}

	public Date getDob() {
		return dob;
	}

	public Date getLicenseDate() {
		return licenseDate;
	}

	public int getRiskFactor() {
		return riskFactor;
	}
	
	public int getAge(){
		return age;
	}
	
	private void setAge(){
		age = getYearDiff(new Date(), dob);
	}
	
	public int getDrivingYear(){
		return drivingYear;
	}
	
   private void setDrivingYear(){
      drivingYear = getYearDiff(new Date(), licenseDate);
	}
   
   public int getAdjRiskFactor(){
	   return adjRiskFactor;
   }
	
	private void setAdjRiskFactor(){
		adjRiskFactor = Math.min(getRiskFactor(), 100)-getDrivingYear();	
		adjRiskFactor = Math.max(adjRiskFactor, 0);
	}
	
	@Override
	public String toString(){
		return String.format("Driver: name=%s; ID=%s; birthday=%s; license_date=%s; riskFactor=%d"
				, name, id, getDateStr(dob, "yyyy-MM-dd"), getDateStr(licenseDate, "yyyy-MM-dd"), riskFactor);
	}


}

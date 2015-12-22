package com.realtimestudio.transport.dao;

import org.apache.hadoop.hbase.util.Bytes;

public class Constants {
	public static final String GPSSIGNAL_TAB = "gpssignal";	
	
	public static final String DATA_FAMILY = "d";
	public static final String LATI = "lati";
	public static final String LONG = "long";
	public static final String WEATHER = "weather";
	public static final String SPEED = "speed";
	public static final String GAS = "gas";
	public static final String PRESSURE = "pressure";
	public static final String CROSSLINE = "crossline";
	
	public static final byte[] DATA_FAMILY_H = Bytes.toBytes(DATA_FAMILY );
	public static final byte[] LATI_H = Bytes.toBytes(LATI);
	public static final byte[] LONG_H = Bytes.toBytes(LONG);
	public static final byte[] WEATHER_H = Bytes.toBytes(WEATHER);
	public static final byte[] SPEED_H = Bytes.toBytes(SPEED);
	public static final byte[] GAS_H = Bytes.toBytes(GAS);
	public static final byte[] PRESSURE_H = Bytes.toBytes(PRESSURE);
	public static final byte[] CROSSLINE_H = Bytes.toBytes(CROSSLINE);
	
	public static final String CARDRIVER_TAB = "car_driver";
	
	public static final String CAR_FAMILY = "c";
	public static final String MODEL = "model"; 
	public static final String COLOR = "color";
	public static final String YEAR = "year";
	
	public static final String DRIVER_FAMILY = "d";
	public static final String NAME = "name";
	public static final String ID = "id";
	public static final String DOB = "dob";
	public static final String LICENSEDATE = "licenseDate";
	public static final String RISKFACTOR = "riskFactor";
	public static final String PHONENUM = "PHONENUM";
	public static final String EMAIL = "email";
	public static final String LICENSENUM = "licenseNum";
	
	public static final byte[] CAR_FAMILY_H = Bytes.toBytes(CAR_FAMILY);
	public static final byte[] MODEL_H = Bytes.toBytes(MODEL);
	public static final byte[] COLOR_H = Bytes.toBytes(COLOR);
	public static final byte[] YEAR_H = Bytes.toBytes(YEAR);
	
	public static final byte[] DRIVER_FAMILY_H  = Bytes.toBytes(DRIVER_FAMILY);
	public static final byte[] NAME_H  = Bytes.toBytes(NAME);
	public static final byte[] ID_H  = Bytes.toBytes(ID);
	public static final byte[] DOB_H  = Bytes.toBytes(DOB);
	public static final byte[] LICENSEDATE_H  = Bytes.toBytes(LICENSEDATE);
	public static final byte[] RISKFACTOR_H  = Bytes.toBytes(RISKFACTOR);
	public static final byte[] PHONENUM_H  = Bytes.toBytes(PHONENUM);
	public static final byte[] EMAIL_H  = Bytes.toBytes(EMAIL);
	public static final byte[] LICENSENUM_H  = Bytes.toBytes(LICENSENUM);
}
	
	
	
	

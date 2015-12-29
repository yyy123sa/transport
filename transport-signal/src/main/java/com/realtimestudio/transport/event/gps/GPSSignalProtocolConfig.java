package com.realtimestudio.transport.event.gps;

import static com.realtimestudio.transport.utils.CSVUtils.getRecordsFromFile;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap.Builder;

public class GPSSignalProtocolConfig {
	private static final Logger LOGGER = LoggerFactory
			.getLogger(GPSSignalProtocolConfig.class);

	private static ImmutableList<Field> fieldList;
	
	private final static String[] signalProtoCSVHeader = { "name", "type" };
	private final static String signalProtolCSVName = "GPS_Shaige_Signal_Protocol.csv";

	static {
		try (Reader signalFileReader = new InputStreamReader(
				GPSSignalProtocolConfig.class.getClassLoader()
						.getResourceAsStream(signalProtolCSVName));) {
			List<Map<String, String>> signalRecords = getRecordsFromFile(
					signalFileReader, signalProtoCSVHeader, true);
			
			List<Field> list = new ArrayList<>();
			for (Map<String, String> record : signalRecords) {
				Field field = new Field(record.get(signalProtoCSVHeader[0]),
						FieldType.getFieldType(record.get(signalProtoCSVHeader[1])));
				list.add(field);
			}
			ImmutableList.Builder<Field> builder = ImmutableList.builder();
			fieldList = builder.addAll(list).build();
			
			LOGGER.debug("signal protocol input file is parsed");


		} catch (FileNotFoundException e) {
			LOGGER.error("The GPS protocol input files are not available", e);
			throw new RuntimeException(e);
		} catch (IOException e) {
			LOGGER.error("The GPS protocol input files can not be parsed", e);
			throw new RuntimeException(e);
		}

	}
	

	public final static String PACKET_HEAD = "Packet_Head";
	public final static String PACKET_TYPE = "Packet_Type";
	public final static String SIM_NO = "SIM_No";
	public final static String GPS_TIME = "GPS_Time";
	public final static String GPS_LAT = "GPS_Lat";
	public final static String GPS_LON = "GPS_Lon";
	public final static String SPEED = "Speed";
	public final static String DIRECTION = "Direction";
	public final static String GAS = "Gas";
	public final static String WEATHER = "Weather";
	public final static String PRESSURE = "Pressure";
	public final static String STATUS = "Status";
	public final static String PACKET_TAIL = "Packet_Tail";
	
	public final static String DATEFORMAT = "yyMMddHHmmss";
	public final static String DEFAULTPACKETTYPE = "01";
	public final static String SEPERATOR = "|";
	public final static String SEPERATORESCAPED="\\|";
	
	public static ImmutableList<Field> getFieldDescList(){
		return fieldList;
	}
	

}

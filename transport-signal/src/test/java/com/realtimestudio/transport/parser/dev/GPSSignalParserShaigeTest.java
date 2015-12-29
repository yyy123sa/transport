package com.realtimestudio.transport.parser.dev;

import static org.junit.Assert.assertEquals;

import java.text.ParseException;

import org.junit.Test;

import com.realtimestudio.transport.event.GPSSignalFormatter;
import com.realtimestudio.transport.event.GPSSignalParser;
import com.realtimestudio.transport.event.gps.GPSSignalFormatterImpl;
import com.realtimestudio.transport.event.gps.GPSSignalParserImpl;
import com.realtimestudio.transport.model.RoutePoint;
import com.realtimestudio.transport.utils.DateUtils;

public class GPSSignalParserShaigeTest {

	@Test
	public void testWithoutStatus() throws ParseException {
	//(0113811111111 070406120010 +23.123456 +113.567890 050 240 050100B1012100E)
		//String str = "(0113811111111070406120010+23.123456+113.567890050240050100B1012100E)";
		//(0113811111111 070406120010 +23.123456 +113.567890 080 240 050 02 34.49 100E)
		//simid timestamp, lat, long, speed, Direction, gas, weather, presssure, status
		//(0100001000011151103191542 +28.198180 +113.0191001091800610234.99)
		//String str = "(0100001000011151103191542+28.198180+113.0191001091800610234.99)";
		//String str = "(01粤B77601151103191542+28.198180+113.0191001091800610234.99)";
		//( 01 粤B77617 151222184930 +31.351570 +120.658680 108 001 0-1 0332.59)"
		String str = "粤B77601|070406120010|+23.12345|+113.56789|80|240|50|2|34.49";
		//System.out.println("粤B7".length());
		parseNformat(str);		
	}
	
	@Test
	public void testWithStatus() throws ParseException {
	//(0113811111111 070406120010 +23.123456 +113.567890 050 240 050100B1012100E)
		//String str = "(0113811111111070406120010+23.123456+113.567890050240050100B1012100E)";
		//(0113811111111 070406120010 +23.123456 +113.567890 080 240 050 02 34.49 100E)
		//simid timestamp, lat, long, speed, Direction, gas, weather, presssure, status
		//String str = "(0113811111111070406120010+23.123456+113.5678900802400500234.49100E)";
		//String str = "(0100001000011151103191542+28.198180+113.0191001091800610234.99)";
		String str = "粤B77601|070406120010|+23.12345|+113.56789|80|240|50|2|34.49|100E";
		parseNformat(str);		
	}
	
	private void parseNformat(String str) throws ParseException{
		GPSSignalParser parser = new GPSSignalParserImpl(str);
		String carID = parser.getCarID();
		RoutePoint point = parser.getRoutePoint();
		System.out.println(carID);
		System.out.println(point);
		
		GPSSignalFormatter formatter = new GPSSignalFormatterImpl(carID, point);
		String signal = formatter.getSignal();
		System.out.println(str);
		System.out.println(signal);
		
		assertEquals(str, signal);
	}
	
	
	
	@Test
	public void dateUtilsTest() throws ParseException{
		String str = "070406120010";
		//String format = GPSSignalProtocolConfig.DATEFORMAT;
		String format = "yyMMddHHmmSS";
		System.out.println(DateUtils.getDateStr(DateUtils.getDate(str, format), format));
	}
	
	@Test
	public void stringSplit(){
		String str = "sss;";
		System.out.println(str.split(";").length);
	}

}

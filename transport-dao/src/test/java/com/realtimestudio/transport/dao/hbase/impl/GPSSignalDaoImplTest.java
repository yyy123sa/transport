package com.realtimestudio.transport.dao.hbase.impl;

import java.io.IOException;
import java.util.Date;
import java.util.HashSet;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.realtimestudio.transport.dao.GPSSignalDao;
import com.realtimestudio.transport.event.GPS_Event;
import com.realtimestudio.transport.model.Location;
import com.realtimestudio.transport.model.RoutePoint;
import com.realtimestudio.transport.model.Weather;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)
public class GPSSignalDaoImplTest {
	@Autowired
	private GPSSignalDao dao;
	
	private RoutePoint contructPoint(){
		return new RoutePoint(new Location(1.0f, 2.0f), new Date().getTime(), (short)80,
				null, (short)50, Weather.valueOf("SUNNY"), 34.5f, new HashSet<GPS_Event>());
		
	}
	
	@Test
	public void testGetPoint(){
		String carid = "1311000001";
		System.out.println(dao.findById(carid));
	}
	
	@Test
	public void test() throws IOException{
		dao.put("abc123", contructPoint());
		Map<String, RoutePoint> map = dao.findAll();
		for(String key : map.keySet()){
			System.out.println(key);
			System.out.println(map.get(key));
		}
	}

}

@Configuration
@ComponentScan("com.realtimestudio.transport")
class TestConfig{}


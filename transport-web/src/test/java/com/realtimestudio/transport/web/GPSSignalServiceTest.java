package com.realtimestudio.transport.web;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.realtimestudio.transport.web.service.RouteService;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes=App.class)
@WebAppConfiguration
public class GPSSignalServiceTest {
	@Autowired
	RouteService service;

	@Test
	public void test() {
		System.out.println("This is route point");
		System.out.println(service.getLatestRoutePoint("1311000005"));
	}

}

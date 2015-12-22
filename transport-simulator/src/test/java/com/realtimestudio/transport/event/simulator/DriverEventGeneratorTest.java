package com.realtimestudio.transport.event.simulator;

import static org.junit.Assert.assertEquals;

import java.util.Date;
import java.util.Set;

import org.junit.Test;

import com.realtimestudio.transport.event.GPS_Event;
import com.realtimestudio.transport.event.simulation.Generator.Impl.DriverEventGeneratorImpl;
import com.realtimestudio.transport.model.Driver;

public class DriverEventGeneratorTest {
	
	private static Set<GPS_Event> eventSet = GPS_Event.getStatusMap().values();

	@Test
	public void mostDangerousDirverTest() {
		Driver driver = new Driver("Winston Chen", "6507401234", "sdf@163.com", "23234234", "100001", new Date(), new Date(), 100);
		DriverEventGeneratorImpl generator = new DriverEventGeneratorImpl(driver);
		assertEquals(generator.generateEvents().size(), eventSet.size());
	}
	
	@Test
	public void safestDirverTest() {
		Driver driver = new Driver("Winston Chen", "6507401234", "sdf@163.com", "23234234", "100001", new Date(), new Date(), 0);
		DriverEventGeneratorImpl generator = new DriverEventGeneratorImpl(driver);
		assertEquals(generator.generateEvents().size(), 0);
	}
	

}

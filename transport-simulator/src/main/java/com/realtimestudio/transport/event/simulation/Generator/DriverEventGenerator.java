package com.realtimestudio.transport.event.simulation.Generator;

import java.util.Set;

import com.realtimestudio.transport.event.GPS_Event;

public interface DriverEventGenerator {
	Set<GPS_Event> generateEvents();

}

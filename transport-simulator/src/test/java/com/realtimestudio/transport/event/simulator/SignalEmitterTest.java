package com.realtimestudio.transport.event.simulator;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.stereotype.Component;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.realtimestudio.transport.event.GPSSignalParser;
import com.realtimestudio.transport.event.gps.GPSSignalParserImpl;
import com.realtimestudio.transport.event.simulation.App;
import com.realtimestudio.transport.event.simulation.emitter.SignalEmitter;
import com.realtimestudio.transport.streaming.SignalCollector;


@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = App.class)

public class SignalEmitterTest {
	//@Component
	private static class StdoutCollector implements SignalCollector{

		@Override
		public void send(String signal) {
			GPSSignalParser parser=null;
			System.out.println(signal);
			try {
				parser = new GPSSignalParserImpl(signal);
			} catch (Exception e) {
				e.printStackTrace();
			}
			System.out.println(parser.getCarID());
			System.out.println(parser.getRoutePoint());			
		}

		@Override
		public void send(String key, String signal) {
			System.out.println(key+":"+signal);	
			GPSSignalParser parser=null;
			try {
				parser = new GPSSignalParserImpl(signal);
			} catch (Exception e) {
				e.printStackTrace();
			}
			System.out.println(parser.getCarID());
			System.out.println(parser.getRoutePoint());	
			
		}

		@Override
		public void close() {
			System.out.println("collector is closed");
			
		}		
	}
	
	@Autowired
	SignalEmitter emitter;

	@Test
	public void test() throws InterruptedException {
		emitter.emit();
	}

}

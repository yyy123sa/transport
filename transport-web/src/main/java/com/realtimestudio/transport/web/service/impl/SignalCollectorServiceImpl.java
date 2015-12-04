package com.realtimestudio.transport.web.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.realtimestudio.transport.event.GPSSignalParser;
import com.realtimestudio.transport.event.gps.GPSSignalParserImpl;
import com.realtimestudio.transport.streaming.SignalCollector;
import com.realtimestudio.transport.web.service.SignalCollectorService;

@Service
public class SignalCollectorServiceImpl implements SignalCollectorService {
	private final static Logger LOGGER = LoggerFactory.getLogger(SignalCollectorServiceImpl.class);
	@Autowired
	private SignalCollector collector;	

	@Override
	public void send(String signal) {
		String key = null;
		try{
			GPSSignalParser parser = new GPSSignalParserImpl(signal);
			key = parser.getCarID();
		}
		catch(Exception e){
			LOGGER.error(signal + " can not be parsed", e);
			return;
		}
		collector.send(key, signal);

	}

}

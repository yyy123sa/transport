package com.realtimestudio.transport.web.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.realtimestudio.transport.web.service.SignalCollectorService;

@RestController
@RequestMapping("/signal")
public class SignalCollectorController {
	
	private final static Logger LOGGER = LoggerFactory.getLogger(SignalCollectorController.class);
	
	@Autowired
	private SignalCollectorService service;
	
	@RequestMapping(value="/", method = RequestMethod.POST)
	public ResponseEntity<String> collectSignal(@RequestBody String signal){
		LOGGER.debug("Request: collectSignal: " + signal);
		try{
			service.send(signal);
			return new ResponseEntity<String>(HttpStatus.OK);
		}
		catch(Exception e){
			LOGGER.error("Failed to collect signal: " + signal, e);
			return new ResponseEntity<String>(HttpStatus.BAD_REQUEST);
		}
	}

}

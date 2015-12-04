package com.realtimestudio.transport.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan("com.realtimestudio.transport")
@EnableAutoConfiguration
public class App {
	private static final Logger LOGGER = LoggerFactory.getLogger(App.class);
	public static void main (String[] args){
		LOGGER.info("Transport web is starting...");
		SpringApplication.run(App.class, args);
	}

}

package com.realtimestudio.transport.httpclient.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

import com.realtimestudio.transport.httpclient.impl.HttpSignalCollector;
import com.realtimestudio.transport.streaming.SignalCollector;

@Configuration
@PropertySource("classpath:http.properties")
public class HttpConfig {
	
	@Autowired
	private Environment env;
	
	@Bean 
	public SignalCollector httpSignalCollector(){
		return new HttpSignalCollector(env.getProperty("http.endpoint"));
		
	}

}

package com.realtimestudio.transport.streaming.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.Scope;
import org.springframework.core.env.Environment;

import com.realtimestudio.transport.streaming.SignalCollector;
import com.realtimestudio.transport.streaming.impl.KafkaSignalCollector;

@Configuration
@PropertySource("classpath:kafka.properties")
public class KafkaConfig {
	
	@Autowired
	private Environment env;
	
	@Bean (destroyMethod ="close")
	public SignalCollector kafkaSignalCollector(){
		return new KafkaSignalCollector(env.getProperty("broker_list"), env.getProperty("topic"));
		
	}

}

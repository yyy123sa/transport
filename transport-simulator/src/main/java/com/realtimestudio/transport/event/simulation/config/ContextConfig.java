package com.realtimestudio.transport.event.simulation.config;

import static com.realtimestudio.transport.utils.StringUtils.parseBooleanOrDefault;
import static com.realtimestudio.transport.utils.StringUtils.parseIntOrDefault;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

import com.realtimestudio.transport.event.simulation.emitter.SignalEmitter;
import com.realtimestudio.transport.event.simulation.emitter.impl.SignalEmitterImpl;

@Configuration
@PropertySource("classpath:Simulator.properties")
public class ContextConfig {
	private static final Logger LOGGER = LoggerFactory.getLogger(ContextConfig.class);
	
	@Autowired
	private Environment env;
	
	@Bean
	public SignalEmitter signalEmitter(){
		int interval = parseIntOrDefault(env.getProperty("interval"), 1);
		boolean liveForever = parseBooleanOrDefault(env.getProperty("live_forever"), false);
		int emitterNum  = parseIntOrDefault(env.getProperty("emitter_num"), 20);
		LOGGER.info(String.format("The emitter will emit signal every %d sec.", interval));
		if(liveForever) LOGGER.info("The emitter will run continuously until killed manually");
		
		return new SignalEmitterImpl(liveForever, interval, emitterNum);
		
	}

}

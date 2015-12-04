package com.realtimestudio.transport.dao.config;

import static com.realtimestudio.transport.utils.StringUtils.parseIntOrDefault;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

import com.realtimestudio.transport.dao.ConnectionManager;
import com.realtimestudio.transport.dao.hbase.impl.ConnectionManagerImpl;

@Configuration
@PropertySource("classpath:HBase.properties")
public class DaoConfig {
	
	@Autowired
	private Environment env;
	
	@Bean (destroyMethod = "closeAll")
	public ConnectionManager connectionManager(){
		return new ConnectionManagerImpl(
				parseIntOrDefault(env.getProperty("hbase.min.connections"), 1)
				, parseIntOrDefault(env.getProperty("hbase.max.connections"), 5));
	}	
}

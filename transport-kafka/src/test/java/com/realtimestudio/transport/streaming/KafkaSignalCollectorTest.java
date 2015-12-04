package com.realtimestudio.transport.streaming;

import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)   
@ContextConfiguration(classes = KafkaSignalCollectorTestConfig.class)
public class KafkaSignalCollectorTest {
	@Autowired
	SignalCollector collector;
	
	@Test
	public void test(){
		String key = "key";
		String value = "value2";
		
		collector.send(key, value);
	}
	
	@After
	public void cleanup(){
		collector.close();
	}
	

}

@Configuration
@ComponentScan("com.realtimestudio.transport")
class KafkaSignalCollectorTestConfig{
	
}

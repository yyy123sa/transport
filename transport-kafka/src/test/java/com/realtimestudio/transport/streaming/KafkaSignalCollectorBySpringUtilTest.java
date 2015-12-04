package com.realtimestudio.transport.streaming;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.realtimestudio.transport.utils.SpringUtils;

@RunWith(SpringJUnit4ClassRunner.class)   
@ContextConfiguration(classes = KafkaSignalCollectorTestConfig2.class)
public class KafkaSignalCollectorBySpringUtilTest {
	SignalCollector collector;
	
	@Before
	public void setup(){
		collector = SpringUtils.getBean(SignalCollector.class);
	}
	
	@Test
	public void test(){
		String key = "key";
		String value = "value3";
		
		collector.send(key, value);
	}
	
	@After
	public void cleanup(){
		collector.close();
	}
	

}

@Configuration
@ComponentScan("com.realtimestudio")
class KafkaSignalCollectorTestConfig2{
	
}


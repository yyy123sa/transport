package com.realtimestudio.transport.httpclient;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.realtimestudio.transport.httpclient.config.HttpConfig;
import com.realtimestudio.transport.streaming.SignalCollector;

@RunWith(SpringJUnit4ClassRunner.class)   
@ContextConfiguration(classes = HttpConfig.class)
public class HttpSignalCollectorTest {
	@Autowired
	private SignalCollector collector;

	@Test
	public void test() {
		collector.send("this 中国is a test");
	}

}

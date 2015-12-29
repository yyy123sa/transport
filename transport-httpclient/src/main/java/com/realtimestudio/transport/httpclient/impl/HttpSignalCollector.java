package com.realtimestudio.transport.httpclient.impl;

import java.nio.charset.Charset;

import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import com.realtimestudio.transport.streaming.SignalCollector;

public class HttpSignalCollector implements SignalCollector{
	private RestTemplate sender;
	private String endPoint;
	
	public HttpSignalCollector(String endPoint){
		this.endPoint = endPoint;
		sender = new RestTemplate();
		sender.getMessageConverters().add(0, new StringHttpMessageConverter(Charset.forName("UTF-8")));
	}
	
	@Override
	public void send(String signal) {
		sender.postForEntity(endPoint, signal, String.class);
		
	}

	@Override
	public void send(String key, String signal) {
		send(signal);
		
	}

	@Override
	public void close() {
		// empty implementation on purpose
		
	}

}

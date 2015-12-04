package com.realtimestudio.transport.streaming.impl;

import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import kafka.javaapi.producer.Producer;
import kafka.producer.KeyedMessage;
import kafka.producer.ProducerConfig;

import com.realtimestudio.transport.streaming.SignalCollector;

public class KafkaSignalCollector implements SignalCollector {
	private String topic;
	private Producer<String, String> producer;
	
	private final static Logger LOGGER = LoggerFactory.getLogger(KafkaSignalCollector.class);
	
	public KafkaSignalCollector(String brokerList, String topic){
		this.topic = topic;
		
		Properties props = new Properties();
		props.put("metadata.broker.list", brokerList);
		props.put("serializer.class", "kafka.serializer.StringEncoder");
		props.put("request.required.acks", "1");
		//props.put("producer.type", "async");
		//props.put("zk.connect", "120.234.16.3:2181");
		
		ProducerConfig config = new ProducerConfig(props);

		producer = new Producer<String, String>(config);
		LOGGER.info("Kafka producer is initilized");
	}

	@Override
	public void send(String signal) {
		KeyedMessage<String, String> message = new KeyedMessage<>(topic, signal);
		producer.send(message);
		LOGGER.trace("Kafka send message: " + signal);
	}

	@Override
	public void send(String key, String signal) {
		KeyedMessage<String, String> message = new KeyedMessage<>(topic, key, signal);
		producer.send(message);
		LOGGER.trace("Kafka send keyed message: " + key+":"+signal);

	}
	
	@Override
	public void close(){
		producer.close();		
		LOGGER.info("kafka producer is closed");
	}
	

}

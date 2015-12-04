package com.realtimestudio.transport.storm.spout;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import storm.kafka.BrokerHosts;
import storm.kafka.KafkaSpout;
import storm.kafka.SpoutConfig;
import storm.kafka.ZkHosts;
import backtype.storm.spout.SchemeAsMultiScheme;

import com.realtimestudio.transport.storm.util.Constants;
import com.realtimestudio.transport.storm.util.Parameters;

public class GPSSignalSpout extends KafkaSpout{
	
	private static final long serialVersionUID = 640945700751873685L;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(GPSSignalSpout.class);

	public GPSSignalSpout(){
		super(constructConf());
	}
	
	private static SpoutConfig constructConf() {
		BrokerHosts hosts = new ZkHosts(Parameters.getProperty(Constants.KAFKAHOST));
		String topic = Parameters.getProperty(Constants.KAFKATOPIC);
		String zkRoot = Parameters.getProperty(Constants.KAFKAZKROOT);
		String consumerGroupId = Parameters.getProperty(Constants.KAFKACONSUMERGROUPID);

		SpoutConfig spoutConfig = new SpoutConfig(hosts, topic, zkRoot,	consumerGroupId);

		spoutConfig.scheme = new SchemeAsMultiScheme(new GPSSignalScheme());

		return spoutConfig;
	}
}

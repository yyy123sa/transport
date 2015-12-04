package com.realtimestudio.transport.storm;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import backtype.storm.Config;
import backtype.storm.topology.TopologyBuilder;

import com.realtimestudio.transport.storm.bolt.SplitBolt;
import com.realtimestudio.transport.storm.spout.TextSpout;
import com.realtimestudio.transport.storm.util.StormRunner;

public class TextTopology {
	//private static final Logger LOGGER = Logger.getLogger(TextTopology.class);
	//private static final Log LOGGER = LogFactory.getLog(TextTopology.class);
	private static final Logger LOGGER = LoggerFactory.getLogger(TextTopology.class);
	@Test
	public void test() throws InterruptedException{
		LOGGER.debug("toppology is going to start...");
		TopologyBuilder builder = new TopologyBuilder();
		builder.setSpout("text", new TextSpout(), 3);
		builder.setBolt("split", new SplitBolt(), 2).shuffleGrouping("text");
		Config conf = new Config();
		conf.setDebug(false);
		
		StormRunner.runTopologyLocally(builder.createTopology(), "test", conf, 2);
		LOGGER.trace("toppology is going to end...");
		
		
	}
	

}

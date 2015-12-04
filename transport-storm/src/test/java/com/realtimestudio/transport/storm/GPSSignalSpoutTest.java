package com.realtimestudio.transport.storm;

import org.junit.Test;

import backtype.storm.Config;
import backtype.storm.topology.TopologyBuilder;

import com.esotericsoftware.kryo.serializers.FieldSerializer;
import com.realtimestudio.transport.dao.GPSSignalDao;
import com.realtimestudio.transport.model.RoutePoint;
import com.realtimestudio.transport.storm.bolt.PrintBolt;
import com.realtimestudio.transport.storm.spout.GPSSignalSpout;
import com.realtimestudio.transport.storm.util.Constants;
import com.realtimestudio.transport.storm.util.StormRunner;
import com.realtimestudio.transport.utils.SpringUtils;

public class GPSSignalSpoutTest {

	//@Test
	public void test() throws InterruptedException {
		TopologyBuilder builder = new TopologyBuilder();
		builder.setSpout(Constants.KAFKA_SPOUT_ID , new GPSSignalSpout());
		builder.setBolt("printbolt", new PrintBolt()).shuffleGrouping(Constants.KAFKA_SPOUT_ID);
		Config conf = new Config();
		conf.registerSerialization(RoutePoint.class, FieldSerializer.class);
		StormRunner.runTopologyLocally(builder.createTopology(), "testtopologgy", conf, 60);
	}
	
	@Test
	public void testTopology() throws InterruptedException{
		GPSSignalTopology topology = new GPSSignalTopology();
		StormRunner.runTopologyLocally(topology.buildTopology(), "testTopology", topology.buildConfig(), 120);
	}
	
	@Test
	public void testBean(){
		GPSSignalDao dao = SpringUtils.getBean(GPSSignalDao.class);
	}

}

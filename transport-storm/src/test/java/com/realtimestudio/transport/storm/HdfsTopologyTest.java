package com.realtimestudio.transport.storm;

import org.junit.Test;

import backtype.storm.Config;
import backtype.storm.topology.TopologyBuilder;

import com.esotericsoftware.kryo.serializers.FieldSerializer;
import com.realtimestudio.transport.model.RoutePoint;
import com.realtimestudio.transport.storm.bolt.GPSSignalHBaseBolt;
import com.realtimestudio.transport.storm.bolt.GPSSignalHDFSBolt;
import com.realtimestudio.transport.storm.spout.GPSSignalSpout;
import com.realtimestudio.transport.storm.util.Constants;
import com.realtimestudio.transport.storm.util.StormRunner;

public class HdfsTopologyTest {

	@Test
	public void test() throws InterruptedException {
		TopologyBuilder builder = new TopologyBuilder();
		builder.setSpout(Constants.KAFKA_SPOUT_ID , new GPSSignalSpout());
		//builder.setBolt("printbolt", new PrintBolt()).shuffleGrouping(Constants.KAFKA_SPOUT_ID);
		builder.setBolt(Constants.HDFS_CARSIGNAL_BOLT_ID, new GPSSignalHDFSBolt()).shuffleGrouping(Constants.KAFKA_SPOUT_ID);
	//	builder.setBolt("printbolt", new PrintBolt()).shuffleGrouping(Constants.KAFKA_SPOUT_ID);
		builder.setBolt(Constants.HDFS_CARSIGNAL_BOLT_ID, new GPSSignalHBaseBolt()).shuffleGrouping(Constants.KAFKA_SPOUT_ID);
		Config conf = new Config();
		conf.registerSerialization(RoutePoint.class, FieldSerializer.class);
		StormRunner.runTopologyLocally(builder.createTopology(), "hdfstopologytest", conf, 3000);
	}

}

package com.realtimestudio.transport.storm;

import org.junit.Test;

import backtype.storm.Config;
import backtype.storm.topology.TopologyBuilder;
import backtype.storm.tuple.Fields;

import com.esotericsoftware.kryo.serializers.FieldSerializer;
import com.realtimestudio.transport.model.RoutePoint;
import com.realtimestudio.transport.storm.bolt.GPSSignalHBaseBolt;
import com.realtimestudio.transport.storm.bolt.PrintBolt;
import com.realtimestudio.transport.storm.spout.GPSSignalSpout;
import com.realtimestudio.transport.storm.util.Constants;
import com.realtimestudio.transport.storm.util.StormRunner;

public class HBaseTopologyTest {

	@Test
	public void test() throws InterruptedException {
		TopologyBuilder builder = new TopologyBuilder();
		builder.setSpout(Constants.KAFKA_SPOUT_ID , new GPSSignalSpout());
		builder.setBolt("printbolt", new PrintBolt()).shuffleGrouping(Constants.KAFKA_SPOUT_ID);
		builder.setBolt(Constants.HBASE_CARSIGNAL_BOLT_ID, new GPSSignalHBaseBolt()).fieldsGrouping(Constants.KAFKA_SPOUT_ID, new Fields(Constants.CARIDFIELD));
		Config conf = new Config();
		conf.registerSerialization(RoutePoint.class, FieldSerializer.class);
		StormRunner.runTopologyLocally(builder.createTopology(), "hbasetopologytest", conf, 60);
	}

}

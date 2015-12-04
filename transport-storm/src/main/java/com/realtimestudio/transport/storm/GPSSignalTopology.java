package com.realtimestudio.transport.storm;

import static com.realtimestudio.transport.storm.util.Constants.CARIDFIELD;
import static com.realtimestudio.transport.storm.util.Constants.HBASE_CARSIGNAL_BOLT_ID;
import static com.realtimestudio.transport.storm.util.Constants.HDFS_CARSIGNAL_BOLT_ID;
import static com.realtimestudio.transport.storm.util.Constants.KAFKA_SPOUT_ID;
import static com.realtimestudio.transport.storm.util.Constants.TOPOLOGYHBASEBOLTS;
import static com.realtimestudio.transport.storm.util.Constants.TOPOLOGYHDFSBOLTS;
import static com.realtimestudio.transport.storm.util.Constants.TOPOLOGYNAME;
import static com.realtimestudio.transport.storm.util.Constants.TOPOLOGYSPOUTS;
import static com.realtimestudio.transport.storm.util.Constants.TOPOLOGYWORKERS;
import static com.realtimestudio.transport.utils.StringUtils.parseIntOrDefault;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import backtype.storm.Config;
import backtype.storm.generated.AlreadyAliveException;
import backtype.storm.generated.AuthorizationException;
import backtype.storm.generated.InvalidTopologyException;
import backtype.storm.generated.StormTopology;
import backtype.storm.topology.TopologyBuilder;
import backtype.storm.tuple.Fields;

import com.esotericsoftware.kryo.serializers.FieldSerializer;
import com.realtimestudio.transport.model.RoutePoint;
import com.realtimestudio.transport.storm.bolt.GPSSignalHBaseBolt;
import com.realtimestudio.transport.storm.bolt.GPSSignalHDFSBolt;
import com.realtimestudio.transport.storm.spout.GPSSignalSpout;
import com.realtimestudio.transport.storm.util.Parameters;
import com.realtimestudio.transport.storm.util.StormRunner;


public class GPSSignalTopology {
	private static final int spoutsN = parseIntOrDefault(Parameters.getProperty(TOPOLOGYSPOUTS), 1);
	private static final int hdfsBoltsN = parseIntOrDefault(Parameters.getProperty(TOPOLOGYHDFSBOLTS), 1);
	private static final int hbaseBoltsN = parseIntOrDefault(Parameters.getProperty(TOPOLOGYHBASEBOLTS), 1);
	private static final int workersN = parseIntOrDefault(Parameters.getProperty(TOPOLOGYWORKERS), 1);
	
	private static final Logger LOGGER = LoggerFactory.getLogger(GPSSignalTopology.class);
	
	public StormTopology buildTopology(){
		TopologyBuilder builder = new TopologyBuilder();	
		LOGGER.info("Setting GPSSignalSpout");
		builder.setSpout(KAFKA_SPOUT_ID, new GPSSignalSpout(), spoutsN);
		LOGGER.info("Setting GPSSignalHDFSBolt");
		builder.setBolt(HDFS_CARSIGNAL_BOLT_ID, new GPSSignalHDFSBolt(), hdfsBoltsN).fieldsGrouping(KAFKA_SPOUT_ID, new Fields(CARIDFIELD));
		LOGGER.info("Setting GPSSignalHBaseBolt");
		builder.setBolt(HBASE_CARSIGNAL_BOLT_ID, new GPSSignalHBaseBolt(), hbaseBoltsN).fieldsGrouping(KAFKA_SPOUT_ID, new Fields(CARIDFIELD));		
		LOGGER.info("Creating topology");
		return builder.createTopology();
		
	}
	
	public Config buildConfig(){
		Config conf = new Config();
		conf.registerSerialization(RoutePoint.class, FieldSerializer.class);
		//conf.setNumWorkers(workersN);
		conf.setDebug(true);
		return conf;		
	}
	
	public static void main(String[] args) throws AlreadyAliveException, InvalidTopologyException, AuthorizationException{
		GPSSignalTopology topology = new GPSSignalTopology();
		StormRunner.runTopologyRemotely(topology.buildTopology(), TOPOLOGYNAME, topology.buildConfig());
	}
	
	

}
